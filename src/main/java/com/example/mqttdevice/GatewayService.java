package com.example.mqttdevice;

import java.time.Instant;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import com.example.protocon.core.Gateway;
import com.example.protocon.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {
    @Autowired
    Gateway gateway;

    @PostConstruct
    void init() {
        gateway.init(new ArrayList<>());
        gateway.registerSignUpHandler(c -> {
        });
        gateway.registerSignInHandler(c -> {
        });

        gateway.registerRequestHandler((short) 0x0004, (client, request) -> {
            return new Response(Instant.now().getEpochSecond(), (byte) 0x00, "{}");
        });
    }
}
