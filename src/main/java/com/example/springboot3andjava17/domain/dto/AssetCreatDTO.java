package com.example.springboot3andjava17.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssetCreatDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
