package com.dmos.dmos_manageserver.dmos_register.handler;

import com.dmos.dmos_common.data.ClientReportDTO;
import com.dmos.dmos_common.data.ServerReportDTO;
import com.dmos.dmos_common.message.Message;
import com.dmos.dmos_common.message.MessageType;
import com.dmos.dmos_manageserver.bean.SpringUtil;
import com.dmos.dmos_manageserver.dmos_register.component.DMOSRegisterContext;
import com.dmos.dmos_manageserver.dmos_register.config.JwtConfig;
import com.dmos.dmos_manageserver.dmos_register.util.JwtUtils;
import com.dmos.dmos_manageserver.dmos_register.util.RegisterReport;
import com.dmos.dmos_server.DMOSServerContext;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.stream.Collectors;

@Slf4j
@ChannelHandler.Sharable
public class DMOSRegisterServerHandler extends ChannelInboundHandlerAdapter {
    private final DMOSRegisterContext registerContext = SpringUtil.getBean(DMOSRegisterContext.class);
    private final DMOSServerContext serverContext = SpringUtil.getBean(DMOSServerContext.class);
    private final JwtConfig jwtConfig = SpringUtil.getBean(JwtConfig.class);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("子节点通道已建立: {}", ctx.channel().id().asLongText());
        serverContext.saveChannel(ctx.channel());
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("从子节点通道 {} 中收到信息: {}", ctx.channel().id().asLongText(), msg.toString());
        if(!serverContext.established(ctx.channel().id().asLongText()))
            return;
        Gson gson = new Gson();
        Message message = gson.fromJson(msg.toString(), Message.class);
        if(message.getType() == MessageType.IDENTIFY){
            String verifyToken = message.getData();
            int id = JwtUtils.verify(verifyToken, jwtConfig);
            if(id == -1){
                serverContext.deleteChannel(ctx.channel().id().asLongText());
            }
            else{
                serverContext.verifyChannel(ctx.channel().id().asLongText(), id);
            }
        }
        else if(message.getType() == MessageType.HEARTBEAT){
            serverContext.heartbeat(ctx.channel().id().asLongText());
        }
        else if(message.getType() == MessageType.SERVER_REPORT){
            ServerReportDTO reportDTO = gson.fromJson(message.getData(), ServerReportDTO.class);
            RegisterReport report = registerContext.report(reportDTO);
            // ==================================== report to user ==================================== //
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("子节点通道 {} 出现异常", ctx.channel().id().asLongText());
//        channelCache.deleteChannel(ctx.channel().id().asLongText());
        cause.printStackTrace();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.warn("子节点通道 {} 关闭", ctx.channel().id().asLongText());
        serverContext.deleteChannel(ctx.channel().id().asLongText());
    }

    @Scheduled(fixedRate = 60000)
    public void checkHeartbeat() throws InterruptedException {
        log.info("正在检查心跳");
        serverContext.disconnectTimeout();
        serverContext.resetHeartbeat();
    }
    @Scheduled(fixedRate = 10000)
    public void reportChild(){
        log.info("正在更新子节点信息");
        ServerReportDTO reportDTO = new ServerReportDTO();
        reportDTO.setChild(serverContext.getClients().stream().collect(Collectors.toList()));
        reportDTO.setId(0);
        registerContext.report(reportDTO);
    }
}
