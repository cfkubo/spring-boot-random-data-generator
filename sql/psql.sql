-- ## bug in procedure. need to fix

CREATE SEQUENCE IF NOT EXISTS public.salesorders_order_id_seq;

CREATE TABLE IF NOT EXISTS public.salesorders (
    order_id integer NOT NULL DEFAULT nextval('salesorders_order_id_seq'::regclass),
    payload character varying(1000) COLLATE pg_catalog."default",
    CONSTRAINT salesorders_pkey PRIMARY KEY (order_id)
);

CREATE OR REPLACE TRIGGER sales_order_update_trigger
    AFTER INSERT OR UPDATE 
    ON public.salesorders
    FOR EACH ROW
    EXECUTE FUNCTION public.sales_order_trigger_func();

CREATE TABLE IF NOT EXISTS public.salesorders_read
(
    order_id integer,
    product character varying(255) COLLATE pg_catalog."default",
    price numeric(10,2),
    quantity integer,
    ship_to character varying(50) COLLATE pg_catalog."default",
    payment_method character varying(50) COLLATE pg_catalog."default",
    order_date date,
    address character varying(50) COLLATE pg_catalog."default",
    store_name character varying(50) COLLATE pg_catalog."default",
    store_address character varying(50) COLLATE pg_catalog."default",
    sales_rep_name character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT salesorders_read_pkey PRIMARY KEY (order_id)
);
	
CREATE OR REPLACE PROCEDURE public.process_sales_order(order_id INT, payload TEXT)
LANGUAGE plpgsql
AS $$
DECLARE
    product VARCHAR(255);
    price NUMERIC(10, 2);
    quantity INT;
    ship_to VARCHAR(50);
    payment_method VARCHAR(50);
    order_date DATE;
    address VARCHAR(50);
    store_name VARCHAR(50);
    store_address VARCHAR(50);
    sales_rep_name VARCHAR(50);
BEGIN
    -- Extract values using regular expressions
    product := REGEXP_REPLACE(payload, '.*product=''([^'']*)''.*', '\1');
    price := REGEXP_REPLACE(payload, '.*price=([\d\.]+).*', '\1')::NUMERIC(10, 2);
    quantity := REGEXP_REPLACE(payload, '.*quantity=(\d+).*', '\1')::INT;
    ship_to := REGEXP_REPLACE(payload, '.*shipTo=''([^'']*)''.*', '\1');
    payment_method := REGEXP_REPLACE(payload, '.*paymentMethod=''([^'']*)''.*', '\1');
    order_date := REGEXP_REPLACE(payload, '.*orderDate=([\d\-]+).*', '\1')::DATE;
    address := REGEXP_REPLACE(payload, '.*address=''([^'']*)''.*', '\1');
    store_name := REGEXP_REPLACE(payload, '.*storeName=''([^'']*)''.*', '\1');
    store_address := REGEXP_REPLACE(payload, '.*storeAddress=''([^'']*)''.*', '\1');
    sales_rep_name := REGEXP_REPLACE(payload, '.*salesRepName=''([^'']*)''.*', '\1');

    -- Insert or update the salesorders_read table
    INSERT INTO public.salesorders_read (
        order_id, product, price, quantity, ship_to, payment_method,
        order_date, address, store_name, store_address, sales_rep_name
    ) VALUES (
        order_id, product, price, quantity, ship_to, payment_method,
        order_date, address, store_name, store_address, sales_rep_name
    )
    ON CONFLICT ((salesorders_read.order_id)) DO UPDATE SET -- Corrected ON CONFLICT syntax
        product = EXCLUDED.product,
        price = EXCLUDED.price,
        quantity = EXCLUDED.quantity,
        ship_to = EXCLUDED.ship_to,
        payment_method = EXCLUDED.payment_method,
        order_date = EXCLUDED.order_date,
        address = EXCLUDED.address,
        store_name = EXCLUDED.store_name,
        store_address = EXCLUDED.store_address,
        sales_rep_name = EXCLUDED.sales_rep_name;

