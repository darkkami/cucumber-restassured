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
    E o review está preenchido com os dados da avaliação
    Quando o usuário enviar a request
    Então a API deve retornar o HTTP Code 404

  Cenário: Atualização de review inexistente
    Dado Usuário entra na aplicação Multibags
    E Um produto com ID definido
    E usuário está autenticado
      | email    | o181804@g.unicamp.br |
      | password | aA#123456789         |
    E o <reviewId> informado não existe no sistema
    Quando o usuário enviar a request
    Então a API deve retornar o HTTP Code 404

  Cenário: Atualização de review não pertencente ao usuário
    Dado Usuário entra na aplicação Multibags
    E Um produto com ID definido
    E usuário está autenticado
      | email    | o181804@g.unicamp.br |
      | password | aA#123456789         |
    E o <reviewId> informado existe no sistema, porém foi criado por outro usuário
    Quando o usuário enviar a request
    Então a API deve retornar o HTTP Code 403

  Esquema do Cenário: Atualização de review com sucesso
    Dado Usuário entra na aplicação Multibags
    E Um produto com ID definido
    E usuário está autenticado
      | email    | o181804@g.unicamp.br |
      | password | aA#123456789         |
    E o produto já tem um review criado pelo usuário
    Quando os dados do review forem preenchidos corretamente, inserindo a nota no campo “rating” e a descrição do review no campo “description”
    Então a API deve atualizar o review do cliente com os novos dados informados
    E a API deve retornar o HTTP Code 200
    E a API deve retornar o review atualizado
    Exemplos:
      | id          | 10
      | description | “Produto excelente”
      | rating      | 5