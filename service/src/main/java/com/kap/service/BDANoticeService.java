package com.kap.service;

import com.kap.core.dto.bd.bda.BDANoticeDTO;

/**
 * BDANoticeService 서비스
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
public interface BDANoticeService {

    /**
     * 공지사항 조회
    */
    public BDANoticeDTO selectNoticeList(BDANoticeDTO pBDANoticeDTO) throws Exception;

    /**
     * 공지사항 탭 조회
     */
    public BDANoticeDTO selectNoticeTabList(BDANoticeDTO pBDANoticeDTO) throws Exception;

    /**
     * 공지사항 조회 갯수(통합검색용)
     */
    public int selectNoticeListCnt(BDANoticeDTO pBDANoticeDTO) throws Exception;

    /**
     * 공지사항 상세
     */
    public BDANoticeDTO selectNoticeDtl(BDANoticeDTO pBDANoticeDTO) throws Exception;

    /**
     * 공지사항 등록
     */
    public int insertNotice(BDANoticeDTO pBDANoticeDTO) throws Exception;

    /**
     * 공지사항 수정
     */
    public int updateNotice(BDANoticeDTO pBDANoticeDTO) throws Exception;

    /**
     * 공지사항 삭제
     */
    public int deleteNotice(BDANoticeDTO pBDANoticeDTO) throws Exception;

    /**
     * 공지사항 첨부파일 목록 조회
     */
    public BDANoticeDTO selectNoticeFileList(BDANoticeDTO pBDANoticeDTO) throws Exception;

    /**
     * 공지사항 조회수 증가
     */
    public int updateNoticeReadCnt(BDANoticeDTO pBDANoticeDTO) throws Exception;

    /**
     * 공지사항 이전, 다음 글 SEQ 조회
     */
    public BDANoticeDTO selectNextAndPrevSeqVal(BDANoticeDTO pBDANoticeDTO) throws Exception;

    /**
     * 공지사항 중요공지 목록을 조회
     */
    public BDANoticeDTO selectMainPostList(BDANoticeDTO pBDANoticeDTO) throws Exception;


}
