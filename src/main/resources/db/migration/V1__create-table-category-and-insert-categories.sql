CREATE TABLE category (
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO category (name)
values 
('Lazer'),
('Alimentação'),
('Supermercado'),
('Farmácia'),
('Outros');