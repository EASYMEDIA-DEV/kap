package com.kap.service.impl.wb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBPartCompanyDTO;
import com.kap.service.WBPartCompanyService;
import com.kap.service.dao.wb.WBPartCompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 스마트공장구축 > 신청업체관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBFBRegisterCompanyServiceImpl.java
 * @Description		: 신청업체관리를 위한 ServiceImpl
 * @author 김동현
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.16		김동현				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WBPartCompanyServiceImpl implements WBPartCompanyService {

    /* Mapper */
    private final WBPartCompanyMapper wBPartCompanyMapper;

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

        wBPartCompanyDTO.setList(wBPartCompanyMapper.selPartCompanyUserList(wBPartCompanyDTO));
        wBPartCompanyDTO.setTotalCount(wBPartCompanyMapper.selPartCompanyUserListCnt(wBPartCompanyDTO));

        return wBPartCompanyDTO;
    }

    /**
     * 상생 부품사 User Select Detail
     */
    public WBPartCompanyDTO selPartUserDetail(WBPartCompanyDTO wBPartCompanyDTO) {
        return wBPartCompanyMapper.selPartUserDetail(wBPartCompanyDTO);
    }

    /**
     * 상생 부품사 User Company Select Detail
     */
    public WBCompanyDetailMstDTO selectPartUserCompDetailAjax(WBPartCompanyDTO wBPartCompanyDTO) {
        WBCompanyDetailMstDTO wBCompanyDetailMstDTO = new WBCompanyDetailMstDTO();
        wBCompanyDetailMstDTO.setList(wBPartCompanyMapper.selPartUserCompDetail(wBPartCompanyDTO));
        return wBCompanyDetailMstDTO;
    }
}
