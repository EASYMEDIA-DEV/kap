package com.kap.service.impl;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.SMBMainVslDTO;
import com.kap.core.utility.COSeqGnrUtil;
import com.kap.service.COFileService;
import com.kap.service.COSystemLogService;
import com.kap.service.SMBMnVslService;
import com.kap.service.dao.SMBMnVslMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class SMBMnVslServiceImpl implements SMBMnVslService {

    // DAO
    private COFileService cOFileService;

    private SMBMnVslMapper sMBMnVslMapper;

    // 로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    // 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;

    @Value("${app.file.videoExtns}")
    private String videoExtns;

    // SEQ Gnr
    String tableNm = "MAIN_VSL_SEQ";

    COSeqGnrUtil cOSeqGnrUtil;

    /**
     * 메인 비주얼 목록 조회
     */
    public SMBMainVslDTO selectMnVslList(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pSMBMainVslDTO.getPageIndex());
        page.setRecordCountPerPage(pSMBMainVslDTO.getListRowSize());

        page.setPageSize(pSMBMainVslDTO.getPageRowSize());

        pSMBMainVslDTO.setFirstIndex( page.getFirstRecordIndex() );
        pSMBMainVslDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        pSMBMainVslDTO.setImageExtns(imageExtns);
        pSMBMainVslDTO.setVideoExtns(videoExtns);

        pSMBMainVslDTO.setList( sMBMnVslMapper.selectMnVslList(pSMBMainVslDTO) );
        pSMBMainVslDTO.setTotalCount( sMBMnVslMapper.selectMnVslCnt(pSMBMainVslDTO) );

//        emfMap.put("gbArr", emfMap.getList("gb"));
//        emfMap.put("positionOrdArr", emfMap.getList("positionOrd"));   //2023-01-10 메인 배너 3개 띄우기 위한 수정 (jds)

        return pSMBMainVslDTO;
    }

    /**
     * 메인 비주얼 상세 조회
     */
    public SMBMainVslDTO selectMnVslDtl(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
        SMBMainVslDTO info = new SMBMainVslDTO();

        if (!"".equals(pSMBMainVslDTO.getDetailsKey()))
        {
            info = sMBMnVslMapper.selectMnVslDtl(pSMBMainVslDTO);

            if (info != null)
            {
                int pcAtchFileSeq = info.getPcAtchFileSeq();

                if (pcAtchFileSeq != 0)
                {
                    info.setFileList(cOFileService.getFileInfs(pcAtchFileSeq));
                }

                int mblAtchFileSeq = info.getMblAtchFileSeq();

                if (mblAtchFileSeq != 0)
                {
                    info.setFileList(cOFileService.getFileInfs(mblAtchFileSeq));
                }
            }
        }

        return info;
    }

    /**
     * 메인 비주얼 등록
     */
    public int insertMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {

        HashMap<String, Integer> fileSeqMap;

        // PC 이미지/영상
        if(pSMBMainVslDTO.getDvcCd().equals("pc")) {
            fileSeqMap = cOFileService.setFileInfo(pSMBMainVslDTO.getFileList());
            pSMBMainVslDTO.setPcAtchFileSeq(fileSeqMap.get("fileSeq"));
//            cOFileService.copyFileInfs(file, "pcAtchFileSeq", pSMBMainVslDTO.getPcAtchAddFile(), pSMBMainVslDTO.getPcAtchAddFileAlt());
        }

        // 모바일 이미지/영상
        if(pSMBMainVslDTO.getDvcCd().equals("mobile")) {
            fileSeqMap = cOFileService.setFileInfo(pSMBMainVslDTO.getFileList());
            pSMBMainVslDTO.setMblAtchFileSeq(fileSeqMap.get("fileSeq"));
//            cOFileService.copyFileInfs(file, "mblAtchFileSeq", pSMBMainVslDTO.getMblAtchAddFile(), pSMBMainVslDTO.getMblAtchAddFileAlt());
        }

        // 기존 노출중인 메인 비주얼과 현재 업로드하는 메인 비주얼의 첨부파일 확장자가 같은지 확인
        if("Y".equals(pSMBMainVslDTO.getUseYn())) {
            pSMBMainVslDTO.setImageExtns(imageExtns);
            pSMBMainVslDTO.setVideoExtns(videoExtns);
            SMBMainVslDTO checkMnVsl = sMBMnVslMapper.selectNowMnVsl(pSMBMainVslDTO);

            if(checkMnVsl != null) {
                String beforeMnVslExtn = checkMnVsl.getBeforeGb();
                String nowMnVslExtn = checkMnVsl.getNowGb();
                if(beforeMnVslExtn.equals(nowMnVslExtn)) {
                    // 2023-06-05 메인 비주얼 배너 업로드 개수 5개 제한 복원
                    if(this.selectMnVslCnt(pSMBMainVslDTO) < 5 ) {
                        pSMBMainVslDTO.setVslSeq(cOSeqGnrUtil.getSeq(tableNm));
                        pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.insertMnVsl(pSMBMainVslDTO));
                    }else {
                        pSMBMainVslDTO.setRespCd("error");
                        pSMBMainVslDTO.setRespMsg("CNTOVER");
                    }
                }
                else {
                    pSMBMainVslDTO.setRespCd("error");
                    pSMBMainVslDTO.setRespMsg("FILE_MISS_MATCH");
                }
            }
            else {
                // 메인 비주얼 배너 업로드 개수 5개 제한
                if(this.selectMnVslCnt(pSMBMainVslDTO) < 5 ) {
                    pSMBMainVslDTO.setVslSeq(cOSeqGnrUtil.getSeq(tableNm));
                    pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.insertMnVsl(pSMBMainVslDTO));
                }else {
                    pSMBMainVslDTO.setRespCd("error");
                    pSMBMainVslDTO.setRespMsg("CNTOVER");
                }
            }
        }
        else {
            // 메인 비주얼 배너 업로드 개수 5개 제한
            if(this.selectMnVslCnt(pSMBMainVslDTO) < 5 ) {
                pSMBMainVslDTO.setVslSeq(cOSeqGnrUtil.getSeq(tableNm));
                pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.insertMnVsl(pSMBMainVslDTO));
            }else {
                pSMBMainVslDTO.setRespCd("error");
                pSMBMainVslDTO.setRespMsg("CNTOVER");
            }
        }

        return pSMBMainVslDTO.getRespCnt();
    }

    /**
     * 메인 비주얼 수정
     */
    public int updateMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
        HashMap<String, Integer> fileSeqMap;

        // PC 이미지/영상
        if(pSMBMainVslDTO.getDvcCd().equals("pc")) {
            fileSeqMap = cOFileService.setFileInfo(pSMBMainVslDTO.getFileList());
            pSMBMainVslDTO.setPcAtchFileSeq(fileSeqMap.get("fileSeq"));
//            cOFileService.copyFileInfs(emfMap, "pcAtchFileSeq", emfMap.getList("pcAtchAddFile"), emfMap.getList("pcAtchAddFileAlt"), emfMap.getString("pcAtchDelFile"));
        }

        // 모바일 이미지/영상
        if(pSMBMainVslDTO.getDvcCd().equals("mobile")) {
            fileSeqMap = cOFileService.setFileInfo(pSMBMainVslDTO.getFileList());
            pSMBMainVslDTO.setMblAtchFileSeq(fileSeqMap.get("fileSeq"));
//            cOFileService.copyFileInfs(emfMap, "mblAtchFileSeq", emfMap.getList("mblAtchAddFile"), emfMap.getList("mblAtchAddFileAlt"), emfMap.getString("mblAtchDelFile"));
        }

        // 기존 노출중인 메인 비주얼과 현재 업로드하는 메인 비주얼의 첨부파일 확장자가 같은지 확인
        if("Y".equals(pSMBMainVslDTO.getUseYn())) {
            pSMBMainVslDTO.setImageExtns(imageExtns);
            pSMBMainVslDTO.setVideoExtns(videoExtns);
            SMBMainVslDTO checkMnVsl = sMBMnVslMapper.selectNowMnVsl(pSMBMainVslDTO);

            if(checkMnVsl != null) {
                String beforeMnVslExtn = pSMBMainVslDTO.getBeforeGb();
                String nowMnVslExtn = pSMBMainVslDTO.getNowGb();
                if(beforeMnVslExtn.equals(nowMnVslExtn)) {
                    // 메인 비주얼 배너 업로드 개수 5개 제한
                    if(this.selectMnVslCnt(pSMBMainVslDTO) < 5 ) {
                        pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.updateMnVsl(pSMBMainVslDTO));
                    }else {
                        pSMBMainVslDTO.setRespCd("error");
                        pSMBMainVslDTO.setRespMsg("CNTOVER");
                    }
                }
                else {
                    pSMBMainVslDTO.setRespCd("error");
                    pSMBMainVslDTO.setRespMsg("FILE_MISS_MATCH");
                }
            }
            else {
                // 메인 비주얼 배너 업로드 개수 5개 제한
                if(this.selectMnVslCnt(pSMBMainVslDTO) < 5 ) {
                    pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.updateMnVsl(pSMBMainVslDTO));
                }else {
                    pSMBMainVslDTO.setRespCd("error");
                    pSMBMainVslDTO.setRespMsg("CNTOVER");
                }
            }
        }
        else {
            // 2023-06-05 메인 비주얼 배너 업로드 개수 5개 제한 복원
            if("N".equals(pSMBMainVslDTO.getUseYn()) || this.selectMnVslCnt(pSMBMainVslDTO) < 5 ) {
                pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.updateMnVsl(pSMBMainVslDTO));
            }else {
                pSMBMainVslDTO.setRespCd("error");
                pSMBMainVslDTO.setRespMsg("CNTOVER");
            }
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
     * 메인 비주얼 사용 여부 수정
     */
    public int updateUseYn(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
        return sMBMnVslMapper.updateUseYn(pSMBMainVslDTO);
    }

    /**
     * 메인 비주얼 정렬 수정
     */
    public int updateOrder(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
        /*List<String> seqList = emfMap.getList("seqList[]");

        int listLen = seqList.size();*/
        int actCnt = 0;

        /*if (listLen > 0)
        {
            EmfMap ordMap = new EmfMap();

            ordMap.put("gubun", emfMap.getString("gubun"));
            ordMap.put("admId", emfMap.getString("admId"));
            ordMap.put("admIp", emfMap.getString("admIp"));

            int minOrd = emfMap.getInt("minOrd");

            for (int i = 0; i < listLen; i++)
            {
                ordMap.put("ord", minOrd + i);
                ordMap.put("detailsKey", seqList.get(i));

                actCnt += sMBMnVslMapper.updateOrder(ordMap);
            }
        }
        else
        {
            actCnt = sMBMnVslMapper.updateOrder(emfMap);
        }*/

        return actCnt;
    }

    /**
     * 노출=Y 메인 비주얼 개수
     */
    public int selectMnVslCnt(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
        return sMBMnVslMapper.selectMnVslCnt(pSMBMainVslDTO);
    }

}
