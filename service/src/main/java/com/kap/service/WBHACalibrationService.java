package com.kap.service;

import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbb.WBBACompanyDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbb.WBBATransDTO;
import com.kap.core.dto.wb.wbh.*;

import javax.servlet.http.HttpServletRequest;

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
}
