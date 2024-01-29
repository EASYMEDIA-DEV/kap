package com.kap.service.impl.bd;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.bd.bdb.BDBCompanyNewsDTO;
import com.kap.service.BDBCompanyNewsService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.bd.BDBCompanyNewsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * BDBCompanyNewsService 서비스 Impl
 *
 * @author 장두석
 * @since 2023.11.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.20  장두석         최초 생성
 *   2024.01.05  이옥정         사용자에서 재단소식 리스트 가져올때 건수를 1건으로 예외 처리 추가
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BDBCompanyNewsServiceImpl implements BDBCompanyNewsService {

    /** Mapper **/
    private final BDBCompanyNewsMapper bDBCompanyNewsMapper;

    /** Service **/
    private final COFileService cOFileService;

    /** Sequence **/
    private final EgovIdGnrService fndnNewsSeqIdgen;

    /** Upload Path **/
    @Value(" ${app.file.upload-path}")
    private String fileUploadPath;

    /**
     * 재단소식 조회
     */
    public BDBCompanyNewsDTO selectCompanyNewsList(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pBDBCompanyNewsDTO.getPageIndex());
        page.setRecordCountPerPage(pBDBCompanyNewsDTO.getListRowSize());

        page.setPageSize(pBDBCompanyNewsDTO.getPageRowSize());

        pBDBCompanyNewsDTO.setFirstIndex(page.getFirstRecordIndex());
        // 사용자 메인 노출 갯수 조건문 추가
        if(pBDBCompanyNewsDTO.getMainYn().equals("Y")) {
            pBDBCompanyNewsDTO.setRecordCountPerPage(1);
        }else{
            pBDBCompanyNewsDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        }

        pBDBCompanyNewsDTO.setTotalCount(bDBCompanyNewsMapper.getCompanyNewsListTotCnt(pBDBCompanyNewsDTO));
        pBDBCompanyNewsDTO.setList(bDBCompanyNewsMapper.selectCompanyNewsList(pBDBCompanyNewsDTO));
        return pBDBCompanyNewsDTO;
    }

    /**
     * 재단소식 조회 갯수(통합검색)
     */
    public int selectCompanyNewsListCnt(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception {
        return bDBCompanyNewsMapper.getCompanyNewsListTotCnt(pBDBCompanyNewsDTO);
    }

    /**
     * 재단소식 상세
     */
    public BDBCompanyNewsDTO selectCompanyNewsDtl(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception{
        return bDBCompanyNewsMapper.selectCompanyNewsDtl(pBDBCompanyNewsDTO);
    }

    /**
     * 재단소식 등록
     */
    public int insertCompanyNews(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception {
        //작성자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pBDBCompanyNewsDTO.setRegId(cOUserDetailsDTO.getId());
        pBDBCompanyNewsDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(pBDBCompanyNewsDTO.getFileList() != null && !pBDBCompanyNewsDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pBDBCompanyNewsDTO.getFileList());
            pBDBCompanyNewsDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }
        if(pBDBCompanyNewsDTO.getPcThumbList() != null && !pBDBCompanyNewsDTO.getPcThumbList().isEmpty()) {
            HashMap<String, Integer> pcThnlSeqMap = cOFileService.setFileInfo(pBDBCompanyNewsDTO.getPcThumbList());
            pBDBCompanyNewsDTO.setPcThnlSeq(pcThnlSeqMap.get("pcThnlSeq"));
        }
        if(pBDBCompanyNewsDTO.getMoThumbList() != null && !pBDBCompanyNewsDTO.getMoThumbList().isEmpty()) {
            HashMap<String, Integer> mblThnlSeqMap = cOFileService.setFileInfo(pBDBCompanyNewsDTO.getMoThumbList());
            pBDBCompanyNewsDTO.setMblThnlSeq(mblThnlSeqMap.get("mblThnlSeq"));
        }

        //HTML 태그 처리
        String cntn = pBDBCompanyNewsDTO.getCntn();
        pBDBCompanyNewsDTO.setCntn(COWebUtil.clearXSSMinimum(cntn));

        pBDBCompanyNewsDTO.setFndnNewsSeq(fndnNewsSeqIdgen.getNextIntegerId());

        return bDBCompanyNewsMapper.insertCompanyNews(pBDBCompanyNewsDTO);
    }

    /**
     * 재단소식 수정
     */
    public int updateCompanyNews(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception{
        //수정자
        COUserDetailsDTO cOUserDetailsDTO  = COUserDetailsHelperService.getAuthenticatedUser();
        pBDBCompanyNewsDTO.setModId(cOUserDetailsDTO.getId());
        pBDBCompanyNewsDTO.setModIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(pBDBCompanyNewsDTO.getFileList() != null && !pBDBCompanyNewsDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pBDBCompanyNewsDTO.getFileList());
            pBDBCompanyNewsDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }
        if(pBDBCompanyNewsDTO.getPcThumbList() != null && !pBDBCompanyNewsDTO.getPcThumbList().isEmpty()) {
            HashMap<String, Integer> pcThnlSeqMap = cOFileService.setFileInfo(pBDBCompanyNewsDTO.getPcThumbList());
            pBDBCompanyNewsDTO.setPcThnlSeq(pcThnlSeqMap.get("pcThnlSeq"));
        }
        if(pBDBCompanyNewsDTO.getMoThumbList() != null && !pBDBCompanyNewsDTO.getMoThumbList().isEmpty()) {
            HashMap<String, Integer> mblThnlSeqMap = cOFileService.setFileInfo(pBDBCompanyNewsDTO.getMoThumbList());
            pBDBCompanyNewsDTO.setMblThnlSeq(mblThnlSeqMap.get("mblThnlSeq"));
        }

        //HTML 태그 처리
        String cntn = pBDBCompanyNewsDTO.getCntn();
        pBDBCompanyNewsDTO.setCntn(COWebUtil.clearXSSMinimum(cntn));

        return bDBCompanyNewsMapper.updateCompanyNews(pBDBCompanyNewsDTO);
    }

    /**
     * 재단소식 삭제
     */
    public int deleteCompanyNews(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception{
        return bDBCompanyNewsMapper.deleteCompanyNews(pBDBCompanyNewsDTO);
    }

    /**
     * 재단소식 첨부파일 목록 조회
     */
    public BDBCompanyNewsDTO selectCompanyNewsFileList(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception {
        pBDBCompanyNewsDTO.setList(bDBCompanyNewsMapper.selectCompanyNewsFileList(pBDBCompanyNewsDTO));
        return pBDBCompanyNewsDTO;
    }

    /**
     * 재단소식 조회수 증가
     */
    public int updateCompanyNewsReadCnt(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception{
        return bDBCompanyNewsMapper.updateCompanyNewsReadCnt(pBDBCompanyNewsDTO);
    }

    /**
     * 재단소식 이전, 다음 글 SEQ 조회
     */
    public BDBCompanyNewsDTO selectNextAndPrevSeqVal(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception{
        return bDBCompanyNewsMapper.selectNextAndPrevSeqVal(pBDBCompanyNewsDTO);
    }

    /**
     * 통합검색 재단소식 탭 조회
     */
    public BDBCompanyNewsDTO selectCompanyNewsTabList(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception {

        pBDBCompanyNewsDTO.setTotalCount(bDBCompanyNewsMapper.getCompanyNewsListTotCnt(pBDBCompanyNewsDTO));

        int recordCountPerPage = (pBDBCompanyNewsDTO.getPageIndex() * pBDBCompanyNewsDTO.getPageRowSize() >= pBDBCompanyNewsDTO.getTotalCount()) ? pBDBCompanyNewsDTO.getTotalCount() : pBDBCompanyNewsDTO.getPageIndex() * pBDBCompanyNewsDTO.getPageRowSize();
        pBDBCompanyNewsDTO.setRecordCountPerPage(recordCountPerPage);
        pBDBCompanyNewsDTO.setList(bDBCompanyNewsMapper.selectCompanyNewsList(pBDBCompanyNewsDTO));
        return pBDBCompanyNewsDTO;
    }
}
