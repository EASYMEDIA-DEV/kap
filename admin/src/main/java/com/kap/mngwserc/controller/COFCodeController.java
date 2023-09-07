package com.kap.mngwserc.controller;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COCodeDTO;
import com.kap.service.COFCodeService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <pre>
 * 코드 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: COFCodeController.java
 * @Description		: 코드 관리를 위한 Controller
 * @author 허진영
 * @since 2021.04.26
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2021.04.26		허진영					최초생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/co/cof")
public class COFCodeController {

    private final COFCodeService cOFCodeService;

	/**
	 * 코드 관리 페이지
	 */
    @GetMapping(value="/list")
	public String getCodePage(COCodeDTO cOCodeDTO, ModelMap modelMap) throws Exception
	{
    	try
    	{
    		modelMap.addAttribute("rtnData", cOCodeDTO);
    	}
    	catch (Exception e)
		{
			if (log.isErrorEnabled())
			{
				log.debug(e.getMessage());
            }
			throw new Exception(e.getMessage());
		}
    	return "mngwserc/co/cof/COFCodeWrite.admin";
	}
	/**
	 * <pre>
	 * 코드 관리 API Controller
	 * </pre>
	 *
	 * @ClassName		: COFCodeRestController.java
	 * @Description		: 코드 관리 API Controller
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
	@RequestMapping("/mngwserc/co/cof")
	public class COFCodeRestController {
		/**
		 * 코드 조회
		 */
		@PostMapping(value="/select")
		public String selectCodeList(COCodeDTO cOCodeDTO, HttpServletResponse response) throws Exception
		{
			response.setContentType("text/html;charset=UTF-8");
			List<COCodeDTO> codeList = null;
			JSONArray jSONArray = null;
			try
			{
				codeList = cOFCodeService.getCodeList(cOCodeDTO);
				int startNum = 0, paramSeq = cOCodeDTO.getCdSeq();
				jSONArray = cOFCodeService.getJsonData(codeList, startNum, paramSeq);
			}
			catch (Exception e)
			{
				if (log.isDebugEnabled())
				{
					log.error(e.getMessage());
				}
				throw new Exception(e.getMessage());
			}
			return jSONArray.toString();
		}

		/**
		 * 코드 상세정보를 조회한다.
		 */
		@GetMapping(value="/details")
		public COCodeDTO selectCodeDtl(COCodeDTO cOCodeDTO) throws Exception
		{
			COCodeDTO rtnCodeDto = null;
			try
			{
				rtnCodeDto = cOFCodeService.selectCodeDtl(cOCodeDTO);
			}
			catch (Exception e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
				throw new Exception(e.getMessage());
			}
			return rtnCodeDto;
		}

		/**
		 * 코드를 등록한다.
		 */
		@PostMapping(value="/insert")
		public int insertCode(COCodeDTO cOCodeDTO) throws Exception
		{
			int actCnt = 0;
			try
			{
				COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
				cOCodeDTO.setRegId( coaAdmDTO.getId() );
				cOCodeDTO.setRegIp( coaAdmDTO.getLoginIp() );
				cOCodeDTO.setModId( coaAdmDTO.getId() );
				cOCodeDTO.setModIp( coaAdmDTO.getLoginIp() );
				actCnt = cOFCodeService.insertCode(cOCodeDTO);
			}
			catch (Exception e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
				throw new Exception(e.getMessage());
			}
			return actCnt;
		}

		/**
		 * 코드명을 수정한다.
		 */
		@PostMapping(value="/name-update")
		public int updateCodeNm(COCodeDTO cOCodeDTO) throws Exception
		{
			int actCnt = 0;
			try
			{
				COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
				cOCodeDTO.setRegId( coaAdmDTO.getId() );
				cOCodeDTO.setRegIp( coaAdmDTO.getLoginIp() );
				cOCodeDTO.setModId( coaAdmDTO.getId() );
				cOCodeDTO.setModIp( coaAdmDTO.getLoginIp() );
				actCnt = cOFCodeService.updateCodeNm(cOCodeDTO);
			}
			catch (Exception e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
				throw new Exception(e.getMessage());
			}
			return actCnt;
		}

		/**
		 * 코드 정보를 수정한다.
		 */
		@PostMapping(value="/info-update")
		public int updateCodeInf(COCodeDTO cOCodeDTO) throws Exception
		{
			int actCnt = 0;
			try
			{
				System.out.println(  cOCodeDTO.toString());
				COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
				cOCodeDTO.setRegId( coaAdmDTO.getId() );
				cOCodeDTO.setRegIp( coaAdmDTO.getLoginIp() );
				cOCodeDTO.setModId( coaAdmDTO.getId() );
				cOCodeDTO.setModIp( coaAdmDTO.getLoginIp() );
				actCnt = cOFCodeService.updateCodeInf(cOCodeDTO);
			}
			catch (Exception e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
				throw new Exception(e.getMessage());
			}
			return actCnt;
		}

		/**
		 * 코드를 이동한다.
		 */
		@PostMapping(value="/pstn-update")
		public int updateCodePstn(COCodeDTO cOCodeDTO) throws Exception
		{
			int actCnt = 0;
			try
			{
				COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
				cOCodeDTO.setRegId( coaAdmDTO.getId() );
				cOCodeDTO.setRegIp( coaAdmDTO.getLoginIp() );
				cOCodeDTO.setModId( coaAdmDTO.getId() );
				cOCodeDTO.setModIp( coaAdmDTO.getLoginIp() );
				actCnt = cOFCodeService.updateCodePstn(cOCodeDTO);
			}
			catch (Exception e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
				throw new Exception(e.getMessage());
			}
			return actCnt;
		}

		/**
		 * 코드를 삭제한다.
		 */
		@PostMapping(value="/delete")
		public int deleteCode(COCodeDTO cOCodeDTO) throws Exception
		{
			int actCnt = 0;
			try
			{
				COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
				cOCodeDTO.setRegId( coaAdmDTO.getId() );
				cOCodeDTO.setRegIp( coaAdmDTO.getLoginIp() );
				cOCodeDTO.setModId( coaAdmDTO.getId() );
				cOCodeDTO.setModIp( coaAdmDTO.getLoginIp() );
				actCnt = cOFCodeService.deleteCode(cOCodeDTO);
			}
			catch (Exception e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
				throw new Exception(e.getMessage());
			}
			return actCnt;
		}
	}
}