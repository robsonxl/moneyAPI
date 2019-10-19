CREATE TABLE pessoa(
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome   VARCHAR(50) NOT NULL,
    ativo  BOOLEAN NOT NULL,
    logradouro VARCHAR(70) NOT NULL,
    numero INT NOT NULL,
    complemento VARCHAR(50),
    bairro VARCHAR(70) NOT NULL,
    cep VARCHAR(8) NOT NULL,
    cidade  VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL
) ENGINE=Innodb DEFAULT CHARSET=utf8;


INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,estado,cidade) values 
(
    'Jose Dos santos Silva',
	TRUE,
    'Rua estados unidos',
    123,
    'proximo ao Mexico',
    'wasihngton',
    01234567,
    'Minas Gerais',
    'Minas Gerais'
);

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,estado,cidade) values 
(
    'Manoel Silva',
	TRUE,
    'Rua Holanda',
    123,
    'Europa',
    'holanda',
    01234567,
    'Santa Catarina',
    'Santa Catarina'
);

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,estado,cidade) values 
(
    'Josue',
	TRUE,
    'Rua quarenta e quatro',
    123,
    'proximo proxima rua quarenta',
    'numeros',
    01234567,
    'São Paulo',
    'São Paulo'
);