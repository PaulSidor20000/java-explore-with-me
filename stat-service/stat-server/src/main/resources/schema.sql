drop table if exists endpoint_hits;

CREATE TABLE IF NOT EXISTS endpoint_hits
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    NOT
    NULL,
    app
    VARCHAR
(
    255
) NOT NULL,
    uri VARCHAR
(
    255
) NOT NULL,
    ip VARCHAR
(
    64
) NOT NULL,
    timestamp_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_endpoint_hit_id
    PRIMARY KEY
(
    id
)
    );
