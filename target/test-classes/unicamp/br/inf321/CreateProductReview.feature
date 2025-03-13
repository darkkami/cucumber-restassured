#language: pt
Funcionalidade: Criar um novo review

  Cenário: Inserção de um novo review, com usuário autenticado
    Dado o usuário está autenticado
      | email    | ex188991@g.unicamp.br |
      | password | Teste123@         |
    Quando o usuário informar um comentário e uma nota
      | id | 5 |
      | description | Produto excelente |
      | rating |  5 |
      | date | 2025-02-22 |
      | language | pt |
      | resposta | 201 |
    Então a API deve registrar esse review na base de dados


  Cenário: Inserção de um novo review, sem usuário autenticado
    Dado o usuário não está autenticado
      | email    | ex188991@g.unicamp.br |
      | password | Teste123@         |
    Quando o usuário informar um comentário e uma nota
      | id | 5 |
      | description | Produto excelente |
      | rating |  5 |
      | date | 2025-02-22 |
      | language | pt |
    Então a API deve retornar a resposta 401


  Cenário: Inserção de um novo review, com usuário autenticado, sem permissão
    Dado o usuário está autenticado
      | email    | ex188991@g.unicamp.br |
      | password | Teste123@         |
    E o usuário não tem permissão para registrar um review
    Quando o usuário informar um comentário e uma nota
      | id | 6 |
      | description | Produto excelente |
      | rating |  5 |
      | date | 2025-02-22 |
      | language | pt |
    Então a API deve retornar a resposta 403
