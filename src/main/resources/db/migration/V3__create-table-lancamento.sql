CREATE TABLE lancamento (
    id bigint(20) auto_increment primary key,
    descricao varchar(50) not null,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    valor decimal(15,2) NOT NULL,
    observacao VARCHAR(20),
    tipo VARCHAR(20) NOT NULL,
    categoria_id BIGINT(20) NOT NULL,
    pessoa_id BIGINT(20) NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id),
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;