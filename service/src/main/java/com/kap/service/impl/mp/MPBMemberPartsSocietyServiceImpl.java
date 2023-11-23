package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.annotation.MaskedFieldProcessor;
import com.kap.core.dto.*;
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


    /**
     * 교육 사업 리스트 조회
     * @param mpbEduDto
     * @return
     * @throws Exception
     */
    @Override
    public MPBEduDto selectEduList(MPBEduDto mpbEduDto) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpbEduDto.getPageIndex());
        page.setRecordCountPerPage(mpbEduDto.getListRowSize());

        page.setPageSize(mpbEduDto.getPageRowSize());

        mpbEduDto.setFirstIndex( page.getFirstRecordIndex() );
        mpbEduDto.setRecordCountPerPage( page.getRecordCountPerPage() );
        mpbEduDto.setTotalCount( mpbMemberPartsSocietyMapper.selectEduListCnt(mpbEduDto));
        List<MPBEduDto> mpbEduDtos = mpbMemberPartsSocietyMapper.selectEduList(mpbEduDto);

        mpbEduDto.setList( mpbEduDtos );
        return mpbEduDto;
    }

    /**
     * 컨설팅 리스트 조회
     * @param mpbBusDto
     * @return
     * @throws Exception
     */
    @Override
    public MPBBusDto selectBusList(MPBBusDto mpbBusDto) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpbBusDto.getPageIndex());
        page.setRecordCountPerPage(mpbBusDto.getListRowSize());

        page.setPageSize(mpbBusDto.getPageRowSize());

        mpbBusDto.setFirstIndex( page.getFirstRecordIndex() );
        mpbBusDto.setRecordCountPerPage( page.getRecordCountPerPage() );

        mpbBusDto.setTotalCount( mpbMemberPartsSocietyMapper.selectBusListCnt(mpbBusDto));
        List<MPBBusDto> mpbBusDtos = mpbMemberPartsSocietyMapper.selectBusList(mpbBusDto);

        mpbBusDto.setList( mpbBusDtos );
        return mpbBusDto;
    }

    /**
     * 상생 리스트 조회
     * @param mpbSanDto
     * @return
     * @throws Exception
     */
    @Override
    public MPBSanDto selectSanList(MPBSanDto mpbSanDto) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpbSanDto.getPageIndex());
        page.setRecordCountPerPage(mpbSanDto.getListRowSize());

        page.setPageSize(mpbSanDto.getPageRowSize());

        mpbSanDto.setFirstIndex( page.getFirstRecordIndex() );
        mpbSanDto.setRecordCountPerPage( page.getRecordCountPerPage() );
        mpbSanDto.setTotalCount( mpbMemberPartsSocietyMapper.selectSanListCnt(mpbSanDto));
        List<MPBSanDto> mpbSanDtos = mpbMemberPartsSocietyMapper.selectSanList(mpbSanDto);

        mpbSanDto.setList( mpbSanDtos );
        return mpbSanDto;
    }
}