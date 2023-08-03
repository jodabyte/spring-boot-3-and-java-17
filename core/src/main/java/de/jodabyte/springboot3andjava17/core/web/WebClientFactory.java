package de.jodabyte.springboot3andjava17.core.web;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientFactory {

  private WebClientFactory() {}

  public static WebClient getWebClient(String baseUrl) {
    return WebClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

  public static <T> Mono<T> prepareGetRequest(
      WebClient client, String uri, ParameterizedTypeReference<T> responseType) {
    return client
        .get()
        .uri(uri)
        .accept(MediaType.APPLICATION_JSON)
        .exchangeToMono(
            response -> {
              if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(responseType);
              } else {
                return response.createException().flatMap(Mono::error);
              }
            });
  }

  public static <T> Mono<ResponseEntity<Void>> preparePostRequest(
      WebClient client, String uri, T body) {
    return preparePostRequest(client, uri, body, HttpStatus.OK);
  }

  public static <T> Mono<ResponseEntity<Void>> preparePostRequest(
      WebClient client, String uri, T body, HttpStatus responseStatus) {
    return client
        .post()
        .uri(uri)
        .bodyValue(body)
        .accept(MediaType.APPLICATION_JSON)
        .exchangeToMono(
            response -> {
              if (response.statusCode().equals(responseStatus)) {
                return response.toBodilessEntity();
              } else {
                return response.createException().flatMap(Mono::error);
              }
            });
  }

  public static <T> Mono<T> preparePutRequest(
      WebClient client, String uri, T body, ParameterizedTypeReference<T> responseType) {
    return client
        .put()
        .uri(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(body)
        .exchangeToMono(
            response -> {
              if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(responseType);
              } else {
                return response.createException().flatMap(Mono::error);
              }
            });
  }
}
