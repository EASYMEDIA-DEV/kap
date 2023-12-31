package com.kap.service;

import com.kap.core.dto.cb.cba.CBAConsultSuveyRsltListDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceUpdateDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * CBATechGuidanceService 서비스
 *
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *  2023.11.14      임서화             최초 생성
 * </pre>
 */
public interface CBATechGuidanceService {

    /**
     * 컨설팅 기술 지도 관리 리스트
     */
    public CBATechGuidanceInsertDTO selectTechGuidanceList(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;
    /**
     * 컨설팅 기술 지도 상세
     */
    public CBATechGuidanceInsertDTO selectTechGuidanceDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 관리 등록
     */
    public int insertTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 관리 삭제
     */
    public int deleteTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 관리 수정
     */
    public int updateTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO, CBATechGuidanceUpdateDTO cBATechGuidanceUpdateDTO) throws Exception;

    /**
     * 부품사 상세 정보 조회
     */
    public List<MPEPartsCompanyDTO> selectPartsCompanyDtl(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 만족도 종합 결과 리스트 조회
     */
    public CBAConsultSuveyRsltListDTO selectConsultSuveyRsltList(CBAConsultSuveyRsltListDTO pCBAConsultSuveyRsltListDTO) throws Exception;

    /**
     * 이관 내역 조회
     */
    public CBATechGuidanceInsertDTO selectTrsfGuidanceList(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

    /**
     * 엑셀 생성
     */
    void excelDownload(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO, HttpServletResponse response) throws Exception;


}
