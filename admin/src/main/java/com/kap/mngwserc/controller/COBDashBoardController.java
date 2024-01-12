package com.kap.mngwserc.controller;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COBDashBoardDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.service.COBDashBoardService;
import com.kap.service.COLgnService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.Valid;
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

	private final COBDashBoardService cOBDashBoardService;

	@Autowired
	private COLgnService cOLgnService;

	/**
	 * 대시보드 목록 페이지
	 */
	@GetMapping(value="/co/coz/dashboard")
	public String getDashBoard(COBDashBoardDTO cOBDashBoardDTO, ModelMap modelMap, COAAdmDTO cOAAdmDTO) throws Exception
	{
		try
		{
			COBDashBoardDTO rtnData = cOBDashBoardService.selectDashboard(cOBDashBoardDTO);
			modelMap.addAttribute("rtnData", rtnData);


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


	@PostMapping(value="/co/coz/dashboardAuth")
	public String getDashBoardLink(@Valid @RequestBody COBDashBoardDTO cOBDashBoardDTO,  ModelMap modelMap) throws Exception
	{
		try
		{

			int driveMenuSeq = cOBDashBoardDTO.getDriveMenuSeq();
			String moveLink = cOBDashBoardDTO.getLink();

			RequestContextHolder.getRequestAttributes().setAttribute("driveMenuSeq", driveMenuSeq, RequestAttributes.SCOPE_SESSION);
			COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
			cOUserDetailsDTO.setDriveMenuSeq( driveMenuSeq );
			List<COMenuDTO> menuList = cOLgnService.getMenuList(cOUserDetailsDTO);
			RequestContextHolder.getRequestAttributes().setAttribute("menuList", menuList, RequestAttributes.SCOPE_SESSION);

			modelMap.addAttribute("moveLink", moveLink);
		}
		catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return "jsonView";
	}

}