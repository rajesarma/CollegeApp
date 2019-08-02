select * from services;

update services set disabled = 0 where service_id = 28;

select * from roles;

select * from user_roles;

insert into user_roles(user_id, role_id)
value(1, 28);

select * from users;
select * from user_roles;

select * from role_services;

select * from services;

-- delete from users where id = 21;



update users set user_name = 'faculty' where id = 14;
update users set id = 5 where id = 14;

update users set password = '$2a$10$5yI.aXNyGO93GiDmDp0ZYuuRtqC8uehLyjBwsZTA.Nn9hszfoy9gG' where id = 5;

select *, s.service_name from role_services rs
join services s on (rs.service_id = s.service_id)
where role_id = 2;

select * from services;

select -- user0_.id as id1_9_, user0_.disabled as disabled2_9_, user0_.email as email3_9_, user0_.failed_login_attempts as failed_l4_9_, user0_.last_login as last_log5_9_, 
user0_.last_password_change as last_pas6_9_, user0_.password as password7_9_, user0_.user_desc as user_des8_9_, user0_.user_name as user_nam9_9_ 
from users user0_ 
left outer join user_roles roles1_ on user0_.id=roles1_.user_id 
left outer join roles role2_ on roles1_.role_id=role2_.role_id 
where role2_.role_id=1;


-- alter table users add column last_password_change date null;

insert into role_services(role_id, service_id)
values(1,28);

insert into services(service_id, service_url, service_name, parent_id, display_order, menu_display)
values(28,'/admin/usersList','Users List',16,3,1);

select * from user_roles;
select * from users;

insert into user_roles(user_id, role_id)
values(3,3);


select * 
from users user0_ 
left outer join user_roles roles1_ on user0_.id=roles1_.user_id 
;

select * from users;
select * from roles;
select * from user_roles where user_id = 23;

select * from user_roles;
select * from role_services;
select * from services;

INSERT INTO `roles` VALUES (3,'Student');

INSERT INTO `services` VALUES (15,'/logout','Log Out',0,0,10,1),(18,'/changePassword','Change Password',0,16,2,1),(19,'/student/attPerReport','Student Attendance Report',0,14,54,1),
(20,'/student/marksPerReport','Student Marks Report',0,14,55,1),(21,'/student/display','Student Display',0,14,56,1);
INSERT INTO `role_services` VALUES (3,1),(3,14),(3,15),(3,18),(3,19),(3,20),(3,21);

INSERT INTO `role_services` VALUES (5,1),(5,15),(5,18);
INSERT INTO `role_services` VALUES (5,14);
INSERT INTO `role_services` VALUES (1,29);


select * from role_services where role_id = 3;

INSERT INTO `role_services` VALUES (1,18);

delete from users where id = 23;

select * from role_services;
select * from role_services where service_id in (1,14,15,18,19,20,21);

select * from role_services where role_id in (2,3);
select * from services where service_id in (1,14,15,18,19,20,21);

update services set menu_display =0 where service_id in (25,26,27);

INSERT INTO `services` VALUES (29,'/super/roleServices','Role Services Mapping',0, 16, 4,1);

select * from user_roles where user_id = 2;
select * from role_services where role_id = 4;

select * from users where user_name = 'management';

select * from roles;
update roles set role_name = 'Management' where role_id = 4;


select * 
from role_services rs
join services s on s.service_id = rs.service_id
where rs.role_id = 4;

select * from users;
delete from users where id = 43;

update users set disabled = 0 where id != 50;

select * from student_details;

select student_id, joining_semester_id, joining_year_no from student_details where joining_year_no in (2);

update student_details set dob = 3 where student_id=62;

update student_details set photo = (select photo from student_details where student_id = 5) where student_id = 3;


update student_details s1
join (
select * from student_details where student_id = 61
) s2
set s1.dob = s2.dob, s1.doj = s2.doj
where s1.student_id = 65;

select * from year;
select * from services;

update services set service_url = '/super/student/addStudentAttendance' where service_id = 5;

select * from academic_year;

select * from student_attendance;

select * from semester;

select * from role_services where service_id = 4;

create table batch
select * from academic_year;

select * from batch;

select * from branch;

alter table student_details change column semester_id joining_semester_id integer;

select * from exam_type;
select * from year;


