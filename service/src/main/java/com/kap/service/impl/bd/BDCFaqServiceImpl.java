package com.kap.service.impl.bd;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.bd.bdc.BDCFaqDTO;
import com.kap.service.BDCFaqService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.bd.BDCFaqMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * BDCFaqService 서비스 Impl
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
 *   2024.01.05  이옥정         사용자에서 FAQ 리스트 가져올때 건수를 3건만 가져오므로 예외 처리 추가
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BDCFaqServiceImpl implements BDCFaqService {

    /** Mapper **/
    private final BDCFaqMapper bDCFaqMapper;

    /** Service **/
    private final COFileService cOFileService;

    /** Sequence **/
    private final EgovIdGnrService faqSeqIdgen;

    /** Upload Path **/
    @Value(" ${app.file.upload-path}")
    private String fileUploadPath;

    /**
     * FAQ 조회
     */
    public BDCFaqDTO selectFaqList(BDCFaqDTO pBDCFaqDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pBDCFaqDTO.getPageIndex());
        page.setRecordCountPerPage(pBDCFaqDTO.getListRowSize());

        page.setPageSize(pBDCFaqDTO.getPageRowSize());

        pBDCFaqDTO.setFirstIndex(page.getFirstRecordIndex());

        // 사용자 메인 노출 갯수 조건문 추가
        if(pBDCFaqDTO.getMainYn().equals("Y")) {
            pBDCFaqDTO.setRecordCountPerPage(3);
        }else{
            pBDCFaqDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        }

        pBDCFaqDTO.setTotalCount(bDCFaqMapper.getFaqListTotCnt(pBDCFaqDTO));
        pBDCFaqDTO.setList(bDCFaqMapper.selectFaqList(pBDCFaqDTO));
        return pBDCFaqDTO;
    }

    /**
     * FAQ 상세
     */
    public BDCFaqDTO selectFaqDtl(BDCFaqDTO pBDCFaqDTO) throws Exception{
        return bDCFaqMapper.selectFaqDtl(pBDCFaqDTO);
    }

    /**
     * FAQ 등록
     */
    public int insertFaq(BDCFaqDTO pBDCFaqDTO) throws Exception {
        //작성자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pBDCFaqDTO.setRegId(cOUserDetailsDTO.getId());
        pBDCFaqDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(pBDCFaqDTO.getFileList() != null && !pBDCFaqDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pBDCFaqDTO.getFileList());
            pBDCFaqDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }

        //HTML 태그 처리
        String cntn = pBDCFaqDTO.getCntn();
        pBDCFaqDTO.setCntn(COWebUtil.clearXSSMinimum(cntn));

        pBDCFaqDTO.setFaqSeq(faqSeqIdgen.getNextIntegerId());

        return bDCFaqMapper.insertFaq(pBDCFaqDTO);
    }

    /**
     * FAQ 수정
     */
    public int updateFaq(BDCFaqDTO pBDCFaqDTO) throws Exception{
        //수정자
        COUserDetailsDTO cOUserDetailsDTO  = COUserDetailsHelperService.getAuthenticatedUser();
        pBDCFaqDTO.setModId(cOUserDetailsDTO.getId());
        pBDCFaqDTO.setModIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(pBDCFaqDTO.getFileList() != null && !pBDCFaqDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pBDCFaqDTO.getFileList());
            pBDCFaqDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }

        //HTML 태그 처리
        String cntn = pBDCFaqDTO.getCntn();
        pBDCFaqDTO.setCntn(COWebUtil.clearXSSMinimum(cntn));

        return bDCFaqMapper.updateFaq(pBDCFaqDTO);
    }

    /**
     * FAQ 삭제
     */
    public int deleteFaq(BDCFaqDTO pBDCFaqDTO) throws Exception{

        return bDCFaqMapper.deleteFaq(pBDCFaqDTO);
    }

    /**
     * FAQ 첨부파일 목록 조회
     */
    public BDCFaqDTO selectFaqFileList(BDCFaqDTO pBDCFaqDTO) throws Exception {
        pBDCFaqDTO.setList(bDCFaqMapper.selectFaqFileList(pBDCFaqDTO));
        return pBDCFaqDTO;
    }
}
