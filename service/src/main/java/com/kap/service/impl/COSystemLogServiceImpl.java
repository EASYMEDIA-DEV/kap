package com.kap.service.impl;

import com.kap.core.dto.COSystemLogDTO;
import com.kap.service.COSystemLogService;
import com.kap.service.dao.COSystemLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 시스템 로그 수집
 * </pre>
 *
 * @ClassName		: AspectConfig.java
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
@Slf4j
@Service
@RequiredArgsConstructor
public class COSystemLogServiceImpl implements COSystemLogService {

    //매퍼
    private final COSystemLogMapper cOSystemLogMapper;

   /**
    * 시스템 로그 등록
   */
    public int logInsertSysLog(COSystemLogDTO cOSystemLogDTO)
    {
        return cOSystemLogMapper.logInsertSysLog(cOSystemLogDTO);
    }
    /**
    * 시스템 에러 로그 등록
    */
    public int logInsertErrLog(COSystemLogDTO cOSystemLogDTO) {
        return cOSystemLogMapper.logInsertErrLog(cOSystemLogDTO);
    }
    /*
     * 쿼리 수집
     */
    public int logInsertSysQuery(COSystemLogDTO cOSystemLogDTO){
        return cOSystemLogMapper.logInsertSysQuery(cOSystemLogDTO);
    }
}
