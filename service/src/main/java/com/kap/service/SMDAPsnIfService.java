package com.kap.service;

import com.kap.core.dto.SMDAPsnIfDTO;

/**
 * <pre>
 * 개인정보처리방침 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: SMDAPsnIfService.java
 * @Description		: 개인정보처리방침 관리를 위한 Service
 * @author 구은희
 * @since 2023.09.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.26		구은희				   최초 생성
 * </pre>
 */
public interface SMDAPsnIfService {
    /**
     * 개인정보처리방침 목록을 조회한다.
     */public SMDAPsnIfDTO selectPsnIfList(SMDAPsnIfDTO smdPsnIfDTO) throws Exception;

    /**
     * 개인정보처리방침 개수를 조회한다.
     */
    public int selectPsnIfCnt(SMDAPsnIfDTO smdPsnIfDTO) throws Exception;

    /**
     * 개인정보처리방침 상세를 조회한다.
     */
    public SMDAPsnIfDTO selectPsnIfDtl(SMDAPsnIfDTO smdPsnIfDTO) throws Exception;

    /**
     * 개인정보처리방침을 등록한다.
     */
    public int insertPsnIf(SMDAPsnIfDTO smdPsnIfDTO) throws Exception;

    /**
     * 개인정보처리방침을 수정한다.
     */
    public int updatePsnIf(SMDAPsnIfDTO smdPsnIfDTO) throws Exception;

    /**
     * 개인정보처리방침을 삭제한다.
     */
    public int deletePsnIf(SMDAPsnIfDTO smdPsnIfDTO) throws Exception;
}
