package com.kap.service.impl.bd;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COMailDTO;
import com.kap.core.dto.COMessageReceiverDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.bd.bdd.BDDNewsletterDTO;
import com.kap.core.dto.mp.mph.MPHNewsLetterDTO;
import com.kap.service.*;
import com.kap.service.dao.bd.BDDNewsletterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * BDDNewsletterService 서비스 Impl
 *
 * @author 장두석
 * @since 2023.11.22
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.22  장두석         최초 생성
 *   2024.06.12  구은희         사용자 뉴스레터 리스트 페이징 처리
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BDDNewsletterServiceImpl implements BDDNewsletterService {

    /** Mapper **/
    private final BDDNewsletterMapper bDDNewsletterMapper;

    /** Service **/
    private final COFileService cOFileService;

    /** Sequence **/
    private final EgovIdGnrService newsletterSeqIdgen;

    /** 뉴스레터 구독 신청 관리 서비스 **/
    public final MPHNewsLetterService mphNewsLetterService;

    // 이메일 서비스
    private final COMessageService cOMessageService;

    @Value("${app.site.name}")
    private String siteName;

    //사용자 http 경로
    @Value("${app.user-domain}")
    private String appUserDomain;

    /**
     * 뉴스레터 조회
     */
    public BDDNewsletterDTO selectNewsletterList(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pBDDNewsletterDTO.getPageIndex());
        int recordCountPerPage = pBDDNewsletterDTO.getSiteGubun().equals("front") ? 9 : 10;
        page.setRecordCountPerPage(recordCountPerPage);
        page.setPageSize(pBDDNewsletterDTO.getPageRowSize());

        pBDDNewsletterDTO.setFirstIndex(page.getFirstRecordIndex());
        pBDDNewsletterDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pBDDNewsletterDTO.setTotalCount(bDDNewsletterMapper.getNewsletterListTotCnt(pBDDNewsletterDTO));
        pBDDNewsletterDTO.setList(bDDNewsletterMapper.selectNewsletterList(pBDDNewsletterDTO));
        return pBDDNewsletterDTO;
    }

    /**
     * 통합검색 뉴스레터 탭 조회
     */
    public BDDNewsletterDTO selectNewsletterTabList(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pBDDNewsletterDTO.getPageIndex());
        page.setRecordCountPerPage(9);
        page.setPageSize(pBDDNewsletterDTO.getPageRowSize());

        // 1페이지인 경우에는 LIMIT 0부터 SELECT
        if(pBDDNewsletterDTO.getPageIndex() == 1) {
            pBDDNewsletterDTO.setFirstIndex(page.getFirstRecordIndex());
        } else {
            pBDDNewsletterDTO.setFirstIndex((pBDDNewsletterDTO.getPageIndex() - 1) * pBDDNewsletterDTO.getPageRowSize());
        }

        pBDDNewsletterDTO.setRecordCountPerPage(9);
        pBDDNewsletterDTO.setTotalCount(bDDNewsletterMapper.getNewsletterListTotCnt(pBDDNewsletterDTO));
        pBDDNewsletterDTO.setList(bDDNewsletterMapper.selectNewsletterList(pBDDNewsletterDTO));
        return pBDDNewsletterDTO;

    }

    /**
     * 통합검색 뉴스레터 조회
     */
    public BDDNewsletterDTO selectNewsletterTotalList(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pBDDNewsletterDTO.getPageIndex());
        page.setRecordCountPerPage(pBDDNewsletterDTO.getListRowSize());

        page.setPageSize(pBDDNewsletterDTO.getPageRowSize());

        pBDDNewsletterDTO.setFirstIndex(page.getFirstRecordIndex());
        pBDDNewsletterDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pBDDNewsletterDTO.setTotalCount(bDDNewsletterMapper.getNewsletterListTotCnt(pBDDNewsletterDTO));
        pBDDNewsletterDTO.setList(bDDNewsletterMapper.selectNewsletterTotalList(pBDDNewsletterDTO));
        return pBDDNewsletterDTO;
    }

    /**
     * 뉴스레터 조회 갯수
     */
    public int selectNewsletterListCnt(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception {
        return bDDNewsletterMapper.getNewsletterListTotCnt(pBDDNewsletterDTO);
    }

    /**
     * 뉴스레터 상세
     */
    public BDDNewsletterDTO selectNewsletterDtl(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception{
        return bDDNewsletterMapper.selectNewsletterDtl(pBDDNewsletterDTO);
    }

    /**
     * 뉴스레터 등록
     */
    public int insertNewsletter(BDDNewsletterDTO pBDDNewsletterDTO, HttpServletRequest request) throws Exception {
        //작성자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pBDDNewsletterDTO.setRegId(cOUserDetailsDTO.getId());
        pBDDNewsletterDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(pBDDNewsletterDTO.getFileList() != null && !pBDDNewsletterDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pBDDNewsletterDTO.getFileList());
            pBDDNewsletterDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }
        if(pBDDNewsletterDTO.getPcThumbList() != null && !pBDDNewsletterDTO.getPcThumbList().isEmpty()) {
            HashMap<String, Integer> pcThnlSeqMap = cOFileService.setFileInfo(pBDDNewsletterDTO.getPcThumbList());
            pBDDNewsletterDTO.setPcThnlSeq(pcThnlSeqMap.get("pcThnlSeq"));
        }
        if(pBDDNewsletterDTO.getMoThumbList() != null && !pBDDNewsletterDTO.getMoThumbList().isEmpty()) {
            HashMap<String, Integer> mblThnlSeqMap = cOFileService.setFileInfo(pBDDNewsletterDTO.getMoThumbList());
            pBDDNewsletterDTO.setMblThnlSeq(mblThnlSeqMap.get("mblThnlSeq"));
        }

        //HTML 태그 처리
        String cntn = pBDDNewsletterDTO.getCntn();
        pBDDNewsletterDTO.setCntn(COWebUtil.clearXSSMinimum(cntn));

        pBDDNewsletterDTO.setNwsltSeq(newsletterSeqIdgen.getNextIntegerId());

        int respCnt = 0;

        // 뉴스레터 게시물 노출 처리한 경우만 이메일 발송
        if(pBDDNewsletterDTO.getExpsYn().equals("Y")) {

            String regIp = CONetworkUtil.getMyIPaddress(request);
            pBDDNewsletterDTO.setRegIp(regIp);

            MPHNewsLetterDTO dto = new MPHNewsLetterDTO();
            // 뉴스레터 신청자 목록 조회
            MPHNewsLetterDTO newMphNewsLetterDTO = mphNewsLetterService.selectNewsLetterList(dto);

            String infoFormCntn = pBDDNewsletterDTO.getCntn();
            infoFormCntn = COWebUtil.clearXSSMinimum(infoFormCntn);

            // 뉴스레터 구독 신청한 회원에게 이메일 발송
            for (MPHNewsLetterDTO userEmailDTO : newMphNewsLetterDTO.getList()) {
                System.err.println(userEmailDTO.getEmail());
                /* 메일 처리 */
                COMailDTO cOMailDTO = new COMailDTO();
                cOMailDTO.setSubject("["+siteName+"] " + pBDDNewsletterDTO.getTitl());
                cOMailDTO.setEditorContents(infoFormCntn);
                //수신자 정보
                COMessageReceiverDTO userReceiverDto = new COMessageReceiverDTO();

                userReceiverDto.setEmail(userEmailDTO.getEmail());
                //치환문자1
                userReceiverDto.setNote1(pBDDNewsletterDTO.getTitl());
                //수신자 정보 등록
                cOMailDTO.getReceiver().add(userReceiverDto);
                //메일 발송
                cOMessageService.sendMail(cOMailDTO, "BDDNewsletterContentsMail.html");
            }
        }
        respCnt = bDDNewsletterMapper.insertNewsletter(pBDDNewsletterDTO);
        pBDDNewsletterDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 뉴스레터 수정
     */
    public int updateNewsletter(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception{
        //수정자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pBDDNewsletterDTO.setModId(cOUserDetailsDTO.getId());
        pBDDNewsletterDTO.setModIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(pBDDNewsletterDTO.getFileList() != null && !pBDDNewsletterDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pBDDNewsletterDTO.getFileList());
            pBDDNewsletterDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }
        if(pBDDNewsletterDTO.getPcThumbList() != null && !pBDDNewsletterDTO.getPcThumbList().isEmpty()) {
            HashMap<String, Integer> pcThnlSeqMap = cOFileService.setFileInfo(pBDDNewsletterDTO.getPcThumbList());
            pBDDNewsletterDTO.setPcThnlSeq(pcThnlSeqMap.get("pcThnlSeq"));
        }
        if(pBDDNewsletterDTO.getMoThumbList() != null && !pBDDNewsletterDTO.getMoThumbList().isEmpty()) {
            HashMap<String, Integer> mblThnlSeqMap = cOFileService.setFileInfo(pBDDNewsletterDTO.getMoThumbList());
            pBDDNewsletterDTO.setMblThnlSeq(mblThnlSeqMap.get("mblThnlSeq"));
        }

        //HTML 태그 처리
        String cntn = pBDDNewsletterDTO.getCntn();
        pBDDNewsletterDTO.setCntn(COWebUtil.clearXSSMinimum(cntn));

        return bDDNewsletterMapper.updateNewsletter(pBDDNewsletterDTO);
    }

    /**
     * 뉴스레터 삭제
     */
    public int deleteNewsletter(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception{

        return bDDNewsletterMapper.deleteNewsletter(pBDDNewsletterDTO);
    }

    /**
     * 뉴스레터 첨부파일 목록 조회
     */
    public BDDNewsletterDTO selectNewsletterFileList(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception {
        pBDDNewsletterDTO.setList(bDDNewsletterMapper.selectNewsletterFileList(pBDDNewsletterDTO));
        return pBDDNewsletterDTO;
    }

    /**
     * 뉴스레터 조회수 증가
     */
    public int updateNewsletterReadCnt(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception{
        return bDDNewsletterMapper.updateNewsletterReadCnt(pBDDNewsletterDTO);
    }

    /**
     * 뉴스레터 이전, 다음 글 SEQ 조회
     */
    public BDDNewsletterDTO selectNextAndPrevSeqVal(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception{
        return bDDNewsletterMapper.selectNextAndPrevSeqVal(pBDDNewsletterDTO);
    }


    
}
