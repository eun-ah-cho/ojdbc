CREATE TABLE board (
  bno NUMBER(5) PRIMARY KEY,
  btitle NVARCHAR2(30) NOT NULL,
  bcontent NVARCHAR2(1000) NOT NULL,
  bname NVARCHAR2(10),  -- 작성자 ID, NULL 허용
  bdate DATE NOT NULL
);

CREATE SEQUENCE board_seq INCREMENT BY 1 START WITH 1 NOCYCLE NOCACHE
--시퀀스 객체는 이미 생성해서 중복안해도됨. board_seq 같이 쓰면됨 


CREATE TABLE member(
mno  NUMBER(5) NOT NULL, --연속번호 시퀀스 설정 
mname NVARCHAR2(10) NOT NULL, --회원이름 
id NVARCHAR2(10) PRIMARY KEY, -- id board 테이블의 BNAME와 FK 관계설정 하려고함
pw NVARCHAR2(10) NOT NULL, --pw 
regidate DATE DEFAULT sysdate NOT NULL --날짜 
)

--회원탈퇴시 게시글은 그대로이고 회원만 NULL값설정 
ALTER TABLE board ADD CONSTRAINT board_member_fk FOREIGN KEY (bname) REFERENCES member(id) -- board 테이블은 member의 자식테이블로 member에 mname과 board bwriter를 관계 설정(외래키) 
ALTER TABLE board MODIFY BNAME NVARCHAR2(10) NULL
SELECT constraint_name FROM user_constraints WHERE table_name = 'BOARD' AND constraint_type = 'R'
ALTER TABLE board DROP CONSTRAINT board_member_fk
ALTER TABLE board ADD CONSTRAINT board_member_fk FOREIGN KEY (bname) REFERENCES member(id) ON DELETE SET NULL


DROP TABLE board CASCADE CONSTRAINTS




INSERT INTO member (mno, mname, id, pw) VALUES (board_seq.nextval, '김기원', 'kkw','1234')
INSERT INTO member (mno, mname, id, pw) VALUES (board_seq.nextval, '이재상', 'ljs','1234')
INSERT INTO member (mno, mname, id, pw) VALUES (board_seq.nextval, '최준오', 'cjo','1234')
INSERT INTO member (mno, mname, id, pw) VALUES (board_seq.nextval, '이은진', 'lej','1234')
INSERT INTO member (mno, mname, id, pw) VALUES (board_seq.nextval, '윤제석', 'yjs','1234')
INSERT INTO member (mno, mname, id, pw) VALUES (board_seq.nextval, '최장옥', 'cjo','1234')
INSERT INTO member (mno, mname, id, pw) VALUES (board_seq.nextval, '김채하', 'kch','1234')

INSERT INTO board (bno, btitle, bcontent, bname, bdate) VALUES (board_seq.nextval, '안녕하세요', '방갑습니다.','kkw', sysdate)
INSERT INTO board (bno, btitle, bcontent, bname, bdate) VALUES (board_seq.nextval, '점심시간', '점심은 즐거워','ljs', sysdate)
INSERT INTO board (bno, btitle, bcontent, bname, bdate) VALUES (board_seq.nextval, '졸리다', '왜이렇게 졸리지','cjo', sysdate)
INSERT INTO board (bno, btitle, bcontent, bname, bdate) VALUES (board_seq.nextval, '오늘은', '금요일이다','lej', sysdate)
INSERT INTO board (bno, btitle, bcontent, bname, bdate) VALUES (board_seq.nextval, '안녕히계세요', '잘있어요','kch', sysdate)
INSERT INTO board (bno, btitle, bcontent, bname, bdate) VALUES (board_seq.nextval, '저녁시간', '저녁은 밥하기 귀찮아','yjs1', sysdate)


SELECT b.bno, b.title,bconetent,b.name, b.bdate FROM board b JOIN member m ON b.bname = m.id
select b.bname as 작성자 , m.mname as 아이디 from b.bname join 

SELECT e.ename, d.dname FROM emp e JOIN dept d ON e.deptno = d.deptno;

SELECT * FROM board
DELETE * FROM board 
DROP SEQUENCE board_seq
select *  from member order by mno desc
SELECT * FROM member 
DELETE  FROM member 
DROP TABLE member 
drop table board




SELECT constraint_name
FROM user_constraints 
WHERE table_name = 'BOARD' AND constraint_type = 'R';

ALTER TABLE board DROP CONSTRAINT BOARD_MEMBER_FK
ALTER TABLE board 
ADD CONSTRAINT board_member_fk 
FOREIGN KEY (bname) REFERENCES member(id)
ON DELETE SET NULL

DESC board
ALTER TABLE board MODIFY bname NVARCHAR2(10)
DELETE FROM member WHERE id = 'kkw'

SELECT bno, btitle, bcontent,bname, bdate FROM board ORDER BY bdate DESC
SELECT b.*,  m.bwriter from member m inner join board b on m.id = b.bwriter where id ='kkw'