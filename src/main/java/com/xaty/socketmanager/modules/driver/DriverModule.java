package com.xaty.socketmanager.modules.driver;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.protocol.Packet;
import com.xaty.socketmanager.dto.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverModule {

    private static final Logger log = LoggerFactory.getLogger(DriverModule.class);

    private final SocketIONamespace namespace;

    SocketIOServer server;

    public DriverModule(SocketIOServer server) {
        this.server = server;
        this.namespace = server.addNamespace("/driver");
        this.namespace.addEventListener("position", Location.class, onSendPositionReceived());

    }

   /* @OnEvent(value = "position")
    private void onPositionEvent(SocketIOClient client, AckRequest request, Location data) {
        log.debug("Client[{}] - Received location '{}'", client.getSessionId().toString(), data);
        namespace.getBroadcastOperations().sendEvent("send-position", data);
    }*/

    private DataListener<Location> onSendPositionReceived() {
        return (client, data, ackSender) -> {
            log.debug("Client[{}] - Received chat message '{}'", client.getSessionId().toString(), data);
            //namespace.getBroadcastOperations().sendEvent("chat", data);
            server.getNamespace("/passenger").getAllClients().forEach(x -> x.sendEvent("driver",data));
            //server.getNamespace("/passenger").getAllClients().forEach(x -> System.out.println(x.getSessionId()));
        };
    }

    @OnDisconnect
    private void onDisconnected(SocketIOClient client) {
        log.debug("Client[{}] - Disconnected to driver module", client.getSessionId().toString());
    }

    @OnConnect
    private void onConnected(SocketIOClient client) {
        HandshakeData handshakeData = client.getHandshakeData();
        log.debug("Client[{}] - Connected to driver module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
    }
}
