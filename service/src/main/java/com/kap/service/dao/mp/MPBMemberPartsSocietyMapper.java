package com.kap.service.dao.mp;

import com.kap.core.dto.MPAUserDto;
import com.kap.core.dto.MPBEduDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 부품사회원 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: MPBMemberPartsSocietyMapper.java
 * @Description		: 부품사회원 관리를 위한 mapper
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


@Mapper
public interface MPBMemberPartsSocietyMapper {

    /**
     * 교육 사업 현황 리스트
     * @param mpbEduDto
     * @return
     */
    List<MPBEduDto> selectEduList(MPBEduDto mpbEduDto);

    /**
     * 교육 사업 현황 cnt
     * @param mpbEduDto
     * @return
     */
    int selectEduListCnt(MPBEduDto mpbEduDto);
}