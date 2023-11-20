package com.kap.service.dao.wb.wbl;

import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
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

}
