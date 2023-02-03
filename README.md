# People Registration API (Desafio Attornatus)

[Link do build do projeto](https://app.travis-ci.com/github/camilabarpp/people-registration)

[![Build Status](https://app.travis-ci.com/camilabarpp/people-registration.svg?branch=master)](https://app.travis-ci.com/camilabarpp/people-registration)
[![codecov](https://codecov.io/github/camilabarpp/people-registration/branch/master/graph/badge.svg?token=IMSTHUY2IM)](https://codecov.io/github/camilabarpp/people-registration)
[![Documentation](https://img.shields.io/badge/Documentation-yes-pink.svg)](http://localhost:8080/swagger-ui/index.html#/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5.6-blue.svg)](https://spring.io/projects/spring-boot)
[![JUnit](https://img.shields.io/badge/JUnit-5.9.0-green.svg)](https://junit.org/junit5/)
[![Gradle](https://img.shields.io/badge/Gradle-7.6-red.svg)](https://gradle.org/)


## Durante a implementação de uma nova funcionalidade de software solicitada, quais critérios você avalia e implementa para garantia de qualidade de software?
```bash
Avaliação de critérios de qualidade de software incluem:
    - Planejamento: definição dos requisitos de qualidade e definição de uma estratégia
      de garantia de qualidade.
    - Análise e design: identificação de problemas potenciais de qualidade e implementação
      de soluções.
    - Teste: verificação da qualidade do software por meio de testes unitários, 
      de integração, 
      de sistema e de aceitação.

```

## Em qual etapa da implementação você considera a qualidade de software?
```bash
A qualidade de software é considerada em todas as etapas do ciclo de vida de desenvolvimento
de software, incluindo:
    - Documentação: qualidade e clareza da documentação do software.
    - Manutenibilidade: capacidade de modificar o software sem impactar outras partes.
    - Testabilidade: facilidade de testar o software para identificar falhas.
```

# Para executar o projeto

Git clone no repositório people-registration
```bash 
https://github.com/camilabarpp/people-registration
```

Após aberto o projeto na sua ide, rode este comando no terminal:
```bash
gradle build
```


# 🚀 Sobre o projeto
É uma api de cadastro de pessoas, onde é possível criar, editar listar e deletar pessoas e endereços. Exemplo do request body abaixo:

```bash
{
    #request body
    "name": "Fernando Lima",
    "birthdate": "02/01/2000",
    "addresses": [
        {
            "cep": "93010-003",
            "number": "670",
            "mainAddress": true,
        }
    ]
}
```
Retornará um response body já com as propriedades do endereço apartir do CEP, se válido irá retornar logradouro, bairro, cidade e UF da pessoa. Exemplo abaixo:

```bash
{
    #response body
    "id": 1,
    "name": "Fernando Lima",
    "birthdate": "02/01/2000",
    "addresses": [
        {
            "id": 1,
            "cep": "93010-003",
            "number": "670",
            "logradouro": "Rua Independência",
            "bairro": "Centro",
            "localidade": "São Leopoldo"
            "uf": "RS",
            "mainAddress": true,
        }
    ]
}
```
Para o cadastro do endereço é possível passar na requisição somente o número do imóvel, cep e se é o endereço príncipal ou não, no restantes dos dasdos será feitas uma requisição na api de ViaCep e preencerá automáticamente. Exemplo do request body para cadatrar endereço:

```bash
{
    #request body
    "cep": "93212200",
    "number": "1022",
    "mainAddress": true
}
```
# ****ENDPOINTS****

[Pode consultar a documentação Swagger para melhor entendimento, clique aqui!](http://localhost:8080/swagger-ui/index.html#/)


List all people (GET), Delete all people (DELETE), Create a person (POST)
```bash 
http://localhost:8080/v1/person/
```
List a person by ID (GET), Delete a person by ID (DELETE), Update a person by ID (PUT)
```bash 
http://localhost:8080/v1/person/
```
Get all person addresses by ID (GET), Create a new person address (POST)
```bash 
http://localhost:8080/v1/person/1/address
```
Delete a person address by ID (DELETE), Update a address person by ID (PUT)
```bash 
http://localhost:8080/v1/person/1/address/2
```
# Tecnologias utilizadas
## Back end
- Java 17
- Spring Boot
- Junit e Mockito
- Jacoco
- Codecov
- Travis
- Swagger
- Gradle
- H2 Database
- ViaCep API

Aplicação em H2
[H2 Database](http://localhost:8080/h2-console/)

## Autor

Camila Ramão Barpp


[![Instagram Badge](https://img.shields.io/badge/-instagram-red?style=for-the-badge&logo=instagram&logoColor=white&link=https://github.com/camilabarpp)](https://www.instagram.com/camilabarpp/)
[![Linkedin Badge](https://img.shields.io/badge/-Linkedin-blue?style=for-the-badge&logo=Linkedin&logoColor=white&link=https://github.com/camilabarpp)](https://www.linkedin.com/in/camilabarpp/)
[![Spotify Badge](https://img.shields.io/badge/-Spotify-3bb34b?style=for-the-badge&logo=Spotify&logoColor=161f16&link=https://github.com/camilabarpp)](https://open.spotify.com/user/21o2si6ombl5lygoggs5m6bsy)




Outros repositórios

[![DoctorCare](https://img.shields.io/badge/DoctorCare-darkgreen.svg)](https://camilabarpp.github.io/DoctorCare/)
[![Pokedex](https://img.shields.io/badge/Pokedex-darkblue.svg)](https://camilabarpp.github.io/Pokedex/)


