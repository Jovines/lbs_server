delete user from user where phone != 13110170730;

insert into user(phone, nickname,description, password,avatar, lon, lat)
VALUES (34251426154, '奥特曼','一个有梦想的奥特曼', 'ajdjj','14-56-27-timg.jpeg' ,109.64738, 30.821785);

insert into user(phone, nickname, password, lon, lat)
VALUES (4388824878, '超级飞侠', 'ajdjj', 109.6218748, 30.87342875);

insert into user(phone, nickname, password, lon, lat)
VALUES (21389891471, '白雪公主', 'ajdjj', 109.61674, 30.8236476);

insert into user(phone, nickname, password, lon, lat)
VALUES (128328464, '小矮人', 'ajdjj', 109.6324767, 30.82347);

insert into user(phone, nickname, password, lon, lat)
VALUES (214787746, '初音未来', 'ajdjj', 109.623145, 30.85315);

insert into user(phone, nickname, password, lon, lat)
VALUES (147917481658, '死神', 'ajdjj', 109.653135, 30.854354);


delete from lifeCircleMessageItem where user != 13110170730;

insert into lifeCircleMessageItem(user, title, content, time, lon, lat)
VALUES (34251426154,'今日份收获','今天同时挑战10个小怪兽',current_time,109.64738,30.821785) ;


insert into lifeCircleMessageItem(user, title, content, time, lon, lat)
VALUES (34251426154,'就像一场梦','醒来还是很感动',current_time,109.64738,30.821785) ;


insert into lifeCircleMessageItem(user, title, content, time, lon, lat)
VALUES (34251426154,'人生在世','走走停停',current_time,109.64738,30.821785) ;


insert into lifeCircleMessageItem(user, title, content, time, lon, lat)
VALUES (34251426154,'深夜感伤','小怪兽去哪了',current_time,109.64738,30.821785) ;



insert into lifeCircleMessageItem(user, title, content, time, lon, lat)
VALUES (34251426154,'深夜感伤','今天也是悲伤的一天','2020-05-03 21:47:19',109.64738,30.821785) ;

select current_timestamp;


