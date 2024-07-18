1. Обзор архитектуры системы
   Компоненты системы:

   `Test Application: Имитирует рабочую среду, генерирует метрики.
   Producer Service: Собирает метрики и отправляет их в Kafka.
   Consumer Service: Принимает метрики из Kafka, обрабатывает их и предоставляет через REST API.`

Связь между компонентами:

    Метрики собираются в Test Application и передаются в Producer Service через HTTP.
    Producer Service сериализует метрики в JSON и отправляет в Kafka Topic.
    Consumer Service читает сообщения из Kafka Topic, обрабатывает их и сохраняет во внутреннем хранилище для доступа через REST API.

2. Конфигурация Kafka
   Настройки Producer:

   `Bootstrap Servers: localhost:9092
   Key Serializer: StringSerializer
   Value Serializer: StringSerializer
`
Настройки Consumer:

   ` Bootstrap Servers: localhost:9092
    Group ID: group_id
    Key Deserializer: StringDeserializer
    Value Deserializer: JsonDeserializer`

Topic:

    Name: metrics-topic
    Partitions: Рекомендуется установить несколько партиций для масштабируемости и надежности.

3. Инструкции по запуску системы
   Запуск Kafka:

   Установите и настройте Kafka и Zookeeper.
   Запустите Zookeeper:

   `bash`

`zookeeper-server-start.sh config/zookeeper.properties`

Запустите Kafka:

bash

    kafka-server-start.sh config/server.properties

Запуск компонентов:

    Test Application:

    bash

`cd test-application
mvn spring-boot:run`

Producer Service:

`bash

cd producer-service
mvn spring-boot:run`

Consumer Service:

bash

    cd consumer-service
    mvn spring-boot:run

4. Использование системы
   Доступ к REST API:

   `Получение всех метрик: GET http://localhost:8085/metrics
   Получение конкретной метрики: GET http://localhost:8085/metrics/{id}`

Отправка метрик через Producer Service:

    POST http://localhost:8084/metrics

    json

    {
      "metricName": "example_metric",
      "value": 100
    }

5. Мониторинг и логирование

Описать настройки логирования для каждого из сервисов, включая уровни логирования и пути сохранения лог-файлов.
6. Техническая поддержка

Контактные данные для обращения в случае возникновения проблем с системой, а также руководство по устранению распространенных ошибок.