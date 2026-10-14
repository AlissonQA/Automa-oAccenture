# language: pt

Funcionalidade: Testar APIs de usuários e livros

  Cenário: Criar usuário
    Dado que informo nome e senha válidos
    Quando envio a requisição para criar o usuário
    Então o usuário deve ser criado com sucesso

  Cenário: Gerar token de acesso
    Dado que possuo um usuário cadastrado
    Quando solicito um token utilizando suas credenciais
    Então o token de acesso deve ser gerado com sucesso

  Cenário: Validar autorização do usuário
    Dado que possuo um usuário cadastrado e um token válido
    Quando consulto o endpoint de autorização
    Então o sistema deve confirmar que o usuário está autorizado

  Cenário: Consultar livros disponíveis
    Dado que a API de livros está disponível
    Quando consulto a lista de livros
    Então o sistema deve retornar a lista de livros disponíveis

  Cenário: Alugar dois livros
    Dado que possuo um usuário autorizado e um token válido
    E existem dois livros disponíveis para aluguel
    Quando solicito o aluguel dos dois livros para o usuário
    Então os dois livros devem ser associados ao usuário com sucesso

  Cenário: Consultar usuário e livros alugados
    Dado que possuo um usuário com dois livros alugados
    Quando consulto os detalhes do usuário
    Então os dados retornados devem conter os dois livros alugados