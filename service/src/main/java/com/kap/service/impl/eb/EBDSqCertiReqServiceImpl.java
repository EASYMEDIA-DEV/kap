package com.kap.service.impl.eb;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserCmpnDto;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.eb.eba.EBACouseDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdSqCertDTO;
import com.kap.core.dto.eb.ebd.EBDEdctnEdisdDTO;
import com.kap.core.dto.eb.ebd.EBDPrePrcsDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiListDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import com.kap.core.dto.eb.ebg.EBGExamAppctnMstDTO;
import com.kap.core.utility.COFileUtil;
import com.kap.service.*;
import com.kap.service.dao.eb.EBDSqCertiReqMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
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
    private final COFileUtil cOFileUtil;
    // 파일 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;
    //파일 업로드 사이즈
    @Value("${app.file.max-size}")
    private int atchUploadMaxSize;
    /* SQ 평가원 시퀀스 */
    private final EgovIdGnrService sqCertiApplyIdgen;

    /** 서비스 **/
    public final EBACouseService eBACouseService;

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
        EBGExamAppctnMstDTO eBGExamAppctnMstDTO = eBDSqCertiReqMapper.selectExamAppctnMst(eBDSqCertiSearchDTO);
        if(eBGExamAppctnMstDTO != null) {
            eBGExamAppctnMstDTO.setFileList(cOFileService.getFileInfs(eBGExamAppctnMstDTO.getIdntfnPhotoFileSeq()));
        }
        return eBGExamAppctnMstDTO;
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

            if(!"".equals(eBDEdctnEdisdDTO.getOrgIssueCd()) && "EBD_SQ_I".equals(eBDEdctnEdisdDTO.getOrgIssueCd())){

                //발급인데 수정이 일어났을경우 SQ평가원 구분, 자격증 번호를 업데이트 한다
                if(!eBDEdctnEdisdDTO.getOrgExamCd().equals(eBDEdctnEdisdDTO.getExamCd()) || !eBDEdctnEdisdDTO.getOrgJdgmtNo().equals(eBDEdctnEdisdDTO.getJdgmtNo())){
                    //SQ평가원 구분값과 자격증 번호의 변경이 있을때만 업데이트
                    eBDEdctnEdisdDTO.setModId(COUserDetailsHelperService.getAuthenticatedUser().getId());
                    eBDEdctnEdisdDTO.setModIp(CONetworkUtil.getMyIPaddress(request));
                    eBDSqCertiReqMapper.updateSqExamCdJdgmtNo( eBDEdctnEdisdDTO );
                }

            }else{
                //발급 대기일경우만 수정
                eBDEdctnEdisdDTO.setModId(COUserDetailsHelperService.getAuthenticatedUser().getId());
                eBDEdctnEdisdDTO.setModIp(CONetworkUtil.getMyIPaddress(request));
                eBDSqCertiReqMapper.updateIssue( eBDEdctnEdisdDTO );
            }



        }
        return respCnt;
    }

    /**
     * 자격증 정보 수정
     */
    public int updateConfirmInfo(COUserCmpnDto cOUserCmpnDto, EBGExamAppctnMstDTO eBGExamAppctnMstDTO, HttpServletRequest request) throws Exception{
        //회원 회사 정보 변경
        cOCommService.setMemCmpnDtl(cOUserCmpnDto, request);
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBGExamAppctnMstDTO.getFileList());
        eBGExamAppctnMstDTO.setIdntfnPhotoFileSeq( fileSeqMap.get("idntfnPhotoFileSeq") );
        eBGExamAppctnMstDTO.setModId(COUserDetailsHelperService.getAuthenticatedUser().getId());
        eBGExamAppctnMstDTO.setModIp(CONetworkUtil.getMyIPaddress(request));
        int respCnt = eBDSqCertiReqMapper.updateConfirmInfo( eBGExamAppctnMstDTO );
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
    public int insert(EBGExamAppctnMstDTO eBGExamAppctnMstDTO, HttpServletRequest request) throws Exception{
        int respCnt = 0;
        COUserDetailsDTO coUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        eBGExamAppctnMstDTO.setExamAppctnSeq(sqCertiApplyIdgen.getNextIntegerId());
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBGExamAppctnMstDTO.getFileList());
        eBGExamAppctnMstDTO.setIdntfnPhotoFileSeq(fileSeqMap.get("idntfnPhotoFileSeq"));
        //회원번호
        eBGExamAppctnMstDTO.setMemSeq(coUserDetailsDTO.getSeq());
        eBGExamAppctnMstDTO.setBsnmNo(coUserDetailsDTO.getBsnmNo());
        //일반
        eBGExamAppctnMstDTO.setExamCd("EBD_SQ_TP_D");
        //발급 대기
        eBGExamAppctnMstDTO.setIssueCd("EBD_SQ_R");
        eBGExamAppctnMstDTO.setRegId(coUserDetailsDTO.getId());
        eBGExamAppctnMstDTO.setRegIp(CONetworkUtil.getMyIPaddress(request));
        respCnt = eBDSqCertiReqMapper.insert(eBGExamAppctnMstDTO);

        EBDSqCertiSearchDTO eBDSqCertiSearchDTO= new EBDSqCertiSearchDTO();
        eBDSqCertiSearchDTO.setLcnsCnnctCd("LCNS_CNNCT02");
        eBDSqCertiSearchDTO.setMemSeq(coUserDetailsDTO.getSeq());
        List<EBBEpisdSqCertDTO> list = eBDSqCertiReqMapper.getEducationCompleteLcnsCnnct(eBDSqCertiSearchDTO);
        eBGExamAppctnMstDTO.setPtcptSeq( list.get(0).getPtcptSeq() );
        eBDSqCertiReqMapper.insertPtcptMst(eBGExamAppctnMstDTO);
        return respCnt;
    }

    /**
     * SQ 평가원 자격증 변경
     */
    public int updateCerti(EBGExamAppctnMstDTO eBGExamAppctnMstDTO, HttpServletRequest request) throws Exception{
        int respCnt = 0;
        //증명사진의 값이 꼭 있어야 변경이 가능
        if(eBGExamAppctnMstDTO.getFileList() != null && eBGExamAppctnMstDTO.getFileList().size() > 0){
            COUserDetailsDTO coUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            EBDSqCertiSearchDTO eBDSqCertiSearchDTO = new EBDSqCertiSearchDTO();
            //자격증 조회(반려일경우는 발급대기로 변경)
            eBDSqCertiSearchDTO.setMemSeq(coUserDetailsDTO.getSeq());
            EBGExamAppctnMstDTO rtnAppctnMstDTO = this.selectExamAppctnMst(eBDSqCertiSearchDTO);
            //파일 삭제
            cOFileService.deleteFile(eBGExamAppctnMstDTO.getFileList());
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBGExamAppctnMstDTO.getFileList());
            rtnAppctnMstDTO.setIdntfnPhotoFileSeq(fileSeqMap.get("idntfnPhotoFileSeq"));
            //발급 대기
            rtnAppctnMstDTO.setRegIp(CONetworkUtil.getMyIPaddress(request));
            respCnt = eBDSqCertiReqMapper.updateCerti(rtnAppctnMstDTO);
        }
        return respCnt;
    }

    /**
     * SQ 평가원 자격증 갱신
     * 교육 과정 마스터
     */
    public int updateCertiValid(int edctnSeq) throws Exception{
        int respCnt = 0;
        EBDSqCertiSearchDTO eBDSqCertiSearchDTO = new EBDSqCertiSearchDTO();
        eBDSqCertiSearchDTO.setMemSeq(COUserDetailsHelperService.getAuthenticatedUser().getSeq());
        EBGExamAppctnMstDTO eBGExamAppctnMstDTO = eBDSqCertiReqMapper.selectExamAppctnMst(eBDSqCertiSearchDTO);
        if(eBGExamAppctnMstDTO != null) {
            EBACouseDTO eBACouseDTO = new EBACouseDTO();
            eBACouseDTO.setDetailsKey(String.valueOf(edctnSeq));
            HashMap<String, Object> rtnMap = eBACouseService.selectCouseDtl(eBACouseDTO);
            EBACouseDTO ebaDto = (EBACouseDTO) rtnMap.get("rtnData");
            if (ebaDto == null) {
                throw new Exception("NO DATA");
            }
            log.error("ebaDto : {}", ebaDto);
            if ("LCNS_CNNCT03".equals(ebaDto.getLcnsCnnctCd())) {
                //자격증 갱신
                eBDSqCertiReqMapper.updateCertiRenewal(eBGExamAppctnMstDTO);
            }
        }
        return respCnt;
    }
}
