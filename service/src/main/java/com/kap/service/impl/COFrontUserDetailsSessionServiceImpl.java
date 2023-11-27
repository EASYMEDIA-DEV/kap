package com.kap.service.impl;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.service.COUserDetailsService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * COUserDetailsSessionServiceImpl 세션 클래스
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
public class COFrontUserDetailsSessionServiceImpl implements COUserDetailsService {

	public COUserDetailsDTO getAuthenticatedUser()
	{
		return (COUserDetailsDTO)RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION);
	}

	public Boolean isAuthenticated() 
	{
		// 인증된 유저인지 확인한다.
		if (RequestContextHolder.getRequestAttributes() == null) 
		{
			return false;
		} 
		else 
		{
			if (RequestContextHolder.getRequestAttributes().getAttribute("loginMap", RequestAttributes.SCOPE_SESSION) == null)
			{
				return false;
			}
			else 
			{
				return true;
			}
		}
	}
}