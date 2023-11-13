package com.kap.service.dao.ex.exg;

import com.kap.core.dto.ex.exg.EXGExamMstDTO;
import com.kap.core.dto.ex.exg.EXGExamMstInsertDTO;
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
    public List<EXGExamMstDTO> selectExamList(EXGExamMstDTO eXGExamMstDTO);

    /**
     * 리스트 갯수 조회
     */
    public int getExamListCnt(EXGExamMstDTO eXGExamMstDTO);

    /**
     * 교육회차 매핑 여부
     */
    public int getExamEdctnEpisdCnt(EXGExamMstDTO eXGExamMstDTO);

    /**
     * 리스트 삭제
     */
    public int deleteExamList(EXGExamMstDTO eXGExamMstDTO);

    /**
     * 교육 시험 마스터 등록
     */
    public int insertExamMst(EXGExamMstInsertDTO eXGExamMstDTO);

}
