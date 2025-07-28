CREATE TABLE IF NOT EXISTS accounts (
    id VARCHAR(255) PRIMARY KEY,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    points INT NOT NULL
);

CREATE TABLE IF NOT EXISTS customers (
    cpf VARCHAR(11) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    mobile_phone_number VARCHAR(11),
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS purchases (
    id VARCHAR(255) PRIMARY KEY,
    number_nota VARCHAR(255) NOT NULL,
    valor_compra VARCHAR(255) NOT NULL,
    cnpj_loja VARCHAR(14) NOT NULL,
    machine_reference VARCHAR(255) NOT NULL,
    purchase_time TIMESTAMP WITH TIME ZONE NOT NULL,
    customer_cpf VARCHAR(11) NOT NULL,
    CONSTRAINT fk_customer_cpf FOREIGN KEY (customer_cpf) REFERENCES customers(cpf)
);

CREATE INDEX idx_accounts_cpf ON accounts (cpf);
CREATE INDEX idx_customers_cpf ON customers (cpf);
CREATE INDEX idx_purchases_customer_cpf ON purchases (customer_cpf);