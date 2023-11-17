package com.kap.service.dao.sv;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.sv.sva.SVASurveyExmplDtlDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
import com.kap.core.dto.sv.sva.SVASurveyQstnDtlDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 설문조사 Mapper
 * </pre>
 *
 * @ClassName		: SVASurveyMapper.java
 * @Description		: 설문조사 Mapper
 * @author 박준희
 * @since 2023.11.06
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.11.06	  박준희	             최초 생성
 * </pre>
 */
@Mapper
public interface SVASurveyMapper {
    /**
     * 설문문항 코드 조회
   */
    public List<COCodeDTO> getSurveyTypeList() throws Exception;


    /**
     * 목록을 조회
     */
    public List<SVASurveyMstSearchDTO> selectSurveyList(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception;
    /**
     * 목록개수를 조회
     */
    public int selectSurveyListCnt(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception;


    /**
     * 설문 마스터 등록
     */
    public int insertSurveyMst(SVASurveyMstInsertDTO sVASurveyMstInsertDTO);

    /**
     * 설문 질문 등록
     */
    public int insertSurveyQstnDtl(SVASurveyMstInsertDTO sVASurveyMstInsertDTO);

    /**
     * 설문 질문 보기 등록
     */
    public int insertSurveyExmplDtl(SVASurveyQstnDtlDTO sVASurveyQstnDtlDTO);

    /**
     * 설문 마스터 수정
     */
    public int updateSurveyMst(SVASurveyMstInsertDTO sVASurveyMstInsertDTO);

    /**
     * 교육회차 매핑 여부
     */
    public int getSurveyEdctnEpisdCnt(SVASurveyMstSearchDTO sVASurveyDTO);

    /**
     * 컨설팅 매핑 여부
     */
    public int getSurveyCnstgRsumeCnt(SVASurveyMstSearchDTO sVASurveyDTO);

    /**
     * 설문 삭제
     */
    public int deleteSurveyMst(SVASurveyMstSearchDTO sVASurveyDTO);


    /**
     * 설문 마스터 조회
     */
    public SVASurveyMstInsertDTO selectSurveyDtl(SVASurveyMstSearchDTO sVASurveyDTO);

    /**
     * 설문 질문 조회
     */
    public List<SVASurveyQstnDtlDTO> selectSurveyQstnDtlList(SVASurveyMstSearchDTO sVASurveyDTO);

    /**
     * 설문질문 보기 조회
     */
    public List<SVASurveyExmplDtlDTO> selectSurveyExmplDtlList(SVASurveyQstnDtlDTO sVASurveyQstnDtlDTO);

}