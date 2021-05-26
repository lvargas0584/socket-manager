package com.xaty.socketmanager;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class XatySocketManagerApplication {

    @Value("${rt-server.host}")
    private String host;
    @Value("${rt-server.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketioserver() {
        Configuration config = new Configuration();
        //config.setHostname(host);
        config.setPort(port);
        //This can be used for authentication
        config.setAuthorizationListener(data -> {
            //http://localhost:8081?username=test&password=test
            //Example If you use the above link to connect, you can use the following code to get the user password information,This article does not do authentication
            //string username=data.getsingleurlparam ("username");
            //string password=data.getsingleurlparam ("password");
            return true;
        });
        return new SocketIOServer(config);
    }

    @Bean
    public SpringAnnotationScanner springannotationscanner(SocketIOServer socketserver) {
        return new SpringAnnotationScanner(socketserver);
    }

    public static void main(String[] args) {
        SpringApplication.run(XatySocketManagerApplication.class, args);
    }

}
