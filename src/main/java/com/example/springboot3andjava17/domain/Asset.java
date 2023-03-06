package com.example.springboot3andjava17.domain;

import org.springframework.data.annotation.Id;

import com.example.springboot3andjava17.service.validation.ValidationConstants;
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
    @Pattern(regexp = ValidationConstants.MONGODB_ID_PATTERN, message = "{mongodb.id.message}")
    private String id;
    @NonNull
    @NotBlank(message = "Name is mandatory")
    private String name;
}
