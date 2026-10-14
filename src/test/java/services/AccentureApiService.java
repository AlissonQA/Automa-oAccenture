package services;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AccentureApiService {

    private String userId;
    private String token;
    private String userName;
    private final String password = "Senha@123";

    private String isbn1;
    private String isbn2;

    private Response livrosResponse;
    private Response aluguelResponse;
    private Response usuarioResponse;
    private Response autorizacaoResponse;

    public AccentureApiService() {
        RestAssured.baseURI = "https://demoqa.com";
    }

    // CRIAR USUÁRIO

    public void criarUsuarioApi() {
        userName = "usuario" +
                UUID.randomUUID().toString()
                        .replace("-", "").substring(0, 10);

        String body = """
                {
                    "userName": "%s",
                    "password": "%s"
                }
                """.formatted(userName, password);

        Response response = given()
                .contentType("application/json")
                .body(body)
                .post("/Account/v1/User");

        assertThat(response.statusCode(), is(201));

        userId = response.jsonPath().getString("userID");
    }

    // GERAR TOKEN

    public void gerarTokenApi() {
        String body = """
                {
                    "userName": "%s",
                    "password": "%s"
                }
                """.formatted(userName, password);

        Response response = given()
                .contentType("application/json")
                .body(body)
                .post("/Account/v1/GenerateToken");

        assertThat(response.statusCode(), is(200));

        token = response.jsonPath().getString("token");

        assertThat(token != null && !token.isBlank(), is(true));
    }

    // CONSULTAR LIVROS

    public void consultarLivrosApi() {
        livrosResponse = given()
                .get("/BookStore/v1/Books");

        assertThat(livrosResponse.statusCode(), is(200));

        int quantidade = livrosResponse.jsonPath()
                .getList("books").size();

        assertThat(quantidade, greaterThanOrEqualTo(2));

        isbn1 = livrosResponse.jsonPath()
                .getString("books[0].isbn");

        isbn2 = livrosResponse.jsonPath()
                .getString("books[1].isbn");
    }

    // ALUGAR LIVROS

    public void alugarLivrosApi() {
        String body = """
                {
                    "userId": "%s",
                    "collectionOfIsbns": [
                        {"isbn": "%s"},
                        {"isbn": "%s"}
                    ]
                }
                """.formatted(userId, isbn1, isbn2);

        aluguelResponse = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(body)
                .post("/BookStore/v1/Books");
    }

    // PREPARAR USUÁRIO COM DOIS LIVROS

    public void prepararUsuarioComLivros() {
        criarUsuarioApi();
        gerarTokenApi();
        consultarLivrosApi();
        alugarLivrosApi();

        assertThat(aluguelResponse.statusCode(), is(201));
    }

    // CONSULTAR AUTORIZAÇÃO

    public void consultarAutorizacao() {
        String body = """
                {
                    "userName": "%s",
                    "password": "%s"
                }
                """.formatted(userName, password);

        autorizacaoResponse = given()
                .contentType("application/json")
                .body(body)
                .post("/Account/v1/Authorized");
    }

    // CONSULTAR DETALHES DO USUÁRIO

    public void consultarDetalhesUsuario() {
        usuarioResponse = given()
                .header("Authorization", "Bearer " + token)
                .get("/Account/v1/User/" + userId);
    }

    // VALIDAR AUTORIZAÇÃO

    public void validarUsuarioAutorizado() {
        consultarAutorizacao();

        assertThat(autorizacaoResponse.statusCode(), is(200));
        assertThat(autorizacaoResponse.asString().trim(), is("true"));
    }

    // GETTERS PARA OS STEPS

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public Response getLivrosResponse() {
        return livrosResponse;
    }

    public Response getAluguelResponse() {
        return aluguelResponse;
    }

    public Response getUsuarioResponse() {
        return usuarioResponse;
    }

    public Response getAutorizacaoResponse() {
        return autorizacaoResponse;
    }
}
