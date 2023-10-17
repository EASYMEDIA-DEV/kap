package com.kap.service.dao.um;

import com.kap.core.dto.UMAFtpUploadDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * FTP 업로드 Mapper
 * </pre>
 *
 * @ClassName		: UMAFtpUploadMapper.java
 * @Description		: FTP 업로드 Mapper
 * @author 임서화
 * @since 2023.10.12
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.10.12	  임서화	             최초 생성
 * </pre>
 */
@Mapper
public interface UMAFtpUploadMapper {
    /**
     * FTP 업로드 조회
   */
    public List<UMAFtpUploadDTO> selectUploadFileList(UMAFtpUploadDTO ppUMAFtpUploadDTO) throws Exception;
    /**
     * FTP 업로드 전체 갯수
     */
    public int getFtpUploadListTotCnt(UMAFtpUploadDTO pUMAFtpUploadDTO) throws Exception;
    /**
     * FTP 업로드 등록
     */
    public int insertUploadFile(UMAFtpUploadDTO pUMAFtpUploadDTO) throws Exception;
    /**
     * FTP 업로드 삭제
     */
    public int deleteUploadFile(UMAFtpUploadDTO pUMAFtpUploadDTO) throws Exception;

}
