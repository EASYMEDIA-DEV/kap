package com.kap.service;

import com.kap.core.dto.cb.cbb.CBBConsultSuveyRsltListDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultInsertDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultSearchDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultUpdateDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * 컨설팅 사업 경영컨설팅 서비스
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.21  이옥정         최초 생성
 * </pre>
 */
public interface CBBManageConsultService {
    /**
     * 리스트 조회
     */
    public CBBManageConsultSearchDTO selectManageConsultList(CBBManageConsultSearchDTO CBBManageConsultSearchDTO) throws Exception;

    /**
     * 엑셀 생성
     */
    void excelDownload(CBBManageConsultSearchDTO CBBManageConsultSearchDTO, HttpServletResponse response) throws Exception;

    /**
     * 만족도 종합 결과 리스트 조회
     */
    public CBBConsultSuveyRsltListDTO selectConsultSuveyRsltList(CBBConsultSuveyRsltListDTO CBBConsultSuveyRsltListDTO) throws Exception;

    /**
     * 만족도 종합 결과 엑셀 상세 조회
     */
    public CBBConsultSuveyRsltListDTO selectConsultSuveyRsltDtlExcel(CBBConsultSuveyRsltListDTO CBBConsultSuveyRsltListDTO) throws Exception;

    /**
     * 만족도 종합 결과 엑셀 생성
     */
    void srvRsltExcelDownload(CBBConsultSuveyRsltListDTO CBBConsultSuveyRsltListDTO, HttpServletResponse response) throws Exception;

    /**
     * 경영 컨설팅 관리 수정
     */
    public CBBManageConsultInsertDTO selectManageConsultDtl(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception;

    /**
     * 경영 컨설팅 관리 등록 (관리자)
     */
    public int insertManageConsult(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception;

    /**
     * 경영 컨설팅 관리 등록 (사용자)
     */
    public int insertUserManageConsult(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO, MultipartHttpServletRequest multiRequest) throws Exception;

    /**
     * 경영 컨설팅 관리 수정
     */
    public int updateManageConsultDtl(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO, CBBManageConsultUpdateDTO pCBBManageConsultUpdateDTO) throws Exception;
    
    /**
     * 이관 내역 리스트
     */
    public CBBManageConsultInsertDTO selectTrsfGuidanceList(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO, CBBManageConsultSearchDTO pCBBManageConsultSearchDTO) throws Exception;

    /**
     * 컨설팅사업 경영컨설팅 삭제
     */
    public int deleteManageConsult(CBBManageConsultSearchDTO CBBManageConsultSearchDTO) throws Exception;

    /**
     * 컨설팅사업 등록 사용자
     */
    public int insertUserManageCmssrInfoConsult(CBBManageConsultInsertDTO cBBManageConsultInsertDTO) throws Exception;

}
