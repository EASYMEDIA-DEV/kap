package com.kap.service.impl.eb;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COUserCmpnDto;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.MPBEduDto;
import com.kap.core.dto.eb.ebb.EBBEpisdSqCertDTO;
import com.kap.core.dto.eb.ebd.EBDEdctnEdisdDTO;
import com.kap.core.dto.eb.ebd.EBDPrePrcsDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiListDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import com.kap.core.dto.eb.ebg.EBGExamAppctnMstDTO;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COCommService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBDSqCertiReqService;
import com.kap.service.dao.eb.EBDSqCertiReqMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final COFileUtil cOFileUtil;
    // 파일 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;
    //파일 업로드 사이즈
    @Value("${app.file.max-size}")
    private int atchUploadMaxSize;
    /* SQ 평가원 시퀀스 */
    private final EgovIdGnrService sqCertiApplyIdgen;

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


    /**
     * 만료일시 기준 몇개월전 알림 발송
     */
    public List<EBDSqCertiListDTO> getSqValidEndEmailList(int validMonth) throws Exception{
        List<EBDSqCertiListDTO> EBDSqCertiListDTOList = eBDSqCertiReqMapper.getSqValidEndEmailList(validMonth);
        return EBDSqCertiListDTOList;
    }

    /**
     * 사용자 MY-PAGE 참여한 교육중 자격증연계코드의 값이 LCNS_CNNCT02이고 수료 완료인 경우
     */
    public EBDSqCertiSearchDTO getEducationCompleteList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(eBDSqCertiSearchDTO.getPageIndex());
        page.setRecordCountPerPage(eBDSqCertiSearchDTO.getListRowSize());
        page.setPageSize(eBDSqCertiSearchDTO.getPageRowSize());
        eBDSqCertiSearchDTO.setFirstIndex( page.getFirstRecordIndex() );
        eBDSqCertiSearchDTO.setRecordCountPerPage( page.getRecordCountPerPage() );
        eBDSqCertiSearchDTO.setTotalCount( eBDSqCertiReqMapper.selectEducationCompleteListCnt(eBDSqCertiSearchDTO));
        eBDSqCertiSearchDTO.setEducationList( eBDSqCertiReqMapper.selectEducationCompleteList(eBDSqCertiSearchDTO) );
        return eBDSqCertiSearchDTO;
    }

    /**
     * 사용자 MY-PAGE 참여한 교육중 자격증연계코드의 값이 LCNS_CNNCT02이고 보수 과목
     */
    public EBDSqCertiSearchDTO getEducationRepairList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(eBDSqCertiSearchDTO.getPageIndex());
        page.setRecordCountPerPage(eBDSqCertiSearchDTO.getListRowSize());
        page.setPageSize(eBDSqCertiSearchDTO.getPageRowSize());
        eBDSqCertiSearchDTO.setFirstIndex( page.getFirstRecordIndex() );
        eBDSqCertiSearchDTO.setRecordCountPerPage( page.getRecordCountPerPage() );
        eBDSqCertiSearchDTO.setRepairTotalCount( eBDSqCertiReqMapper.selectEducationCompleteListCnt(eBDSqCertiSearchDTO));
        eBDSqCertiSearchDTO.setEducationRepairList( eBDSqCertiReqMapper.selectEducationCompleteList(eBDSqCertiSearchDTO) );
        return eBDSqCertiSearchDTO;
    }

    /**
     * 사용자 MY-PAGE 참여한 교육중 자격증연계코드의 값이 LCNS_CNNCT02이고 수료 완료인 갯수
     */
    public int selectEducationCompleteListCnt(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception{
        return eBDSqCertiReqMapper.selectEducationCompleteListCnt(eBDSqCertiSearchDTO);
    }

    /**
     * SQ 평가원 자격증 신청 조건(자격증 연계를 수료하였고 평가원을 신청하지 않아야함)
     */
    public int getPosibleSqCertiCnt(EBDSqCertiSearchDTO eBDSqCertiSearchDTO) throws Exception{
        return eBDSqCertiReqMapper.getPosibleSqCertiCnt(eBDSqCertiSearchDTO);
    }

    /**
     * SQ 평가원 자격증 신청
     */
    public int insert(MultipartHttpServletRequest multiRequest) throws Exception{
        int respCnt = 0;
        Map<String, MultipartFile> files = multiRequest.getFileMap();
        COUserDetailsDTO coUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        if (!files.isEmpty())
        {
            List<COFileDTO>  fileResult = cOFileUtil.parseFileInf(files, "", 0, "", "idntfnPhotoFileSeq", atchUploadMaxSize);
            if(fileResult.size()>0){
                EBGExamAppctnMstDTO eBGExamAppctnMstDTO = new EBGExamAppctnMstDTO();
                eBGExamAppctnMstDTO.setExamAppctnSeq(sqCertiApplyIdgen.getNextIntegerId());
                eBGExamAppctnMstDTO.setIdntfnPhotoFileSeq(cOFileService.insertFiles(fileResult));;
                //회원번호
                eBGExamAppctnMstDTO.setMemSeq(coUserDetailsDTO.getSeq());
                eBGExamAppctnMstDTO.setBsnmNo(coUserDetailsDTO.getBsnmNo());
                //일반
                eBGExamAppctnMstDTO.setExamCd("EBD_SQ_TP_D");
                //발급 대기
                eBGExamAppctnMstDTO.setIssueCd("EBD_SQ_R");
                eBGExamAppctnMstDTO.setRegId(coUserDetailsDTO.getId());
                eBGExamAppctnMstDTO.setRegIp(CONetworkUtil.getMyIPaddress(multiRequest));
                respCnt = eBDSqCertiReqMapper.insert(eBGExamAppctnMstDTO);

                EBDSqCertiSearchDTO eBDSqCertiSearchDTO= new EBDSqCertiSearchDTO();
                eBDSqCertiSearchDTO.setLcnsCnnctCd("LCNS_CNNCT02");
                eBDSqCertiSearchDTO.setMemSeq(coUserDetailsDTO.getSeq());
                List<EBBEpisdSqCertDTO> list = eBDSqCertiReqMapper.getEducationCompleteLcnsCnnct(eBDSqCertiSearchDTO);
                eBGExamAppctnMstDTO.setPtcptSeq( list.get(0).getPtcptSeq() );
                eBDSqCertiReqMapper.insertPtcptMst(eBGExamAppctnMstDTO);
            }
        }
        return respCnt;
    }
}
