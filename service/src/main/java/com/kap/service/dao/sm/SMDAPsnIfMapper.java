package com.kap.service.dao.sm;

import com.kap.core.dto.sm.smd.SMDAPsnIfDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 개인정보처리방침 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMDAPsnIfMapper.java
 * @Description		: 개인정보처리방침 관리를 위한 DAO
 * @author 구은희
 * @since 2023.09.26
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.26		구은희				   최초 생성
 * </pre>
 */
@Mapper
public interface SMDAPsnIfMapper {

    /**
     * 목록을 조회
     */public List<SMDAPsnIfDTO> selectPsnIfList(SMDAPsnIfDTO smdPsnIfDTO) throws Exception;

    /**
     * 목록 개수를 조회
     */
    public int selectPsnIfCnt(SMDAPsnIfDTO smdPsnIfDTO) throws Exception;

    /**
     * 상세를 조회
     */
    public SMDAPsnIfDTO selectPsnIfDtl(SMDAPsnIfDTO smdPsnIfDTO) throws Exception;

    /**
     * 현재 카테고리의 CMS 시퀀스 값을 조회
     */
    public String selectSeqNum(String tableNm) throws Exception;

    /**
     * 개인정보처리방침 시퀀스 값을 증가
     */
    public int updatePsnIfSeq(String tableNm) throws Exception;

    /**
     * 등록
     */
    public int insertPsnIf(SMDAPsnIfDTO smdPsnIfDTO) throws Exception;

    /**
     * 수정
     */
    public int updatePsnIf(SMDAPsnIfDTO smdPsnIfDTO) throws Exception;

    /**
     * 삭제
     */
    public int deletePsnIf(SMDAPsnIfDTO smdPsnIfDTO) throws Exception;


}
