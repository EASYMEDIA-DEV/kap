package com.kap.service.dao.sm;

import com.kap.core.dto.SMCPopDTO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <pre>
 * 팝업 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMPopMapper.java
 * @Description		: 팝업 관리를 위한 DAO
 * @author 구은희
 * @since 2023.09.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.07		구은희				   최초 생성
 * </pre>
 */
@Mapper
public interface SMPopMapper {

    /**
     * 팝업 목록을 조회
     */
    public List<SMCPopDTO> selectMnPopList(SMCPopDTO smcPopDTO) throws Exception;
    /**
     * 팝업 개수를 조회
     */
    public int selectUseMnPopCnt(SMCPopDTO smcPopDTO) throws Exception;
    /**
     * 팝업 상세를 조회
     */
    public SMCPopDTO selectMnPopDtl(SMCPopDTO smcPopDTO) throws Exception;
    /**
     * 팝업을 수정
     */
    public int updateMnPop(SMCPopDTO smcPopDTO) throws Exception;
    /**
     * 팝업 미노출 여부를 수정
     */
    public int updateUseYn(SMCPopDTO smcPopDTO) throws Exception;
    /**
     * 팝업 정렬을 수정
     */
    public void updateOrder(SMCPopDTO smcPopDTO) throws Exception;
    /**
     * 팝업을 등록
     */
    public int insertMnPop(SMCPopDTO smcPopDTO) throws Exception;
    /**
     * 팝업을 삭제
     */
    public int deleteMnPop(SMCPopDTO smcPopDTO) throws Exception;
    /**
     * 정렬할 팝업을 조회
     */
    public SMCPopDTO selectPopNewRow(SMCPopDTO smcPopDTO) throws Exception;

}
