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

##Просмотр созданных запросов
* JSON конфигурация OpenAPI:
    + http://localhost:8080/openapi.json - private-service
    + http://localhost:8080/openapi.json - public-service
    + http://localhost:8080/openapi.json - display-service
    + http://localhost:8080/openapi.json - send-notification-service


* HTML конфигурация OpenAPI:
  + http://localhost:8080/openapi.html - private-service
  + http://localhost:8080/openapi.html - public-service
  + http://localhost:8080/openapi.html - display-service
  + http://localhost:8080/openapi.html - send-notification-service
    
##Список сервисов и портов:
* PostgreSQL = 5432
* Kafka = 9092, 19092
* Zookeeper = 2181  
* config-service = 8888
* private-service = 8080
* public-service = 8081
* display-service = 8082
* send-notifications-service = 8083
