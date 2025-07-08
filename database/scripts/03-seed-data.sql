-- =====================================================
-- Sonic Nest API Sample Data Script
-- Inserts sample/seed data for testing
-- =====================================================

-- Set schema
SET search_path TO "sonic-nest-api";

-- Clear existing data (in proper order to respect foreign keys)
TRUNCATE TABLE podcast_categories, playlist_tracks, track_categories, track_genres, album_genres CASCADE;
TRUNCATE TABLE podcast_episodes, chapters, episodes, payments, refresh_token, player_states, playlists, tracks, albums CASCADE;
TRUNCATE TABLE podcasts, audiobooks, shows, artists, categories, genres, markets, users CASCADE;

-- =====================================================
-- Core Reference Data
-- =====================================================

-- Insert Markets
INSERT INTO markets (country_code, country_name, currency) VALUES
('US', 'United States', 'USD'),
('GB', 'United Kingdom', 'GBP'),
('CA', 'Canada', 'CAD'),
('DE', 'Germany', 'EUR'),
('FR', 'France', 'EUR'),
('JP', 'Japan', 'JPY'),
('AU', 'Australia', 'AUD'),
('BR', 'Brazil', 'BRL'),
('IN', 'India', 'INR'),
('KR', 'South Korea', 'KRW');

-- Insert Genres
INSERT INTO genres (name, description) VALUES
('Rock', 'Rock music featuring electric guitars, drums, and strong rhythms'),
('Pop', 'Popular music with catchy melodies and mainstream appeal'),
('Hip-Hop', 'Music featuring rhythmic speech over strong beats'),
('Electronic', 'Music created using electronic instruments and technology'),
('Jazz', 'Improvisational music with complex harmonies and rhythms'),
('Classical', 'Traditional Western art music with orchestral compositions'),
('Country', 'American folk music with rural themes and acoustic instruments'),
('R&B', 'Rhythm and Blues music with soulful vocals and groove'),
('Indie', 'Independent music with alternative and experimental elements'),
('Alternative', 'Non-mainstream rock music with diverse influences'),
('Folk', 'Traditional acoustic music passed down through generations'),
('Blues', 'Music expressing emotions through twelve-bar progressions'),
('Reggae', 'Jamaican music with distinctive rhythm and social themes'),
('Punk', 'Fast, raw rock music with rebellious attitude'),
('Metal', 'Heavy rock music with aggressive guitar work and powerful vocals');

-- Insert Categories
INSERT INTO categories (name, description, image_url) VALUES
('New Releases', 'Latest music releases across all genres', 'https://example.com/images/new-releases.jpg'),
('Top Charts', 'Most popular tracks and albums', 'https://example.com/images/top-charts.jpg'),
('Workout', 'High-energy music perfect for exercise', 'https://example.com/images/workout.jpg'),
('Chill', 'Relaxing music for unwinding', 'https://example.com/images/chill.jpg'),
('Party', 'Upbeat music for celebrations and gatherings', 'https://example.com/images/party.jpg'),
('Sleep', 'Calming music for relaxation and sleep', 'https://example.com/images/sleep.jpg'),
('Focus', 'Instrumental music for concentration', 'https://example.com/images/focus.jpg'),
('Romance', 'Love songs and romantic ballads', 'https://example.com/images/romance.jpg'),
('Throwback', 'Classic hits from past decades', 'https://example.com/images/throwback.jpg'),
('Indie Picks', 'Curated independent artist selections', 'https://example.com/images/indie.jpg');

-- =====================================================
-- Users
-- =====================================================

INSERT INTO users (username, email, password) VALUES
('john_doe', 'john.doe@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye7VfK0.6pyfUu2ZEcfdpZ6xfLhOpdeRG'), -- password: password123
('jane_smith', 'jane.smith@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye7VfK0.6pyfUu2ZEcfdpZ6xfLhOpdeRG'), -- password: password123
('music_lover', 'music@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye7VfK0.6pyfUu2ZEcfdpZ6xfLhOpdeRG'), -- password: password123
('podcast_fan', 'podcast@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye7VfK0.6pyfUu2ZEcfdpZ6xfLhOpdeRG'), -- password: password123
('admin_user', 'admin@sonicnest.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye7VfK0.6pyfUu2ZEcfdpZ6xfLhOpdeRG'); -- password: password123

