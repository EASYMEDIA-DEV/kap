package com.kap.service;

import com.kap.core.dto.bd.bdd.BDDNewsletterDTO;

/**
 * BDDNewsletterService 서비스
 *
 * @author 장두석
 * @since 2023.11.22
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.22  장두석         최초 생성
 * </pre>
 */
public interface BDDNewsletterService {

    /**
     * 뉴스레터 조회
    */
    public BDDNewsletterDTO selectNewsletterList(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;

    /**
     * 뉴스레터 상세
     */
    public BDDNewsletterDTO selectNewsletterDtl(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;

    /**
     * 뉴스레터 등록
     */
    public int insertNewsletter(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;

    /**
     * 뉴스레터 수정
     */
    public int updateNewsletter(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;

    /**
     * 뉴스레터 삭제
     */
    public int deleteNewsletter(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;

}
