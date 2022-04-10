package com.example.mqttdevice;

import java.time.Instant;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import com.example.protocon.core.Gateway;
import com.example.protocon.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GatewayService {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class UploadMessage {
        ArrayList<ArrayList<Long>> data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SensorData {
        long ts;
        int value;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    Gateway gateway;

    @Autowired
    MqttBathroomLightGateway lightGateway;

    @Autowired
    MqttBathroomMotionGateway motionGateway;

    @PostConstruct
    void init() {
        gateway.init(new ArrayList<>());
        gateway.registerSignUpHandler(c -> {
        });
        gateway.registerSignInHandler(c -> {
        });

        gateway.registerRequestHandler((short) 0x0004, (client, request) -> {
            try {
                var msg = objectMapper.readValue(request.getData(), UploadMessage.class);
                for (var item : msg.data) {
                    switch (item.get(0).intValue()) {
                        case 5895:
                            motionGateway.sendToMqtt(
                                    objectMapper
                                            .writeValueAsString(new SensorData(item.get(1), item.get(2).intValue())),
                                    "v1/devices/me/telemetry");
                            break;
                        case 7125:
                            lightGateway.sendToMqtt(
                                    objectMapper
                                            .writeValueAsString(new SensorData(item.get(1), item.get(2).intValue())),
                                    "v1/devices/me/telemetry");
                            break;
                    }
                }
            } catch (Exception e) {
                log.error("解析失败, data: {}", request.getData());
            }

            return new Response(Instant.now().getEpochSecond(), (byte) 0x00, "{}");
        });
    }
}
