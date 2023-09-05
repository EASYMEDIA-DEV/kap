package com.kap.service;


import com.kap.core.dto.COAAdmDTO;

/**
 * EgovUserDetails Helper 클래스
 *
 * @author sjyoon
 * @since 2022.01.11
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.01.11  박주석         최초 생성
 * </pre>
 */
public class COUserDetailsHelperService {

	static COUserDetailsService cOUserDetailsService;

	public COUserDetailsService getEgovUserDetailsService()
	{
		return cOUserDetailsService;
	}

	/**
	 * 객체 등록
	 * @return Object - 사용자 ValueObject
	 */
	public void setEgovUserDetailsService(COUserDetailsService cOUserDetailsService)
	{
		COUserDetailsHelperService.cOUserDetailsService = cOUserDetailsService;
	}

	/**
	 * 인증된 사용자객체를 가져온다.
	 * @return Object - 사용자 ValueObject
	 */
	public static COAAdmDTO getAuthenticatedUser()
	{
		return cOUserDetailsService.getAuthenticatedUser();
	}

	/**
	 * 인증된 사용자 여부를 체크한다.
	 * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)
	 */
	public static Boolean isAuthenticated() 
	{
		return cOUserDetailsService.isAuthenticated();
	}
}