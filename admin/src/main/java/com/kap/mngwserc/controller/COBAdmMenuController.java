package com.kap.mngwserc.controller;

import com.kap.core.annotation.MapData;
import com.kap.core.dto.EmfMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <pre> 
 * 관리자 메뉴 관리를 위한 Controller
 * </pre>
 * 
 * @ClassName		: CODAdmMenuController.java
 * @Description		: 관리자 메뉴 관리를 위한 Controller
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
@RequestMapping(value="/mngwserc/co/cob/coba")
public class COBAdmMenuController  {
	
	/**
	 * 관리자 메뉴 페이지
	 */
	@RequestMapping(value="/admin", method= RequestMethod.GET)
	public String getAdmMenuPage(@MapData EmfMap emfMap, ModelMap modelMap) throws Exception
	{
		try
    	{
    		modelMap.addAttribute("rtnData", emfMap);
    	}
    	catch (Exception e)
		{
			if (log.isErrorEnabled())
			{
				log.debug(e.getMessage());
            }
			throw new Exception(e.getMessage());
		}
		return "mngwserc/co/cob/COBAdmMenuWrite.admin";
	}
}