package com.kap.service;

import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;

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
     * 목록을 조회한다.
     */
    public EBCVisitEduDTO selectVisitEduList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;


    /**
     * 방문교육 상세 조회
     */
    public EBCVisitEduDTO selectVisitEduDtl(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

    /**
     * 목록을 조회
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
    public void excelDownload(EBCVisitEduDTO ebcVisitEduDTO, HttpServletResponse response) throws Exception;

}
