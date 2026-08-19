# 🚗 nxtDrive

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring_Security-Auth-blue?style=for-the-badge&logo=springsecurity)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

O **nxtDrive** é uma aplicação web desenvolvida em Java com **Spring Boot**, voltada para o gerenciamento e catalogação de veículos/produtos, controle de usuários, painel administrativo (dashboard) e sistema de envio de newsletters por e-mail.

Este projeto foi desenvolvido como parte das atividades acadêmicas do **IFSP (Instituto Federal de Educação, Ciência e Tecnologia de São Paulo)**.

---

## 📌 Sumário

- [Visão Geral](#-visão-geral)
- [Funcionalidades](#-funcionalidades)
- [Arquitetura e Estrutura de Pastas](#-arquitetura-e-estrutura-de-pastas)
- [Tecnologias e Bibliotecas Utilizadas](#-tecnologias-e-bibliotecas-utilizadas)
- [APIs e Serviços Externos](#-apis-e-serviços-externos)
- [Pré-requisitos](#-pré-requisitos)
- [Como Executar o Projeto](#-como-executar-o-projeto)
- [Configuração de E-mail (`application.properties`)](#-configuração-de-e-mail-applicationproperties)
- [Licença](#-licença)

---

## 🔍 Visão Geral

O projeto **nxtDrive** oferece uma solução completa com backend robusto e páginas estáticas/dinâmicas para interação do usuário e administração do sistema. Ele inclui controle detalhado de permissões de acesso (com páginas customizadas de erro, como 403 Forbidden), gestão completa de catálogo de produtos e categorias, além de integração com serviços de e-mail.

---

## ✨ Funcionalidades

- 🔐 **Autenticação e Autorização:**
  - Login e controle de sessão gerenciado pelo **Spring Security**.
  - Perfis de usuário com diferentes níveis de acesso.
  - Serviço customizado de detalhes do usuário (`UsuarioDetailService`).
  - Tratamento visual para acessos negados (`403.css`).

- 📦 **Gestão de Produtos e Categorias (CRUD):**
  - Cadastro, listagem, edição e exclusão de produtos.
  - Organização e vínculo de produtos por categorias.

- 📊 **Painel Administrativo (Dashboard):**
  - Visão geral sobre métricas, produtos e usuários cadastrados.

- ✉️ **Módulo de Newsletter & E-mails:**
  - Inscrição de e-mails em newsletter.
  - Serviço dedicado para envio automático de e-mails (`EmailService`).

---

## 📂 Arquitetura e Estrutura de Pastas

O projeto segue o padrão arquitetural em camadas do Spring Framework (**MVC - Model-View-Controller** + **Service** + **Repository**):

```text
nxtDrive/
├── .mvn/ / mvnw / mvnw.cmd   # Wrapper do Apache Maven
├── pom.xml                     # Gerenciamento de dependências do projeto
└── src/
    └── main/
        ├── java/br/com/ifsp/edu/br/nxtdrive/
        │   ├── config/
        │   │   ├── SecurityConfig.java       # Configurações de segurança e rotas do Spring Security
        │   │   └── UsuarioDetailService.java # Serviço de autenticação de usuários
        │   ├── controller/
        │   │   ├── DashboardController.java  # Controller da dashboard administrativa
        │   │   ├── NewsletterController.java # Controller de inscrições e envios de newsletter
        │   │   ├── ProdutoController.java    # Controller para CRUD de produtos
        │   │   └── UsuarioController.java    # Controller para gestão e registro de usuários
        │   ├── model/
        │   │   ├── Categoria.java            # Entidade de Categoria
        │   │   ├── Produto.java              # Entidade de Produto
        │   │   └── Usuario.java              # Entidade de Usuário
        │   ├── repository/
        │   │   ├── CategoriaRepository.java  # Interface JPA para Categoria
        │   │   ├── ProdutoRepository.java    # Interface JPA para Produto
        │   │   └── UsuarioRepository.java    # Interface JPA para Usuário
        │   ├── service/
        │   │   ├── CategoriaService.java     # Regras de negócio de Categorias
        │   │   ├── EmailService.java         # Regras e integração de envio de e-mails
        │   │   └── ProdutoService.java       # Regras de negócio de Produtos
        │   └── NxtdriveApplication.java      # Classe principal de inicialização da aplicação
        └── resources/
            ├── application.properties        # Arquivo de configuração (BD, Mail, Server)
            └── static/                       # Recursos estáticos (CSS, Imagens, Logos)
                ├── 403.css
                └── img/                      # Imagens do sistema
```

---

## 🛠️ Tecnologias e Bibliotecas Utilizadas

| Tecnologia / Biblioteca | Função / Descrição |
| :--- | :--- |
| **Java 17+** | Linguagem de programação principal do projeto. |
| **Spring Boot** | Framework base para criação da aplicação web independente. |
| **Spring Security** | Segurança, autenticação de usuários e controle de rotas HTTP. |
| **Spring Data JPA / Hibernate** | Abstração da camada de banco de dados e Mapeamento Objeto-Relacional (ORM). |
| **Spring Boot Starter Mail / JavaMailSender** | Biblioteca para integração e envio de e-mails via SMTP. |
| **Spring Web (MVC)** | Construção da API REST e Controllers da aplicação. |
| **Thymeleaf / HTML5 / CSS3** | Renderização das telas e estilização do front-end. |
| **Apache Maven** | Ferramenta de gerenciamento de dependências e automação de build. |

---

## 🌐 APIs e Serviços Externos

- **Serviço SMTP de E-mail (JavaMailSender API):**
  - Utilizado pelo `EmailService.java` para o envio de e-mails de confirmação e disparo de comunicações para os inscritos da newsletter.

---

## 📋 Pré-requisitos

Antes de iniciar, certifique-se de ter instalado em sua máquina:
- [Java JDK 17](https://www.oracle.com/java/technologies/downloads/) ou superior.
- [Git](https://git-scm.com/).
- Banco de dados de sua preferência (MySQL, PostgreSQL ou H2) configurado no `application.properties`.

---

## 🚀 Como Executar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/nxtDrive.git
   cd nxtDrive/nxtDrive
   ```

2. **Configure o arquivo `application.properties`:**
   Ajuste as credenciais do banco de dados e do servidor de e-mail em `src/main/resources/application.properties`.

3. **Execute a aplicação usando o Maven Wrapper:**
   - No Linux/macOS:
     ```bash
     ./mvnw spring-boot:run
     ```
   - No Windows:
     ```cmd
     mvnw.cmd spring-boot:run
     ```

4. **Acesse a aplicação:**
   Abra o seu navegador e acesse `http://localhost:8080`.

---

## ⚙️ Configuração de E-mail (`application.properties`)

Para habilitar o funcionamento correto do `EmailService`, configure as propriedades de e-mail no arquivo `application.properties`:

```properties
# Configuração do Servidor SMTP (Exemplo: Gmail)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

## 📜 Licença

Este projeto é de uso acadêmico e educacional desenvolvido no âmbito do **IFSP**. Sinta-se livre para estudar, modificar e aprimorar o código.
