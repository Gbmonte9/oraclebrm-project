#!/bin/bash

USER="system"
PASS="root"
DB="localhost/XEPDB1"

echo "Criando tabelas..."
sqlplus ${USER}/${PASS}@${DB} @../scripts/create_tables.sql

echo "Inserindo dados iniciais..."
sqlplus ${USER}/${PASS}@${DB} @../scripts/insert_data.sql

echo "Atualizando dados..."
sqlplus ${USER}/${PASS}@${DB} @../scripts/update_status.sql

echo "Exibindo consultas..."
sqlplus ${USER}/${PASS}@${DB} @../scripts/query_data.sql

echo "Processo finalizado."