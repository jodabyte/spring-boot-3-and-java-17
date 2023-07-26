package de.jodabyte.springboot3andjava17.common.web;

import lombok.Getter;

@Getter
public enum UriContract {
  ASSET_GET("/assets"),
  ASSET_POST("/assets");

  private final String uriPath;

  private UriContract(String uriPath) {
    this.uriPath = uriPath;
  }
}
