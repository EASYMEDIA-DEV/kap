package com.kap.service;

import com.kap.core.dto.mp.mph.MPHNewsLetterDTO;

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
 * </pre>
 */
public interface MPHNewsLetterService {

    /**
     * 목록을 조회한다.
     */
    public MPHNewsLetterDTO selectNewsLetterList(MPHNewsLetterDTO mphNewsLetterDTO) throws Exception;
}
