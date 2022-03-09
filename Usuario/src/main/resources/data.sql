INSERT INTO PERFIL (nome) VALUES ('ROLE_USUARIO');
INSERT INTO USER(email, senha) VALUES('user@email.com', '$2a$10$fCgVs3Asfej.65RIsBmQ6.9eFUIKqBAOz/rkjAZleVgzp2bIViIum');
INSERT INTO USER_PERFIS (user_id, perfis_id) VALUES (1,1);

INSERT INTO PERFIL (nome) VALUES ('ROLE_BIBLIOTECA');
INSERT INTO USER(email, senha) VALUES('biblioteca@email.com', '$2a$10$fCgVs3Asfej.65RIsBmQ6.9eFUIKqBAOz/rkjAZleVgzp2bIViIum');
INSERT INTO USER_PERFIS (user_id, perfis_id) VALUES (2,2);
