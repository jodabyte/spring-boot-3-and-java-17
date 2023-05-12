package de.jodabyte.springboot3andjava17.assetservice.test;

import de.jodabyte.springboot3andjava17.assetservice.asset.Asset;
import de.jodabyte.springboot3andjava17.assetservice.asset.AssetCreatDto;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import net.datafaker.Faker;
import net.datafaker.providers.base.Vehicle;

public class DataFactory {

  private final Vehicle vehicleProvider;

  public DataFactory() {
    this.vehicleProvider = new Faker(new Random(42)).vehicle();
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
    return new Asset(null, createAssetName());
  }

  public AssetCreatDto createAssetCreatDTO() {
    return new AssetCreatDto(createAssetName());
  }

  public String createAssetName() {
    return vehicleProvider.model();
  }
}
