package com.kap.mngwserc.controller;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.service.COBUserMenuService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

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
 *    2023.10.19	 	 임서화				사용자 메뉴 분리
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/co/cob/cobb")
public class COBUserMenuController {

	//서비스
	private final COBUserMenuService cOBUserMenuService;
	
	/**
	 * 사용자 메뉴 관리 페이지
	 */
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public String getUserMenuPage(COMenuDTO cOMenuDTO, ModelMap modelMap) throws Exception
	{
		try
    	{
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

	@RequestMapping(value="/select", method=RequestMethod.POST)
	public void getMenuList(COMenuDTO cOMenuDTO, HttpServletResponse response, HttpServletRequest request) throws Exception
	{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try
		{
			cOMenuDTO.setIsChkd("N");
			List<COMenuDTO> menuList = cOBUserMenuService.getMenuList(cOMenuDTO);
			int startNum = 0, paramSeq = cOMenuDTO.getMenuSeq();
			JSONArray jSONArray = cOBUserMenuService.getJsonData(menuList, startNum, paramSeq);
			out.print(jSONArray);
			jSONArray = null;
		}
		catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		finally
		{
			out.close();
		}
	}
	/**
	 * 메뉴 상세정보를 조회한다.
	 */
	@RequestMapping(value="/details", method=RequestMethod.POST)
	public String selectMenuDtl(COMenuDTO cOMenuDTO, ModelMap modelMap) throws Exception
	{
		try
		{
			modelMap.addAttribute("rtnData", cOBUserMenuService.selectMenuDtl(cOMenuDTO));
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

	/**
	 * 메뉴를 등록한다.
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insertMenu(COMenuDTO cOMenuDTO, ModelMap modelMap) throws Exception
	{
		try
		{
			COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
			cOMenuDTO.setRegId( coaAdmDTO.getId() );
			cOMenuDTO.setRegIp( coaAdmDTO.getLoginIp() );
			cOMenuDTO.setModId( coaAdmDTO.getId() );
			cOMenuDTO.setModIp( coaAdmDTO.getLoginIp() );
			modelMap.addAttribute("actCnt", cOBUserMenuService.insertMenu(cOMenuDTO));
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

	/**
	 * 메뉴명을 수정한다.
	 */
	@RequestMapping(value ="/name-update", method=RequestMethod.POST)
	public String updateMenuNm(COMenuDTO cOMenuDTO, ModelMap modelMap) throws Exception
	{
		try
		{
			COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
			cOMenuDTO.setRegId( coaAdmDTO.getId() );
			cOMenuDTO.setRegIp( coaAdmDTO.getLoginIp() );
			cOMenuDTO.setModId( coaAdmDTO.getId() );
			cOMenuDTO.setModIp( coaAdmDTO.getLoginIp() );
			modelMap.addAttribute("actCnt", cOBUserMenuService.updateMenuNm(cOMenuDTO));
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

	/**
	 * 메뉴 정보를 수정한다.
	 */
	@RequestMapping(value="/info-update", method=RequestMethod.POST)
	public String updateMenuInf(COMenuDTO cOMenuDTO, ModelMap modelMap) throws Exception
	{
		try
		{
			COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
			cOMenuDTO.setRegId( coaAdmDTO.getId() );
			cOMenuDTO.setRegIp( coaAdmDTO.getLoginIp() );
			cOMenuDTO.setModId( coaAdmDTO.getId() );
			cOMenuDTO.setModIp( coaAdmDTO.getLoginIp() );
			modelMap.addAttribute("actCnt", cOBUserMenuService.updateMenuInf(cOMenuDTO));
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

	/**
	 * 메뉴를 이동한다.
	 */
	@RequestMapping(value="/pstn-update", method=RequestMethod.POST)
	public String updateMenuPstn(COMenuDTO cOMenuDTO, ModelMap modelMap) throws Exception
	{
		try
		{
			COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
			cOMenuDTO.setRegId( coaAdmDTO.getId() );
			cOMenuDTO.setRegIp( coaAdmDTO.getLoginIp() );
			cOMenuDTO.setModId( coaAdmDTO.getId() );
			cOMenuDTO.setModIp( coaAdmDTO.getLoginIp() );
			modelMap.addAttribute("actCnt", cOBUserMenuService.updateMenuPstn(cOMenuDTO));
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

	/**
	 * 메뉴를 삭제한다.
	 */
	@RequestMapping(value ="/delete", method=RequestMethod.POST)
	public String deleteMenu(COMenuDTO cOMenuDTO, ModelMap modelMap) throws Exception
	{
		try
		{
			COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
			cOMenuDTO.setRegId( coaAdmDTO.getId() );
			cOMenuDTO.setRegIp( coaAdmDTO.getLoginIp() );
			cOMenuDTO.setModId( coaAdmDTO.getId() );
			cOMenuDTO.setModIp( coaAdmDTO.getLoginIp() );
			modelMap.addAttribute("actCnt", cOBUserMenuService.deleteMenu(cOMenuDTO));
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

	/**
	 * 상위부모를 다 가져온다.
	 */
	@RequestMapping(value="/parnt-data", method=RequestMethod.GET)
	public void getParntData(COMenuDTO cOMenuDTO, ModelMap modelMap, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		try
		{
			List<COMenuDTO> list = cOBUserMenuService.getParntData(cOMenuDTO);
			JSONArray jSONArray = new JSONArray();
			for (int i = 0; i < list.size();)
			{
				JSONObject jSONObject = new JSONObject();

				jSONObject.put("data", list.get(i).getMenuNm());

				JSONObject jsonAttr = new JSONObject();

				jsonAttr.put("id", list.get(i).getMenuSeq());
				jsonAttr.put("rel", list.get(i).getMenuType());
				jsonAttr.put("parent_treeid", list.get(i).getParntSeq());
				jsonAttr.put("level", list.get(i).getDpth());
				jsonAttr.put("status", list.get(i).getUseYn());
				jsonAttr.put("link", list.get(i).getUserUrl());
				jsonAttr.put("treeid", list.get(i).getMenuSeq());
				jsonAttr.put("dpth", list.get(i).getDpth());
				jSONObject.put("attr", jsonAttr);
				jsonAttr = null;
				i++;
				jSONArray.add(jSONObject);
				jSONObject = null;
			}

			out.print(jSONArray);

			jSONArray = null;
		}
		catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		finally
		{
			out.close();
		}
	}

	/**
	 * 하위노드를 다 가져온다.
	 */
	@RequestMapping(value="/child-data", method=RequestMethod.GET)
	public void getChildData(COMenuDTO cOMenuDTO, ModelMap modelMap, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		try
		{
			List<COMenuDTO> list = cOBUserMenuService.getChildData(cOMenuDTO);
			JSONArray jSONArray = new JSONArray();
			COMenuDTO jsonDto = null;

			for (int i = 0; i < list.size();)
			{
				jsonDto = list.get(i);
				JSONObject jSONObject = new JSONObject();

				jSONObject.put("data", jsonDto.getMenuNm());

				JSONObject jsonAttr = new JSONObject();
				jsonAttr.put("id", jsonDto.getMenuSeq());
				jsonAttr.put("rel", jsonDto.getMenuType());
				jsonAttr.put("parent_treeid", jsonDto.getParntSeq());
				jsonAttr.put("level", jsonDto.getDpth());
				jsonAttr.put("status", jsonDto.getUseYn());
				jsonAttr.put("link", jsonDto.getUserUrl());
				jsonAttr.put("treeid", jsonDto.getMenuSeq());
				jsonAttr.put("type", jsonDto.getMenuType());
				jSONObject.put("attr", jsonAttr);

				jsonAttr = null;
				i++;
				jSONArray.add(jSONObject);
				jSONObject = null;
			}

			out.print(jSONArray);

			jSONArray = null;
		}
		catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		finally
		{
			out.close();
		}
	}
}