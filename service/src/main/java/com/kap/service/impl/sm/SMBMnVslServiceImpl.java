package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.SMBMainVslDTO;
import com.kap.service.COFileService;
import com.kap.service.COSeqGnrService;
import com.kap.service.COSystemLogService;
import com.kap.service.SMBMnVslService;
import com.kap.service.dao.sm.SMBMnVslMapper;
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
    private final COFileService cOFileService;

    private final COSeqGnrService cOSeqGnrService;

    private final SMBMnVslMapper sMBMnVslMapper;

    // 로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    // 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;

    @Value("${app.file.videoExtns}")
    private String videoExtns;

    // SEQ Gnr
    String tableNm = "MAIN_VSL_SEQ";

    /**
     * 메인 비주얼 목록 조회
     */
    public SMBMainVslDTO selectMnVslList(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
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
    public SMBMainVslDTO selectMnVslDtl(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
        SMBMainVslDTO info = new SMBMainVslDTO();

        if (!"".equals(pSMBMainVslDTO.getDetailsKey()))
        {
            info = sMBMnVslMapper.selectMnVslDtl(pSMBMainVslDTO);

            /*if (info != null)
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
            }*/
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
        if(pSMBMainVslDTO.getMdCd().equals("pc")) {
            fileSeqMap = cOFileService.setFileInfo(pSMBMainVslDTO.getFileList());
            pSMBMainVslDTO.setPcAtchFileSeq(fileSeqMap.get("fileSeq"));
        }

        // 모바일 이미지/영상
        if(pSMBMainVslDTO.getMdCd().equals("mobile")) {
            fileSeqMap = cOFileService.setFileInfo(pSMBMainVslDTO.getFileList());
            pSMBMainVslDTO.setMblAtchFileSeq(fileSeqMap.get("fileSeq"));
        }

       /* int mainCnt = sMBMnVslMapper.selectMnVslCnt(pSMBMainVslDTO);*/
        // 기존 노출중인 메인 비주얼과 현재 업로드하는 메인 비주얼의 첨부파일 확장자가 같은지 확인
        /*if("Y".equals(pSMBMainVslDTO.getMainYn())) {
            pSMBMainVslDTO.setImageExtns(imageExtns);
            pSMBMainVslDTO.setVideoExtns(videoExtns);
            SMBMainVslDTO checkMnVsl = sMBMnVslMapper.selectNowMnVsl(pSMBMainVslDTO);

            if(checkMnVsl != null) {
                String beforeMnVslExtn = checkMnVsl.getBeforeGb();
                String nowMnVslExtn = checkMnVsl.getNowGb();
                if(beforeMnVslExtn.equals(nowMnVslExtn)) {
                    // 2023-06-05 메인 비주얼 배너 업로드 개수 5개 제한 복원
                    if(mainCnt < 5 ) {
                        pSMBMainVslDTO.setSeq(cOSeqGnrService.selectSeq(tableNm));
                        pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.insertMnVsl(pSMBMainVslDTO));
                    }else {
                        pSMBMainVslDTO.setRespCnt(-1);
                        pSMBMainVslDTO.setRespMsg("메인에 노출할 게시물은 최대 5개까지입니다.");
                    }
                }
                else {
                    pSMBMainVslDTO.setRespCnt(-1);
                    pSMBMainVslDTO.setRespMsg("FILE_MISS_MATCH");
                }
            }
            else {
                // 메인 비주얼 배너 업로드 개수 5개 제한
                if(mainCnt < 5 ) {
                    pSMBMainVslDTO.setSeq(cOSeqGnrService.selectSeq(tableNm));
                    pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.insertMnVsl(pSMBMainVslDTO));
                }else {
                    pSMBMainVslDTO.setRespCnt(-1);
                    pSMBMainVslDTO.setRespMsg("메인에 노출할 게시물은 최대 5개까지입니다.");
                }
            }
        }*/
        /*else {
            // 메인 비주얼 배너 업로드 개수 5개 제한
            if(mainCnt < 5 ) {
                pSMBMainVslDTO.setSeq(cOSeqGnrService.selectSeq(tableNm));
                pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.insertMnVsl(pSMBMainVslDTO));
            }else {
                pSMBMainVslDTO.setRespCnt(-1);
                pSMBMainVslDTO.setRespMsg("메인에 노출할 게시물은 최대 5개까지입니다.");
            }
        }*/
        pSMBMainVslDTO.setSeq(cOSeqGnrService.selectSeq(tableNm));
        pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.insertMnVsl(pSMBMainVslDTO));
        return pSMBMainVslDTO.getRespCnt();
    }

    /**
     * 메인 비주얼 수정
     */
    public int updateMnVsl(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
        HashMap<String, Integer> fileSeqMap;

        // PC 이미지/영상
        if(pSMBMainVslDTO.getMdCd().equals("pc")) {
            fileSeqMap = cOFileService.setFileInfo(pSMBMainVslDTO.getFileList());
            pSMBMainVslDTO.setPcAtchFileSeq(fileSeqMap.get("fileSeq"));
        }

        // 모바일 이미지/영상
        if(pSMBMainVslDTO.getMdCd().equals("mobile")) {
            fileSeqMap = cOFileService.setFileInfo(pSMBMainVslDTO.getFileList());
            pSMBMainVslDTO.setMblAtchFileSeq(fileSeqMap.get("fileSeq"));
        }
        // 기존 노출중인 메인 비주얼과 현재 업로드하는 메인 비주얼의 첨부파일 확장자가 같은지 확인
        /*if("Y".equals(pSMBMainVslDTO.getMainYn())) {
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
                        pSMBMainVslDTO.setRespCd("-1");
                        pSMBMainVslDTO.setRespMsg("메인에 노출할 게시물은 최대 5개까지입니다.");
                    }
                }
                else {
                    pSMBMainVslDTO.setRespCd("-1");
                    pSMBMainVslDTO.setRespMsg("파일 확장자가 일치하지 않습니다.");
                }
            }
            else {
                // 메인 비주얼 배너 업로드 개수 5개 제한
                if(this.selectMnVslCnt(pSMBMainVslDTO) < 5 ) {
                    pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.updateMnVsl(pSMBMainVslDTO));
                }else {
                    pSMBMainVslDTO.setRespCd("-1");
                    pSMBMainVslDTO.setRespMsg("메인에 노출할 게시물은 최대 5개까지입니다.");
                }
            }
        }
        else {
            // 2023-06-05 메인 비주얼 배너 업로드 개수 5개 제한 복원
            if("N".equals(pSMBMainVslDTO.getMainYn()) || this.selectMnVslCnt(pSMBMainVslDTO) < 5 ) {
                pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.updateMnVsl(pSMBMainVslDTO));
            }else {
                pSMBMainVslDTO.setRespCd("-1");
                pSMBMainVslDTO.setRespMsg("업로드 개수를 확인해주세요.");
            }
        }*/
        pSMBMainVslDTO.setRespCnt(sMBMnVslMapper.updateMnVsl(pSMBMainVslDTO));
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
    /*public int updateUseYn(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
        return sMBMnVslMapper.updateUseYn(pSMBMainVslDTO);
    }*/

    /**
     * 메인 비주얼 정렬 수정
     */
    public void updateOrder(SMBMainVslDTO pSMBMainVslDTO) throws Exception {

        SMBMainVslDTO newRow = sMBMnVslMapper.selectMnNewRow(pSMBMainVslDTO);
        newRow.setGubun(newRow.getMdCd());

        if(newRow != null){

            int newRowOrd = newRow.getOrd();
            int orginOrd = pSMBMainVslDTO.getOrd();

            pSMBMainVslDTO.setOrd(newRowOrd);
            sMBMnVslMapper.updateOrder(pSMBMainVslDTO);

            newRow.setModIp(pSMBMainVslDTO.getModIp());
            newRow.setModId(pSMBMainVslDTO.getModId());
            newRow.setOrd(orginOrd);
            sMBMnVslMapper.updateOrder(newRow);
        }
    }

    /**
     * 정렬할 메인 비주얼을 조회한다.
     */
    public SMBMainVslDTO selectMnNewRow(SMBMainVslDTO pSMBMainVslDTO) throws Exception {
        return sMBMnVslMapper.selectMnNewRow(pSMBMainVslDTO);
    }

    /**
     * 노출=Y 메인 비주얼 개수
     */
    public int selectMnVslCnt(SMBMainVslDTO pSMBMainVslDTO) throws Exception
    {
        return sMBMnVslMapper.selectMnVslCnt(pSMBMainVslDTO);
    }



}
