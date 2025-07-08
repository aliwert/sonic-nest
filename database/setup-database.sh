#!/bin/bash

# =====================================================
# Sonic Nest API Database Setup Script
# Comprehensive PostgreSQL database setup for local development
# =====================================================

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
DEFAULT_DB_HOST="localhost"
DEFAULT_DB_PORT="5432"
DEFAULT_DB_NAME="postgres"
DEFAULT_DB_USER="postgres"
DEFAULT_DB_SCHEMA="sonic-nest-api"

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

# Function to check if PostgreSQL is running
check_postgres() {
    print_info "Checking PostgreSQL connection..."
    
    if ! command -v psql &> /dev/null; then
        print_error "psql command not found. Please install PostgreSQL client tools."
        exit 1
    fi
    
    if ! pg_isready -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" &> /dev/null; then
        print_error "Cannot connect to PostgreSQL at $DB_HOST:$DB_PORT"
        print_info "Please ensure PostgreSQL is running and accessible."
        exit 1
    fi
    
    print_success "PostgreSQL is running and accessible"
}

# Function to execute SQL script
execute_sql_script() {
    local script_file="$1"
    local description="$2"
    
    print_info "Executing: $description"
    
    if [ ! -f "$script_file" ]; then
        print_error "Script file not found: $script_file"
        exit 1
    fi
    
    if PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -f "$script_file" > /dev/null 2>&1; then
        print_success "$description completed"
    else
        print_error "$description failed"
        print_info "You can run the script manually with:"
        print_info "PGPASSWORD='$DB_PASSWORD' psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f $script_file"
        exit 1
    fi
}

# Function to execute SQL script with output
execute_sql_script_with_output() {
    local script_file="$1"
    local description="$2"
    
    print_info "Executing: $description"
    
    if [ ! -f "$script_file" ]; then
        print_error "Script file not found: $script_file"
        exit 1
    fi
    
    PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -f "$script_file"
    
    if [ $? -eq 0 ]; then
        print_success "$description completed"
    else
        print_error "$description failed"
        exit 1
    fi
}

# Function to show usage
show_usage() {
    echo "Usage: $0 [OPTIONS]"
    echo ""
    echo "Options:"
    echo "  -h, --host HOST         Database host (default: $DEFAULT_DB_HOST)"
    echo "  -p, --port PORT         Database port (default: $DEFAULT_DB_PORT)"
    echo "  -d, --database DB       Database name (default: $DEFAULT_DB_NAME)"
    echo "  -u, --user USER         Database user (default: $DEFAULT_DB_USER)"
    echo "  -w, --password PASS     Database password (will prompt if not provided)"
    echo "  --schema SCHEMA         Database schema (default: $DEFAULT_DB_SCHEMA)"
    echo "  --skip-create           Skip database/schema creation"
    echo "  --skip-tables           Skip table creation"
    echo "  --skip-data             Skip sample data insertion"
    echo "  --skip-permissions      Skip permissions setup"
    echo "  --data-only             Only insert sample data (skip everything else)"
    echo "  --clean                 Clean existing data before inserting new data"
    echo "  --help                  Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0                                          # Interactive setup with defaults"
    echo "  $0 -h localhost -u postgres -w mypassword  # Setup with specific connection"
    echo "  $0 --data-only --clean                     # Only refresh sample data"
    echo "  $0 --skip-data                             # Setup without sample data"
}

# Parse command line arguments
DB_HOST="$DEFAULT_DB_HOST"
DB_PORT="$DEFAULT_DB_PORT"
DB_NAME="$DEFAULT_DB_NAME"
DB_USER="$DEFAULT_DB_USER"
DB_PASSWORD=""
DB_SCHEMA="$DEFAULT_DB_SCHEMA"
SKIP_CREATE=false
SKIP_TABLES=false
SKIP_DATA=false
SKIP_PERMISSIONS=false
DATA_ONLY=false
CLEAN_DATA=false

