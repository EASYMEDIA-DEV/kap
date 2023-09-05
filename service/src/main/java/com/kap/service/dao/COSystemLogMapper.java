package com.kap.service.dao;

import com.kap.core.dto.COSystemLogDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <pre>
 * 시스템 로그 수집
 * </pre>
 *
 * @ClassName		: COSystemLogMapper.java
 * @Description		: 시스템 로그 수집
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2022.01.10	  박주석	             최초 생성
 * </pre>
 */
@Mapper
public interface COSystemLogMapper {

    /**
     * 시스템 로그 등록
     */
    public int logInsertSysLog(COSystemLogDTO cOSystemLogDTO);
    /**
     * 시스템 에러 로그 등록
     */
    public int logInsertErrLog(COSystemLogDTO cOSystemLogDTO);

    /**
     * 시스템 쿼리 로그 등록
     */
    public int logInsertSysQuery(COSystemLogDTO cOSystemLogDTO);
}
