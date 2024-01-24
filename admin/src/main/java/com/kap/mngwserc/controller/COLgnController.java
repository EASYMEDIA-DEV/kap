package com.kap.mngwserc.controller;

import com.kap.common.utility.CODateUtil;
import com.kap.common.utility.CONetworkUtil;
import com.kap.core.dto.*;
import com.kap.service.COLgnService;
import com.kap.service.COMessageService;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
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
import java.util.Random;

/**
 * <pre>
 * 관리자 로그인/로그아웃을 위한 Controller
 * </pre>
 *
 * @ClassName		: COBLgnController.java
 * @Description		: 관리자 로그인/로그아웃을 위한 Controller
 * @author 허진영
 * @since 2020.10.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				   description
 *   ===========    ==============    =============================
 *   2020.10.20			허진영			 		최초생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class COLgnController {
	//로그인 서비스
    private final COLgnService cOLgnService;
	//이메일 발송
	private final COMessageService cOMessageService;

	//이메일 발송
	private final COSystemLogService cOSystemLogService;

	/**
	 * ROOT 페이지
	 */
	@GetMapping("/")
	public String getRootPage(ModelMap modelMap, HttpSession session) throws Exception
	{
		try
		{
			// 보안 처리(로그인 세션 변경)
			//session.invalidate();
		}
		catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return "mngwserc/COIndex";
	}

	/**
	 * 로그인 페이지
	 */
    @GetMapping("/mngwsercgateway/login")
	public String getLoginPage(ModelMap modelMap, HttpSession session) throws Exception
    {
    	try
		{
			// 보안 처리(로그인 세션 변경)
    		session.invalidate();
		}
    	catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
    	return "mngwserc/co/COLgn";
	}

	/**
	 * 로그인 이메일 인증 페이지
	 */
	@GetMapping("/mngwsercgateway/email")
	public String getLoginEmailAuthPage(ModelMap modelMap, HttpSession session) throws Exception
	{
		String rtnUrl = "";
		COUserDetailsDTO lgnCOAAdmDTO = null;
		String authNum = "";
		try
		{
			if(
					RequestContextHolder.getRequestAttributes().getAttribute("tmpEmailAuthNum", RequestAttributes.SCOPE_SESSION) != null
					&& RequestContextHolder.getRequestAttributes().getAttribute("tmpEmailLgnMap", RequestAttributes.SCOPE_SESSION) != null
			)
			{
				lgnCOAAdmDTO = (COUserDetailsDTO)RequestContextHolder.getRequestAttributes().getAttribute("tmpEmailLgnMap", RequestAttributes.SCOPE_SESSION);
				authNum      = String.valueOf( RequestContextHolder.getRequestAttributes().getAttribute("tmpEmailAuthNum", RequestAttributes.SCOPE_SESSION) );
				rtnUrl = "mngwserc/co/COLgnEmail";
			}
			else
			{
				rtnUrl = "redirect:/";
			}
			// 보안 처리(로그인 세션 변경)
			session.invalidate();
			RequestContextHolder.getRequestAttributes().setAttribute("tmpEmailLgnMap", lgnCOAAdmDTO, RequestAttributes.SCOPE_SESSION);
			RequestContextHolder.getRequestAttributes().setAttribute("tmpEmailAuthNum", authNum, RequestAttributes.SCOPE_SESSION);
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
	 * 로그아웃을 처리한다.
	 */
    @RequestMapping(value="/mngwserc/**/logout", method=RequestMethod.GET)
    public String actionLogout(COAAdmDTO cOAAdmDTO, ModelMap modelMap, HttpSession sesssion, HttpServletRequest request) throws Exception
    {
    	try
        {
    		if (COUserDetailsHelperService.isAuthenticated())
    		{
    			cOLgnService.actionLogout(cOAAdmDTO, request);
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
    	return "redirect:/mngwsercgateway/login";
    }

    /**
   	 * 비밀번호변경 페이지
   	 */
   	@RequestMapping(value="/mngwsercgateway/change-password", method=RequestMethod.GET)
   	public String getPasswordChnagePage(COAAdmDTO cOAAdmDTO, ModelMap modelMap) throws Exception
   	{
		String rtnUrl = "mngwserc/co/COPwdChngWrite";
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
	 * 관리자 다국어 처리
	 */
	@RequestMapping(value="/mngwserc/drive/{driveMenuSeq}", method= RequestMethod.GET)
	public String getDriveMenuSeq(ModelMap modelMap, @PathVariable int driveMenuSeq) throws Exception
	{
		String url = "";
		try
		{
			RequestContextHolder.getRequestAttributes().setAttribute("driveMenuSeq", driveMenuSeq, RequestAttributes.SCOPE_SESSION);
			COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
			cOUserDetailsDTO.setDriveMenuSeq( driveMenuSeq );
			List<COMenuDTO> menuList = cOLgnService.getMenuList(cOUserDetailsDTO);
			RequestContextHolder.getRequestAttributes().setAttribute("menuList", menuList, RequestAttributes.SCOPE_SESSION);
			for (int i = 0, size = menuList.size(); i < size; i++)
			{
				if(!"".equals(menuList.get(i).getAdmUrl())){
					url = menuList.get(i).getAdmUrl();
					break;
				}
			}
		}
		catch (Exception e)
		{
			if (log.isErrorEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}
		return "redirect:" + url;
	}

	/**
	 * <pre>
	 * 로그인/로그아웃을 위한 API Controller
	 * </pre>
	 *
	 * @ClassName		: COLgnRestController.java
	 * @Description		: 로그인/로그아웃을 위한 API Controller
	 * @author 허진영
	 * @since 2020.10.20
	 * @version 1.0
	 * @see
	 * @Modification Information
	 * <pre>
	 * 		since			author				   description
	 *   ===========    ==============    =============================
	 *   2020.10.20			허진영			 		최초생성
	 * </pre>
	 */
	@RestController
	public class COLgnRestController {

		//사이트 명
		@Value("${app.site.name}")
		private String siteName;
		//사이트 웹 소스 URL
		@Value("${app.user-domain}")
		private String httpFrontUrl;
		//사이트 관리자 URL
		@Value("${app.admin-domain}")
		private String httpAdmtUrl;

		//서버 유무(개발,운영)
		//spring.config.activate.on-profile
		@Value("${spring.config.activate.on-profile}")
		private String serverProfile;


		/**
		 * 로그인을 처리한다.
		 */
		@PostMapping("/mngwsercgateway/login")
		public COLoginDTO actionLogin(COLoginDTO cOLoginDTO, HttpServletRequest request) throws Exception
		{
			COLoginDTO rtnCOLoginDTO = null;
			try
			{
				rtnCOLoginDTO = cOLgnService.actionLogin(cOLoginDTO, request);
				COUserDetailsDTO tmpCOUserDetailsDTO  = (COUserDetailsDTO)RequestContextHolder.getRequestAttributes().getAttribute("tmpLgnMap", RequestAttributes.SCOPE_SESSION);
				if("0000".equals(rtnCOLoginDTO.getRespCd()) && "Y".equals(rtnCOLoginDTO.getLgnCrtfnYn())){

					//이메일 발송
					COMailDTO cOMailDTO = new COMailDTO();
					cOMailDTO.setSubject("["+siteName+"] 인증번호 안내");
					//수신자 정보
					COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
					//이메일
					receiverDto.setEmail(tmpCOUserDetailsDTO.getEmail());
					//이름
					receiverDto.setName("");

					Random random = new Random();
					// 난수 5자리 고정
					String authNum = "";
					for(int i = 0; i < 5; i++){
						authNum += String.valueOf(random.nextInt(10));
					}
					//인증요청일시
					String field2 = CODateUtil.convertDate(CODateUtil.getToday("yyyyMMddHHmm"),"yyyyMMddHHmm", "yyyy-MM-dd HH:mm", "");
					//치환문자1
					receiverDto.setNote1(authNum);
					//치환문자2
					receiverDto.setNote2(field2);
					//수신자 정보 등록
					cOMailDTO.getReceiver().add(receiverDto);
					cOMessageService.sendMail(cOMailDTO, "COAAdmLgnEmail.html");

					//인증번호 로그 등록
					COSystemLogDTO cOSystemLogDTO = new COSystemLogDTO();
					cOSystemLogDTO.setAdmSeq(tmpCOUserDetailsDTO.getSeq());

					cOSystemLogDTO.setCrtfnNo(Integer.parseInt(authNum));
					cOSystemLogDTO.setRegId(tmpCOUserDetailsDTO.getId());
					cOSystemLogDTO.setRegIp(CONetworkUtil.getMyIPaddress(request));

					cOSystemLogService.logInsertCrtfnNo(cOSystemLogDTO);


					RequestContextHolder.getRequestAttributes().setAttribute("tmpEmailLgnMap", tmpCOUserDetailsDTO, RequestAttributes.SCOPE_SESSION);
					RequestContextHolder.getRequestAttributes().setAttribute("tmpEmailAuthNum", authNum, RequestAttributes.SCOPE_SESSION);
				}else{
					// 로그인 세션 생성
					RequestContextHolder.getRequestAttributes().setAttribute("loginMap", tmpCOUserDetailsDTO, RequestAttributes.SCOPE_SESSION);
					rtnCOLoginDTO.setLgnCrtfnPassYn("Y");
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
			return rtnCOLoginDTO;
		}
		/**
		 * 임시 또는 최초 비밀번호를 변경한다.
		 */
		@PostMapping("/mngwsercgateway/change-password")
		public COAAdmDTO setPwdChng(COAAdmDTO cOAAdmDTO, ModelMap modelMap, HttpSession session) throws Exception
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
					cOAAdmDTO.setRespCd("40");
				}
				else
				{
					cOAAdmDTO.setId( cOUserDetailsDTO.getId() );
					String rtnCode = cOLgnService.setPwdChng(cOAAdmDTO);
					if ("00".equals(rtnCode))
					{
						session.invalidate();
					}
					cOAAdmDTO.setRespCd(rtnCode);
				}
				cOAAdmDTO.setPwd("");
				cOAAdmDTO.setPassword("");
				cOAAdmDTO.setNewPassword("");
				cOAAdmDTO.setPassword("");
			}
			catch (Exception e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
				throw new Exception(e.getMessage());
			}
			return cOAAdmDTO;
		}

		/**
		 * 로그인 이메일 인증 진행
		 */
		@PostMapping("/mngwsercgateway/email-auth")
		public COLoginDTO getLoginEmailAuthConfirm(COLoginDTO cOLoginDTO, ModelMap modelMap, HttpSession session) throws Exception
		{
			COUserDetailsDTO lgnCOAAdmDTO = null;
			String authNum = "";
			try
			{
				if(
						  RequestContextHolder.getRequestAttributes().getAttribute("tmpEmailLgnMap", RequestAttributes.SCOPE_SESSION) != null
						&& RequestContextHolder.getRequestAttributes().getAttribute("tmpEmailAuthNum", RequestAttributes.SCOPE_SESSION) != null
				 )
				{
					authNum = String.valueOf( RequestContextHolder.getRequestAttributes().getAttribute("tmpEmailAuthNum", RequestAttributes.SCOPE_SESSION) );
					if(authNum.equals( cOLoginDTO.getEmailAuthNum() )){
						cOLoginDTO.setRespCd("0000");
						lgnCOAAdmDTO = (COUserDetailsDTO)RequestContextHolder.getRequestAttributes().getAttribute("tmpEmailLgnMap", RequestAttributes.SCOPE_SESSION);
						session.invalidate();
						cOLoginDTO.setRdctUrl( lgnCOAAdmDTO.getRdctUrl() );
						RequestContextHolder.getRequestAttributes().setAttribute("loginMap", lgnCOAAdmDTO, RequestAttributes.SCOPE_SESSION);

					}
					else{
						cOLoginDTO.setRespCd("9999");
					}
				}
				else
				{
					cOLoginDTO.setRespCd("9999");
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
			return cOLoginDTO;
		}
	}
}