while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--host)
            DB_HOST="$2"
            shift 2
            ;;
        -p|--port)
            DB_PORT="$2"
            shift 2
            ;;
        -d|--database)
            DB_NAME="$2"
            shift 2
            ;;
        -u|--user)
            DB_USER="$2"
            shift 2
            ;;
        -w|--password)
            DB_PASSWORD="$2"
            shift 2
            ;;
        --schema)
            DB_SCHEMA="$2"
            shift 2
            ;;
        --skip-create)
            SKIP_CREATE=true
            shift
            ;;
        --skip-tables)
            SKIP_TABLES=true
            shift
            ;;
        --skip-data)
            SKIP_DATA=true
            shift
            ;;
        --skip-permissions)
            SKIP_PERMISSIONS=true
            shift
            ;;
        --data-only)
            DATA_ONLY=true
            shift
            ;;
        --clean)
            CLEAN_DATA=true
            shift
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

# Prompt for password if not provided
if [ -z "$DB_PASSWORD" ]; then
    echo -n "Enter database password for user '$DB_USER': "
    read -s DB_PASSWORD
    echo
fi

# Validate required scripts exist
required_scripts=(
    "$SQL_SCRIPTS_DIR/01-create-database.sql"
    "$SQL_SCRIPTS_DIR/02-create-tables.sql"
    "$SQL_SCRIPTS_DIR/03-seed-data.sql"
    "$SQL_SCRIPTS_DIR/04-setup-permissions.sql"
)

for script in "${required_scripts[@]}"; do
    if [ ! -f "$script" ]; then
        print_error "Required script not found: $script"
        exit 1
    fi
done

# Display configuration
print_info "=== Sonic Nest API Database Setup ==="
print_info "Host: $DB_HOST"
print_info "Port: $DB_PORT"
print_info "Database: $DB_NAME"
print_info "User: $DB_USER"
print_info "Schema: $DB_SCHEMA"
print_info "======================================"

# Check PostgreSQL connection
check_postgres

# Main setup process
if [ "$DATA_ONLY" = true ]; then
    print_info "Running data-only setup..."
    if [ "$CLEAN_DATA" = true ]; then
        print_warning "This will clean existing data and insert fresh sample data"
        echo -n "Are you sure? (y/N): "
        read -r confirm
        if [[ ! $confirm =~ ^[Yy]$ ]]; then
            print_info "Operation cancelled"
            exit 0
        fi
    fi
    execute_sql_script_with_output "$SQL_SCRIPTS_DIR/03-seed-data.sql" "Sample data insertion"
else
    print_info "Starting full database setup..."
    
    # Step 1: Create database and schema
    if [ "$SKIP_CREATE" = false ]; then
        execute_sql_script "$SQL_SCRIPTS_DIR/01-create-database.sql" "Database and schema creation"
    else
        print_warning "Skipping database/schema creation"
    fi
    
    # Step 2: Create tables
    if [ "$SKIP_TABLES" = false ]; then
        execute_sql_script_with_output "$SQL_SCRIPTS_DIR/02-create-tables.sql" "Table creation"
    else
        print_warning "Skipping table creation"
    fi
    
    # Step 3: Insert sample data
    if [ "$SKIP_DATA" = false ]; then
        if [ "$CLEAN_DATA" = true ]; then
            print_warning "Clean flag enabled - existing data will be removed"
        fi
        execute_sql_script_with_output "$SQL_SCRIPTS_DIR/03-seed-data.sql" "Sample data insertion"
    else
        print_warning "Skipping sample data insertion"
    fi
    
    # Step 4: Setup permissions
    if [ "$SKIP_PERMISSIONS" = false ]; then
        execute_sql_script_with_output "$SQL_SCRIPTS_DIR/04-setup-permissions.sql" "Permissions setup"
    else
        print_warning "Skipping permissions setup"
    fi
fi

print_success "=== Database setup completed successfully! ==="
print_info ""
print_info "Connection details for your application:"
print_info "  SPRING_DATASOURCE_URL=jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME"
print_info "  SPRING_DATASOURCE_USERNAME=sonic_nest_app"
print_info "  SPRING_DATASOURCE_PASSWORD=sonic_nest_password_2024"
print_info "  SPRING_JPA_PROPERTIES_HIBERNATE_DEFAULT_SCHEMA=$DB_SCHEMA"
print_info ""
print_info "Alternative connection (using postgres user):"
print_info "  SPRING_DATASOURCE_USERNAME=$DB_USER"
print_info "  SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD"
print_info ""
print_info "Read-only access:"
print_info "  Username: sonic_nest_readonly"
print_info "  Password: sonic_nest_readonly_2024"
print_info ""
print_info "You can now start the Sonic Nest API application!"