package com.kap.service;

import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBPartCompanyDTO;

/**
 * 스마트공장구축 > 신청업체관리 Service
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.16  김동현         최초 생성
 * </pre>
 */
public interface WBPartCompanyService {

    /**
     * 상생 - 부품사 회원 Select
     */
    public WBPartCompanyDTO selPartCompanyUserList(WBPartCompanyDTO wBPartCompanyDTO);

    /**
     * 상생 - 부품사 회원 Detail
     */
    public WBPartCompanyDTO selPartUserDetail(WBPartCompanyDTO wBPartCompanyDTO);

    public WBCompanyDetailMstDTO selectPartUserCompDetailAjax(WBPartCompanyDTO wBPartCompanyDTO);

}
