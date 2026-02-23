CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL
);

INSERT INTO usuarios (login, clave)
VALUES ('admin@forohub.com', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bZscxa');