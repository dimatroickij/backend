##Необходимый софт
* Maven
* Docker
##Дополнительный софт для отладки
* Intellij IDEA Ultimate edition
* DBeaver или схожее приложение для подключения к базе данных
* Java 13+

##Запуск приложения
1. Выполнить mvn clean package
2. Запустить все приложение через docker-compose -f docker-compose.yml либо создать конфигурацию в Inteliji IDEA

##Список сервисов и портов:
* PostgreSQL = 5432
* Kafka = 9092, 19092
* Zookeeper = 2181  
* config-service = 8888
* private-service = 8080
* public-service = 8081
* display-service = 8082
* send-notifications-service = 8083
