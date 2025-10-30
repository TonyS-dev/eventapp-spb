ALTER TABLE events
ADD COLUMN venue_id BIGINT;
ALTER TABLE events
ADD CONSTRAINT fk_events_venues
FOREIGN KEY (venue_id) REFERENCES venues (id);
