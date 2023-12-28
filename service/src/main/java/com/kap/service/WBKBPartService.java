package com.kap.service;

import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBPartCompanyDTO;

/**
 * 미래차 공모전 신청팀 Service
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.16  민윤기         최초 생성
 * </pre>
 */
public interface WBKBPartService {

    /**
     * 상생 - 미래차 공모전 회원 Select
     */
    public WBPartCompanyDTO selPartCompanyUserList(WBPartCompanyDTO wBPartCompanyDTO);

    /**
     * 상생 - 미래차 공모전 회원 Detail
     */
    public WBPartCompanyDTO selPartUserDetail(WBPartCompanyDTO wBPartCompanyDTO);

    // public WBCompanyDetailMstDTO selectPartUserCompDetailAjax(WBPartCompanyDTO wBPartCompanyDTO);

}
