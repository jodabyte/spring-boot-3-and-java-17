package com.example.springboot3andjava17.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot3andjava17.domain.Asset;
import com.example.springboot3andjava17.domain.AssetCreatUpdateDTO;
import com.example.springboot3andjava17.service.AssetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Assets", description = "Asset Management")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/assets")
    @Operation(summary = "get all", responses = @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Asset.class))))
    public List<Asset> all() {
        return assetService.getAllAssets();
    }

    @PostMapping("/assets")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create asset", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssetCreatUpdateDTO.class))), responses = @ApiResponse(responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Asset.class))))
    public Asset createAsset(@RequestBody(required = true) AssetCreatUpdateDTO asset) {
        return assetService.createAsset(asset);
    }

    @GetMapping("/assets/{id}")
    @Operation(summary = "find by id", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Asset.class))),
            @ApiResponse(responseCode = "404", content = @Content),
    })
    public Asset findById(@PathVariable(required = true) String id) {
        return assetService.getAssetById(id);
    }

    @PutMapping("/assets/{id}")
    @Operation(summary = "update by id", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssetCreatUpdateDTO.class))), responses = @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Asset.class))))
    public Asset update(@PathVariable(required = true) String id,
            @RequestBody(required = true) AssetCreatUpdateDTO asset) {
        return assetService.updateOrCreateAsset(id, asset);
    }

    @DeleteMapping("/assets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete by id", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public void delete(@PathVariable(required = true) String id) {
        assetService.deleteAsset(id);
    }
}
