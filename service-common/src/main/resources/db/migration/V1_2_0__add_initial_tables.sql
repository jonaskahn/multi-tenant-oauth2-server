
create table if not exists organization
(
    id          bigint auto_increment
    primary key,
    name        varchar(255)               not null,
    description text                       null,
    email       varchar(120)               not null,
    website     varchar(255)               null,
    country     varchar(100)               null,
    state       varchar(100)               null,
    city        varchar(100)               null,
    address     varchar(255)               null,
    zone_info   varchar(100)               null,
    enabled     tinyint(1) default 0       not null comment '0 is enabled
1 is disabled',
    locked      tinyint(1) default 1       not null comment '0 is unlocked
1 is locked',
    created_at  timestamp  default (now()) null,
    updated_at  timestamp  default (now()) null,
    created_by  bigint                     null,
    updated_by  bigint                     null,
    constraint id
    unique (id)
    )     auto_increment = 1000;

create table if not exists department
(
    id              bigint auto_increment
    primary key,
    parent_id       bigint                     not null,
    organization_id bigint                     not null,
    name            varchar(255)               not null,
    description     text                       null,
    enabled         tinyint(1) default 0       not null comment '0 is enabled
1 is disabled',
    created_at      timestamp  default (now()) null,
    updated_at      timestamp  default (now()) null,
    created_by      bigint                     null,
    updated_by      bigint                     null,
    constraint id
    unique (id),
    constraint department_organization_id_fk
    foreign key (organization_id) references organization (id),
    constraint departments_departments_id_fk
    foreign key (parent_id) references department (id)
    )    auto_increment = 1000;

create table if not exists user
(
    id                    bigint auto_increment
    primary key,
    department_id         bigint                                   null,
    organization_id       bigint                                   null,
    username              varchar(120)                             not null,
    preferred_username    binary(16) default (uuid_to_bin(uuid())) null,
    email                 varchar(120)                             null,
    email_verified        tinyint(1)    default 0                  null comment '0 is not verified
1 is verified',
    phone_number          char(15)                                 null,
    phone_number_verified tinyint(1)    default 0                  null comment '0 is not verified
1 is verified',
    password              varchar(255)                             not null,
    family_name           varchar(25)                              null,
    middle_name           varchar(25)                              null,
    given_name            varchar(25)                              null,
    name                  varchar(75)                              null,
    unsigned_name         varchar(75)                              null,
    enabled               tinyint(1) default 0                     not null comment '0 is enabled
1 is disabled',
    locked                tinyint(1) default 1                     not null comment '1 is locked
0 is unlocked',
    gender                tinyint    default 0                     null comment '1 is male
2 is female
0 is unknown',
    birthdate             date       default (now())               null,
    created_at            timestamp  default (now())               null,
    updated_at            timestamp  default (now())               null,
    created_by            bigint                                   null,
    updated_by            bigint                                   null,
    constraint email
    unique (email),
    constraint id
    unique (id),
    constraint preferred_username
    unique (preferred_username),
    constraint username
    unique (username),
    constraint user_organization_id_fk
    foreign key (organization_id) references organization (id),
    constraint users_departments_id_fk
    foreign key (department_id) references department (id)
)    auto_increment = 1000;


create index users_phone_index
    on user (phone_number);

create trigger `before-insert-user`
    before insert
    on user
    for each row
BEGIN
    if new.name is not null then
        SET NEW.unsigned_name = fn_remove_accents(NEW.name);
end if;
END;

create trigger `before-update-user`
    before update
    on user
    for each row
begin
    IF ( new.name != old.name ) then
        if new.name is null then
            set new.unsigned_name = null;
    else
            SET NEW.unsigned_name = fn_remove_accents(NEW.name);
end if;
END IF;
end;

create table if not exists authority
(
    id          bigint auto_increment
    primary key,
    name        varchar(255)         not null,
    description text                 null,
    status      tinyint(1) default 1 not null comment '0 equals active
1 equals inactive
9 equals deleted',
    constraint id
    unique (id)
    )    auto_increment = 1000;

create table if not exists user_authority
(
    user_id      bigint not null,
    authority_id bigint not null,
    primary key (user_id, authority_id),
    constraint user_authority_authority_id_fk
    foreign key (authority_id) references authority (id),
    constraint user_authority_user_id_fk
    foreign key (user_id) references user (id)
    )    auto_increment = 1000;

