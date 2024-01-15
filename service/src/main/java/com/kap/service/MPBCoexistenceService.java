package com.kap.service;

import com.kap.core.dto.mp.mpb.MPBBsnSearchDTO;
import com.kap.core.dto.mp.mpb.MPBCompanyDTO;

/**
 * <pre>
 * 마이페이지 상생 사업 신청내역 Service
 * </pre>
 *
 * @ClassName		: MPBCoexistenceService.java
 * @Description		: 마이페이지 상생 사업 신청내역 Service
 * @author 김태훈
 * @since 2023.11.27
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.08		김태훈				   최초 생성
 * </pre>
 */
public interface MPBCoexistenceService {

    /**
     *   마이페이지 상생 사업 신청내역 목록 List
     */
    public MPBBsnSearchDTO selectApplyList(MPBBsnSearchDTO mpbBnsSearchDTO, String type) throws Exception;

    /**
     *   마이페이지 상생 사업 신청내역 목록 count
     */
    public int selectApplyCount(MPBBsnSearchDTO mpbBnsSearchDTO) throws Exception;

    /**
     *   마이페이지 공통 상생 사업여부
     */
    public String getBusinessYn(MPBBsnSearchDTO mpbBnsSearchDTO) throws Exception;

    /**
     *   마이페이지 상생사업 상세
     */
    public MPBBsnSearchDTO getBsnDetail(MPBBsnSearchDTO mpbBnsSearchDTO) throws Exception;

    /**
     * 회원 부품사정보 상세 조회한다.
     * @return
     */
    public MPBCompanyDTO selectCompanyUserDtl(MPBBsnSearchDTO mpbBnsSearchDTO) throws Exception;
}
