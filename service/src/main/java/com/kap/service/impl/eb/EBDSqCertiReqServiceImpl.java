package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.service.EBDSqCertiReqService;
import com.kap.service.dao.eb.EBDSqCertiReqMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * SQ평가원 자격증 신청관리
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  김학규         최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EBDSqCertiReqServiceImpl implements EBDSqCertiReqService {
    //Mapper
    private final EBDSqCertiReqMapper eBDSqCertiReqMapper;

    /**
     * 리스트 조회
     */
    public EBDSqCertiSearchDTO selectList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception{
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(eBDSqCertiSearchDTO.getPageIndex());
        page.setRecordCountPerPage(eBDSqCertiSearchDTO.getListRowSize());
        page.setPageSize(eBDSqCertiSearchDTO.getPageRowSize());
        eBDSqCertiSearchDTO.setFirstIndex( page.getFirstRecordIndex() );
        eBDSqCertiSearchDTO.setRecordCountPerPage( page.getRecordCountPerPage() );
        eBDSqCertiSearchDTO.setTotalCount( eBDSqCertiReqMapper.getListCnt(eBDSqCertiSearchDTO));
        eBDSqCertiSearchDTO.setList( eBDSqCertiReqMapper.selectList(eBDSqCertiSearchDTO) );
        return eBDSqCertiSearchDTO;
    }
}
