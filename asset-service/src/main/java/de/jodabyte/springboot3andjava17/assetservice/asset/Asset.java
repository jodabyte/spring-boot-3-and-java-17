package de.jodabyte.springboot3andjava17.assetservice.asset;

import com.mongodb.lang.NonNull;
import de.jodabyte.springboot3andjava17.assetservice.common.validation.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asset {

  @Id
  @Pattern(regexp = ValidationConstants.ID_PATTERN, message = "{validation.asset.id}")
  private String id;

  @NonNull
  @NotBlank(message = "{validation.asset.name}")
  private String name;
}
