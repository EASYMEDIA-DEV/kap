package com.kap.service.dao.wb;

import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBPartCompanyDTO;

import java.util.List;

/**
 * <pre>
 * 스마트공장 > 신청부품사 관리 DAO
 * </pre>
 *
 * @ClassName		: WBPartCompanyMapper.java
 * @Description		: 상생 공통 부품사 회원 DAO
 * @author 김동현
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2023.11.16	  김동현	             최초 생성
 * </pre>
 */
public interface WBPartCompanyMapper {

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
