package com.kap.service.dao.eb;

import com.kap.core.dto.COEgovSeqDTO;
import com.kap.core.dto.ex.exg.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 샘플 Mapper
 * </pre>
 *
 * @ClassName		: COSampleMapper.java
 * @Description		: 샘플 Mapper
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2022.01.10	  박주석	             최초 생성
 * </pre>
 */
@Mapper
public interface EBEExamMapper {
    /**
     * 리스트 조회
   */
    public List<EXGExamMstSearchDTO> selectExamList(EXGExamMstSearchDTO EXGExamMstSearchDTO);

    /**
     * 리스트 갯수 조회
     */
    public int getExamListCnt(EXGExamMstSearchDTO EXGExamMstSearchDTO);

    /**
     * 교육회차 매핑 여부
     */
    public int getExamEdctnEpisdCnt(EXGExamMstSearchDTO EXGExamMstSearchDTO);

    /**
     * 마스터 삭제
     */
    public int deleteExamMst(EXGExamMstSearchDTO eXGExamMstSearchDTO);

    /**
     * 질문 삭제
     */
    public int deleteExamQstnDtl(EXGExamQstnDtlDTO eXGExamQstnDtlDTO);

    /**
     * 보기 삭제
     */
    public int deleteExamExmplDtl(EXGExamExmplDtlDTO eXGExamExmplDtlDTO);

    /**
     * 교육 시험 마스터 등록
     */
    public int insertExamMst(EXGExamMstInsertDTO EXGExamMstSearchDTO);

    /**
     * 교육 시험 질문 등록
     */
    public int insertExamQstnDtl(EXGExamMstInsertDTO EXGExamMstSearchDTO);

    /**
     * 교육 시험 질문 보기 등록
     */
    public int insertExamExmplDtl(EXGExamQstnDtlDTO EXGExamQstnDtlDTO);

    /**
     * 교육 시험 질문 시퀀스 변경(For문을 돌려야해서 한번에 업데이트)
     */
    public int updateSequenceGgn(COEgovSeqDTO cOEgovSeqDTO);

    /**
     * 교육 시험 마스터 조회
     */
    public EXGExamMstInsertDTO selectExamDtl(EXGExamMstSearchDTO eXGExamMstSearchDTO);

    /**
     * 교육 시험 질문 조회
     */
    public List<EXGExamQstnDtlDTO> getExamQstnDtlList(EXGExamMstSearchDTO eXGExamMstSearchDTO);

    /**
     * 교육 시험 질문 조회
     */
    public List<EXGExamQstnDtlDTO> getExamQstnRspnDtlList(EXGExamMstSearchDTO eXGExamMstSearchDTO);

    /**
     * 교육 시험 질문 보기 조회
     */
    public List<EXGExamExmplDtlDTO> getExamExmplDtlList(EXGExamMstSearchDTO eXGExamMstSearchDTO);

    /**
     * 교육 시험 질문 답변 조회
     */
    public List<EXGExamExmplRspnDtlDTO> getExamExmplUserRspnDtlList(EXGExamMstSearchDTO eXGExamMstSearchDTO);

    /**
     * 교육에 매핑된 평가지는 사용여부만 수정
     */
    public int updateExamMstExpnYn(EXGExamMstInsertDTO eXGExamMstInsertDTO);
    /**
     * 평가지 마스터 수정
     */
    public int updateExamMst(EXGExamMstInsertDTO eXGExamMstInsertDTO);

    /**
     * 사용자 평가지
     */
    public EXGExamEdctnPtcptMst selectUserExamDtl(EXGExamMstSearchDTO eXGExamMstSearchDTO);

    /**
     * 교육 시험 질문(정답) 조회
     */
    public List<EXGExamQstnDtlDTO> getExamQstnCanswList(EXGExamMstSearchDTO eXGExamMstSearchDTO);

    /**
     * 교육 시험 참여 등록
     */
    public int insertExamPtcptMst(EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst);
    /**
     * 교육 시험 문제 답변 상세
     */
    public int insertExamQstnRspnDtl(EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst);
    /**
     * 교육 시험 주관식 등록
     */
    public int insertExamPtcptSbjctRspnDtl(EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst);
    /**
     * 교육 시험 주관식 질문 정답 수정
     */
    public int updateExamPtcptQstnSbjctRspnDtl(EXGExamQstnRspnDtlDTO eXGExamQstnRspnDtlDTO);
    /**
     * 교육 시험 주관식 정답 수정
     */
    public int updateExamPtcptSbjctRspnDtl(EXGExamQstnRspnDtlDTO eXGExamQstnRspnDtlDTO);
    /**
     * 교육 시험 객관식 등록
     */
    public int insertExamPtcptMtlccRspnDtl(EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst);
    /**
     * 교육 참여 점수 변경
     */
    public int updateEdctnPtcptScord(EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst);

    /**
     * 교육 참여 응답 점수
     */
    public int getExamRspnSum(EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst);

    /**
     * 교육 참여 출석 전체, 출석 수 주말 제외
     */
    public HashMap getEdctnAtndcSum(EXGExamEdctnPtcptMst eXGExamEdctnPtcptMst);

    /**
     * 교육 참여 온라인 강의 전체, 수강 수
     */
    public HashMap getEdctnLtcrSum(EXGExamEdctnPtcptMst eXGExamEdctnPtcptMst);

    /**
     *  관리자 - 오프라인 시험일경우 빈 마스터만 넣어줌
     */
    public int insertOtsdExamPtcptMst(EXGExamEdctnPtcptRspnMst eXGExamEdctnPtcptRspnMst);

}
