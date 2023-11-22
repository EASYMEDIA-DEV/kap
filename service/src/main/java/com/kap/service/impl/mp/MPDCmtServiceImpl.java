package com.kap.service.impl.mp;

import com.kap.common.utility.seed.COSeedCipherUtil;
import com.kap.core.dto.MPAUserDto;
import com.kap.service.COFileService;
import com.kap.service.MPAUserService;
import com.kap.service.MPDCmtService;
import com.kap.service.dao.mp.MPAUserMapper;
import com.kap.service.dao.mp.MPDCmtMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <pre>
 * 위원 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPDCmtServiceImpl.java
 * @Description		: 위원 관리를 위한 ServiceImpl
 * @author 양현우
 * @since 2023.11.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.21		양현우				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MPDCmtServiceImpl implements MPDCmtService {

    private final COFileService cOFileService;

    private final EgovIdGnrService memSeqIdgen;

    private final EgovIdGnrService memModSeqIdgen;


    private final MPAUserMapper mpaUserMapper;

    private final MPDCmtMapper mpdCmtMapper;


    @Override
    public int insertCmt(MPAUserDto mpaUserDto) throws Exception {

        if(mpaUserDto.getFileList() != null && !mpaUserDto.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(mpaUserDto.getFileList());
            mpaUserDto.setCmssrPhotoFileSeq(fileSeqMap.get("fileSeq"));
        }
        mpaUserDto.setMemCd("CS");
        mpaUserDto.setBirth(mpaUserDto.getBirth().replaceAll("-",""));
        mpaUserDto.setMemSeq(memSeqIdgen.getNextIntegerId());
        mpaUserDto.setEncPwd(COSeedCipherUtil.encryptPassword(mpaUserDto.getPwd(), mpaUserDto.getId()));
        int i = mpdCmtMapper.insertCmt(mpaUserDto);
        mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
        mpaUserMapper.insertUserDtlHistory(mpaUserDto);
        return i;
    }

    @Override
    public int deleteCmt(MPAUserDto mpaUserDto) throws Exception {
        mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
        mpaUserMapper.insertUserDtlHistory(mpaUserDto);
        return  mpdCmtMapper.deleteCmt(mpaUserDto);
    }
}