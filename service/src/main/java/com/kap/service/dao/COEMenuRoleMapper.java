package com.kap.service.dao;

import com.kap.core.dto.COEMenuRoleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 메뉴권한 변경 로그 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: COEMenuRoleMapper.java
 * @Description		: 메뉴권한 변경 로그 관리를 위한 DAO
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
@Mapper
public interface COEMenuRoleMapper {

    /**
     * 메뉴권한변경 로그 목록 갯수 조회
     */
    public int getMenuRoleLogListCnt(COEMenuRoleDTO pCOMenuRoleDTO) throws Exception;

    /**
     * 메뉴권한변경 로그 목록 조회
     */
    public List<COEMenuRoleDTO> selectMenuRoleLogList(COEMenuRoleDTO pCOMenuRoleDTO) throws Exception;

    /**
     * 수정 상세를 조회한다.
     */
    public COEMenuRoleDTO selectModDtl(COEMenuRoleDTO pCOMenuRoleDTO) throws Exception;

    /**
     * 수정 메뉴 목록을 조회한다.
     */
    public List<String> selectModMenuList(COEMenuRoleDTO pCOMenuRoleDTO) throws Exception;


}
