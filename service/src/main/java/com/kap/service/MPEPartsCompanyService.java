package com.kap.service;

import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;

/**
 * <pre>
 * 부품사 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: MPEPartsCompanyService.java
 * @Description		: 부품사 관리를 위한 Service
 * @author 구은희
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		구은희				   최초 생성
 * </pre>
 */
public interface MPEPartsCompanyService {

    /**
     * 부품사 목록을 조회한다.
     */
    public MPEPartsCompanyDTO selectPartsCompanyList(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 개수를 조회한다.
     */
    public int selectPartsCompanyCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 상세를 조회한다.
     */
    public MPEPartsCompanyDTO selectPartsCompanyDtl(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사 사업자등록번호 등록여부를 확인한다.
     */
    public MPEPartsCompanyDTO checkBsnmNo(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사를 등록한다.
     */
    public int insertPartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사를 수정한다.
     */
    public int updatePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 부품사를 삭제한다.
     */
    public int deletePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 교육사업 개수를 조회한다.
     */
    public int selectEduCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 컨설팅사업 개수를 조회한다.
     */
    public int selectConsultingCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 상생사업 개수를 조회한다.
     */
    public int selectWinBusinessCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;

    /**
     * 자동차부품산업대상 목록을 조회
     */
    public MPEPartsCompanyDTO selectCarTargetList(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception;
}
