# language: pt
Funcionalidade: Deletar um review

  Esquema do Cenario: Exclusão da review com usuário autenticado (status 200)
    Dado o produto da review possui um ID <id>
    E a review também possui um ID <reviewId> único
    E uma review existente
    E o usuário está autenticado
      | email    | o181804@g.unicamp.br |
      | password | aA#123456789         |
    Quando o usuário solicita a exclusão do review
    Então a API deve deletar essa review do banco de dados
    E retornar a resposta <resposta>
    Exemplos:
      | id | reviewId | resposta |
      | 6  | 1044     | 200      |

  Esquema do Cenario: Exclusão da review com usuário autenticado (status 204)
    Dado o produto da review possui um ID <id>
    E a review também possui um ID <reviewId> único
    E uma review existente
    E o usuário está autenticado
      | email    | o181804@g.unicamp.br |
      | password | aA#123456789         |
    Quando o usuário solicita a exclusão do review
    Então a API deve deletar essa review do banco de dados
    E retornar a resposta <resposta>
    Exemplos:
      | id | reviewId | resposta |
      | 50 | 150      | 204      |

  Esquema do Cenario: Exclusão da review com usuário não autenticado
    Dado o produto da review possui um ID <id>
    E a review também possui um ID <reviewId> único
    E uma review existente
    E o usuário não está autenticado
    Quando o usuário solicita a exclusão do review
    Então retornar a resposta <resposta>
    Exemplos:
      | id | reviewId | resposta |
      | 50 | 150      | 401      |

  Esquema do Cenario: Exclusão da review com usuário autenticado e sem permissão de exclusão
    Dado o produto da review possui um ID <id>
    E a review também possui um ID <reviewId> único
    E uma review existente
    E o usuário está autenticado porém não possui permissão para exclusão
    Quando o usuário solicita a exclusão do review
    Então retornar a resposta <resposta>
    Exemplos:
      | id | reviewId | resposta |
      | 50 | 150      | 403      |