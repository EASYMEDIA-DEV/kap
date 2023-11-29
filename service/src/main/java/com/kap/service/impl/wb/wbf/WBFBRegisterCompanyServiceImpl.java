package com.kap.service.impl.wb.wbf;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundOptnMstDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBFBRegisterCompanyService;
import com.kap.service.dao.wb.wbf.WBFBRegisterCompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * 스마트공장구축 > 신청업체관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBFBRegisterCompanyServiceImpl.java
 * @Description		: 신청업체관리를 위한 ServiceImpl
 * @author 김동현
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.16		김동현				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WBFBRegisterCompanyServiceImpl implements WBFBRegisterCompanyService {

    /* Mapper */
    private final WBFBRegisterCompanyMapper wBFBRegisterCompanyMapper;

    //파일 서비스
    private final COFileService cOFileService;

    //파일 업로드 위치
    @Value("${app.file.upload-path}")
    private String fileUploadPath;

    /* 회사 상세 시퀀스 - SQ */
    private final EgovIdGnrService mpePartsCompanyDtlIdgen;

    /* 상생 신청 마스터 시퀀스 */
    private final EgovIdGnrService cxAppctnMstSeqIdgen;

    /* 상생 신청 상세 시퀀스 */
    private final EgovIdGnrService cxAppctnRsumeDtlSeqIdgen;

    /**
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) {
        return wBFBRegisterCompanyMapper.getOptYearList(wBFBRegisterSearchDTO);
    }

    /**
     *  사업회차 회차 검색
     */
    public List<String> getOptEpisdList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO)  throws Exception
    {
        return wBFBRegisterCompanyMapper.getOptEpisdList(wBFBRegisterSearchDTO);
    }

    /**
     *  사업 연도/회차에 맞는 사업 유형 SELECT
     */
    public WBRoundMstDTO getOptnList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO)  throws Exception
    {
        WBRoundMstDTO wBRoundMstDTO = new WBRoundMstDTO();

        int episdSeq = wBFBRegisterCompanyMapper.getEpisdSeq(wBFBRegisterSearchDTO);
        wBFBRegisterSearchDTO.setEpisdSeq(episdSeq);
        wBRoundMstDTO.setEpisdSeq(episdSeq);

        wBFBRegisterSearchDTO.setOptnCd("BGN_REG_INF01001");
        wBRoundMstDTO.setAsigtList(wBFBRegisterCompanyMapper.getOptnList(wBFBRegisterSearchDTO));

        wBFBRegisterSearchDTO.setOptnCd("BGN_REG_INF01002");
        wBRoundMstDTO.setBsinList(wBFBRegisterCompanyMapper.getOptnList(wBFBRegisterSearchDTO));

        return wBRoundMstDTO;
    }

    /**
     *   신청부품사 목록 List Get
     */
    public WBFBRegisterSearchDTO getRegisterCompanyList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception
    {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBFBRegisterSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBFBRegisterSearchDTO.getListRowSize());

        page.setPageSize(wBFBRegisterSearchDTO.getPageRowSize());

        wBFBRegisterSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBFBRegisterSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        List<WBFBRegisterSearchDTO> getMainLists = wBFBRegisterCompanyMapper.getRegisterCompanyList(wBFBRegisterSearchDTO);

        /* 신청 상세 Data Get - Add Sub List */
        for(int listIdx=0; listIdx < getMainLists.size(); listIdx++) {
            WBFBRegisterSearchDTO MainList = getMainLists.get(listIdx);
            WBFBRegisterSearchDTO SubList = wBFBRegisterCompanyMapper.getRegisterCompanySubList(MainList);

            /* Re Build */
            MainList.setRsumeSttsCd(SubList.getRsumeSttsCd());
            MainList.setRsumeSttsCdNm(SubList.getRsumeSttsCdNm());
            MainList.setAppctnSttsChngDtm(SubList.getAppctnSttsChngDtm());
            MainList.setMngSttsChngDtm(SubList.getMngSttsChngDtm());
            MainList.setMngSttsCd(SubList.getMngSttsCd());
            MainList.setMngSttsCdNm(SubList.getMngSttsCdNm());
            MainList.setModId(SubList.getModId());
            MainList.setModIp(SubList.getModIp());

            getMainLists.set(listIdx, MainList);
        }

        wBFBRegisterSearchDTO.setList(getMainLists);
        wBFBRegisterSearchDTO.setTotalCount(wBFBRegisterCompanyMapper.getRegisterCompanyCount(wBFBRegisterSearchDTO));

        return wBFBRegisterSearchDTO;
    }

    /**
     *  Write Page
     *  신청부품사 등록
     */
    public int putRegisterCompany(WBFBRegisterDTO wBFBRegisterDTO) throws Exception
    {

        int respCnt = 1;

        /* 회원 마스터 Update */
        respCnt *= wBFBRegisterCompanyMapper.updCoMemMst(wBFBRegisterDTO);

        /* 회사 마스터 Update */
        respCnt *= wBFBRegisterCompanyMapper.updCmpnCbsnMst(wBFBRegisterDTO);

        /* 회사 상세 Update or Insert */
        for(WBCompanyDetailMstDTO wBCompanyDetailMstDTO : wBFBRegisterDTO.getSqInfoList()) {
            wBCompanyDetailMstDTO.setBsnmNo(wBFBRegisterDTO.getBsnmNo());
            if(!Objects.nonNull(wBCompanyDetailMstDTO.getCbsnSeq())){
                /* cbsnSeq is null */
                wBCompanyDetailMstDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                wBCompanyDetailMstDTO.setRegId(wBFBRegisterDTO.getRegId());
                wBCompanyDetailMstDTO.setRegIp(wBFBRegisterDTO.getRegIp());
                respCnt *= wBFBRegisterCompanyMapper.insCmpnCbsnDtl(wBCompanyDetailMstDTO);
            } else {
                /* cbsnSeq is not null */
                wBCompanyDetailMstDTO.setModId(wBFBRegisterDTO.getModId());
                wBCompanyDetailMstDTO.setModIp(wBFBRegisterDTO.getModIp());
                respCnt *= wBFBRegisterCompanyMapper.updCmpnCbsnDtl(wBCompanyDetailMstDTO);
            }
        }

        /* 상생신청 마스터 */
        int firstAppctnMstIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
        wBFBRegisterDTO.setAppctnSeq(firstAppctnMstIdgen); /* 신청순번 */
        respCnt *= wBFBRegisterCompanyMapper.putAppctnMst(wBFBRegisterDTO);

        /* 상생신청 상세 */
        int firstAppctnRsumeDtlIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
        wBFBRegisterDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen); /* 진행순번 */
        /* 스마트 신청 상태 코드 */
        respCnt *= wBFBRegisterCompanyMapper.putAppctnRsumeDtl(wBFBRegisterDTO);

        /* 상생신청파일 상세 */
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBFBRegisterDTO.getFileList());
        wBFBRegisterDTO.setFileSeq(fileSeqMap.get("appctnFileSeq")); /* 파일 시퀀스 */
        respCnt *= wBFBRegisterCompanyMapper.putAppctnFileDtl(wBFBRegisterDTO);

        /* 상생신청 스마트 상세 */
        wBFBRegisterDTO.setAppctnSeq(firstAppctnRsumeDtlIdgen);
        respCnt *= wBFBRegisterCompanyMapper.putAppctnRsumeTaskDtl(wBFBRegisterDTO);

        return respCnt;
    }

}
