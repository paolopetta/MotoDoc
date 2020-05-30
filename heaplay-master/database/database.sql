drop database if exists heaplay_db;
create database heaplay_db;

use heaplay_db;

drop user if exists 'admin'@'localhost';
create user 'admin'@'localhost' identified by 'Progetto.TSW';
grant all privileges on heaplay_db.* to 'admin'@'localhost';

drop table if exists users;
create table users ( 
    id integer unsigned auto_increment not null,
    username varchar(192) not null unique,
    email varchar(255) not null unique,
    password varchar(255) not null,
    auth enum('user', 'admin') default 'user',
    active boolean default true,
    image mediumblob default null,
    image_ext varchar(8),
    primary key(id)
) auto_increment = 1;

drop table if exists tracks;
create table tracks (
    id integer unsigned auto_increment not null,
    name varchar(255) not null,
    type enum('free', 'pagamento') default 'free',
    plays integer unsigned default 0,    
    track mediumblob default null,
    track_extension varchar(8) not null, 
    image mediumblob default null,
    image_extension varchar(8) not null,
    indexable boolean default true,
    author integer unsigned not null,
    author_name varchar(192) not null,
    upload_date timestamp not null,
    likes integer unsigned default 0,
    duration integer unsigned default 0,
    primary key(id),
    foreign key(author) references users(id)
        on delete cascade on update cascade,
    foreign key(author_name) references users(username)
        on delete cascade on update cascade    
) auto_increment = 1;

drop table if exists purchasable_tracks;
create table purchasable_tracks (
    id integer unsigned not null,
    sold integer unsigned default 0,
    price decimal(5,2) default 0,
    primary key(id),
    foreign key(id) references tracks(id)
        on delete cascade on update cascade
);

drop table if exists owns;
create table owns (
    user_id integer unsigned not null,
    track_id integer unsigned not null,
    purchase_date timestamp not null,
    primary key(user_id, track_id),
    foreign key(user_id) references users(id)
        on delete cascade on update cascade,
    foreign key(track_id) references tracks(id)
        on delete cascade on update cascade
);

drop table if exists carted;
create table carted (
    user_id integer unsigned not null,
    track_id integer unsigned not null,
    primary key(user_id, track_id),
    foreign key(user_id) references users(id)
        on delete cascade on update cascade,
    foreign key(track_id) references tracks(id)
        on delete cascade on update cascade
);

drop table if exists playlists;
create table playlists (
    id integer unsigned auto_increment not null, 
    name varchar(255) not null,
    privacy enum('public', 'private') default 'public',
    author integer unsigned not null,
    author_name varchar(192) not null,
    primary key(id),
    foreign key(author) references users(id)
        on delete cascade on update cascade,
    foreign key(author_name) references users(username)
        on delete cascade on update cascade    
) auto_increment = 1;

drop table if exists contains;
create table contains (
    playlist_id integer unsigned not null,
    track_id integer unsigned not null,
    primary key(playlist_id, track_id),
    foreign key(playlist_id) references playlists(id)
        on delete cascade on update cascade,
    foreign key(track_id) references tracks(id)
        on delete cascade on update cascade 
);

drop table if exists comments;
create table comments (
	id integer unsigned auto_increment not null,
    user_id integer unsigned not null,
    author varchar(192) not null,
    track_id integer unsigned not null,
    body text not null,
    primary key(id, track_id),
    foreign key(user_id) references users(id)
        on delete cascade on update cascade,
	foreign key(author) references users(username)
        on delete cascade on update cascade,
    foreign key(track_id) references tracks(id)
        on delete cascade on update cascade
) auto_increment = 1, engine=MyIsam;

drop table if exists tags;
create table tags (
    id integer unsigned auto_increment not null,
    name varchar(255) not null,
    primary key(id)
) auto_increment = 1;

drop table if exists tagged;
create table tagged (
    track_id integer unsigned not null,
    tag_id integer unsigned not null,
    primary key(track_id, tag_id),
    foreign key(track_id) references tracks(id)
        on delete cascade on update cascade,
    foreign key(tag_id) references tags(id)
        on delete cascade on update cascade 
);

drop table if exists liked;
create table liked (
	track_id integer unsigned not null,
    user_id integer unsigned not null,
	primary key(track_id, user_id),
    foreign key(track_id) references tracks(id)
        on delete cascade on update cascade,
    foreign key(user_id) references users(id)
        on delete cascade on update cascade 
);

insert into users(username,email,password,auth) values("admin","admin@admin.it",md5("password"),"admin");
set global max_allowed_packet=20971520; 
