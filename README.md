<div align="center">
  <p><img src=".assets/icon.avif" align="center" width="112"></p>
  <h1><code>MARKETUP</code></h1>
</div>

<table>
  <tbody><tr><td align="center" width="99999"><div>
    <a href="https://olankens.com">WEBSITE</a> ·
    <a href="https://ko-fi.com/olankens">FUNDING</a>
  </div></td></tr></tbody>
  <tbody><tr><td align="center" width="99999">&nbsp;<div>
    Ecommerce platform of Spring Boot microservices, exposing inventory REST APIs for stock checks and full CRUD operations, wired through RabbitMQ, secured by Keycloak, and deployed on Kubernetes clusters.
  </div>&nbsp;</td></tr></tbody>
  <tbody><tr><td align="center" width="99999">
    <a href="https://spring.io"><img src=".assets/spring.svg" align="center" width="56"></a>
    <picture><img src=".assets/divider.gif" align="center" height="40" width="1"/></picture>
    <a href="https://rabbitmq.com"><img src=".assets/rabbitmq.svg" align="center" width="56"></a>
    <picture><img src=".assets/divider.gif" align="center" height="40" width="1"/></picture>
    <a href="https://keycloak.org"><img src=".assets/keycloak.svg" align="center" width="56"></a>
    <picture><img src=".assets/divider.gif" align="center" height="40" width="1"/></picture>
    <a href="https://kubernetes.io/"><img src=".assets/kubernetes.svg" align="center" width="56"></a>
  </td></tr></tbody>
</table>

## PREVIEWS

<table><tbody><tr><td width="99999">
  <img src=".assets/preview-01.avif" align="center" width="49.21875%"><picture><img src=".assets/blank.gif" align="center" width="1.5625%"></picture><img src=".assets/preview-02.avif" align="center" width="49.21875%">
</td></tr></tbody></table>

## LEARNING

### LAUNCH THE CONTAINERS

```sh
docker compose down
docker compose up -d
```

### DEBUG WITH INTELLIJ IDEA

```sh
idea .
```

### USEFUL RESOURCE LINKS

<table>
  <tbody><tr><td width="99999">Keycloak URL</td><td><a href="http://localhost:8080">🌐</a></td></tr></tbody>
</table>

### CREATE MONGODB COLLECTION

```mongodb-json
use product-db
db.createCollection("product")
```