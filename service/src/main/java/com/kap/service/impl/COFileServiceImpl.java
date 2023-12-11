package com.kap.service.impl;

import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.EmfMap;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.COFileMapper;
import com.nhncorp.lucy.security.xss.XssPreventer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.*;

/**
 * @Class Name : EgovFileMngServiceImpl.java
 * @Description : 파일정보의 관리를 위한 구현 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 25.     이삼섭    최초생성
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 25.
 * @version
 * @see
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COFileServiceImpl implements COFileService {

    private final COFileUtil fileUtil;

    // DAO
    private final COFileMapper cOFileMapper;

    /**
     * 파일에 대한 목록을 조회한다.
     */
    public List<COFileDTO> getFileInfs(int fileSeq) throws Exception {
        return cOFileMapper.selectFileInfs(fileSeq);
    }

    /**
     * 파일에 대한 상세정보를 조회한다.
     */
    public COFileDTO getFileInf(COFileDTO cOFileDTO) throws Exception {
        return cOFileMapper.selectFileInf(cOFileDTO);
    }

    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     */
    public void insertFileInf(COFileDTO cOFileDTO) throws Exception {
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

        cOFileDTO.setRegId(cOUserDetailsDTO.getId());
        cOFileDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        //파일 마스터를 등록한다.
        cOFileMapper.insertFileMaster(cOFileDTO);

        //파일 상세를 등록한다.
        cOFileMapper.insertFileDetail(cOFileDTO);
    }

    /**
     * cOFileUtil로 바로 저장된 파일 DB 저장
     */
    public Integer insertFiles(List<COFileDTO> cFileDTOList) throws Exception {
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        if(cFileDTOList.size() == 0){
            return null;
        }
        int atchFileSeq = 0;
        COFileDTO mstCOFileDTO = cFileDTOList.get(0);
        cOFileMapper.insertFileMaster(mstCOFileDTO);
        atchFileSeq = mstCOFileDTO.getFileSeq();
        for(int q = 0 ; q < cFileDTOList.size() ; q++){
            cFileDTOList.get(q).setFileSeq(atchFileSeq);
            cFileDTOList.get(q).setFileOrd(q);
            cFileDTOList.get(q).setRegId(cOUserDetailsDTO.getId());
            cFileDTOList.get(q).setRegIp(cOUserDetailsDTO.getLoginIp());
            //파일 상세를 등록한다.
            cOFileMapper.insertFileDetail(cFileDTOList.get(q));
        }
        return atchFileSeq;
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     */
    public int insertFileInfs(List<COFileDTO> fvoList) throws Exception {
        COFileDTO fileDTO = null;

        if (fvoList.size() > 0) {
            fileDTO = (COFileDTO) fvoList.get(0);

            int parntCnt = cOFileMapper.getParntFileCnt(fileDTO.getFileSeq());

            if (parntCnt == 0) {
                cOFileMapper.insertFileMaster(fileDTO);
            }

            Iterator<COFileDTO> iter = fvoList.iterator();

            while (iter.hasNext()) {
                cOFileMapper.insertFileDetail(iter.next());
            }
        }
        return fileDTO.getFileSeq();
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     */
    public void updateFileInfs(List<COFileDTO> fvoList) throws Exception {
        if (fvoList.size() > 0) {
            Iterator<COFileDTO> iter = fvoList.iterator();

            while (iter.hasNext()) {
                cOFileMapper.insertFileDetail(iter.next());
            }
        }
    }

    /**
     * 파일 구분자에 대한 최대값을 구한다.
     */
    public int getMaxFileSeq(int fileSeq) throws Exception {
        return cOFileMapper.getMaxFileSeq(fileSeq);
    }

    /**
     * 파일 임시 업로드 후 정보조회
     */
    public List<EmfMap> getUpdoadFileInfs(EmfMap EmfMap, MultipartHttpServletRequest multiRequest) throws Exception {
        return fileUtil.tempFileInf(multiRequest.getFileMap(), "file");
    }

    /**
     * 임시폴더의 파일을 업로드 폴더로 복사한다. (등록시)
     */
    public void copyFileInfs(COFileDTO cOFileDTO, String fileTypeId, List<String> filePaths, List<String> fileAlts) throws Exception {
        this.copyFileInfs(cOFileDTO, fileTypeId, filePaths, fileAlts, null, null);
    }

    /**
     * 임시폴더의 파일을 업로드 폴더로 복사한다. (수정시)
     */
    public void copyFileInfs(COFileDTO cOFileDTO, String fileTypeId, List<String> filePaths, List<String> fileAlts, String fileSeqCnct, List<Integer> fileOrds) throws Exception {
        Integer fileSeq = cOFileDTO.getFileSeq();
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

        if (!"".equals(fileSeq) && !"".equals(COStringUtil.nullConvert(fileSeqCnct))) {
            COFileDTO fileDTO = new COFileDTO();

            fileDTO.setFileSeq(fileSeq);
            fileDTO.setFileOrdArr(Arrays.stream(fileSeqCnct.split(",")).mapToInt(Integer::parseInt).toArray());

            fileDTO.setRegId(cOUserDetailsDTO.getId());
            fileDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            cOFileMapper.deleteFileInfs(fileDTO);
        }

        if(filePaths != null && filePaths.size() > 0) {
            int maxFileSeq = 0;

            if (!"".equals(fileSeq) && !"".equals(COStringUtil.nullConvert(fileSeq))) {
                // 파일 구분자에 대한 최대값을 구한다.
                maxFileSeq = cOFileMapper.getMaxFileSeq(fileSeq);
            }
            List<COFileDTO> fileList = fileUtil.copyFileInfs(cOFileDTO, fileSeq, maxFileSeq, filePaths, fileAlts);

            if (fileList.size() > 0) {
                if (!"".equals(fileSeq) && !"".equals(COStringUtil.nullConvert(fileSeq))) {
                    this.updateFileInfs(fileList);
                } else {
                    // 해당 파일 ID에 해당하는 fileSeq 값 넣음
                    cOFileDTO.setFileTypeIdSeq(this.insertFileInfs(fileList));
                }
            }
        }

        if (!"".equals(fileSeq) && !"".equals(COStringUtil.nullConvert(fileSeq))) {
            // 파일 구분자에 대한 파일 갯수를 가져온다.
            int fileCnt = cOFileMapper.getTotFileCnt(fileSeq);

            COFileDTO paramDTO = null;

            if (fileCnt == 0) {
                paramDTO = new COFileDTO();

                paramDTO.setFileSeq(fileSeq);
                paramDTO.setRegId(cOUserDetailsDTO.getId());
                paramDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

                cOFileMapper.deleteAllFileInf(paramDTO);

                cOFileDTO.setFileSeq(null);
            } else {
                // 대체 텍스트 처리
                List<COFileDTO> fileInfList = new ArrayList<>();

                for (int i = 0, len = fileCnt; i < len; i++) {
                    paramDTO = new COFileDTO();

                    paramDTO.setFileOrd(fileOrds.get(i));
                    paramDTO.setFileDsc(fileAlts.get(i));

                    fileInfList.add(paramDTO);
                }

                if (fileInfList.size() > 0) {
                    paramDTO = new COFileDTO();

                    paramDTO.setFileSeq(fileSeq);
                    paramDTO.setFileInfList(fileInfList);

                    cOFileMapper.updateFileAlts(paramDTO);
                }
            }
        }
    }

    /**
     * DEXT5 사용으로 인한 cos.jar의 multipart 사용 및 파일 임시 업로드 후 정보조회
     */
    /*public List<COFileDTO> getCosUpdoadFileInfs(COFileDTO cFileDTO, HttpServletRequest request) throws Exception
    {
        return fileUtil.tempCosFileInf(cFileDTO, request);
    }*/

    /**
     * 파일 설명 수정
     */
    public int upadateFileDsc(List<COFileDTO> cFileDTOList) throws Exception
    {
        return cOFileMapper.updateFileAlt(cFileDTOList);
    }

    /**
     * 파일 삭제
     */
    public int deleteFile(List<COFileDTO> cFileDTOList) throws Exception
    {
        return cOFileMapper.deleteFile(cFileDTOList);
    }

    /**
     * 파일 마스터, 파일 상세 모두 삭제 (USE_YN = N 처리)
     */
    public void deleteAllFile(int fileSeq) throws Exception {
        cOFileMapper.deleteFileDtl(fileSeq);
        cOFileMapper.deleteFileMst(fileSeq);
    }

    /**
     * 임시 파일 복사 및 등록
     */
    public int insertFile(List<COFileDTO> cFileDTOList, HashMap<String, Integer> rtnData) throws Exception
    {
        COFileDTO cOFileDTO = null;
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

        int fileOrd = 0;
        String filedNm = "";
        HashMap<String, Integer> rtnOrdData = new HashMap<String, Integer>();

        for(int q = 0 ; q < cFileDTOList.size() ; q++) {
            cOFileDTO = cFileDTOList.get(q);
            fileUtil.cosFileCopy(cOFileDTO);

            cOFileDTO.setRegId( cOUserDetailsDTO.getId() );
            cOFileDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );

            // 파일이 정상적으로 이동하였고 파일 순번이 없으면 파일 순번딴다.
            if("00".equals(cOFileDTO.getRespCd()) && rtnData.get(cOFileDTO.getFieldNm()) == null && !filedNm.equals(cOFileDTO.getFieldNm()))
            {
                // 파일 마스터 순번 조회
                // 파일 순번 마이바티스에서 리턴받음.
                cOFileMapper.insertFileMaster(cOFileDTO);

                filedNm = cOFileDTO.getFieldNm();
                rtnData.put(filedNm, cOFileDTO.getFileSeq());
            }
            cOFileDTO.setFileSeq(rtnData.get(cOFileDTO.getFieldNm()));

            // 파일 정렬 값 분기 처리
            // fileNmOrd가 없으면 파일 순번의 maxOrd 있으면 rtnOrdData의 fileNmOrd +1
            if(rtnOrdData.get(cOFileDTO.getFieldNm() + "Ord") == null) {
                fileOrd = cOFileMapper.getMaxFileSeq(rtnData.get(cOFileDTO.getFieldNm()));
                rtnOrdData.put(cOFileDTO.getFieldNm() + "Ord", fileOrd);
            } else {
                rtnOrdData.put(cOFileDTO.getFieldNm() + "Ord", rtnOrdData.get(cOFileDTO.getFieldNm() + "Ord") + 1);
            }
            cOFileDTO.setFileOrd( rtnOrdData.get(cOFileDTO.getFieldNm() + "Ord"));

        }
        //DB 전송
        return cOFileMapper.insertFileDetailList( cFileDTOList );
    }

    /*
     * 파일 운영 복사 및 DB 처리
     */
    public HashMap<String, Integer> setFileInfo(List<COFileDTO> coFileDTOList) throws Exception{
        HashMap<String, Integer> rtnData = new HashMap<String, Integer>();
        if(coFileDTOList != null)
        {
            COFileDTO cOFileDTO = null;
            List<COFileDTO> addedfileList   = new ArrayList<COFileDTO>();
            List<COFileDTO> successfileList = new ArrayList<COFileDTO>();
            List<COFileDTO> delfileList     = new ArrayList<COFileDTO>();
            //수정, 삭제, 등록 한번씩만 처리. 파일이 여러개가 넘어오면 부하 걸림.
            for(int q = 0 ; q < coFileDTOList.size() ; q++){
                cOFileDTO = coFileDTOList.get(q);
                cOFileDTO.setOrgnFileNm(XssPreventer.unescape(cOFileDTO.getOrgnFileNm()));
                cOFileDTO.setFileDsc(XssPreventer.unescape(cOFileDTO.getFileDsc()));
                //파일 필드 명과 순번 담기
                if("addedfile".equals(cOFileDTO.getStatus()) && cOFileDTO.getFileSeq() != null && cOFileDTO.getFileOrd() != null){
                    //파일 설명 수정 처리
                    //fileSeq, fileOrd는 필수
                    addedfileList.add(cOFileDTO);
                }
                else if("success".equals(cOFileDTO.getStatus())){
                    //파일 이동 및 DB 등록 처리
                    successfileList.add(cOFileDTO);
                }
                else if("delfile".equals(cOFileDTO.getStatus())  && cOFileDTO.getFileSeq() != null && cOFileDTO.getFileOrd() != null){
                    //파일 삭제 처리
                    //fileSeq, fileOrd는 필수
                    delfileList.add(cOFileDTO);
                }
                rtnData.put(cOFileDTO.getFieldNm(), cOFileDTO.getFileSeq());
            }
            if(addedfileList.size() > 0){
                //DB 처리
                upadateFileDsc( addedfileList );
            }
            if(delfileList.size() > 0){
                //DB 처리
                deleteFile( delfileList );
            }
            if(successfileList.size() > 0){
                //파일 복사 처리
                //DB처리
                insertFile(successfileList, rtnData);
            }
        }
        return rtnData;
    }
}
