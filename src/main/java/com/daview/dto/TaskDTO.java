package com.daview.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 숲: 요양사/간병인의 업무 활동 데이터 전송 객체
 * 나무: 돌봄 활동, 상담 내용, 특이사항을 포함하여 업무 패턴 분석
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    /**
     * 숲: 업무 이벤트 고유 식별자
     * 나무: UUID 형태로 업무 추적 및 중복 방지
     */
    private String eventId;

    /**
     * 숲: 업무 수행자 식별자
     * 나무: 요양사/간병인 구분 및 개인별 업무 패턴 분석
     */
    private String caregiverId;

    /**
     * 숲: 돌봄 대상자 식별자
     * 나무: 환자별 케어 이력 관리 및 연관 분석
     */
    private String patientId;

    /**
     * 숲: 업무 수행 시점
     * 나무: ISO 8601 형식으로 업무 일정 및 패턴 분석
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * 숲: 업무 유형 분류
     * 나무: VITAL_CHECK, MEDICATION, MEAL_ASSIST, EXERCISE, COUNSELING 등
     */
    private String taskType;

    /**
     * 숲: 업무 설명/내용
     * 나무: 자유 텍스트로 상세 활동 기록, GPT 해석용
     */
    private String description;

    /**
     * 숲: 업무 수행 시간 (분 단위)
     * 나무: 업무 효율성 분석 및 시간 배분 최적화
     */
    private Integer durationMinutes;

    /**
     * 숲: 업무 완료 상태
     * 나무: COMPLETED, PARTIAL, DELAYED, CANCELLED
     */
    private String status;

    /**
     * 숲: 업무 우선순위
     * 나무: HIGH, MEDIUM, LOW로 긴급도 표시
     */
    private String priority;

    /**
     * 숲: 환자 반응/상태
     * 나무: POSITIVE, NEUTRAL, NEGATIVE, UNRESPONSIVE
     */
    private String patientResponse;

    /**
     * 숲: 업무 복잡도 점수
     * 나무: 1-10 범위로 업무 난이도 표시, 스트레스 지표
     */
    private Integer complexityScore;

    /**
     * 숲: 특이사항 여부
     * 나무: true일 경우 GPT 해석 시 가중치 부여
     */
    private Boolean hasAlert;

    /**
     * 숲: 특이사항 내용
     * 나무: 응급상황, 이상 징후, 행동 변화 등 중요 관찰 사항
     */
    private String alertDescription;

    /**
     * 숲: 업무 관련 메모
     * 나무: 인수인계, 주의사항, 개선점 등 자유 기록
     */
    private String notes;

    /**
     * 숲: 추가 메타데이터
     * 나무: 위치 정보, 사용 도구, 협업자 등 확장 정보
     */
    private Map<String, Object> metadata;

    /**
     * 숲: 감정 상태 점수
     * 나무: 1-10 범위로 업무 수행 시 감정 상태, 번아웃 예측용
     */
    private Integer emotionalScore;
} 