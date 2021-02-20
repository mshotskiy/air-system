create table air_company
(
    company_id   bigint auto_increment,
    name         varchar(45) not null,
    company_type varchar(15) not null,
    founded_at   date        not null,
    constraint company_id_UNIQUE
        unique (company_id),
    constraint name_UNIQUE
        unique (name)
)
    charset = utf8;

alter table air_company
    add primary key (company_id);

create table airplane
(
    airplane_id           bigint auto_increment,
    name                  varchar(45)      not null,
    factory_serial_number varchar(15)      not null,
    air_company_id        bigint           null,
    number_of_flights     int    default 0 not null,
    flight_distance       bigint default 0 not null,
    fuel_capacity         int              not null,
    type                  varchar(15)      not null,
    created_at            date             not null,
    constraint factory_serial_number_UNIQUE
        unique (factory_serial_number),
    constraint idnew_table_UNIQUE
        unique (airplane_id),
    constraint airplane_air_company_company_id_fk
        foreign key (air_company_id) references air_company (company_id)
            on update cascade on delete cascade
)
    charset = utf8;

alter table airplane
    add primary key (airplane_id);

create table flight
(
    flight_id             bigint auto_increment,
    flight_status         varchar(10) not null,
    air_company_id        bigint      not null,
    airplane_id           bigint      not null,
    departure_country     varchar(45) not null,
    destination_country   varchar(45) not null,
    distance              int         not null,
    estimated_flight_time time        not null,
    ended_at              timestamp   null,
    delay_started_at      timestamp   null,
    created_at            timestamp   not null default CURRENT_TIMESTAMP,
    started_at            timestamp   null,
    constraint flight_id_UNIQUE
        unique (flight_id),
    constraint flight_air_company_company_id_fk
        foreign key (air_company_id) references air_company (company_id)
            on update cascade on delete cascade,
    constraint flight_airplane_airplane_id_fk
        foreign key (airplane_id) references airplane (airplane_id)
            on update cascade on delete cascade
)
    charset = utf8;

alter table flight
    add primary key (flight_id);

INSERT INTO air_company.air_company (name, company_type, founded_at) VALUES ('Jetstar Airvays', 'standart', '2005-12-10');
INSERT INTO air_company.air_company (name, company_type, founded_at) VALUES ('Virgin Airlanes', 'standart', '2005-12-10');
INSERT INTO air_company.air_company (name, company_type, founded_at) VALUES ('Quantas Airways', 'charter', '2012-11-12');

INSERT INTO air_company.airplane (name, factory_serial_number, air_company_id, number_of_flights, flight_distance, fuel_capacity, type, created_at) VALUES ('Airbus A220', 'ZFR2XOD212', 1, 0, 0, 120000, 'passangers', '2014-03-11');
INSERT INTO air_company.airplane (name, factory_serial_number, air_company_id, number_of_flights, flight_distance, fuel_capacity, type, created_at) VALUES ('Airbus A310', 'ARF120203111AZZ', 1, 0, 0, 120000, 'passangers', '2016-03-11');
INSERT INTO air_company.airplane (name, factory_serial_number, air_company_id, number_of_flights, flight_distance, fuel_capacity, type, created_at) VALUES ('Boeing-717', 'RDA2XOD2', 2, 0, 0, 125000, 'passangers', '2015-07-11');
INSERT INTO air_company.airplane (name, factory_serial_number, air_company_id, number_of_flights, flight_distance, fuel_capacity, type, created_at) VALUES ('Boeing-737', 'RDA2X55D2', 2, 0, 0, 130000, 'passangers', '2015-07-11');
INSERT INTO air_company.airplane (name, factory_serial_number, air_company_id, number_of_flights, flight_distance, fuel_capacity, type, created_at) VALUES ('Superjet-100', 'SA2X55D2', 3, 0, 0, 150000, 'passangers', '2015-07-11');
INSERT INTO air_company.airplane (name, factory_serial_number, air_company_id, number_of_flights, flight_distance, fuel_capacity, type, created_at) VALUES ('Antonov An-140', 'FA2X55D2', 3, 0, 0, 125000, 'passangers', '2016-07-11');
INSERT INTO air_company.airplane (name, factory_serial_number, air_company_id, number_of_flights, flight_distance, fuel_capacity, type, created_at) VALUES ('Antonov An-38', 'FA3X85D2', 3, 0, 0, 125000, 'passangers', '2017-07-11');

