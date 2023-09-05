package com.kap.service;

import com.kap.core.dto.COSystemLogDTO;

/**
 * 시스템 로그 등록
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
public interface COSystemLogService {

	/**
	 * 시스템 로그(select, insert, update, delete) 등록
	 */
	public int logInsertSysLog(COSystemLogDTO cOSystemLogDTO);

	/**
	 * 시스템 로그(error) 등록
	 */
	public int logInsertErrLog(COSystemLogDTO cOSystemLogDTO);
	/*
	* 쿼리 수집
	 */
	public int logInsertSysQuery(COSystemLogDTO cOSystemLogDTO);
}