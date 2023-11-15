package com.kap.service;

import com.kap.core.dto.SMJFormDTO;

/**
 * <pre>
 * 양식 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: SMJFormService.java
 * @Description		: 양식 관리를 위한 Service
 * @author 구은희
 * @since 2023.11.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.15		구은희				   최초 생성
 * </pre>
 */
public interface SMJFormService {

    /**
     * 양식 관리 상세를 조회한다.
     */
    public SMJFormDTO selectFormDtl(SMJFormDTO smjFormDTO) throws Exception;

    /**
     * 양식 관리를 수정한다.
     */
    public int updateFormDtl(SMJFormDTO smjFormDTO) throws Exception;

}
