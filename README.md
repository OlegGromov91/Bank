# Приложение банка #

Логику приложения можно посмотреть здесь **[AppRoadMap](https://github.com/OlegGromov91/Bank/blob/main/bankProject/README.md)**

### **В проекте были задействованы следующие технологии:** ###
___
-  **Frontend**:
  - Angular vCLI 13.3.3
  - NodeJS v14.17.3
  - Package manager npm v6.14.13
  - Angular Material UI

-  **Backend:**
  + PostgreSQL v14.2.2
  + Spring Boot v2.6.6
  + MapStruct

### **Для запуска проекта необходимо выполнить следующие команды:** ###

- ng add @ng-bootstrap/ng-bootstrap
- npm i ngx-pagination
- ng add @angular/material
- ng build
- ng serve
- mvn clean 
- mvn install

---
  **Работа над ошибками**
  
* запросы не по rest
* нет логирования
* дописать тесты:
   - Unit - 0%
   - Integration - 2%

---
**Что необходимо добавить**

-  **Прикрутить брокер сообщений для заявок на разблокировку карты:**
  - Отдельный сервис будет лезть в базу и проверять:
   1) если у оператора нет задачи (boolean поле, когда оператор берет задачу), то даем ему заявку.
   2) предусмотреть вариант, когда все операторы заняты (либо мы смотрим на общее количество сделанных заявок за последний час,
   либо через джобу каждый раз проверяем освободился ли какой-нибудь оператор.
  - Подключить liquibase.
  - Образ в докере.
---

> Для связи со мной:

-  grom4@mail.ru
-  [![https://t.me/OlegGromov13](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white) ](https://t.me/OlegGromov13) 
---


[![codewars](https://www.codewars.com/users/grom4/badges/micro)](https://www.codewars.com/users/grom4)


