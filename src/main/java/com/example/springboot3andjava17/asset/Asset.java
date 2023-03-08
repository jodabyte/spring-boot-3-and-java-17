package com.example.springboot3andjava17.asset;

import org.springframework.data.annotation.Id;

import com.example.springboot3andjava17.common.validation.ValidationConstants;
import com.mongodb.lang.NonNull;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
