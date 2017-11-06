CREATE TABLE stock_quote (
	data timestamp,
	codbdi varchar,
	codneg varchar,
	tpmerc varchar,
	nomres varchar,
	especi varchar,
	prazot decimal,
	modref varchar,
	preabe decimal,
	premax decimal,
	premin decimal,
	premed decimal,
	preult decimal,
	preofc decimal,
	preofv decimal,
	totneg integer,
	quatot bigint,
	voltot decimal,
	preexe decimal,
	indopc integer,
	datven timestamp,
	fatcot integer,
	ptoexe decimal,
	codisi varchar,
	dismes integer
);	


ALTER TABLE stock_quote ADD PRIMARY KEY (data, codneg);

CREATE INDEX stock_quote_codneg_ix ON stock_quote (codneg);

CREATE INDEX stock_quote_upper_codneg_ix ON stock_quote (UPPER(codneg));

CREATE INDEX stock_quote_upper_trim_codneg_ix ON stock_quote (UPPER(TRIM(codneg)));

-- dolar
CREATE TABLE dolar_quote (
	data timestamp,
	precomedio decimal
);

ALTER TABLE dolar_quote ADD PRIMARY KEY (data, precomedio);

ALTER TABLE stock_quote ADD COLUMN adjclose decimal;


CREATE TABLE stock_fundamentals (
	data timestamp,
	symbol varchar,
	name varchar,
	p_l_ratio decimal,	
	p_vp_ratio decimal,
	price_sales_ratio decimal,
	divyield decimal,
	price_ativos_ratio decimal,
	price_capgiro_ratio decimal,
	price_ebit_ratio decimal,
	price_ativo_circ_liq_ratio decimal,
	ev_ebit_ratio decimal,
	mrg_ebit decimal,
	mgr_liq decimal,
	liquidez_corr decimal,
	roic decimal,
	roe decimal,
	liq_2_meses decimal,
	patr_liq decimal,
	divbruta_patrimonio_ratio decimal,
	cresc_receita_ult_5_anos decimal
);

ALTER TABLE stock_fundamentals ADD PRIMARY KEY (data, symbol);

CREATE TABLE yearly_fundamentals (
	ano integer,
	codneg varchar,
	patrimonio_liquido decimal,
	receita_liquida decimal,
	lucro_liquido decimal,
	margem_liquida decimal,
	roe decimal,
	caixa decimal,
	caixa_liquido decimal,
	divida decimal,
	divida_pl_ratio decimal,
	divida_lucro_liquido_ratio decimal
);

ALTER TABLE yearly_fundamentals ADD PRIMARY KEY (ano, codneg);