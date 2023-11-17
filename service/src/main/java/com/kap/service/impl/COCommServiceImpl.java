package com.kap.service.impl;

import com.kap.common.utility.CONetworkUtil;
import com.kap.core.annotation.CheckOriginData;
import com.kap.core.dto.COFrontHeaderNtfyDTO;
import com.kap.core.dto.COUserCmpnDto;
import com.kap.core.dto.MPAUserDto;
import com.kap.core.dto.MPEPartsCompanyDTO;
import com.kap.core.utility.COClassUtil;
import com.kap.service.COCommService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.COCommMapper;
import com.kap.service.dao.mp.MPAUserMapper;
import com.kap.service.dao.mp.MPEPartsCompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

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
    /*공통 서비스*/
    private final COCommMapper cOCommMapper;
    /*회원 서비스*/
    private final MPAUserMapper mpaUserMapper;
    /* 관리자 인증번호 로그 시퀀스*/
    private final EgovIdGnrService memModSeqIdgen;
    /**
     * 신청자, 부품사 정보 조회
     */
    public COUserCmpnDto getMemCmpnDtl(int memSeq) throws Exception{
        return cOCommMapper.getMemCmpnDtl(memSeq);
    }
    /**
     * 신청자, 부품사 정보 수정
     */
    public int setMemCmpnDtl(COUserCmpnDto cOUserCmpnDto, HttpServletRequest request) throws Exception{
        int rspCnt = 0;
        //데이터 변경 확인
        COUserCmpnDto rtnUserCmpnDto =  cOCommMapper.getMemCmpnDtl(cOUserCmpnDto.getMemMemSeq());
        if(COClassUtil.isChangeOriginData(cOUserCmpnDto, rtnUserCmpnDto)){
            //회원 정보 수정
            cOUserCmpnDto.setModId(COUserDetailsHelperService.getAuthenticatedUser().getId());
            cOUserCmpnDto.setModIp(CONetworkUtil.getMyIPaddress(request));
            cOUserCmpnDto.setCmpnBsnmNo( rtnUserCmpnDto.getCmpnBsnmNo() );
            rspCnt = cOCommMapper.updateUserDtl(cOUserCmpnDto);
            //회원 변경 이력 생성
            MPAUserDto mpaUserDto = new MPAUserDto();
            mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
            mpaUserDto.setModCd("AD");
            mpaUserDto.setRegId(COUserDetailsHelperService.getAuthenticatedUser().getId());
            mpaUserDto.setRegIp(CONetworkUtil.getMyIPaddress(request));
            mpaUserDto.setId(rtnUserCmpnDto.getMemId());
            mpaUserMapper.insertUserDtlHistory(mpaUserDto);
            //조회 한 회원의 회원 코드가 회사인 경우 수정
            if("CP".equals( rtnUserCmpnDto.getMemMemCd() )){
                cOCommMapper.updatePartsCompany(cOUserCmpnDto);
            }
        }
        return rspCnt;
    }

    /**
     * 사용자 상단 공지사항
     */
    public List<COFrontHeaderNtfyDTO> getHeaderNtfyList() throws Exception{
        return cOCommMapper.getHeaderNtfyList();
    }
}
