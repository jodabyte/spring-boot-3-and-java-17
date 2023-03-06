package com.example.springboot3andjava17.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.springboot3andjava17.domain.Asset;
import com.example.springboot3andjava17.domain.dto.AssetCreatDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssetMapper {

    Asset assetCreatDtoToAsset(AssetCreatDTO assetCreatDTO);
}
