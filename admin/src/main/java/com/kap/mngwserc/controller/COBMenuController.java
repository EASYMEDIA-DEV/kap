package com.kap.mngwserc.controller;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.service.COBMenuService;
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
 * 메뉴 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: CODMenuController.java
 * @Description		: 메뉴 관리를 위한 Controller
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
@RequestMapping(value={"/mngwserc/co/cob/coba"})
public class COBMenuController {

	//서비스
	private final COBMenuService cOBMenuService;

	/**
	 * 메뉴 목록을 조회한다.
	 */
	@RequestMapping(value="/select", method=RequestMethod.POST)
	public void getMenuList(COMenuDTO cOMenuDTO, HttpServletResponse response, HttpServletRequest request) throws Exception
	{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try
		{
			cOMenuDTO.setIsChkd("N");
			List<COMenuDTO> menuList = cOBMenuService.getMenuList(cOMenuDTO);
			int startNum = 0, paramSeq = cOMenuDTO.getMenuSeq();
			JSONArray jSONArray = cOBMenuService.getJsonData(menuList, startNum, paramSeq);
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
			modelMap.addAttribute("rtnData", cOBMenuService.selectMenuDtl(cOMenuDTO));
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
			modelMap.addAttribute("actCnt", cOBMenuService.insertMenu(cOMenuDTO));
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
			modelMap.addAttribute("actCnt", cOBMenuService.updateMenuNm(cOMenuDTO));
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
			modelMap.addAttribute("actCnt", cOBMenuService.updateMenuInf(cOMenuDTO));
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
			modelMap.addAttribute("actCnt", cOBMenuService.updateMenuPstn(cOMenuDTO));
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
			modelMap.addAttribute("actCnt", cOBMenuService.deleteMenu(cOMenuDTO));
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
			List<COMenuDTO> list = cOBMenuService.getParntData(cOMenuDTO);
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
			List<COMenuDTO> list = cOBMenuService.getChildData(cOMenuDTO);
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