package com.kap.service.dao.wb.wbk;

import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBPartCompanyDTO;

import java.util.List;

/**
 * <pre>
 * 스마트공장 > 일반회원 검색 DAO
 * </pre>
 *
 * @ClassName		: WBPartCompanyMapper.java
 * @Description		: 상생 공통 일반 회원 DAO
 * @author 민윤기
 * 
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2023.11.16	  민윤기	             최초 생성
 * </pre>
 */
public interface WBKBPartMapper {

    /**
     *   부품사 목록
     */
    public List<WBPartCompanyDTO> selPartCompanyUserList(WBPartCompanyDTO wBPartCompanyDTO);

    /**
     *   부품사 목록 Count
     */
    public int selPartCompanyUserListCnt(WBPartCompanyDTO wBPartCompanyDTO);

    /**
     *   부품사 회원 Detail
     */
    public WBPartCompanyDTO selPartUserDetail(WBPartCompanyDTO wBPartCompanyDTO);

    /**
     *   부품사 회원 - 회사 SQ Detail
     */
    public List<WBCompanyDetailMstDTO> selPartUserCompDetail(WBPartCompanyDTO wBPartCompanyDTO);
}
