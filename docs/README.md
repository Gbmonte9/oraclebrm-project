# Billing System - Projeto Oracle BRM

## Descrição
Este projeto implementa um sistema simples de cobrança usando Java e banco de dados Oracle. Ele cria tabelas, insere dados, consulta e atualiza saldos e transações.

## Estrutura do Projeto

- `/java` - Código fonte Java do sistema
- `/lib` - Bibliotecas externas (ex: `ojdbc17.jar` driver JDBC Oracle)
- `/scripts` - Scripts SQL para criação e inserção manual (opcional)
- `/shell-scripts` - Scripts para automatizar execução e inicialização (Linux/macOS)
- `/docs` - Documentação do projeto (este diretório)

## Requisitos

- JDK 11 ou superior
- Banco de dados Oracle (exemplo: Oracle XE 21c)
- Driver JDBC `ojdbc17.jar` na pasta `/lib`

## Como compilar e executar

1. Compile o código Java:

```bash
javac -cp "lib/ojdbc17.jar" java/brm/BillingSystem.java
```

2. Execute o programa::

```bash
java -cp "lib/ojdbc17.jar;java" brm.BillingSystem
```

## Detalhes do Banco de Dados

- Host: localhost
- Porta: 1521
- Serviço: XEPDB1
- Usuário: system
- Senha: root

## Funcionalidades

- Criação das tabelas accounts e transactions
- Inserção de dados iniciais (conta e transação)
- Consulta dos dados da conta e das transações
- Atualização do saldo da conta

## Scripts

Os scripts SQL para criar tabelas e inserir dados também estão disponíveis na pasta /scripts.

## Automação com Shell Scripts

Na pasta /shell-scripts estão scripts para iniciar o sistema e automatizar transações (requer ambiente Linux/macOS).

## Problemas comuns

- Erro "No suitable driver found": verifique se o driver JDBC está no classpath.
- Erro "table or view does not exist": certifique-se que as tabelas foram criadas.

### Autor

## Gabriel Monte
## Email: gabrielmonte485@gmail.com

