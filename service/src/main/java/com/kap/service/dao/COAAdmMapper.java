package com.kap.service.dao;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.EmfMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre> 
 * 관리자 관리를 위한 DAO
 * </pre>
 * 
 * @ClassName		: COAAdmMapper.java
 * @Description		: 관리자 관리를 위한 DAO
 * @author 허진영
 * @since 2020.10.22
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2020.10.22		허진영				   최초 생성
 * </pre>
 */
@Mapper
public interface COAAdmMapper {
    /**
	 * 관리자 목록 갯수 조회
	 */
	public int getAdmListCnt(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 관리자 목록을 조회한다.
	 */
	public List<EmfMap> selectAdmList(COAAdmDTO pCOAAdmDTO) throws Exception;

}