select * from 
(
   select sa.id, sd.batch_id, sa.branch_id, sa., sa.semester_id, sd.student_id, roll_no, student_name, father_name, 
   coalesce(sa.no_of_days,0) as no_of_days, coalesce(sa.days_present,0) as days_present
   from student_attendance sa 
   left join student_details sd on (sd.student_id = sa.student_id )
   where
       sa.branch_id = '12'
   and sa.semester_id = 1
   and sa. = 1
   and sa.batch_id =1
)list
union
(
 	select sa.id, sd.batch_id, sd.branch_id, 1 as , 1 as semester_id, sd.student_id, roll_no, student_name, father_name, 
  0 as no_of_days, 0 as days_present
  from student_details sd
  left join student_attendance sa on (sa.student_id = sd.student_id)
  where sd.student_id not in
  ( select student_id from student_attendance 
     where batch_id = 1
     and branch_id ='12'
     and  = 1
     and semester_id = 1
  )
   and sd.batch_id = 1
   and sd.branch_id = '12'
)
order by roll_no, student_name;

select * from student_attendance;
select * from student_details;

select batch_id, branch_id, , semester_id, student_id, roll_no, student_name, no_of_days, days_present  
from  (   
		select sa.id, sd.batch_id, sa.branch_id, sa., sa.semester_id, sd.student_id, roll_no, student_name, coalesce(sa.no_of_days,0) as no_of_days, 
		coalesce(sa.days_present,0) as days_present
        from student_attendance sa    
        left join student_details sd on (sd.student_id = sa.student_id )   
		where sa.batch_id = 1 and sa.branch_id = '12' and sa. = 1 and sa.semester_id = 1 
       )list 
union ( 
	select sd.batch_id, sd.branch_id, 1 as ,  1 as semester_id, sd.student_id, roll_no, student_name, 0 as no_of_days, 0 as   days_present
    from student_details sd  where batch_id = 1   and sd.branch_id = '12' and current_year_id =1 and current_semester_id =1
    and student_id not in  
	( 
		select student_id from student_attendance where batch_id = 1 and branch_id = '12' and  = 1 and semester_id = 1  
     )   
    ) 
order by roll_no, student_name;

select * from student_details s where s.student_id not in (2, 3);

select sd.batch_id, sd.branch_id, 1 as , 1 as semester_id, sd.student_id, roll_no, student_name, father_name, 
  0 as no_of_days, 0 as days_present
  from student_details sd
  where sd.batch_id = 1
  and sd.branch_id = '12';
  
select student_id from student_attendance where batch_id = 1 and branch_id = '12' and  = 1 and semester_id = 1;

select * from student_details;  
select * from services;
delete from services where service_id = 30;

update services set service_url ='/super/student/studentAttendance', service_name ='Student Attendance' where service_id =5;
insert into services(service_id, service_url, service_name, disabled, parent_id, display_order, menu_display)
values(30, '/super/student/studentAttendance/add', 'Add Student Attendance', 0, 12, 35, 0);
select * from roles;
select * from role_services where service_id = 30;

insert into role_services(role_id, service_id) values(1, 30), (5, 30);

ALTER TABLE student_attendance
ADD CONSTRAINT student_attendance_unique UNIQUE KEY(batch_id,branch_id,,semester_id, student_id );

select * from student_attendance;

delete from student_attendance where id = 173;

select * from student_details where student_id in (3, 5, 17, 60, 63, 64);

select * from academic_year;
select * from test;

update student_attendance set semester_id=1 where id =110;

select * from year;
select * from batch;

select * from year_semseters;
alter table student_details add column current_year integer null;
alter table student_details add column current_semester_id integer null;

select * from academic_year;
select * from student_details;

update student_details set current_semester_id = joining_semester_id where student_id = 116;

update student_details set joining_ = joining_year_id;

select branch_id, year_id, semester_id, round(avg(no_of_days),2) as no_of_days, round(avg(days_present),2) as days_present
from student_attendance

group by branch_id, year_id, semester_id;

select round(avg(no_of_days),2) as no_of_days, round(avg(days_present),2) as days_present
from student_attendance
where batch_id = 1 and branch_id = '12' and year_id = 1 and semester_id=1;

select student_name, roll_no from student_details where student_id in (11,12,29,31,32,36,55);

select * from services;

select * from student_details; 
select * from faculty_details; 

select * from student_marks;

create table employee
select * from faculty_details;

insert into department(dept_id, name) values
(1, 'Administration'),
(2, 'Accounts'),
(3, 'Exams'),
(4, 'Faculty'),
(5, 'IT-Network'),
(6, 'Management'),
(7, 'Sports'),
(8, 'Transport');

select * from employee;
select * from services; 
select * from qualifications;

update services set service_url='/super/searchEmployee', service_name='Search Employee' where service_id = 10;
update services set service_name='Employee Related' where service_id = 11;
update services set service_name='Employee Reports' where service_id = 13;

select * from services where service_url like '^Faculty';

update services set service_name='Employee Edit' where service_id = 32;

update services set menu_display=0 where service_id in (32,33,34);
select * from employee;
select * from student_details;

update employee set salary = 29000 where emp_id = 189;

select * from employee;

