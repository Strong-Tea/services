CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE notification (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    message TEXT NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL
);
