create table if not exists oauth2_client
(
    id                            varchar(255)                        not null
    primary key,
    client_id                     varchar(255)                        not null,
    client_id_issued_at           timestamp default CURRENT_TIMESTAMP not null,
    client_secret                 varchar(255)                        null,
    client_secret_expires_at      timestamp                           null,
    client_name                   varchar(255)                        not null,
    client_authentication_methods varchar(1000)                       not null,
    authorization_grant_types     varchar(1000)                       not null,
    redirect_uris                 varchar(1000)                       null,
    scopes                        varchar(1000)                       not null,
    client_settings               blob                                not null,
    token_settings                blob                                not null
    );

create table if not exists oauth2_authorization
(
    id                            varchar(255)  not null
    primary key,
    registered_client_id          varchar(255)  not null,
    principal_name                varchar(255)  not null,
    authorization_grant_type      varchar(255)  not null,
    attributes                    blob          null,
    state                         varchar(500)  null,
    authorization_code_value      blob          null,
    authorization_code_issued_at   timestamp     null,
    authorization_code_expires_at timestamp     null,
    authorization_code_metadata   blob          null,
    access_token_value            blob          null,
    access_token_issued_at        timestamp     null,
    access_token_expires_at       timestamp     null,
    access_token_metadata         blob          null,
    access_token_type             varchar(255)  null,
    access_token_scopes           varchar(1000) null,
    refresh_token_value           blob          null,
    refresh_token_issued_at       timestamp     null,
    refresh_token_expires_at      timestamp     null,
    refresh_token_metadata        blob          null,
    oidcId_token_value            blob          null,
    oidcId_token_issued_at        timestamp     null,
    oidcId_token_expires_at       timestamp     null,
    oidcId_token_metadata         blob          null,
    oidcId_token_claims           blob          null
    );

create table if not exists oauth2_authorization_consent
(
    registered_client_id varchar(255)  not null,
    principal_name       varchar(255)  not null,
    authorities          varchar(1000) not null,
    primary key (registered_client_id, principal_name)
    );


