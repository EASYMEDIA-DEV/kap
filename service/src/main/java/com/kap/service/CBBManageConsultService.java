package com.kap.service;

import com.kap.core.dto.cb.cbb.CBBConsultSuveyRsltListDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultSearchDTO;

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
     * @param CBBManageConsultSearchDTO
     * @param response
     * @throws Exception
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
     * @param CBBConsultSuveyRsltListDTO
     * @param response
     * @throws Exception
     */
    void srvRsltExcelDownload(CBBConsultSuveyRsltListDTO CBBConsultSuveyRsltListDTO, HttpServletResponse response) throws Exception;
}
