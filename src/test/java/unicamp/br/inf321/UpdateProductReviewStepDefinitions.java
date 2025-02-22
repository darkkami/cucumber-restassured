package unicamp.br.inf321;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import org.json.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UpdateProductReviewStepDefinitions {

    private final CucumberWorld cucumberWorld;

    public UpdateProductReviewStepDefinitions(CucumberWorld cucumberWorld) {
        this.cucumberWorld = cucumberWorld;
    }

    @Dado("Usuário entra na aplicação Multibags")
    public void usuário_entra_na_aplicação_multibags() {
        this.cucumberWorld.setRequest(given().log().all().baseUri("http://multibags.1dt.com.br")
                .contentType(ContentType.JSON.toString())
                .accept(ContentType.JSON.toString())
        );
    }

    @Dado("Um produto com ID definido")
    public void um_produto_com_id_definido() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
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
                .put("/api/v1/auth/products/"+ productId + "/reviews/" + reviewId));
    }

    @Então("a API deve retornar o HTTP Code {int}")
    public void a_api_deve_retornar_o_http_code(Integer int1) {
        if(cucumberWorld.getResponse().statusCode() != int1){
            throw new RuntimeException("HTTP Code diferente do esperado\nEsperado: " + int1 + "\nRetornado: " + cucumberWorld.getResponse().statusCode());
        }
    }

    @Dado("usuário está autenticado")
    public void usuário_está_autenticado(Map<String, String> table) {
        String username = table.get("email");
        String password = table.get("password");
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", username);
        requestBody.put("password", password);
        cucumberWorld.setResponse(cucumberWorld.getRequest()
                .body(requestBody.toString())
                .when().post("/api/v1/customer/login"));
    }

    @Dado("o Product ID informado não existe no sistema")
    public void o_product_id_informado_não_existe_no_sistema() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Dado("o <reviewId> informado não existe no sistema")
    public void o_review_id_informado_não_existe_no_sistema() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Dado("o <reviewId> informado existe no sistema, porém foi criado por outro usuário")
    public void o_review_id_informado_existe_no_sistema_porém_foi_criado_por_outro_usuário() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Dado("o produto já tem um review criado pelo usuário")
    public void o_produto_já_tem_um_review_criado_pelo_usuário() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Quando("os dados do review forem preenchidos corretamente, inserindo a nota no campo “rating” e a descrição do review no campo “description”")
    public void os_dados_do_review_forem_preenchidos_corretamente_inserindo_a_nota_no_campo_rating_e_a_descrição_do_review_no_campo_description() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Então("a API deve atualizar o review do cliente com os novos dados informados")
    public void a_api_deve_atualizar_o_review_do_cliente_com_os_novos_dados_informados() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
