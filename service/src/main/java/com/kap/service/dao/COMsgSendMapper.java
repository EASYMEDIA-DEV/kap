package com.kap.service.dao;

import com.kap.core.dto.COMsgSendDtl;
import com.kap.core.dto.COMsgSendMst;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 메시지 발송 로그 수집 DAO
 * </pre>
 *
 * @ClassName		: COMsgSendMapper.java
 * @Description		: 메시지 발송 로그 수집 DAO
 * @author 이옥정
 * @since 2023.11.28
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.28		이옥정				   최초 생성
 * </pre>
 */
@Mapper
public interface COMsgSendMapper {
	/**
	 * 마스터 등록
	 */
	public int insertMsgSendMst(COMsgSendMst cOMsgSendMst) throws Exception;
	/**
	 * 상세 등록
	 */
	public int insertMsgSendDtl(List<COMsgSendDtl> cOMsgSendDtlList) throws Exception;
}