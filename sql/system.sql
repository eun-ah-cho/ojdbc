create user homepage identified by homepage; -- 사용자 계정 생성 
grant resource, connect to homepage; -- 접속권한과 일반 사용자 권한 제공 

alter user homepage default tablespace users; -- 테이블스페이스(db)는 users용으로 배정 
alter user homepage temporary tablespace temp; --임시용 db는 temp용으로 배정 
 
