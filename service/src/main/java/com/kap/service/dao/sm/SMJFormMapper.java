package com.kap.service.dao.sm;

import com.kap.core.dto.SMJFormDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <pre>
 * 양식 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMJFormMapper.java
 * @Description		: 양식 관리를 위한 DAO
 * @author 구은희
 * @since 2023.11.06
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.06		구은희				   최초 생성
 * </pre>
 */
@Mapper
public interface SMJFormMapper {

    /**
     * 상세를 조회
     */
    public SMJFormDTO selectFormDtl(SMJFormDTO smjFormDTO) throws Exception;

    /**
     * 수정
     */
    public int updateFormDtl(SMJFormDTO smjFormDTO) throws Exception;

}
