package unicamp.br.inf321;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import org.json.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RecoverProductReviewStepDefinitions {

    private final CucumberWorld cucumberWorld;
    private int lastResponseStatus;
    private JSONObject lastResponseBody;

    public RecoverProductReviewStepDefinitions(CucumberWorld cucumberWorld) {
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

    @Dado("um produto com ID existe")
    public void um_produto_com_id_existe(Map<String, String> table) {
        cucumberWorld.addToNotes("productId", table.get("productId"));
    }

    @Dado("o review do produto está disponível")
    public void o_review_do_produto_está_disponível() {
        // Supondo que o review já está criado no sistema, salvamos as informações no contexto
        cucumberWorld.addToNotes("reviewId", "150");
    }

    @Quando("o usuário solicitar a recuperação do review pelo ID {int} do produto")
    public void o_usuário_solicitar_a_recuperação_do_review_pelo_id_do_produto(Integer reviewId) {
        String productId = cucumberWorld.getFromNotes("productId");
        String token = cucumberWorld.getFromNotes("token");

        var response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/v1/auth/products/" + productId + "/reviews/" + reviewId)
                .then()
                .extract();

        lastResponseStatus = response.statusCode();
        lastResponseBody = new JSONObject(response.body().asString());
    }

    @Então("a API deve retornar resposta {int}")
    public void a_api_deve_retornar_a_resposta(Integer expectedStatus) {
        assert lastResponseStatus == expectedStatus;
    }

    @Então("retornar o JSON com os detalhes do review, incluindo campos como customerId, date, description, rating")
    public void retornar_o_json_com_os_detalhes_do_review_incluindo_campos_como_customer_id_date_description_rating() {
        lastResponseBody.getInt("customerId");
        lastResponseBody.getString("date");
        lastResponseBody.getString("description");
        lastResponseBody.getInt("rating");
    }

    @Dado("um produto com ID {int} não existe")
    public void um_produto_com_id_não_existe(Integer productId) {
        cucumberWorld.addToNotes("productId", String.valueOf(productId));
    }

    @Dado("que o usuário não está autenticado")
    public void que_o_usuário_não_está_autenticado() {
        cucumberWorld.addToNotes("token", "");
        cucumberWorld.addToNotes("productId", "1");
        cucumberWorld.addToNotes("reviewId", "1");
    }

    @Dado("o usuário está autenticado, mas não tem permissão para acessar o review")
    public void o_usuário_está_autenticado_mas_não_tem_permissão_para_acessar_o_review() {
        cucumberWorld.addToNotes("token", "INVALID_TOKEN");
    }
}