-- =====================================================
-- Artists
-- =====================================================

INSERT INTO artists (name, biography, image_url) VALUES
('The Electric Waves', 'An indie rock band known for their energetic performances and catchy melodies.', 'https://example.com/artists/electric-waves.jpg'),
('Luna Martinez', 'A solo pop artist with a powerful voice and meaningful lyrics.', 'https://example.com/artists/luna-martinez.jpg'),
('Jazz Collective', 'A group of talented jazz musicians creating contemporary jazz fusion.', 'https://example.com/artists/jazz-collective.jpg'),
('Digital Dreams', 'Electronic music producer creating ambient and dance tracks.', 'https://example.com/artists/digital-dreams.jpg'),
('Classic Symphony Orchestra', 'Renowned orchestra performing classical masterpieces.', 'https://example.com/artists/classic-symphony.jpg'),
('Country Roads Band', 'Traditional country music with modern storytelling.', 'https://example.com/artists/country-roads.jpg'),
('Hip-Hop Legends', 'Veteran hip-hop artists with decades of experience.', 'https://example.com/artists/hiphop-legends.jpg'),
('Acoustic Soul', 'Singer-songwriter duo creating intimate folk melodies.', 'https://example.com/artists/acoustic-soul.jpg');

-- =====================================================
-- Albums
-- =====================================================

INSERT INTO albums (title, release_date, image_url, artist_id) VALUES
('Neon Nights', '2023-06-15', 'https://example.com/albums/neon-nights.jpg', 1),
('Midnight Reflections', '2023-08-22', 'https://example.com/albums/midnight-reflections.jpg', 2),
('Jazz After Dark', '2023-04-10', 'https://example.com/albums/jazz-after-dark.jpg', 3),
('Synthetic Dreams', '2023-09-05', 'https://example.com/albums/synthetic-dreams.jpg', 4),
('Classical Masterworks', '2023-03-18', 'https://example.com/albums/classical-masterworks.jpg', 5),
('Highway Stories', '2023-07-28', 'https://example.com/albums/highway-stories.jpg', 6),
('Urban Chronicles', '2023-11-12', 'https://example.com/albums/urban-chronicles.jpg', 7),
('Whispered Melodies', '2023-05-03', 'https://example.com/albums/whispered-melodies.jpg', 8);

-- =====================================================
-- Tracks
-- =====================================================

INSERT INTO tracks (title, duration, audio_url, album_id) VALUES
-- Neon Nights (Album 1)
('Electric City', INTERVAL '3 minutes 45 seconds', 'https://example.com/audio/electric-city.mp3', 1),
('Neon Dreams', INTERVAL '4 minutes 12 seconds', 'https://example.com/audio/neon-dreams.mp3', 1),
('Midnight Drive', INTERVAL '3 minutes 58 seconds', 'https://example.com/audio/midnight-drive.mp3', 1),
('City Lights', INTERVAL '4 minutes 23 seconds', 'https://example.com/audio/city-lights.mp3', 1),

-- Midnight Reflections (Album 2)
('Stargazing', INTERVAL '3 minutes 32 seconds', 'https://example.com/audio/stargazing.mp3', 2),
('Silent Thoughts', INTERVAL '4 minutes 15 seconds', 'https://example.com/audio/silent-thoughts.mp3', 2),
('Dancing Shadows', INTERVAL '3 minutes 48 seconds', 'https://example.com/audio/dancing-shadows.mp3', 2),

-- Jazz After Dark (Album 3)
('Smooth Saxophone', INTERVAL '5 minutes 22 seconds', 'https://example.com/audio/smooth-saxophone.mp3', 3),
('Blue Note Cafe', INTERVAL '4 minutes 45 seconds', 'https://example.com/audio/blue-note-cafe.mp3', 3),
('Jazz Improvisation', INTERVAL '6 minutes 18 seconds', 'https://example.com/audio/jazz-improvisation.mp3', 3),

-- Synthetic Dreams (Album 4)
('Digital Pulse', INTERVAL '4 minutes 35 seconds', 'https://example.com/audio/digital-pulse.mp3', 4),
('Cyber Romance', INTERVAL '3 minutes 52 seconds', 'https://example.com/audio/cyber-romance.mp3', 4),
('Future Memories', INTERVAL '5 minutes 8 seconds', 'https://example.com/audio/future-memories.mp3', 4),

