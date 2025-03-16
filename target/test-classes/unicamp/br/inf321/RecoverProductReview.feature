# language: pt
Funcionalidade: Recuperar um review

  Esquema do Cenário: Recuperação do review com sucesso
    Dado Usuário entra na aplicação Multibags
    E um produto com ID existe
      | productId    | 10 |
    E o review do produto está disponível
    Quando o usuário solicitar a recuperação dos reviews do produto
    Então a API deve retornar resposta 200
    E retornar o JSON com os detalhes do review, incluindo campos como customerId, date, description, rating
    Exemplos:
      | id | reviewId | customerID | date       | description           | rating | resposta |
      | 10 | 150      | 143        | 15-02-2025 | "product description" | 5      | 200      |

  Esquema do Cenário: Recuperação de review para produto inexistente
    Dado Usuário entra na aplicação Multibags
    E o Product ID informado não existe no sistema
      | customerId  | 1 |
      | productId   | 9999999999999 |
      | reviewId    | 1109 |
    Quando o usuário solicitar a recuperação dos reviews do produto
    Então a API deve retornar resposta 404
    Exemplos:
    | id  | resposta |
    | 999 | 404      |