package com.kap.service;

import com.kap.core.dto.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 일반사용자 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: MPAUserService.java
 * @Description		: 일반사용자 관리를 위한 Service
 * @author 양현우
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		양현우				   최초 생성
 * </pre>
 */
public interface MPAUserService {

    /**
     * 일반 사용자 목록 조회
     * @param mpaUserDto
     * @return
     * @throws Exception
     */
    public MPAUserDto selectUserList(MPAUserDto mpaUserDto) throws Exception;

    /**
     * 엑셀 생성
     * @param mpaUserDto
     * @param response
     * @throws Exception
     */
    void excelDownload(MPAUserDto mpaUserDto, HttpServletResponse response) throws Exception;

    /**
     * 일반 사용자 상세 조회
     */
    MPAUserDto selectUserDtl(MPAUserDto mpaUserDto) throws Exception;

    MPAUserDto selectUserDtlTab(MPAUserDto mpaUserDto) throws Exception;

    /**
     * 이메일 중복 검사
     * @param mpaUserDto
     * @return
     * @throws Exception
     */
    int selectDupEmail(MPAUserDto mpaUserDto) throws Exception;

    /**
     * id 중복 검사
     * @param mpaUserDto
     * @return
     * @throws Exception
     */
    int selectDupId(MPAUserDto mpaUserDto) throws Exception;



    int updateUserDtl(MPAUserDto mpaUserDto) throws Exception;

    /**
     * 미래차공모전 리스트 조회
     */
    MPAAttctnDto selectAttcntList(MPAAttctnDto mpaUserDto) throws Exception;

    /**
     * 문의 리스트 조회
     */
    MPAInqrDto selectInqrList(MPAInqrDto mpaUserDto) throws Exception;

    /**
     * 비밀번호 변경
     * @param mpPwdInitDto
     * @return
     * @throws Exception
     */
    int updatePwdInit(MPPwdInitDto mpPwdInitDto) throws Exception;
}