# DML 쿼리

## 테이블 적용 / 원복 쿼리

적용쿼리

```sql
# 1) 문제 생성
INSERT INTO problems (id, unit_code, level, type)
VALUES (1, 'UC1580', 1, 'SELECTION'),
       (2, 'UC1580', 1, 'SELECTION'),
       (3, 'UC1580', 1, 'SELECTION'),
       (4, 'UC1580', 1, 'SELECTION'),
       (5, 'UC1580', 1, 'SELECTION'),
       (6, 'UC1580', 1, 'SUBJECTIVE'),
       (7, 'UC1580', 1, 'SUBJECTIVE'),
       (8, 'UC1580', 1, 'SUBJECTIVE'),
       (9, 'UC1580', 1, 'SUBJECTIVE');

# 2) 학습지 생성
INSERT INTO worksheets (CREATED_AT, ID, UPDATED_AT, USER_ID, CREATED_BY, DESCRIPTION, TITLE, UPDATED_BY)
VALUES ('2025-01-13 00:04:32.599896', 1, '2025-01-13 00:04:32.599896', 1, null, '난이도 하 수학의 정석 연습문제 모음 입니다.', '수학의 정석 연습문제 모음', null);

# 3) 학습지 문제 매핑
INSERT INTO worksheet_problems (worksheet_id, problem_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9);

# 4) 학습지 정답 지정 
INSERT INTO problem_correct_answers (problem_id, answer, selection_id)
VALUES (1, null, 1),
       (2, null, 2),
       (3, null, 3),
       (4, null, 4),
       (5, null, 5),
       (6, '정답 6', null),
       (7, '정답 7', null),
       (8, '정답 8', null),
       (9, '정답 9', null);
```

원복쿼리

```sql
DELETE FROM problems WHERE id BETWEEN 1 AND 1531;
```
