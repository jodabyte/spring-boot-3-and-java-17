package de.jodabyte.springboot3andjava17.assetservice.asset;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssetMapper {

  Asset assetCreatDtoToAsset(AssetCreatDto assetCreatDto);
}
