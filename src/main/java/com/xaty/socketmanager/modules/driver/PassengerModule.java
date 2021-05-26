package com.xaty.socketmanager.modules.driver;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.listener.DataListener;
import com.xaty.socketmanager.dto.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PassengerModule {

    private static final Logger log = LoggerFactory.getLogger(PassengerModule.class);

    private final SocketIONamespace namespace;

    @Autowired
    public PassengerModule(SocketIOServer server) {
        this.namespace = server.addNamespace("/passenger");
    }

    @OnDisconnect
    private void onDisconnected(SocketIOClient client) {
        log.debug("Client[{}] - Disconnected to passenger module", client.getSessionId().toString());
    }

    @OnConnect
    private void onConnected(SocketIOClient client) {
        HandshakeData handshakeData = client.getHandshakeData();
        log.debug("Client[{}] - Connected to passenger module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
    }
}
