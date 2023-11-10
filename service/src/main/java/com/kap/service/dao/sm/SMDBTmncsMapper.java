package com.kap.service.dao.sm;

import com.kap.core.dto.SMDBTmncsDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <pre>
 * 이용약관 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMDBTmncsMapper.java
 * @Description		: 이용약관 관리를 위한 DAO
 * @author 구은희
 * @since 2023.11.06
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.06		구은희				   최초 생성
 * </pre>
 */
@Mapper
public interface SMDBTmncsMapper {

    /**
     * 상세를 조회
     */
    public SMDBTmncsDTO selectTmncsDtl(SMDBTmncsDTO smdbTmncsDTO) throws Exception;

    /**
     * 현재 카테고리의 CMS 시퀀스 값을 조회
     */
    public String selectSeqNum(String tableNm) throws Exception;

    /**
     * 개인정보처리방침 시퀀스 값을 증가
     */
    public int updateTmncsSeq(String tableNm) throws Exception;

    /**
     * 등록
     */
    public int insertTmncs(SMDBTmncsDTO smdbTmncsDTO) throws Exception;

    /**
     * 수정
     */
    public int updateTmncs(SMDBTmncsDTO smdbTmncsDTO) throws Exception;

}