update services set service_url ='/super/student/studentProgressReport', service_name ='Student Progress Report' where service_id =8;

insert into services(service_id, service_url, service_name, disabled, parent_id, display_order, menu_display)
values(35, '/super/student/studentMarks/add', 'Add Student Marks', 0, 12, 36, 0);

select * from 
(
   select sa.batch_id, sa.branch_id, sa.year_id, sa.semester_id, sd.student_id, sa.subject_id, sa.exam_type_id, roll_no, student_name, coalesce(sa.marks,0) as marks, et.max_marks
   from student_marks sa
   left join exam_type et on (et.exam_type_id = sa.exam_type_id)
   left join student_details sd on (sd.student_id = sa.student_id )
   where sa.branch_id ='12' and sa.year_id = 1 and sa.semester_id = 1 and sa.subject_id = 1 and sa.exam_type_id = 1  
)list
union
(
  select 1 as batch_id, sd.branch_id, 1 as year_id, 1 as semester_id, sd.student_id, 1 as subject_id, 1 as exam_type_id, roll_no, student_name, 0 as marks, 0 as max_marks
  from student_details sd
  where joining_year_id = 1 and sd.branch_id = '12' and 
  student_id not in
  ( select student_id from student_marks sa
     where sa.branch_id ='12' and sa.year_id = 1 and sa.semester_id = 1 and sa.subject_id = 1 and sa.exam_type_id = 1 
   )  
)
order by roll_no, student_name;


select * from student_attendance;
select * from student_marks where exam_type_id = 3;

select *, round( ((internal1 + internal2) / 2) ,1) as internal , 
(internal1_max + assignment_max + external_max) as total_max,
(assignment + external + lab) as subject_total
from (
select subject_id, subject_name, 
(select max_marks from exam_type where exam_type_id = 1) as internal1_max,
(select max_marks from exam_type where exam_type_id = 2) as internal2_max,
(select max_marks from exam_type where exam_type_id = 3) as assignment_max,
(select max_marks from exam_type where exam_type_id = 4) as external_max,
sum(internal1) as internal1, sum(internal2) as internal2, sum(assignment) as assignment, sum(external) as external, sum(lab) as lab
from
(
    select
    s.subject_id, s.subject_name, 
    (select exam_type from exam_type where exam_type_id = sm.exam_type_id ),
    case when sm.exam_type_id = 1 then marks else 0 end as internal1,
    -- case when sm.exam_type_id = 1 then sm.max_marks else 0 end as internal1_max,
    case when sm.exam_type_id = 2 then marks else 0 end as internal2, 
    -- case when sm.exam_type_id = 2 then sm.max_marks else 0 end as internal2_max,
    case when sm.exam_type_id = 3 then marks else 0 end as assignment,
    -- case when sm.exam_type_id = 3 then sm.max_marks else 0 end as assignment_max,
    case when sm.exam_type_id = 4 then marks else 0 end as external,
    -- case when sm.exam_type_id = 4 then sm.max_marks else 0 end as external_max,
    case when sm.exam_type_id = 5 then marks else 0 end as lab
    -- case when sm.exam_type_id = 5 then sm.max_marks else 0 end as lab_max
    from subjects s
    left join student_marks sm on (sm.subject_id = s.subject_id and sm.year_id= 1 and sm.student_id =17)
    -- left join exam_type et on (et.exam_type_id = sm.exam_type_id)
    where s.semester_id = 1  and s.branch_id = (select branch_id from student_details where student_id = 17)
)t
group by subject_id, subject_name
order by subject_id, subject_name
) p;

-- (select max_marks from exam_type where exam_type_id = 1) as internal1_max

select * from batch;
select * from academic_year;
select * from year;
select * from exam_type;
select * from subjects;
select * from branch;

