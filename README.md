# 💳 JBank – Sistema de Gerenciamento Bancário

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge\&logo=openjdk\&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge\&logo=spring\&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge\&logo=mysql\&logoColor=white)

**Java Spring Boot + Spring Data JPA + Hibernate Validator**

Bem-vindo ao projeto **JBank**, uma aplicação backend robusta e segura que simula um sistema bancário completo, implementado com boas práticas utilizando **Spring Boot**, **Spring Data JPA** e **Hibernate Validator**. O sistema permite o gerenciamento completo de carteiras bancárias, depósitos, transferências e extratos detalhados, com auditoria e logging de todas as operações.

---

## 🚀 Objetivo do Projeto

Ao finalizar este projeto, você terá:

* Conhecimento prático em transações bancárias com controle ACID.
* Experiência na criação de filtros e interceptores para auditoria e logging.
* Competência na validação rigorosa de regras de negócio.
* Capacidade de criar APIs RESTful seguras e eficientes.

---

## 📌 Regras de Negócio

### 💼 Carteira

* Código UUID
* CPF único
* Email único
* Nome do titular
* Saldo atual

### 💸 Depósito

* Código UUID
* Carteira de destino (Many-to-One)
* Valor depositado
* Data e hora do depósito
* Endereço IP do usuário

### 🔄 Transferência

* Código UUID
* Carteira origem (Many-to-One)
* Carteira destino (Many-to-One)
* Valor transferido
* Data e hora da transferência

---

## 📡 Endpoints REST

### 💼 Criar Carteira

**POST** `/wallets`

```json
{
  "cpf": "26280016323",
  "email": "italo@gmail.com",
  "name": "Italo Miranda"
}
```

### 💰 Depositar Dinheiro

**POST** `/wallets/{wallet_id}/deposits`

```json
{
  "value": 350.0
}
```

### 💳 Realizar Transferência

**POST** `/transfers`

```json
{
  "sender": "03be6c44-492d-4dd8-b7dd-d56de3c81006",
  "value": 100.0,
  "receiver": "57d9b8ab-4053-409d-830e-49f46fca56cd"
}
```

### 📑 Consultar Extrato

**GET** `/wallets/{wallet_id}/statements`

---

## 🛠️ Tecnologias Utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate Validator
* Spring Web

---

## 🧠 Aprendizados

Durante o desenvolvimento deste projeto, você vai adquirir experiência em:

* Implementação de filtros e interceptores para auditoria e logging.
* Gerenciamento seguro de transações bancárias utilizando ACID.
* Validações rigorosas de regras de negócio.
* Exposição eficiente e segura de APIs RESTful.

---

## 📂 Estrutura do Projeto

```
src/
 ├── main/
 │   ├── java/com/jbank/
 │   │   ├── controller/
 │   │   ├── services/
 │   │   ├── repository/
 │   │   ├── entities/
 │   │   ├── interceptors/
 │   │   ├── exception/
 │   │   └── filter/
 │   │
 │   └── resources/
 │       └── application.properties
```

---

## 🤝 Colaboradores

Agradecemos às seguintes pessoas que contribuíram para este projeto:

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/Ital023" title="Github do Ítalo Miranda">
        <img src="https://avatars.githubusercontent.com/u/113559117?v=4" width="100px;" alt="Foto do Ítalo Miranda no GitHub"/><br>
        <sub>
          <b>Ítalo Miranda</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
