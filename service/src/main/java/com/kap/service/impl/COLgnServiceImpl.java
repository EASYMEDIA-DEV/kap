package com.kap.service.impl;

import com.kap.common.utility.CODateUtil;
import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.seed.COSeedCipherUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COLoginDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.service.COLgnService;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.COLgnMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 로그인/로그아웃을 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: COBLgnServiceImpl.java
 * @Description		: 로그인/로그아웃을 위한 ServiceImpl
 * @author 박주석
 * @since 2022.01.13
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				   description
 *   ===========    ==============    =============================
 *   2022.01.13			박주석			 		최초생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COLgnServiceImpl  implements COLgnService {

	//DAO
    private final COLgnMapper cOLgnMapper;

	//로그인 상태값 시스템 등록
   	private final COSystemLogService cOSystemLogService;

    /**
	 * 로그인을 처리한다.
	 */
    public COLoginDTO actionLogin(COLoginDTO cOLoginDTO, HttpServletRequest request) throws Exception
    {
		// log4j2에 사용할 커스텀 map 초기화
		ThreadContext.clearAll();
		// log4j2에 사용할 커스텀 map 생성
		ThreadContext.put("regId", cOLoginDTO.getId());
		ThreadContext.put("regIp", CONetworkUtil.getMyIPaddress(request));
		ThreadContext.put("trgtMenuNm", "로그인");

		COAAdmDTO rtnCOAAdmDTO = cOLgnMapper.getLoginInfo(cOLoginDTO);

    	if (rtnCOAAdmDTO != null)
    	{
    		// 차단여부 확인
			if (!"Y".equals(rtnCOAAdmDTO.getUseYn()))
			{
				cOLoginDTO.setRespCd("1090");
    		}
			else
			{
				// 암호화 비밀번호
				log.error(COSeedCipherUtil.encryptPassword(cOLoginDTO.getPassword(), cOLoginDTO.getId()));
				rtnCOAAdmDTO.setPasswordConfirm(COSeedCipherUtil.encryptPassword(cOLoginDTO.getPassword(), cOLoginDTO.getId()));
				// 로그인 오류 횟수 확인
				if (rtnCOAAdmDTO.getLgnFailCnt() < 5)
				{
					// 비밀번호 확인
					if (!rtnCOAAdmDTO.getPwd().equals(rtnCOAAdmDTO.getPasswordConfirm()))
					{
						// 로그인 오류 횟수 증가
						cOLgnMapper.updateLgnFailCntIncrs(rtnCOAAdmDTO);
						cOLoginDTO.setRespCd("1190");
					}
					// 임시 비밀번호 확인
					else if ("Y".equals(rtnCOAAdmDTO.getTmpPwdYn()))
					{
						cOLoginDTO.setRespCd("1210");
					}
					// 첫 로그인 확인
					else if ("".equals(rtnCOAAdmDTO.getLastLgnDtm()) || rtnCOAAdmDTO.getLastLgnDtm() == null)
					{
						cOLoginDTO.setRespCd("1310");
					}
					else
					{
						//IP 체크
						String allwIp = rtnCOAAdmDTO.getAllwIp();
						String[] allwIpArr = null;
						String clientIp = CONetworkUtil.getMyIPaddress(request);
						boolean isAllw = false;
						if(allwIp != null && !"".equals(allwIp)){
							allwIpArr = allwIp.split(",");
							for (int i = 0, len = allwIpArr.length; i < len; i++)
							{
								if (clientIp.equals(allwIpArr[i].trim()))
								{
									isAllw = true;
									break;
								}
							}
						}
						// 비밀번호 변경주기(3개월) 확인
						String today = CODateUtil.getToday();
						String pwdChngDtm = CODateUtil.convertDate(rtnCOAAdmDTO.getPwdChngDtm(), "yyyy-MM-dd HH:mm:ss", "yyyyMMdd", "");
						if(allwIp != null && !"".equals(allwIp) && !isAllw)
						{
							//IP 제한
							cOLoginDTO.setRespCd("1420");
						}
						else if (CODateUtil.getDaysDiff(CODateUtil.addYearMonthDay(pwdChngDtm, 0, 0, 90), today) > 0)
						{
							//비밀번호 90일 변경
							cOLoginDTO.setRespCd("1410");
						}
						// 접근가능 메뉴 확인
						else
						{
							//드라이브 메뉴 목록 조회
							List<COMenuDTO> driveMenuList = cOLgnMapper.getDriveMenuList(rtnCOAAdmDTO);
							int driveMenuSeq = driveMenuList.get(0).getMenuSeq();
							RequestContextHolder.getRequestAttributes().setAttribute("driveMenuList", driveMenuList, RequestAttributes.SCOPE_SESSION);
							RequestContextHolder.getRequestAttributes().setAttribute("driveMenuSeq", driveMenuSeq, RequestAttributes.SCOPE_SESSION);

							// 로그인 시간을 업데이트 해준다.
							rtnCOAAdmDTO.setDriveMenuSeq( driveMenuSeq );
							List<COMenuDTO> menuList = cOLgnMapper.getMenuList(rtnCOAAdmDTO);
							// 관리자 메인으로 사용할 메뉴 url
							String admMainUrl = "not";
		        			String admUrl = "";
		        			// 관리자 메인으로 사용할 메뉴 접근권한
		        			boolean admMainAuth = false;
		    				for (int i = 0, size = menuList.size(); i < size; i++)
		        			{
		        				if ("".equals(admUrl) && !"".equals(COStringUtil.nullConvert(menuList.get(i).getAdmUrl())))
		        				{
		        					admUrl = menuList.get(i).getAdmUrl();
		        				}
		        				if (admMainUrl.equals(menuList.get(i).getAdmUrl()))
		        				{
		        					admMainAuth = true;
		        					break;
		        				}
		        			}
		        			if ("".equals(admUrl))
		        			{
								cOLoginDTO.setRespCd("2090");
		        			}
		        			else if (admMainAuth)
		        			{
								cOLoginDTO.setRdctUrl(admMainUrl);
		        			}
		        			else
		        			{
								cOLoginDTO.setRdctUrl(admUrl);
		        			}
						}
					}
				}
				else
				{
					cOLoginDTO.setRespCd("1191");
				}
			}
			//로그인 성공
			if ("".equals(COStringUtil.nullConvert(cOLoginDTO.getRespCd())))
			{
				cOLoginDTO.setRespCd("0000");
				cOLoginDTO.setLgnCrtfnYn(rtnCOAAdmDTO.getLgnCrtfnYn());
			}
			String rtnCode = cOLoginDTO.getRespCd(), loginIp = CONetworkUtil.getMyIPaddress(request), loginPrcsCd = "";
			//로그인 로그 객체
			COSystemLogDTO cOSystemLogDTO = new COSystemLogDTO();
			if (rtnCode.endsWith("00") || rtnCode.endsWith("10"))
			{
				COAAdmDTO lgnCOAAdmDTO = cOLgnMapper.actionLogin(rtnCOAAdmDTO);
				// 보안 처리 (로그인 세션 변경)
				request.getSession().invalidate();
        		/// 로그인 세션을 생성한다.
        		if (rtnCode.endsWith("00"))
        		{
    				// 로그인 IP set
					lgnCOAAdmDTO.setLoginIp(loginIp);
					lgnCOAAdmDTO.setConSessionId(RequestContextHolder.getRequestAttributes().getSessionId());
    				// 로그인 처리코드 set
    				loginPrcsCd = "LI";
        			cOLgnMapper.updateLastLgnDtm(lgnCOAAdmDTO);
					lgnCOAAdmDTO.setRdctUrl( cOLoginDTO.getRdctUrl() );
        			RequestContextHolder.getRequestAttributes().setAttribute("tmpLgnMap", lgnCOAAdmDTO, RequestAttributes.SCOPE_SESSION);
        		}
        		// 임시 로그인 세션을 생성한다.
        		else
        		{
					COAAdmDTO tmpLgnMap = COAAdmDTO.builder().build();
					tmpLgnMap.setId(lgnCOAAdmDTO.getId());
					tmpLgnMap.setRespCd( cOLoginDTO.getRespCd() );
        			RequestContextHolder.getRequestAttributes().setAttribute("tmpLgnMap", tmpLgnMap, RequestAttributes.SCOPE_SESSION);
        		}

        		// 로그인 오류 횟수를 초기화한다.
        		cOLgnMapper.updateLgnFailCntInit(lgnCOAAdmDTO);
			}
			else
			{
				// 로그인실패 처리코드 set
				if("1191".equals( cOLoginDTO.getRespCd() )){
					//5회 실패
					loginPrcsCd = "LF5";
				}
				else
				{
					//로그인 실패
					loginPrcsCd = "LF";
				}
			}

			if (!"".equals(loginPrcsCd))
			{
				cOSystemLogDTO.setTrgtMenuNm("로그인 페이지");
				cOSystemLogDTO.setSrvcNm("mngwserc.co.cob.service.impl.COBLgnServiceImpl");
				cOSystemLogDTO.setFncNm("actionLogin");
				cOSystemLogDTO.setPrcsCd(loginPrcsCd);
				cOSystemLogDTO.setRegId(rtnCOAAdmDTO.getId());
				cOSystemLogDTO.setRegIp(loginIp);
				cOSystemLogService.logInsertSysLog(cOSystemLogDTO);
			}
    	}
    	else
    	{
			//ID가 없다.
			cOLoginDTO.setRespCd("9999");
    	}
    	return cOLoginDTO;
    }

    /**
	 * 일반 로그아웃을 처리한다.
	 */
    public void actionLogout(COAAdmDTO cOAAdmDTO, HttpServletRequest request) throws Exception
    {
		COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
		//로그아웃 로그 객체
		COSystemLogDTO cOSystemLogDTO = new COSystemLogDTO();
		cOSystemLogDTO.setTrgtMenuNm("로그아웃 페이지");
		cOSystemLogDTO.setSrvcNm("mngwserc.co.cob.service.impl.COBLgnServiceImpl");
		cOSystemLogDTO.setFncNm("actionLogout");
		cOSystemLogDTO.setPrcsCd("LO");
		cOSystemLogDTO.setRegId(lgnCOAAdmDTO.getId());
		cOSystemLogDTO.setRegIp(CONetworkUtil.getMyIPaddress(request));
    	cOSystemLogService.logInsertSysLog(cOSystemLogDTO);
    }

    /**
     * 비밀번호를 변경한다.
     */
    public String setPwdChng(COAAdmDTO cOAAdmDTO) throws Exception
    {
		String rtnCode = "";
		COLoginDTO cOLoginDTO = new COLoginDTO();
		cOLoginDTO.setId( cOAAdmDTO.getId() );
		COAAdmDTO rtnCOAAdmDTO = cOLgnMapper.getLoginInfo(cOLoginDTO);
		if (rtnCOAAdmDTO != null)
		{
			// 암호화 비밀번호
			rtnCOAAdmDTO.setNowEncPwd(COSeedCipherUtil.encryptPassword(cOAAdmDTO.getPassword(), cOAAdmDTO.getId()));
			rtnCOAAdmDTO.setNewEncPwd(COSeedCipherUtil.encryptPassword(cOAAdmDTO.getNewPassword(), cOAAdmDTO.getId()));

			List<String> pwdHistoryList = cOLgnMapper.getPwdHistoryCheck(rtnCOAAdmDTO);
    		// 현재 비밀번호 확인
    		if (!rtnCOAAdmDTO.getPwd().equals(rtnCOAAdmDTO.getNowEncPwd()))
        	{
        		rtnCode = "10";
        	}
			// 신규 비밀번호 확인
	    	else if ("".equals(cOAAdmDTO.getNewPassword()) || ( !cOAAdmDTO.getNewPassword().equals(cOAAdmDTO.getPasswordConfirm())) )
	    	{
	    		rtnCode = "20";
	    	}
	    	// 기존 비밀번호 사용여부 확인
			else if(pwdHistoryList != null && pwdHistoryList.size() > 0)
	    	{
	    		rtnCode = "30";
	    	}

			if ("".equals(rtnCode))
	    	{
	    		int actCnt = cOLgnMapper.updatePwdChng(rtnCOAAdmDTO);

				// 비밀번호 이력도 업데이트
				setPwdHistory(rtnCOAAdmDTO);
	    		if (actCnt > 0)
	    		{
	    			rtnCode = "00";
	    		}
	    	}
		}
    	return rtnCode;
    }

	/**
	 * 비밀번호 이력 업데이트한다.
	 */
	public int setPwdHistory(COAAdmDTO cOAAdmDTO) throws Exception{

		String newPwd = cOAAdmDTO.getNewEncPwd();
		cOAAdmDTO.setNewEncPwd("");
		cOAAdmDTO.setRegId(cOAAdmDTO.getId());
		List<String> pwdList = cOLgnMapper.getPwdHistoryCheck(cOAAdmDTO);

		if(pwdList != null && pwdList.size() >= 5){
			// 비밀번호 저장 이력이 5개 이므로 초기값 삭제
			List<String> tmpPwdList = new ArrayList<>();
			tmpPwdList.add(pwdList.get(0));
			cOAAdmDTO.setPwdList(tmpPwdList);
			cOLgnMapper.deletePwdHistory(cOAAdmDTO);

			pwdList.remove(0);
			cOAAdmDTO.setPwdList(pwdList);
			cOLgnMapper.updatePwdHistory(cOAAdmDTO);
		}

		cOAAdmDTO.setNewEncPwd(newPwd);
		return cOLgnMapper.insertPwdHistory(cOAAdmDTO);
	}

	/**
	 * 드라이브 메뉴 목록을 조회한다.
	 */
	public List<COMenuDTO> getDriveMenuList(COAAdmDTO cOAAdmDTO) throws Exception
	{
		return cOLgnMapper.getDriveMenuList(cOAAdmDTO);
	}

  	/**
	 * 로그인 처리에 따른 메뉴를 가져온다.
	 */
    public List<COMenuDTO> getMenuList(COAAdmDTO cOAAdmDTO) throws Exception
    {
		/*cOAAdmDTO.setUserMenuList( cOLgnMapper.getUserMenuList() );*/
    	return cOLgnMapper.getMenuList(cOAAdmDTO);
    }

	/**
	 * CMS Root 메뉴 정보를 가져온다.
	 */
	public COMenuDTO getCmsRootInf(COAAdmDTO cOAAdmDTO) throws Exception
	{
		return cOLgnMapper.getCmsRootInf(cOAAdmDTO);
	}
}