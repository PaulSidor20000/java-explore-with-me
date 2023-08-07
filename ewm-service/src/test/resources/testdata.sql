DELETE
FROM locations;
ALTER TABLE locations
    ALTER COLUMN location_id RESTART WITH 1;

INSERT INTO locations(location_name, lat, lon)
VALUES ('Бразилия, Мараньян', -5.0462, -45.5376),
       ('Гренландское море', 75.4722, -3.8826);