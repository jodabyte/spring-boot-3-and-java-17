package com.example.springboot3andjava17.asset;

import com.example.springboot3andjava17.common.validation.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

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
    return assetRepository.findAll();
  }

  public Asset createAsset(@Valid AssetCreatDto asset) {
    return assetRepository.save(assetMapper.assetCreatDtoToAsset(asset));
  }

  public Asset getAssetById(
      @Pattern(regexp = ValidationConstants.ID_PATTERN, message = "{validation.asset.id}")
          String id) {
    Optional<Asset> asset = assetRepository.findById(id);
    return asset.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public Asset updateAsset(@Valid Asset asset) {
    Optional<Asset> optional = assetRepository.findById(asset.getId());
    if (optional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return assetRepository.save(asset);
  }

  public void deleteAsset(
      @Pattern(regexp = ValidationConstants.ID_PATTERN, message = "{validation.asset.id}")
          String id) {
    Optional<Asset> optional = assetRepository.findById(id);
    Asset asset = optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    assetRepository.delete(asset);
  }
}
