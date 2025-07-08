#!/bin/bash

# =====================================================
# Sonic Nest API Docker Database Setup Script
# Setup script optimized for Docker environments
# =====================================================

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Docker-specific configuration
DB_HOST="postgres"  # Docker service name
DB_PORT="5432"
DB_NAME="postgres"
DB_USER="postgres"
DB_PASSWORD="password"  # Docker default password
DB_SCHEMA="sonic-nest-api"

# Script directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SQL_SCRIPTS_DIR="$SCRIPT_DIR/scripts"

# Function to print colored output
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to wait for PostgreSQL to be ready
wait_for_postgres() {
    print_info "Waiting for PostgreSQL to be ready..."
    
    local max_attempts=30
    local attempt=1
    
    while [ $attempt -le $max_attempts ]; do
        if pg_isready -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" &> /dev/null; then
            print_success "PostgreSQL is ready!"
            return 0
        fi
        
        print_info "Attempt $attempt/$max_attempts: PostgreSQL not ready yet, waiting..."
        sleep 2
        ((attempt++))
    done
    
    print_error "PostgreSQL did not become ready within $max_attempts attempts"
    return 1
}

# Function to execute SQL script
execute_sql_script() {
    local script_file="$1"
    local description="$2"
    
    print_info "Executing: $description"
    
    if [ ! -f "$script_file" ]; then
        print_error "Script file not found: $script_file"
        return 1
    fi
    
    if PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -f "$script_file"; then
        print_success "$description completed"
        return 0
    else
        print_error "$description failed"
        return 1
    fi
}

# Function to check if setup has already been done
check_existing_setup() {
    print_info "Checking if database setup already exists..."
    
    # Check if schema exists
    local schema_exists=$(PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -t -c "SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name = '$DB_SCHEMA';" | tr -d ' ')
    
    if [ "$schema_exists" = "1" ]; then
        # Check if tables exist
        local table_count=$(PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -t -c "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = '$DB_SCHEMA';" | tr -d ' ')
        
        if [ "$table_count" -gt "0" ]; then
            print_warning "Database setup already exists ($table_count tables found in schema '$DB_SCHEMA')"
            return 0
        fi
    fi
    
    return 1
}

# Function to show usage
show_usage() {
    echo "Usage: $0 [OPTIONS]"
    echo ""
    echo "Options:"
    echo "  --force                 Force setup even if database already exists"
    echo "  --data-only             Only insert sample data"
    echo "  --skip-data             Skip sample data insertion"
    echo "  --wait-time SECONDS     Time to wait for PostgreSQL (default: 60)"
    echo "  --help                  Show this help message"
    echo ""
    echo "This script is designed for Docker environments and uses these defaults:"
    echo "  Host: $DB_HOST"
    echo "  Port: $DB_PORT"
    echo "  Database: $DB_NAME"
    echo "  User: $DB_USER"
    echo "  Password: $DB_PASSWORD"
    echo "  Schema: $DB_SCHEMA"
}

# Parse command line arguments
FORCE_SETUP=false
DATA_ONLY=false
SKIP_DATA=false
MAX_WAIT_TIME=60

while [[ $# -gt 0 ]]; do
    case $1 in
        --force)
            FORCE_SETUP=true
            shift
            ;;
        --data-only)
            DATA_ONLY=true
            shift
            ;;
        --skip-data)
            SKIP_DATA=true
            shift
            ;;
        --wait-time)
            MAX_WAIT_TIME="$2"
            shift 2
            ;;
        --help)
            show_usage
            exit 0
            ;;
        *)
            print_error "Unknown option: $1"
            show_usage
            exit 1
            ;;
    esac
done

# Validate required scripts exist
required_scripts=(
    "$SQL_SCRIPTS_DIR/01-create-database.sql"
    "$SQL_SCRIPTS_DIR/02-create-tables.sql"
    "$SQL_SCRIPTS_DIR/03-seed-data.sql"
    "$SQL_SCRIPTS_DIR/04-setup-permissions.sql"
)

print_info "Validating SQL scripts..."
for script in "${required_scripts[@]}"; do
    if [ ! -f "$script" ]; then
        print_error "Required script not found: $script"
        exit 1
    fi
done
print_success "All required SQL scripts found"

# Display configuration
print_info "=== Sonic Nest API Docker Database Setup ==="
print_info "Host: $DB_HOST"
print_info "Port: $DB_PORT"
print_info "Database: $DB_NAME"
print_info "User: $DB_USER"
print_info "Schema: $DB_SCHEMA"
print_info "Force: $FORCE_SETUP"
print_info "Data Only: $DATA_ONLY"
print_info "Skip Data: $SKIP_DATA"
print_info "============================================="

# Wait for PostgreSQL to be ready
if ! wait_for_postgres; then
    print_error "Failed to connect to PostgreSQL. Is the postgres container running?"
    exit 1
fi

# Check if setup already exists (unless forced or data-only)
if [ "$FORCE_SETUP" = false ] && [ "$DATA_ONLY" = false ]; then
    if check_existing_setup; then
        print_info "Database setup already exists. Use --force to recreate or --data-only to refresh data."
        exit 0
    fi
fi

# Main setup process
if [ "$DATA_ONLY" = true ]; then
    print_info "Running data-only setup for Docker environment..."
    execute_sql_script "$SQL_SCRIPTS_DIR/03-seed-data.sql" "Sample data insertion"
else
    print_info "Starting full database setup for Docker environment..."
    
    # Step 1: Create database and schema
    print_info "Step 1/4: Creating database and schema..."
    execute_sql_script "$SQL_SCRIPTS_DIR/01-create-database.sql" "Database and schema creation"
    
    # Step 2: Create tables
    print_info "Step 2/4: Creating tables..."
    execute_sql_script "$SQL_SCRIPTS_DIR/02-create-tables.sql" "Table creation"
    
    # Step 3: Insert sample data (unless skipped)
    if [ "$SKIP_DATA" = false ]; then
        print_info "Step 3/4: Inserting sample data..."
        execute_sql_script "$SQL_SCRIPTS_DIR/03-seed-data.sql" "Sample data insertion"
    else
        print_warning "Step 3/4: Skipping sample data insertion"
    fi
    
    # Step 4: Setup permissions
    print_info "Step 4/4: Setting up permissions..."
    execute_sql_script "$SQL_SCRIPTS_DIR/04-setup-permissions.sql" "Permissions setup"
fi

print_success "=== Docker database setup completed successfully! ==="
print_info ""
print_info "The database is now ready for the Sonic Nest API application."
print_info ""
print_info "Connection details (already configured in docker-compose.yml):"
print_info "  SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/postgres"
print_info "  SPRING_DATASOURCE_USERNAME=postgres"
print_info "  SPRING_DATASOURCE_PASSWORD=password"
print_info "  SPRING_JPA_PROPERTIES_HIBERNATE_DEFAULT_SCHEMA=sonic-nest-api"
print_info ""
print_info "Alternative application user connection:"
print_info "  SPRING_DATASOURCE_USERNAME=sonic_nest_app"
print_info "  SPRING_DATASOURCE_PASSWORD=sonic_nest_password_2024"
print_info ""
print_info "The Sonic Nest API container should now be able to start successfully!"