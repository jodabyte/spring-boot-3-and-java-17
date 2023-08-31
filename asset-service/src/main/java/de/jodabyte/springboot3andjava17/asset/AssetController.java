package de.jodabyte.springboot3andjava17.asset;

import de.jodabyte.springboot3andjava17.core.asset.Asset;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Assets", description = "Asset Management")
public class AssetController {

  @Autowired private AssetMapper assetMapper;
  @Autowired private AssetService assetService;

  @GetMapping("/assets")
  @Operation(
      summary = "get all assets",
      responses =
          @ApiResponse(
              responseCode = "200",
              content =
                  @Content(array = @ArraySchema(schema = @Schema(implementation = Asset.class)))))
  public List<Asset> all() {
    return assetMapper.entityToDto(assetService.getAllAssets());
  }

  @PostMapping("/assets")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
      summary = "create asset",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              content = @Content(schema = @Schema(implementation = Asset.class))),
      responses =
          @ApiResponse(
              responseCode = "201",
              content = @Content(schema = @Schema(implementation = Asset.class))))
  public Asset create(@RequestBody Asset asset) {
    return assetMapper.entityToDto(assetService.createAsset(assetMapper.dtoToEntity(asset)));
  }

  @GetMapping("/assets/{id}")
  @Operation(
      summary = "find asset by id",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = Asset.class))),
        @ApiResponse(responseCode = "404", content = @Content),
      })
  public Asset findById(@PathVariable String id) {
    return assetMapper.entityToDto(assetService.getAssetById(id));
  }

  @PutMapping("/assets")
  @Operation(
      summary = "update asset by id",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              content = @Content(schema = @Schema(implementation = Asset.class))),
      responses = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = Asset.class))),
        @ApiResponse(responseCode = "404", content = @Content)
      })
  public Asset update(@RequestBody Asset asset) {
    return assetMapper.entityToDto(assetService.updateAsset(assetMapper.dtoToEntity(asset)));
  }

  @DeleteMapping("/assets/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(
      summary = "delete asset by id",
      responses = {
        @ApiResponse(responseCode = "204", content = @Content),
        @ApiResponse(responseCode = "404", content = @Content)
      })
  public void delete(@PathVariable String id) {
    assetService.deleteAsset(id);
  }
}
