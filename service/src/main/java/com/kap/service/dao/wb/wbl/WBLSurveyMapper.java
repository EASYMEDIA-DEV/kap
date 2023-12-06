package com.kap.service.dao.wb.wbl;

import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
import com.kap.core.dto.wb.wbl.WBLEpisdMstDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstInsertDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 상생협력체감도조사 Mapper
 * </pre>
 *
 * @ClassName		: SVASurveyMapper.java
 * @Description		: 상생협력체감도조사 Mapper
 * @author 박준희
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.11.20	  박준희	             최초 생성
 * </pre>
 */
@Mapper
public interface WBLSurveyMapper {


    /**
     * 목록을 조회
     */
    public List<WBLSurveyMstSearchDTO> selectSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception;
    /**
     * 목록개수를 조회
     */
    public int selectSurveyListCnt(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception;

    /**
     * 등록
     */
    public int insertSurveyMst(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO);

    /**
     * 설문 삭제
     */
    public int deleteSurveyMst(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO);

    /**
     * 상세 조회
     */
    public WBLSurveyMstInsertDTO selectSurveyDtl(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO);

    public List<WBLEpisdMstDTO> selectEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception;
    /**
     * 목록개수를 조회
     */
    public int selectEpisdListCnt(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception;


    /**
     * 설문 삭제
     */
    public int deleteEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO);

    /**
     * 등록
     */
    public int insertEpisdMst(WBLEpisdMstDTO wBLEpisdMstDTO);

    /**
     * 상세 조회
     */
    public WBLEpisdMstDTO selectEpisdMst(WBLEpisdMstDTO wBLEpisdMstDTO);

    /**
     * 회차 수정
     */
    public int updateEpisdMst(WBLEpisdMstDTO wBLEpisdMstDTO);

    /**
     * 회차 설문 조회
     */

    public List<WBLEpisdMstDTO> selectEpisdSurveyList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception;

    /**
     * 응답 삭제
     */
    public int deleteSurveyRspn(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO);

    /**
     * 응답 초기화
     */
    public int updateSurveyRspn(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO);

}
