# Task8: Microservices Project

Репозиторий содержит реализацию микросервисов для обработки клиентов, аккаунтов, транзакций и кредитов. В рамках задания были выполнены следующие шаги:

---

## 1️⃣ Покрытие Unit-тестами бизнес-логики

- Для каждого микросервиса (Client Processing, Account Processing, Credit Processing) были написаны **unit-тесты** для сервисных классов.
- Примеры покрытых классов:
  - `ClientProductService`
  - `ClientService`
  - `CardService`
  - `PaymentService`
  - `TransactionService`
  - `ProductService`
- Тесты проверяют:
  - Создание, обновление и удаление сущностей
  - Валидацию бизнес-логики
  - Обработку ошибок
  - Взаимодействие с репозиториями (mock-объекты)

---

## 2️⃣ Покрытие интеграционными тестами HTTP-запросов

- Разработаны **Integration Tests** для контроллеров всех микросервисов.
- Примеры тестируемых контроллеров:
  - `ClientController`  
  - `ClientProductController`  
  - `ProductController`  
  - `CardController`  
  - `PaymentController`  
  - `TransactionController`
- Используются:
  - `@SpringBootTest` + `@AutoConfigureMockMvc`
  - MockMvc для симуляции HTTP-запросов
  - Проверка статусов ответов (`200 OK`, `403 Forbidden` и др.)
  - Проверка JSON-ответов (например, `id`, `status`)

---

## 3️⃣ Разработка бизнес-метрик Prometheus

- Разработан сервис `ProductMetricsService`, который вычисляет количество **открытых продуктов** в системе:
  - Клиентские продукты: `DC`, `CC`, `NS`, `PENS`
  - Кредитные продукты: `IPO`, `PC`, `AC`
- Метрики:
  - `countClientProducts()` — количество активных клиентских продуктов
  - `countCreditProducts()` — количество активных кредитных продуктов
  - `countByType(ProductType type)` — количество активных продуктов по конкретному типу

---

## 4️⃣ Структура проекта

