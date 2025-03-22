SELECT * FROM public.salesorders_read
ORDER BY order_id ASC LIMIT 100

create extension system_stats;

drop table salesorders;
drop table salesorders_read cascade;
drop table salesorders_fraud cascade;

SELECT *
FROM salesorders a
INNER JOIN salesorders_read b ON a.order_id = b.order_id;

SELECT a.*
FROM salesorders a
LEFT JOIN salesorders_read b ON a.order_id = b.order_id
WHERE b.order_id IS NULL;

-- ### Working code

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
###########################################################################
delete from salesorders;
delete from salesorders_read;

SELECT count(*) FROM public.salesorders;
SELECT count(*) FROM public.salesorders_read;


lab1: jdbc  --password=password --username=admin --url="jdbc:postgresql://localhost:5432/postgres" --query="select order_id, product, price, quantity, ship_to, payment_method, order_date ,address, store_name,store_address, sales_rep_name from salesorders_read WHERE payment_method != 'cash';" --split=true | lab2: jdbc --password=password --username=admin --url="jdbc:postgresql://localhost:5432/postgres" --table-name=public.salesorders_fraud --split=true

Caused by: org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [INSERT INTO public.salesorders_fraud(payload) VALUES (?)]; nested exception is org.postgresql.util.PSQLException: ERROR: column "payload" of relation "salesorders_fraud" does not exist
  Position: 38

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


lab1: jdbc --password=password --username=admin --url="jdbc:postgresql://localhost:5432/postgres" --query="SELECT order_id, product, price, quantity, ship_to, payment_method, order_date, address, store_name, store_address, sales_rep_name FROM salesorders_read WHERE payment_method != 'cash';" --split=true | lab2: jdbc --password=password --username=admin --url="jdbc:postgresql://localhost:5432/postgres" --table-name=public.salesorders_fraud --columns="order_id,product,price,quantity,ship_to,payment_method,order_date,address,store_name,store_address,sales_rep_name" --split=true

lab1: jdbc --password=password --username=admin --url="jdbc:postgresql://localhost:5432/postgres" --query="SELECT order_id, product, price, quantity, ship_to, payment_method, order_date::date, address, store_name, store_address, sales_rep_name FROM salesorders_read WHERE payment_method != 'cash';" --split=true | lab2: jdbc --password=password --username=admin --url="jdbc:postgresql://localhost:5432/postgres" --table-name=public.salesorders_fraud --columns="order_id,product,price,quantity,ship_to,payment_method,order_date,address,store_name,store_address,sales_rep_name" --split=true


dataflow:> stream create --name fraud-pipeline --definition "lab1: jdbc --password=password --username=admin --url='jdbc:postgresql://localhost:5432/postgres' --query='SELECT order_id, product, price, quantity, ship_to, payment_method, order_date::date AS order_date, address, store_name, store_address, sales_rep_name FROM salesorders_read WHERE payment_method != ''cash'' and product ilike ''%ring%'' or product  = ''diamonds'';' --split=true | lab2: jdbc --password=password --username=admin --url='jdbc:postgresql://localhost:5432/postgres' --table-name=public.salesorders_fraud --columns='order_id,product,price,quantity,ship_to,payment_method, order_date,address,store_name,store_address,sales_rep_name' --split=true" --deploy


lab1: jdbc --password=password --username=admin --url='jdbc:postgresql://localhost:5432/postgres' --query='SELECT order_id, product, price, quantity, ship_to, payment_method, order_date::date AS order_date, address, store_name, store_address, sales_rep_name FROM salesorders_read WHERE payment_method != ''cash'' and product ilike ''%ring%'' or product  = ''diamonds'';' --split=true | lab2: jdbc --password=password --username=admin --url='jdbc:postgresql://localhost:5432/postgres' --table-name=public.salesorders_fraud --query='INSERT INTO public.salesorders_fraud(order_id, product, price, quantity, ship_to, payment_method, order_date, address, store_name, store_address, sales_rep_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);' --columns='order_id,product,price,quantity,ship_to,payment_method, order_date,address,store_name,store_address,sales_rep_name' --split=true

