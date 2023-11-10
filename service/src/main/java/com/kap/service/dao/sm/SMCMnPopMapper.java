package com.kap.service.dao.sm;

import com.kap.core.dto.SMCMnPopDTO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <pre>
 * 팝업 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMCMnPopMapper.java
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
public interface SMCMnPopMapper {

    /**
     * 팝업 목록을 조회
     */
    public List<SMCMnPopDTO> selectMnPopList(SMCMnPopDTO smcMnPopDTO) throws Exception;
    /**
     * 팝업 개수를 조회
     */
    public int selectUseMnPopCnt(SMCMnPopDTO smcMnPopDTO) throws Exception;
    /**
     * 팝업 상세를 조회
     */
    public SMCMnPopDTO selectMnPopDtl(SMCMnPopDTO smcMnPopDTO) throws Exception;
    /**
     * 현재 카테고리의 CMS 시퀀스 값을 가져온다.
     */
    public String selectSeqNum(String tableNm) throws Exception;

    /**
     * 팝업 시퀀스 값을 상승시킨다.
     */
    public int updatePopSeq(String tableNm) throws Exception;

    /**
     * 팝업을 수정
     */
    public int updateMnPop(SMCMnPopDTO smcMnPopDTO) throws Exception;
    /**
     * 팝업 미노출 여부를 수정
     */
    public int updateUseYn(SMCMnPopDTO smcMnPopDTO) throws Exception;
    /**
     * 팝업 정렬을 수정
     */
    public void updateOrder(SMCMnPopDTO smcMnPopDTO) throws Exception;
    /**
     * 팝업을 등록
     */
    public int insertMnPop(SMCMnPopDTO smcMnPopDTO) throws Exception;
    /**
     * 팝업을 삭제
     */
    public int deleteMnPop(SMCMnPopDTO smcMnPopDTO) throws Exception;
    /**
     * 정렬할 팝업을 조회
     */
    public SMCMnPopDTO selectPopNewRow(SMCMnPopDTO smcMnPopDTO) throws Exception;

}
