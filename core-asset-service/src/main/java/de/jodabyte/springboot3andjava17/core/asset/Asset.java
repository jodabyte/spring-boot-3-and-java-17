package de.jodabyte.springboot3andjava17.core.asset;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Asset {

  @Nullable private String id;

  @NotBlank(message = "{validation.asset.name}")
  private String name;

  @NotNull(message = "{validation.asset.networkConfiguration}")
  @Valid
  private NetworkConfiguration networkConfiguration;
}
