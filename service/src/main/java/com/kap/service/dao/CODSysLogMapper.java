package com.kap.service.dao;

import com.kap.core.dto.COSystemLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 시스템 로그 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: CODSysLogMapper.java
 * @Description		: 시스템 로그 관리를 위한 DAO
 * @author 손태주
 * @since 2022.02.17
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2022.02.17		손태주					최초생성
 * </pre>
 */

@Mapper
public interface CODSysLogMapper {

	/**
	 * 시스템 로그 목록 갯수 조회
	 */
	public int getSysLogListCnt(COSystemLogDTO pCODSysLogDTO) throws Exception;

	/**
	 * 시스템 로그 목록 조회
	 */
	public List<COSystemLogDTO> selectSysLogList(COSystemLogDTO pCODSysLogDTO) throws Exception;
}