-- Classical Masterworks (Album 5)
('Symphony No. 1', INTERVAL '12 minutes 35 seconds', 'https://example.com/audio/symphony-1.mp3', 5),
('Moonlight Sonata (Modern)', INTERVAL '8 minutes 42 seconds', 'https://example.com/audio/moonlight-modern.mp3', 5),

-- Highway Stories (Album 6)
('Open Road', INTERVAL '3 minutes 28 seconds', 'https://example.com/audio/open-road.mp3', 6),
('Small Town Heart', INTERVAL '4 minutes 5 seconds', 'https://example.com/audio/small-town-heart.mp3', 6),
('Country Dawn', INTERVAL '3 minutes 44 seconds', 'https://example.com/audio/country-dawn.mp3', 6),

-- Urban Chronicles (Album 7)
('Street Wisdom', INTERVAL '3 minutes 55 seconds', 'https://example.com/audio/street-wisdom.mp3', 7),
('City Hustle', INTERVAL '4 minutes 18 seconds', 'https://example.com/audio/city-hustle.mp3', 7),

-- Whispered Melodies (Album 8)
('Autumn Leaves', INTERVAL '3 minutes 22 seconds', 'https://example.com/audio/autumn-leaves.mp3', 8),
('River Song', INTERVAL '4 minutes 12 seconds', 'https://example.com/audio/river-song.mp3', 8);

-- =====================================================
-- Playlists
-- =====================================================

INSERT INTO playlists (name, description, is_public, user_id) VALUES
('My Favorites', 'Personal collection of favorite tracks', false, 1),
('Workout Mix', 'High-energy songs for exercise', true, 1),
('Chill Vibes', 'Relaxing music for unwinding', true, 2),
('Road Trip Classics', 'Perfect songs for long drives', true, 3),
('Study Focus', 'Instrumental music for concentration', false, 4),
('Party Hits', 'Upbeat songs for celebrations', true, 2);

-- =====================================================
-- Shows (for Episodes)
-- =====================================================

INSERT INTO shows (title, description, publisher, image_url) VALUES
('Tech Talk Weekly', 'Latest technology news and discussions', 'Tech Media Corp', 'https://example.com/shows/tech-talk.jpg'),
('Music History Podcast', 'Exploring the history of different music genres', 'Music Education Network', 'https://example.com/shows/music-history.jpg'),
('Daily Meditation', 'Guided meditation sessions for mindfulness', 'Wellness Studio', 'https://example.com/shows/meditation.jpg');

-- =====================================================
-- Episodes
-- =====================================================

INSERT INTO episodes (title, description, duration, audio_url, release_date, image_url, show_id) VALUES
('AI Revolution in Music', 'How artificial intelligence is changing the music industry', 2400, 'https://example.com/episodes/ai-music.mp3', '2023-12-01 10:00:00', 'https://example.com/episodes/ai-music.jpg', 1),
('The Birth of Jazz', 'Exploring the origins of jazz music in New Orleans', 3600, 'https://example.com/episodes/birth-jazz.mp3', '2023-11-28 15:00:00', 'https://example.com/episodes/birth-jazz.jpg', 2),
('Morning Mindfulness', 'A 10-minute guided meditation for starting your day', 600, 'https://example.com/episodes/morning-meditation.mp3', '2023-12-02 06:00:00', 'https://example.com/episodes/morning-meditation.jpg', 3);

-- =====================================================
-- Audiobooks
-- =====================================================

INSERT INTO audiobooks (title, author, narrator, description, publisher, duration, image_url) VALUES
('The Art of Music Production', 'Sarah Johnson', 'Michael Thompson', 'A comprehensive guide to modern music production techniques', 'Audio Learning Press', 18000, 'https://example.com/audiobooks/music-production.jpg'),
('Digital Audio Mastering', 'Robert Chen', 'Lisa Williams', 'Master the art of audio mastering in the digital age', 'Tech Audio Books', 15600, 'https://example.com/audiobooks/audio-mastering.jpg');

-- =====================================================
-- Chapters
-- =====================================================

