package com.kap.service;

import com.kap.core.dto.COEMenuRoleDTO;

import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 메뉴 권한 변경 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: COEMenuRoleServiceImpl.java
 * @Description		: 메뉴 권한 변경 관리를 위한 Service
 * @author 신혜정
 * @since 2022.04.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2022.04.14		신혜정					최초생성
 * </pre>
 */
public interface COEMenuRoleService {

    /**
     * 메뉴권한 변경 목록을 조회한다.
     */
    public COEMenuRoleDTO selectMenuRoleLogList(COEMenuRoleDTO pCoMenuRoleDTO) throws Exception;

    /**
     * 메뉴권한 변경 상세 조회한다.
     */
    public COEMenuRoleDTO selectMenuRoleLogWrite(COEMenuRoleDTO pCoMenuRoleDTO) throws Exception;

    /**
     * 엑셀 생성
     */
    public void excelDownload(COEMenuRoleDTO pCoMenuRoleDTO, HttpServletResponse response) throws Exception;
}
