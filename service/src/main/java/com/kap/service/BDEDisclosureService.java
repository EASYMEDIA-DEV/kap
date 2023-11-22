package com.kap.service;

import com.kap.core.dto.bd.bde.BDEDisclosureDTO;

/**
 * BDEDisclosureService 서비스
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
public interface BDEDisclosureService {

    /**
     * 경영공시 조회
    */
    public BDEDisclosureDTO selectDisclosureList(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception;

    /**
     * 경영공시 상세
     */
    public BDEDisclosureDTO selectDisclosureDtl(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception;

    /**
     * 경영공시 등록
     */
    public int insertDisclosure(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception;

    /**
     * 경영공시 수정
     */
    public int updateDisclosure(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception;

    /**
     * 경영공시 삭제
     */
    public int deleteDisclosure(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception;

}
