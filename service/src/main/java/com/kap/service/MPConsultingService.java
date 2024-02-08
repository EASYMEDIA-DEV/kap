package com.kap.service;

import com.kap.core.dto.MPBBusDto;
import com.kap.core.dto.MPBEduDto;
import com.kap.core.dto.MPBSanDto;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.eb.eba.EBACouseDTO;

/**
 * <pre>
 * 부품사회원 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: MPBMemberPartsSocietyService.java
 * @Description		: 부품사회원 관리를 위한 Service
 * @author 양현우
 * @since 2023.11.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.14		양현우				   최초 생성
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
