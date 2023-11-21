package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.MPHNewsLetterDTO;
import com.kap.service.MPHNewsLetterService;
import com.kap.service.dao.mp.MPHNewsLetterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 뉴스레터 신청자 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPHNewsLetterServiceImpl.java
 * @Description		: 뉴스레터 신청자 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MPHNewsLetterServiceImpl implements MPHNewsLetterService {

    // DAO
    private final MPHNewsLetterMapper mphNewsLetterMapper;
    
    /**
     * 강사 목록을 조회한다.
     */
    public MPHNewsLetterDTO selectNewsLetterList(MPHNewsLetterDTO mphNewsLetterDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mphNewsLetterDTO.getPageIndex());
        page.setRecordCountPerPage(mphNewsLetterDTO.getListRowSize());

        page.setPageSize(mphNewsLetterDTO.getPageRowSize());

        mphNewsLetterDTO.setFirstIndex(page.getFirstRecordIndex());
        mphNewsLetterDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        mphNewsLetterDTO.setList(mphNewsLetterMapper.selectNewsLetterList(mphNewsLetterDTO));
        mphNewsLetterDTO.setTotalCount(mphNewsLetterMapper.selectNewsLetterCnt(mphNewsLetterDTO));

        return mphNewsLetterDTO;
    }

    /**
     * 신청자 수를 조회한다.
     */
    public int selectNewsLetterCnt(MPHNewsLetterDTO mphNewsLetterDTO) throws Exception {
        return mphNewsLetterMapper.selectNewsLetterCnt(mphNewsLetterDTO);
    }

}
