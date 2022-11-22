package com.dmos.dmos_manageserver.dmos_register.component;

import com.dmos.dmos_common.util.Port;
import com.dmos.dmos_manageserver.dmos_register.config.ThreadPoolTaskConfig;
import com.dmos.dmos_manageserver.dmos_register.handler.DMOSRegisterServerHandler;
import com.dmos.dmos_server.DMOSServer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetSocketAddress;

@Component
public class DMOSPoolRegisterServer {
    @Resource
    private ThreadPoolTaskConfig poolTaskExecutor;
    private static DMOSPoolRegisterServer single = null;

    private DMOSServer server;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        single = this;
        single.poolTaskExecutor = this.poolTaskExecutor;
        // 初使化时将已静态化的testService实例化
    }

    public static DMOSPoolRegisterServer getSingle(){
        return single;
    }

    public void  run(){
        poolTaskExecutor.taskExecutor().execute(new Runnable() {
            @Override
            public void run() {
                //启动服务端
                System.out.println("DMOSPoolRegisterServer当前线程池：" + Thread.currentThread().getName());
                DMOSServer server = new DMOSServer(new InetSocketAddress("127.0.0.1", Port.REGISTER_CHANNEL_PORT), new DMOSRegisterServerHandler());
                server.start();
            }
        });
    }

    public void stop(){
        if(server!= null && server.isRunning){
            server.stop();
        }
    }

    public boolean getIsRunning(){
        if(server == null){return false;}
        return  server.isRunning;
    }
}
