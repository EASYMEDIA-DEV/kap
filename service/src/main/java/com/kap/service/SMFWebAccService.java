package com.kap.service;

import com.kap.core.dto.SMFWebAccDTO;

/**
 * <pre>
 * 웹접근성 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: SMFWebAccService.java
 * @Description		: 웹접근성 관리를 위한 Service
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
public interface SMFWebAccService {
    /**
     * 웹접근성 목록을 조회한다.
     */public SMFWebAccDTO selectWebAccList(SMFWebAccDTO smfWebAccDTO) throws Exception;

    /**
     * 웹접근성 개수를 조회한다.
     */
    public int selectWebAccCnt(SMFWebAccDTO smfWebAccDTO) throws Exception;

    /**
     * 웹접근성 상세를 조회한다.
     */
    public SMFWebAccDTO selectWebAccDtl(SMFWebAccDTO smfWebAccDTO) throws Exception;

    /**
     * 웹접근성을 등록한다.
     */
    public int insertWebAcc(SMFWebAccDTO smfWebAccDTO) throws Exception;

    /**
     * 웹접근성을 수정한다.
     */
    public int updateWebAcc(SMFWebAccDTO smfWebAccDTO) throws Exception;

    /**
     * 웹접근성을 삭제한다.
     */
    public int deleteWebAcc(SMFWebAccDTO smfWebAccDTO) throws Exception;
}
