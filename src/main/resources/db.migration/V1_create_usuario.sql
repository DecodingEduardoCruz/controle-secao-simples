CREATE TABLE IF NOT EXISTS public._usuario
(
    id bigint NOT NULL,
    password character varying(255) COLLATE pg_catalog."default",
    role character varying(255) COLLATE pg_catalog."default",
    username character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT _usuario_pkey PRIMARY KEY (id),
    CONSTRAINT uk_ffgstb624utfjftsoquhg1jfg UNIQUE (username)
);

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public._usuario
    OWNER to postgres;