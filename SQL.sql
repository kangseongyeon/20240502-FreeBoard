-- ��ü �Խ��� ��ȸ
SELECT NO, NAME, CONTENT, WRITER, REGDATE
FROM JAVA_FREEBOARD
WHERE DELYN = 'N'
ORDER BY NO;

-- �� �Խ���
SELECT NO, NAME, CONTENT, WRITER, REGDATE
FROM JAVA_FREEBOARD
WHERE NO = 1;

UPDATE JAVA_FREEBOARD
SET DELYN = 'Y'
WHERE NO = 1;

-- insert
INSERT INTO JAVA_FREEBOARD (NO, NAME, CONTENT, WRITER)
VALUES ((SELECT NVL(MAX(NO), 0) + 1 FROM JAVA_FREEBOARD), '����', '����', '�ۼ���');

-- update
UPDATE JAVA_FREEBOARD
SET NAME = '�̸�', CONTENT = '����'
WHERE NO = 1;

SELECT * FROM JAVA_FREEBOARD;