EXCEPTION
    WHEN others THEN
        RAISE NOTICE 'Error processing order %: %', order_id, SQLERRM;
END;
$$;

CREATE OR REPLACE FUNCTION public.sales_order_trigger_func()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    -- Call the stored procedure to process the order data
    CALL public.process_sales_order(NEW.order_id, NEW.payload);
    RETURN NEW;
END;
$$;


CREATE OR REPLACE TRIGGER sales_order_update_trigger
AFTER INSERT OR UPDATE ON public.salesorders
FOR EACH ROW
EXECUTE FUNCTION public.sales_order_trigger_func();

DROP TABLE public.salesorders_fraud;

CREATE TABLE IF NOT EXISTS public.salesorders_fraud
(
    order_id integer,
    product character varying(255) COLLATE pg_catalog."default",
    price numeric(10,2),
    quantity integer,
    ship_to character varying(50) COLLATE pg_catalog."default",
    payment_method character varying(50) COLLATE pg_catalog."default",
    order_date date,
    address character varying(50) COLLATE pg_catalog."default",
    store_name character varying(50) COLLATE pg_catalog."default",
    store_address character varying(50) COLLATE pg_catalog."default",
    sales_rep_name character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT salesorders_fraud_pkey PRIMARY KEY (order_id)
);


-- Create the stored procedure to process fraud orders
CREATE OR REPLACE PROCEDURE public.process_fraud_order(order_id INT, product VARCHAR(255), price NUMERIC(10, 2), quantity INT, ship_to VARCHAR(50), payment_method VARCHAR(50), order_date DATE, address VARCHAR(50), store_name VARCHAR(50), store_address VARCHAR(50), sales_rep_name VARCHAR(50))
LANGUAGE plpgsql
AS $$
BEGIN
    -- Insert or update the salesorders_fraud table if payment method is not 'cash'
    IF payment_method != 'cash' THEN
        INSERT INTO public.salesorders_fraud (
            order_id, product, price, quantity, ship_to, payment_method,
            order_date, address, store_name, store_address, sales_rep_name
        ) VALUES (
            order_id, product, price, quantity, ship_to, payment_method,
            order_date, address, store_name, store_address, sales_rep_name
        )
        ON CONFLICT (order_id) DO UPDATE SET
            product = EXCLUDED.product,
            price = EXCLUDED.price,
            quantity = EXCLUDED.quantity,
            ship_to = EXCLUDED.ship_to,
            payment_method = EXCLUDED.payment_method,
            order_date = EXCLUDED.order_date,
            address = EXCLUDED.address,
            store_name = EXCLUDED.store_name,
            store_address = EXCLUDED.store_address,
            sales_rep_name = EXCLUDED.sales_rep_name;
    END IF;
END;
$$;

-- Create the trigger function to call the stored procedure
CREATE OR REPLACE FUNCTION public.sales_order_fraud_trigger_func()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    -- Call the stored procedure to process the fraud order data
    CALL public.process_fraud_order(
        NEW.order_id, NEW.product, NEW.price, NEW.quantity, NEW.ship_to,
        NEW.payment_method, NEW.order_date, NEW.address, NEW.store_name,
        NEW.store_address, NEW.sales_rep_name
    );
    RETURN NEW;
END;
$$;

-- Create the trigger on the salesorders_read table
CREATE OR REPLACE TRIGGER sales_order_fraud_trigger
AFTER INSERT OR UPDATE ON public.salesorders_read
FOR EACH ROW
EXECUTE FUNCTION public.sales_order_fraud_trigger_func();




-- drop table salesorders;
-- drop table salesorders_read cascade;
-- drop table salesorders_fraud cascade;
-- DROP TABLE public.salesorders_read;



-- delete from salesorders;
-- delete from salesorders_read;

-- SELECT count(*) FROM public.salesorders;
-- SELECT count(*) FROM public.salesorders_read;
-- SELECT count(*) FROM public.salesorders_fraud;

-- SELECT * FROM public.salesorders_read WHERE payment_method != 'cash';


-- SELECT * FROM public.salesorders_fraud
-- ORDER BY order_id ASC LIMIT 100
