package de.jodabyte.springboot3andjava17.mqtt.config;

import de.jodabyte.springboot3andjava17.mqtt.MqttMessageHandler;
import de.jodabyte.springboot3andjava17.mqtt.zigbee2mqtt.Zigbee2MqttHandler;
import de.jodabyte.springboot3andjava17.openapi.asset.ApiClient;
import de.jodabyte.springboot3andjava17.openapi.asset.api.AssetsApi;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

@Slf4j
@Configuration
public class MqttConfiguration {

  private static final String CLIENT_ID_FORMAT = "%s-%d";

  @Autowired private MqttProperties properties;

  @Bean
  public AssetsApi getAssetsApi(@Value("${application.asset-service.url}") String assetServiceUrl) {
    return new AssetsApi(getApiClient(assetServiceUrl));
  }

  @Bean
  public Zigbee2MqttHandler getZigbee2MqttHandler(
      AssetsApi assetServiceApi, MqttPahoMessageDrivenChannelAdapter mqttInboundClient) {
    return new Zigbee2MqttHandler(assetServiceApi, mqttInboundClient);
  }

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
    log.info("MqttConnectOptions: {}", options);
    return options;
  }

  @Bean
  public MqttPahoClientFactory mqttClientFactory(MqttConnectOptions mqttConnectOptions) {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    factory.setConnectionOptions(mqttConnectOptions);
    return factory;
  }

  @Bean
  public MqttPahoMessageDrivenChannelAdapter mqttInboundAdapter(
      MqttPahoClientFactory mqttPahoClientFactory) {
    String clientId = String.format(CLIENT_ID_FORMAT, properties.getClientId(), System.nanoTime());
    MqttPahoMessageDrivenChannelAdapter adapter =
        new MqttPahoMessageDrivenChannelAdapter(
            clientId, mqttPahoClientFactory, properties.getAllInitialTopics());
    adapter.setConverter(new DefaultPahoMessageConverter());
    adapter.setQos(0);
    adapter.setOutputChannel(mqttInputChannel());
    log.info("MqttPahoMessageDrivenChannelAdapter clientId: {}", clientId);
    return adapter;
  }

  @Bean
  public MessageChannel mqttInputChannel() {
    return new DirectChannel();
  }

  @Bean
  @ServiceActivator(inputChannel = "mqttInputChannel")
  public MqttMessageHandler mqttMessageHandler(Zigbee2MqttHandler zigbee2MqttHandler) {
    return new MqttMessageHandler(zigbee2MqttHandler);
  }

  private ApiClient getApiClient(String assetServiceUrl) {
    return new ApiClient().setBasePath(assetServiceUrl);
  }
}
