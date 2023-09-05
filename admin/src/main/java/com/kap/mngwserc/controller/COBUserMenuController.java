package com.kap.mngwserc.controller;

import com.kap.core.dto.COMenuDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <pre> 
 * 사용자 메뉴 관리를 위한 Controller
 * </pre>
 * 
 * @ClassName		: CODUserMenuController.java
 * @Description		: 사용자 메뉴 관리를 위한 Controller
 * @author 허진영
 * @since 2020.10.19
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2020.10.19		허진영					최초생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc")
public class COBUserMenuController {
	
	/**
	 * 사용자 메뉴 관리 페이지
	 */
	@RequestMapping(value="/{lnggCd}/co/cob/cobb/user", method=RequestMethod.GET)
	public String getUserMenuPage(COMenuDTO cOMenuDTO, ModelMap modelMap, @PathVariable String lnggCd) throws Exception
	{
		try
    	{
			cOMenuDTO.setLnggCd(lnggCd);
    		modelMap.addAttribute("rtnData", cOMenuDTO);
    	}
    	catch (Exception e)
		{
			if (log.isErrorEnabled())
			{
				log.debug(e.getMessage());
            }
			throw new Exception(e.getMessage());
		}
		
		return "mngwserc/co/cob/COBUserMenuWrite.admin";
	}
}