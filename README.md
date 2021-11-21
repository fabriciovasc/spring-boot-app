[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/fabsvas/fatec-uol-backend)

# fatec-uol-backend
Repositório de desenvolvimento do back-end do Aprendizado por Projeto Integrador (API) da FATEC São José dos Campos.

## :clipboard: Requisitos
Você precisará iniciar este projeto através da plataforma do GitPod.

## :gear: Configuração
Rodar projeto
```
mvn spring-boot:run
```
Rodar testes
```
mvn test
```
## :pushpin: Informações da API
Nesta seção você terá acesso as informações para requisições da API.

```
<base_url>: Significa a URL gerada pelo workspace do GitPod.
Exemplo: https://8080-blush-mandrill-7o4f6lia.ws-us18.gitpod.io
```

Root
```
GET: <base_url>/api
RESPONSE: Bem vindo a API do BOL.com.br!
```

### Registration
Exemplo de corpo da requisição (POST, PUT)
```
{
  "email": "<email>",
  "password": "<password>",
  "name": "<name>",
  "cellphone": "<celular>",
  "audioHash": "<hashqualquer>",
  "webGLHash": "<hashqualquer>",
  "canvasHash": "<hashqualquer>"
}
```

GET
```
<base_url>/api/registration
```

GET
```
<base_url>/api/registration/:id
```

POST
```
<base_url>/api/registration
```

PUT
```
<base_url>/api/registration/:id
```

DELETE
```
<base_url>/api/registration/:id
```

Profile

GET
```
<base_url>/api/profile
```

GET
```
<base_url>/api/profile/:id
```

DELETE
```
<base_url>/api/profile/:id
```
