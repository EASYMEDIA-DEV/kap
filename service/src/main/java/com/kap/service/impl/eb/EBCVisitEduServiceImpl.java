package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;
import com.kap.service.EBCVisitEduService;
import com.kap.service.dao.eb.EBCVisitEduMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 방문교육 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: EBCVisitEduServiceImpl.java
 * @Description		: 방문교육 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.11.30
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.30		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class EBCVisitEduServiceImpl implements EBCVisitEduService {

    // DAO
    private final EBCVisitEduMapper ebcVisitEduMapper;

    /**
     * 방문교육 목록을 조회한다.
     */
    public EBCVisitEduDTO selectVisitEduList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(ebcVisitEduDTO.getPageIndex());
        page.setRecordCountPerPage(ebcVisitEduDTO.getListRowSize());

        page.setPageSize(ebcVisitEduDTO.getPageRowSize());

        ebcVisitEduDTO.setFirstIndex(page.getFirstRecordIndex());
        ebcVisitEduDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        ebcVisitEduDTO.setTotalCount(ebcVisitEduMapper.selectVisitEduCnt(ebcVisitEduDTO));
        ebcVisitEduDTO.setList(ebcVisitEduMapper.selectVisitEduList(ebcVisitEduDTO));
        return ebcVisitEduDTO;
    }

    /**
     * 강사를 수정한다. -- 삭제예정!!!
     */
    public int updateVisitEdu(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {
        return ebcVisitEduMapper.updateVisitEdu(ebcVisitEduDTO);
    }

    /**
     * 강사를 수정한다.
     */
    public EBCVisitEduDTO selectVisitEduDtl(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {
        return ebcVisitEduMapper.selectVisitEduDtl(ebcVisitEduDTO);
    }

}
