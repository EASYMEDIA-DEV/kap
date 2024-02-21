package com.kap.service;

import com.kap.core.dto.bd.bdf.BDFOnlineDTO;

/**
 * BDFOnlineService 서비스
 *
 * @author 오병호
 * @since 2024.02.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2024.02.20  오병호         최초 생성
 * </pre>
 */
public interface BDFOnlineService {

    /**
     * Online 조회
    */
    public BDFOnlineDTO selectOnlineList(BDFOnlineDTO bDFOnlineDTO) throws Exception;

    /**
     * Online 상세
     */
    public BDFOnlineDTO selectOnlineDtl(BDFOnlineDTO bDFOnlineDTO) throws Exception;

    /**
     * Online 등록
     */
    public int insertOnline(BDFOnlineDTO bDFOnlineDTO) throws Exception;

    /**
     * Online 수정
     */
    public int updateOnline(BDFOnlineDTO bDFOnlineDTO) throws Exception;

    /**
     * Online 삭제
     */
    public int deleteOnline(BDFOnlineDTO bDFOnlineDTO) throws Exception;

    /**
     * Online 첨부파일 목록 조회
     */
    public BDFOnlineDTO selectOnlineFileList(BDFOnlineDTO bDFOnlineDTO) throws Exception;

}
