package com.kap.service.impl.bd;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.bd.bde.BDEDisclosureDTO;
import com.kap.service.BDEDisclosureService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.bd.BDEDisclosureMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * BDEDisclosureService 서비스 Impl
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
public class BDEDisclosureServiceImpl implements BDEDisclosureService {

    /** Mapper **/
    private final BDEDisclosureMapper bDADisclosureMapper;

    /** Service **/
    private final COFileService cOFileService;

    /** Sequence **/
    private final EgovIdGnrService disclosureSeqIdgen;

    /**
     * 공지사항 조회
     */
    public BDEDisclosureDTO selectDisclosureList(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pBDEDisclosureDTO.getPageIndex());
        page.setRecordCountPerPage(pBDEDisclosureDTO.getListRowSize());

        page.setPageSize(pBDEDisclosureDTO.getPageRowSize());

        pBDEDisclosureDTO.setFirstIndex(page.getFirstRecordIndex());
        pBDEDisclosureDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pBDEDisclosureDTO.setTotalCount(bDADisclosureMapper.getDisclosureListTotCnt(pBDEDisclosureDTO));
        pBDEDisclosureDTO.setList(bDADisclosureMapper.selectDisclosureList(pBDEDisclosureDTO));
        return pBDEDisclosureDTO;
    }

    /**
     * 공지사항 상세
     */
    public BDEDisclosureDTO selectDisclosureDtl(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception{
        return bDADisclosureMapper.selectDisclosureDtl(pBDEDisclosureDTO);
    }

    /**
     * 공지사항 등록
     */
    public int insertDisclosure(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception {
        //작성자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pBDEDisclosureDTO.setRegId(cOUserDetailsDTO.getId());
        pBDEDisclosureDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(pBDEDisclosureDTO.getFileList() != null && !pBDEDisclosureDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pBDEDisclosureDTO.getFileList());
            pBDEDisclosureDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }

        //HTML 태그 처리
        String cntn = pBDEDisclosureDTO.getCntn();
        pBDEDisclosureDTO.setCntn(COWebUtil.clearXSSMinimum(cntn));

        pBDEDisclosureDTO.setDsclsrSeq(disclosureSeqIdgen.getNextIntegerId());

        return bDADisclosureMapper.insertDisclosure(pBDEDisclosureDTO);
    }

    /**
     * 공지사항 수정
     */
    public int updateDisclosure(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception{
        //수정자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pBDEDisclosureDTO.setModId(cOUserDetailsDTO.getId());
        pBDEDisclosureDTO.setModIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(pBDEDisclosureDTO.getFileList() != null && !pBDEDisclosureDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pBDEDisclosureDTO.getFileList());
            pBDEDisclosureDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }

        //HTML 태그 처리
        String cntn = pBDEDisclosureDTO.getCntn();
        pBDEDisclosureDTO.setCntn(COWebUtil.clearXSSMinimum(cntn));

        return bDADisclosureMapper.updateDisclosure(pBDEDisclosureDTO);
    }

    /**
     * 공지사항 삭제
     */
    public int deleteDisclosure(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception{

        return bDADisclosureMapper.deleteDisclosure(pBDEDisclosureDTO);
    }
}
