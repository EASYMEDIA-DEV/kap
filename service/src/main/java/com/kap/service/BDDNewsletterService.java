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
     * 통합검색 뉴스레터 탭 조회
     */
    public BDDNewsletterDTO selectNewsletterTabList(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;

    /**
     * 뉴스레터 조회 갯수(통합검색)
     */
    public int selectNewsletterListCnt(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;

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

    /**
     * 뉴스레터 첨부파일 목록 조회
     */
    public BDDNewsletterDTO selectNewsletterFileList(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;

    /**
     * 뉴스레터 조회수 증가
     */
    public int updateNewsletterReadCnt(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;

    /**
     * 뉴스레터 이전, 다음 글 SEQ 조회
     */
    public BDDNewsletterDTO selectNextAndPrevSeqVal(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception;

}
