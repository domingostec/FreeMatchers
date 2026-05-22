# 🎯 FreeMatchers

> Um ecossistema inteligente para conexão assertiva entre profissionais e recrutadores através de algoritmos de compatibilidade técnica.


| ☕ Linguagem | 🍃 Framework | 📦 Build | 🛠️ Status |
| :---: | :---: | :---: | :---: |
| **Java 17+** | **Spring Boot 3.x** | **Maven** | **Em Desenvolvimento** |

---

---

## 📌 Índice
* [💡 Sobre o Projeto](#-sobre-o-projeto)
* [🚀 Funcionalidades](#-funcionalidades)
* [🛠️ Tecnologias Utilizadas](#️-tecnologias-utilizadas)
* [🛣️ Rotas da API (Endpoints)](#️-rotas-da-api-endpoints)
* [💻 Como Executar o Projeto](#-como-executar-o-projeto)
* [🤝 Contribuindo](#-contribuindo)

---

## 💡 Sobre o Projeto
O **FreeMatchers** nasceu com o objetivo de otimizar a conexão entre profissionais e recrutadores, eliminando ruídos em processos seletivos para vagas de emprego, projetos pessoais e freelas. Como estudante e entusiasta do setor, acredito que o mercado exige um foco muito maior na qualidade das relações e interações iniciais entre quem contrata e quem busca uma oportunidade.

Para solucionar esse problema, o sistema utiliza um **algoritmo inteligente que analisa as habilidades técnicas do profissional e as confronta com os requisitos exigidos pelo recrutador**. A partir desse cruzamento, a plataforma gera um ranking automatizado baseado na **porcentagem (%) exata de compatibilidade (match)**. 

> 🚀 *Foco Inicial:* No momento, a plataforma atende exclusivamente a área de Tecnologia da Informação e afins, mas a arquitetura do projeto está sendo desenhada para escalar e suportar qualquer profissão de qualquer setor no futuro.
---

## 🚀 Funcionalidades
Aqui está o mapa de desenvolvimento das funcionalidades do sistema:

- [x] Estruturação inicial do projeto e configuração do Git.
- [x] Lógica de Serviço completa
- [ ] Testes Unitarios com JUnit & Mockito
- [ ] Cadastro e autenticação de usuários (JWT).
- [ ] Sistema de filtros inteligentes (Match de habilidades).
- [ ] Painel de gerenciamento de propostas.
- [ ] Sistema de notificações em tempo real.

---

## 🛠️ Tecnologias Utilizadas

<details>
<summary><b>📐 Arquitetura & Backend (Clique para expandir)</b></summary>

* **Linguagem Principal:** Java 17
* **Framework:** Spring Boot 3
* **Persistência de Dados:** Spring Data JPA
* **Gerenciador de Dependências:** Maven
</details>

<details>
<summary><b>🛢️ Banco de Dados & Infraestrutura (Clique para expandir)</b></summary>

* **Banco de Dados:** [Ex: PostgreSQL / MySQL / H2]
* **Migrações:** [Ex: Flyway / Liquibase / Nenhuma]
</details>

---

## 🛣️ Rotas da API (Endpoints)


| Método | Endpoint | Descrição | Autenticação |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/matchers` | Lista todos os matches disponíveis | 🔓 Pública |
| `POST` | `/api/v1/users/register` | Cria uma nova conta no sistema | 🔓 Pública |
| `POST` | `/api/v1/jobs` | Publica uma nova vaga de freela | 🔓 Publica por enquanto |

---

## 💻 Como Executar o Projeto

### Pré-requisitos
Antes de começar, você vai precisar ter instalado em sua máquina:
* **Java JDK 17** ou superior
* **Maven** (opcional, se usar o `mvnw`)
* Uma IDE como **IntelliJ IDEA** ou Eclipse

### 🏃‍♂️ Passo a passo

```bash
# 1. Clone este repositório
\$ git clone https://github.com

# 2. Acesse a pasta do projeto
\$ cd FreeMatchers

# 3. Compile e execute a aplicação
\$ ./mvnw spring-boot:run
```
A API estará disponível no endereço local `http://localhost:8080`.

---

## 🤝 Contribuindo

Contribuições deixam a comunidade open source um lugar incrível para aprender, inspirar e criar. Qualquer contribuição que você fizer será **muito apreciada**.

1. Faça um **Fork** do projeto.
2. Crie uma **Branch** para sua funcionalidade (`git checkout -b feature/NovaFuncionalidade`).
3. Faça o **Commit** de suas alterações (`git commit -m 'feat: adiciona nova funcionalidade'`).
4. Envie para a Branch original (`git push origin feature/NovaFuncionalidade`).
5. Abra um **Pull Request**.

---

<p align="center">
  Desenvolvido por <b>Domingos Tec</b> ✨<br>
  Me encontre no <a href="https://linkedin.com[seu-linkedin]">LinkedIn</a>
</p>
