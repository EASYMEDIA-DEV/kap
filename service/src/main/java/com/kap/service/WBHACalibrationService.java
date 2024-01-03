package com.kap.service;

import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.WBBATransDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanySearchDTO;
import com.kap.core.dto.wb.wbh.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <pre>
 * 신청업체관리 Service
 * </pre>
 *
 * @ClassName		: WBHACalibrationService.java
 * @Description		: 신청업체관리 Service
 * @author 김태훈
 * @since 2023.11.27
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.11		김태훈				   최초 생성
 * </pre>
 */
public interface WBHACalibrationService {

    /**
     * 신청 목록을 조회한다.
     */
    public WBHACalibrationSearchDTO selectCalibrationList(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;

    /**
     * 연도 상세 조회한다.
     */
    public List<String> selectYearDtl(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;


    /**
     * 신청 리스트 삭제
     */
    public int carbonCompanyDeleteList(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;

    /**
     * 자격 옵션을 조회한다.
     */
    public WBHAValidDTO selectExamValid(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;

    /**
     * 자격 옵션을 수정.
     */
    public int examValidUpdate(WBHAValidDTO wBGAValidDTO, HttpServletRequest request) throws Exception;

    /**
     * 엑셀 다운로드
     */
    public void excelDownload(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO, HttpServletResponse response) throws Exception;

    /**
     * 부품사관리 상세 조회한다.
     * @return
     */
    public WBHAApplyMstDTO selectCompanyDtl(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;

    /**
     * 회원 부품사정보 상세 조회한다.
     * @return
     */
    public WBHACompanyDTO selectCompanyUserDtl(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;

    /**
     * 부품사 컨설팅 사업 리스트를 가져온다.
     */
    public WBHAConsultingDTO getConsultingList(WBHAConsultingDTO wbaConsultingDTO) throws Exception;

    /**
     * 신청자를 등록한다.
     * @return
     */
    public int insert(WBHACompanyDTO wbhaCompanyDTO, WBHAApplyMstDTO wbhaApplyMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 신청자를 수정한다.
     * @return
     */
    public int update(WBHACompanyDTO wbhaCompanyDTO, WBHAApplyMstDTO wbhaApplyMstDTO, WBHAMsEuipmentDTO wbhaMsEuipmentDTO, HttpServletRequest request) throws Exception;

    /**
     * 참여 이관 로그를 가져온다.
     */
    public WBBATransDTO getTrnsfList(WBBATransDTO wbbTransDTO) throws Exception;

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;

    /**
     * 최신 회차 목록을 조회한다.
     */
    public WBHACalibrationSearchDTO getRoundDtl(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception;
}
