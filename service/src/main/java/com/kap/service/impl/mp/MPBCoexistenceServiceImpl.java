package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpb.MPBBnsSearchDTO;
import com.kap.core.dto.mp.mpb.MPBCompanyDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPBCoexistenceService;
import com.kap.service.dao.mp.MPBCoexistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * 마이페이지 상생 사업 신청내역 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPBCoexistenceServiceImpl.java
 * @Description		: 마이페이지 상생 사업 신청내역 ServiceImpl
 * @author 김태훈
 * @since 2024.01.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.08		김태훈				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MPBCoexistenceServiceImpl implements MPBCoexistenceService {

    private final MPBCoexistenceMapper mpbCoexistenceMapper;

    /**
     *   마이페이지 상생 사업 신청내역 List
     */
    public MPBBnsSearchDTO selectApplyList(MPBBnsSearchDTO mpbBnsSearchDTO, String type) throws Exception
    {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpbBnsSearchDTO.getPageIndex());
        page.setRecordCountPerPage(mpbBnsSearchDTO.getListRowSize());

        page.setPageSize(mpbBnsSearchDTO.getPageRowSize());

        if ("init".equals(type)) {
            mpbBnsSearchDTO.setFirstIndex(page.getFirstRecordIndex());
            mpbBnsSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        }

        COUserDetailsDTO cOUserDetailsDTO = null;
        cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

        mpbBnsSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());

        mpbBnsSearchDTO.setList(mpbCoexistenceMapper.selectApplyList(mpbBnsSearchDTO));
        mpbBnsSearchDTO.setTotalCount(mpbCoexistenceMapper.selectApplyCount(mpbBnsSearchDTO));

        return mpbBnsSearchDTO;
    }

    /**
     *   마이페이지 공통 상생 사업여부
     */
    public String getBusinessYn(MPBBnsSearchDTO mpbBnsSearchDTO) throws Exception
    {
        String businessYn = mpbCoexistenceMapper.getBusinessYn(mpbBnsSearchDTO);

        return businessYn;
    }

    /**
     *   마이페이지 상생 상세
     */
    public MPBBnsSearchDTO getBsnDetail(MPBBnsSearchDTO mpbBnsSearchDTO) throws Exception
    {
        return mpbCoexistenceMapper.getBsnDetail(mpbBnsSearchDTO);
    }

    /**
     * 부품사 회원 정보를 조회한다.
     * @return
     */
    public MPBCompanyDTO selectCompanyUserDtl(MPBBnsSearchDTO mpbBnsSearchDTO) throws Exception {

        MPBCompanyDTO mpbCompanyDTO = new MPBCompanyDTO();

        mpbCompanyDTO = mpbCoexistenceMapper.getCompanyInfo(mpbBnsSearchDTO);
        List<MPBCompanyDTO> sqList = mpbCoexistenceMapper.selectSqList(mpbBnsSearchDTO);
        mpbCompanyDTO.setSqInfoList(sqList);
        mpbCompanyDTO.setMemSeq(mpbBnsSearchDTO.getMemSeq());

        return mpbCompanyDTO;
    }
}
