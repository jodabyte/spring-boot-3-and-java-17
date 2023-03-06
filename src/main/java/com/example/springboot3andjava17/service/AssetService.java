package com.example.springboot3andjava17.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import com.example.springboot3andjava17.domain.Asset;
import com.example.springboot3andjava17.domain.dto.AssetCreatDTO;
import com.example.springboot3andjava17.domain.mapper.AssetMapper;
import com.example.springboot3andjava17.repository.AssetRepository;
import com.example.springboot3andjava17.service.validation.ValidationConstants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Service
@Validated
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetMapper assetMapper;

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset createAsset(@Valid AssetCreatDTO asset) {
        return assetRepository.insert(assetMapper.assetCreatDtoToAsset(asset));
    }

    public Asset getAssetById(
            @Pattern(regexp = ValidationConstants.MONGODB_ID_PATTERN, message = "{mongodb.id.message}") String id) {
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
            @Pattern(regexp = ValidationConstants.MONGODB_ID_PATTERN, message = "{mongodb.id.message}") String id) {
        Optional<Asset> optional = assetRepository.findById(id);
        Asset asset = optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        assetRepository.delete(asset);
    }
}
