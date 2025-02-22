# language: pt
Funcionalidade: Atualizar um review (Endpoint /api/v1/auth/products/{id}/reviews/{reviewId})

  Cenário: Atualização de review para usuário não autenticado
    Dado Usuário entra na aplicação Multibags
    E usuário que não está autenticado
    Quando o usuário enviar a request
    Então a API deve retornar o HTTP Code 401

  Cenário: Atualização de review para produto inexistente
    Dado Usuário entra na aplicação Multibags
    E Um produto com ID definido
    E usuário está autenticado
    E o Product ID informado não existe no sistema
    Quando o usuário enviar a request
    Então a API deve retornar o HTTP Code 404

  Cenário: Atualização de review inexistente
    Dado Usuário entra na aplicação Multibags
    E Um produto com ID definido
    E usuário está autenticado
    E o <reviewId> informado não existe no sistema
    Quando o usuário enviar a request
    Então a API deve retornar o HTTP Code 404

  Cenário: Atualização de review não pertencente ao usuário
    Dado Usuário entra na aplicação Multibags
    E Um produto com ID definido
    E usuário está autenticado
    E o <reviewId> informado existe no sistema, porém foi criado por outro usuário
    Quando o usuário enviar a request
    Então a API deve retornar o HTTP Code 403

  Esquema do Cenário: Atualização de review com sucesso
    Dado Usuário entra na aplicação Multibags
    E Um produto com ID definido
    E usuário está autenticado
    E o produto já tem um review criado pelo usuário
    Quando os dados do review forem preenchidos corretamente, inserindo a nota no campo “rating” e a descrição do review no campo “description”
    Então a API deve atualizar o review do cliente com os novos dados informados
    E a API deve retornar o HTTP Code 200
    Exemplos:
      | id | description         | rating
      | 10 | “Produto excelente” | 5