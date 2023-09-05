package com.kap.service;

import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.EmfMap;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;

/**
 * @Class Name : EgovFileMngService.java
 * @Description : 파일정보의 관리를 위한 서비스 인터페이스
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
public interface COFileService {
    /**
     * 파일에 대한 목록을 조회한다.
     */
    public List<COFileDTO> getFileInfs(int fileSeq) throws Exception;

    /**
     * 파일에 대한 상세정보를 조회한다.
     */
    public COFileDTO getFileInf(COFileDTO cOFileDTO) throws Exception;

    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     */
    public void insertFileInf(COFileDTO cOFileDTO) throws Exception;

    /**
     * 파일에 대한 정보(속성 및 상세)를 등록한다.
     */
    public int insertFileInfs(List<COFileDTO> fvoList) throws Exception;

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     */
    public void updateFileInfs(List<COFileDTO> fvoList) throws Exception;

    /**
     * 파일 구분자에 대한 최대값을 구한다.
     */
    public int getMaxFileSeq(int fileSeq) throws Exception;

    /**
     * 파일 임시 업로드 후 정보조회
     */
    public List<EmfMap> getUpdoadFileInfs(EmfMap EmfMap, MultipartHttpServletRequest multiRequest) throws Exception;

    /**
     * 임시폴더의 파일을 업로드 폴더로 복사한다. (등록시)
     */
    public void copyFileInfs(COFileDTO cOFileDTO, String fileTypeId, List<String> filePaths, List<String> fileAlts) throws Exception;

    /**
     * 임시폴더의 파일을 업로드 폴더로 복사한다. (수정시)
     */
    public void copyFileInfs(COFileDTO cOFileDTO, String fileTypeId, List<String> filePaths, List<String> fileAlts, String fileSeqCnct, List<Integer> FileOrd) throws Exception;


    /**
     * DEXT5 사용으로 인한 cos.jar의 multipart 사용 및 파일 임시 업로드 후 정보조회
     */
    //public List<COFileDTO> getCosUpdoadFileInfs(COFileDTO cFileDTO, HttpServletRequest request) throws Exception;

    /**
     * 파일 설명 수정
     */
    public int upadateFileDsc(List<COFileDTO> cFileDTOList) throws Exception;

    /**
     * 파일을 삭제
     */
    public int deleteFile(List<COFileDTO> coFileDTOList) throws Exception;

    /**
     * 파일 순번으로 파일 마스터, 파일 상세 모두 삭제 (USE_YN = N 처리)
     */
    public void deleteAllFile(int fileSeq) throws Exception;

    /**
     * 임시 파일 복사 및 등록
     */
    public int insertFile(List<COFileDTO> cFileDTOList, HashMap<String, Integer> rtnData) throws Exception;

    /*
    * 파일 운영 복사 및 DB 처리
     */
    public HashMap<String, Integer> setFileInfo(List<COFileDTO> cFileDTOList) throws Exception;
}
