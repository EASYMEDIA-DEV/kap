package com.kap.service.dao.sm;

import com.kap.core.dto.SMELegNtcDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 법적고지 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMELegNtcMapper.java
 * @Description		: 법적고지 관리를 위한 DAO
 * @author 허진영
 * @since 2023.09.26
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.10.04		구은희				   최초 생성
 * </pre>
 */
@Mapper
public interface SMELegNtcMapper {

    /**
     * 법적고지 목록을 조회
     */public List<SMELegNtcDTO> selectLegNtcList(SMELegNtcDTO smeLegNtcDTO) throws Exception;

    /**
     * 법적고지 개수를 조회
     */
    public int selectLegNtcCnt(SMELegNtcDTO smeLegNtcDTO) throws Exception;

    /**
     * 법적고지 상세를 조회
     */
    public SMELegNtcDTO selectLegNtcDtl(SMELegNtcDTO smeLegNtcDTO) throws Exception;

    /**
     * 현재 카테고리의 CMS 시퀀스 값을 조회
     */
    public String selectSeqNum(String tableNm) throws Exception;

    /**
     * 법적고지 시퀀스 값을 증가
     */
    public int updateLegNtcSeq(String tableNm) throws Exception;

    /**
     * 법적고지 등록
     */
    public int insertLegNtc(SMELegNtcDTO smeLegNtcDTO) throws Exception;

    /**
     * 법적고지 수정
     */
    public int updateLegNtc(SMELegNtcDTO smeLegNtcDTO) throws Exception;

    /**
     * 법적고지 삭제
     */
    public int deleteLegNtc(SMELegNtcDTO smeLegNtcDTO) throws Exception;


}
