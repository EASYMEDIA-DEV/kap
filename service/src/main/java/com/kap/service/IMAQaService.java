package com.kap.service;

import com.kap.core.dto.im.ima.IMAQaDTO;
import com.kap.core.dto.im.ima.IMAQaPicDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 1:1 문의 service
 *
 * @author 장두석
 * @since 2023.11.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.01  장두석         최초 생성
 * </pre>
 */
public interface IMAQaService {

    /**
     * 1:1 문의 목록 조회
    */
    public IMAQaDTO selectQaList(IMAQaDTO pIMAQaDTO) throws Exception;

    /**
     * 1:1 문의 상세
     */
    public IMAQaDTO selectQaDtl(IMAQaDTO pIMAQaDTO) throws Exception;

    /**
     * 1:1 문의 답변 등록
     */
    public int insertQaRply(IMAQaDTO pIMAQaDTO) throws Exception;

    /**
     * 1:1 문의 담당자 목록 조회
     */
    public IMAQaPicDTO selectQaPicList(IMAQaPicDTO pIMAQaPicDTO) throws Exception;

    /**
     * 1:1 문의 담당자 등록
     */
    public int insertQaPic(IMAQaPicDTO pIMAQaPicDTO) throws Exception;

    /**
     * 1:1 문의 담당자 수정
     */
    public int updateQaPic(IMAQaPicDTO pIMAQaPicDTO) throws Exception;

    /**
     * 1:1 문의 담당자 삭제
     */
    public int deleteQaPic(IMAQaPicDTO pIMAQaPicDTO) throws Exception;

    /**
     * 1:1 문의 유형별 담당자 등록 개수 조회
     */
    public int getQaPicCnt(IMAQaPicDTO pIMAQaPicDTO) throws Exception;

    /**
     * 1:1 문의 담당자 상세 조회
     */
    public IMAQaPicDTO selectQaPicDtl(IMAQaPicDTO pIMAQaPicDTO) throws Exception;




    /**
     * 1:1 문의 등록 (사용자)
     */
    public int insertQa(IMAQaDTO pIMAQaDTO, MultipartFile[] atchFile) throws Exception;

}
