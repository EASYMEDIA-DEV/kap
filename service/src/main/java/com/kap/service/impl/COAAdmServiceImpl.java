package com.kap.service.impl;

import com.kap.core.dto.COAAdmDTO;
import com.kap.service.dao.COAAdmMapper;
import com.kap.service.COAAdmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre> 
 * 관리자 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: COCAdmServiceImpl.java
 * @Description		: 관리자 관리를 위한 ServiceImpl
 * @author 신혜정
 * @since 2022.04.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2022.04.14		신혜정				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COAAdmServiceImpl  implements COAAdmService {

	//DAO
	private final COAAdmMapper cOAAdmMapper;


	/**
	 * 관리자 목록을 조회한다.
	 */
	public COAAdmDTO selectAdmList(COAAdmDTO pCOAAdmDTO) throws Exception
	{
		cOAAdmMapper.selectAdmList(pCOAAdmDTO);
		return pCOAAdmDTO;
	}

}