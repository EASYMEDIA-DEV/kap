package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBCVisitEduService;
import com.kap.service.MPEPartsCompanyService;
import com.kap.service.dao.eb.EBCVisitEduMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 방문교육 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: EBCVisitEduServiceImpl.java
 * @Description		: 방문교육 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.11.30
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.30		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class EBCVisitEduServiceImpl implements EBCVisitEduService {

    // DAO
    private final EBCVisitEduMapper ebcVisitEduMapper;
    // 파일 서비스
    private final COFileService cOFileService;
    
    // 부품사 관리 서비스
    public final MPEPartsCompanyService mpePartsCompanyService;

    /* 방문교육 결과 상세 시퀀스 */
    private final EgovIdGnrService edctnVstRsltSeqIdgen;

    /* 방문교육 결과옵션 상세 시퀀스 */
    private final EgovIdGnrService edctnVstOptnSeqIdgen;

    /**
     * 방문교육 목록을 조회한다.
     */
    public EBCVisitEduDTO selectVisitEduList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(ebcVisitEduDTO.getPageIndex());
        page.setRecordCountPerPage(ebcVisitEduDTO.getListRowSize());

        page.setPageSize(ebcVisitEduDTO.getPageRowSize());

        ebcVisitEduDTO.setFirstIndex(page.getFirstRecordIndex());
        ebcVisitEduDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        ebcVisitEduDTO.setTotalCount(ebcVisitEduMapper.selectVisitEduCnt(ebcVisitEduDTO));
        ebcVisitEduDTO.setList(ebcVisitEduMapper.selectVisitEduList(ebcVisitEduDTO));

        return ebcVisitEduDTO;
    }

    /**
     * 방문교육 목록을 조회한다.
     */
    public List<EBCVisitEduDTO> selectAppctnTypeList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {
        return ebcVisitEduMapper.selectAppctnTypeList(ebcVisitEduDTO);
    }

    /**
     * 방문교육 강사를 조회한다.
     */
    public List<EBCVisitEduDTO> selectIsttrList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {
        return ebcVisitEduMapper.selectIsttrList(ebcVisitEduDTO);
    }

    /**
     * 방문교육 교육실적 조회
     */
    public List<EBCVisitEduDTO> selectResultOpList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception{
        return ebcVisitEduMapper.selectResultOpList(ebcVisitEduDTO);
    }

    /**
     * 강사를 수정한다.
     */
    public EBCVisitEduDTO selectVisitEduDtl(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {
        return ebcVisitEduMapper.selectVisitEduDtl(ebcVisitEduDTO);
    }

    /**
     * 방문교육 상세를 수정한다.
     */
    public int updateVisitEdu(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {

        HashMap<String, Integer> itrdcFileSeqMap = cOFileService.setFileInfo(ebcVisitEduDTO.getItrdcFileList());
        HashMap<String, Integer> lctrFileMap = cOFileService.setFileInfo(ebcVisitEduDTO.getLctrFileList());
        HashMap<String, Integer> etcMatlsFileMap = cOFileService.setFileInfo(ebcVisitEduDTO.getEtcMatlsFileList());
        ebcVisitEduDTO.setItrdcFileSeq(itrdcFileSeqMap.get("itrdcFileSeq"));
        ebcVisitEduDTO.setLctrFileSeq(lctrFileMap.get("lctrFileSeq"));
        ebcVisitEduDTO.setEtcMatlsFileSeq(etcMatlsFileMap.get("etcMatlsFileSeq"));

        int respCnt = 0;
        List<EBCVisitEduDTO> resultOpList = ebcVisitEduDTO.getResultOpList();

        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        ebcVisitEduDTO.setRegId( cOUserDetailsDTO.getId() );
        ebcVisitEduDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
        ebcVisitEduDTO.setModId( cOUserDetailsDTO.getId() );
        ebcVisitEduDTO.setModIp( cOUserDetailsDTO.getLoginIp() );

        //신청자 정보 수정
        ebcVisitEduMapper.updateMemPartsSociety(ebcVisitEduDTO);

        //부품사 정보 수정
        MPEPartsCompanyDTO mpePartsCompanyDTO = new MPEPartsCompanyDTO();

        // dto 세팅
        mpePartsCompanyDTO.setBsnmNo(ebcVisitEduDTO.getAppctnBsnmNo());
        mpePartsCompanyDTO.setCtgryCd(ebcVisitEduDTO.getCtgryCd());
        mpePartsCompanyDTO.setSizeCd(ebcVisitEduDTO.getSizeCd());
        mpePartsCompanyDTO.setStbsmDt(ebcVisitEduDTO.getStbsmDt());
        mpePartsCompanyDTO.setTelNo(ebcVisitEduDTO.getTelNo());
        mpePartsCompanyDTO.setZipcode(ebcVisitEduDTO.getZipcode());
        mpePartsCompanyDTO.setBscAddr(ebcVisitEduDTO.getBscAddr());
        mpePartsCompanyDTO.setDtlAddr(ebcVisitEduDTO.getDtlAddr());
        mpePartsCompanyDTO.setSlsPmt(ebcVisitEduDTO.getSlsPmt());
        mpePartsCompanyDTO.setSlsYear(ebcVisitEduDTO.getSlsYear());
        mpePartsCompanyDTO.setMpleCnt(ebcVisitEduDTO.getMpleCnt());
        mpePartsCompanyDTO.setMjrPrdct1(ebcVisitEduDTO.getMjrPrdct1());
        mpePartsCompanyDTO.setMjrPrdct2(ebcVisitEduDTO.getMjrPrdct2());
        mpePartsCompanyDTO.setMjrPrdct3(ebcVisitEduDTO.getMjrPrdct3());

        if(ebcVisitEduDTO.getCtgryCd().equals("COMPANY01001")) {
            mpePartsCompanyDTO.setQlty5StarCd(ebcVisitEduDTO.getQlty5StarCd());
            mpePartsCompanyDTO.setQlty5StarYear(ebcVisitEduDTO.getQlty5StarYear());
            mpePartsCompanyDTO.setPay5StarCd(ebcVisitEduDTO.getPay5StarCd());
            mpePartsCompanyDTO.setPay5StarYear(Integer.valueOf(ebcVisitEduDTO.getPay5StarYear()));
            mpePartsCompanyDTO.setTchlg5StarCd(ebcVisitEduDTO.getTchlg5StarCd());
            mpePartsCompanyDTO.setTchlg5StarYear(ebcVisitEduDTO.getTchlg5StarYear());

        } else if(ebcVisitEduDTO.getCtgryCd().equals("COMPANY01002")) {
            mpePartsCompanyDTO.setSqInfoList1(ebcVisitEduDTO.getSqInfoList1());
            mpePartsCompanyDTO.setSqInfoList2(ebcVisitEduDTO.getSqInfoList2());
            mpePartsCompanyDTO.setSqInfoList3(ebcVisitEduDTO.getSqInfoList3());
        }

        mpePartsCompanyDTO.setRegId(cOUserDetailsDTO.getId());
        mpePartsCompanyDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        mpePartsCompanyService.updatePartsCompany(mpePartsCompanyDTO);

        //강사 수정
        ebcVisitEduMapper.deleteIsttrRel(ebcVisitEduDTO);
        ebcVisitEduMapper.insertIsttrRel(ebcVisitEduDTO);

        //신청내용 수정
        ebcVisitEduMapper.updateEdctnVst(ebcVisitEduDTO);
        ebcVisitEduMapper.updateEdctnVstRslt(ebcVisitEduDTO);

        //신청분야 상세 수정
        //ebcVisitEduMapper.deleteAppctnType(ebcVisitEduDTO);
        //ebcVisitEduMapper.insertAppctnType(ebcVisitEduDTO);

        //교육실적 수정
        ebcVisitEduMapper.deleteResultOp(ebcVisitEduDTO);

        for (EBCVisitEduDTO dto : resultOpList) {

            ebcVisitEduDTO.setRsltOptnSeq(edctnVstOptnSeqIdgen.getNextIntegerId());
            ebcVisitEduDTO.setVstSeq(dto.getVstSeq());
            ebcVisitEduDTO.setVstSeq(dto.getVstSeq());
            ebcVisitEduDTO.setMemSeq(dto.getMemSeq());
            ebcVisitEduDTO.setVstRsltSeq(dto.getVstRsltSeq());
            ebcVisitEduDTO.setRsltTypeCd(dto.getRsltTypeCd());
            ebcVisitEduDTO.setOptnCd(dto.getOptnCd());
            ebcVisitEduDTO.setRsltVal(dto.getRsltVal());

            ebcVisitEduMapper.insertResultOp(ebcVisitEduDTO);
        }

        return respCnt;
    }
}