Caused by: org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [INSERT INTO public.salesorders_fraud(order_id, product, price, quantity, ship_to, payment_method, order_date, address, store_name, store_address, sales_rep_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)]; nested exception is org.postgresql.util.PSQLException: ERROR: column "order_date" is of type date but expression is of type character varying
  

INSERT INTO public.salesorders_fraud(order_id, product, price, quantity, ship_to, payment_method, order_date, address, store_name, store_address, sales_rep_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);



lab1: jdbc --password=password --username=admin --url='jdbc:postgresql://localhost:5432/postgres' --query='SELECT order_id, product, price, quantity, ship_to, payment_method, order_date::date AS order_date, address, store_name, store_address, sales_rep_name FROM salesorders_read WHERE payment_method != ''cash'' and (product ilike ''%ring%'' or product = ''diamonds'');' --split=true | lab2: jdbc --password=password --username=admin --url='jdbc:postgresql://localhost:5432/postgres' --query='INSERT INTO public.salesorders_fraud(order_id, product, price, quantity, ship_to, payment_method, order_date, address, store_name, store_address, sales_rep_name) VALUES (?, ?, ?, ?, ?, ?, ?::date, ?, ?, ?, ?);' --columns='order_id,product,price,quantity,ship_to,payment_method,order_date,address,store_name,store_address,sales_rep_name' --split=true


lab1: jdbc --password=password --username=admin --url='jdbc:postgresql://localhost:5432/postgres' --query='SELECT order_id, product, price, quantity, ship_to, payment_method, order_date::date AS order_date, address, store_name, store_address, sales_rep_name FROM salesorders_read WHERE payment_method != ''cash'' and (product ilike ''%ring%'' or product = ''diamonds'');' --split=true | lab2: jdbc --password=password --username=admin --url='jdbc:postgresql://localhost:5432/postgres' --query='INSERT INTO public.salesorders_fraud(order_id, product, price, quantity, ship_to, payment_method, order_date, address, store_name, store_address, sales_rep_name) VALUES (?, ?, ?, ?, ?, ?, ?::date, ?, ?, ?, ?);' --columns='order_id,product,price,quantity,ship_to,payment_method,order_date,address,store_name,store_address,sales_rep_name' --split=true


lab1: jdbc --password=password --username=admin --url='jdbc:postgresql://localhost:5432/postgres' --query='SELECT order_id, product, price, quantity, ship_to, payment_method, order_date::date AS order_date, address, store_name, store_address, sales_rep_name FROM salesorders_read WHERE payment_method != ''cash'' and (product ilike ''%ring%'' or product = ''diamonds'');'  | lab2: jdbc --password=password --username=admin --url='jdbc:postgresql://localhost:5432/postgres' --query='INSERT INTO public.salesorders_fraud(order_id, product, price, quantity, ship_to, payment_method, order_date, address, store_name, store_address, sales_rep_name) VALUES (?, ?, ?, ?, ?, ?, ?::date, ?, ?, ?, ?);' --table-name=public.salesorders_fraud


Caused by: org.springframework.jdbc.BadSqlGrammarException: PreparedStatementCallback; bad SQL grammar [INSERT INTO public.salesorders_fraud(order_id, product, price, quantity, ship_to, payment_method, order_date, address, store_name, store_address, sales_rep_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)]; nested exception is org.postgresql.util.PSQLException: ERROR: column "order_date" is of type date but expression is of type character varying
 


