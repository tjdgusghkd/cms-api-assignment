create table members (
    member_id bigint primary key auto_increment,
    login_id varchar(50) not null unique,
    password varchar(255) not null,
    name varchar(50) not null,
    role varchar(20) not null,
    created_at timestamp default now() not null
);

create table contents (
    content_id bigint primary key auto_increment,
    title varchar(100) not null,
    description text,
    view_count bigint not null default 0,
    created_date timestamp default now() not null,
    created_by_id bigint not null,
    last_modified_date timestamp,
    last_modified_by_id bigint,
    status varchar(10) default 'active' not null,
    constraint fk_contents_created_by foreign key (created_by_id) references members(member_id),
    constraint fk_contents_last_modified_by foreign key (last_modified_by_id) references members(member_id)
);