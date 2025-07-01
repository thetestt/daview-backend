package com.daview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.daview.dto.ReservationDTO;

@Mapper
public interface ReservationMapper {
	int insertReservation(ReservationDTO reservation);

	List<ReservationDTO> selectReservationById(Long memberId);

	int deleteReservation(String rsvId);

	int deleteAllReservation();
	
	int updateReservationCount(String rsvId, int rsvCnt);
	
	int updateReservationStatus(@Param("rsvId") String rsvId, @Param("rsvType") int rsvType);
	
	List<ReservationDTO> selectReservationByPaymentId(String pymId);
}
