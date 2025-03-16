package unicamp.br.inf321;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RecoverProductReviewStepDefinitions {

    private final CucumberWorld cucumberWorld;
    private int lastResponseStatus;
    private String lastResponseBody;

    public RecoverProductReviewStepDefinitions(CucumberWorld cucumberWorld) {
        this.cucumberWorld = cucumberWorld;
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

    @Quando("o usuário solicitar a recuperação dos reviews do produto")
    public void o_usuário_solicitar_a_recuperação_do_review_pelo_id_do_produto() {
        String productId = cucumberWorld.getFromNotes("productId");

        cucumberWorld.setResponse(cucumberWorld.getRequest()
                .when()
                .get("/api/v1/products/" + productId + "/reviews"));

        lastResponseStatus = cucumberWorld.getResponse().statusCode();
        System.out.println(cucumberWorld.getResponse().body().asString());
        lastResponseBody = cucumberWorld.getResponse().body().asString();
    }

    @Então("a API deve retornar resposta {int}")
    public void a_api_deve_retornar_a_resposta(Integer expectedStatus) {
        assert lastResponseStatus == expectedStatus;
    }

    @Então("retornar o JSON com os detalhes do review, incluindo campos como customerId, date, description, rating")
    public void retornar_o_json_com_os_detalhes_do_review_incluindo_campos_como_customer_id_date_description_rating() {
        System.out.println(lastResponseBody);

        JSONArray jsonArray = new JSONArray(lastResponseBody);
        jsonArray.getJSONObject(0).getJSONObject("customer").getInt("id");
        jsonArray.getJSONObject(0).getString("date");
        jsonArray.getJSONObject(0).getString("description");
        jsonArray.getJSONObject(0).getInt("rating");
    }

    @Dado("um produto com ID {int} não existe")
    public void um_produto_com_id_não_existe(Integer productId) {
        cucumberWorld.addToNotes("productId", String.valueOf(productId));
    }
}
