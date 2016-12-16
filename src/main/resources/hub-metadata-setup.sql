CREATE USER hub_metadata_owner WITH PASSWORD 'cylonhub';
CREATE DATABASE hub_metadata;
\connect hub_metadata
GRANT ALL PRIVILEGES ON DATABASE hub_metadata TO hub_metadata_owner;