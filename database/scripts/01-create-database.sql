-- =====================================================
-- Sonic Nest API Database Setup Script
-- Creates PostgreSQL database and schema
-- =====================================================

-- Create database (if running as superuser)
-- This step may need to be run manually with appropriate privileges
-- SELECT 'CREATE DATABASE postgres' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'postgres')\gexec

-- Connect to the database (assumes database 'postgres' exists)
\c postgres;

-- Create schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS "sonic-nest-api";

-- Set search path for current session
SET search_path TO "sonic-nest-api";

-- Grant usage on schema to postgres user
GRANT USAGE ON SCHEMA "sonic-nest-api" TO postgres;
GRANT CREATE ON SCHEMA "sonic-nest-api" TO postgres;

-- Set default search path for postgres user
ALTER USER postgres SET search_path = "sonic-nest-api";

-- Show current schema
SELECT current_schema();

-- Verify schema creation
SELECT schema_name 
FROM information_schema.schemata 
WHERE schema_name = 'sonic-nest-api';

COMMENT ON SCHEMA "sonic-nest-api" IS 'Sonic Nest music streaming API database schema';