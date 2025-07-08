# Sonic Nest API Database Setup

This directory contains comprehensive database setup scripts for the Sonic Nest music streaming API. The scripts handle PostgreSQL database creation, table setup, sample data insertion, and permissions configuration for both local development and Docker environments.

## 📁 Directory Structure

```
database/
├── scripts/
│   ├── 01-create-database.sql    # Database and schema creation
│   ├── 02-create-tables.sql      # Complete table DDL
│   ├── 03-seed-data.sql         # Sample/seed data for testing
│   └── 04-setup-permissions.sql # User permissions and security
├── setup-database.sh           # Main setup script for local development
├── docker-setup.sh            # Docker-optimized setup script
└── README.md                  # This documentation
```

## 🚀 Quick Start

### Local Development Setup

1. **Ensure PostgreSQL is running** on your local machine
2. **Run the setup script**:
   ```bash
   cd database
   chmod +x setup-database.sh
   ./setup-database.sh
   ```
3. **Follow the interactive prompts** or use command-line options

### Docker Environment Setup

1. **Start the Docker containers**:
   ```bash
   docker-compose up -d postgres
   ```
2. **Run the Docker setup script**:
   ```bash
   cd database
   chmod +x docker-setup.sh
   ./docker-setup.sh
   ```

## 📋 What Gets Created

### Database Schema
- **Schema Name**: `sonic-nest-api`
- **Primary Database**: `postgres`
- **Character Set**: UTF-8

### Tables Created
The setup creates tables for all Spring Boot entities:

#### Core Music Tables
- `users` - User accounts and authentication
- `artists` - Music artists and performers  
- `albums` - Music albums with metadata
- `tracks` - Individual music tracks
- `genres` - Music genre classifications
- `categories` - Content category groupings

#### Playlist and Player Tables
- `playlists` - User-created track collections
- `playlist_tracks` - Many-to-many playlist-track relationships
- `player_states` - Current playback state per user

#### Podcast and Audio Content
- `podcasts` - Podcast shows
- `podcast_episodes` - Individual podcast episodes
- `shows` - General shows (video/audio)
- `episodes` - Show episodes
- `audiobooks` - Audio book content
- `chapters` - Audiobook chapters

#### Supporting Tables
- `payments` - Payment transactions
- `markets` - Geographical regions
- `refresh_token` - JWT authentication tokens
- `application_logs` - Application logging (optional)

#### Join Tables (Many-to-Many)
- `album_genres`
- `track_genres`
- `track_categories`
- `podcast_categories`

### Sample Data
The setup includes realistic sample data:
- **5 users** with bcrypt-hashed passwords
- **8 artists** across different genres
- **8 albums** with release dates and artwork
- **22 tracks** with proper durations and audio URLs
- **6 playlists** with varied themes
- **15 genres** covering major music categories
- **10 categories** for content organization
- **Multiple podcasts, shows, and audiobooks** with episodes/chapters
- **Payment records** showing different transaction types
- **Market data** for international content

## 🛠 Setup Scripts

### Main Setup Script (`setup-database.sh`)

**Purpose**: Comprehensive setup for local development environments

**Features**:
- Interactive configuration
- Flexible command-line options
- Connection validation
- Step-by-step execution
- Detailed logging and error handling

**Usage**:
```bash
# Basic interactive setup
./setup-database.sh

# Setup with specific connection
./setup-database.sh -h localhost -u postgres -w mypassword

# Skip sample data
./setup-database.sh --skip-data

# Only refresh sample data
./setup-database.sh --data-only --clean

# Custom host and schema
./setup-database.sh -h db.example.com --schema my-custom-schema
```

**Command Line Options**:
- `-h, --host` - Database host (default: localhost)
- `-p, --port` - Database port (default: 5432)
- `-d, --database` - Database name (default: postgres)
- `-u, --user` - Database user (default: postgres)
- `-w, --password` - Database password (prompts if not provided)
- `--schema` - Schema name (default: sonic-nest-api)
- `--skip-create` - Skip database/schema creation
- `--skip-tables` - Skip table creation
- `--skip-data` - Skip sample data insertion
- `--skip-permissions` - Skip permissions setup
- `--data-only` - Only insert sample data
- `--clean` - Clean existing data before inserting
- `--help` - Show help message

### Docker Setup Script (`docker-setup.sh`)

**Purpose**: Optimized setup for Docker container environments

**Features**:
- Automatic PostgreSQL readiness detection
- Docker-specific defaults
- Container-friendly logging
- Idempotent execution

**Usage**:
```bash
# Basic Docker setup
./docker-setup.sh

# Force setup even if tables exist
./docker-setup.sh --force

# Only refresh sample data
./docker-setup.sh --data-only

# Setup without sample data
./docker-setup.sh --skip-data
```

## 🔧 Individual SQL Scripts

### 1. Database Creation (`01-create-database.sql`)
- Creates the `sonic-nest-api` schema
- Sets up proper search paths
- Configures basic permissions
- Handles schema comments and documentation

