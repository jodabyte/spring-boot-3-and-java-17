package com.example.springboot3andjava17.asset;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssetCreatDTO {

    @NotBlank(message = "{validation.asset.name}")
    private String name;
}
