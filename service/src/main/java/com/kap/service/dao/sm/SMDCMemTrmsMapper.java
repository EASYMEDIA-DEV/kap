package com.kap.service.dao.sm;

import com.kap.core.dto.SMDCMemTrmsDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <pre>
 * 회원약관 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMDCMemTrmsMapper.java
 * @Description		: 회원약관 관리를 위한 DAO
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
public interface SMDCMemTrmsMapper {

    /**
     * 상세를 조회
     */
    public SMDCMemTrmsDTO selectMemTrmsDtl(SMDCMemTrmsDTO smdcMemTrmsDTO) throws Exception;

    /**
     * 현재 카테고리의 CMS 시퀀스 값을 조회
     */
    public String selectSeqNum(String tableNm) throws Exception;

    /**
     * 회원약관 시퀀스 값을 증가
     */
    public int updateMemTrmsSeq(String tableNm) throws Exception;

    /**
     * 등록
     */
    public int insertMemTrms(SMDCMemTrmsDTO smdcMemTrmsDTO) throws Exception;

    /**
     * 수정
     */
    public int updateMemTrms(SMDCMemTrmsDTO smdcMemTrmsDTO) throws Exception;

}
