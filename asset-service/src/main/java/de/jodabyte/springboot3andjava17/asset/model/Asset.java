package de.jodabyte.springboot3andjava17.asset.model;

import de.jodabyte.springboot3andjava17.core.asset.NetworkConfiguration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public final class Asset {

  @Id private String id;

  @NotBlank(message = "{validation.asset.name}")
  private String name;

  @NotNull(message = "{validation.asset.networkConfiguration}")
  @Valid
  private NetworkConfiguration networkConfiguration;
}
