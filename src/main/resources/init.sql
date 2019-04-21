CREATE SCHEMA adv_ktor_login_server;

CREATE TABLE adv_ktor_login_server.users
(
    id serial PRIMARY KEY,
    email varchar(255) NOT NULL,
    password_hash TEXT NOT NULL,
    create_time BIGINT NOT NULL
);
CREATE UNIQUE INDEX users_email_uindex ON adv_ktor_login_server.users (email);

CREATE TABLE adv_ktor_login_server.updatedb
(
    version int DEFAULT 0 NOT NULL,
    update_time BIGINT DEFAULT 0 NOT NULL
);

CREATE TABLE adv_ktor_login_server.restore_password_codes
(
    user_id int NOT NULL,
    expire_time BIGINT NOT NULL,
    code VARCHAR(20) NOT NULL,
    CONSTRAINT restore_password_codes_users_id_fk FOREIGN KEY (user_id) REFERENCES adv_ktor_login_server.users (id) ON DELETE CASCADE
);

CREATE TABLE adv_ktor_login_server.sessions
(
    session VARCHAR(255) NOT NULL,
    user_id int NOT NULL,
    user_agent TEXT NOT NULL,
    create_time BIGINT NOT NULL,
    CONSTRAINT sessions_users_id_fk FOREIGN KEY (user_id) REFERENCES adv_ktor_login_server.users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX sessions_session_uindex ON adv_ktor_login_server.sessions (session);

ALTER TABLE adv_ktor_login_server.sessions ADD expired_reason_code int NULL;
ALTER TABLE adv_ktor_login_server.sessions ALTER COLUMN expired_reason_code SET DEFAULT 0;
ALTER TABLE adv_ktor_login_server.sessions ALTER COLUMN expired_reason_code SET NOT NULL;