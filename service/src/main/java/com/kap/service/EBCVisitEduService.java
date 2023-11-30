package com.kap.service;

import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;

/**
 * <pre>
 * 방문교육 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: EBCVisitEduService.java
 * @Description		: 방문교육 관리를 위한 Service
 * @author 구은희
 * @since 2023.11.30
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.30		구은희				   최초 생성
 * </pre>
 */
public interface EBCVisitEduService {

    /**
     * 목록을 조회한다.
     */
    public EBCVisitEduDTO selectVisitEduList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

    /**
     * 방문교육 신청회원 정보 수정
     */
    public int updateVisitEdu(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

    /**
     * 방문교육 상세 조회
     */
    public EBCVisitEduDTO selectVisitEduDtl(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

}
