# language: pt
Funcionalidade: Testar o formulário do site

  Cenário: Preencher formulário com sucesso
    Dado que eu estou na pagina inicial do site
    E clico no submenu Practice Form
    E preencher todo o formulário com valores aleatórios
    E realizo o upload do Arquivo.txt
    Quando clicar em submit para enviar form
    Então o pop é aberto com sucesso e clico em close

  Cenário: Criar usuário, autenticar, alugar livros e consultar dados
    Dado que eu crio um usuário com nome e senha válidos
    E gero um token de acesso para o usuário criado
    E verifico se o usuário está autorizado
    Quando eu consulto a lista de livros disponíveis
    E seleciono dois livros e associo ao usuário
    Então os dados do usuário devem conter os dois livros alugados