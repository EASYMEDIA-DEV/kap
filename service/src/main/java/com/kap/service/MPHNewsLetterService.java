package com.kap.service;

import com.kap.core.dto.mp.mph.MPHNewsLetterDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 뉴스레터 신청자 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: MPHNewsLetterService.java
 * @Description		: 뉴스레터 신청자 관리를 위한 Service
 * @author 구은희
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		구은희				   최초 생성
 *    2024.01.05        이옥정                  뉴스레터 이메일 중복 검사 추가
 *    2024.01.08        이옥정                  뉴스레터 등록 추가
 * </pre>
 */
public interface MPHNewsLetterService {

    /**
     * 목록을 조회한다.
     */
    public MPHNewsLetterDTO selectNewsLetterList(MPHNewsLetterDTO mphNewsLetterDTO) throws Exception;

    /**
     * 엑셀 생성
     */
    public void excelDownload(MPHNewsLetterDTO mphNewsLetterDTO, HttpServletResponse response) throws Exception;

    /**
     * 뉴스레터 이메일 중복 검사
     * @param mphNewsLetterDTO
     * @return
     * @throws Exception
     */
    int selectDupEmail(MPHNewsLetterDTO mphNewsLetterDTO) throws Exception;

    /**
     * 뉴스레터 등록
     * @param mphNewsLetterDTO
     * @return
     * @throws Exception
     */
    public int insertNewsletter(MPHNewsLetterDTO mphNewsLetterDTO, HttpServletRequest request) throws Exception;

}
