
# URL Shortener

Nesse projeto foi criado uma aplicação para encurtar URLs longas em URLs curtas e fáceis de compartilhar. Este serviço oferece a funcionalidade de gerar aliases personalizados, estatísticas de acesso e redirecionamento de URLs.




## Funcionalidades

- Encurtar URLs longas em URLs curtas.
- Personalizar aliases para URLs encurtadas.
- Recuperar a URL encurtada através do Alias.
- Redirecionar automaticamente para a URL original a partir de um alias.
- Retorna as tops 10 url mais acessadas no encurtador.


## Endpoints da API

- PUT /shortener/create: Encurta uma URL longa e opcionalmente permite a definição de um alias personalizado.
- GET /shortener/u/{alias}: Redireciona para a URL original correspondente ao alias fornecido.
- GET /shortener/top10: Retorna as 10 URLs mais acessadas.

## Documentação da API

#### Encurtar a URL

```http
  PUT /shortener/create
```

| Parâmetro   | Tipo       |
| :---------- | :--------- |
| `url` | `string` |  

```
{
   "alias": "XYhakp",
   "url": "http://shortener/u/XYhakp",
   "statistics": {
       "time_taken": "10ms",
   }
}
```

#### Encurtar a URL com um CUSTOM_ALIAS

```http
  PUT http://shortener/create?url=http:/www.lampp-it.com.br&CUSTOM_ALIAS=vale
```

| Parâmetro   | Tipo       | ALIAS     |
| :---------- | :--------- |:--------- |
| `CUSTOM_ALIAS` | `string` |  Exemplo: vale |

```
{
    "alias": "lamppit",
    "url": "http://url-encurtada/u/lamppit",
    "statistics": {
        "time_taken": "35ms"
    }
}
```

#### Caso com CUSTOM_ALIAS já exista

```http
  PUT http://shortener/create?url=http:/www.lampp-it.com.br&CUSTOM_ALIAS=vale
```
 Retorna
 
 ```
{
    "errCode": "001",
    "description": "CUSTOM ALIAS ALREADY EXISTS"
}
```

#### Recuperar a URL

```http
  GET /shortener/u/{alias}
```

| Parâmetro   | Tipo      |
| :---------- | :---------| 
| `alias`      | `string` | 

 ```
{
    "status": "200 OK",
     Redireciona a URL Original
}
```

#### Top 10 URL's mais acessadas

```http
  GET /shortener/top-10
```

*Obs: Não incluí a saída porque ocuparia muito espaço com o retorno das 10 URL's.*





## Instalação
1- Clone este repositório

2- Monte a imagem do conteiner Docker com MySQL
```bash
  cd docker
  docker-compose -f docker-compose.yml up -d
```
2- Configure o banco de dados MySQL editando o arquivo application.properties.
```bash
  application.properties
```
3- Execute a aplicação
    
## Diagrama de Sequencia [UML]

![URL Shortener_page-0001](https://github.com/allyson-sousa/url-hireme/assets/107058501/d50ca5db-fbea-4e62-ab6e-0e79fe80a9d0)


## Stack utilizada

- Java 17
- Arquitetura MVC
- Spring Boot
- Hibernate
- Docker
- MySQL

