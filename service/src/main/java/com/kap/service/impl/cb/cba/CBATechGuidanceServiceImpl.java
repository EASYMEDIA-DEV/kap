package com.kap.service.impl.cb.cba;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.cb.cba.CBATechGuidanceDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.service.CBATechGuidanceService;
import com.kap.service.COFileService;
import com.kap.service.COSystemLogService;
import com.kap.service.dao.cb.cba.CBATechGuidanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.HashMap;

/**
 * <pre>
 * 컨설팅 기술 지도를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: CBATechGuidanceServiceImpl.java
 * @Description		: 컨설팅 기술 지도를 위한 ServiceImpl
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.14		임서화				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CBATechGuidanceServiceImpl implements CBATechGuidanceService {

    private final COFileService cOFileService;

    /* 컨설팅 기술 지도 시퀀스 */
    private final EgovIdGnrService winBusIdgen;

    CBATechGuidanceMapper cBATechGuidanceMapper;

    // 로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    // 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;

    @Value("${app.file.videoExtns}")
    private String videoExtns;

    /**
     * 컨설팅 기술 지도 목록 조회
     */
    public CBATechGuidanceDTO selectTechGuidanceList(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pCBATechGuidanceDTO.getPageIndex());
        page.setRecordCountPerPage(pCBATechGuidanceDTO.getListRowSize());

        page.setPageSize(pCBATechGuidanceDTO.getPageRowSize());

        pCBATechGuidanceDTO.setFirstIndex(page.getFirstRecordIndex());
        pCBATechGuidanceDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pCBATechGuidanceDTO.setTotalCount(cBATechGuidanceMapper.selectTechGuidanceTopCnt(pCBATechGuidanceDTO));
        pCBATechGuidanceDTO.setList(cBATechGuidanceMapper.selectTechGuidanceList(pCBATechGuidanceDTO));

        return pCBATechGuidanceDTO;
    }

    /**
     * 컨설팅 기술 지도 상세 조회
     */
    public CBATechGuidanceInsertDTO selectTechGuidanceDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {
        CBATechGuidanceInsertDTO info = new CBATechGuidanceInsertDTO();

        if (!"".equals(pCBATechGuidanceInsertDTO.getDetailsKey()))
        {
            info = cBATechGuidanceMapper.selectTechGuidanceDtl(pCBATechGuidanceInsertDTO);
        }

        return info;
    }

    /**
     * 컨설팅 기술 지도 등록
     */
    public int insertTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pCBATechGuidanceInsertDTO.getFileList());
        pCBATechGuidanceInsertDTO.setItrdcFileSeq(fileSeqMap.get("itrdcFileSeq"));
        pCBATechGuidanceInsertDTO.setImpvmFileSeq(fileSeqMap.get("impvmFileSeq"));
        pCBATechGuidanceInsertDTO.setCnstgSeq(winBusIdgen.getNextIntegerId());

        // 신청 회원 정보
        HashMap memberMap = new HashMap();
        memberMap.put("memSeq", pCBATechGuidanceInsertDTO.getMemSeq());
        memberMap.put("deptCd", pCBATechGuidanceInsertDTO.getDeptCd());
        memberMap.put("deptDtlNm", pCBATechGuidanceInsertDTO.getDeptDtlNm());
        memberMap.put("pstnCd", pCBATechGuidanceInsertDTO.getPstnCd());
        memberMap.put("memTelNo", pCBATechGuidanceInsertDTO.getMemTelNo());
        updateTechMemberInfo(memberMap);

        // 부품사 정보
        ModelMap companyMap = new ModelMap();

        String ctgryCd = pCBATechGuidanceInsertDTO.getCtgryCd();
        // 1차사 - 5스타
        if(ctgryCd.equals("COMPANY01001")){
            companyMap.put("qlty5StarCd", pCBATechGuidanceInsertDTO.getQlty5StarCd());
            companyMap.put("qlty5StarYear", pCBATechGuidanceInsertDTO.getQlty5StarYear());
            companyMap.put("pay5StarCd", pCBATechGuidanceInsertDTO.getPay5StarCd());
            companyMap.put("tchlg5StarCd", pCBATechGuidanceInsertDTO.getTchlg5StarCd());
        } else if(ctgryCd.equals("COMPANY01002")){ // 2차사 - SQ 정보
            companyMap.put("nm", pCBATechGuidanceInsertDTO.getNm());
            companyMap.put("score", pCBATechGuidanceInsertDTO.getScore());
            companyMap.put("year", pCBATechGuidanceInsertDTO.getYear());
            companyMap.put("crtfnCmpnNm", pCBATechGuidanceInsertDTO.getCrtfnCmpnNm());
        }
        companyMap.put("ctgryCd", pCBATechGuidanceInsertDTO.getCtgryCd());
        companyMap.put("sizeCd", pCBATechGuidanceInsertDTO.getSizeCd());
        companyMap.put("stbsmDt",pCBATechGuidanceInsertDTO.getStbsmDt());
        companyMap.put("zipcode", pCBATechGuidanceInsertDTO.getZipcode());
        companyMap.put("bscAddr", pCBATechGuidanceInsertDTO.getBscAddr());
        companyMap.put("dtlAddr", pCBATechGuidanceInsertDTO.getDtlAddr());
        companyMap.put("slsPmt", pCBATechGuidanceInsertDTO.getSlsPmt());
        companyMap.put("slsYear", pCBATechGuidanceInsertDTO.getSlsYear());
        companyMap.put("mpleCnt", pCBATechGuidanceInsertDTO.getMpleCnt());
        companyMap.put("mjrPrdct1", pCBATechGuidanceInsertDTO.getMjrPrdct1());
        companyMap.put("mjrPrdct2", pCBATechGuidanceInsertDTO.getMjrPrdct2());
        companyMap.put("mjrPrdct3", pCBATechGuidanceInsertDTO.getMjrPrdct3());
        updateTechCompanyInfo(companyMap);

        pCBATechGuidanceInsertDTO.setRespCnt(cBATechGuidanceMapper.insertTechGuidance(pCBATechGuidanceInsertDTO));
        return pCBATechGuidanceInsertDTO.getRespCnt();
    }

    void updateTechMemberInfo(HashMap memberMap){
        System.err.println("pCBATechGuidanceInsertDTO::"+memberMap);
        cBATechGuidanceMapper.updateTechMemberInfo(memberMap);
    }

    void updateTechCompanyInfo(HashMap companyMap){
        System.err.println("pCBATechGuidanceInsertDTO::"+companyMap);
        cBATechGuidanceMapper.updateTechCompanyInfo(companyMap);
    }

    /**
     * 컨설팅 기술 지도 삭제
     */
    public int deleteTechGuidance(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception {
        return cBATechGuidanceMapper.deleteTechGuidance(pCBATechGuidanceDTO);
    }

    /**
     * 컨설팅 기술 지도 수정
     */
    public int updateTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pCBATechGuidanceInsertDTO.getFileList());
        pCBATechGuidanceInsertDTO.setFileSeq(fileSeqMap.get("fileSeq"));

        pCBATechGuidanceInsertDTO.setRespCnt(cBATechGuidanceMapper.updateTechGuidance(pCBATechGuidanceInsertDTO));
        return pCBATechGuidanceInsertDTO.getRespCnt();
    }
}
