package com.kap.service;

import com.kap.core.dto.im.ima.IMAQaDTO;

import javax.servlet.http.HttpServletRequest;

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
     * 1:1 문의 등록
     */
    public int insertQa(IMAQaDTO pIMAQaDTO, HttpServletRequest request) throws Exception;

    /**
     * 1:1 문의 상세
     */
    public IMAQaDTO selectQaDtl(IMAQaDTO pIMAQaDTO) throws Exception;

}
