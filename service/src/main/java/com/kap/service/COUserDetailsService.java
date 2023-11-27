package com.kap.service;


import com.kap.core.dto.COUserDetailsDTO;

/**
 * COUserDetailsService Helper 클래스
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
public interface COUserDetailsService {

	/**
	 * 인증된 사용자객체를 VO형식으로 가져온다.
	 * @return Object - 사용자 ValueObject
	 */
	public COUserDetailsDTO getAuthenticatedUser();

	/**
	 * 인증된 사용자 여부를 체크한다.
	 * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)	
	 */
	public Boolean isAuthenticated();
}