package com.kap.service;

import com.kap.core.dto.MPAUserDto;

/**
 * <pre>
 * 위원 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: MPDCmtService.java
 * @Description		: 위원 관리를 위한 Service
 * @author 양현우
 * @since 2023.11.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.21		양현우				   최초 생성
 * </pre>
 */
public interface MPDCmtService {


     int insertCmt(MPAUserDto mpaUserDto) throws Exception;

     int deleteCmt(MPAUserDto mpaUserDto) throws Exception;

}