package com.pulley.freewheelin.domain.entity

import com.pulley.freewheelin.application.worksheet.request.StudentProblemAnswerRequest
import com.pulley.freewheelin.domain.entity.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "user_problem_answers",
    uniqueConstraints = [
        jakarta.persistence.UniqueConstraint(
            columnNames = ["user_id", "problem_id", "answerSelectionId"],
            name = "user_problem_answers_user_id_problem_id_answerSelectionId_unique"
        ), jakarta.persistence.UniqueConstraint(
            columnNames = ["user_id", "problem_id", "answer"],
            name = "user_problem_answers_user_id_problem_id_answer_unique"
        )
    ],
    indexes = [
        Index(name = "idx_user_id", columnList = "user_id"),
        Index(name = "idx_problem_id", columnList = "problem_id"),
        Index(name = "idx_answer_selection_id", columnList = "answer_selection_id"),
    ]
)
class StudentProblemAnswerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
    val userId: Long,
    val problemId: Long,
    val answer: String?,
    val answerSelectionId: Long?,
    val isCorrect: Boolean,
) : BaseEntity() {
    companion object {
        fun of(
            dto: StudentProblemAnswerRequest,
            userId: Long,
            isCorrect: Boolean,
        ): StudentProblemAnswerEntity {
            return StudentProblemAnswerEntity(
                problemId = dto.problemId,
                answer = dto.answer,
                answerSelectionId = dto.answerSelectionId,
                userId = userId,
                isCorrect = isCorrect,
            )
        }
    }
}
