package com.kap.service.dao.sm;

import com.kap.core.dto.sm.smk.SMKTrendDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * TREND 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMCTrendMapper.java
 * @Description		: TREND 관리를 위한 DAO
 * @author 구은희
 * @since 2023.12.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		구은희				   최초 생성
 * </pre>
 */
@Mapper
public interface SMKTrendMapper {

    /**
     * TREND 목록을 조회
     */
    public List<SMKTrendDTO> selectTrendList(SMKTrendDTO smkTrendDTO) throws Exception;
    /**
     * TREND 개수를 조회
     */
    public int selectUseTrendCnt(SMKTrendDTO smkTrendDTO) throws Exception;
    /**
     * TREND 상세를 조회
     */
    public SMKTrendDTO selectTrendDtl(SMKTrendDTO smkTrendDTO) throws Exception;

    /**
     * TREND 수정
     */
    public int updateTrend(SMKTrendDTO smkTrendDTO) throws Exception;
    /**
     * TREND 미노출 여부를 수정
     */
    public int updateUseYn(SMKTrendDTO smkTrendDTO) throws Exception;
    /**
     * TREND 정렬을 수정
     */
    public void updateOrder(SMKTrendDTO smkTrendDTO) throws Exception;
    /**
     * TREND 등록
     */
    public int insertTrend(SMKTrendDTO smkTrendDTO) throws Exception;
    /**
     * TREND 삭제
     */
    public int deleteTrend(SMKTrendDTO smkTrendDTO) throws Exception;
    /**
     * 정렬할 TREND 조회
     */
    public SMKTrendDTO selectTrendNewRow(SMKTrendDTO smkTrendDTO) throws Exception;

}
