package com.kap.service;

import com.kap.core.dto.cb.cba.CBATechGuidanceDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;

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
    public CBATechGuidanceDTO selectTechGuidanceList(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception;

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
    public int deleteTechGuidance(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception;

    /**
     * 컨설팅 기술 지도 관리 수정
     */
    public int updateTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception;

}
