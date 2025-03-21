package unicamp.br.inf321;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.Date;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DeleteReviewStepDefinitions {

    private Response response;
    private final CucumberWorld world;

    public DeleteReviewStepDefinitions(CucumberWorld world) {
        this.world = world;
    }

    @Dado("uma review existente")
    public void uma_review_existente() {
        Integer reviewId = world.getFromNotes("reviewId");
        Integer productId = world.getFromNotes("productId");

        if (reviewId == null || productId == null) {
            throw new IllegalStateException("Nenhum reviewId ou productId salvo no contexto.");
        }

        world.addToNotes("deleteReviewId", reviewId);
        world.addToNotes("deleteProductId", productId);
    }

    @Dado("o usuário está autenticado porém não possui permissão para exclusão")
    public void o_usuario_esta_autenticado_porém_nao_possui_permissao_para_exclusao() {
        this.world.setRequest(given().log().all().baseUri("http://multibags.1dt.com.br")
                .contentType(ContentType.JSON.toString())
                .accept(ContentType.JSON.toString())
        );

        world.addToNotes("token", "TOKEN_SEM_PERMISSAO");
    }

    @Quando("o usuário solicita a exclusão do review")
    public void o_usuario_solicita_a_exclusao_do_review() {
        RequestSpecification spec = world.getRequest();
        Integer reviewId = world.getFromNotes("deleteReviewId");
        Integer productId = world.getFromNotes("deleteProductId");
        String token = world.getFromNotes("token");

        if (reviewId == null || productId == null) {
            throw new IllegalStateException("Review ID ou Product ID não foram recuperados corretamente.");
        }

        if (token != null && !token.isEmpty()) {
            response = spec.header("Authorization", "Bearer " + token)
                    .when()
                    .delete("/api/v1/auth/products/" + productId + "/reviews/" + reviewId);
        } else {
            response = spec.when()
                    .delete("/api/v1/auth/products/" + productId + "/reviews/" + reviewId);
        }
        world.setResponse(response);
    }

    @Entao("a API deve deletar essa review do banco de dados")
    public void a_api_deve_deletar_essa_review_do_banco_de_dados() {
        response.then().statusCode(HttpStatus.SC_OK);
    }

    @Entao("retornar a resposta {int}")
    public void retornar_a_resposta(Integer statusCode) {
        response.then().statusCode(statusCode);
    }

    @Dado("o produto da review possui um ID {int}")
    public void o_produto_da_review_possui_um_id(Integer int1) {
        world.addToNotes("productId", int1);
    }

    @Dado("a review também possui um ID {int} único")
    public void a_review_também_possui_um_id_único(Integer int1) {
        world.addToNotes("reviewId", int1);
    }
}
