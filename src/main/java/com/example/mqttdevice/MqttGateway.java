package com.example.mqttdevice;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}

@Component
@MessagingGateway(defaultRequestChannel = "mqttBathroomLightOutboundChannel")
interface MqttBathroomLightGateway {
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}

@Component
@MessagingGateway(defaultRequestChannel = "mqttBathroomMotionOutboundChannel")
interface MqttBathroomMotionGateway {
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}

@Component
@MessagingGateway(defaultRequestChannel = "mqttBathroomHumidityOutboundChannel")
interface MqttBathroomHumidityGateway {
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}

@Component
@MessagingGateway(defaultRequestChannel = "mqttBathroomTemperatureOutboundChannel")
interface MqttBathroomTemperatureGateway {
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}

@Component
@MessagingGateway(defaultRequestChannel = "mqttBathroomWashingmachineOutboundChannel")
interface MqttBathroomWashingmachineGateway {
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}
