package com.kap.service;

import com.kap.core.dto.COSampleDTO;

/**
 * COSampleService 서비스
 *
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.01.10  박주석         최초 생성
 * </pre>
 */
public interface COSampleService {

    /**
     * 샘플 조회
    */
    public COSampleDTO selectSmplList(COSampleDTO cOSampleDTO) throws Exception;

    /**
     * 샘플 등록
     */
    public COSampleDTO insertSmpl(COSampleDTO cOSampleDTO) throws Exception;

    /**
     * 샘플 상세
     */
    public COSampleDTO selectSmplDtl(COSampleDTO cOSampleDTO) throws Exception;

    /**
     * 샘플 수정
     */
    public COSampleDTO updateSmpl(COSampleDTO cOSampleDTO) throws Exception;
    /**
     * 샘플 삭제
     */
    public COSampleDTO deleteSmpl(COSampleDTO cOSampleDTO) throws Exception;

    /**
     * 데이터 이관
     */
    public COSampleDTO setTbNm(COSampleDTO cOSampleDTO) throws Exception;

    /**
     * 데이터 삭제
     */
    public COSampleDTO setRemoveTbNm(COSampleDTO cOSampleDTO) throws Exception;

}
