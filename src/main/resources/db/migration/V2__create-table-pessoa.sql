CREATE TABLE pessoa (
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    ativo tinyint(1) NOT NULL DEFAULT 1,
    logradouro varchar(50),
    numero varchar(20),
    complemento varchar(40),
    bairro varchar(50),
    cep varchar(8),
    cidade varchar(50),
    estado varchar(2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;