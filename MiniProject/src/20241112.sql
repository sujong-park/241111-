select * from boards;

CREATE TABLE boards (
    boardNo NUMBER PRIMARY KEY,                       -- 게시물 번호, 기본 키
    title VARCHAR2(100) NOT NULL,                     -- 제목, 필수 입력, 최대 100자
    content VARCHAR2(1000) NOT NULL,                  -- 내용, 필수 입력, 최대 1000자
    writer VARCHAR2(100) NOT NULL,                    -- 작성자, users 테이블의 id 참조
    empno NUMBER,                                     -- 사원 번호, emp 테이블 참조
    regdate DATE DEFAULT SYSDATE,                     -- 등록 날짜, 기본값 현재 날짜
    CONSTRAINT fk_writer FOREIGN KEY (writer) REFERENCES users(id), -- users 테이블의 id 참조
    CONSTRAINT fk_empno FOREIGN KEY (empno) REFERENCES emp(empno)   -- emp 테이블의 empno 참조
);


CREATE TABLE users (
    id VARCHAR2(100) PRIMARY KEY,
    password VARCHAR2(100) NOT NULL
);

ALTER TABLE boards
DROP CONSTRAINT fk_writer;

ALTER TABLE boards
ADD CONSTRAINT fk_writer 
FOREIGN KEY (writer) 
REFERENCES users(id) 
ON DELETE CASCADE;

select * from users;