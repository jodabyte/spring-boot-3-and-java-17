package de.jodabyte.springboot3andjava17.asset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Asset {

  @Id private String id;

  @NotBlank(message = "{validation.asset.name}")
  private String name;

  @NotNull(message = "{validation.asset.networkconfiguration}")
  private NetworkConfiguration networkConfiguration;

  public static Asset of(
      final @NonNull String name, final @NonNull NetworkConfiguration networkConfiguration) {
    return new Asset(null, name, networkConfiguration);
  }
}
