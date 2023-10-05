package com.kap.service.dao.sm;

import com.kap.core.dto.SMFWebAccDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 웹접근성 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMFWebAccMapper.java
 * @Description		: 웹접근성 관리를 위한 DAO
 * @author 구은희
 * @since 2023.10.05
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.10.05		구은희				   최초 생성
 * </pre>
 */
@Mapper
public interface SMFWebAccMapper {

    /**
     * 목록을 조회
     */public List<SMFWebAccDTO> selectWebAccList(SMFWebAccDTO smfWebAccDTO) throws Exception;

    /**
     * 목록 개수를 조회
     */
    public int selectWebAccCnt(SMFWebAccDTO smfWebAccDTO) throws Exception;

    /**
     * 상세를 조회
     */
    public SMFWebAccDTO selectWebAccDtl(SMFWebAccDTO smfWebAccDTO) throws Exception;

    /**
     * 현재 카테고리의 CMS 시퀀스 값을 조회
     */
    public String selectSeqNum(String tableNm) throws Exception;

    /**
     * 웹접근성 시퀀스 값을 증가
     */
    public int updateWebAccSeq(String tableNm) throws Exception;

    /**
     * 등록
     */
    public int insertWebAcc(SMFWebAccDTO smfWebAccDTO) throws Exception;

    /**
     * 수정
     */
    public int updateWebAcc(SMFWebAccDTO smfWebAccDTO) throws Exception;

    /**
     * 삭제
     */
    public int deleteWebAcc(SMFWebAccDTO smfWebAccDTO) throws Exception;


}
