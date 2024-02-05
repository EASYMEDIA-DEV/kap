package com.kap.service.dao;

import com.kap.core.dto.eb.EBScheduleGuideDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 배치 스케줄
 * </pre>
 *
 * @ClassName		: COScheduleMapper.java
 * @Description		: 배치 스케줄
 * @author 장두석
 * @since 2024.01.24
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2024.01.24	  장두석	             최초 생성
 * </pre>
 */
@Mapper
public interface COScheduleMapper {

    /**
     * 교육 시작 후 해당 교육의 선발대기 상태인 신청자 상태 미선발로 변경
     */
    public int updateStrtEduPtcptStts();

    /**
     * 퇴실 안한사람 자동 퇴실처리
     */
    public int updatAtndcInfo();

    /**
     * 교육 시작 3일 전 교육 일정 안내 대상자 조회
     */
    public List<EBScheduleGuideDTO> selectSubjectList();



}
