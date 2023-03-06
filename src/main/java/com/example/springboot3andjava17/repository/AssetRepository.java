package com.example.springboot3andjava17.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.springboot3andjava17.domain.Asset;

public interface AssetRepository extends MongoRepository<Asset, String> {
}
