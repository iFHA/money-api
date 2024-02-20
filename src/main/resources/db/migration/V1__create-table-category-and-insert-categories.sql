CREATE TABLE categoria (
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (nome)
values 
('Lazer'),
('Alimentação'),
('Supermercado'),
('Farmácia'),
('Outros');