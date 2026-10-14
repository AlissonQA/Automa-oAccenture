
package steps;

import io.cucumber.java.gl.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.After;

import services.AccentureService;

public class AccentureSteps {

    private final AccentureService front =
            new AccentureService();

    // ACESSAR PÁGINA INICIAL

    @Dado("que eu estou na pagina inicial do site")
    public void que_eu_estou_na_pagina_inicial_do_site() {
        front.abrirPaginaInicial();
    }

    // ACESSAR PRACTICE FORM

    @Quando("clico no submenu Practice Form")
    public void eu_clicar_no_submenu_practice_form() {
        front.acessarPracticeForm();
    }

    // PREENCHER FORMULÁRIO

    @Quando("preencher todo o formulário com valores aleatórios")
    public void preencher_todo_o_formulario_com_valores_aleatorios() {
        front.preencherFormulario();
    }

    // UPLOAD DE ARQUIVO

    @Quando("realizo o upload do Arquivo.txt")
    public void realizo_o_upload_do_arquivo_txt() {
        front.realizarUploadArquivo();
    }

    // ENVIAR FORMULÁRIO

    @Quando("clicar em submit para enviar form")
    public void clicar_em_submit_para_enviar_form() {
        front.enviarFormulario();
    }

    // FECHAR POP-UP

    @Entao("o pop é aberto com sucesso e clico em close")
    public void o_pop_e_aberto_com_sucesso_e_clico_em_close() {
        front.fecharPopUp();
    }

    // FECHAR NAVEGADOR AO FINALIZAR

    @After
    public void fecharNavegador() {
        front.fecharNavegador();
    }

}
