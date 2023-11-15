package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.annotation.MaskedFieldProcessor;
import com.kap.core.dto.MPAUserDto;
import com.kap.service.MPBMemberPartsSocietyService;
import com.kap.service.dao.mp.MPAUserMapper;
import com.kap.service.dao.mp.MPBMemberPartsSocietyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <pre>
 * 부품사회원 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPAUserServiceImpl.java
 * @Description		: 부품사회원 관리를 위한 ServiceImpl
 * @author 양현우
 * @since 2023.11.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.1		양현우				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MPBMemberPartsSocietyServiceImpl implements MPBMemberPartsSocietyService {

    private final MPBMemberPartsSocietyMapper mpbMemberPartsSocietyMapper;


}