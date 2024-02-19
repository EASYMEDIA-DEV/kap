package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.sm.smb.SMBMainVslDTO;
import com.kap.service.COFileService;
import com.kap.service.COSystemLogService;
import com.kap.service.SMBMnVslService;
import com.kap.service.dao.sm.SMBMnVslMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <pre>
 * 메인 비주얼 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMBMnVslServiceImpl.java
 * @Description		: 메인 비주얼 관리를 위한 ServiceImpl
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.14		임서화				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SMBMnVslServiceImpl implements SMBMnVslService {

    // DAO
    private final COFileService cOFileService;

    private final SMBMnVslMapper sMBMnVslMapper;

    // 메인 시퀀스
    private final EgovIdGnrService mainVslIdgen;

    // 로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    // 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;

    @Value("${app.file.videoExtns}")
    private String videoExtns;

    /**
     * 메인 비주얼 목록 조회
     */
    public SMBMainVslDTO selectMnVslList(SMBMainVslDTO pSMBMainVslDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pSMBMainVslDTO.getPageIndex());
        page.setRecordCountPerPage(pSMBMainVslDTO.getListRowSize());

        page.setPageSize(pSMBMainVslDTO.getPageRowSize());

        pSMBMainVslDTO.setFirstIndex(page.getFirstRecordIndex());
        pSMBMainVslDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pSMBMainVslDTO.setTotalCount(sMBMnVslMapper.selectMnVsldTotCnt(pSMBMainVslDTO));
        pSMBMainVslDTO.setList(sMBMnVslMapper.selectMnVslList(pSMBMainVslDTO));

        return pSMBMainVslDTO;
    }

    /**
     * 메인 비주얼 상세 조회
     */
    public SMBMainVslDTO selectMnVslDtl(SMBMainVslDTO pSMBMainVslDTO) throws Exception {
        SMBMainVslDTO info = new SMBMainVslDTO();

        if (!"".equals(pSMBMainVslDTO.getDetailsKey())) {
            info = sMBMnVslMapper.selectMnVslDtl(pSMBMainVslDTO);
            String fileExtn = info.getFileExtn();
            if (fileExtn.toLowerCase().equals("mp4")) {
                info.setVideoFileSeq(info.getFileSeq());
            } else {
                info.setImgFileSeq(info.getFileSeq());
            }
        }
        return info;
    }

    /**
     * 메인 비주얼 등록
     */
    public int insertMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception {

        int mainCnt = sMBMnVslMapper.selectMnVslCnt(pSMBMainVslDTO);

        // 등록 시점에 노출이 5개면 제한
        // 메인 비주얼 배너 업로드 갯수 5개 제한
        // 노출여부가 N이면 갯수 체크 안함
        boolean temp = (pSMBMainVslDTO.getExpsYn().equals("N") || mainCnt < 5) ? true : false;

        if(temp){
            pSMBMainVslDTO.setVslSeq(mainVslIdgen.getNextIntegerId());
            pSMBMainVslDTO.setMnHexCd(pSMBMainVslDTO.getMnHexCd().replace(" ", ""));
            pSMBMainVslDTO.setSubHexCd(pSMBMainVslDTO.getSubHexCd().replace(" ", ""));

            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pSMBMainVslDTO.getFileList());
            String tagCd = pSMBMainVslDTO.getTagCd();

            if (tagCd.equals("image")) {
                pSMBMainVslDTO.setFileSeq(fileSeqMap.get("imgFileSeq"));
            } else {
                pSMBMainVslDTO.setFileSeq(fileSeqMap.get("videoFileSeq"));
            }

            pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.insertMnVsl(pSMBMainVslDTO));
        }else{
            pSMBMainVslDTO.setRespCnt(-1);
            pSMBMainVslDTO.setRespMsg("메인에 노출할 게시물은 최대 5개까지입니다.");
        }

        return pSMBMainVslDTO.getRespCnt();
    }

    /**
     * 메인 비주얼 수정
     */
    public int updateMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pSMBMainVslDTO.getFileList());

        String tagCd = pSMBMainVslDTO.getTagCd();
        if(tagCd.equals("image")){
            pSMBMainVslDTO.setFileSeq(fileSeqMap.get("imgFileSeq"));
        }else{
            pSMBMainVslDTO.setFileSeq(fileSeqMap.get("videoFileSeq"));
        }
        pSMBMainVslDTO.setVslSeq(Integer.valueOf(pSMBMainVslDTO.getDetailsKey()));
        String expsYn = pSMBMainVslDTO.getExpsYn();
        if(expsYn.equals("Y")){
            // 등록하는 글의 노출 여부가 Y일 때, 개수 체크
            pSMBMainVslDTO.setUpdateYn("Y");
            pSMBMainVslDTO.setDStrDt(pSMBMainVslDTO.getExpsStrtDtm());
            pSMBMainVslDTO.setDEndDt(pSMBMainVslDTO.getExpsEndDtm());
            int mainCnt = sMBMnVslMapper.selectMnVslCnt(pSMBMainVslDTO);
            // 메인 비주얼 배너 업로드 개수 5개 제한
            if (mainCnt < 5) {
                pSMBMainVslDTO.setMnHexCd(pSMBMainVslDTO.getMnHexCd().replace(" ", ""));
                pSMBMainVslDTO.setSubHexCd(pSMBMainVslDTO.getSubHexCd().replace(" ", ""));
                pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.updateMnVsl(pSMBMainVslDTO));
            } else {
                pSMBMainVslDTO.setRespCnt(-1);
                pSMBMainVslDTO.setRespMsg("메인에 노출할 게시물은 최대 5개까지입니다.");
            }
        }else{
            // 노출여부가 N이면 개수 체크 x
            pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.updateMnVsl(pSMBMainVslDTO));
        }
        return pSMBMainVslDTO.getRespCnt();
    }

    /**
     * 메인 비주얼 삭제
     */
    public int deleteMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
        return sMBMnVslMapper.deleteMnVsl(pSMBMainVslDTO);
    }

    /**
     * 메인 비주얼 정렬 수정
     */
    public void updateOrder(SMBMainVslDTO pSMBMainVslDTO) throws Exception {

        SMBMainVslDTO newRow = sMBMnVslMapper.selectMnNewRow(pSMBMainVslDTO);
        newRow.setMdCd(newRow.getMdCd());

        if(newRow != null){

            int newRowOrd = newRow.getExpsOrd();
            int orginOrd = pSMBMainVslDTO.getExpsOrd();

            pSMBMainVslDTO.setExpsOrd(newRowOrd);
            sMBMnVslMapper.updateOrder(pSMBMainVslDTO);

            newRow.setModIp(pSMBMainVslDTO.getModIp());
            newRow.setModId(pSMBMainVslDTO.getModId());
            newRow.setExpsOrd(orginOrd);
            sMBMnVslMapper.updateOrder(newRow);
        }
    }

    /**
     * 정렬할 메인 비주얼을 조회한다.
     */
    public SMBMainVslDTO selectMnNewRow(SMBMainVslDTO pSMBMainVslDTO) throws Exception {
        return sMBMnVslMapper.selectMnNewRow(pSMBMainVslDTO);
    }
}
