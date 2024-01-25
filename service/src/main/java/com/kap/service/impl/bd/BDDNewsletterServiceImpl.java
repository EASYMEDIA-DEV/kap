package com.kap.service.impl.bd;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.bd.bdd.BDDNewsletterDTO;
import com.kap.service.BDDNewsletterService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.bd.BDDNewsletterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

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

    /**
     * 뉴스레터 조회
     */
    public BDDNewsletterDTO selectNewsletterList(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pBDDNewsletterDTO.getPageIndex());
        page.setRecordCountPerPage(pBDDNewsletterDTO.getListRowSize());

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
        pBDDNewsletterDTO.setTotalCount(bDDNewsletterMapper.getNewsletterListTotCnt(pBDDNewsletterDTO));

        int recordCountPerPage = (pBDDNewsletterDTO.getPageIndex() * pBDDNewsletterDTO.getPageRowSize() >= pBDDNewsletterDTO.getTotalCount()) ? pBDDNewsletterDTO.getTotalCount() : pBDDNewsletterDTO.getPageIndex() * pBDDNewsletterDTO.getPageRowSize();
        pBDDNewsletterDTO.setRecordCountPerPage(recordCountPerPage);
        pBDDNewsletterDTO.setList(bDDNewsletterMapper.selectNewsletterList(pBDDNewsletterDTO));
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
    public int insertNewsletter(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception {
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

        return bDDNewsletterMapper.insertNewsletter(pBDDNewsletterDTO);
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
