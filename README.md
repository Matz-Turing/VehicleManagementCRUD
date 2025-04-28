# Projeto Montadora (Gerenciador de Veículos)

<img src="https://user-images.githubusercontent.com/74038190/212284115-f47cd8ff-2ffb-4b04-b5bf-4d1c14c0247f.gif" width="1000">

## Descrição

Aplicação Java simples para demonstrar operações CRUD (Criar, Ler, Atualizar, Deletar) em um banco de dados MySQL para gerenciamento de veículos (Carros e Motos). O projeto utiliza JDBC para a conectividade com o banco e implementa o padrão de projeto DAO (Data Access Object) para separar a lógica de acesso a dados da lógica de negócio.

## Funcionalidades

O sistema permite gerenciar informações sobre Carros e Motos, incluindo:

**Gerenciamento de Carros:**
*   Inserir novos carros (individualmente ou em lote pré-definido).
*   Selecionar/Buscar carros:
    *   Por ID único.
    *   Todos os carros de uma montadora específica.
    *   (Implicitamente, via DAO) Todos os carros.
*   Alterar dados de carros:
    *   Atualizar todos os dados de um carro buscando por ID.
    *   Atualizar a montadora para todos os carros de uma montadora específica.
    *   (Implicitamente, via DAO) Atualizar montadora e portas buscando por nome.
*   Excluir carros:
    *   Por ID único.
    *   Por nome do carro.
    *   Todos os carros de uma montadora específica.
    *   **Todos** os carros da tabela (com confirmação).

**Gerenciamento de Motos:**
*   Inserir novas motos (individualmente ou em lote pré-definido).
*   Selecionar/Buscar motos:
    *   Por ID único.
    *   Todas as motos de uma montadora específica.
*   Alterar dados de motos:
    *   Atualizar todos os dados de uma moto buscando por ID.
    *   Atualizar a montadora para todas as motos de uma montadora específica.
    *   (Implicitamente, via DAO) Atualizar montadora e quantidade de adesivos buscando por nome/modelo.
*   Excluir motos:
    *   Por ID único.
    *   Por nome/modelo da moto.
    *   Todas as motos de uma montadora específica.
    *   **Todas** as motos da tabela (com confirmação).

**Testes:**
*   Execução de testes interativos via console para cada funcionalidade CRUD principal, permitindo a entrada manual de dados pelo usuário.

## Tecnologias Utilizadas

*   **Java SE 17**
*   **JDBC (Java Database Connectivity)**: Para interação com o banco de dados.
*   **MySQL**: Sistema de Gerenciamento de Banco de Dados Relacional.
*   **Padrão DAO (Data Access Object)**: Para encapsular o acesso aos dados.
*   **Eclipse IDE** (ou outra IDE Java compatível): Ambiente de desenvolvimento.

## Estrutura do Projeto

O código fonte (`src`) está organizado nos seguintes pacotes:

*   `br.com.montadora.conexao`: Contém as classes `Conexao` (para abrir/fechar conexões JDBC) e `ConexaoFactory` (para teste de conexão separado).
*   `br.com.montadora.dao`: Contém as classes `CarroDAO` e `MotoDAO`, responsáveis pelas operações SQL nas tabelas correspondentes.
*   `br.com.montadora.model`: Contém as classes de modelo (POJOs) `Veiculo` (classe base), `Carro` e `Moto`.
*   `br.com.montadora.teste`: Contém classes executáveis (`main`) para testar interativamente as funcionalidades dos DAOs via console (`CarroInserir`, `MotoSelecionar`, etc.).

## Pré-requisitos

*   JDK 17 ou superior instalado.
*   Servidor MySQL instalado e em execução.
*   Uma IDE Java (Eclipse, IntelliJ IDEA, VS Code com extensões Java, etc.).
*   Driver JDBC do MySQL (`mysql-connector-java-*.jar`) adicionado às bibliotecas do projeto na IDE.

## Configuração do Banco de Dados

1.  **Crie o Banco de Dados:**
    Execute o seguinte comando SQL no seu cliente MySQL:
    ```sql
    CREATE DATABASE montadora;
    ```
2.  **Use o Banco de Dados:**
    ```sql
    USE montadora;
    ```
3.  **Crie as Tabelas:**
    Execute os seguintes comandos SQL para criar as tabelas `carro` e `moto`:
    ```sql
    -- Tabela Carro
    CREATE TABLE carro (
        id_carro INT AUTO_INCREMENT PRIMARY KEY,
        montadora VARCHAR(50) NOT NULL,
        nomecarro VARCHAR(100) NOT NULL,
        quantidadeportas INT
    );

    -- Tabela Moto
    CREATE TABLE moto (
        id_moto INT AUTO_INCREMENT PRIMARY KEY,
        montadora VARCHAR(50) NOT NULL,
        nomemoto VARCHAR(100) NOT NULL, -- Atenção: O modelo Java usa 'nomeCarro' herdado, mas o DAO usa 'nomemoto' no SQL.
        quantidadeadesivos INT
    );
    ```
4.  **Verifique as Credenciais de Conexão:**
    Abra o arquivo `src/br/com/montadora/conexao/Conexao.java`. Verifique se a URL (`jdbc:mysql://localhost:3306/montadora`), o usuário (`USER = "root"`) e a senha (`PASS = "senha1234"`) correspondem à sua configuração do MySQL. Ajuste se necessário.

## Como Usar / Executar

1.  Clone ou baixe este repositório.
2.  Importe o projeto na sua IDE Java.
3.  Certifique-se de que o driver JDBC do MySQL está corretamente configurado como uma dependência/biblioteca do projeto.
4.  Para testar as funcionalidades, navegue até o pacote `br.com.montadora.teste` na sua IDE.
5.  Clique com o botão direito em uma das classes de teste (ex: `CarroInserir.java`) e selecione "Run As" > "Java Application".
6.  Siga as instruções que aparecerão no console da IDE para interagir com a aplicação (inserir dados, escolher opções, etc.).
7.  Repita o processo para as outras classes de teste (`CarroAlterar`, `CarroDeletar`, `CarroSelecionar`, `MotoInserir`, `MotoAlterar`, `MotoDeletar`, `MotoSelecionar`).

## Créditos

Desenvolvido por Mateus S.  
GitHub: [Matz-Turing](https://github.com/Matz-Turing)
