package com.daview.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 숲: Care Pulse 이상 징후 알림 데이터 전송 객체
 * 나무: Flink 분석 결과와 GPT 해석을 포함하여 실시간 알림 전송
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PulseAlertDTO {

    /**
     * 숲: 알림 고유 식별자
     * 나무: UUID 형태로 알림 추적 및 중복 방지
     */
    private String alertId;

    /**
     * 숲: 대상자 식별자
     * 나무: 환자별 알림 관리 및 이력 추적
     */
    private String userId;

    /**
     * 숲: 담당 요양사 식별자
     * 나무: 책임자 식별 및 알림 라우팅
     */
    private String caregiverId;

    /**
     * 숲: 알림 생성 시점
     * 나무: ISO 8601 형식으로 시계열 분석 지원
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * 숲: 이상 점수
     * 나무: 0.0-1.0 범위로 Flink Autoencoder 출력, 0.7 초과 시 알림
     */
    private Double anomalyScore;

    /**
     * 숲: 알림 심각도 등급
     * 나무: LOW, MEDIUM, HIGH, CRITICAL로 우선순위 표시
     */
    private String severity;

    /**
     * 숲: 24시간 위험도 예측
     * 나무: 0.0-1.0 범위로 GPT Function Calling 결과
     */
    private Double riskScore;

    /**
     * 숲: GPT 해석 요약
     * 나무: 2줄 내외 한글 요약으로 UI 표시용
     */
    private String interpretation;

    /**
     * 숲: 추정 원인
     * 나무: GPT 분석 결과로 가능한 원인 목록
     */
    private List<String> possibleCauses;

    /**
     * 숲: 권장 조치사항
     * 나무: GPT 제안 행동 지침 목록
     */
    private List<String> recommendations;

    /**
     * 숲: 관련 바이탈 데이터
     * 나무: 알림 트리거가 된 생체신호 정보
     */
    private Map<String, Object> triggerData;

    /**
     * 숲: 관련 업무 데이터
     * 나무: 알림 트리거가 된 업무 활동 정보
     */
    private Map<String, Object> relatedTasks;

    /**
     * 숲: 알림 처리 상태
     * 나무: PENDING, ACKNOWLEDGED, RESOLVED, IGNORED
     */
    private String status;

    /**
     * 숲: 알림 확인 시점
     * 나무: 요양사가 알림을 확인한 시간, 응답 시간 측정용
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime acknowledgedAt;

    /**
     * 숲: 알림 확인자
     * 나무: 알림을 확인한 요양사 식별자
     */
    private String acknowledgedBy;

    /**
     * 숲: 처리 메모
     * 나무: 요양사가 작성한 대응 내용 및 결과
     */
    private String responseNotes;

    /**
     * 숲: 추가 메타데이터
     * 나무: 알림 생성 환경, 디바이스 정보 등 확장 정보
     */
    private Map<String, Object> metadata;

    /**
     * 숲: 연관 알림 목록
     * 나무: 동일 환자의 관련 알림 ID 목록, 패턴 분석용
     */
    private List<String> relatedAlerts;

    /**
     * 숲: 자동 에스컬레이션 여부
     * 나무: true일 경우 상급자나 의료진에게 자동 알림
     */
    private Boolean autoEscalate;

    /**
     * 숲: 에스컬레이션 지연 시간 (분)
     * 나무: 미확인 시 상급자 알림까지의 대기 시간
     */
    private Integer escalationDelay;
} 