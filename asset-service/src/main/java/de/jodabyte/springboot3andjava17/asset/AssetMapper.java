package de.jodabyte.springboot3andjava17.asset;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssetMapper {

  Asset assetCreatDtoToAsset(AssetCreatDto assetCreatDto);
}
