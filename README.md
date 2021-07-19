# estock-wms-api

# **Projeto TCC - API**

- Criar usuário: 

**Tipo:** POST 
https://api-estock.herokuapp.com/api/usuarios

**Body**:

```json
{
    "username": "user",
    "password": 123
}
````

**Autenticação (TOKEN)**

**Tipo:** POST 
https://api-estock.herokuapp.com/api/oauth/token

**Authorization**

1. **User:** my-angular-app
2. **Password:** @321