### 2. Table Creation (`02-create-tables.sql`)
- Creates all tables in dependency order
- Defines foreign key relationships
- Sets up constraints and indexes
- Includes performance optimization indexes
- Adds table comments for documentation

### 3. Sample Data (`03-seed-data.sql`)
- Inserts realistic test data
- Maintains referential integrity
- Provides diverse content for testing
- Includes bcrypt-hashed passwords
- Shows relationship examples

### 4. Permissions Setup (`04-setup-permissions.sql`)
- Creates dedicated application users
- Sets up role-based security
- Configures read-only access
- Establishes proper schema privileges
- Optional row-level security examples

## 🔐 Database Users Created

### Application User
- **Username**: `sonic_nest_app`
- **Password**: `sonic_nest_password_2024`
- **Privileges**: Full access to all tables and sequences
- **Purpose**: Primary application database user

### Read-Only User
- **Username**: `sonic_nest_readonly`
- **Password**: `sonic_nest_readonly_2024`
- **Privileges**: SELECT only on all tables
- **Purpose**: Reporting, analytics, and monitoring

### Admin User
- **Username**: `postgres` (existing)
- **Purpose**: Database administration and maintenance

## ⚙ Application Configuration

After running the setup, update your Spring Boot configuration:

### Local Development (`application.properties`)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=sonic_nest_app
spring.datasource.password=sonic_nest_password_2024
spring.jpa.properties.hibernate.default_schema=sonic-nest-api
spring.jpa.hibernate.ddl-auto=validate
```

### Docker Environment
The `docker-compose.yml` is already configured with the correct settings:
```yaml
environment:
  - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/postgres
  - SPRING_DATASOURCE_USERNAME=postgres
  - SPRING_DATASOURCE_PASSWORD=password
  - SPRING_JPA_PROPERTIES_HIBERNATE_DEFAULT_SCHEMA=sonic-nest-api
```

## 🧪 Testing the Setup

### Verify Database Connection
```bash
# Test with application user
PGPASSWORD=sonic_nest_password_2024 psql -h localhost -U sonic_nest_app -d postgres -c "SELECT current_schema();"

# Test with read-only user
PGPASSWORD=sonic_nest_readonly_2024 psql -h localhost -U sonic_nest_readonly -d postgres -c "SELECT COUNT(*) FROM users;"
```

### Check Sample Data
```sql
-- Connect to database and check data
\c postgres
SET search_path TO "sonic-nest-api";

-- View sample data counts
SELECT 'users' as table_name, COUNT(*) as row_count FROM users
UNION ALL SELECT 'artists', COUNT(*) FROM artists
UNION ALL SELECT 'albums', COUNT(*) FROM albums
UNION ALL SELECT 'tracks', COUNT(*) FROM tracks;
```

## 🔄 Maintenance Commands

### Refresh Sample Data Only
```bash
./setup-database.sh --data-only --clean
```

### Backup Database
```bash
pg_dump -h localhost -U postgres -d postgres --schema="sonic-nest-api" > sonic-nest-backup.sql
```

### Reset Database
```bash
# Drop and recreate schema
PGPASSWORD=yourpassword psql -h localhost -U postgres -d postgres -c "DROP SCHEMA IF EXISTS \"sonic-nest-api\" CASCADE;"
./setup-database.sh
```

## 🐛 Troubleshooting

### Connection Issues
- Verify PostgreSQL is running: `pg_isready -h localhost -p 5432`
- Check firewall settings for port 5432
- Verify user credentials and permissions

### Permission Errors
- Ensure the user has database creation privileges
- Check schema ownership and access rights
- Verify search_path is set correctly

### Docker Issues
- Ensure postgres container is running: `docker-compose ps`
- Check container logs: `docker-compose logs postgres`
- Verify network connectivity between containers

### Script Execution Errors
- Make scripts executable: `chmod +x *.sh`
- Check file paths and current directory
- Verify all SQL script files are present

## 🔒 Security Considerations

1. **Change Default Passwords**: Update default passwords before production deployment
2. **Network Security**: Restrict database access to authorized hosts only
3. **User Permissions**: Use principle of least privilege
4. **SSL/TLS**: Enable encrypted connections in production
5. **Regular Updates**: Keep PostgreSQL updated with security patches

## 📈 Performance Optimization

The setup includes several performance optimizations:
- **Indexes** on frequently queried columns
- **Foreign key constraints** for data integrity
- **Proper data types** for efficient storage
- **Schema organization** for logical separation

### Additional Recommendations
- Consider connection pooling for high traffic
- Monitor query performance and add indexes as needed
- Regular VACUUM and ANALYZE operations
- Configure appropriate PostgreSQL memory settings

## 🤝 Contributing

When modifying the database scripts:
1. Test changes in a clean environment
2. Update documentation if schema changes
3. Maintain backward compatibility when possible
4. Follow naming conventions for consistency
5. Add appropriate indexes for new tables

## 📞 Support

For issues with the database setup:
1. Check this documentation first
2. Review error messages and logs
3. Verify PostgreSQL configuration
4. Test with minimal configuration
5. Check Spring Boot application logs for connection issues