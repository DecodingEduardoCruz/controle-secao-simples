CREATE TABLE IF NOT EXISTS public.token
(
    id bigint NOT NULL,
    data_cadastro timestamp(6) without time zone,
    data_inativacao timestamp(6) without time zone,
    expirado boolean,
    renovado boolean,
    token character varying(255) COLLATE pg_catalog."default",
    token_type character varying(255) COLLATE pg_catalog."default",
    usuario_id bigint,
    CONSTRAINT token_pkey PRIMARY KEY (id),
    CONSTRAINT uk_pddrhgwxnms2aceeku9s2ewy5 UNIQUE (token),
    CONSTRAINT fkt4bhka4qm30nvjerga8pqnm1m FOREIGN KEY (usuario_id)
        REFERENCES public._usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.token
    OWNER to postgres;