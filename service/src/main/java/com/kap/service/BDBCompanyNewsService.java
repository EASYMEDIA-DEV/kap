package com.kap.service;

import com.kap.core.dto.bd.bdb.BDBCompanyNewsDTO;

/**
 * BDBCompanyNewsService 서비스
 *
 * @author 장두석
 * @since 2023.11.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.20  장두석         최초 생성
 * </pre>
 */
public interface BDBCompanyNewsService {

    /**
     * 재단소식 조회
    */
    public BDBCompanyNewsDTO selectCompanyNewsList(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;

    /**
     * 통합검색 재단소식 조회
    */
    public BDBCompanyNewsDTO selectCompanyNewsTotalList(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;

    /**
     * 통합검색 재단소식 탭 조회
     */
    public BDBCompanyNewsDTO selectCompanyNewsTabList(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;
    
    /**
     * 재단소식 조회 갯수(통합검색)
     */
    public int selectCompanyNewsListCnt(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;

    /**
     * 재단소식 상세
     */
    public BDBCompanyNewsDTO selectCompanyNewsDtl(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;

    /**
     * 재단소식 등록
     */
    public int insertCompanyNews(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;

    /**
     * 재단소식 수정
     */
    public int updateCompanyNews(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;

    /**
     * 재단소식 삭제
     */
    public int deleteCompanyNews(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;

    /**
     * 재단소식 첨부파일 목록 조회
     */
    public BDBCompanyNewsDTO selectCompanyNewsFileList(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;

    /**
     * 재단소식 조회수 증가
     */
    public int updateCompanyNewsReadCnt(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;

    /**
     * 재단소식 이전, 다음 글 SEQ 조회
     */
    public BDBCompanyNewsDTO selectNextAndPrevSeqVal(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception;
}