INSERT INTO air_company.flight (flight_status, air_company_id, airplane_id, departure_country, destination_country, distance, estimated_flight_time, ended_at, delay_started_at, created_at, started_at) VALUES ('Completed', 1, 1, 'Kiev', 'Berlin', 400, '03:10:10', '2021-02-19 18:00:00', null, '2021-02-20 10:56:15', '2021-02-18 15:00:36');
INSERT INTO air_company.flight (flight_status, air_company_id, airplane_id, departure_country, destination_country, distance, estimated_flight_time, ended_at, delay_started_at, created_at, started_at) VALUES ('Completed', 1, 1, 'Kiev', 'Lviv', 500, '01:00:00', '2021-02-19 11:00:00', '2021-02-18 17:00:36', '2021-02-19 10:10:10', '2021-02-19 10:00:36');
INSERT INTO air_company.flight (flight_status, air_company_id, airplane_id, departure_country, destination_country, distance, estimated_flight_time, ended_at, delay_started_at, created_at, started_at) VALUES ('Completed', 2, 3, 'Paris', 'Lviv', 800, '08:00:00', '2021-02-19 19:00:00', null, '2021-02-19 11:20:00', '2021-02-19 10:00:36');
INSERT INTO air_company.flight (flight_status, air_company_id, airplane_id, departure_country, destination_country, distance, estimated_flight_time, ended_at, delay_started_at, created_at, started_at) VALUES ('Active', 2, 3, 'Warsaw', 'New York', 1000, '10:00:00', null, null, '2021-02-19 15:30:00', '2021-02-19 12:00:36');
INSERT INTO air_company.flight (flight_status, air_company_id, airplane_id, departure_country, destination_country, distance, estimated_flight_time, ended_at, delay_started_at, created_at, started_at) VALUES ('Pending', 2, 4, 'Warsaw', 'Kiev', 250, '03:20:00', null, null, '2021-02-19 14:30:00', null);
INSERT INTO air_company.flight (flight_status, air_company_id, airplane_id, departure_country, destination_country, distance, estimated_flight_time, ended_at, delay_started_at, created_at, started_at) VALUES ('Completed', 3, 5, 'New York', 'Kiev', 1200, '25:20:00', '2021-02-20 11:25:28', null, '2021-02-18 14:30:00', '2021-02-19 12:00:36');
INSERT INTO air_company.flight (flight_status, air_company_id, airplane_id, departure_country, destination_country, distance, estimated_flight_time, ended_at, delay_started_at, created_at, started_at) VALUES ('Completed', 3, 5, 'New York', 'Washington', 500, '03:20:00', '2021-02-19 19:00:00', '2021-02-18 20:00:36', '2021-02-18 16:30:00', '2021-02-19 12:00:36');
INSERT INTO air_company.flight (flight_status, air_company_id, airplane_id, departure_country, destination_country, distance, estimated_flight_time, ended_at, delay_started_at, created_at, started_at) VALUES ('Completed', 3, 5, 'Prague', 'Washington', 1300, '26:20:00', '2021-02-20 11:25:41', null, '2021-02-18 16:30:00', '2021-02-19 18:00:36');
INSERT INTO air_company.flight (flight_status, air_company_id, airplane_id, departure_country, destination_country, distance, estimated_flight_time, ended_at, delay_started_at, created_at, started_at) VALUES ('Completed', 3, 6, 'Kiew', 'Cairo', 500, '05:50:00', '2021-02-10 05:00:00', null, '2021-02-18 17:50:00', '2021-02-19 19:00:36');
INSERT INTO air_company.flight (flight_status, air_company_id, airplane_id, departure_country, destination_country, distance, estimated_flight_time, ended_at, delay_started_at, created_at, started_at) VALUES ('Completed', 3, 7, 'Kiew', 'Istanbul', 250, '01:50:00', '2021-02-10 06:00:00', '2021-02-19 08:00:36', '2021-02-18 18:50:00', '2021-02-19 21:00:36');