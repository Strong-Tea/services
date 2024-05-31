CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE orders (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    customer_id UUID NOT NULL,
    order_quantity BIGINT NOT NULL,
    order_status SMALLINT NOT NULL,
    product_id UUID NOT NULL
);
