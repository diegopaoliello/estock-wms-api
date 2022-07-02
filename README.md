# estock-wms-api

# **Projeto TCC - API**

- Criar usuário: 

**Tipo:** POST 
https://api-estock.herokuapp.com/api/usuarios

**Body**: JSON

```json
{
    "username": "user@estock.com",
    "password": 123
}
````

**Autenticação (TOKEN)**

**Tipo:** POST 
https://api-estock.herokuapp.com/api/oauth/token

**Authorization**

1. **User:** estock-app
2. **Password:** #estock2022

**Body**: x-www-form-urlencoded

1. **username:** user
2. **password:** 123
3. **grant_type:** password

**Coleção de URLs da API (Postman)**

Coleção de URLs da API. O arquivo [eStock.postman_collection.json](https://github.com/diegopaoliello/estock-wms-api/blob/main/src/main/resources/eStock.postman_collection.json) deve ser importado pelo [Postman](https://www.postman.com/downloads/).
