package com.kap.service;

import com.kap.core.dto.bd.bdc.BDCFaqDTO;

/**
 * BDCFaqService 서비스
 *
 * @author 장두석
 * @since 2023.11.21
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.21  장두석         최초 생성
 * </pre>
 */
public interface BDCFaqService {

    /**
     * FAQ 조회
    */
    public BDCFaqDTO selectFaqList(BDCFaqDTO pBDCFaqDTO) throws Exception;

    /**
     * FAQ 상세
     */
    public BDCFaqDTO selectFaqDtl(BDCFaqDTO pBDCFaqDTO) throws Exception;

    /**
     * FAQ 등록
     */
    public int insertFaq(BDCFaqDTO pBDCFaqDTO) throws Exception;

    /**
     * FAQ 수정
     */
    public int updateFaq(BDCFaqDTO pBDCFaqDTO) throws Exception;

    /**
     * FAQ 삭제
     */
    public int deleteFaq(BDCFaqDTO pBDCFaqDTO) throws Exception;

    /**
     * FAQ 첨부파일 목록 조회
     */
    public BDCFaqDTO selectFaqFileList(BDCFaqDTO pBDCFaqDTO) throws Exception;

}
