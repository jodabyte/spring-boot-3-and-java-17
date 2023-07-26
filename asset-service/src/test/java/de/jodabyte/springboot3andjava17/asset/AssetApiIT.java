package de.jodabyte.springboot3andjava17.asset;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import de.jodabyte.springboot3andjava17.common.validation.ValidationConstants;
import de.jodabyte.springboot3andjava17.common.validation.ValidationErrorResponse;
import de.jodabyte.springboot3andjava17.common.validation.Violation;
import de.jodabyte.springboot3andjava17.test.AbstractIntegrationTest;
import de.jodabyte.springboot3andjava17.test.DataFactory;
import de.jodabyte.springboot3andjava17.test.IntegrationTestConfiguration;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(IntegrationTestConfiguration.class)
class AssetApiIT extends AbstractIntegrationTest {

  @Autowired private WebTestClient webClient;

  @Autowired private MessageSource messageSource;

  @Autowired private AssetRepository assetRepository;

  @Autowired private DataFactory dataFactory;

  @Test
  void All_GetAllAssets_ReturnsAllAssets() throws Exception {
    List<Asset> expected = assetRepository.saveAll(dataFactory.createAssets(5));

    ResponseSpec response =
        webClient.get().uri("/assets").accept(MediaType.APPLICATION_JSON).exchange();
    response.expectStatus().isOk();

    List<Asset> actual = response.expectBodyList(Asset.class).returnResult().getResponseBody();
    assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
  }

  @Test
  void Create_SaveAsset_ReturnIsCreated() throws Exception {
    Asset expected = dataFactory.createAsset();

    ResponseSpec response =
        webClient
            .post()
            .uri("/assets")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(expected)
            .exchange();
    response.expectStatus().isCreated();

    Asset actual = response.expectBody(Asset.class).returnResult().getResponseBody();
    assertThat(actual)
        .extracting("id", as(InstanceOfAssertFactories.STRING))
        .matches(ValidationConstants.ID_PATTERN);
    assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
  }

  @Test
  void Create_EmptyRequestBody_ReturnBadRequest() throws Exception {
    String requestBody = "";

    ResponseSpec response =
        webClient
            .post()
            .uri("/assets")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange();

    response.expectStatus().isBadRequest();
    response.expectBody().jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void Create_RequiredAssetPropertiesAreEmpty_ReturnBadRequest() throws Exception {
    Asset invalidAsset = new Asset();
    // List<Violation> expected = Arrays.asList(new Violation("name",
    // messageSource.getMessage("validation.asset.name", null, null)),
    // new Violation("networkconfiguration",
    // messageSource.getMessage("validation.asset.networkconfiguration", null, null)));
    List<Violation> expected =
        Arrays.asList(
            new Violation("name", messageSource.getMessage("validation.asset.name", null, null)),
            new Violation(
                "networkConfiguration",
                messageSource.getMessage("validation.asset.networkconfiguration", null, null)));

    ResponseSpec response =
        webClient
            .post()
            .uri("/assets")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(invalidAsset)
            .exchange();
    response.expectStatus().isBadRequest();

    ValidationErrorResponse actual =
        response.expectBody(ValidationErrorResponse.class).returnResult().getResponseBody();
    assertThat(actual)
        .extracting(ValidationErrorResponse::getViolations)
        .asInstanceOf(InstanceOfAssertFactories.list(Violation.class))
        .containsExactlyInAnyOrderElementsOf(expected);
  }

  @Test
  void FindById_GetAsset_ReturnAsset() throws Exception {
    Asset expected = assetRepository.save(dataFactory.createAsset());

    ResponseSpec response =
        webClient
            .get()
            .uri("/assets/{id}", expected.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();
    response.expectStatus().isOk();

    Asset actual = response.expectBody(Asset.class).returnResult().getResponseBody();
    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void FindById_InvalidId_ReturnBadRequest() throws Exception {
    String invalidId = "1q2w3e4r";
    Violation expected =
        new Violation("id", messageSource.getMessage("validation.asset.id", null, null));

    ResponseSpec response =
        webClient
            .get()
            .uri("/assets/{id}", invalidId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange();
    response.expectStatus().isBadRequest();

    ValidationErrorResponse actual =
        response.expectBody(ValidationErrorResponse.class).returnResult().getResponseBody();
    assertThat(actual)
        .extracting(ValidationErrorResponse::getViolations)
        .asInstanceOf(InstanceOfAssertFactories.list(Violation.class))
        .contains(expected);
  }

  @Test
  void FindById_UnknownId_ReturnNotFound() throws Exception {
    String unknownId = ObjectId.get().toString();

    ResponseSpec response =
        webClient
            .get()
            .uri("/assets/{id}", unknownId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

    response.expectStatus().isNotFound();
    response.expectBody().jsonPath("$.status").isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void Update_UpdateAsset_ReturnAsset() throws Exception {
    Asset expected = assetRepository.save(dataFactory.createAsset());
    expected.setName(dataFactory.createAssetName());
    expected.setNetworkConfiguration(dataFactory.createMqttNetworkConfiguration());

    ResponseSpec response =
        webClient
            .put()
            .uri("/assets")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(expected)
            .exchange();
    response.expectStatus().isOk();

    Asset actual = response.expectBody(Asset.class).returnResult().getResponseBody();
    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void Update_EmptyRequestBody_ReturnBadRequest() throws Exception {
    ResponseSpec response =
        webClient
            .put()
            .uri("/assets")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

    response.expectStatus().isBadRequest();
    response.expectBody().jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void Update_UnknownAsset_ReturnNotFound() throws Exception {
    Asset expected = dataFactory.createAsset();
    expected.setId((ObjectId.get().toString()));

    ResponseSpec response =
        webClient
            .put()
            .uri("/assets")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(expected)
            .exchange();

    response.expectStatus().isNotFound();
    response.expectBody().jsonPath("$.status").isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void Delete_DeleteAsset_ReturnNoContent() throws Exception {
    Asset expected = assetRepository.save(dataFactory.createAsset());

    ResponseSpec response =
        webClient
            .delete()
            .uri("/assets/{id}", expected.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

    response.expectStatus().isNoContent();
  }

  @Test
  void Delete_InvalidId_ReturnBadRequest() throws Exception {
    String invalidId = "1q2w3e4r";
    Violation expected =
        new Violation("id", messageSource.getMessage("validation.asset.id", null, null));

    ResponseSpec response =
        webClient
            .delete()
            .uri("/assets/{id}", invalidId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange();
    response.expectStatus().isBadRequest();

    ValidationErrorResponse actual =
        response.expectBody(ValidationErrorResponse.class).returnResult().getResponseBody();
    assertThat(actual)
        .extracting(ValidationErrorResponse::getViolations)
        .asInstanceOf(InstanceOfAssertFactories.list(Violation.class))
        .contains(expected);
  }

  @Test
  void Delete_DeleteAsset_ReturnNotFound() throws Exception {
    String unknownId = ObjectId.get().toString();

    ResponseSpec response =
        webClient
            .delete()
            .uri("/assets/{id}", unknownId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

    response.expectStatus().isNotFound();
    response.expectBody().jsonPath("$.status").isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @AfterEach
  void afterEach() {
    assetRepository.deleteAll();
  }
}
