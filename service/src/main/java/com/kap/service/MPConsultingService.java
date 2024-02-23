package com.kap.service;

import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;

/**
 * <pre>
 * 마이페이지 > 컨설팅신청내역을 위한 Service
 * </pre>
 *
 * @ClassName		: MPConsultingService.java
 * @Description		: 마이페이지 > 컨설팅신청내역을 위한 Service
 * @author 김학규
 * @since 2024.02.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.02.08		김학규				   최초 생성
 * </pre>
 */
public interface MPConsultingService {
    /**
     * 컨설팅 리스트 조회
     * @return
     * @throws Exception
     */
    CBATechGuidanceInsertDTO selectConsultingList(CBATechGuidanceInsertDTO cBATechGuidanceInsertDTO) throws Exception;


}
