package com.example.springboot3andjava17.domain;

import org.springframework.data.annotation.Id;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Asset {

    @Id
    private String id;
    @lombok.NonNull
    @NonNull
    private String name;
}
