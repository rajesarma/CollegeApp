select * from services;

update services set show_in_menu = 'Y' where service_id = 2;
update services set service_url = '/college/delete', service_name = 'Student Delete' where service_id = 27;

insert into services (service_url,service_name,is_active,parent_id,display_order, show_in_menu) values('/college/delete', 'Student Delete', 'Y', 12, 36, 'N' );

update services set show_in_menu = 'N' where service_id = 2;

select nextval('student_details_student_id_seq') as student_id from student_details;


CREATE TABLE sequence_data (
    `sequence_name` varchar(100) NOT NULL,
    `sequence_increment` int(11) unsigned NOT NULL DEFAULT 1,
    `sequence_min_value` int(11) unsigned NOT NULL DEFAULT 1,
    `sequence_max_value` bigint(20) unsigned NOT NULL DEFAULT 18446744073709551615,
    `sequence_cur_value` bigint(20) unsigned DEFAULT 1,
    `sequence_cycle` boolean NOT NULL DEFAULT FALSE,
    PRIMARY KEY (`sequence_name`)
);

INSERT INTO sequence_data
    (sequence_name)
VALUE
    ('student_details_student_id_seq')
;

select * from student_details;


SELECT nextval('student_sequence') as next_sequence;

-- To relax the preceding conditions on function creation
SET GLOBAL log_bin_trust_function_creators = 1;


create table test (
dat date null

);

insert into test(dat)values(10/03/2019);
select * from test;

INSERT INTO test (dat) VALUES ('2008-7-04');
INSERT INTO test VALUES (STR_TO_DATE('17/03/2019', '%d/%m/%Y'));


describe student_details;

select * from student_details;

select md5('guest');
select * from users;

select * FROM student_details;


select sd.student_id, ay.batch, b.branch_name,  roll_no, student_name, father_name,
-- STR_TO_DATE(dob,'%d/%m/%y') as dob, STR_TO_DATE(doj,'%d/%m/%y') as doj 
date_format(dob,'%d/%m/%Y') as dob, STR_TO_DATE(doj,'%d/%m/%y') as doj 

from student_details sd 
left join academic_year ay on (ay.year_id = sd.academic_year_id) 
left join branch b on (b.branch_id = sd.branch_id) 
where ay.year_id = 1  and b.branch_id ='01'  
order by branch_name, roll_no, student_name;

select s.service_id, s.service_url, s.service_name,s.parent_id, s.display_order, s.menu_display
from users u
join role_services rs on(u.role_id = rs.role_id )
join services s on (s.service_id = rs.service_id)
where user_name = 'admin'
order by parent_id, service_id;

select * from role_services;

insert into role_services(role_id, service_id) values(1,25), (1,26), (1,27);

select student_id, student_name, father_name,   
 dob, doj, coalesce(joining_year_id,1) as joining_year_id, blood_group_id, height
 aadhar, address, email,  photo,photo_name,
 guardian_mobile, (select blood_group from blood_group where bg_id = sd.blood_group_id) as blood_group, academic_year_id,
 branch_id, is_active, roll_no,  
 mobile_no, mother_name,  
 gender, mentor_name, address, height  
 from student_details sd; 
 -- where student_id = 10;

update student_details set photo_name = 'ALIET_15_photo.jpg' where student_id = 15;

update student_details s1 
join (
select * from student_details where student_id = 15
) s2
set s1.photo = s2.photo
where s1.student_id = 10;

update student_details set dob = STR_TO_DATE('02/11/1982','%d/%m/%Y') where student_id = 10;

update student_details  set  student_name = 'Rajeswara Rao',  father_name = 'seeta Rama Prasad',  dob = STR_TO_DATE('1982-11-02','%d/%m/%Y'),  
doj = STR_TO_DATE('2018-09-20','%d/%m/%Y'),  address = 'dfgdfgdfg', email = 'rajeswararao.pamarthi@mytrah.com',  guardian_mobile = '9866489944',  
blood_group_id = 1,  branch_id = '01', roll_no = 'A123',  mobile_no = '9949479944',  
mother_name = 'Sujatha',  gender = 'M',  academic_year_id = 1,  aadhar = '234234',  height = 170,  joining_year_no = '1'    where student_id = 10;

insert into student_details
select (student_id + 1), concat('Lakshmi P ',student_name) as student_name, father_name, dob, doj, photo_name, aadhar, address,
email,
guardian_mobile,
blood_group_id,
academic_year_id,
branch_id,
is_active,
roll_no,
mobile_no,
mother_name,
gender,
mentor_name,
photo,
height,
joining_year_no from student_details where student_id = 11;

SELECT column_name
FROM information_schema.columns
WHERE  table_name = 'student_details';

SHOW COLUMNS
FROM student.services;

create table user_role(
id int auto_increment not null,
user_id int,
role_id int,
primary key(id),
foreign key (user_id) references users(id),
foreign key (role_id) references roles(role_id)
);

select * from users;
insert into user_role(id, user_id,role_id) values(2, 1, 4);
select * from user_role;

select * from users u 
join user_role ur on u.id = ur.user_id;


select u.id, user_name, user_desc, email, ur.role_id, GROUP_CONCAT(role_name) as role_name
-- " from users u join roles r on r.role_id = u.role_id 
from users u 
join user_roles ur on u.id = ur.user_id 
join roles r on r.role_id = ur.role_id
where user_name = 'admin';


select * from users;
update users set real_password = 'guest';
update users set password = 'guest';
update users set disabled = false;


alter table users add column real_password varchar(100) null;

select * from user_roles;

select * from roles;

select * from users;


select * from services;
update services set menu_display = 0 where service_id in (1,2);


SHOW COLUMNS
FROM student.role_services;

SHOW COLUMNS
FROM student.services;

select * from role_services where role_id = 1;

delete from role_services where role_id = 1 and service_id in (5,6,7,8);

select * from services;

select * from user_roles;
delete from user_roles where id = 2;


select s.service_id, s.service_url, s.service_name,s.parent_id, s.display_order, s.menu_display 
from users u join role_services rs on (u.role_id = rs.role_id ) 
join services s on (s.service_id = rs.service_id) 
where user_name = 'admin'           
order by parent_id, service_id;


select s.service_id, s.service_url, s.service_name, s.parent_id, s.display_order, s.disabled, s.menu_display from users u join role_services rs on (u.role_id = rs.role_id ) join services s on (s.service_id = rs.service_id) where user_name = 'admin' order by parent_id, service_id