dataflow:> stream create --name fraud-pipeline --definition "lab1: jdbc --password=postgres --username=postgres --url='jdbc:postgresql://localhost:5432/postgres' --query='SELECT order_id, product, price, quantity, ship_to, payment_method, order_date::date AS order_date, address, store_name, store_address, sales_rep_name FROM salesorders_read WHERE payment_method != ''cash'' and (product ilike ''%ring%'' or product = ''diamonds'');'  | lab2: jdbc --password=postgres --username=postgres --url='jdbc:postgresql://localhost:5432/postgres' --query='INSERT INTO public.salesorders_fraud(order_id, product, price, quantity, ship_to, payment_method, order_date, address, store_name, store_address, sales_rep_name) VALUES (?, ?, ?, ?, ?, ?, TO_DATE(?, ''YYYY-MM-DD''), ?, ?, ?, ?);' --columns='order_id,product,price,quantity,ship_to,payment_method,order_date,address,store_name,store_address,sales_rep_name' --table-name=public.salesorders_fraud" --deploy



cdc.config.debezium.source.database.hostname=localhost,cdc.config.debezium.source.database.port=5432,cdc.config.debezium.source.database.user=admin,cdc.config.debezium.source.database.password=password,cdc.config.debezium.source.database.dbname=postgres,cdc.config.debezium.source.table.include.list=public.customers

cdc-debezium --name=demo --config="cdc.config.debezium.source.database.hostname=localhost,cdc.config.debezium.source.database.port=5432,cdc.config.debezium.source.database.user=admin,cdc.config.debezium.source.database.password=password,cdc.config.debezium.source.database.dbname=postgres,cdc.config.debezium.source.table.include.list=public.salesorders_read" | jdbc --password=password --username=admin --url="jdbc:postgresql://localhost:5432/postgres" --table-name=public.salesorders_read
 --connector=io.debezium.connector.postgresql.PostgresConnector" | jdbc --password=password --username=admin --url="jdbc:postgresql://localhost:5432/postgres" --columns=order_id,product,price,quantity,ship_to,payment_method,order_date,address,store_name,store_address,sales_rep_name --table-name=public.salesorders_fraud



 cdc-debezium --name=demo --config="cdc.config.debezium.source.database.hostname=localhost,cdc.config.debezium.source.database.port=5432,cdc.config.debezium.source.database.user=admin,cdc.config.debezium.source.database.password=password,cdc.config.debezium.source.database.dbname=postgres,cdc.config.debezium.source.table.include.list=public.salesorders_read" | jdbc --password=password --username=admin --url="jdbc:postgresql://localhost:5432/postgres" --connector=io.debezium.connector.postgresql.PostgresConnector --columns=order_id,product,price,quantity,ship_to,payment_method,order_date,address,store_name,store_address,sales_rep_name --table-name=public.salesorders_fraud

  cdc-debezium --name=demo --config="cdc.config.database.user=admin,cdc.config.database.password=password,cdc.config.database.hostname=localhost,cdc.config.database.port=5432,cdc.config.table.include.list=public.salesorders_read" | jdbc --password=password --username=admin --url="jdbc:postgresql://localhost:5432/postgres" --connector=io.debezium.connector.postgresql.PostgresConnector --columns=order_id,product,price,quantity,ship_to,payment_method,order_date,address,store_name,store_address,sales_rep_name --table-name=public.salesorders_fraud

debezium/example-postgres:2.3.3.Final

lab1: jdbc --password=postgres --username=postgres --url='jdbc:postgresql://localhost:5432/postgres' --query='SELECT order_id, product, price, quantity, ship_to, payment_method, order_date::date AS order_date, address, store_name, store_address, sales_rep_name FROM salesorders_read WHERE payment_method != ''cash'' and (product ilike ''%ring%'' or product = ''diamonds'');'  | lab2: jdbc --password=postgres --username=postgres --url='jdbc:postgresql://localhost:5432/postgres' --query='INSERT INTO public.salesorders_fraud(order_id, product, price, quantity, ship_to, payment_method, order_date, address, store_name, store_address, sales_rep_name) VALUES (?, ?, ?, ?, ?, ?, TO_DATE(?, ''YYYY-MM-DD''), ?, ?, ?, ?);' --columns='order_id,product,price,quantity,ship_to,payment_method,order_date,address,store_name,store_address,sales_rep_name' --table-name=public.salesorders_fraud


