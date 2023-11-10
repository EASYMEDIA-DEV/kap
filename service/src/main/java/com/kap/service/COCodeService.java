package com.kap.service;

import com.kap.core.dto.COCodeDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * 공통코드등 전체 업무에서 공용해서 사용해야 하는 서비스를 정의하기 위한 서비스 인터페이스
 * @author 공통서비스 개발팀 이삼섭
 * @since 2009.04.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.11  이삼섭          최초 생성
 *
 * </pre>
 */
public interface COCodeService {
	/**
     * 공통코드를 조회한다.
     */
    public HashMap<String, List<COCodeDTO>> getCmmCodeBindAll(ArrayList<String> cdDtlList) throws Exception;

    /**
     * 공통코드를 조회한다.(뎁스선택)
     */
    public HashMap<String, List<COCodeDTO>> getCmmCodeBindAll(ArrayList<String> cdDtlList , String depth) throws Exception;


    /**
     * 코드 목록(선택한 코드부터 자식코드까지)
     */
    public List<COCodeDTO> getCdIdList(COCodeDTO cOCodeDTO) throws Exception;

    /**
     * 선택한 코드의 부모값까지 조회
     */
    public List<COCodeDTO> getCdIdPrntList(COCodeDTO cOCodeDTO) throws Exception;

}