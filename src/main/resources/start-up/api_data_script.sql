--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: task_owner
--

CREATE TABLE public.categories (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.categories OWNER TO task_owner;

--
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: task_owner
--

CREATE SEQUENCE public.categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categories_id_seq OWNER TO task_owner;

--
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: task_owner
--

ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


--
-- Name: tasks; Type: TABLE; Schema: public; Owner: task_owner
--

CREATE TABLE public.tasks (
    due_date date,
    category_id bigint,
    taskid bigint NOT NULL,
    user_id bigint,
    description character varying(255),
    priority character varying(255),
    status character varying(255),
    title character varying(255),
    CONSTRAINT tasks_priority_check CHECK (((priority)::text = ANY ((ARRAY['HIGH'::character varying, 'MEDIUM'::character varying, 'LOW'::character varying])::text[]))),
    CONSTRAINT tasks_status_check CHECK (((status)::text = ANY ((ARRAY['OPEN'::character varying, 'ONGOING'::character varying, 'COMPLETED'::character varying, 'OVERDUE'::character varying])::text[])))
);


ALTER TABLE public.tasks OWNER TO task_owner;

--
-- Name: tasks_taskid_seq; Type: SEQUENCE; Schema: public; Owner: task_owner
--

CREATE SEQUENCE public.tasks_taskid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tasks_taskid_seq OWNER TO task_owner;

--
-- Name: tasks_taskid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: task_owner
--

ALTER SEQUENCE public.tasks_taskid_seq OWNED BY public.tasks.taskid;


--
-- Name: users; Type: TABLE; Schema: public; Owner: task_owner
--

CREATE TABLE public.users (
    created_date timestamp(6) without time zone,
    id bigint NOT NULL,
    email character varying(255),
    username character varying(255)
);


ALTER TABLE public.users OWNER TO task_owner;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: task_owner
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO task_owner;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: task_owner
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: task_owner
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- Name: tasks taskid; Type: DEFAULT; Schema: public; Owner: task_owner
--

ALTER TABLE ONLY public.tasks ALTER COLUMN taskid SET DEFAULT nextval('public.tasks_taskid_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: task_owner
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: task_owner
--

COPY public.categories (id, name) FROM stdin;
1	Socializing
2	Hobbies
3	Work
4	Coding
\.


--
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: task_owner
--

COPY public.tasks (due_date, category_id, taskid, user_id, description, priority, status, title) FROM stdin;
2023-06-21	4	1	2	use Docker and write start up scripts	HIGH	OPEN	finish building api
2023-06-23	1	2	2	art exhibition at National Gallery	LOW	OPEN	visit art exhibition
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: task_owner
--

COPY public.users (created_date, id, email, username) FROM stdin;
2023-06-09 19:06:26.839183	1	nicole.sar.93@gmail.com	nicolesar
2023-06-09 19:06:26.890961	2	example@example.com	example
\.


--
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: task_owner
--

SELECT pg_catalog.setval('public.categories_id_seq', 4, true);


--
-- Name: tasks_taskid_seq; Type: SEQUENCE SET; Schema: public; Owner: task_owner
--

SELECT pg_catalog.setval('public.tasks_taskid_seq', 2, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: task_owner
--

SELECT pg_catalog.setval('public.users_id_seq', 2, true);


--
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: task_owner
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: tasks tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: task_owner
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (taskid);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: task_owner
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: tasks fk19nbrmutuqhlidppwns1wmfro; Type: FK CONSTRAINT; Schema: public; Owner: task_owner
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT fk19nbrmutuqhlidppwns1wmfro FOREIGN KEY (category_id) REFERENCES public.categories(id);


--
-- Name: tasks fk6s1ob9k4ihi75xbxe2w0ylsdh; Type: FK CONSTRAINT; Schema: public; Owner: task_owner
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT fk6s1ob9k4ihi75xbxe2w0ylsdh FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

