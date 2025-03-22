

cdc-fruad=cdc-debezium --cdc.name=postgres-connector --cdc.config.database.dbname=postgres --connector=postgres --cdc.config.database.server.name=my-app-connector --cdc.config.database.user=postgres --cdc.config.database.password=postgres --cdc.config.database.hostname=localhost --cdc.config.database.port=5432 --cdc.flattening.enabled="true" --cdc.config.schema.include.list=public --cdc.config.table.include.list="public.salesorders_read" | geode --host-addresses=localhost:10334 --region-name=orders --key-expression="payload.getField('order_id')" --json="true"

