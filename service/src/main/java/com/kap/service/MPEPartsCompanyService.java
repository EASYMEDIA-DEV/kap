package com.kap.service;

import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultListDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.wb.wbg.WBGAExamSearchDTO;
import com.kap.core.dto.wb.wbj.WBJAcomSearchDTO;

import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 부품사 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: MPEPartsCompanyService.java
 * @Description		: 부품사 관리를 위한 Service
 * @author 구은희
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		구은희				   최초 생성
 * </pre>
 */
public interface MPEPartsCompanyService {

    /**
     * 부품사 목록을 조회한다.
     */
    public MPEPartsCompanyDTO selectPartsCompanyList(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 개수를 조회한다.
     */
    public int selectPartsCompanyCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 SQ목록을 조회한다.
     */
    public MPEPartsCompanyDTO selectPartsComSQInfo(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 상세를 조회한다.
     */
    public MPEPartsCompanyDTO selectPartsCompanyDtl(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 사업자등록번호 등록여부를 확인한다.
     */
    public MPEPartsCompanyDTO checkBsnmNo(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사를 등록한다.
     */
    public int insertPartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사를 수정한다.
     */
    public int updatePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사를 삭제한다.
     */
    public int deletePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 교육사업 개수를 조회한다.
     */
    public int selectEduCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 컨설팅사업 개수를 조회한다.
     */
    public int selectConsultingCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 상생사업 개수를 조회한다.
     */
    public int selectWinBusinessCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 교육사업 연도별 개수 목록을 조회
     */
    public MPEPartsCompanyDTO selectEduStatisticsCntList(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 컨설팅 목록(기술지도)을 조회
     */
    public CBATechGuidanceInsertDTO selectTechGuidanceList(CBATechGuidanceInsertDTO cbaTechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 목록(경영컨설팅)을 조회
     */
    public CBBManageConsultListDTO selectManageConsultList(CBBManageConsultListDTO cbbManageConsultListDTO) throws Exception;


    /**
     * 자금지원 목록을 조회
     */
    public WBGAExamSearchDTO selectFundingList(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception;


    /**
     * 자동차부품산업대상 목록을 조회
     */
    public WBJAcomSearchDTO selectCarTargetList(WBJAcomSearchDTO wbjAcomSearchDTO) throws Exception;

    /**
     * 엑셀 생성
     */
    public void excelDownload(MPEPartsCompanyDTO mpePartsCompanyDTO, HttpServletResponse response) throws Exception;

    /**
     * 등록된 부품사 회원이 있는지 체크
     */
    public int selectMemberPartsSocietyExistsCheck(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;
}
