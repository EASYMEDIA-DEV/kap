package com.kap.service.impl.bd;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.bd.bda.BDANoticeDTO;
import com.kap.service.BDANoticeService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.bd.BDANoticeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * BDANoticeService 서비스 Impl
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
 *   2024.01.05  이옥정         사용자에서 공지사항 리스트 가져올때 건수를 3건만 가져오므로 예외 처리 추가
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BDANoticeServiceImpl implements BDANoticeService {

    /** Mapper **/
    private final BDANoticeMapper bDANoticeMapper;

    /** Service **/
    private final COFileService cOFileService;

    /** Sequence **/
    private final EgovIdGnrService ntfySeqIdgen;

    /** Upload Path **/
    @Value("${app.file.upload-path}")
    private String fileUploadPath;

    /**
     * 공지사항 조회
     */
    public BDANoticeDTO selectNoticeList(BDANoticeDTO pBDANoticeDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pBDANoticeDTO.getPageIndex());
        page.setRecordCountPerPage(pBDANoticeDTO.getListRowSize());

        page.setPageSize(pBDANoticeDTO.getPageRowSize());

        pBDANoticeDTO.setFirstIndex(page.getFirstRecordIndex());

        // 사용자 메인 노출 갯수 조건문 추가
        if (pBDANoticeDTO.getMainYn().equals("Y")) {
            pBDANoticeDTO.setRecordCountPerPage(3);
        }else {
            pBDANoticeDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        }

        pBDANoticeDTO.setTotalCount(bDANoticeMapper.getNoticeListTotCnt(pBDANoticeDTO));
        pBDANoticeDTO.setList(bDANoticeMapper.selectNoticeList(pBDANoticeDTO));
        return pBDANoticeDTO;
    }

    /**
     * 통합검색 공지사항 탭 조회
     */
    public BDANoticeDTO selectNoticeTabList(BDANoticeDTO pBDANoticeDTO) throws Exception {
        pBDANoticeDTO.setTotalCount(bDANoticeMapper.getNoticeListTotCnt(pBDANoticeDTO));

        int recordCountPerPage = (pBDANoticeDTO.getPageIndex() * pBDANoticeDTO.getPageRowSize() >= pBDANoticeDTO.getTotalCount()) ? pBDANoticeDTO.getTotalCount() : pBDANoticeDTO.getPageIndex() * pBDANoticeDTO.getPageRowSize();
        pBDANoticeDTO.setRecordCountPerPage(recordCountPerPage);
        pBDANoticeDTO.setList(bDANoticeMapper.selectNoticeList(pBDANoticeDTO));
        return pBDANoticeDTO;
    }



    /**
     * 공지사항 조회 갯수(통합검색)
     */
    public int selectNoticeListCnt(BDANoticeDTO pBDANoticeDTO) throws Exception {
        return bDANoticeMapper.getNoticeListTotCnt(pBDANoticeDTO);
    }

    /**
     * 공지사항 상세
     */
    public BDANoticeDTO selectNoticeDtl(BDANoticeDTO pBDANoticeDTO) throws Exception{
        return bDANoticeMapper.selectNoticeDtl(pBDANoticeDTO);
    }

    /**
     * 공지사항 등록
     */
    public int insertNotice(BDANoticeDTO pBDANoticeDTO) throws Exception {
        //작성자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pBDANoticeDTO.setRegId(cOUserDetailsDTO.getId());
        pBDANoticeDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(pBDANoticeDTO.getFileList() != null && !pBDANoticeDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pBDANoticeDTO.getFileList());
            pBDANoticeDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }

        //HTML 태그 처리
        String cntn = pBDANoticeDTO.getCntn();
        pBDANoticeDTO.setCntn(COWebUtil.clearXSSMinimum(cntn));

        //중요 공지 여부
        if(!"Y".equals(pBDANoticeDTO.getNtfyYn())) {
            pBDANoticeDTO.setNtfyYn("N");
            pBDANoticeDTO.setExpsStrtDtm(null);
            pBDANoticeDTO.setExpsEndDtm(null);
        }
        //중요 공지 상시 여부
        if(!"Y".equals(pBDANoticeDTO.getOdtmYn())) {
            pBDANoticeDTO.setOdtmYn("N");
        }

        pBDANoticeDTO.setNtfySeq(ntfySeqIdgen.getNextIntegerId());

        return bDANoticeMapper.insertNotice(pBDANoticeDTO);
    }

    /**
     * 공지사항 수정
     */
    public int updateNotice(BDANoticeDTO pBDANoticeDTO) throws Exception{
        //수정자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pBDANoticeDTO.setModId(cOUserDetailsDTO.getId());
        pBDANoticeDTO.setModIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(pBDANoticeDTO.getFileList() != null && !pBDANoticeDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pBDANoticeDTO.getFileList());
            pBDANoticeDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }

        //HTML 태그 처리
        String cntn = pBDANoticeDTO.getCntn();
        pBDANoticeDTO.setCntn(COWebUtil.clearXSSMinimum(cntn));

        //중요 공지 여부
        if(!"Y".equals(pBDANoticeDTO.getNtfyYn())) {
            pBDANoticeDTO.setNtfyYn("N");
            pBDANoticeDTO.setExpsStrtDtm(null);
            pBDANoticeDTO.setExpsEndDtm(null);
        }
        //중요 공지 상시 여부
        if(!"Y".equals(pBDANoticeDTO.getOdtmYn())) {
            pBDANoticeDTO.setOdtmYn("N");
        }

        return bDANoticeMapper.updateNotice(pBDANoticeDTO);
    }

    /**
     * 공지사항 삭제
     */
    public int deleteNotice(BDANoticeDTO pBDANoticeDTO) throws Exception{

        return bDANoticeMapper.deleteNotice(pBDANoticeDTO);
    }

    /**
     * 공지사항 첨부파일 목록 조회
     */
    public BDANoticeDTO selectNoticeFileList(BDANoticeDTO pBDANoticeDTO) throws Exception {
        pBDANoticeDTO.setList(bDANoticeMapper.selectNoticeFileList(pBDANoticeDTO));
        return pBDANoticeDTO;
    }

    /**
     * 공지사항 조회수 증가
     */
    public int updateNoticeReadCnt(BDANoticeDTO pBDANoticeDTO) throws Exception{
        return bDANoticeMapper.updateNoticeReadCnt(pBDANoticeDTO);
    }

    /**
     * 공지사항 이전, 다음 글 SEQ 조회
     */
    public BDANoticeDTO selectNextAndPrevSeqVal(BDANoticeDTO pBDANoticeDTO) throws Exception{
        return bDANoticeMapper.selectNextAndPrevSeqVal(pBDANoticeDTO);
    }

    /**
     * 공지사항 조회
     */
    public BDANoticeDTO selectMainPostList(BDANoticeDTO pBDANoticeDTO) throws Exception {
        pBDANoticeDTO.setMainPostList(bDANoticeMapper.selectMainPostList(pBDANoticeDTO));
        return pBDANoticeDTO;
    }
}
