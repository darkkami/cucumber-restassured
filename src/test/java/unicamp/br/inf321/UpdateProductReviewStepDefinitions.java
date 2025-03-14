package unicamp.br.inf321;

import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UpdateProductReviewStepDefinitions {

    private final CucumberWorld cucumberWorld;
    private JSONObject requestBody = new JSONObject();

    public UpdateProductReviewStepDefinitions(CucumberWorld cucumberWorld) {
        this.cucumberWorld = cucumberWorld;
    }

    public JSONObject createReviewJson(int customerId, String date, String description, String rating) {
        JSONObject review = new JSONObject();
        review.put("customerId", customerId);
        review.put("date", date);
        review.put("description", description);
        review.put("rating", rating);
        return review;
    }

    @Dado("Usuário entra na aplicação Multibags")
    public void usuário_entra_na_aplicação_multibags() {
        this.cucumberWorld.setRequest(given().log().all().baseUri("http://multibags.1dt.com.br")
                .contentType(ContentType.JSON.toString())
                .accept(ContentType.JSON.toString())
        );
    }

    @Dado("Um produto com ID existente")
    public void um_produto_com_id_existente(Map<String, String> table) {
        cucumberWorld.addToNotes("productId", table.get("productId"));
    }

    @Dado("usuário que não está autenticado")
    public void usuário_que_não_está_autenticado() {
        cucumberWorld.addToNotes("token", "");
        cucumberWorld.addToNotes("productId", "1");
        cucumberWorld.addToNotes("reviewId", "1");
    }

    @Quando("o usuário enviar a request")
    public void o_usuário_enviar_a_request() {
        // Write code here that turns the phrase above into concrete actions
        String token = cucumberWorld.getFromNotes("token");
        String productId = cucumberWorld.getFromNotes("productId");
        String reviewId = cucumberWorld.getFromNotes("reviewId");
        cucumberWorld.setResponse(cucumberWorld.getRequest()
                .when().header("Authorization", "Bearer " + token)
                .body(requestBody.toString())
                .put("/api/v1/auth/products/" + productId + "/reviews/" + reviewId));
    }

    @Então("a API deve retornar o HTTP Code {int}")
    public void a_api_deve_retornar_o_http_code(Integer expectedStatusCode) {
        if (cucumberWorld.getResponse().statusCode() != expectedStatusCode) {
            System.out.println("Response:\n" + cucumberWorld.getResponse().body().asString());
            throw new RuntimeException("HTTP Code diferente do esperado\nEsperado: " + expectedStatusCode + "\nRetornado: " + cucumberWorld.getResponse().statusCode());
        }
    }

    @Dado("usuário está autenticado")
    public void usuário_está_autenticado(Map<String, String> table) {
        this.cucumberWorld.addToNotes("email", table.get("email"));
        this.cucumberWorld.addToNotes("password", table.get("password"));

        // reusing already existent steps to avoid code duplication
        LoginStepDefinitions loginStepDefinitions = new LoginStepDefinitions(cucumberWorld);

        loginStepDefinitions.isRegisteredOnTheMultibagsWebsite();
        loginStepDefinitions.loginsWithValidCredentials(table);
        loginStepDefinitions.shouldBeLoggedWithSuccess();
    }

    @Dado("o Product ID informado não existe no sistema")
    public void o_product_id_informado_não_existe_no_sistema(Map<String, String> table) {
        cucumberWorld.addToNotes("customerId", table.get("customerId"));
        cucumberWorld.addToNotes("reviewId", table.get("reviewId"));
        cucumberWorld.addToNotes("productId", table.get("productId"));
    }

    @Dado("o review está preenchido com os dados da avaliação")
    public void o_review_está_preenchido_com_os_dados_da_avaliação(Map<String, String> table) {
        int customerId = Integer.parseInt(cucumberWorld.getFromNotes("customerId"));
        String date = table.get("date");
        cucumberWorld.addToNotes("date", table.get("date"));
        String description = table.get("description");
        cucumberWorld.addToNotes("description", table.get("description"));
        String rating = table.get("rating");
        cucumberWorld.addToNotes("rating", table.get("rating"));

        requestBody = createReviewJson(customerId, date, description, rating);
    }

    @Dado("o <reviewId> informado não existe no sistema")
    public void o_review_id_informado_não_existe_no_sistema(Map<String, String> table) {
        cucumberWorld.addToNotes("customerId", table.get("customerId"));
        cucumberWorld.addToNotes("reviewId", table.get("reviewId"));
    }

    @Dado("o <reviewId> informado existe no sistema, porém foi criado por outro usuário")
    public void o_review_id_informado_existe_no_sistema_porém_foi_criado_por_outro_usuário(Map<String, String> table) {
        cucumberWorld.addToNotes("customerId", table.get("customerId"));
        cucumberWorld.addToNotes("reviewId", table.get("reviewId"));
    }

    @Dado("o produto já tem um review criado pelo usuário")
    public void o_produto_já_tem_um_review_criado_pelo_usuário(Map<String, String> table) {
        cucumberWorld.addToNotes("customerId", table.get("customerId"));
        cucumberWorld.addToNotes("reviewId", table.get("reviewId"));
    }
}
