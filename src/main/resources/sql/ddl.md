# DDL 쿼리

## 테이블 적용 / 원복 쿼리

적용쿼리

```sql
CREATE SCHEMA pulley;
CREATE TABLE IF NOT EXISTS problems
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '문제 ID',
    unit_code VARCHAR(20)  NOT NULL COMMENT '유형 정보',
    level     INT          NOT NULL COMMENT '난이도',
    type      VARCHAR(20)  NOT NULL COMMENT '문제 유형',
    answer    VARCHAR(255) NOT NULL COMMENT '정답'
);
```

원복쿼리

```sql
DROP TABLE IF EXISTS problems;
```
