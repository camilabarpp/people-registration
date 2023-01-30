# People Registration API (Desafio Attornatus)

## 📖 1 - Durante a implementação de uma nova funcionalidade de software solicitada, quais critérios você avalia e implementa para garantia de qualidade de software?
```bash

```

API de cadastro de pessoas e endereço em JAVA

[![Build Status](https://app.travis-ci.com/camilabarpp/people-registration.svg?branch=master)](https://app.travis-ci.com/camilabarpp/people-registration)
[![codecov](https://codecov.io/github/camilabarpp/people-registration/branch/master/graph/badge.svg?token=IMSTHUY2IM)](https://codecov.io/github/camilabarpp/people-registration)

# 🚀 Sobre o projeto
É uma api de cadastro de pessoas, onde é possível criar, editar listar e deletar pessoas e endereços. Exemplo do response body abaixo:

```bash
{
    #request body
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
Para o cadastro do endreço é possível passar na requisição somente o número do imóvel, cep e se é o endereço príncipal ou não, no restantes dos dasdos será feitas uma requisição na api de ViaCep e preencerá automáticamente. Exemplo do request body para cadatrar endereço:

```bash
{
    #request body
    "cep": "93212200",
    "number": "1022",
    "mainAddress": true
}
```
# ****ENDPOINTS****

[Pode consultar a documentação Swagger para melher entendimento, clique aqui!](http://localhost:8080/swagger-ui/index.html#/)


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
- Swagger
- Gradle
- H2 Database
- ViaCep API


## Autor

Camila Ramão Barpp


[![Instagram Badge](https://img.shields.io/badge/-instagram-red?style=for-the-badge&logo=instagram&logoColor=white&link=https://github.com/camilabarpp)](https://www.instagram.com/camilabarpp/)
[![Linkedin Badge](https://img.shields.io/badge/-Linkedin-blue?style=for-the-badge&logo=Linkedin&logoColor=white&link=https://github.com/camilabarpp)](https://www.linkedin.com/in/camilabarpp/)
[![Spotify Badge](https://img.shields.io/badge/-Spotify-3bb34b?style=for-the-badge&logo=Spotify&logoColor=161f16&link=https://github.com/camilabarpp)](https://open.spotify.com/user/21o2si6ombl5lygoggs5m6bsy)


