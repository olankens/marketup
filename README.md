<div align="center">
  <p><img src=".assets/icon.avif" align="center" width="128"></p>
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
    <a href="https://spring.io"><img src=".assets/logo-spring.svg" align="center" width="56"></a>
    <picture><img src=".assets/splitter.gif" align="center" height="40" width="1"/></picture>
    <a href="https://rabbitmq.com"><img src=".assets/logo-rabbitmq.svg" align="center" width="56"></a>
    <picture><img src=".assets/splitter.gif" align="center" height="40" width="1"/></picture>
    <a href="https://docker.com"><img src=".assets/logo-docker.svg" align="center" width="56"></a>
    <picture><img src=".assets/splitter.gif" align="center" height="40" width="1"/></picture>
    <a href="https://keycloak.org"><img src=".assets/logo-keycloak.svg" align="center" width="56"></a>
    <picture><img src=".assets/splitter.gif" align="center" height="40" width="1"/></picture>
    <a href="https://kubernetes.io/"><img src=".assets/logo-kubernetes.svg" align="center" width="56"></a>
  </td></tr></tbody>
</table>

## PREVIEWS

<table><tbody><tr><td width="99999">
  <img src=".assets/preview-01.avif" align="center" width="49.21875%"><picture><img src=".assets/spacer.gif" align="center" width="1.5625%"></picture><img src=".assets/preview-02.avif" align="center" width="49.21875%">
</td></tr></tbody></table>

## FEATURES

<table>
  <tbody><tr><td width="99999">Manage the product catalog with full create, read, update and delete operations backed by MongoDB for scalable ecommerce data storage and fast data retrieval across every single microservice.</td><td>✅</td></tr></tbody>
  <tbody><tr><td>Track inventory stock levels with real time availability checks and quantity reduction endpoints powered by MySQL for reliable warehouse management and precise stock control operations.</td><td>✅</td></tr></tbody>
  <tbody><tr><td>Process customer orders with JWT authentication and role based access control so only authorized users can place new orders and view their complete purchase history securely online every day.</td><td>✅</td></tr></tbody>
  <tbody><tr><td>Deliver asynchronous order confirmation notifications through RabbitMQ message broker to keep customers informed about their latest purchase status and upcoming delivery updates promptly.</td><td>✅</td></tr></tbody>
  <tbody><tr><td>Protect all service endpoints with Keycloak based OAuth2 authentication and fine grained role based authorization for both admin and regular users across the entire ecommerce platform.</td><td>✅</td></tr></tbody>
  <tbody><tr><td>Route all incoming requests through a centralized Spring Cloud Gateway that enforces security policies and directs traffic to the correct downstream microservice instance automatically.</td><td>✅</td></tr></tbody>
  <tbody><tr><td>Implement circuit breaker patterns with automatic retry logic to maintain system resilience when downstream inventory services experience temporary failures or unexpected network issues.</td><td>✅</td></tr></tbody>
  <tbody><tr><td>Deploy polyglot persistence architecture using MongoDB for products, MySQL for inventory and PostgreSQL for orders to optimize each data access pattern independently and efficiently at scale.</td><td>✅</td></tr></tbody>
</table>

## LEARNING

### USEFUL RESOURCE LINKS

<table>
  <tbody><tr><td width="99999">Keycloak Administration UI</td><td><a href="http://localhost:8080">🌐</a></td></tr></tbody>
  <tbody><tr><td>RabbitMQ Management</td><td><a href="http://localhost:15672">🌐</a></td></tr></tbody>
</table>

### LAUNCH IN INTELLIJ IDEA

```sh
idea .
```

### INVOKE THE CONTAINERS

```shell
docker compose down
docker compose up
```