cdc-fruad=cdc-debezium --cdc.name=postgres-connector --cdc.config.database.dbname=postgres --connector=postgres --cdc.config.database.server.name=my-app-connector --cdc.config.database.user=postgres --cdc.config.database.password=postgres --cdc.config.database.hostname=localhost --cdc.config.database.port=5432 --cdc.flattening.enabled="true" --cdc.config.schema.include.list=public --cdc.config.table.include.list="public.salesorders_read" | jdbc --password=postgres --username=postgres --url='jdbc:postgresql://localhost:5432/postgres' --query='INSERT INTO public.salesorders_fraud(order_id, product, price, quantity, ship_to, payment_method, order_date, address, store_name, store_address, sales_rep_name) VALUES (?, ?, CAST(? AS NUMERIC), ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?);' --columns='order_id,product,price,quantity,ship_to,payment_method,order_date,address,store_name,store_address,sales_rep_name' --table-name=public.salesorders_fraud | geode --host-addresses=localhost:10334 --region-name=orders --key-expression="payload.getField('order_id')" --json="true"

cdc.connector=postgres

lab1: jdbc --password=postgres --username=postgres --url='jdbc:postgresql://localhost:5432/postgres' --query='SELECT order_id, product, price, quantity, ship_to, payment_method, order_date::date AS order_date, address, store_name, store_address, sales_rep_name FROM salesorders_read WHERE payment_method != ''cash'' and (product ilike ''%ring%'' or product = ''diamonds'');'  | lab2: jdbc --password=postgres --username=postgres --url='jdbc:postgresql://localhost:5432/postgres' --query='INSERT INTO public.salesorders_fraud(order_id, product, price, quantity, ship_to, payment_method, order_date, address, store_name, store_address, sales_rep_name) VALUES (?, ?, ?, ?, ?, ?, TO_DATE(?, ''YYYY-MM-DD''), ?, ?, ?, ?);' --columns='order_id,product,price,quantity,ship_to,payment_method,order_date,address,store_name,store_address,sales_rep_name' --table-name=public.salesorders_fraud | geode --host-addresses=localhost:10334 --region-name=orders --key-expression="payload.getField('order_id')" --json="true"


# DB access
cdc.config.database.user=admin,cdc.config.database.password=password,cdc.config.database.hostname=localhost,cdc.config.database.port=5432,cdc.config.table.include.list=public.salesorders_read

# DB source metadata
cdc.name=my-sql-connector
cdc.config.database.server.id=85744
cdc.config.database.server.name=my-app-connector


debezium/postgres


cdc-fruad=cdc-debezium --cdc.name=postgres-connector --cdc.config.database.dbname=postgres --connector=postgres --cdc.config.database.server.name=my-app-connector --cdc.config.database.user=postgres --cdc.config.database.password=postgres --cdc.config.database.hostname=localhost --cdc.config.database.port=5432 --cdc.flattening.enabled="true" --cdc.config.schema.include.list=public --cdc.config.table.include.list="public.salesorders_read" | geode --host-addresses=localhost:10334 --region-name=orders --key-expression="payload.getField('order_id')" --json="true"

cdc-stream=cdc-debezium --cdc.name=postgres-connector --cdc.config.database.dbname=postgres --connector=postgres --cdc.config.database.server.name=my-app-connector --cdc.config.database.user=postgres --cdc.config.database.password=postgres --cdc.config.database.hostname=localhost --cdc.config.database.port=5432 --cdc.flattening.enabled="true" --cdc.config.schema.include.list=inventory --cdc.config.table.include.list="inventory.customers" | geode --host-addresses=localhost:10334 --region-name=orders --key-expression="payload.getField('order_id')" --json="true"