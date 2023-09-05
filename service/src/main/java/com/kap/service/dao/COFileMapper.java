package com.kap.service.dao;

import com.kap.core.dto.COFileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 파일을 위한 DAO
 * </pre>
 *
 * @ClassName		: COFileMapper.java
 * @Description		: 파일을 위한 DAO
 * @author 손태주
 * @since 2022.03.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2022.03.08	  손태주	             최초 생성
 * </pre>
 */

@Mapper
public interface COFileMapper {

    /**
     * 파일에 대한 목록을 조회한다.
     */
    public List<COFileDTO> selectFileInfs(int fileSeq) throws Exception;

    /**
     * 파일에 대한 상세정보를 조회한다.
     */
    public COFileDTO selectFileInf(COFileDTO coFileDTO) throws Exception;

    /**
     * 파일 마스터를 등록한다.
     */
    public void insertFileMaster(COFileDTO coFileDTO) throws Exception;

    /**
     * 파일 상세를 등록한다.
     */
    public void insertFileDetail(COFileDTO coFileDTO) throws Exception;

    /**
     * 임시 파일 마스터를 등록한다.
     */
    public void insertTmpFileMaster(COFileDTO coFileDTO) throws Exception;

    /**
     * 임시 파일 상세를 등록한다.
     */
    public void insertTmpFileDetail(COFileDTO coFileDTO) throws Exception;

    /**
     * 하나의 파일을 삭제한다.
     */
    public void deleteFileInf(COFileDTO coFileDTO) throws Exception;

    /**
     * 여러 개의 파일을 삭제한다.
     */
    public void deleteFileInfs(COFileDTO coFileDTO) throws Exception;

    /**
     * 전체 파일을 삭제한다.
     */
    public void deleteAllFileInf(COFileDTO coFileDTO) throws Exception;

    /**
     * 파일 마스터에 데이터가 있는지 확인한다.
     */
    public int getParntFileCnt(int fileSeq) throws Exception;

    /**
     * 파일 구분자에 대한 최대값을 구한다.
     */
    public int getMaxFileSeq(int fileSeq) throws Exception;

    /**
     * 파일 구분자에 대한 파일갯수를 가져온다.
     */
    public int getTotFileCnt(int fileSeq) throws Exception;

    /**
     * 대체 텍스트를 수정한다.
     */
    public int updateFileAlts(COFileDTO coFileDTO) throws Exception;

    /**
     * 대체 텍스트를 수정한다.
     */
    public int updateFileAlt(List<COFileDTO> coFileDTOList) throws Exception;

    /**
     * 파일 등록
     */
    public int insertFileDetailList(List<COFileDTO> coFileDTOList) throws Exception;

    /**
     * 파일 삭제
     */
    public int deleteFile(List<COFileDTO> coFileDTOList) throws Exception;

    /**
     * 파일 마스터 fileSeq로 파일 삭제 (USE_YN = N 처리)
     */
    public int deleteFileMst(int fileSeq) throws Exception;

    /**
     * 파일 상세 fileSeq로 파일 삭제 (USE_YN = N 처리)
     */
    public int deleteFileDtl(int fileSeq) throws Exception;
}
