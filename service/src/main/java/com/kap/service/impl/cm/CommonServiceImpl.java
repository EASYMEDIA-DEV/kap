package com.kap.service.impl.cm;

import com.kap.common.utility.seed.COSeedCipherUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.MPPwdInitDto;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.CommonService;
import com.kap.service.dao.cm.CommonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {


    private final CommonMapper commonMapper;



    @Override
    public String selectSeq(String tableNm) {
        return commonMapper.selectSeqNum(tableNm);
    }

    @Override
    public int updatePwdInit(MPPwdInitDto mpPwdInitDto) throws Exception
    {
        // 특수문자 포함하도록 변경
        String password = RandomStringUtils.random(8, 0, 0, true, true, null, new SecureRandom()) + RandomStringUtils.random(2, 35, 64, false, false, null, new SecureRandom());
        COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();

        // & < > : ; ? ' " 공백은 치환 -> !
        password = password.replaceAll("[\\s&<>:;?']", "!");


        MPPwdInitDto mpPwdInitDtos = commonMapper.selectMemDtl(mpPwdInitDto);
        mpPwdInitDto.setPwd(password);
        mpPwdInitDto.setEmail(mpPwdInitDtos.getEmail());
        mpPwdInitDto.setMemCd(mpPwdInitDtos.getMemCd());
        mpPwdInitDto.setRegId( coaAdmDTO.getId() );
        mpPwdInitDto.setRegIp( coaAdmDTO.getLoginIp() );
        mpPwdInitDto.setModId( coaAdmDTO.getId() );
        mpPwdInitDto.setModIp( coaAdmDTO.getLoginIp() );


        mpPwdInitDto.setNewEncPwd(COSeedCipherUtil.encryptPassword(password, mpPwdInitDto.getId()));



        return commonMapper.updatePwdInit(mpPwdInitDto);
    }
}