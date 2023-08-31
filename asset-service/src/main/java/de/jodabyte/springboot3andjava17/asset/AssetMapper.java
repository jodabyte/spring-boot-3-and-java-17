package de.jodabyte.springboot3andjava17.asset;

import de.jodabyte.springboot3andjava17.asset.model.Asset;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper
public interface AssetMapper {

  Asset dtoToEntity(de.jodabyte.springboot3andjava17.core.asset.Asset asset);

  List<Asset> dtoToEntity(List<de.jodabyte.springboot3andjava17.core.asset.Asset> assets);

  @InheritInverseConfiguration
  de.jodabyte.springboot3andjava17.core.asset.Asset entityToDto(Asset asset);

  @InheritInverseConfiguration
  List<de.jodabyte.springboot3andjava17.core.asset.Asset> entityToDto(List<Asset> assets);
}
