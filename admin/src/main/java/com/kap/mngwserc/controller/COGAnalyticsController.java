package com.kap.mngwserc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 * 구글애털리틱스 Controller
 * </pre>
 *
 * @ClassName		: COGAnalyticsController.java
 * @Description		: 구글애털리틱스 Controller
 * @author 박주석
 * @since 2022.04.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2022.04.10		박주석					최초생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/co/cog")
public class COGAnalyticsController {

	/**
	 * 코드 관리 페이지
	 */
    @GetMapping(value="/list")
	public String getCodePage(ModelMap modelMap) throws Exception
	{
    	try
    	{

    	}
    	catch (Exception e)
		{
			if (log.isErrorEnabled())
			{
				log.debug(e.getMessage());
            }
			throw new Exception(e.getMessage());
		}
    	return "mngwserc/co/cog/COGGoogleStatView.admin";
	}
}