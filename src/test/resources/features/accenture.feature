# language: pt

Funcionalidade: Testar o formulário do site

  Cenário: Preencher formulário com sucesso
    Dado que eu estou na pagina inicial do site
    Quando clico no submenu Practice Form
    E preencher todo o formulário com valores aleatórios
    E realizo o upload do Arquivo.txt
    E clicar em submit para enviar form
    Então o pop é aberto com sucesso e clico em close
