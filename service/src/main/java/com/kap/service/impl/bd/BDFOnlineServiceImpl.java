package com.kap.service.impl.bd;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.bd.bdf.BDFOnlineDTO;
import com.kap.service.BDFOnlineService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.bd.BDFOnlineMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * BDFOnlineService 서비스 Impl
 *
 * @author 오병호
 * @since 2024.02.20
 * @version 1
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2024.02.20  오병호         최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BDFOnlineServiceImpl implements BDFOnlineService {

    /** Mapper **/
    private final BDFOnlineMapper bDFOnlineMapper;

    /** Service **/
    private final COFileService cOFileService;

    /** Sequence **/
    private final EgovIdGnrService mnlSeqIdgen;

    /** Upload Path **/
    @Value(" ${app.file.upload-path}")
    private String fileUploadPath;

    /**
     * Online 조회
     */
    public BDFOnlineDTO selectOnlineList(BDFOnlineDTO bDFOnlineDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(bDFOnlineDTO.getPageIndex());
        page.setRecordCountPerPage(bDFOnlineDTO.getListRowSize());

        page.setPageSize(bDFOnlineDTO.getPageRowSize());

        bDFOnlineDTO.setFirstIndex(page.getFirstRecordIndex());

        // 사용자 메인 노출 갯수 조건문 추가
        if(bDFOnlineDTO.getMainYn().equals("Y")) {
            bDFOnlineDTO.setRecordCountPerPage(3);
        }else{
            bDFOnlineDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        }

        bDFOnlineDTO.setTotalCount(bDFOnlineMapper.getOnlineListTotCnt(bDFOnlineDTO));
        bDFOnlineDTO.setList(bDFOnlineMapper.selectOnlineList(bDFOnlineDTO));
        return bDFOnlineDTO;
    }

    /**
     * Online 상세
     */
    public BDFOnlineDTO selectOnlineDtl(BDFOnlineDTO bDFOnlineDTO) throws Exception{
        return bDFOnlineMapper.selectOnlineDtl(bDFOnlineDTO);
    }

    /**
     * Online 등록
     */
    public int insertOnline(BDFOnlineDTO bDFOnlineDTO) throws Exception {
        //작성자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        bDFOnlineDTO.setRegId(cOUserDetailsDTO.getId());
        bDFOnlineDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(bDFOnlineDTO.getFileList() != null && !bDFOnlineDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(bDFOnlineDTO.getFileList());
            bDFOnlineDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }

        //HTML 태그 처리
        String cntn = bDFOnlineDTO.getCntn();
        bDFOnlineDTO.setCntn(COWebUtil.clearXSSMinimum(cntn));

        bDFOnlineDTO.setMnlSeq(mnlSeqIdgen.getNextIntegerId());

        return bDFOnlineMapper.insertOnline(bDFOnlineDTO);
    }

    /**
     * Online 수정
     */
    public int updateOnline(BDFOnlineDTO bDFOnlineDTO) throws Exception{
        //수정자
        COUserDetailsDTO cOUserDetailsDTO  = COUserDetailsHelperService.getAuthenticatedUser();
        bDFOnlineDTO.setModId(cOUserDetailsDTO.getId());
        bDFOnlineDTO.setModIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(bDFOnlineDTO.getFileList() != null && !bDFOnlineDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(bDFOnlineDTO.getFileList());
            bDFOnlineDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }

        //HTML 태그 처리
        String cntn = bDFOnlineDTO.getCntn();
        bDFOnlineDTO.setCntn(COWebUtil.clearXSSMinimum(cntn));

        return bDFOnlineMapper.updateOnline(bDFOnlineDTO);
    }


    /**
     * Online 삭제
     */
    public int deleteOnline(BDFOnlineDTO bDFOnlineDTO) throws Exception{

        return bDFOnlineMapper.deleteOnline(bDFOnlineDTO);
    }

    /**
     * Online 첨부파일 목록 조회
     */
    public BDFOnlineDTO selectOnlineFileList(BDFOnlineDTO bDFOnlineDTO) throws Exception {
        bDFOnlineDTO.setList(bDFOnlineMapper.selectOnlineFileList(bDFOnlineDTO));
        return bDFOnlineDTO;
    }
}
