CREATE TABLE hub_metadata (
    uuid uuid primary key,
    title text
);

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO hub_metadata_owner;