package de.jodabyte.springboot3andjava17.test;

import de.jodabyte.springboot3andjava17.asset.Asset;
import de.jodabyte.springboot3andjava17.asset.MqttNetworkConfiguration;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import net.datafaker.Faker;
import net.datafaker.providers.base.Bool;
import net.datafaker.providers.base.Device;
import net.datafaker.providers.base.Vehicle;
import org.apache.commons.lang3.StringUtils;

public class DataFactory {

  private final Vehicle vehicleProvider;
  private final Bool boolProvider;
  private final Device deviceProvider;

  public DataFactory() {
    Faker faker = new Faker(new Random(42));
    this.vehicleProvider = faker.vehicle();
    this.boolProvider = faker.bool();
    this.deviceProvider = faker.device();
  }

  public List<Asset> createAssets(int count) {
    return IntStream.rangeClosed(1, count)
        .mapToObj(
            value -> {
              return createAsset();
            })
        .toList();
  }

  public Asset createAsset() {
    return Asset.of(createAssetName(), createMqttNetworkConfiguration());
  }

  public String createAssetName() {
    return this.vehicleProvider.model();
  }

  public MqttNetworkConfiguration createMqttNetworkConfiguration() {
    return new MqttNetworkConfiguration(createMqttTopic(), this.boolProvider.bool());
  }

  public String createMqttTopic() {
    return Pattern.compile("\s")
        .matcher(
            String.join("/", this.deviceProvider.manufacturer(), this.deviceProvider.modelName()))
        .replaceAll(StringUtils.EMPTY);
  }
}
