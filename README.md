##Необходимый софт
* Maven
* Docker
##Дополнительный софт для отладки
* Intellij IDEA Ultimate edition
* DBeaver или схожее приложение для подключения к базе данных
* Java 13+

##Запуск приложения для разработки и тестирования
1. Выполнить mvn clean package -DskipTests
2. Запустить все приложение через docker-compose -f docker-compose.yml либо создать конфигурацию в Inteliji IDEA
3. В терминале ввести последовательно команды. Терминал должен быть запущен от корневой папки репозитория:
```bash
docker-compose exec vault sh
vault kv put secret/config-service @vault/secrets/config-service.json
vault kv put secret/send-notifications-service @vault/secrets/send-notifications-service.json
vault kv put secret/display-service @vault/secrets/display-service.json
vault kv put secret/public-service @vault/secrets/public-service.json
vault kv put secret/private-service @vault/secrets/private-service.json
exit
```
4. Закрыть терминал

## Рекомендации
1. При создании записи приёма к врачу внимательно заполняйте значения полей пациента, так как производится поиск на 
   существование такого пациента в базе. Проверяются поля: Фамилия, Имя, Отчество, Полис, Дата рождения.
2. По умолчанию отключена рассылка уведомления о записи по email. Для включения установите значение переменной SEND-EMAIL в true.

##Просмотр созданных запросов
* JSON конфигурация OpenAPI:
    + http://localhost:8080/openapi.json - private-service
    + http://localhost:8081/openapi.json - public-service
    + http://localhost:8082/openapi.json - display-service
    + http://localhost:8083/openapi.json - send-notification-service


* HTML конфигурация OpenAPI:
  + http://localhost:8080/openapi.html - private-service
  + http://localhost:8081/openapi.html - public-service
  + http://localhost:8082/openapi.html - display-service
  + http://localhost:8083/openapi.html - send-notification-service

##База данных:
В базе данных при развёртывании создаются следующие данные (подробнее через запросы OpenAPI):
* Роли:
  + ROLE_ADMIN
  + ROLE_DOCTOR
  + ROLE_USER
* Специальности:
  + Стоматолог
  + Хирург
  + Офтальмолог
  + Терапевт
  + Оториноларинголог
* Пользователи (логин / пароль):
  + admin / admin
  + doctor1 / doctor1
  + doctor2 / doctor2
  + doctor3 / doctor3
  + doctor4 / doctor4
  + doctor5 / doctor5
* Врачи (id):
  + 27fbb8d4-686a-4099-87e1-cf7118f1f3eb
  + b2a07ca7-4f8d-4806-8a14-fc0835984f3f
  + 76390437-1843-4d46-95d2-c8efbbd0c45e
  + eb2bc39f-b9b7-4fce-8e09-bae6a4f41a5b
  + 392bf1e2-5ef1-4b7f-a2ff-02b62e5b0e29
* Пациенты (3 человека)
##Список сервисов и портов:
* PostgreSQL = 5432
* Kafka = 9092, 19092
* Zookeeper = 2181  
* config-service = 8888
* private-service = 8080
* public-service = 8081
* display-service = 8082
* send-notifications-service = 8083
