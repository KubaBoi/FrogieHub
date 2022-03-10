sudo apt-get update
sudo apt-get install postgresql postgresql-contrib
sudo -u postgres psql
alter user postgres PASSWORD 'admin';
CREATE DATABASE frogie_database;
\q
sudo -u postgres psql frogie_database

BEGIN;


CREATE TABLE IF NOT EXISTS public.chats
(
    id bigint NOT NULL,
    last_activity bigint NOT NULL,
    chat_name text NOT NULL,
    picture_id bigint,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.chats_t
(
    user_id bigint NOT NULL,
    chat_id bigint NOT NULL,
    last_delivered_message_id bigint,
    last_seen_message_id bigint,
    id bigint NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.groups
(
    id bigint NOT NULL,
    group_name text NOT NULL,
    chat_id bigint NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.groups_t
(
    user_id bigint NOT NULL,
    group_id bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS public.messages
(
    id bigint NOT NULL,
    author_id bigint NOT NULL,
    content text NOT NULL,
    chat_id bigint NOT NULL,
    time_stamp bigint NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.passwords
(
    user_id bigint NOT NULL,
    password text NOT NULL,
    duration bigint NOT NULL,
    id bigint NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.services
(
    id bigint NOT NULL,
    name text NOT NULL,
    port bigint NOT NULL,
    icon text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.tokens
(
    token text NOT NULL,
    user_id bigint NOT NULL,
    ip text NOT NULL,
    end_time bigint NOT NULL,
    id bigint NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.users
(
    email text NOT NULL,
    picture_id bigint NOT NULL,
    id bigint NOT NULL,
    user_name text NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE public.chats_t
    ADD FOREIGN KEY (chat_id)
    REFERENCES public.chats (id)
    NOT VALID;


ALTER TABLE public.chats_t
    ADD FOREIGN KEY (last_delivered_message_id)
    REFERENCES public.messages (id)
    NOT VALID;


ALTER TABLE public.chats_t
    ADD FOREIGN KEY (last_seen_message_id)
    REFERENCES public.messages (id)
    NOT VALID;


ALTER TABLE public.chats_t
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (id)
    NOT VALID;


ALTER TABLE public.groups
    ADD FOREIGN KEY (chat_id)
    REFERENCES public.chats (id)
    NOT VALID;


ALTER TABLE public.groups_t
    ADD FOREIGN KEY (group_id)
    REFERENCES public.groups (id)
    NOT VALID;


ALTER TABLE public.groups_t
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (id)
    NOT VALID;


ALTER TABLE public.messages
    ADD FOREIGN KEY (chat_id)
    REFERENCES public.chats (id)
    NOT VALID;


ALTER TABLE public.passwords
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (id)
    NOT VALID;


ALTER TABLE public.tokens
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (id)
    NOT VALID;

END;

sudo apt install libpq-dev python3-dev python3-pip unixodbc-dev
pip install psycopg2-binary
pip install psycopg2
pip install pyodbc

sudo crontab -e
@reboot python3 /home/Cloudos/server.py &
sudo ufw allow from any to any port 8000 proto tcp