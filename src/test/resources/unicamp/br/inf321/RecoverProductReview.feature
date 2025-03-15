# language: pt
Funcionalidade: Recuperar um review

  Esquema do Cenário: Recuperação do review com sucesso
    Dado Usuário entra na aplicação Multibags
    E um produto com ID existe
      | productId    | 10 |
    E o review do produto está disponível
    Quando o usuário solicitar a recuperação do review pelo ID <reviewId> do produto
    Então a API deve retornar resposta 200
    E retornar o JSON com os detalhes do review, incluindo campos como customerId, date, description, rating
    Exemplos:
      | id | reviewId | customerID | date       | description           | rating | resposta |
      | 10 | 150      | 143        | 15-02-2025 | "product description" | 5      | 200      |

  Esquema do Cenário: Recuperação de review para produto inexistente
    Dado Usuário entra na aplicação Multibags
    E usuário está autenticado
      | email    | o181804@g.unicamp.br |
      | password | aA#123456789         |
    E o Product ID informado não existe no sistema
      | customerId  | 1 |
      | productId   | 9999999999999 |
      | reviewId    | 1109 |
    Quando o usuário solicitar a recuperação do review pelo ID 999 do produto
    Então a API deve retornar resposta 404
    Exemplos:
    | id  | resposta |
    | 999 | 404      |

  Esquema do Cenário: Recuperação de review sem autorização
    Dado que o usuário não está autenticado
    Quando o usuário solicitar a recuperação do review pelo ID <id> do produto
    Então a API deve retornar resposta 401
    Exemplos:
    | id | resposta |
    | 10 | 401      |

  Esquema do Cenário: Recuperação de review com permissão negada
    Dado Usuário entra na aplicação Multibags
    E o usuário está autenticado, mas não tem permissão para acessar o review
    Quando o usuário solicitar a recuperação do review pelo ID <id> do produto
    Então a API deve retornar resposta 403
    Exemplos:
    | id | resposta |
    | 10 | 403      |