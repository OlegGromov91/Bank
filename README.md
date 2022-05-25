# Приложение банка #

Логику приложения можно посмотреть здесь **[AppRoadMap](https://github.com/OlegGromov91/Bank/blob/main/bankApp/README.md)**

### **В проекте были задействованы следующие технологии:** ###
___
- [ ]  **Frontend**:
  - Angular vCLI 13.3.3
  - NodeJS v14.17.3
  - Package manager npm v6.14.13
  - Angular Material UI

- [ ] **Backend:**
  + PostgreSQL v14.2.2
  + Spring Boot(web\security\data) v2.6.6
  + MapStruct
  + Liquibase

### **Для запуска проекта необходимо выполнить следующие команды:** ###

- ng add @ng-bootstrap/ng-bootstrap
- npm i ngx-pagination
- ng add @angular/material
- ng build
- ng serve
- mvn clean-install on root package

---
  **Работа над ошибками**
  
* хранение токенов в локал сторадже такое себе решение
* Убрать запросы достающие все из базы, добавить page
* необходим рефакторинг клиента
* дописать тесты:
   - Unit - 0%
   - Integration - 2%

---
**Что необходимо добавить**

- Прикрутить брокер сообщений для заявок на разблокировку карты, на создание карты\счета. Задача в том, чтобы свободному оператору приходила заявка для обработки.
- Образ в докере.
- Локализировать ошибки на фронте
- Добавить гуарды на пин, чтобы юзер не мог зайти на страницу карты без ввода пин кода
---

> Для связи со мной:

-  grom4@mail.ru
-  [![https://t.me/OlegGromov13](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white) ](https://t.me/OlegGromov13) 
---


[![codewars](https://www.codewars.com/users/grom4/badges/micro)](https://www.codewars.com/users/grom4)


