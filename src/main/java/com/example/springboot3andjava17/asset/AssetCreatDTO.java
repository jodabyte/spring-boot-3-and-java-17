package com.example.springboot3andjava17.asset;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetCreatDto {

  @NotBlank(message = "{validation.asset.name}")
  private String name;
}
