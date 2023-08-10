package de.jodabyte.springboot3andjava17.mqtt.config;

import de.jodabyte.springboot3andjava17.mqtt.MqttMessageHandler;
import de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.Zigbee2MqttHandler;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Slf4j
@Configuration
public class MqttConfiguration {

  @Autowired private MqttProperties properties;

  @Bean
  public MqttConnectOptions mqttConnectOptions() {
    log.info("MqttProperties: {}", properties);
    MqttConnectOptions options = new MqttConnectOptions();
    options.setServerURIs(
        new String[] {String.format("tcp://%s:%d", properties.getHost(), properties.getPort())});
    options.setUserName(properties.getUsername());
    options.setPassword(properties.getPasswordAsCharArray());
    options.setCleanSession(true);
    options.setAutomaticReconnect(true);
    return options;
  }

  @Bean
  public MqttPahoClientFactory mqttClientFactory(MqttConnectOptions mqttConnectOptions) {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    factory.setConnectionOptions(mqttConnectOptions);
    log.info("MqttConnectOptions: {}", mqttConnectOptions);
    return factory;
  }

  @Bean
  public MqttPahoMessageDrivenChannelAdapter mqttInboundAdapter(
      MqttPahoClientFactory mqttPahoClientFactory) {
    MqttPahoMessageDrivenChannelAdapter adapter =
        new MqttPahoMessageDrivenChannelAdapter(
            properties.getClientId(), mqttPahoClientFactory, properties.getAllInitialTopics());
    adapter.setConverter(new DefaultPahoMessageConverter());
    adapter.setQos(0);
    adapter.setOutputChannel(mqttInputChannel());
    return adapter;
  }

  @Bean
  public MessageChannel mqttInputChannel() {
    return new DirectChannel();
  }

  @Bean
  @ServiceActivator(inputChannel = "mqttInputChannel")
  public MessageHandler mqttMessageHandler(Zigbee2MqttHandler zigbee2MqttHandler) {
    return new MqttMessageHandler(zigbee2MqttHandler);
  }
}
