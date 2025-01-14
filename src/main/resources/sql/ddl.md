# DDL 쿼리

## 테이블 적용 / 원복 쿼리

적용쿼리

```sql
CREATE SCHEMA pulley;
CREATE TABLE IF NOT EXISTS problems
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '문제 ID',
    title         VARCHAR(255) NOT NULL COMMENT '문제 제목',
    answer        VARCHAR(255) NOT NULL COMMENT '문제 정답',
    unit_code VARCHAR(20)  NOT NULL COMMENT '유형 정보',
    level     INT          NOT NULL COMMENT '난이도',
    type      VARCHAR(20)  NOT NULL COMMENT '문제 유형',
    created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
    created_by    VARCHAR(255) DEFAULT NULL COMMENT '생성자',
    updated_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일자',
    updated_by    VARCHAR(255) DEFAULT NULL COMMENT '수정자',
    INDEX idx_unit_code (unit_code),
    INDEX idx_level_type (level, type)
);

CREATE TABLE IF NOT EXISTS worksheets
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '학습지 ID',
    title         VARCHAR(255) NOT NULL COMMENT '학습지 제목',
    description   VARCHAR(5000) DEFAULT NULL COMMENT '학습지 설명',
    user_id       BIGINT NOT NULL COMMENT '생성한 유저 ID',
    created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
    created_by    VARCHAR(255) DEFAULT NULL COMMENT '생성자',
    updated_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일자',
    updated_by    VARCHAR(255) DEFAULT NULL COMMENT '수정자',
    INDEX idx_user_id (user_id)
);

CREATE TABLE IF NOT EXISTS worksheet_problems
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '매핑 ID',
    worksheet_id BIGINT NOT NULL COMMENT '학습지 ID',
    problem_id  BIGINT NOT NULL COMMENT '문제 ID',
    created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
    created_by    VARCHAR(255) DEFAULT NULL COMMENT '생성자',
    updated_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일자',
    updated_by    VARCHAR(255) DEFAULT NULL COMMENT '수정자',
    UNIQUE KEY unique_worksheet_problem (worksheet_id, problem_id),
    INDEX idx_worksheet_id (worksheet_id),
    INDEX idx_problem_id (problem_id)
);

CREATE TABLE IF NOT EXISTS user_worksheets
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '매핑 ID',
    user_id     BIGINT NOT NULL COMMENT '유저 ID',
    worksheet_id BIGINT NOT NULL COMMENT '학습지 ID',
    created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
    created_by    VARCHAR(255) DEFAULT NULL COMMENT '생성자',
    updated_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일자',
    updated_by    VARCHAR(255) DEFAULT NULL COMMENT '수정자',
    INDEX idx_user_id_worksheet (user_id, worksheet_id)
);

CREATE TABLE IF NOT EXISTS problem_correct_answers
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '정답 ID',
    problem_id  BIGINT NOT NULL COMMENT '문제 ID',
    answer      TEXT NULL COMMENT '주관식 정답',
    selection_id BIGINT NULL COMMENT '선택지 ID',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
    created_by  VARCHAR(255) DEFAULT NULL COMMENT '생성자',
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일자',
    updated_by  VARCHAR(255) DEFAULT NULL COMMENT '수정자',
    INDEX idx_problem_id (problem_id),
    INDEX idx_selection_id (selection_id)
);

CREATE TABLE IF NOT EXISTS user_problem_answers
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '응답 ID',
    user_id             BIGINT NOT NULL COMMENT '유저 ID',
    problem_id          BIGINT NOT NULL COMMENT '문제 ID',
    answer              TEXT NOT NULL COMMENT '유저 응답',
    answer_selection_id BIGINT NOT NULL COMMENT '선택지 ID',
    is_correct          BOOLEAN COMMENT '정답 여부',
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
    created_by          VARCHAR(255) DEFAULT NULL COMMENT '생성자',
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일자',
    updated_by          VARCHAR(255) DEFAULT NULL COMMENT '수정자',
    INDEX idx_problem_id (problem_id),
    INDEX idx_selection_id (answer_selection_id),
    INDEX idx_user_id (user_id)
);
```

원복쿼리

```sql
DROP TABLE IF EXISTS problems;
DROP TABLE IF EXISTS worksheets;
DROP TABLE IF EXISTS worksheet_problems;
DROP TABLE IF EXISTS user_worksheets;
DROP TABLE IF EXISTS user_problem_answers;
DROP TABLE IF EXISTS subjective_correct_answers;
DROP TABLE IF EXISTS selection_correct_answers;
```
