---- 1. user 테이블 생성
CREATE TABLE users (
    id VARCHAR2(100) PRIMARY KEY,
    password VARCHAR2(100) NOT NULL
);
-- 2. boards 테이블 생성
-- 기존 테이블 삭제 (필요한 경우만)
DROP TABLE boards CASCADE CONSTRAINTS;

-- 3. boards 테이블 생성
CREATE TABLE boards (
    boardNo NUMBER PRIMARY KEY,                       -- 게시물 번호, 기본 키
    title VARCHAR2(100) NOT NULL,                     -- 제목, 필수 입력, 최대 100자
    content VARCHAR2(1000) NOT NULL,                  -- 내용, 필수 입력, 최대 1000자
    writer VARCHAR2(100) NOT NULL,                    -- 작성자
    empno NUMBER,                                     -- 사원 번호
    regdate DATE DEFAULT SYSDATE                      -- 등록 날짜, 기본값 현재 날짜
);

-- 3. 시퀀스 삭제 및 생성 (필요한 경우만)
DROP SEQUENCE board_seq;
CREATE SEQUENCE board_seq START WITH 1 INCREMENT BY 1;

-- 4. 트리거 삭제 및 생성 (기존에 있다면 삭제하고 재생성)
DROP TRIGGER trg_board_no;
CREATE OR REPLACE TRIGGER trg_board_no
BEFORE INSERT ON boards
FOR EACH ROW
WHEN (NEW.boardNo IS NULL)
BEGIN
   SELECT board_seq.NEXTVAL INTO :NEW.boardNo FROM dual;
END;
/