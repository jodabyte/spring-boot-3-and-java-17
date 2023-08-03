package de.jodabyte.springboot3andjava17.core.web;

import lombok.Getter;

@Getter
public enum UriContract {
  ASSET_SERVICE("/assets");

  private final String uriPath;

  UriContract(String uriPath) {
    this.uriPath = uriPath;
  }
}
