
create database admin;
create database finance;
create database hr_dept;
create database technical;


CREATE SEQUENCE IF NOT EXISTS public.employee_seq
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.employee(
    emp_id bigint NOT NULL,
    tenant_id bigint NOT NULL,
    emp_name character varying(255) COLLATE pg_catalog."default",
    emp_salary double precision,
    dept_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT employee_pkey PRIMARY KEY (emp_id)
);