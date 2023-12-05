package com.kap.service.mp.mpb;

import com.kap.core.dto.MPBBusDto;
import com.kap.core.dto.MPBEduDto;
import com.kap.core.dto.MPBSanDto;

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
public interface MPBMemberPartsSocietyService {


    /**
     * 교육 사업 리스트 조회
     * @param mpbEduDto
     * @return
     * @throws Exception
     */
    MPBEduDto selectEduList(MPBEduDto mpbEduDto) throws Exception;

    /**
     * 컨설팅 리스트 조회
     */
    MPBBusDto selectBusList(MPBBusDto mpbBusDto) throws Exception;

    /**
     * 상생 리스트 조회
     */
    MPBSanDto selectSanList(MPBSanDto mpbSanDto) throws Exception;


}