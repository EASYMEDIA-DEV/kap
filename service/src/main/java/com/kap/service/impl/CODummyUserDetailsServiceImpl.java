package com.kap.service.impl;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.service.COUserDetailsService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


/**
 * CODummyUserDetailsServiceImpl 더비 클래스
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
public class CODummyUserDetailsServiceImpl implements COUserDetailsService
{
	public COUserDetailsDTO getAuthenticatedUser()
	{
		COUserDetailsDTO cOLoginUserDTO = COUserDetailsDTO.builder().build();
		cOLoginUserDTO.setSeq(1);
		cOLoginUserDTO.setId("hyumain1");
		cOLoginUserDTO.setName("TBD");
		cOLoginUserDTO.setAuthCd("99");
		cOLoginUserDTO.setDeptNm("DEPT01");
		cOLoginUserDTO.setDeptCd("DEV");
		cOLoginUserDTO.setEmail("dev@easymedia.net");
		cOLoginUserDTO.setLastLgnDtm("2023-11-16 00:00:00");
		cOLoginUserDTO.setLoginIp("127.0.0.1");
		cOLoginUserDTO.setConSessionId("11111");
		RequestContextHolder.getRequestAttributes().setAttribute("loginMap", cOLoginUserDTO, RequestAttributes.SCOPE_SESSION);
		return cOLoginUserDTO;
	}

	public Boolean isAuthenticated()
	{
		return true;
	}
}