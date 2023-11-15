package com.kap.service;

import com.kap.core.dto.SMGWinBusinessDTO;

/**
 * SMGWinBusinessService 서비스
 *
 * @author 임서화
 * @since 2023.10.16
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.10.16  임서화         최초 생성
 * </pre>
 */
public interface SMGWinBusinessService {

    /**
     * 상생사업 관리 리스트
     */
    public SMGWinBusinessDTO selectWinBusinessList(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

    /**
     * 상생사업 상세
     */
    public SMGWinBusinessDTO selectWinBusinessDtl(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

    /**
     * 상생사업 관리 등록
     */
    public int insertWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

    /**
     * 상생사업 관리 삭제
     */
    public int deleteWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

    /**
     * 상생사업 관리 수정
     */
    public int updateWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

    /**
     * 상생사업 관리 정렬 수정
     */
    public void updateOrder(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception;

}