INSERT INTO chapters (title, duration, chapter_number, audio_url, audiobook_id) VALUES
('Introduction to Music Production', 1200, 1, 'https://example.com/chapters/intro-production.mp3', 1),
('Setting Up Your Home Studio', 1800, 2, 'https://example.com/chapters/home-studio.mp3', 1),
('Recording Techniques', 2100, 3, 'https://example.com/chapters/recording-techniques.mp3', 1),
('Mixing Fundamentals', 1900, 4, 'https://example.com/chapters/mixing-fundamentals.mp3', 1),
('Understanding EQ', 1500, 1, 'https://example.com/chapters/understanding-eq.mp3', 2),
('Compression Techniques', 1650, 2, 'https://example.com/chapters/compression.mp3', 2),
('Mastering Workflow', 1800, 3, 'https://example.com/chapters/mastering-workflow.mp3', 2);

-- =====================================================
-- Podcasts
-- =====================================================

INSERT INTO podcasts (title, author, description, publisher, language, explicit, image_url) VALUES
('Indie Music Spotlight', 'Alex Rodriguez', 'Discovering new independent artists and their stories', 'Independent Media', 'English', false, 'https://example.com/podcasts/indie-spotlight.jpg'),
('Electronic Beats', 'DJ Synthwave', 'Exploring the world of electronic music and culture', 'Electronic Music Network', 'English', false, 'https://example.com/podcasts/electronic-beats.jpg'),
('Hip-Hop Chronicles', 'MC History', 'Deep dives into hip-hop culture and history', 'Urban Culture Media', 'English', true, 'https://example.com/podcasts/hiphop-chronicles.jpg');

-- =====================================================
-- Podcast Episodes
-- =====================================================

INSERT INTO podcast_episodes (title, description, duration, audio_url, release_date, image_url, episode_number, explicit, podcast_id) VALUES
('Rising Stars of 2023', 'Featuring new indie artists making waves this year', 2700, 'https://example.com/podcast-episodes/rising-stars.mp3', '2023-12-01 12:00:00', 'https://example.com/podcast-episodes/rising-stars.jpg', 1, false, 1),
('The Evolution of Synthesizers', 'From analog to digital: the synth revolution', 3300, 'https://example.com/podcast-episodes/synth-evolution.mp3', '2023-11-30 14:00:00', 'https://example.com/podcast-episodes/synth-evolution.jpg', 1, false, 2),
('Golden Age of Hip-Hop', 'Exploring the classic era of hip-hop music', 4200, 'https://example.com/podcast-episodes/golden-age.mp3', '2023-11-29 16:00:00', 'https://example.com/podcast-episodes/golden-age.jpg', 1, true, 3);

-- =====================================================
-- Many-to-Many Relationships
-- =====================================================

-- Album Genres
INSERT INTO album_genres (album_id, genre_id) VALUES
(1, 9), (1, 1), -- Neon Nights: Indie, Rock
(2, 2), (2, 9), -- Midnight Reflections: Pop, Indie
(3, 5), -- Jazz After Dark: Jazz
(4, 4), -- Synthetic Dreams: Electronic
(5, 6), -- Classical Masterworks: Classical
(6, 7), -- Highway Stories: Country
(7, 3), -- Urban Chronicles: Hip-Hop
(8, 11), (8, 9); -- Whispered Melodies: Folk, Indie

-- Track Genres (inherit from albums, with some additional)
INSERT INTO track_genres (track_id, genre_id) VALUES
-- Electric City, Neon Dreams, Midnight Drive, City Lights (Album 1 - Indie/Rock)
(1, 9), (1, 1), (2, 9), (2, 1), (3, 9), (3, 1), (4, 9), (4, 1),
-- Stargazing, Silent Thoughts, Dancing Shadows (Album 2 - Pop/Indie)
(5, 2), (5, 9), (6, 2), (6, 9), (7, 2), (7, 9),
-- Jazz tracks
(8, 5), (9, 5), (10, 5),
-- Electronic tracks
(11, 4), (12, 4), (13, 4),
-- Classical tracks
(14, 6), (15, 6),
-- Country tracks
(16, 7), (17, 7), (18, 7),
-- Hip-Hop tracks
(19, 3), (20, 3),
-- Folk tracks
(21, 11), (22, 11);

