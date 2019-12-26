CREATE TABLE usuario(
	codigo BIGINT(20) PRIMARY KEY,
	nome VARCHAR (50) NOT NULL,
	email VARCHAR (50) NOT NULL,
	senha VARCHAR (150) NOT NULL
) ENGINE= InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao(
	codigo BIGINT(20) PRIMARY KEY NOT NULL, 
	descricao VARCHAR (50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permissao(
	codigo_usuario BIGINT(20),
	codigo_permissao BIGINT(20),
	PRIMARY KEY (codigo_usuario, codigo_permissao),	
	foreign key (codigo_usuario) references usuario(codigo),
	foreign key (codigo_permissao) REFERENCES permissao(codigo)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



insert into usuario values (1,'Administrador','administrador@rxl.com.br','');
insert into usuario values (2,'Robson Xavier','','');

insert into permissao values (1,'ROLE_CADASTRAR_CATEGORIA');
insert into permissao values (2,'ROLE_PESQUISAR_CATEGORIA');

insert into permissao values (3,'ROLE_CADASTRAR_PESSOA');
insert into permissao values (4,'ROLE_PESQUISAR_PESSOA');
insert into permissao values (5,'ROLE_REMOVER_PESSOA');

insert into permissao values (6,'ROLE_CADASTRAR_LANCAMENTO');
insert into permissao values (7,'ROLE_PESQUISAR_LANCAMENTO');
insert into permissao values (8,'ROLE_REMOVER_LANCAMENTO');

insert into usuario_permissao values (1,1);
insert into usuario_permissao values (1,2);
insert into usuario_permissao values (1,3);	
insert into usuario_permissao values (1,4);
insert into usuario_permissao values (1,5);
insert into usuario_permissao values (1,6);
insert into usuario_permissao values (1,7);
insert into usuario_permissao values (1,8);

insert into usuario_permissao values (2,2);
insert into usuario_permissao values (2,5);
insert into usuario_permissao values (2,8);

