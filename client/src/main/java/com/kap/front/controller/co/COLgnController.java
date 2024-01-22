package com.kap.front.controller.co;

import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.service.*;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <pre>
 * 로그인/로그아웃을 위한 Controller
 * </pre>
 *
 * @ClassName		: COBLgnController.java
 * @Description		: 로그인/로그아웃을 위한 Controller
 * @author 양현우
 * @since 2023.12.06
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				   description
 *   ===========    ==============    =============================
 *   2023.12.06			양현우			 		최초생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class COLgnController {
	//로그인 서비스
    private final COUserLgnService cOUserLgnService;
	//이메일 발송
	private final COMessageService cOMessageService;

	private final MPAUserService mpaUserService;


	@Value("${app.site.name}")
	private String siteName;

	/**
	 * 로그인 페이지
	 */
    @GetMapping("/login")
	public String getLoginPage(HttpServletRequest request, HttpSession session, ModelMap modelMap) throws Exception
    {
    	try
		{
			// 보안 처리(로그인 세션 변경)
			session.invalidate();
			log.error("request.getAttribute(\"rtnUrl\") : {}", request.getParameter("rtnUrl"));
			modelMap.addAttribute("rtnUrl", COWebUtil.clearXSSMinimum(COStringUtil.nullConvert(request.getParameter("rtnUrl"))));
		}
    	catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
    	return "/front/co/COLgn.front";
	}

	/**
	 * 아이디 찾기
	 */
	@GetMapping("/id-find")
	public String getIdFind() throws Exception
	{
		return "/front/co/COIdFindWrite.front";
	}

	/**
	 * 아이디 결과 페이지
	 */
	@RequestMapping("/id-find-res")
	public String getIdFindPage(COIdFindDto coIdFindDto, ModelMap modelMap, HttpSession session) throws Exception
	{
		modelMap.addAttribute("rtnData", cOUserLgnService.getIdFind(coIdFindDto));
		return "/front/co/COIdResPage.front";
	}

	/**
	 * 비밀번호 찾기
	 */
	@GetMapping("/pwd-find")
	public String getPwdFind() throws Exception
	{

		return "/front/co/COPwdFindWrite.front";
	}

	/**
	 * 비밀번호 찾기 후 설정 화면
	 */
	@RequestMapping("/pwd-find-setting")
	public String getPwdFindSetting(COIdFindDto coIdFindDto , ModelMap modelMap) throws Exception
	{
		MPAUserDto idFind = cOUserLgnService.getIdFind(coIdFindDto);

		modelMap.addAttribute("rtnData",idFind);


		return "/front/co/COPwdSettingWrite.front";
	}


	/**
	 * 아이디 이메일 전송
	 * @param
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/id-email-trans")
	public String idEmailTran(COIdFindDto coIdFindDto) throws Exception
	{
		try
		{
				//이메일 발송
				COMailDTO cOMailDTO = new COMailDTO();
				cOMailDTO.setSubject("["+siteName+"] 아이디 찾기 결과 안내");
				//인증요청일시
				//수신자 정보
				COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
				//이메일
				receiverDto.setEmail(coIdFindDto.getEmail());
				//이름
				receiverDto.setName("");
				//치환문자1
				receiverDto.setNote1(coIdFindDto.getName());
				receiverDto.setNote2(coIdFindDto.getId());
				//수신자 정보 등록
				cOMailDTO.getReceiver().add(receiverDto);
				cOMessageService.sendMail(cOMailDTO, "IdEmailEDM.html");
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
	 * 비밀번호 변경
	 * @param
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/pwd-chng")
	public String idEmailTran() throws Exception
	{
		try
		{

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
	 * 비밀번호 찾기
	 */
	@PostMapping("/pwd-find")
	public String getIdFindPage(COIdFindDto coIdFindDto, ModelMap modelMap) throws Exception
	{

		modelMap.addAttribute("data", cOUserLgnService.getIdFind(coIdFindDto));
		return "jsonView";
	}


    /**
	 * 로그아웃을 처리한다.
	 */
    @RequestMapping(value="/my-page/logout", method=RequestMethod.GET)
    public String actionLogout(HttpSession sesssion, HttpServletRequest request) throws Exception
    {
    	try
        {
    		if (COUserDetailsHelperService.isAuthenticated())
    		{
				cOUserLgnService.actionLogout(request);
    		}
    		// 보안 처리(로그인 세션 변경)
    		sesssion.invalidate();
    	}
    	catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
    	return "redirect:/login";
    }

    /**
   	 * 비밀번호변경 페이지
   	 */
   	@RequestMapping(value="/change-password", method=RequestMethod.GET)
   	public String getPasswordChnagePage(ModelMap modelMap) throws Exception
   	{
		String rtnUrl = "/front/co/COPwdChngWrite.front";
		try
    	{
			if(RequestContextHolder.getRequestAttributes().getAttribute("tmpLgnMap", RequestAttributes.SCOPE_SESSION) == null)
			{
				rtnUrl = "redirect:/";
			}
			else
			{
				COUserDetailsDTO rtnCOAAdmDTO = (COUserDetailsDTO)RequestContextHolder.getRequestAttributes().getAttribute("tmpLgnMap", RequestAttributes.SCOPE_SESSION);
				modelMap.addAttribute("tmpLgnVO", rtnCOAAdmDTO);
			}
    	}
		catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return rtnUrl;
   	}

	/**
	 * 정보 업데이트 페이지
	 */
	@RequestMapping(value="/info-upd", method=RequestMethod.GET)
	public String getInfoUpdatePage(ModelMap modelMap) throws Exception
	{
		String rtnUrl = "/front/co/COLgnInfoUpd";
		try
		{
			if(RequestContextHolder.getRequestAttributes().getAttribute("tmpLgnMap", RequestAttributes.SCOPE_SESSION) == null)
			{
				rtnUrl = "redirect:/";
			}
			else
			{
				COUserDetailsDTO rtnCOAAdmDTO = (COUserDetailsDTO)RequestContextHolder.getRequestAttributes().getAttribute("tmpLgnMap", RequestAttributes.SCOPE_SESSION);
				MPAUserDto mpaUserDto = new MPAUserDto();
				mpaUserDto.setId(String.valueOf(rtnCOAAdmDTO.getId()));
				modelMap.addAttribute("rtnDtl", mpaUserService.selectCiUser(mpaUserDto));
				modelMap.addAttribute("tmpLgnVO", rtnCOAAdmDTO);
			}
		}
		catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return rtnUrl;
	}

	/**
	 * <pre>
	 * 로그인/로그아웃을 위한 API Controller
	 * </pre>
	 *
	 * @ClassName		: COLgnRestController.java
	 * @Description		: 로그인/로그아웃을 위한 API Controller
	 * @author 양현우
	 * @since 2023.12.06
	 * @version 1.0
	 * @see
	 * @Modification Information
	 * <pre>
	 * 		since			author				   description
	 *   ===========    ==============    =============================
	 *   2023.12.06		양현우			 		최초생성
	 * </pre>
	 */
	@RestController
	public class COLgnRestController {

		/**
		 * 로그인을 처리한다.
		 */
		@PostMapping("/login")
		public COLoginDTO actionLogin(COLoginDTO cOLoginDTO, HttpServletRequest request) throws Exception
		{
			COLoginDTO rtnCOLoginDTO = null;
			try
			{
				rtnCOLoginDTO = cOUserLgnService.actionLogin(cOLoginDTO, request);
 				COUserDetailsDTO tmpCOUserDetailsDTO  = (COUserDetailsDTO)RequestContextHolder.getRequestAttributes().getAttribute("tmpLgnMap", RequestAttributes.SCOPE_SESSION);
				// 로그인 세션 생성
				RequestContextHolder.getRequestAttributes().setAttribute("loginMap", tmpCOUserDetailsDTO, RequestAttributes.SCOPE_SESSION);

			}
			catch (Exception e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
				throw new Exception(e.getMessage());
			}
			return rtnCOLoginDTO;
		}
		/**
		 * 임시 또는 최초 비밀번호를 변경한다  로그인 한 경우 비밀번호 변경.
		 */
		@PostMapping("/change-password")
		public MPAUserDto setPwdChng(MPAUserDto mpaUserDto,HttpSession session) throws Exception
		{
			COUserDetailsDTO cOUserDetailsDTO = null;
			try
			{
				if (COUserDetailsHelperService.isAuthenticated())
				{
					cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
				}
				else
				{
					cOUserDetailsDTO = (COUserDetailsDTO)RequestContextHolder.getRequestAttributes().getAttribute("tmpLgnMap", RequestAttributes.SCOPE_SESSION);
				}
				if(cOUserDetailsDTO == null)
				{
					//세션이 끊겼을때
					mpaUserDto.setRespCd("40");
				}
				else
				{
					mpaUserDto.setId( cOUserDetailsDTO.getId() );
					String rtnCode = cOUserLgnService.setPwdChng(mpaUserDto);
					if ("00".equals(rtnCode))
					{
						session.invalidate();
					}
					mpaUserDto.setRespCd(rtnCode);
				}
				mpaUserDto.setPwd("");
				mpaUserDto.setPassword("");
				mpaUserDto.setNewPassword("");
				mpaUserDto.setPassword("");
			}
			catch (Exception e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
				throw new Exception(e.getMessage());
			}
			return mpaUserDto;
		}

		/**
		 * 임시 또는 최초 비밀번호를 변경한다.
		 */
		@PostMapping("/upd-password")
		public MPAUserDto setPwdChngUpd(MPAUserDto mpaUserDto) throws Exception
		{
			try
			{
				String rtnCode = cOUserLgnService.setPwdChng(mpaUserDto);
				mpaUserDto.setRespCd(rtnCode);
				mpaUserDto.setPwd("");
				mpaUserDto.setPassword("");
				mpaUserDto.setNewPassword("");
				mpaUserDto.setPassword("");
			}
			catch (Exception e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
				throw new Exception(e.getMessage());
			}
			return mpaUserDto;
		}

		/**
		 * 다음에 비밀번호 변경하기
		 */
		@PostMapping("/change-password-next")
		public MPAUserDto setPwdNextChng(MPAUserDto mpaUserDto,HttpSession session) throws Exception
		{
			COUserDetailsDTO cOUserDetailsDTO = null;
			try
			{
				if (COUserDetailsHelperService.isAuthenticated())
				{
					cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
				}
				else
				{
					cOUserDetailsDTO = (COUserDetailsDTO)RequestContextHolder.getRequestAttributes().getAttribute("tmpLgnMap", RequestAttributes.SCOPE_SESSION);
				}
				if(cOUserDetailsDTO == null)
				{
					//세션이 끊겼을때
					mpaUserDto.setRespCd("40");
				}
				else
				{
					mpaUserDto.setId( cOUserDetailsDTO.getId() );
					String rtnCode = cOUserLgnService.setPwdNextChng(mpaUserDto);
					if ("00".equals(rtnCode))
					{
						session.invalidate();
					}
					mpaUserDto.setRespCd(rtnCode);
				}
				mpaUserDto.setPwd("");
				mpaUserDto.setPassword("");
				mpaUserDto.setNewPassword("");
				mpaUserDto.setPassword("");
			}
			catch (Exception e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
				throw new Exception(e.getMessage());
			}
			return mpaUserDto;
		}

	}
}