-- Track Categories
INSERT INTO track_categories (track_id, category_id) VALUES
-- New Releases
(1, 1), (2, 1), (5, 1), (11, 1),
-- Top Charts
(1, 2), (5, 2), (19, 2),
-- Workout
(1, 3), (3, 3), (11, 3), (19, 3), (20, 3),
-- Chill
(6, 4), (9, 4), (12, 4), (21, 4), (22, 4),
-- Party
(1, 5), (7, 5), (11, 5), (19, 5),
-- Focus
(8, 7), (14, 7), (15, 7),
-- Romance
(5, 8), (6, 8), (12, 8), (21, 8),
-- Throwback
(14, 9), (15, 9),
-- Indie Picks
(1, 10), (2, 10), (5, 10), (21, 10), (22, 10);

-- Playlist Tracks
INSERT INTO playlist_tracks (playlist_id, track_id) VALUES
-- My Favorites (Playlist 1)
(1, 1), (1, 5), (1, 8), (1, 11), (1, 21),
-- Workout Mix (Playlist 2)
(2, 1), (2, 3), (2, 11), (2, 19), (2, 20),
-- Chill Vibes (Playlist 3)
(3, 6), (3, 9), (3, 12), (3, 21), (3, 22),
-- Road Trip Classics (Playlist 4)
(4, 3), (4, 16), (4, 17), (4, 18),
-- Study Focus (Playlist 5)
(5, 8), (5, 14), (5, 15),
-- Party Hits (Playlist 6)
(6, 1), (6, 7), (6, 11), (6, 19);

-- Podcast Categories
INSERT INTO podcast_categories (podcast_id, category_id) VALUES
(1, 10), -- Indie Music Spotlight: Indie Picks
(2, 1), (2, 7), -- Electronic Beats: New Releases, Focus
(3, 9); -- Hip-Hop Chronicles: Throwback

-- =====================================================
-- Player States (sample for some users)
-- =====================================================

INSERT INTO player_states (user_id, current_track_id, progress_ms, is_playing, shuffle_state, repeat_state, volume) VALUES
(1, 1, 45000, true, false, 'off', 85),
(2, 5, 120000, false, true, 'track', 70),
(3, 16, 0, false, false, 'context', 90);

-- =====================================================
-- Sample Payments
-- =====================================================

INSERT INTO payments (transaction_id, amount, currency, status, payment_method, payment_date, description, user_id) VALUES
('TXN_001', 9.99, 'USD', 'COMPLETED', 'Credit Card', '2023-11-15 14:30:00', 'Monthly Premium Subscription', 1),
('TXN_002', 4.99, 'USD', 'COMPLETED', 'PayPal', '2023-11-20 10:15:00', 'Single Track Purchase', 2),
('TXN_003', 19.99, 'USD', 'PENDING', 'Bank Transfer', '2023-12-01 09:00:00', 'Annual Premium Subscription', 3),
('TXN_004', 9.99, 'USD', 'FAILED', 'Credit Card', '2023-11-28 16:45:00', 'Monthly Premium Subscription', 4),
('TXN_005', 14.99, 'USD', 'COMPLETED', 'Credit Card', '2023-11-25 12:30:00', 'Album Purchase', 1);

-- =====================================================
-- Display summary
-- =====================================================

SELECT 'Data insertion completed successfully!' as status;

-- Show table row counts
SELECT 
    'users' as table_name, COUNT(*) as row_count FROM users
UNION ALL
SELECT 'artists', COUNT(*) FROM artists
UNION ALL
SELECT 'albums', COUNT(*) FROM albums
UNION ALL
SELECT 'tracks', COUNT(*) FROM tracks
UNION ALL
SELECT 'playlists', COUNT(*) FROM playlists
UNION ALL
SELECT 'genres', COUNT(*) FROM genres
UNION ALL
SELECT 'categories', COUNT(*) FROM categories
UNION ALL
SELECT 'shows', COUNT(*) FROM shows
UNION ALL
SELECT 'episodes', COUNT(*) FROM episodes
UNION ALL
SELECT 'audiobooks', COUNT(*) FROM audiobooks
UNION ALL
SELECT 'chapters', COUNT(*) FROM chapters
UNION ALL
SELECT 'podcasts', COUNT(*) FROM podcasts
UNION ALL
SELECT 'podcast_episodes', COUNT(*) FROM podcast_episodes
UNION ALL
SELECT 'payments', COUNT(*) FROM payments
UNION ALL
SELECT 'markets', COUNT(*) FROM markets
UNION ALL
SELECT 'player_states', COUNT(*) FROM player_states
ORDER BY table_name;