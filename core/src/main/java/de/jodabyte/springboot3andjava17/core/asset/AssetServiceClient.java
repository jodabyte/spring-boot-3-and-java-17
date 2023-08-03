package de.jodabyte.springboot3andjava17.core.asset;

import de.jodabyte.springboot3andjava17.core.web.UriContract;
import de.jodabyte.springboot3andjava17.core.web.WebClientFactory;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AssetServiceClient {

  @Value("${application.asset-service.url}")
  private String assetServiceUrl;

  private WebClient client;

  @PostConstruct
  private void init() {
    client = WebClientFactory.getWebClient(this.assetServiceUrl);
  }

  public List<Asset> getAllAssets() {
    Mono<List<Asset>> exchange =
        WebClientFactory.prepareGetRequest(
            client, UriContract.ASSET_SERVICE.getUriPath(), new ParameterizedTypeReference<>() {});
    return exchange.block();
  }

  public void createAsset(Asset asset) {
    Mono<ResponseEntity<Void>> exchange =
        WebClientFactory.preparePostRequest(
            client, UriContract.ASSET_SERVICE.getUriPath(), asset, HttpStatus.CREATED);
    exchange.block();
  }

  public Asset updateAsset(Asset asset) {
    Mono<Asset> exchange =
        WebClientFactory.preparePutRequest(
            client,
            UriContract.ASSET_SERVICE.getUriPath(),
            asset,
            new ParameterizedTypeReference<>() {});
    return exchange.block();
  }
}
