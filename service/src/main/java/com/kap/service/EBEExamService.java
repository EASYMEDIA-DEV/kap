package com.kap.service;

import com.kap.core.dto.eb.ebb.EBBPtcptDTO;
import com.kap.core.dto.ex.exg.EXGExamEdctnPtcptMst;
import com.kap.core.dto.ex.exg.EXGExamEdctnPtcptRspnMst;
import com.kap.core.dto.ex.exg.EXGExamMstInsertDTO;
import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * 교육 시험 서비스
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  김학규         최초 생성
 * </pre>
 */
public interface EBEExamService {
    /**
     * 리스트 조회
    */
    public EXGExamMstSearchDTO selectExamList(EXGExamMstSearchDTO EXGExamMstSearchDTO) throws Exception;

    /**
     * 교육회차마스터 매핑 여부
     */
    public int getExamEdctnEpisdCnt(EXGExamMstSearchDTO EXGExamMstSearchDTO) throws Exception;

    /**
     * 리스트 삭제
     */
    public int deleteExamList(EXGExamMstSearchDTO EXGExamMstSearchDTO) throws Exception;

    /**
     * 리스트 등록
     */
    public int insertExamList(EXGExamMstInsertDTO eXGExamMstInsertDTO, HttpServletRequest request) throws Exception;

    /**
     * 리스트 수정
     */
    public int updateExamList(EXGExamMstInsertDTO eXGExamMstInsertDTO, HttpServletRequest request) throws Exception;

    /**
     * 평가지 상세
     */
    public EXGExamMstInsertDTO selectExamDtl(EXGExamMstSearchDTO eXGExamMstSearchDTO) throws Exception;

    /**
     * 평가지 답변 상세
     */
    public EXGExamMstInsertDTO selectExamRspnDtl(EXGExamMstSearchDTO eXGExamMstSearchDTO) throws Exception;

    /**
     * 사용자 평가지
     */
    public EXGExamEdctnPtcptMst selectUserExamDtl(int ptcptSeq) throws Exception;
    /**
     * 사용자 평가지
     */
    public EXGExamEdctnPtcptMst selectUserExamDtl(EXGExamMstSearchDTO eXGExamMstSearchDTO) throws Exception;

    /**
     * 답변 등록
     */
    public int insertEdctnRspn(EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst, EXGExamEdctnPtcptMst eXGExamEdctnPtcptMst, HttpServletRequest request) throws Exception;


    /**
     * 주관식 답변 수정
     */
    public int updateEdctnSbjctRspn(EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst, EXGExamEdctnPtcptMst eXGExamEdctnPtcptMst, HttpServletRequest request) throws Exception;

    /**
     * 관리자 - 오프라인 시험일경우 빈 마스터만 넣어줌
     */
    public int insertOtsdExamPtcptMst(EBBPtcptDTO eBBPtcptDTO) throws Exception;

}
