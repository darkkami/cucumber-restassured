# language: pt
Funcionalidade: Deletar um review

  Esquema do Cenario: Exclusão da review com usuário autenticado (status 200)
    Dado uma review existente
    E o produto da review possui um ID <id>
    E a review também possui um ID <reviewId> único
    E o usuário está autenticado
    Quando o usuário solicitar a exclusão da review por meio da reviewId <reviewId> e ID <id> do produto
    Então a API deve deletar essa review do banco de dados
    E retornar a resposta <resposta>
    Exemplos:
      | id | reviewId | resposta |
      | 50 | 150      | 200      |

  Esquema do Cenario: Exclusão da review com usuário autenticado (status 204)
    Dado uma review existente
    E o produto da review possui um ID <id>
    E a review também possui um ID <reviewId> único
    E o usuário está autenticado
    Quando o usuário solicitar a exclusão da review por meio da reviewId <reviewId> e ID <id> do produto
    Então a API deve deletar essa review do banco de dados
    E retornar a resposta <resposta>
    Exemplos:
      | id | reviewId | resposta |
      | 50 | 150      | 204      |

  Esquema do Cenario: Exclusão da review com usuário não autenticado
    Dado uma review existente
    E o produto da review possui um ID <id>
    E a review também possui um ID <reviewId> único
    E o usuário não está autenticado
    Quando o usuário solicitar a exclusão da review por meio da reviewId <reviewId> e ID <id> do produto
    Então a API vai ignorar a solicitação de exclusão
    E retornar a resposta <resposta>
    Exemplos:
      | id | reviewId | resposta |
      | 50 | 150      | 401      |

  Esquema do Cenario: Exclusão da review com usuário autenticado e sem permissão de exclusão
    Dado uma review existente
    E o produto da review possui um ID <id>
    E a review também possui um ID <reviewId> único
    E o usuário está autenticado porém não possui permissão para exclusão
    Quando o usuário solicitar a exclusão da review por meio da reviewId <reviewId> e ID <id> do produto
    Então a API vai ignorar a solicitação de exclusão
    E retornar a resposta <resposta>
    Exemplos:
      | id | reviewId | resposta |
      | 50 | 150      | 403      |