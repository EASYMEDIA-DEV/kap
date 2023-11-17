package com.kap.service.impl.eb;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserCmpnDto;
import com.kap.core.dto.eb.ebd.EBDEdctnEdisdDTO;
import com.kap.core.dto.eb.ebd.EBDPrePrcsDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import com.kap.core.dto.eb.ebg.EBGExamAppctnMstDTO;
import com.kap.service.COCommService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBDSqCertiReqService;
import com.kap.service.dao.eb.EBDSqCertiReqMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * SQ평가원 자격증 신청관리
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  김학규         최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EBDSqCertiReqServiceImpl implements EBDSqCertiReqService {
    //Mapper
    private final EBDSqCertiReqMapper eBDSqCertiReqMapper;

    /** 공통 서비스 **/
    private final COCommService cOCommService;

    /* 파일 서비스 */
    private final COFileService cOFileService;

    /**
     * 리스트 조회
     */
    public EBDSqCertiSearchDTO selectList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception{
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(eBDSqCertiSearchDTO.getPageIndex());
        page.setRecordCountPerPage(eBDSqCertiSearchDTO.getListRowSize());
        page.setPageSize(eBDSqCertiSearchDTO.getPageRowSize());
        eBDSqCertiSearchDTO.setFirstIndex( page.getFirstRecordIndex() );
        eBDSqCertiSearchDTO.setRecordCountPerPage( page.getRecordCountPerPage() );
        eBDSqCertiSearchDTO.setTotalCount( eBDSqCertiReqMapper.getListCnt(eBDSqCertiSearchDTO));
        eBDSqCertiSearchDTO.setList( eBDSqCertiReqMapper.selectList(eBDSqCertiSearchDTO) );
        return eBDSqCertiSearchDTO;
    }

    /**
     * 상세
     */
    public EBDEdctnEdisdDTO selectView(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception{
        EBDEdctnEdisdDTO eBDEdctnEdisdDTO = eBDSqCertiReqMapper.selectView(eBDSqCertiSearchDTO);
        eBDSqCertiSearchDTO.setMemSeq( eBDEdctnEdisdDTO.getMemSeq() );
        return eBDEdctnEdisdDTO;
    }

    /**
     * 자격증 상세
     */
    public EBGExamAppctnMstDTO selectExamAppctnMst(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception{
        return eBDSqCertiReqMapper.selectExamAppctnMst(eBDSqCertiSearchDTO);
    }

    /**
     * 선수과목 수료 목록 조회
     */
    public List<EBDPrePrcsDTO> getPrePrcsList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception
    {
        return eBDSqCertiReqMapper.getPrePrcsList(eBDSqCertiSearchDTO);
    }

    /**
     * 수정
     */
    public int update(COUserCmpnDto cOUserCmpnDto, EBDEdctnEdisdDTO eBDEdctnEdisdDTO, HttpServletRequest request) throws Exception{
        //회원 회사 정보 변경
        int respCnt = cOCommService.setMemCmpnDtl(cOUserCmpnDto, request);
        //평가 신청 정보 변경
        if(!"EBD_SQ_R".equals( eBDEdctnEdisdDTO.getIssueCd() )){
            //발급 대기일경우만 수정
            eBDEdctnEdisdDTO.setModId(COUserDetailsHelperService.getAuthenticatedUser().getId());
            eBDEdctnEdisdDTO.setModIp(CONetworkUtil.getMyIPaddress(request));
            eBDSqCertiReqMapper.updateIssue( eBDEdctnEdisdDTO );
        }
        return respCnt;
    }

    /**
     * 자격증 정보 수정
     */
    public int updateConfirmInfo(COUserCmpnDto cOUserCmpnDto, EBGExamAppctnMstDTO eBGExamAppctnMstDTO, HttpServletRequest request) throws Exception{
        //회원 회사 정보 변경
        int respCnt = cOCommService.setMemCmpnDtl(cOUserCmpnDto, request);
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBGExamAppctnMstDTO.getFileList());
        eBGExamAppctnMstDTO.setIdntfnPhotoFileSeq( fileSeqMap.get("idntfnPhotoFileSeq") );
        eBGExamAppctnMstDTO.setModId(COUserDetailsHelperService.getAuthenticatedUser().getId());
        eBGExamAppctnMstDTO.setModIp(CONetworkUtil.getMyIPaddress(request));
        eBDSqCertiReqMapper.updateConfirmInfo( eBGExamAppctnMstDTO );
        return respCnt;
    }
}
