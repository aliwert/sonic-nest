-- =====================================================
-- Sonic Nest API Table Creation Script
-- Creates all tables based on JPA entities
-- =====================================================

-- Set schema
SET search_path TO "sonic-nest-api";

-- =====================================================
-- Core Tables (no dependencies)
-- =====================================================

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Artists table
CREATE TABLE IF NOT EXISTS artists (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    biography TEXT,
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Genres table
CREATE TABLE IF NOT EXISTS genres (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Categories table
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Markets table
CREATE TABLE IF NOT EXISTS markets (
    id BIGSERIAL PRIMARY KEY,
    country_code VARCHAR(10) NOT NULL UNIQUE,
    country_name VARCHAR(255) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Shows table
CREATE TABLE IF NOT EXISTS shows (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    publisher VARCHAR(255),
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Audiobooks table
CREATE TABLE IF NOT EXISTS audiobooks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    narrator VARCHAR(255),
    description TEXT,
    publisher VARCHAR(255),
    duration INTEGER, -- in seconds
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Podcasts table
CREATE TABLE IF NOT EXISTS podcasts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description TEXT,
    publisher VARCHAR(255),
    language VARCHAR(50),
    explicit BOOLEAN DEFAULT FALSE,
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tables with Foreign Keys
-- =====================================================

-- Albums table (depends on artists)
CREATE TABLE IF NOT EXISTS albums (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    release_date DATE,
    image_url VARCHAR(500),
    artist_id BIGINT REFERENCES artists(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tracks table (depends on albums)
CREATE TABLE IF NOT EXISTS tracks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    duration INTERVAL, -- PostgreSQL interval type for Duration
    audio_url VARCHAR(500),
    album_id BIGINT REFERENCES albums(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Playlists table (depends on users)
CREATE TABLE IF NOT EXISTS playlists (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_public BOOLEAN DEFAULT FALSE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Player States table (depends on users and tracks)
CREATE TABLE IF NOT EXISTS player_states (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    current_track_id BIGINT REFERENCES tracks(id) ON DELETE SET NULL,
    progress_ms INTEGER DEFAULT 0,
    is_playing BOOLEAN DEFAULT FALSE,
    shuffle_state BOOLEAN DEFAULT FALSE,
    repeat_state VARCHAR(20) DEFAULT 'off' CHECK (repeat_state IN ('off', 'track', 'context')),
    volume INTEGER DEFAULT 100 CHECK (volume >= 0 AND volume <= 100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Episodes table (depends on shows)
CREATE TABLE IF NOT EXISTS episodes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duration INTEGER, -- in seconds
    audio_url VARCHAR(500),
    release_date TIMESTAMP,
    image_url VARCHAR(500),
    show_id BIGINT NOT NULL REFERENCES shows(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Chapters table (depends on audiobooks)
CREATE TABLE IF NOT EXISTS chapters (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    duration INTEGER, -- in seconds
    chapter_number INTEGER NOT NULL,
    audio_url VARCHAR(500),
    audiobook_id BIGINT NOT NULL REFERENCES audiobooks(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(audiobook_id, chapter_number)
);

-- Podcast Episodes table (depends on podcasts)
CREATE TABLE IF NOT EXISTS podcast_episodes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duration INTEGER, -- in seconds
    audio_url VARCHAR(500),
    release_date TIMESTAMP,
    image_url VARCHAR(500),
    episode_number INTEGER,
    explicit BOOLEAN DEFAULT FALSE,
    podcast_id BIGINT NOT NULL REFERENCES podcasts(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(podcast_id, episode_number)
);

-- Payments table (depends on users)
CREATE TABLE IF NOT EXISTS payments (
    id BIGSERIAL PRIMARY KEY,
    transaction_id VARCHAR(255) NOT NULL UNIQUE,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED', 'CANCELLED')),
    payment_method VARCHAR(50) NOT NULL,
    payment_date TIMESTAMP,
    description TEXT,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Refresh Tokens table (depends on users)
CREATE TABLE IF NOT EXISTS refresh_token (
    id BIGSERIAL PRIMARY KEY,
    refresh_token VARCHAR(500) NOT NULL UNIQUE,
    expired_token TIMESTAMP NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Many-to-Many Join Tables
-- =====================================================

-- Album Genres
CREATE TABLE IF NOT EXISTS album_genres (
    album_id BIGINT NOT NULL REFERENCES albums(id) ON DELETE CASCADE,
    genre_id BIGINT NOT NULL REFERENCES genres(id) ON DELETE CASCADE,
    PRIMARY KEY (album_id, genre_id)
);

-- Track Genres
CREATE TABLE IF NOT EXISTS track_genres (
    track_id BIGINT NOT NULL REFERENCES tracks(id) ON DELETE CASCADE,
    genre_id BIGINT NOT NULL REFERENCES genres(id) ON DELETE CASCADE,
    PRIMARY KEY (track_id, genre_id)
);

-- Track Categories
CREATE TABLE IF NOT EXISTS track_categories (
    track_id BIGINT NOT NULL REFERENCES tracks(id) ON DELETE CASCADE,
    category_id BIGINT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
    PRIMARY KEY (track_id, category_id)
);

-- Playlist Tracks
CREATE TABLE IF NOT EXISTS playlist_tracks (
    playlist_id BIGINT NOT NULL REFERENCES playlists(id) ON DELETE CASCADE,
    track_id BIGINT NOT NULL REFERENCES tracks(id) ON DELETE CASCADE,
    PRIMARY KEY (playlist_id, track_id)
);

-- Podcast Categories
CREATE TABLE IF NOT EXISTS podcast_categories (
    podcast_id BIGINT NOT NULL REFERENCES podcasts(id) ON DELETE CASCADE,
    category_id BIGINT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
    PRIMARY KEY (podcast_id, category_id)
);

-- =====================================================
-- Indexes for Performance
-- =====================================================

-- User indexes
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- Track indexes
CREATE INDEX IF NOT EXISTS idx_tracks_title ON tracks(title);
CREATE INDEX IF NOT EXISTS idx_tracks_album_id ON tracks(album_id);

-- Album indexes
CREATE INDEX IF NOT EXISTS idx_albums_title ON albums(title);
CREATE INDEX IF NOT EXISTS idx_albums_artist_id ON albums(artist_id);
CREATE INDEX IF NOT EXISTS idx_albums_release_date ON albums(release_date);

-- Playlist indexes
CREATE INDEX IF NOT EXISTS idx_playlists_user_id ON playlists(user_id);
CREATE INDEX IF NOT EXISTS idx_playlists_name ON playlists(name);

-- Artist indexes
CREATE INDEX IF NOT EXISTS idx_artists_name ON artists(name);

-- Payment indexes
CREATE INDEX IF NOT EXISTS idx_payments_user_id ON payments(user_id);
CREATE INDEX IF NOT EXISTS idx_payments_transaction_id ON payments(transaction_id);
CREATE INDEX IF NOT EXISTS idx_payments_status ON payments(status);

-- Podcast episode indexes
CREATE INDEX IF NOT EXISTS idx_podcast_episodes_podcast_id ON podcast_episodes(podcast_id);
CREATE INDEX IF NOT EXISTS idx_podcast_episodes_release_date ON podcast_episodes(release_date);

-- Chapter indexes
CREATE INDEX IF NOT EXISTS idx_chapters_audiobook_id ON chapters(audiobook_id);
CREATE INDEX IF NOT EXISTS idx_chapters_chapter_number ON chapters(chapter_number);

-- Episode indexes
CREATE INDEX IF NOT EXISTS idx_episodes_show_id ON episodes(show_id);
CREATE INDEX IF NOT EXISTS idx_episodes_release_date ON episodes(release_date);

-- =====================================================
-- Comments
-- =====================================================

COMMENT ON TABLE users IS 'User accounts for the music streaming platform';
COMMENT ON TABLE artists IS 'Music artists and performers';
COMMENT ON TABLE albums IS 'Music albums containing tracks';
COMMENT ON TABLE tracks IS 'Individual music tracks';
COMMENT ON TABLE playlists IS 'User-created collections of tracks';
COMMENT ON TABLE genres IS 'Music genre classifications';
COMMENT ON TABLE categories IS 'Content category classifications';
COMMENT ON TABLE player_states IS 'Current playback state for each user';
COMMENT ON TABLE shows IS 'Podcast or video shows';
COMMENT ON TABLE episodes IS 'Individual episodes of shows';
COMMENT ON TABLE audiobooks IS 'Audio book content';
COMMENT ON TABLE chapters IS 'Individual chapters of audiobooks';
COMMENT ON TABLE podcasts IS 'Podcast shows';
COMMENT ON TABLE podcast_episodes IS 'Individual podcast episodes';
COMMENT ON TABLE payments IS 'Payment transactions';
COMMENT ON TABLE markets IS 'Geographical markets for content availability';
COMMENT ON TABLE refresh_token IS 'JWT refresh tokens for authentication';

-- Display table creation summary
SELECT 
    schemaname,
    tablename,
    tableowner
FROM pg_tables 
WHERE schemaname = 'sonic-nest-api'
ORDER BY tablename;