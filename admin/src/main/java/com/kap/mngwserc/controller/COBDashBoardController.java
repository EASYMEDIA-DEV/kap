package com.kap.mngwserc.controller;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.service.COBMenuService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 대시보드를 위한 Controller
 * </pre>
 *
 * @ClassName		: COBDashBoardontroller.java
 * @Description		: 대시보드를 위한 Controller
 * @author 김학규
 * @since 2023.10.23
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2023.10.23		김학규					최초생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value={"/mngwserc"})
public class COBDashBoardController {

	/**
	 * 대시보드 목록 페이지
	 */
	@GetMapping(value="/co/coz/dashboard")
	public String getDashBoard(ModelMap modelMap, COAAdmDTO cOAAdmDTO) throws Exception
	{
		try
		{
			// 공통코드 배열 셋팅
			ArrayList<String> cdDtlList = new ArrayList<String>();
			// 코드 set
			cdDtlList.add("ADMIN_AUTH_CD");

			// 로그인한 계정
			COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
			cOAAdmDTO.setLgnSsnId(cOUserDetailsDTO.getId());

		}
		catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}

		return "mngwserc/co/CODashBoard.admin";
	}

}