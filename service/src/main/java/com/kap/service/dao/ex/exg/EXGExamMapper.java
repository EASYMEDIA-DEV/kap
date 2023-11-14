package com.kap.service.dao.ex.exg;

import com.kap.core.dto.ex.exg.EXGExamExmplDtlDTO;
import com.kap.core.dto.ex.exg.EXGExamMstInsertDTO;
import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.core.dto.COEgovSeqDTO;
import com.kap.core.dto.ex.exg.EXGExamQstnDtlDTO;
import org.apache.ibatis.annotations.Mapper;

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
public interface EXGExamMapper {
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
     * 교육 시험 질문 보기 조회
     */
    public List<EXGExamExmplDtlDTO> getExamExmplDtlList(EXGExamMstSearchDTO eXGExamMstSearchDTO);

    /**
     * 교육에 매핑된 평가지는 사용여부만 수정
     */
    public int updateExamMstExpnYn(EXGExamMstInsertDTO eXGExamMstInsertDTO);
    /**
     * 평가지 마스터 수정
     */
    public int updateExamMst(EXGExamMstInsertDTO eXGExamMstInsertDTO);
}
