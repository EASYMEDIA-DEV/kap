package com.kap.service.dao.sm;

import com.kap.core.dto.SMISmsCntnDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * SMS 내용 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMISmsCntnMapper.java
 * @Description		: SMS 내용 관리를 위한 DAO
 * @author 구은희
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.16	구은희				   최초 생성
 * </pre>
 */
@Mapper
public interface SMISmsCntnMapper {

    /**
     * 목록을 조회
     */
    public List<SMISmsCntnDTO> selectSmsCntnList(SMISmsCntnDTO smiSmsCntnDTO) throws Exception;

    /**
     * SMS 내용 관리 개수를 조회
     */
    public int selectSmsCntnCnt(SMISmsCntnDTO smiSmsCntnDTO) throws Exception;

    /**
     * 상세를 조회
     */
    public SMISmsCntnDTO selectSmsCntnDtl(SMISmsCntnDTO smiSmsCntnDTO) throws Exception;

    /**
     * SMS 내용 등록 시 구분 코드 중복을 확인한다.
     */
    public int selectSmsCodeDupCheck(SMISmsCntnDTO smiSmsCntnDTO) throws Exception;


    /**
     * SMS 내용 등록
     */
    public int insertSmsCntn(SMISmsCntnDTO smiSmsCntnDTO) throws Exception;


    /**
     * SMS 내용 수정
     */
    public int updateSmsCntn(SMISmsCntnDTO smiSmsCntnDTO) throws Exception;

}
