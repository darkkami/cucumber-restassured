# language: pt
Funcionalidade: Atualizar um review (Endpoint /api/v1/auth/products/{id}/reviews/{reviewId})

  Cenário: Atualização de review para usuário não autenticado
    Dado Usuário entra na aplicação Multibags
    E usuário que não está autenticado
    Quando o usuário enviar a request
    Então a API deve retornar o HTTP Code 401

  Cenário: Atualização de review para produto inexistente
    Dado Usuário entra na aplicação Multibags
    E usuário está autenticado
      | email    | o181804@g.unicamp.br |
      | password | aA#123456789         |
    E o Product ID informado não existe no sistema
      | customerId  | 1 |
      | productId   | 9999999999999 |
      | reviewId    | 1109 |
    E o review está preenchido com os dados da avaliação
      | date        | 2025-03-13 |
      | description | Teste Atualização de review para produto inexistente |
      | rating      | 1 |
    Quando o usuário enviar a request
    Então a API deve retornar o HTTP Code 404

  Cenário: Atualização de review inexistente
    Dado Usuário entra na aplicação Multibags
    E Um produto com ID existente
      | productId    | 6 |
    E usuário está autenticado
      | email    | o181804@g.unicamp.br |
      | password | aA#123456789         |
    E o <reviewId> informado não existe no sistema
      | customerId  | 1 |
      | reviewId    | 9999999999 |
    E o review está preenchido com os dados da avaliação
      | date        | 2025-03-13 |
      | description | Teste Atualização de review inexistente |
      | rating      | 2 |
    Quando o usuário enviar a request
    Então a API deve retornar o HTTP Code 404

  Cenário: Atualização de review não pertencente ao usuário
    Dado Usuário entra na aplicação Multibags
    E Um produto com ID existente
      | productId    | 6 |
    E usuário está autenticado
      | email    | o181804@g.unicamp.br |
      | password | aA#123456789         |
    E o <reviewId> informado existe no sistema, porém foi criado por outro usuário
      | customerId  | 1 |
      | reviewId    | 1044 |
    E o review está preenchido com os dados da avaliação
      | date        | 2025-03-13 |
      | description | Teste Atualização de review não pertencente ao usuário |
      | rating      | 3 |
    Quando o usuário enviar a request
    Então a API deve retornar o HTTP Code 404

  Esquema do Cenário: Atualização de review com sucesso
    Dado Usuário entra na aplicação Multibags
    E Um produto com ID existente
      | productId    | 6 |
    E usuário está autenticado
      | email    | o181804@g.unicamp.br |
      | password | aA#123456789         |
    E o produto já tem um review criado pelo usuário
      | customerId  | 500 |
      | reviewId    | 1044 |
    E o review está preenchido com os dados da avaliação
      | date        | 2025-03-13 |
      | description | Teste Atualização de review com sucesso |
      | rating      | 4 |
    Quando o usuário enviar a request
    Então a API deve retornar o HTTP Code 200