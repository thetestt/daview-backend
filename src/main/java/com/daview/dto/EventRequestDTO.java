package com.daview.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 숲: 클라이언트에서 서버로 이벤트 전송 요청 객체
 * 나무: 바이탈과 업무 데이터를 배치로 전송하여 Kafka 처리
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {

    /**
     * 숲: 요청 고유 식별자
     * 나무: 클라이언트 측 중복 요청 방지 및 추적
     */
    private String requestId;

    /**
     * 숲: 요청 전송 시점
     * 나무: 클라이언트 타임스탬프로 네트워크 지연 보정
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime clientTimestamp;

    /**
     * 숲: 바이탈 사인 데이터 목록
     * 나무: 여러 환자의 생체신호를 배치로 전송
     */
    private List<VitalDTO> vitals;

    /**
     * 숲: 업무 활동 데이터 목록
     * 나무: 여러 업무 이벤트를 배치로 전송
     */
    private List<TaskDTO> tasks;

    /**
     * 숲: 클라이언트 식별자
     * 나무: React 앱, 모바일 앱 등 클라이언트 구분
     */
    private String clientId;

    /**
     * 숲: 클라이언트 버전
     * 나무: 호환성 검사 및 기능 지원 확인
     */
    private String clientVersion;

    /**
     * 숲: 우선순위 처리 여부
     * 나무: true일 경우 Kafka 고우선순위 파티션으로 전송
     */
    private Boolean priority;
} 