	CREATE TABLE lancamento(
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    descricao   VARCHAR(50) NOT NULL,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    valor DECIMAL(10,2) NOT NULL,
    observacao VARCHAR(100),
    tipo VARCHAR(20) NOT NULL,
    codigo_categoria BIGINT(20) NOT NULL,
    codigo_pessoa BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
    FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
) ENGINE=Innodb DEFAULT CHARSET=utf8;


INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo,
                        codigo_categoria, codigo_pessoa) values(
                        'Salário Mensal','2017-06-10',null,6400,'Distribuição de lucros','RECEITA',1,1
                        );


INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo,
                        codigo_categoria, codigo_pessoa) values(
                        'Bahamas','2017-02-10','2017-02-10',100.32,null,'DESPESA',2,2
                        );