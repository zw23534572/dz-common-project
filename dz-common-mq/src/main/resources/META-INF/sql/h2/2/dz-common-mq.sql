ALTER TABLE dz_mq_producer ADD COLUMN attachment text NULL AFTER send_third;
ALTER TABLE dz_mq_consumer ADD COLUMN attachment text NULL AFTER body;