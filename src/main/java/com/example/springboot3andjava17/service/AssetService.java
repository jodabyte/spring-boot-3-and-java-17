package com.example.springboot3andjava17.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.springboot3andjava17.adapter.AssetRepository;
import com.example.springboot3andjava17.domain.Asset;
import com.example.springboot3andjava17.domain.AssetCreatUpdateDTO;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset createAsset(AssetCreatUpdateDTO asset) {

        return assetRepository.insert(Asset.of(asset.getName()));
    }

    public Asset getAssetById(String id) {
        Optional<Asset> asset = assetRepository.findById(id);
        return asset.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Asset updateOrCreateAsset(String id, AssetCreatUpdateDTO asset) {
        return assetRepository.save(Asset.of(id, asset.getName()));
    }

    public void deleteAsset(String id) {
        Optional<Asset> optional = assetRepository.findById(id);
        Asset asset = optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        assetRepository.delete(asset);
    }
}