create table subject_exam_types(
subject_id int(11) default null,
exam_type_id int(11) default null,
CONSTRAINT `subject_exam_type_ibfk_1` foreign key (subject_id) references subjects(subject_id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT `subject_exam_type_ibfk_2` foreign key (exam_type_id) references exam_type(exam_type_id) ON DELETE CASCADE ON UPDATE CASCADE
);

insert into subject_exam_types(subject_id, exam_type_id) values
(1,1), (1,2), (1,3), (2,1), (2,2), (2,3), (3,1), (3,2), (3,3),
(4,1), (4,2), (4,3), (5,1), (5,2), (5,3), (6,1), (6,2), (6,3),
(7,1), (7,2), (7,4), (8,1), (8,2), (8,4), (9,1), (9,2), (9,4);

select * from users;
select * from roles;
select * from user_roles;

select student_id, student_name as student from student_details where joining_year_id = 1;
select * from services;

update student_marks set exam_type_id = 4 where  exam_type_id = 3;
update exam_type set exam_type ='External Lab' where exam_type_id = 4;
    
delete from exam_type where exam_type_id = 5;
insert into exam_type (exam_type_id, exam_type, max_marks) values(5, 'External Lab', 50);


select
s.subject_id, s.subject_name, 
(select exam_type from exam_type where exam_type_id = sm.exam_type_id ),
case when sm.exam_type_id = 1 then marks else 0 end as internal1,
case when sm.exam_type_id = 2 then marks else 0 end as internal2, 
case when sm.exam_type_id = 3 then marks else 0 end as external,
case when sm.exam_type_id = 4 then marks else 0 end as lab
from subjects s
left join student_marks sm on (sm.subject_id = s.subject_id and sm.year_id= 1 and sm.student_id =64)
where s.semester_id = 1  and s.branch_id = (select branch_id from student_details where student_id = 64);

select subject_id, subject_name, internal1_max, internal1, internal2_max, internal2, round( ((internal1 + internal2) / 2) ,1) as internal, 
external_max as external_descriptive_max, external as external_descriptive, lab_max as external_lab_max, lab as external_lab,
( round( ((internal1_max + internal1_max) / 2) ,0) + external_max + lab_max) as total_max,
(internal1 + external + lab) as subject_total
from (
	select subject_id, subject_name, 
    (select max_marks from exam_type where exam_type_id = 1) as internal1_max, sum(internal1) as internal1, 
    (select max_marks from exam_type where exam_type_id = 2) as internal2_max,  sum(internal2) as internal2, 
    (select max_marks from exam_type where exam_type_id = 3) as external_max,  sum(external) as external, 
    (select max_marks from exam_type where exam_type_id = 4) as lab_max, sum(lab) as lab
	from
	(
		select
		s.subject_id, s.subject_name, ets.exam_type_id,
        case when sm.exam_type_id = 1 then marks else 0 end as internal1,
        case when sm.exam_type_id = 2 then marks else 0 end as internal2, 
        case when sm.exam_type_id = 3 then marks else 0 end as external,
        case when sm.exam_type_id = 4 then marks else 0 end as lab
		from subjects s 
		left join subject_exam_types ets on (ets.subject_id = s.subject_id )
		left join exam_type et on (et.exam_type_id = ets.exam_type_id)
		left join student_marks sm on (sm.subject_id = s.subject_id and sm.branch_id = s.branch_id and sm.exam_type_id = ets.exam_type_id and sm.student_id =64 ) -- and sm.exam_type_id = ets.exam_type_id 
		where s.semester_id = 1 and s.branch_id = '12'-- and sm.year_id= 1 and sm.student_id =64
	)t
	group by subject_id, subject_name	
) p

order by subject_id, subject_name;

select * from subjects;
select * from student_marks where student_id = 64;

update student_marks set marks = 46 where  id = 213;

select
s.subject_id, s.subject_name, 
case when sm.exam_type_id = 1 then marks else 0 end as internal1,
case when sm.exam_type_id = 2 then marks else 0 end as internal2, 
case when sm.exam_type_id = 3 then marks else 0 end as external,
case when sm.exam_type_id = 4 then marks else 0 end as lab
from subjects s
left join subject_exam_types ets on (ets.subject_id = s.subject_id)
left join exam_type et on (et.exam_type_id = ets.exam_type_id)
left join student_marks sm on (sm.subject_id = s.subject_id and sm.branch_id = s.branch_id and sm.student_id =64 and sm.exam_type_id = ets.exam_type_id)
where s.semester_id = 1 and s.branch_id = '12';

select subject_id, subject_name, internal1_max, internal1, internal2_max, internal2, internal, external_descriptive_max, external_descriptive, external_lab_max, external_lab,
case when (select is_lab from subjects s where subject_id = k.subject_id) = true then external_lab_max else external_descriptive_max end as external_max, 
case when (select is_lab from subjects s where subject_id = k.subject_id) = true then external_lab else external_descriptive end as external,
case when (select is_lab from subjects s where subject_id = k.subject_id) = true then total_lab_max else total_descriptive_max end as subject_total_max, 
case when (select is_lab from subjects s where subject_id = k.subject_id) = true then total_lab else total_descriptive end as subject_total
from 
(
	select p.subject_id, subject_name, 
	internal1_max, coalesce(internal1,0) as internal1, internal2_max, coalesce(internal2, 0) as internal2, 
	round( ((coalesce(internal1,0) + coalesce(internal2,0)) / 2) ,1) as internal, 
	external_max as external_descriptive_max, coalesce(external,0) as external_descriptive, lab_max as external_lab_max, coalesce(lab,0) as external_lab,
    (round( ((internal1_max + internal2_max) / 2) ,0) + lab_max) as total_lab_max, ( round( ((internal1_max + internal2_max) / 2) ,0) + external_max ) as total_descriptive_max,
    coalesce(( round( ((internal1 + internal2) / 2) ,0) + lab),0) as total_lab, coalesce(( round( ((internal1 + internal2) / 2) ,0) + external ),0) as total_descriptive
    from (
		select s.subject_id, s.subject_name,
		(select max_marks from exam_type where exam_type_id = 1) as internal1_max, sum(internal1) as internal1, 
		(select max_marks from exam_type where exam_type_id = 2) as internal2_max,  sum(internal2) as internal2, 
		(select max_marks from exam_type where exam_type_id = 3) as external_max,  sum(external) as external, 
		(select max_marks from exam_type where exam_type_id = 4) as lab_max, sum(lab) as lab
		from
		(
			select subject_id, exam_type_id,
			case when sm.exam_type_id = 1 then marks else 0 end as internal1,
			case when sm.exam_type_id = 2 then marks else 0 end as internal2, 
			case when sm.exam_type_id = 3 then marks else 0 end as external,
			case when sm.exam_type_id = 4 then marks else 0 end as lab
			from 
			student_marks sm
			where student_id = 64
		) t
		right join subjects s on (t.subject_id = s.subject_id )
		where s.semester_id = 1 and s.branch_id = '12'
		group by s.subject_name
	)p
)k
order by subject_id;

select 
subject_id, subject_name, internal1_max, internal1, internal2_max, internal2, internal, 
case when (select case when group_concat(et.exam_type_id) = '1,2,4' then true else false end as is_lab from exam_type et join subject_exam_types es on (es. exam_type_id = et.exam_type_id) where es.subject_id = k.subject_id) = true then external_lab_max else external_descriptive_max end as external_max, 
case when (select case when group_concat(et.exam_type_id) = '1,2,4' then true else false end as is_lab from exam_type et join subject_exam_types es on (es. exam_type_id = et.exam_type_id) where es.subject_id = k.subject_id) = true then external_lab else external_descriptive end as external,
case when (select case when group_concat(et.exam_type_id) = '1,2,4' then true else false end as is_lab from exam_type et join subject_exam_types es on (es. exam_type_id = et.exam_type_id) where es.subject_id = k.subject_id) = true then total_lab_max else total_descriptive_max end as subject_total_max, 
case when (select case when group_concat(et.exam_type_id) = '1,2,4' then true else false end as is_lab from exam_type et join subject_exam_types es on (es. exam_type_id = et.exam_type_id) where es.subject_id = k.subject_id) = true then total_lab else total_descriptive end as subject_total
from 
(
	select p.subject_id, subject_name, 
	internal1_max, coalesce(internal1,0) as internal1, internal2_max, coalesce(internal2, 0) as internal2, 
	round( ((coalesce(internal1,0) + coalesce(internal2,0)) / 2) ,1) as internal, 
	external_max as external_descriptive_max, coalesce(external,0) as external_descriptive, lab_max as external_lab_max, coalesce(lab,0) as external_lab,
    (round( ((internal1_max + internal2_max) / 2) ,0) + lab_max) as total_lab_max, ( round( ((internal1_max + internal2_max) / 2) ,0) + external_max ) as total_descriptive_max,
    coalesce(( round( ((internal1 + internal2) / 2) ,0) + lab),0) as total_lab, coalesce(( round( ((internal1 + internal2) / 2) ,0) + external ),0) as total_descriptive
    from (
		select s.subject_id, s.subject_name,
		(select max_marks from exam_type where exam_type_id = 1) as internal1_max, sum(internal1) as internal1, 
		(select max_marks from exam_type where exam_type_id = 2) as internal2_max,  sum(internal2) as internal2, 
		(select max_marks from exam_type where exam_type_id = 3) as external_max,  sum(external) as external, 
		(select max_marks from exam_type where exam_type_id = 4) as lab_max, sum(lab) as lab
		from
		(
			select subject_id, exam_type_id,
			case when sm.exam_type_id = 1 then marks else 0 end as internal1,
			case when sm.exam_type_id = 2 then marks else 0 end as internal2, 
			case when sm.exam_type_id = 3 then marks else 0 end as external,
			case when sm.exam_type_id = 4 then marks else 0 end as lab
			from 
			student_marks sm
			where student_id = 64
		) t
		right join subjects s on (t.subject_id = s.subject_id )
		where s.semester_id = 1 and s.branch_id = '12'
		group by s.subject_name
	)p
)k
order by subject_id;

select * from subjects;
update subjects set is_lab = true where subject_id in (7, 8, 9);
select * from exam_type;

select et.exam_type_id, et.exam_type, et.max_marks 
from exam_type et
join subject_exam_types es on (es. exam_type_id = et.exam_type_id)
where es.subject_id = 7;

select case when group_concat(et.exam_type_id) = '1,2,4' then true else false end as is_lab from exam_type et join subject_exam_types es on (es. exam_type_id = et.exam_type_id) where es.subject_id = 8;

insert into subject_exam_types(subject_id, exam_type_id)
select subject_id, 4 from subjects where is_lab = 1;

select count(*) from subject_exam_types;
select * from subject_exam_types;

select subject_id, 4 from subjects where is_lab = 0;

select * from users; 
select * from users where id = 217;
select * from user_roles;
select * from roles;

select id, user_name, password, disabled, user_desc, email, last_login, failed_login_attempts, last_password_change
from user_roles ur
join users u on (u.id = ur.user_id)
where role_id = 2;

create table user_role_id_mapping(
user_id int(11) not null,
role_id int(11) not null,
id int(11) not null
);

select * from employee;
delete from employee where emp_id = 221;

select * from roles;

select * from hibernate_sequence;

select * from users;

alter table users;

select s.student_name, b.branch_name, t.batch_name
from 
student_details s
join student_attendance sa on (sa.student_id = s.student_id)
join branch b on (b.branch_id = sa.branch_id)
join batch t on (t.batch_id = sa.batch_id);

select * from student_attendance;
delete from student_attendance where id = 102;

update student_attendance set id = 108 where id = 177;


select * from semester;

select semester_id as next_semester, semester_name from semester where semester_id = 7 + 1;
-- (select semester_id + 1 as semester_id from semester where semester_id = 2) t;

select * from student_marks;

select * from services;

select * from hibernate_sequence;

delete from student_marks where id > 220;

insert into services (service_id, service_url, service_name,disabled, parent_id, display_order, menu_display)
values(36, '/super/promoteStudent', 'Promote Student', 0, 12, 37, 1 );

update services set service_name = 'Student Promote', service_url = '/super/student/studentPromote' where service_id = 36;

select * from student_details where student_id in (3, 5, 63, 64, 116);

update student_details s
set current_year_id =1, current_semester_id = 1, promoted_date = null where student_id in (3, 5, 63, 64, 116);

update student_details s
set current_year_id = (select year_id from semester where semester_id = (s.current_semester_id + 1)),
current_semester_id = (select semester_id from semester where semester_id = (s.current_semester_id + 1)),
promoted_date = current_timestamp()
where s.student_id in (3, 5);

select semester_id + 1, year_id from semester where semester_id = (select current_semester_id from student_details where student_id = 3);

select * from semester; -- where semester_id = 1;
select current_timestamp();

select * from student_details where promoted_date is not null;

select count(sa.id) 
from student_attendance sa
join student_details sd on (sa.student_id = sd.student_id and sa.semester_id = sd.current_semester_id and sa.year_id = sd.current_year_id)
where sa.student_id = 3;
-- and semester_id = (select current_semester_id from student_details where student_id = 3 )

select case when count(sa.id) > 0 then sa.student_id else 0 end as student_id
from student_attendance sa
join student_details sd on (sa.student_id = sd.student_id and sa.semester_id = sd.current_semester_id and sa.year_id = sd.current_year_id)
where sa.student_id = 4;

select 
-- case when count(su.subject_id) > 0 then 0 else sd.student_id end as student_id
 su.subject_id, marks
 ,coalesce(case when count(su.subject_id) > 0 then 0 else sd.student_id end,0) as student_id
from subjects su
left join student_details sd on (sd.current_semester_id = su.semester_id and sd.branch_id = su.branch_id)
left join student_marks sm on (sm.student_id = sd.student_id and sm.semester_id = sd.current_semester_id and sm.year_id = sd.current_year_id and sm.branch_id = su.branch_id and sm.subject_id = su.subject_id and (exam_type_id = 3 or exam_type_id = 4 ))
where 
marks is null and
sd.student_id = 3;

select * from subjects;
select * from student_marks;

select * from exam_type;


select 
case when count(subj) = count(subm) then count(subj) else 0 end as student_id
from (
select 
su.subject_id as subj, sm.subject_id as subm, marks, exam_type_id
from subjects su
left join student_details sd on (sd.current_semester_id = su.semester_id and sd.branch_id = su.branch_id)
left join student_marks sm on (sm.student_id = sd.student_id and sm.semester_id = sd.current_semester_id and sm.year_id = sd.current_year_id and sm.branch_id = su.branch_id and sm.subject_id = su.subject_id)
where (exam_type_id = 3 or exam_type_id = 4 ) and marks is not null and
sd.student_id = 63
)t;


select 
su.subject_id as subj, sm.subject_id as subm, marks, exam_type_id
from subjects su
left join student_details sd on (sd.current_semester_id = su.semester_id and sd.branch_id = su.branch_id)
left join student_marks sm on (sm.student_id = sd.student_id and sm.semester_id = sd.current_semester_id and sm.year_id = sd.current_year_id and sm.branch_id = su.branch_id and sm.subject_id = su.subject_id)
where (exam_type_id = 3 or exam_type_id = 4) and 
-- marks is not null and
sd.student_id = 63;

select * from student_marks where student_id = 63;


select 
su.subject_id as subj, sm.subject_id as subm, marks, exam_type_id
from subjects su
left join student_details sd on (sd.current_semester_id = su.semester_id and sd.branch_id = su.branch_id)
left join student_marks sm on (sm.student_id = sd.student_id and sm.semester_id = sd.current_semester_id and sm.year_id = sd.current_year_id and sm.branch_id = su.branch_id and sm.subject_id = su.subject_id)
where (exam_type_id = 3 or exam_type_id = 4 ) and marks is not null and
sd.student_id = 63;

select case when sum(subj) = sum(subm) then subj else 0 end as student_id
from (
select count(su.subject_id) as subj, 0 as subm
from subjects su
left join student_details sd on (sd.current_semester_id = su.semester_id and sd.branch_id = su.branch_id)
where sd.student_id = 64
union all
select 0 as subj, count(sm.subject_id) as subm
from subjects su
left join student_details sd on (sd.current_semester_id = su.semester_id and sd.branch_id = su.branch_id)
left join student_marks sm on (sm.student_id = sd.student_id and sm.semester_id = sd.current_semester_id and sm.year_id = sd.current_year_id and sm.branch_id = su.branch_id and sm.subject_id = su.subject_id)
where (exam_type_id = 3 or exam_type_id = 4 ) and marks is not null and
sd.student_id = 3
)t;

select * from student_details where student_id in (3,4);

select * from branch;
select * from subjects;
select is_lab from subjects where subject_id = 1 and semester_id = 1;

select 
case when count(exam_type_id) = 0 then 1
when count(exam_type_id) = 1 then 2
when (count(exam_type_id) = 2 and sum(is_lab) = 0) then 3
when (count(exam_type_id) = 3 and sum(is_lab) = 0) then 0
when (count(exam_type_id) = 2 and sum(is_lab) = 2) then 4
when (count(exam_type_id) = 3 and sum(is_lab) = 3) then '-'
else 0
end as exam_type
from student_marks sm
left join subjects su on (su.subject_id = sm.subject_id and su.semester_id = sm.semester_id)
where student_id = 3
and sm.semester_id = 1 
and sm.year_id = 1 
and sm.branch_id = '12'
and sm.subject_id = 1;

select * from student_marks where student_id = 3 and subject_id = 7;
select * from student_details where student_id = 9;

select * from branch;

-- Batch wise
select bt.batch_name as batch, count(sd.student_id) as student_count
from batch bt 
left join student_details sd on (sd.batch_id = bt.batch_id)
group by bt.batch_name
order by bt.batch_name;

-- Branch wise
select b.branch_name, count(sd.student_id) as student_count
from branch b
left join student_details sd on (sd.branch_id = b.branch_id)
group by b.branch_name
order by b.branch_name;

-- Year wise
select y.year_name as year, count(sd.student_id) as student_count
from year y 
left join student_details sd on (sd.current_year_id = y.year_id)
group by y.year_name
order by y.year_name;

-- Year Semester wise
select y.year_name as year, coalesce(s.semester_name,'-') as semester, count(sd.student_id) as student_count
from year y 
left join student_details sd on (sd.current_year_id = y.year_id)
left join semester s on (s.semester_id = sd.current_semester_id)
group by y.year_name, s.semester_name
order by y.year_name, s.semester_name;

-- Year Semester wise - 2
select y.year_name as year, coalesce(s.semester_name,'-') as semester, s.semester_id, coalesce(sd.student_id, 0) as student_count
from semester s
left join student_details sd on (s.semester_id = sd.current_semester_id)
left join year y on (s.year_id = y.year_id)
group by s.semester_name, y.year_name
order by y.year_name, s.semester_name;

select * from branch;
-- Batch Branch wise
select bt.batch_name as batch, coalesce(b.branch_name, '-') as branch, count(sd.student_id) as student_count
from batch bt 
left join student_details sd on (sd.batch_id = bt.batch_id)
left join branch b on (sd.branch_id = b.branch_id)
group by bt.batch_name, b.branch_name
order by bt.batch_id, b.branch_name;

select * from year;
-- Batch Branch Year wise
select bt.batch_name as batch, coalesce(y.year_name,'-') as year, coalesce(b.branch_name, '-') as branch, count(sd.student_id) as student_count
from batch bt 
left join student_details sd on (sd.batch_id = bt.batch_id)
left join year y on (y.year_id = sd.current_year_id)
left join branch b on (sd.branch_id = b.branch_id)
group by bt.batch_name, b.branch_name, y.year_name
order by bt.batch_id, y.year_name, b.branch_name;

-- Batch Branch Year Semester wise
select bt.batch_name as batch, coalesce(b.branch_name, '-') as branch, coalesce(y.year_name,'-') as year, coalesce(s.semester_name,'-') as semester, count(sd.student_id) as student_count
from batch bt 
left join student_details sd on (sd.batch_id = bt.batch_id)
left join branch b on (sd.branch_id = b.branch_id)
left join year y on (y.year_id = sd.current_year_id)
left join semester s on (s.semester_id = sd.current_semester_id)
group by bt.batch_name, b.branch_name, y.year_name, s.semester_name
order by bt.batch_id, b.branch_name, y.year_name, s.semester_name;

-- Employee Qualfications wise
select q.qly_name, count(e.emp_id) as emp_count
from employee e 
left join qualifications q on (e. qly_id = q.qly_id)
group by q.qly_name
order by qly_name;

select * from employee;

select * from services;

select * from branch;

insert into services (service_id, service_url, service_name,disabled, parent_id, display_order, menu_display)
values(37, '/management/managementDashboard', 'Management Dashboard', 0, 23, 38, 1 );

select * from student_details;

select sum(students_total) as 1_students_total, sum(male) as 2_male, sum(female) as 3_female, sum(employees_count) as 4_employees_count
from (
	select sum(student_id) as students_total, sum(male) as male, sum(female) as female, 0 as employees_count
	from (
		select 
			case when student_id is not null then 1 else 0 end as student_id,
			case when gender = 'MALE' then 1 else 0 end as male,
			case when gender = 'FEMALE' then 1 else 0 end as female
		from student_details
	) t
	union all 
	select 0 as students_total, 0 as male, 0 as female, count(*) as employees_count from employee
)p;

select * from branch;

select 
'01' as civil_code,
'02' as eee_code,
'03' as mech_code,
'04' as ece_code,
'05' as cse_code,
'12' as it_code,

sum(civil) as civil, sum(eee) as eee, sum(mech) as mech, sum(ece) as ece, sum(cse) as cse, sum(it) as it
	from (
	select 
	case when branch_id = '01' then 1 else 0 end as civil,
	case when branch_id = '02' then 1 else 0 end as eee,
	case when branch_id = '03' then 1 else 0 end as mech,
	case when branch_id = '04' then 1 else 0 end as ece,
	case when branch_id = '05' then 1 else 0 end as cse,
	case when branch_id = '12' then 1 else 0 end as it
from student_details) t;

select b.branch_id, b.branch_name, count(sd.student_id)
from 
branch b
left join student_details sd on (sd.branch_id = b.branch_id)
group by b.branch_id, b.branch_name;

select student_name, student_id, DATE_FORMAT(doj, "%d %M, %Y") as doj, doj, timestamp(doj) from student_details 
order by timestamp(doj) desc;
-- DATE_FORMAT(doj, "%d %M %Y"), 
-- limit 5;

select student_name, roll_no, father_name, batch_name, year_name as current_year, semester_name as current_semester
from student_details sd
left join batch b on (b.batch_id = sd.batch_id)
left join year y on (y.year_id = sd.current_year_id)
left join semester s on (s.semester_id = sd.current_semester_id)
where branch_id = '01';

select student_name, roll_no, father_name, 
(select batch_name from batch b where b.batch_id = sd.batch_id) as batch_name, 
(select year_name from year y where y.year_id = sd.current_year_id) as current_year,
(select semester_name from semester s where s.semester_id = sd.current_semester_id) as current_semester
from student_details sd
where branch_id = '01';

select * from batch;

select count(*) from student_details;
select count(*) from student_details where gender = 'MALE';
select count(*) from student_details where gender = 'FEMALE';

update student_details set  gender = 'FEMALE' where student_id = 225;

SELECT batch_name, coalesce(branch_name, '-') as branch_name, coalesce(semester_name, '-') as semester_name, 
coalesce(round(avg(no_of_days),2),0) as no_of_days, coalesce(round(avg(days_present),2),0) as days_present 
FROM batch b
left join student_attendance sd on(sd.batch_id = b.batch_id)
left join branch br on (sd.branch_id = br.branch_id)
left join semester s on (s.semester_id = sd.semester_id)
group by batch_name, branch_name, semester_name;

SELECT batch_name, 
coalesce(case when sum(days_present)=0 then 0 else round((sum(days_present)/sum(no_of_days))*100,2) end, 0) as att_avg
FROM batch b
left join student_attendance sa on(sa.batch_id = b.batch_id)
group by batch_name

