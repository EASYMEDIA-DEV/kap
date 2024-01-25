package com.kap.service;

import com.kap.core.dto.wb.wbb.WBBATransDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanySearchDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbg.*;
import com.kap.core.dto.wb.wbh.WBHAApplyMstDTO;
import com.kap.core.dto.wb.wbh.WBHACalibrationSearchDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <pre>
 * 신청업체관리 Service
 * </pre>
 *
 * @ClassName		: WBGAExamService.java
 * @Description		: 신청업체관리 Service
 * @author 김대성
 * @since 2023.11.27
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.11		김대성				   최초 생성
 * </pre>
 */
public interface WBGAExamService {

    /**
     * 신청 목록을 조회한다.
     */
    public WBGAExamSearchDTO selectCalibrationList(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception;

    /**
     * 연도 상세 조회한다.
     */
    public List<String> selectYearDtl(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception;


    /**
     * 신청 리스트 삭제
     */
    public int carbonCompanyDeleteList(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception;

    /**
     * 자격 옵션을 조회한다.
     */
    public WBGAValidDTO selectExamValid(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception;

    /**
     * 자격 옵션을 수정.
     */
    public int examValidUpdate(WBGAValidDTO wBGAValidDTO, HttpServletRequest request) throws Exception;

    /**
     * 엑셀 다운로드
     */
    public void excelDownload(WBGAExamSearchDTO wBGAExamSearchDTO, HttpServletResponse response) throws Exception;

    /**
     * 부품사관리 상세 조회한다.
     * @return
     */
    public WBGAApplyMstDTO selectCompanyDtl(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception;

    /**
     * 회원 부품사정보 상세 조회한다.
     * @return
     */
    public WBGACompanyDTO selectCompanyUserDtl(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception;

    /**
     * 부품사 컨설팅 사업 리스트를 가져온다.
     */
    public WBGAConsultingDTO getConsultingList(WBGAConsultingDTO wBGAConsultingDTO) throws Exception;

    /**
     * 신청자를 등록한다.
     * @return
     */
    public int insert(WBGACompanyDTO wBGACompanyDTO, WBGAApplyMstDTO wBGAApplyMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 신청자를 수정한다.
     * @return
     */
    public int update(WBGACompanyDTO wBGACompanyDTO, WBGAApplyMstDTO wBGAApplyMstDTO, WBGAMsEuipmentDTO wBGAMsEuipmentDTO, HttpServletRequest request) throws Exception;

    /**
     * 참여 이관 로그를 가져온다.
     */
    public WBBATransDTO getTrnsfList(WBBATransDTO wbbTransDTO) throws Exception;

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception;

    /**
     * 최신 회차 목록을 조회한다.
     */
    public WBGAExamSearchDTO getRoundDtl(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception;

    /**
     * 신청가능여부 코드를 조회한다.
     */
    public int getApplyChecked(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception;

    /**
     * 신청자를 등록한다.
     * @return
     */
    public int insertApply(WBGAApplyMstDTO wbgaApplyMstDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception;

    /**
     *   신청자 정보 조회
     */
    public WBGAExamSearchDTO getApplyDtl(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception;

    /**
     * 사용자 신청자정보를 수정한다.
     * @return
     */
    public int updateInfo(WBGAApplyDtlDTO wbgaApplyDtlDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception;

    /**
     *  Edit Page
     *  관리자 메모 수정
     */
    public int updAdmMemo(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception;
}
