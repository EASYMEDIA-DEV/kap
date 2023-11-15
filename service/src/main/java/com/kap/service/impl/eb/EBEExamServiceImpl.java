package com.kap.service.impl.eb;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COEgovSeqDTO;
import com.kap.core.dto.ex.exg.EXGExamExmplDtlDTO;
import com.kap.core.dto.ex.exg.EXGExamMstInsertDTO;
import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.core.dto.ex.exg.EXGExamQstnDtlDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBEExamService;
import com.kap.service.dao.eb.EBEExamMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
public class EBEExamServiceImpl implements EBEExamService {
    //Mapper
    private final EBEExamMapper eBEExamMapper;

    /* 교육평가마스터 시퀀스 */
    private final EgovIdGnrService examMstIdgen;

    /* 교육평가질문 시퀀스 */
    private final EgovIdGnrService examQstnDtlIdgen;

    /* 교육평가질문보기 시퀀스 */
    private final EgovIdGnrService examQstnExmplDtlIdgen;

    /**
     * 리스트 조회
     */
    public EXGExamMstSearchDTO selectExamList(EXGExamMstSearchDTO EXGExamMstSearchDTO) throws Exception{
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(EXGExamMstSearchDTO.getPageIndex());
        page.setRecordCountPerPage(EXGExamMstSearchDTO.getListRowSize());
        page.setPageSize(EXGExamMstSearchDTO.getPageRowSize());
        EXGExamMstSearchDTO.setFirstIndex( page.getFirstRecordIndex() );
        EXGExamMstSearchDTO.setRecordCountPerPage( page.getRecordCountPerPage() );
        EXGExamMstSearchDTO.setTotalCount( eBEExamMapper.getExamListCnt(EXGExamMstSearchDTO));
        EXGExamMstSearchDTO.setList( eBEExamMapper.selectExamList(EXGExamMstSearchDTO) );
        return EXGExamMstSearchDTO;
    }
    /**
     * 교육회차마스터 매핑 여부
     */
    public int getExamEdctnEpisdCnt(EXGExamMstSearchDTO EXGExamMstSearchDTO) throws Exception
    {
        return eBEExamMapper.getExamEdctnEpisdCnt(EXGExamMstSearchDTO);
    }
    /**
     * 리스트 삭제
     */
    public int deleteExamList(EXGExamMstSearchDTO eXGExamMstSearchDTO) throws Exception
    {
        int respCnt = eBEExamMapper.getExamEdctnEpisdCnt(eXGExamMstSearchDTO);
        if(respCnt == 0) {
            //삭제하려는 데이터가 교육회차마스터에 매핑되어있지 않음.
            //삭제 개수를 담아서 응답
            respCnt = eBEExamMapper.deleteExamMst(eXGExamMstSearchDTO);
            EXGExamQstnDtlDTO eXGExamQstnDtlDTO = new EXGExamQstnDtlDTO();
            eXGExamQstnDtlDTO.setDelValueList( eXGExamMstSearchDTO.getDelValueList() );
            eBEExamMapper.deleteExamQstnDtl(eXGExamQstnDtlDTO);
            EXGExamExmplDtlDTO eXGExamExmplDtlDTO = new EXGExamExmplDtlDTO();
            eXGExamExmplDtlDTO.setDelValueList( eXGExamMstSearchDTO.getDelValueList() );
            eBEExamMapper.deleteExamExmplDtl( eXGExamExmplDtlDTO );
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
        int firstExamMstIdgen = 0;
        eXGExamMstInsertDTO.setRegIp(regIp);
        eXGExamMstInsertDTO.setRegId(regId);
        eXGExamMstInsertDTO.setModIp(regIp);
        eXGExamMstInsertDTO.setModId(regId);
        if(eXGExamMstInsertDTO.getDetailsKey().trim().equals("")){
            //등록
            firstExamMstIdgen = examMstIdgen.getNextIntegerId();
            eXGExamMstInsertDTO.setExamSeq( firstExamMstIdgen );
            respCnt = eBEExamMapper.insertExamMst( eXGExamMstInsertDTO );
        }else{
            //수정
            firstExamMstIdgen = Integer.parseInt( eXGExamMstInsertDTO.getDetailsKey() );
            respCnt = eBEExamMapper.updateExamMst( eXGExamMstInsertDTO );
        }
        //응닶 cnt
        eXGExamMstInsertDTO.setRespCnt( respCnt );
        if(eXGExamMstInsertDTO.getExExamQstnDtlList() != null && eXGExamMstInsertDTO.getExExamQstnDtlList().size() > 0){
            //질문 순번 생성
            int firstExamQstnDtlIdgen = examQstnDtlIdgen.getNextIntegerId();
            int firstExamExplDtlIdgen = 0;
            firstExamExplDtlIdgen = examQstnExmplDtlIdgen.getNextIntegerId();
            //질문 순번 건너띄기
            COEgovSeqDTO COEgovSeqDTO = new COEgovSeqDTO();
            COEgovSeqDTO.setTableName("EXAM_QSTN_SEQ");
            COEgovSeqDTO.setNetxId(firstExamQstnDtlIdgen + eXGExamMstInsertDTO.getQstnSize());
            eBEExamMapper.updateSequenceGgn( COEgovSeqDTO );

            //보기 순번 생성
            COEgovSeqDTO.setTableName("EXAM_EXMPL_SEQ");
            COEgovSeqDTO.setNetxId(firstExamExplDtlIdgen + eXGExamMstInsertDTO.getExmplSize());
            eBEExamMapper.updateSequenceGgn( COEgovSeqDTO );

            for(int q = 0 ; q < eXGExamMstInsertDTO.getExExamQstnDtlList().size() ; q++){
                EXGExamQstnDtlDTO eXGExamQstnDtlDTO = eXGExamMstInsertDTO.getExExamQstnDtlList().get(q);
                //질문 순번
                eXGExamQstnDtlDTO.setExamSeq( firstExamMstIdgen );
                eXGExamQstnDtlDTO.setQstnSeq( firstExamQstnDtlIdgen + q  );
                eXGExamQstnDtlDTO.setQstnOrd( q+1 );
                eXGExamQstnDtlDTO.setRegId( regId );
                eXGExamQstnDtlDTO.setRegIp( regIp );
                eXGExamQstnDtlDTO.setModId( regId );
                eXGExamQstnDtlDTO.setModIp( regIp );

                if(eXGExamQstnDtlDTO.getExExamExmplDtlList() != null && eXGExamQstnDtlDTO.getExExamExmplDtlList().size() > 0){
                    for(int x = 0 ; x < eXGExamQstnDtlDTO.getExExamExmplDtlList().size() ; x++){
                        EXGExamExmplDtlDTO eXGExamExmplDtlDTO = eXGExamQstnDtlDTO.getExExamExmplDtlList().get(x);
                        eXGExamExmplDtlDTO.setExamSeq( firstExamMstIdgen );
                        eXGExamExmplDtlDTO.setQstnSeq( firstExamQstnDtlIdgen + q );
                        eXGExamExmplDtlDTO.setExmplSeq( firstExamExplDtlIdgen );
                        eXGExamExmplDtlDTO.setExmplOrd( x+1 );
                        eXGExamExmplDtlDTO.setRegId( regId );
                        eXGExamExmplDtlDTO.setRegIp( regIp );
                        eXGExamExmplDtlDTO.setModId( regId );
                        eXGExamExmplDtlDTO.setModIp( regIp );
                        firstExamExplDtlIdgen = firstExamExplDtlIdgen + 1;
                    }
                    //보기 등록
                    eBEExamMapper.insertExamExmplDtl( eXGExamQstnDtlDTO );
                }
            }
            //질문 등록
            eBEExamMapper.insertExamQstnDtl( eXGExamMstInsertDTO );
        }
        return respCnt;
    }
    /**
     * 리스트 수정
     */
    public int updateExamList(EXGExamMstInsertDTO eXGExamMstInsertDTO, HttpServletRequest request) throws Exception
    {
        EXGExamMstSearchDTO eXGExamMstSearchDTO = new EXGExamMstSearchDTO();
        eXGExamMstSearchDTO.setDetailsKey( eXGExamMstInsertDTO.getDetailsKey() );
        int respCnt = eBEExamMapper.getExamEdctnEpisdCnt(eXGExamMstSearchDTO);
        if(respCnt > 0)
        {
            //사용 여부만 수정
            respCnt = eBEExamMapper.updateExamMstExpnYn( eXGExamMstInsertDTO );
        }
        else
        {
            //질문 삭제
            EXGExamQstnDtlDTO eXGExamQstnDtlDTO = new EXGExamQstnDtlDTO();
            eXGExamQstnDtlDTO.setDetailsKey( eXGExamMstInsertDTO.getDetailsKey() );
            eBEExamMapper.deleteExamQstnDtl( eXGExamQstnDtlDTO );
            //보기 삭제
            EXGExamExmplDtlDTO eXGExamExmplDtlDTO = new EXGExamExmplDtlDTO();
            eXGExamExmplDtlDTO.setDetailsKey( eXGExamMstInsertDTO.getDetailsKey() );
            eBEExamMapper.deleteExamExmplDtl( eXGExamExmplDtlDTO );

            //내용 등록
            insertExamList(eXGExamMstInsertDTO, request);
        }
        return respCnt;
    }
    /**
     * 평가지 상세
     */
    public EXGExamMstInsertDTO selectExamDtl(EXGExamMstSearchDTO eXGExamMstSearchDTO) throws Exception
    {
        EXGExamMstInsertDTO eXGExamMstInsertDTO = eBEExamMapper.selectExamDtl(eXGExamMstSearchDTO);
        List<EXGExamQstnDtlDTO> examQstnDtlList = eBEExamMapper.getExamQstnDtlList(eXGExamMstSearchDTO);
        List<EXGExamExmplDtlDTO> examExmplDtlList = eBEExamMapper.getExamExmplDtlList(eXGExamMstSearchDTO);
        for(EXGExamQstnDtlDTO eXGExamQstnDtlDTO : examQstnDtlList){
            eXGExamQstnDtlDTO.setExExamExmplDtlList( new ArrayList<EXGExamExmplDtlDTO>());
            for(EXGExamExmplDtlDTO eXGExamExmplDtlDTO : examExmplDtlList){
                if(eXGExamQstnDtlDTO.getQstnSeq() == eXGExamExmplDtlDTO.getQstnSeq()){
                    eXGExamQstnDtlDTO.getExExamExmplDtlList().add(eXGExamExmplDtlDTO);
                }
            }
        }
        eXGExamMstInsertDTO.setExExamQstnDtlList( examQstnDtlList );
        //교육 시험 매핑 여부
        int edctnEpisdCnt = eBEExamMapper.getExamEdctnEpisdCnt( eXGExamMstSearchDTO );
        eXGExamMstInsertDTO.setPosbChg( edctnEpisdCnt > 0 ? false : true );
        return eXGExamMstInsertDTO;
    }
}
