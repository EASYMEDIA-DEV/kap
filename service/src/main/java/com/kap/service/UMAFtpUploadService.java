package com.kap.service;

import com.kap.core.dto.UMAFtpUploadDTO;

/**
 * UMAFtpUploadService 서비스
 *
 * @author 임서화
 * @since 2023.10.16
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.10.16  임서화         최초 생성
 * </pre>
 */
public interface UMAFtpUploadService {

    /**
     * FTP 업로드 조회
    */
    public UMAFtpUploadDTO selectUploadFileList(UMAFtpUploadDTO pUMAFtpUploadDTO) throws Exception;

    /**
     * FTP 업로드 등록
     */
    public int insertUploadFile(UMAFtpUploadDTO pUMAFtpUploadDTO) throws Exception;

    /**
     * FTP 업로드 삭제
     */
    public int deleteUploadFile(UMAFtpUploadDTO pUMAFtpUploadDTO) throws Exception;

}
