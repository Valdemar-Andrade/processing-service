# Serviço de Processamento

Parte de um **Simulador de Pipeline Industrial da Indústria 4.0** distribuído, construído com arquitetura de micro-serviços orientada a eventos.

---

## 🧠 Visão Geral do Sistema

Este projeto simula um pipeline de produção industrial real, onde serviços independentes colaboram para produzir bens.

Fluxo do pipeline:

Matéria-prima → Processamento → Produção de Componentes → Montagem do Produto

Cada etapa opera como um microsserviço isolado, comunicando através de eventos Kafka.

---

## 🎯 Função deste Serviço

O **Serviço de Processamento** é responsável pela transformação das matérias-primas em materiais industriais utilizáveis.

Atua como a segunda etapa do pipeline de produção, ligando a extração da matéria-prima ao fabrico de componentes.

Exemplos:
- Minério de ferro → Aço
- Areia → Vidro
- Látex → Borracha

---

## ⚙️ Responsabilidades

- Consumir eventos de matéria-prima do Kafka
- Aplicar pipelines de transformação às matérias-primas
- Validar regras de transformação e compatibilidade
- Persistir materiais processados
- Publicar eventos de materiais processados ​​para serviços subsequentes

---

## 🔄 Posição no Pipeline
[ Serviço de Matéria-Prima ] → [ Serviço de Processamento ] → [ Serviço de Componentes ] → [ Serviço de Montagem ]

---

## 📡 Comunicação Orientada a Eventos

### Eventos Consumidos

- `RAW_MATERIAL_EXTRACTED`

### Eventos Produzidos

- `MATERIAL_PROCESSED`

---

## 📦 Estrutura do Evento

### Evento de Entrada

```
{
"eventId": "uuid",

"eventType": "RAW_MATERIAL_EXTRACTED",

"timestamp": 1710000000,

"sourceService": "raw-material-service",

"targetService": "processing-service",

"payload": {

"name": "Iron Ore",

"quantidade": 10

}
}
```

### Evento de Saída

```
{
"eventId": "uuid",

"eventType": "MATERIAL_PROCESSED",

"timestamp": 1710000000,

"sourceService": "processing-service",

"targetService": "component-service",

"payload": {

"nome": "Steel",

"quantidade": 8

}
}
```

## ⏱️ Pipeline de Processamento (Simulação de Latência)

A transformação de materiais é realizada em etapas baseadas no tempo.

Exemplo: Produção de Aço
```
[
{ "name": "FUNDIÇÃO", "durationMs": 8000 },

{ "name": "REFINO", "durationMs": 6000 }
]
```

Isto garante atrasos realistas no processamento industrial.

POST /processing/start
```
{
"material": "Minério de Ferro",

"quantidade": 10
}
```

**Nota:** Em funcionamento normal, o processamento é automaticamente acionado através de eventos Kafka.

## 🔄 Fluxo Interno

Receber evento Kafka (RAW_MATERIAL_EXTRACTED)
Validar se o material pode ser processado
Executar pipeline de transformação (com atraso)
Converter matéria-prima em material processado
Persistir o resultado na base de dados
Publicar evento MATERIAL_PROCESSED

## 🗄️ Propriedade dos Dados

Este serviço segue os princípios da arquitetura de micro-serviços:

## Base de dados própria
- Sem acesso direto aos dados de outros serviços
- Comunicação estritamente via eventos Kafka

## 🧱 Tecnologias
- Java + Spring Boot
- Apache Kafka
- PostgreSQL
- Docker

## Executar o Serviço
- docker-compose up --build

## 🧠 Conceitos-chave Demonstrados

- Pipelines de processamento orientados a eventos
- Transformação de dados em sistemas distribuídos
- Desacoplamento de serviços através de comunicação assíncrona
- Simulação de latência em fluxos de trabalho industriais

## Outros Serviços:
- [raw-material-service](https://github.com/Valdemar-Andrade/raw-material-service.git)

## 👤 Autor
- GitHub: [@Valdemar-Andrade]
- LinkedIn: [Valdemar Andrade](https://www.linkedin.com/in/valdemar-andrade-8b0b45189)
