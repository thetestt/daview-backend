package com.daview.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 숲: 요양 대상자의 생체 신호 및 활동 데이터 전송 객체
 * 나무: 혈압, 심박수, 활동량 등을 포함하여 Kafka로 스트리밍
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VitalDTO {

    /**
     * 숲: 이벤트 고유 식별자
     * 나무: UUID 형태로 중복 처리 방지 및 추적 가능
     */
    private String eventId;

    /**
     * 숲: 요양 대상자 식별자
     * 나무: 개인별 Pulse 패턴 분석을 위한 키
     */
    private String userId;

    /**
     * 숲: 데이터 수집 시점
     * 나무: ISO 8601 형식으로 시계열 분석 지원
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * 숲: 수축기 혈압 (mmHg)
     * 나무: 정상 범위 90-140, 이상치 탐지용
     */
    private Integer systolicBp;

    /**
     * 숲: 이완기 혈압 (mmHg)
     * 나무: 정상 범위 60-90, 이상치 탐지용
     */
    private Integer diastolicBp;

    /**
     * 숲: 심박수 (bpm)
     * 나무: 정상 범위 60-100, 부정맥 감지 가능
     */
    private Integer heartRate;

    /**
     * 숲: 체온 (°C)
     * 나무: 정상 범위 36.1-37.2, 발열 감지용
     */
    private Double bodyTemperature;

    /**
     * 숲: 혈당 수치 (mg/dL)
     * 나무: 정상 범위 70-140, 당뇨 관리용
     */
    private Integer bloodGlucose;

    /**
     * 숲: 산소 포화도 (%)
     * 나무: 정상 범위 95-100, 호흡기 상태 모니터링
     */
    private Integer oxygenSaturation;

    /**
     * 숲: 일일 걸음 수
     * 나무: 활동량 평가 및 운동 능력 추적
     */
    private Integer dailySteps;

    /**
     * 숲: 수면 시간 (시간 단위)
     * 나무: 수면 패턴 분석으로 건강 상태 평가
     */
    private Double sleepHours;

    /**
     * 숲: 식사 섭취량 (%)
     * 나무: 0-100 범위로 영양 상태 모니터링
     */
    private Integer mealIntake;

    /**
     * 숲: 추가 메타데이터
     * 나무: 센서 타입, 측정 환경, 특이사항 등 확장 정보
     */
    private Map<String, Object> metadata;

    /**
     * 숲: 데이터 품질 점수
     * 나무: 0.0-1.0 범위로 신뢰도 표시, Flink 필터링 기준
     */
    private Double qualityScore;

    /**
     * 숲: 간병인/요양사 식별자
     * 나무: 데이터 입력자 추적 및 책임 소재 명확화
     */
    private String caregiverId;
} 