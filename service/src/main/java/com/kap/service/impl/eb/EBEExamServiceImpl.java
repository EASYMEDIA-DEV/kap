package com.kap.service.impl.eb;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COEgovSeqDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.eb.ebb.EBBPtcptDTO;
import com.kap.core.dto.ex.exg.*;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBEExamService;
import com.kap.service.dao.eb.EBBEpisdMapper;
import com.kap.service.dao.eb.EBEExamMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
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

    private final EBBEpisdMapper eBBEpisdMapper;


    /* 교육평가마스터 시퀀스 */
    private final EgovIdGnrService examMstIdgen;

    /* 교육평가질문 시퀀스 */
    private final EgovIdGnrService examQstnDtlIdgen;

    /* 교육평가질문보기 시퀀스 */
    private final EgovIdGnrService examQstnExmplDtlIdgen;

    /* 교육 참여 순번 시퀀스 */
    private final EgovIdGnrService examPtcptSeqIdgen;

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
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            eXGExamMstInsertDTO.setModId(cOUserDetailsDTO.getId());
            eXGExamMstInsertDTO.setModIp(CONetworkUtil.getMyIPaddress(request));
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
        if(!examQstnDtlList.isEmpty()) {
            for (EXGExamQstnDtlDTO eXGExamQstnDtlDTO : examQstnDtlList) {
                eXGExamQstnDtlDTO.setExExamExmplDtlList(new ArrayList<EXGExamExmplDtlDTO>());
                if(!examExmplDtlList.isEmpty()) {
                    for (EXGExamExmplDtlDTO eXGExamExmplDtlDTO : examExmplDtlList) {
                        if (eXGExamQstnDtlDTO.getQstnSeq().equals(eXGExamExmplDtlDTO.getQstnSeq())) {
                            eXGExamQstnDtlDTO.getExExamExmplDtlList().add(eXGExamExmplDtlDTO);
                        }
                    }
                }
            }
        }
        if(eXGExamMstInsertDTO.getExamSeq() != null && eXGExamMstInsertDTO.getExamSeq() > -1) {
            eXGExamMstInsertDTO.setExExamQstnDtlList(examQstnDtlList);
            //교육 시험 매핑 여부
            int edctnEpisdCnt = eBEExamMapper.getExamEdctnEpisdCnt(eXGExamMstSearchDTO);
            eXGExamMstInsertDTO.setPosbChg(edctnEpisdCnt > 0 ? false : true);
        }
        return eXGExamMstInsertDTO;
    }

    /**
     * 평가지 답변 상세
     */
    public EXGExamMstInsertDTO selectExamRspnDtl(EXGExamMstSearchDTO eXGExamMstSearchDTO) throws Exception
    {
        EXGExamMstInsertDTO eXGExamMstInsertDTO = eBEExamMapper.selectExamDtl(eXGExamMstSearchDTO);
        List<EXGExamQstnDtlDTO> examQstnDtlList = eBEExamMapper.getExamQstnRspnDtlList(eXGExamMstSearchDTO);
        List<EXGExamExmplDtlDTO> examExmplDtlList = eBEExamMapper.getExamExmplDtlList(eXGExamMstSearchDTO);
        List<EXGExamExmplRspnDtlDTO> examExmplRspnDtlList = eBEExamMapper.getExamExmplUserRspnDtlList(eXGExamMstSearchDTO);
        for(EXGExamQstnDtlDTO eXGExamQstnDtlDTO : examQstnDtlList){
            eXGExamQstnDtlDTO.setExExamExmplDtlList( new ArrayList<EXGExamExmplDtlDTO>());
            eXGExamQstnDtlDTO.setExExamExmplRspnDtlList( new ArrayList<EXGExamExmplRspnDtlDTO>());
            for(EXGExamExmplDtlDTO eXGExamExmplDtlDTO : examExmplDtlList){
                if(eXGExamQstnDtlDTO.getQstnSeq().equals( eXGExamExmplDtlDTO.getQstnSeq() )){
                    eXGExamQstnDtlDTO.getExExamExmplDtlList().add(eXGExamExmplDtlDTO);
                }
            }
            for(EXGExamExmplRspnDtlDTO eXGExamExmplRspnDtlDTO : examExmplRspnDtlList){
                if(eXGExamQstnDtlDTO.getQstnSeq().equals( eXGExamExmplRspnDtlDTO.getQstnSeq() )){
                    eXGExamQstnDtlDTO.getExExamExmplRspnDtlList().add(eXGExamExmplRspnDtlDTO);
                }
            }
        }
        eXGExamMstInsertDTO.setExExamQstnDtlList( examQstnDtlList );
        //교육 시험 매핑 여부
        int edctnEpisdCnt = eBEExamMapper.getExamEdctnEpisdCnt( eXGExamMstSearchDTO );
        eXGExamMstInsertDTO.setPosbChg( edctnEpisdCnt > 0 ? false : true );
        return eXGExamMstInsertDTO;
    }

    /**
     * 사용자 평가지
     */
    public EXGExamEdctnPtcptMst selectUserExamDtl(int ptcptSeq) throws Exception{
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        EXGExamMstSearchDTO eXGExamMstSearchDTO = EXGExamMstSearchDTO.builder().ptcptSeq(ptcptSeq).memSeq(cOUserDetailsDTO.getSeq()).build();
        return eBEExamMapper.selectUserExamDtl(eXGExamMstSearchDTO);
    }

    /**
     * 사용자 평가지
     */
    public EXGExamEdctnPtcptMst selectUserExamDtl(EXGExamMstSearchDTO eXGExamMstSearchDTO) throws Exception{
        if(eXGExamMstSearchDTO.getPtcptSeq() == null){
            new Exception("NOT PTCPT SEQ");
        }
        if(eXGExamMstSearchDTO.getMemSeq() == null){
            new Exception("NOT MEM SEQ");
        }
        return eBEExamMapper.selectUserExamDtl(eXGExamMstSearchDTO);
    }

    /**
     * 답변 등록
     */
    public int insertEdctnRspn(EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst, EXGExamEdctnPtcptMst eXGExamEdctnPtcptMst, HttpServletRequest request) throws Exception{
        int examPtcptSeq = examPtcptSeqIdgen.getNextIntegerId();
        int actCnt = 0;
        EXGExamMstSearchDTO eXGExamMstSearchDTO = new EXGExamMstSearchDTO();
        eXGExamMstSearchDTO.setDetailsKey( String.valueOf(eXGExamEdctnPtcptRspnMst.getExamSeq()) );
        //객관식 평가
        int totalScord = 0;
        eXGExamMstSearchDTO.setCanswYn("Y");
        List<EXGExamQstnDtlDTO> eXGExamExmplDtlDTOList = eBEExamMapper.getExamQstnCanswList(eXGExamMstSearchDTO);
        if(eXGExamEdctnPtcptRspnMst.getMtlccList() != null && eXGExamEdctnPtcptRspnMst.getMtlccList().size() > 0 && eXGExamExmplDtlDTOList != null && eXGExamExmplDtlDTOList.size() > 0)
        {
            //답변 리스트 초기화
            if(eXGExamEdctnPtcptRspnMst.getQstnList() == null){
                eXGExamEdctnPtcptRspnMst.setQstnList( new ArrayList<EXGExamQstnRspnDtlDTO>() );
            }
            boolean isSuccess = false;
            for(EXGExamQstnDtlDTO eXGExamQstnDtlDTO : eXGExamExmplDtlDTOList)
            {
                isSuccess = false;
                int exmplSize = 0;
                int size = 0;
                String[] exmplCanswList = null;
                for(EXGExamEdctnPtcptMtlccRspnMst eXGExamEdctnPtcptMtlccRspnMst : eXGExamEdctnPtcptRspnMst.getMtlccList()) {
                    eXGExamEdctnPtcptMtlccRspnMst.setCanswYn("N");
                    if (eXGExamEdctnPtcptMtlccRspnMst.getQstnSeq().equals(eXGExamQstnDtlDTO.getQstnSeq()) )
                    {
                        exmplSize = exmplSize + 1;
                        exmplCanswList = eXGExamQstnDtlDTO.getExmplCansw().split(",");
                        if (exmplCanswList != null && exmplCanswList.length > 0)
                        {
                            for (int q = 0; q < exmplCanswList.length; q++)
                            {
                                //등록된 정답 보기와 요청한 보기가 맞는지
                                if(Integer.parseInt(exmplCanswList[q]) == eXGExamEdctnPtcptMtlccRspnMst.getExmplSeq())
                                {
                                    size = size + 1;
                                    eXGExamEdctnPtcptMtlccRspnMst.setCanswYn("Y");
                                    isSuccess = true;
                                }
                            }
                        }
                    }
                }
                if(exmplCanswList != null && (size != exmplCanswList.length || exmplSize != exmplCanswList.length)){
                    isSuccess = false;
                }
                if(isSuccess){
                    totalScord += eXGExamQstnDtlDTO.getScord();
                }
                EXGExamQstnRspnDtlDTO eXGExamQstnRspnDtlDTO = new EXGExamQstnRspnDtlDTO();
                eXGExamQstnRspnDtlDTO.setExamPtcptSeq( examPtcptSeq );
                eXGExamQstnRspnDtlDTO.setPtcptSeq( eXGExamEdctnPtcptRspnMst.getPtcptSeq() );
                eXGExamQstnRspnDtlDTO.setExamSeq( eXGExamEdctnPtcptRspnMst.getExamSeq() );
                eXGExamQstnRspnDtlDTO.setQstnSeq( eXGExamQstnDtlDTO.getQstnSeq() );
                eXGExamQstnRspnDtlDTO.setCanswYn( (isSuccess) ? "Y" : "N" );
                eXGExamQstnRspnDtlDTO.setQstnScore( (isSuccess) ? eXGExamQstnDtlDTO.getScord() : 0 );
                eXGExamEdctnPtcptRspnMst.getQstnList().add(eXGExamQstnRspnDtlDTO);
            }
        }
        //교육 참여 마스터 등록
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        eXGExamEdctnPtcptRspnMst.setMemSeq( cOUserDetailsDTO.getSeq() );
        eXGExamEdctnPtcptRspnMst.setRegId( cOUserDetailsDTO.getId() );
        eXGExamEdctnPtcptRspnMst.setRegIp( CONetworkUtil.getMyIPaddress(request) );
        eXGExamEdctnPtcptRspnMst.setExamPtcptSeq( examPtcptSeq );
        eXGExamEdctnPtcptRspnMst.setExamScore( totalScord );
        actCnt = eBEExamMapper.insertExamPtcptMst(eXGExamEdctnPtcptRspnMst);

        if(eXGExamEdctnPtcptRspnMst.getSbjctList() != null && eXGExamEdctnPtcptRspnMst.getSbjctList().size() > 0){
            //답변 리스트 초기화
            if(eXGExamEdctnPtcptRspnMst.getQstnList() == null){
                eXGExamEdctnPtcptRspnMst.setQstnList( new ArrayList<EXGExamQstnRspnDtlDTO>() );
            }
            for(EXGExamEdctnPtcptSbjctRspnMst eXGExamEdctnPtcptSbjctRspnMst : eXGExamEdctnPtcptRspnMst.getSbjctList())
            {
                EXGExamQstnRspnDtlDTO eXGExamQstnRspnDtlDTO = new EXGExamQstnRspnDtlDTO();
                eXGExamQstnRspnDtlDTO.setExamPtcptSeq( examPtcptSeq );
                eXGExamQstnRspnDtlDTO.setPtcptSeq( eXGExamEdctnPtcptRspnMst.getPtcptSeq() );
                eXGExamQstnRspnDtlDTO.setExamSeq( eXGExamEdctnPtcptRspnMst.getExamSeq() );
                eXGExamQstnRspnDtlDTO.setQstnSeq( eXGExamEdctnPtcptSbjctRspnMst.getQstnSeq() );
                eXGExamQstnRspnDtlDTO.setCanswYn( "X" );
                eXGExamQstnRspnDtlDTO.setQstnScore(  0 );
                eXGExamEdctnPtcptRspnMst.getQstnList().add(eXGExamQstnRspnDtlDTO);
            }
            eBEExamMapper.insertExamPtcptSbjctRspnDtl(eXGExamEdctnPtcptRspnMst);
        }
        if(eXGExamEdctnPtcptRspnMst.getMtlccList() != null && eXGExamEdctnPtcptRspnMst.getMtlccList().size() > 0){
            eBEExamMapper.insertExamPtcptMtlccRspnDtl(eXGExamEdctnPtcptRspnMst);
        }
        if(eXGExamEdctnPtcptRspnMst.getQstnList() != null && eXGExamEdctnPtcptRspnMst.getQstnList().size() > 0){
            eBEExamMapper.insertExamQstnRspnDtl(eXGExamEdctnPtcptRspnMst);
        }
        //수료 자동 처리
        //교육 차수의 수료 자동화 여부가 Y이면 객관식 총합으로 비고 후 Y, 일시저장
        //교육 차수의 수료 자동화 여부가 N이면 나두고.
        //수료 자동화 여부
        //2024-11-26 수료 방식 개편 s
        if("Y".equals(eXGExamEdctnPtcptMst.getCmptnAutoYn())){
            //평가 점수 체크
            /*if(totalScord >= eXGExamEdctnPtcptMst.getCmptnJdgmtCdNm()){
                //수료 여부
                eXGExamEdctnPtcptRspnMst.setCmptnYn( "Y" );
            }else{
                eXGExamEdctnPtcptRspnMst.setCmptnYn( "N" );
            }
            //평가 점수가 Y이면
            if("Y".equals(eXGExamEdctnPtcptRspnMst.getCmptnYn()))
            {*/
            int atndcPrsnt= 0;
            if("STDUY_MTHD02".equals(eXGExamEdctnPtcptMst.getStduyMthdCd())){
                //학습방식 온라인 경우 수강 대체
                HashMap edctnLctrMap = eBEExamMapper.getEdctnLtcrSum(eXGExamEdctnPtcptMst);
                if(edctnLctrMap != null ) {
                    Double totLctr = Double.parseDouble(edctnLctrMap.get("totLctr").toString());
                    Double cntLctr = Double.parseDouble(edctnLctrMap.get("cntLctr").toString());
                    atndcPrsnt = (int) (Math.ceil(cntLctr / totLctr * 100));
                }
            }
            else
            {
                //학습방식 온라인 아닌 경우 출석 대체
                HashMap edctnAtndcMap = eBEExamMapper.getEdctnAtndcSum(eXGExamEdctnPtcptMst);
                if(edctnAtndcMap != null ) {
                    Double totEdctn = Double.parseDouble(edctnAtndcMap.get("totEdctn").toString());
                    Double strtEdctn = Double.parseDouble(edctnAtndcMap.get("strtEdctn").toString());
                    atndcPrsnt = (int) (Math.ceil(strtEdctn / totEdctn * 100));
                }
            }

            double baseScore = eBEExamMapper.getExamMaxSum(eXGExamEdctnPtcptRspnMst);  //2024-11-26 수료 방식 개편
            double atndcPrsntPer = atndcPrsnt * 0.8;
            double totalScordPer = (totalScord/baseScore * 100) * 0.2;
            if(atndcPrsntPer + totalScordPer >= 90.0) {
                eXGExamEdctnPtcptRspnMst.setCmptnYn( "S" );
            }
            else if(atndcPrsntPer + totalScordPer >= 80.0) {
                eXGExamEdctnPtcptRspnMst.setCmptnYn( "Y" );
            }
            else {
                eXGExamEdctnPtcptRspnMst.setCmptnYn( "N" );
            }
                /*if(atndcPrsnt <  eXGExamEdctnPtcptMst.getCmptnStndCdNm()){
                    eXGExamEdctnPtcptRspnMst.setCmptnYn( "N" );
                }
            }*/
        }
        //2024-11-26 수료 방식 개편 e

        EBBPtcptDTO ptcptDto = new EBBPtcptDTO();
        ptcptDto.setPtcptSeq(eXGExamEdctnPtcptRspnMst.getPtcptSeq());
        EBBPtcptDTO rtnPtcptDto = eBBEpisdMapper.selectPtcptExamDtl(ptcptDto);
        EBBEpisdDTO eBBEpisdDTO = new EBBEpisdDTO();
        eBBEpisdDTO.setEdctnSeq(rtnPtcptDto.getEdctnSeq());
        eBBEpisdDTO.setEpisdYear(rtnPtcptDto.getEpisdYear());
        EBBEpisdDTO cmptnNo = eBBEpisdMapper.selectCmptnNo(eBBEpisdDTO);
        eXGExamEdctnPtcptRspnMst.setCrtfctNo(cmptnNo.getCrtfctNo());
        eXGExamEdctnPtcptRspnMst.setOrgCmptnYn(rtnPtcptDto.getCmptnYn());

        eBEExamMapper.updateEdctnPtcptScord(eXGExamEdctnPtcptRspnMst);
        return actCnt;
    }

    /**
     * 관리자 주관식 답변 채점 수정
     */
    public int updateEdctnSbjctRspn(EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst, EXGExamEdctnPtcptMst eXGExamEdctnPtcptMst, HttpServletRequest request) throws Exception{
        int actCnt = 0;
        EXGExamMstSearchDTO eXGExamMstSearchDTO = new EXGExamMstSearchDTO();
        eXGExamMstSearchDTO.setDetailsKey( String.valueOf(eXGExamEdctnPtcptRspnMst.getExamSeq()) );
        if(eXGExamEdctnPtcptRspnMst.getSbjctList() != null && eXGExamEdctnPtcptRspnMst.getSbjctList().size() > 0){
            for(EXGExamEdctnPtcptSbjctRspnMst eXGExamEdctnPtcptSbjctRspnMst : eXGExamEdctnPtcptRspnMst.getSbjctList())
            {
                log.error("eXGExamEdctnPtcptSbjctRspnMst : {}", eXGExamEdctnPtcptSbjctRspnMst);
                EXGExamQstnRspnDtlDTO eXGExamQstnRspnDtlDTO = new EXGExamQstnRspnDtlDTO();
                eXGExamQstnRspnDtlDTO.setExamPtcptSeq( eXGExamEdctnPtcptRspnMst.getExamPtcptSeq() );
                eXGExamQstnRspnDtlDTO.setPtcptSeq( eXGExamEdctnPtcptRspnMst.getPtcptSeq() );
                eXGExamQstnRspnDtlDTO.setExamSeq( eXGExamEdctnPtcptRspnMst.getExamSeq() );
                eXGExamQstnRspnDtlDTO.setQstnSeq( eXGExamEdctnPtcptSbjctRspnMst.getQstnSeq() );
                eXGExamQstnRspnDtlDTO.setCanswYn( eXGExamEdctnPtcptSbjctRspnMst.getCanswYn() );
                //정답이면 점수 합산
                if("Y".equals(eXGExamEdctnPtcptSbjctRspnMst.getCanswYn()))
                {
                    eXGExamQstnRspnDtlDTO.setQstnScore(  eXGExamEdctnPtcptSbjctRspnMst.getScord() );
                }else{
                    eXGExamQstnRspnDtlDTO.setQstnScore(  0 );
                }
                //질문 응답 수정
                eBEExamMapper.updateExamPtcptQstnSbjctRspnDtl(eXGExamQstnRspnDtlDTO);
                //주관식 응답 수정
                eBEExamMapper.updateExamPtcptSbjctRspnDtl(eXGExamQstnRspnDtlDTO);
            }
        }
        //저장된 점수 합산
        int totalScord = eBEExamMapper.getExamRspnSum(eXGExamEdctnPtcptRspnMst);
        //수료 자동화 여부
        //2024-11-26 수료 방식 개편 s
//        if("Y".equals(eXGExamEdctnPtcptMst.getCmptnAutoYn())){
            //평가 점수 체크
            /*if(totalScord >= eXGExamEdctnPtcptMst.getCmptnJdgmtCdNm()){
                //수료 여부
                eXGExamEdctnPtcptRspnMst.setCmptnYn( "Y" );
            }else{
                eXGExamEdctnPtcptRspnMst.setCmptnYn( "N" );
            }
            //평가 점수가 Y이면
            if("Y".equals(eXGExamEdctnPtcptRspnMst.getCmptnYn()))
            {*/
            int atndcPrsnt= 0;
            if("STDUY_MTHD02".equals(eXGExamEdctnPtcptMst.getStduyMthdCd())){
                //학습방식 온라인 경우 수강 대체
                HashMap edctnLctrMap = eBEExamMapper.getEdctnLtcrSum(eXGExamEdctnPtcptMst);
                if(edctnLctrMap != null ) {
                    Double totLctr = Double.parseDouble(edctnLctrMap.get("totLctr").toString());
                    Double cntLctr = Double.parseDouble(edctnLctrMap.get("cntLctr").toString());
                    atndcPrsnt = (int) (Math.ceil(cntLctr / totLctr * 100));
                }
            }
            else
            {
                //학습방식 온라인 아닌 경우 출석 대체
                HashMap edctnAtndcMap = eBEExamMapper.getEdctnAtndcSum(eXGExamEdctnPtcptMst);
                if(edctnAtndcMap != null ) {
                    Double totEdctn = Double.parseDouble(edctnAtndcMap.get("totEdctn").toString());
                    Double strtEdctn = Double.parseDouble(edctnAtndcMap.get("strtEdctn").toString());
                    atndcPrsnt = (int) (Math.ceil(strtEdctn / totEdctn * 100));
                }
            }

            double baseScore = eBEExamMapper.getExamMaxSum(eXGExamEdctnPtcptRspnMst);  //2024-11-26 수료 방식 개편
            double atndcPrsntPer = atndcPrsnt * 0.8;
            double totalScordPer = (totalScord/baseScore * 100) * 0.2;
            if(atndcPrsntPer + totalScordPer >= 90.0) {
                eXGExamEdctnPtcptRspnMst.setCmptnYn( "S" );
            }
            else if(atndcPrsntPer + totalScordPer >= 80.0) {
                eXGExamEdctnPtcptRspnMst.setCmptnYn( "Y" );
            }
            else {
                eXGExamEdctnPtcptRspnMst.setCmptnYn( "N" );
            }

                /*if(atndcPrsnt <  eXGExamEdctnPtcptMst.getCmptnStndCdNm()){
                    eXGExamEdctnPtcptRspnMst.setCmptnYn( "N" );
                }
            }*/
//        }
        //2024-11-26 수료 방식 개편 e

        //수료번호 출력
        EBBPtcptDTO ptcptDto = new EBBPtcptDTO();
        ptcptDto.setPtcptSeq(eXGExamEdctnPtcptRspnMst.getPtcptSeq());
        EBBPtcptDTO rtnPtcptDto = eBBEpisdMapper.selectPtcptExamDtl(ptcptDto);
        EBBEpisdDTO eBBEpisdDTO = new EBBEpisdDTO();
        eBBEpisdDTO.setEdctnSeq(rtnPtcptDto.getEdctnSeq());
        eBBEpisdDTO.setEpisdYear(rtnPtcptDto.getEpisdYear());
        EBBEpisdDTO cmptnNo = eBBEpisdMapper.selectCmptnNo(eBBEpisdDTO);
        eXGExamEdctnPtcptRspnMst.setCrtfctNo(cmptnNo.getCrtfctNo());
        eXGExamEdctnPtcptRspnMst.setOrgCmptnYn(rtnPtcptDto.getCmptnYn());


        //교육참여마스터 시험 점수 변경
        eXGExamEdctnPtcptRspnMst.setExamScore(totalScord);
        eBEExamMapper.updateEdctnPtcptScord(eXGExamEdctnPtcptRspnMst);
        return actCnt;
    }


    /**
     * 관리자 - 오프라인 시험일경우 빈 마스터만 넣어줌
     */
    public int insertOtsdExamPtcptMst(EBBPtcptDTO eBBPtcptDTO) throws Exception{
        System.out.println("@@@진입");
        int actCnt = 0;
        EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst = new EXGExamEdctnPtcptRspnMst();
        int examPtcptSeq = examPtcptSeqIdgen.getNextIntegerId();

        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        eXGExamEdctnPtcptRspnMst.setMemSeq( cOUserDetailsDTO.getSeq() );
        eXGExamEdctnPtcptRspnMst.setRegId( cOUserDetailsDTO.getId() );
        eXGExamEdctnPtcptRspnMst.setRegIp( cOUserDetailsDTO.getLoginIp() );

        eXGExamEdctnPtcptRspnMst.setModId( cOUserDetailsDTO.getId() );
        eXGExamEdctnPtcptRspnMst.setModIp( cOUserDetailsDTO.getLoginIp() );

        eXGExamEdctnPtcptRspnMst.setPtcptSeq( eBBPtcptDTO.getPtcptSeq() );
        eXGExamEdctnPtcptRspnMst.setExamPtcptSeq( eBBPtcptDTO.getPtcptSeq() );
        eXGExamEdctnPtcptRspnMst.setExamScore( eBBPtcptDTO.getExamScore() );
        System.out.println("@@@등록전 정보 eXGExamEdctnPtcptRspnMst = " + eXGExamEdctnPtcptRspnMst);
        actCnt = eBEExamMapper.insertOtsdExamPtcptMst(eXGExamEdctnPtcptRspnMst);

        return actCnt;
    }

}
