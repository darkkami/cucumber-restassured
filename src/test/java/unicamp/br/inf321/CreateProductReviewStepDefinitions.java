package unicamp.br.inf321;

import io.cucumber.java.en.Then;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateProductReviewStepDefinitions {

    private final CucumberWorld cucumberWorld;

    public CreateProductReviewStepDefinitions(CucumberWorld cucumberWorld) {
        this.cucumberWorld = cucumberWorld;
    }

    @Dado("o usuário está autenticado")
    public void usuarioEstaAutenticado(Map<String, String> table) {
        this.cucumberWorld.addToNotes("email", table.get("email"));
        this.cucumberWorld.addToNotes("password", table.get("password"));

        LoginStepDefinitions loginStepDefinitions = new LoginStepDefinitions(cucumberWorld);
        loginStepDefinitions.isRegisteredOnTheMultibagsWebsite();
        loginStepDefinitions.loginsWithValidCredentials(table);
        loginStepDefinitions.shouldBeLoggedWithSuccess();
    }

    @Quando("o usuário informar um comentário e uma nota")
    public void usuarioInformaComentarioENota(Map<String, String> table) {
        String token = (String) Optional.ofNullable(cucumberWorld.getFromNotes("token"))
                .orElseThrow(() -> new IllegalStateException("Token não encontrado no contexto do Cucumber"));
        Integer customerId = (Integer) Optional.ofNullable(cucumberWorld.getFromNotes("customerId"))
                .orElseThrow(() -> new IllegalStateException("CustomerId não encontrado no contexto do Cucumber"));
        Integer productId = Optional.ofNullable(table.get("id"))
                .map(Integer::parseInt)
                .orElseThrow(() -> new IllegalArgumentException("ID do produto não pode ser nulo"));
        String description = Optional.ofNullable(table.get("description"))
                .orElseThrow(() -> new IllegalArgumentException("Descrição não pode ser nula"));
        double rating = Optional.ofNullable(table.get("rating"))
                .map(Double::parseDouble)
                .orElseThrow(() -> new IllegalArgumentException("Nota não pode ser nula"));

        JSONObject review = new JSONObject();
        review.put("customerId", customerId);
        review.put("description", description);
        review.put("id", 0);
        review.put("rating", rating);

        cucumberWorld.addToNotes("productId", productId);

        cucumberWorld.setResponse(
                cucumberWorld.getRequest()
                        .when().header("Authorization", "Bearer " + token)
                        .body(review.toString())
                        .post("/api/v1/auth/products/" + productId + "/reviews"));
    }

    @Entao("a API deve registrar esse review na base de dados")
    public void apiDeveRegistrarReviewNaBaseDeDados() {
        Integer reviewId = cucumberWorld.getResponse().then().log().all().assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body(matchesJsonSchemaInClasspath("unicamp/br/inf321/ProductReviewJsonSchema.json"))
                .body("id", not(blankOrNullString()))
                .extract().body().jsonPath().get("id");

        assertThat("ID deve ser um inteiro", reviewId, not(0));
        cucumberWorld.addToNotes("reviewId", reviewId);
    }

    @Entao("a API deve retornar a resposta 401")
    public void apiDeveRetornarNaoAutorizado() {
        cucumberWorld.getResponse().then().log().all().assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(matchesJsonSchemaInClasspath("unicamp/br/inf321/ErrorJsonSchema.json"))
                .body("status", not(blankOrNullString()));
    }

    @Entao("a API deve retornar a resposta 403")
    public void apiDeveRetornarProibido() {
        cucumberWorld.getResponse().then().log().all().assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body(matchesJsonSchemaInClasspath("unicamp/br/inf321/ErrorJsonSchema.json"))
                .body("status", not(blankOrNullString()));
    }
}