package com.kap.service.dao.sm;

import com.kap.core.dto.SMHSmsSendYnDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <pre>
 * SMS 발송 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: SMHSmsSendYnMapper.java
 * @Description		: SMS 발송 관리를 위한 DAO
 * @author 구은희
 * @since 2023.11.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.15	구은희				   최초 생성
 * </pre>
 */
@Mapper
public interface SMHSmsSendYnMapper {

    /**
     * 상세를 조회
     */
    public SMHSmsSendYnDTO selectSmsSendYnDtl(SMHSmsSendYnDTO smhSmsSendYnDTO) throws Exception;

    /**
     * SMS 발송 여부를 수정
     */
    public int updateSmsSendYn(SMHSmsSendYnDTO smhSmsSendYnDTO) throws Exception;

}
