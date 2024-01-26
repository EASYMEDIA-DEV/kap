package com.kap.service;

import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;
import com.kap.core.dto.eb.ebc.EBCVisitEduExcelDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <pre>
 * 방문교육 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: EBCVisitEduService.java
 * @Description		: 방문교육 관리를 위한 Service
 * @author 구은희
 * @since 2023.11.30
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.30		구은희				   최초 생성
 * </pre>
 */
public interface EBCVisitEduService {

    /**
     * 방문교육 신청 목록을 조회한다.
     */
    public EBCVisitEduDTO selectVisitEduList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

    /**
     * 방문교육 신청 이관이력 목록을 조회한다.
     */
    public EBCVisitEduDTO selectTrsfVisitEduList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

    /**
     * 방문교육 신청 상세 조회
     */
    public EBCVisitEduDTO selectVisitEduDtl(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

    /**
     * 방문교육 신청분야 목록을 조회
     */
    public List<EBCVisitEduDTO> selectAppctnTypeList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

    /**
     * 방문교육 강사를 조회
     */
    public List<EBCVisitEduDTO> selectIsttrList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

    /**
     * 방문교육 교육실적 조회
     */
    public List<EBCVisitEduDTO> selectResultOpList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

    /**
     * 방문교육 수정
     */
    public int updateVisitEdu(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

    /**
     * 부품사 정보 조회
     */
    public List<MPEPartsCompanyDTO> selectPartsCompanyDtl(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 엑셀 생성
     */
    public void excelDownload(EBCVisitEduExcelDTO ebcVisitEduExcelDTO, HttpServletResponse response) throws Exception;

    /**
     *  엑셀 목록을 조회한다.
     */
    public EBCVisitEduExcelDTO selectExcelList(EBCVisitEduExcelDTO ebcVisitEduExcelDTO) throws Exception;

    /**
     * 방문교육 엑셀 강사 목록을 조회
     */
    public EBCVisitEduExcelDTO selectIsttrExcelList(EBCVisitEduExcelDTO ebcVisitEduExcelDTO) throws Exception;

    /**
     * 방문교육 신청정보를 등록
     */
    public int applyVisitEduInfo(EBCVisitEduDTO ebcVisitEduDTO, MultipartHttpServletRequest multiRequest) throws Exception;

    /**
     * 방문교육 신청일시 값 조회
     */
    public EBCVisitEduDTO selectVisitEduApplyRegDtm(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

    /**
     * 사용자 방문교육 신청자정보를 조회
     */
    public EBCVisitEduDTO selectApplicantInfo(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

    /**
     * 신청한 방문교육을 교육취소로 변경
     */
    public void updateVisitEduApplyCancel(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

}
