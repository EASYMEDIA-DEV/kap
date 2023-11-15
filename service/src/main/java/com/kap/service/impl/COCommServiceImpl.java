package com.kap.service.impl;

import com.kap.common.utility.CONetworkUtil;
import com.kap.core.annotation.CheckOriginData;
import com.kap.core.dto.COUserCmpnDto;
import com.kap.core.utility.COClassUtil;
import com.kap.service.COCommService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.COCommMapper;
import com.kap.service.dao.mp.MPAUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 공통 Service 구현
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.15		이옥정				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COCommServiceImpl implements COCommService {

    private final COCommMapper cOCommMapper;
    /**
     * 신청자, 부품사 정보 조회
     */
    public COUserCmpnDto getMemCmpnDtl(int memSeq) throws Exception{
        return cOCommMapper.getMemCmpnDtl(memSeq);
    }
    /**
     * 신청자, 부품사 정보 수정
     */
    public int updateMemCmpnDtl(COUserCmpnDto cOUserCmpnDto, HttpServletRequest request) throws Exception{
        int rspCnt = 0;
        //데이터 변경 확인
        COUserCmpnDto rtnUserCmpnDto =  cOCommMapper.getMemCmpnDtl(cOUserCmpnDto.getMemMemSeq());
        if(COClassUtil.isChangeOriginData(cOUserCmpnDto, rtnUserCmpnDto)){
            //회원 정보 수정
            cOUserCmpnDto.setModId(COUserDetailsHelperService.getAuthenticatedUser().getId());
            cOUserCmpnDto.setModId(CONetworkUtil.getMyIPaddress(request));
            cOCommMapper.updateMem(cOUserCmpnDto);
        }
        log.error("CHECK : {}", COClassUtil.isChangeOriginData(cOUserCmpnDto, rtnUserCmpnDto));
        //회사 정보

        return rspCnt;
    }
}
