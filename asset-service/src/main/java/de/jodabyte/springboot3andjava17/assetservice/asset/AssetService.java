package de.jodabyte.springboot3andjava17.assetservice.asset;

import de.jodabyte.springboot3andjava17.assetservice.common.validation.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
public class AssetService {

  private AssetRepository assetRepository;

  private AssetMapper assetMapper;

  public AssetService(AssetRepository assetRepository, AssetMapper assetMapper) {
    this.assetRepository = assetRepository;
    this.assetMapper = assetMapper;
  }

  public List<Asset> getAllAssets() {
    List<Asset> assets = assetRepository.findAll();
    log.info("Query for all assets, result count={}", assets.size());
    return assets;
  }

  public Asset createAsset(@Valid AssetCreatDto assetDto) {
    Asset asset = assetRepository.save(assetMapper.assetCreatDtoToAsset(assetDto));
    log.info("Added asset={}", asset);
    return asset;
  }

  public Asset getAssetById(
      @Pattern(regexp = ValidationConstants.ID_PATTERN, message = "{validation.asset.id}")
          String id) {
    Optional<Asset> optional = assetRepository.findById(id);
    Asset asset = optional.orElseThrow(() -> prepareNoSuchElementException(id));
    log.info("Query for asset by id, result={}", asset);
    return asset;
  }

  public Asset updateAsset(@Valid Asset assetToUpdate) {
    Optional<Asset> optional = assetRepository.findById(assetToUpdate.getId());
    if (optional.isEmpty()) {
      throw prepareNoSuchElementException(assetToUpdate.getId());
    }
    Asset asset = assetRepository.save(assetToUpdate);
    log.info("Asset updated={}", asset);
    return asset;
  }

  public void deleteAsset(
      @Pattern(regexp = ValidationConstants.ID_PATTERN, message = "{validation.asset.id}")
          String id) {
    Optional<Asset> optional = assetRepository.findById(id);
    Asset asset = optional.orElseThrow(() -> prepareNoSuchElementException(id));
    assetRepository.delete(asset);
    log.info("Asset deleted={}", asset);
  }

  private NoSuchElementException prepareNoSuchElementException(String id) {
    var msg = String.format("Could not find asset with id=%s", id);
    log.warn(msg);
    return new NoSuchElementException(msg);
  }
}
