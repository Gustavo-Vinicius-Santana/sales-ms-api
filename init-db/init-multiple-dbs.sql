-- cria os bancos
CREATE DATABASE pedidos_db;
CREATE DATABASE vendas_db;

-- cria o usuário (se ainda não existir)
CREATE USER "user" WITH ENCRYPTED PASSWORD 'password';

-- dá permissões nos bancos
GRANT ALL PRIVILEGES ON DATABASE pedidos_db TO "user";
GRANT ALL PRIVILEGES ON DATABASE vendas_db TO "user";

-- dá permissões no schema public de cada banco
\c pedidos_db
GRANT ALL PRIVILEGES ON SCHEMA public TO "user";

\c vendas_db
GRANT ALL PRIVILEGES ON SCHEMA public TO "user";
