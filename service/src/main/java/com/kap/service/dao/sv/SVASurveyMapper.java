package com.kap.service.dao.sv;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.ex.exg.EXGExamMstInsertDTO;
import com.kap.core.dto.sv.sva.*;
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
     * 상생체감도 매핑 여부
     */
    public int getSurveyCmpnEpisdCnt(SVASurveyMstSearchDTO sVASurveyDTO);

        
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

    /**
     * 교육/컨설팅에 매핑된 설문지는 사용여부만 수정
     */
    public int updateSurveyMstExpnYn(SVASurveyMstInsertDTO sVASurveyMstInsertDTO);

    /**
     * 질문,보기 삭제
     */
    public int deleteSurveyQstnList(SVASurveyMstSearchDTO sVASurveyDTO);

    /**
     * 설문 질문 조회(내용)
     */
    public List<SVASurveyQstnDtlDTO> selectSurveyQstnTypeDtlList(SVASurveyMstSearchDTO sVASurveyDTO);

    /**
     * 설문질문 보기 조회(상생응답)
     */
    public List<SVASurveyExmplDtlDTO> selectSurveyExmplWinDtlList(SVASurveyQstnDtlDTO sVASurveyQstnDtlDTO);

    /**
     * 설문질문 보기 조회(교육응답)
     */
    public List<SVASurveyExmplDtlDTO> selectSurveyExmplEduDtlList(SVASurveyQstnDtlDTO sVASurveyQstnDtlDTO);

    /**
     * 강사 설문 질문 조회(내용)
     */
    public List<SVASurveyQstnDtlDTO> selectSurveyIsttrQstnTypeDtlList(SVASurveyMstSearchDTO sVASurveyDTO);

    /**
     * 설문질문 보기 조회(교육응답)
     */
    public List<SVASurveyExmplDtlDTO> selectSurveyIsttrExmplEduDtlList(SVASurveyQstnDtlDTO sVASurveyQstnDtlDTO);

    /**
     * 설문질문 보기 조회(컨설팅응답)
     */
    public List<SVASurveyExmplDtlDTO> selectSurveyExmplConDtlList(SVASurveyQstnDtlDTO sVASurveyQstnDtlDTO);

    /**
     * 설문 마스터 조회 (컨설팅)
     */
    public SVASurveyMstInsertDTO selectSurveyConDtl(SVASurveyMstSearchDTO sVASurveyDTO);

    /**
     * 설문 응답 마스터 등록
     */
    public int insertSurveyRspnMst(SVASurveyRspnMstInsertDTO sVASurveyRspnMstDTO);

    /**
     * 설문 응답 등록 객관식
     */
    public int insertSurveyRspnMtlccDtl(SVASurveyExmplRspnDtlDTO sVASurveyExmplRspnDtlDTO);

    /**
     * 설문 응답 등록 주관식
     */
    public int insertSurveyRspnSbjctDtl(SVASurveyExmplRspnDtlDTO sVASurveyExmplRspnDtlDTO);

    /**
     * 설문 점수 계산
     */
    public int selectSurveyScore(SVASurveyRspnScoreDTO sVASurveyRspnScoreDTO);

    /**
     * 설문 마스터 조회(Api)
     */
    public SVASurveyApiMstInsertDTO selectApiSurveyDtl(SVASurveyMstSearchDTO sVASurveyDTO);

    /**
     * 설문 질문 조회(Api)
     */
    public List<SVASurveyApiQstnDtlDTO> selectApiSurveyQstnDtlList(SVASurveyMstSearchDTO sVASurveyDTO);


}
