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

}
