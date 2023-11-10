package com.kap.service.impl.ex.exg;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.ex.exg.EXGExamMstDTO;
import com.kap.core.dto.ex.exg.EXGExamMstInsertDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EXGExamService;
import com.kap.service.dao.ex.exg.EXGExamMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 교육 시험 서비스 구현 클래스
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
public class EXGExamServiceImpl implements EXGExamService {
    //Mapper
    private final EXGExamMapper eXGExamMapper;

    /* 관리자 싴퉌스 */
    private final EgovIdGnrService examMstIdgen;

    /**
     * 리스트 조회
     */
    public EXGExamMstDTO selectExamList(EXGExamMstDTO eXGExamMstDTO) throws Exception{
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(eXGExamMstDTO.getPageIndex());
        page.setRecordCountPerPage(eXGExamMstDTO.getListRowSize());
        page.setPageSize(eXGExamMstDTO.getPageRowSize());
        eXGExamMstDTO.setFirstIndex( page.getFirstRecordIndex() );
        eXGExamMstDTO.setRecordCountPerPage( page.getRecordCountPerPage() );
        eXGExamMstDTO.setTotalCount( eXGExamMapper.getExamListCnt(eXGExamMstDTO));
        eXGExamMstDTO.setList( eXGExamMapper.selectExamList(eXGExamMstDTO) );
        return eXGExamMstDTO;
    }
    /**
     * 교육회차마스터 매핑 여부
     */
    public int getExamEdctnEpisdCnt(EXGExamMstDTO eXGExamMstDTO) throws Exception
    {
        return eXGExamMapper.getExamEdctnEpisdCnt(eXGExamMstDTO);
    }
    /**
     * 리스트 삭제
     */
    public int deleteExamList(EXGExamMstDTO eXGExamMstDTO) throws Exception
    {
        int respCnt = eXGExamMapper.getExamEdctnEpisdCnt(eXGExamMstDTO);
        if(respCnt == 0) {
            //삭제하려는 데이터가 교육회차마스터에 매핑되어있지 않음.
            //삭제 개수를 담아서 응답
            eXGExamMstDTO.setRespCnt(eXGExamMapper.deleteExamList(eXGExamMstDTO));
        }else{
            //삭제하려는 데이터가 교육회차마스터에 평가지가 매핑되어있음.
            respCnt = -1;
        }
        return respCnt;
    }

    /**
     * 리스트 등록
     */
    public int insertExamList(EXGExamMstInsertDTO eXGExamMstInsertDTO, HttpServletRequest request) throws Exception{
        int respCnt = 0;
        String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
        String regIp = CONetworkUtil.getMyIPaddress(request);
        eXGExamMstInsertDTO.setExamSeq( examMstIdgen.getNextIntegerId() );
        eXGExamMstInsertDTO.setRegIp(regIp);
        eXGExamMstInsertDTO.setRegId(regId);
        eXGExamMstInsertDTO.setModIp(regIp);
        eXGExamMstInsertDTO.setModId(regId);
        return respCnt;
    }
}
