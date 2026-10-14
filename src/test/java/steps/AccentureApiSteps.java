
package steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

import services.AccentureApiService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AccentureApiSteps {

    private final AccentureApiService api =
            new AccentureApiService();

    // CENÁRIO 1 — CRIAR USUÁRIO

    @Dado("que informo nome e senha válidos")
    public void informarCredenciais() {
        // As credenciais são geradas ao criar o usuário.
    }

    @Quando("envio a requisição para criar o usuário")
    public void enviarRequisicaoCriarUsuario() {
        api.criarUsuarioApi();
    }

    @Então("o usuário deve ser criado com sucesso")
    public void validarUsuarioCriado() {
        assertThat(
                api.getUserId() != null &&
                        !api.getUserId().isBlank(),
                is(true)
        );
    }

    // CENÁRIO 2 — GERAR TOKEN

    @Dado("que possuo um usuário cadastrado")
    public void possuirUsuarioCadastrado() {
        api.criarUsuarioApi();
    }

    @Quando("solicito um token utilizando suas credenciais")
    public void solicitarToken() {
        api.gerarTokenApi();
    }

    @Então("o token de acesso deve ser gerado com sucesso")
    public void validarTokenGerado() {
        assertThat(
                api.getToken() != null &&
                        !api.getToken().isBlank(),
                is(true)
        );
    }

    // CENÁRIO 3 — VALIDAR AUTORIZAÇÃO

    @Dado("que possuo um usuário cadastrado e um token válido")
    public void possuirUsuarioEToken() {
        api.criarUsuarioApi();
        api.gerarTokenApi();
    }

    @Quando("consulto o endpoint de autorização")
    public void consultarAutorizacao() {
        api.consultarAutorizacao();
    }

    @Então("o sistema deve confirmar que o usuário está autorizado")
    public void validarAutorizacao() {
        Response response = api.getAutorizacaoResponse();

        assertThat(response.statusCode(), is(200));
        assertThat(response.asString().trim(), is("true"));
    }

    // CENÁRIO 4 — CONSULTAR LIVROS

    @Dado("que a API de livros está disponível")
    public void validarApiLivrosDisponivel() {
        Response response = RestAssured.given()
                .get("/BookStore/v1/Books");

        assertThat(response.statusCode(), is(200));
    }

    @Quando("consulto a lista de livros")
    public void consultarListaLivros() {
        api.consultarLivrosApi();
    }

    @Então("o sistema deve retornar a lista de livros disponíveis")
    public void validarListaLivros() {
        Response response = api.getLivrosResponse();

        assertThat(response.statusCode(), is(200));

        int quantidade = response.jsonPath()
                .getList("books").size();

        assertThat(quantidade, greaterThanOrEqualTo(2));
    }

    // CENÁRIO 5 — ALUGAR DOIS LIVROS

    @Dado("que possuo um usuário autorizado e um token válido")
    public void possuirUsuarioAutorizado() {
        api.criarUsuarioApi();
        api.gerarTokenApi();
        api.validarUsuarioAutorizado();
    }

    @Dado("existem dois livros disponíveis para aluguel")
    public void verificarDoisLivrosDisponiveis() {
        api.consultarLivrosApi();
    }

    @Quando("solicito o aluguel dos dois livros para o usuário")
    public void solicitarAluguelLivros() {
        api.alugarLivrosApi();
    }

    @Então("os dois livros devem ser associados ao usuário com sucesso")
    public void validarAluguelLivros() {
        assertThat(
                api.getAluguelResponse().statusCode(),
                is(201)
        );
    }

    // CENÁRIO 6 — CONSULTAR USUÁRIO E LIVROS ALUGADOS

    @Dado("que possuo um usuário com dois livros alugados")
    public void possuirUsuarioComLivros() {
        api.prepararUsuarioComLivros();
    }

    @Quando("consulto os detalhes do usuário")
    public void consultarDetalhesUsuario() {
        api.consultarDetalhesUsuario();
    }

    @Então("os dados retornados devem conter os dois livros alugados")
    public void validarDetalhesUsuario() {
        Response response = api.getUsuarioResponse();

        assertThat(response.statusCode(), is(200));

        int quantidade = response.jsonPath()
                .getList("books").size();

        assertThat(quantidade, is(2));
    }
}
