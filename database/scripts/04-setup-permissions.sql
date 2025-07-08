-- =====================================================
-- Sonic Nest API Database Permissions Script
-- Sets up proper user permissions and security
-- =====================================================

-- Set schema
SET search_path TO "sonic-nest-api";

-- =====================================================
-- Create application user (if not exists)
-- =====================================================

-- Create a dedicated application user for better security
-- (This should be run by a superuser or database owner)
DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_user WHERE usename = 'sonic_nest_app') THEN
        CREATE USER sonic_nest_app WITH PASSWORD 'sonic_nest_password_2024';
    END IF;
END
$$;

-- =====================================================
-- Grant Schema Permissions
-- =====================================================

-- Grant usage on schema
GRANT USAGE ON SCHEMA "sonic-nest-api" TO sonic_nest_app;
GRANT USAGE ON SCHEMA "sonic-nest-api" TO postgres;

-- Grant create privileges (for development)
GRANT CREATE ON SCHEMA "sonic-nest-api" TO sonic_nest_app;
GRANT CREATE ON SCHEMA "sonic-nest-api" TO postgres;

-- =====================================================
-- Grant Table Permissions
-- =====================================================

-- Grant all privileges on all tables to application user
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA "sonic-nest-api" TO sonic_nest_app;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA "sonic-nest-api" TO postgres;

-- Grant privileges on future tables
ALTER DEFAULT PRIVILEGES IN SCHEMA "sonic-nest-api" 
    GRANT ALL PRIVILEGES ON TABLES TO sonic_nest_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA "sonic-nest-api" 
    GRANT ALL PRIVILEGES ON TABLES TO postgres;

-- =====================================================
-- Grant Sequence Permissions
-- =====================================================

-- Grant usage and update on all sequences (for SERIAL columns)
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA "sonic-nest-api" TO sonic_nest_app;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA "sonic-nest-api" TO postgres;

-- Grant privileges on future sequences
ALTER DEFAULT PRIVILEGES IN SCHEMA "sonic-nest-api" 
    GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO sonic_nest_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA "sonic-nest-api" 
    GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO postgres;

-- =====================================================
-- Set Default Search Path
-- =====================================================

-- Set default search path for application user
ALTER USER sonic_nest_app SET search_path = "sonic-nest-api";
ALTER USER postgres SET search_path = "sonic-nest-api";

-- =====================================================
-- Security Settings
-- =====================================================

-- Revoke connect privilege from public on the database (optional security measure)
-- REVOKE CONNECT ON DATABASE postgres FROM PUBLIC;

-- Grant connect privilege to specific users
GRANT CONNECT ON DATABASE postgres TO sonic_nest_app;
GRANT CONNECT ON DATABASE postgres TO postgres;

-- =====================================================
-- Read-only user for reporting/analytics (optional)
-- =====================================================

-- Create a read-only user for reporting purposes
DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_user WHERE usename = 'sonic_nest_readonly') THEN
        CREATE USER sonic_nest_readonly WITH PASSWORD 'sonic_nest_readonly_2024';
    END IF;
END
$$;

-- Grant read-only permissions
GRANT USAGE ON SCHEMA "sonic-nest-api" TO sonic_nest_readonly;
GRANT SELECT ON ALL TABLES IN SCHEMA "sonic-nest-api" TO sonic_nest_readonly;
GRANT USAGE ON ALL SEQUENCES IN SCHEMA "sonic-nest-api" TO sonic_nest_readonly;

-- Grant read permissions on future tables
ALTER DEFAULT PRIVILEGES IN SCHEMA "sonic-nest-api" 
    GRANT SELECT ON TABLES TO sonic_nest_readonly;

ALTER DEFAULT PRIVILEGES IN SCHEMA "sonic-nest-api" 
    GRANT USAGE ON SEQUENCES TO sonic_nest_readonly;

-- Set search path for readonly user
ALTER USER sonic_nest_readonly SET search_path = "sonic-nest-api";

-- Grant connect privilege
GRANT CONNECT ON DATABASE postgres TO sonic_nest_readonly;

-- =====================================================
-- Function Permissions (if any custom functions exist)
-- =====================================================

-- Grant execute on all functions
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA "sonic-nest-api" TO sonic_nest_app;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA "sonic-nest-api" TO postgres;

-- Grant execute on future functions
ALTER DEFAULT PRIVILEGES IN SCHEMA "sonic-nest-api" 
    GRANT EXECUTE ON FUNCTIONS TO sonic_nest_app;

-- =====================================================
-- Row Level Security (optional, for multi-tenant scenarios)
-- =====================================================

-- Example: Enable RLS on sensitive tables (uncomment if needed)
-- ALTER TABLE users ENABLE ROW LEVEL SECURITY;
-- ALTER TABLE playlists ENABLE ROW LEVEL SECURITY;
-- ALTER TABLE payments ENABLE ROW LEVEL SECURITY;

-- Example policies (uncomment and modify as needed)
-- CREATE POLICY user_own_data ON users
--     FOR ALL TO sonic_nest_app
--     USING (id = current_setting('app.current_user_id')::bigint);

-- CREATE POLICY playlist_owner ON playlists
--     FOR ALL TO sonic_nest_app
--     USING (user_id = current_setting('app.current_user_id')::bigint);

-- =====================================================
-- Monitoring and Logging
-- =====================================================

-- Create a table for application logs (optional)
CREATE TABLE IF NOT EXISTS application_logs (
    id BIGSERIAL PRIMARY KEY,
    log_level VARCHAR(10) NOT NULL,
    message TEXT NOT NULL,
    user_id BIGINT,
    request_id VARCHAR(255),
    ip_address INET,
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Grant permissions on logs table
GRANT ALL PRIVILEGES ON application_logs TO sonic_nest_app;
GRANT ALL PRIVILEGES ON application_logs TO postgres;
GRANT SELECT ON application_logs TO sonic_nest_readonly;

-- Index for performance
CREATE INDEX IF NOT EXISTS idx_application_logs_created_at ON application_logs(created_at);
CREATE INDEX IF NOT EXISTS idx_application_logs_level ON application_logs(log_level);
CREATE INDEX IF NOT EXISTS idx_application_logs_user_id ON application_logs(user_id);

-- =====================================================
-- Display Permissions Summary
-- =====================================================

-- Show database users
SELECT usename, usecreatedb, usesuper, userepl, usebypassrls 
FROM pg_user 
WHERE usename IN ('postgres', 'sonic_nest_app', 'sonic_nest_readonly')
ORDER BY usename;

-- Show schema privileges
SELECT grantee, privilege_type 
FROM information_schema.usage_privileges 
WHERE object_schema = 'sonic-nest-api' AND object_type = 'SCHEMA'
ORDER BY grantee, privilege_type;

-- Show table count
SELECT COUNT(*) as table_count 
FROM information_schema.tables 
WHERE table_schema = 'sonic-nest-api';

SELECT 'Database permissions configured successfully!' as status;