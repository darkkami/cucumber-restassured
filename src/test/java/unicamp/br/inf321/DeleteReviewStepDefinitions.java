package unicamp.br.inf321;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DeleteReviewStepDefinitions {

    private int productId;
    private int reviewId;
    private String token;
    private Response response;
    private final CucumberWorld world;

    public DeleteReviewStepDefinitions(CucumberWorld world) {
        this.world = world;
    }

    @Dado("uma review existente")
    public void uma_review_existente() {
        reviewId = 1;
    }

    @Dado("o produto da review possui um ID {int}")
    public void o_produto_da_review_possui_um_id(Integer prodId) {
        this.productId = prodId;
    }

    @Dado("a review também possui um ID {int} único")
    public void a_review_também_possui_um_id_único(Integer rId) {
        this.reviewId = rId;
    }

    @Dado("o usuário está autenticado")
    public void o_usuario_esta_autenticado() {
        this.world.setRequest(given().log().all().baseUri("http://multibags.1dt.com.br")
                .contentType(ContentType.JSON.toString())
                .accept(ContentType.JSON.toString()));

        String username = "o181804@g.unicamp.br";
        String password = "aA#123456789";

        JSONObject requestBody = new JSONObject();
        requestBody.put("username", username);
        requestBody.put("password", password);

        world.setResponse(world.getRequest()
                .body(requestBody.toString())
                .when().post("/api/v1/customer/login"));

        token = world.getResponse().then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("token", not(blankOrNullString()))
                .extract().body().jsonPath().get("token");
    }

    @Dado("o usuário não está autenticado")
    public void o_usuario_nao_esta_autenticado() {
        token = "";
    }

    @Dado("o usuário está autenticado porém não possui permissão para exclusão")
    public void o_usuario_esta_autenticado_porém_nao_possui_permissao_para_exclusao() {
        token = "TOKEN_SEM_PERMISSAO";
    }

    @Quando("o usuário solicitar a exclusão da review por meio da reviewId {int} e ID {int} do produto")
    public void o_usuario_solicitar_a_exclusao_da_review_por_meio_da_review_id_e_id_do_produto(Integer rId, Integer prodId) {
        this.productId = prodId;
        this.reviewId = rId;

        RequestSpecification spec;
        try {
            spec = world.getRequest();
        } catch (IllegalArgumentException e) {
            spec = given().baseUri("http://multibags.1dt.com.br");
        }

        if (token != null && !token.isEmpty()) {
            response = spec.header("Authorization", "Bearer " + token)
                    .when()
                    .delete("/api/v1/auth/products/" + productId + "/reviews/" + reviewId);
        } else {
            response = spec.when()
                    .delete("/api/v1/auth/products/" + productId + "/reviews/" + reviewId);
        }
    }

    @Entao("a API deve deletar essa review do banco de dados")
    public void a_api_deve_deletar_essa_review_do_banco_de_dados() {
        response.then().statusCode(HttpStatus.SC_OK);
    }

    @Entao("retornar a resposta {int}")
    public void retornar_a_resposta(Integer statusCode) {
        response.then().statusCode(statusCode);
    }

    @Entao("a API vai ignorar a solicitação de exclusão")
    public void a_api_vai_ignorar_a_solicitacao_de_exclusao() {
        response.then().statusCode(HttpStatus.SC_FORBIDDEN);
    }
}