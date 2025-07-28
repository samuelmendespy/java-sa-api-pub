# sa-api-pub


## Tecnologias
- Kotlin 1.9.25
- Spring Boot 3.5.4
- RabbitMQ
- PostgreSQL 14

## Padrões
- Data e hora : String com formato ISO 8601 com fuso horário UTC -03. America/Fortaleza
- Conjunto de caracteres padrão : UTF-8
- Credenciais armazenadas no arquivo .env

## purchase-transactions
Recebe registros de compras em XML vindas de um dispositivo POS(Ponto de venda) e envia para a fila purchases.new.queue

### Enviar compra

```
POST /api/v1/purchase
```

Exemplo de XML enviado pelo POS
```XML
<Purchase>
    <id>AAAA-0000-0000-0000</id>
    <numberNota>12345</numberNota>
    <valorCompra>99.50</valorCompra>
    <cnpjLoja>00.000.000/0001-00</cnpjLoja>
    <machineReference>123-TEST-123</machineReference>
    <purchaseTime>2025-07-28T10:00:00-03:00</purchaseTime>
    <custommerCpf>123.456.789-00</custommerCpf>
</Purchase>
```

## account-manager
Processa compras e controla recursos.