INSERT INTO role (name)
VALUES ('ROLE_ADMIN');
INSERT INTO role (name)
VALUES ('ROLE_DOCTOR');
INSERT INTO role (name)
VALUES ('ROLE_USER');

INSERT INTO speciality (name)
VALUES ('Стоматолог');
INSERT INTO speciality (name)
VALUES ('Хирург');
INSERT INTO speciality (name)
VALUES ('Офтальмолог');
INSERT INTO speciality (name)
VALUES ('Терапевт');
INSERT INTO speciality (name)
VALUES ('Оториноларинголог');

INSERT INTO users (id, email, password, username, role_id)
VALUES ('ea062682-7f4d-4591-a4b0-ed14c6d0f89d', 'admin@admin.ru',
        '$2y$10$JOpHVvpPBSxQtgWZn9vtwe8tFHNKPPZRUews7kU.QqGBaYSvTQbim', 'admin', 1);

INSERT INTO users (id, email, password, username, role_id)
VALUES ('ab29ec7f-6dfb-4608-a320-c7a4d6c454e3', 'doctor1@doctor.ru',
        '$2y$10$ULF5IZCV5JrxgtdhJT7f.u7er2r7hTY7xCydp.9dAv5UFBC0CclCi', 'doctor1', 2);
INSERT INTO users (id, email, password, username, role_id)
VALUES ('6f1993a1-b4c3-4d1e-a3c3-11235f73437b', 'doctor2@doctor.ru',
        '$2y$10$GcJ0bIqvs7u5Nem4pmPv2ek1WAXtIWf4ckfL35oblmKw7FQWVkdGS', 'doctor2', 2);
INSERT INTO users (id, email, password, username, role_id)
VALUES ('eb2bc39f-b9b7-4fce-8e09-bae6a4f41a5b', 'doctor3@doctor.ru',
        '$2y$10$LkK0tjbHpYz/yHOqyzDcO.uu3nUDP8SR7zcyLmiBjIokQTj8MLaEa', 'doctor3', 2);
INSERT INTO users (id, email, password, username, role_id)
VALUES ('2abf3725-b3e9-4ef5-b0f3-f23942836824', 'doctor4@doctor.ru',
        '$2y$10$gWhjgXzj9mwt.rHPsZi6debwT0SSWH9QN2L.JBQK568n2Xz/40NX6', 'doctor4', 2);
INSERT INTO users (id, email, password, username, role_id)
VALUES ('c6c34236-7f55-450d-86c1-2b74220131f4', 'doctor5@doctor.ru',
        '$2y$10$Py50YeyQHrqp1/APIxdEmeMyOupPYUNgrmsVrHyGHAn4GzxPxaYCK', 'doctor5', 2);

INSERT INTO doctor (id, middle_name, name, sur_name, speciality_id, user_id, cabinet)
VALUES ('27fbb8d4-686a-4099-87e1-cf7118f1f3eb', 'Иванов', 'Иван', 'Иванович', 1,
        'ab29ec7f-6dfb-4608-a320-c7a4d6c454e3', '101');
INSERT INTO doctor (id, middle_name, name, sur_name, speciality_id, user_id, cabinet)
VALUES ('b2a07ca7-4f8d-4806-8a14-fc0835984f3f', 'Иванова', 'Наталья', 'Ивановна', 2,
        '6f1993a1-b4c3-4d1e-a3c3-11235f73437b', '202');
INSERT INTO doctor (id, middle_name, name, sur_name, speciality_id, user_id, cabinet)
VALUES ('76390437-1843-4d46-95d2-c8efbbd0c45e', 'Петров', 'Иван', 'Иванович', 3,
        'eb2bc39f-b9b7-4fce-8e09-bae6a4f41a5b', '303');
INSERT INTO doctor (id, middle_name, name, sur_name, speciality_id, user_id, cabinet)
VALUES ('392bf1e2-5ef1-4b7f-a2ff-02b62e5b0e29', 'Петрова', 'Мария', 'Алексеевна', 4,
        '2abf3725-b3e9-4ef5-b0f3-f23942836824', '404');
INSERT INTO doctor (id, middle_name, name, sur_name, speciality_id, user_id, cabinet)
VALUES ('a3261c20-ced5-43d4-96b0-4e8c001f63a4', 'Иванов', 'Александ', 'Анатольевич', 5,
        'c6c34236-7f55-450d-86c1-2b74220131f4', '505');
