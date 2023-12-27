package com.kap.service.impl.wb.wbk;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBPartCompanyDTO;
import com.kap.service.WBKBPartService;
import com.kap.service.WBPartCompanyService;
import com.kap.service.dao.wb.WBPartCompanyMapper;
import com.kap.service.dao.wb.WBPartCompanyMapper;
import com.kap.service.dao.wb.wbk.WBKBPartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *  미래차 공모전 신청팀  위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBFBRegisterCompanyServiceImpl.java
 * @Description		: 미래차 신청팀 ServiceImpl
 * @author 민윤기
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.16		민윤기				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WBKBPartServiceImpl implements WBKBPartService {

    /* Mapper */
    private final WBKBPartMapper wBKBPartMapper;

    /**
     * 상생 부품사 User Select
     */
    public WBPartCompanyDTO selPartCompanyUserList(WBPartCompanyDTO wBPartCompanyDTO) {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBPartCompanyDTO.getPageIndex());
        page.setRecordCountPerPage(wBPartCompanyDTO.getListRowSize());

        page.setPageSize(wBPartCompanyDTO.getPageRowSize());

        wBPartCompanyDTO.setFirstIndex( page.getFirstRecordIndex() );
        wBPartCompanyDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        wBPartCompanyDTO.setList(wBKBPartMapper.selPartCompanyUserList(wBPartCompanyDTO));
        wBPartCompanyDTO.setTotalCount(wBKBPartMapper.selPartCompanyUserListCnt(wBPartCompanyDTO));

        return wBPartCompanyDTO;
    }

    /**
     * 상생 부품사 User Select Detail
     */
    public WBPartCompanyDTO selPartUserDetail(WBPartCompanyDTO wBPartCompanyDTO) {
        return wBKBPartMapper.selPartUserDetail(wBPartCompanyDTO);
    }

    /**
     * 상생 부품사 User Company Select Detail
     */
    public WBCompanyDetailMstDTO selectPartUserCompDetailAjax(WBPartCompanyDTO wBPartCompanyDTO) {
        WBCompanyDetailMstDTO wBCompanyDetailMstDTO = new WBCompanyDetailMstDTO();
        wBCompanyDetailMstDTO.setList(wBKBPartMapper.selPartUserCompDetail(wBPartCompanyDTO));
        return wBCompanyDetailMstDTO;
    }
}
