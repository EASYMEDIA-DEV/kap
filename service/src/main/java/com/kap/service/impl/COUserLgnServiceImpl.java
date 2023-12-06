package com.kap.service.impl;

import com.kap.common.utility.CODateUtil;
import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.seed.COSeedCipherUtil;
import com.kap.core.dto.*;
import com.kap.service.COLgnService;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.COUserLgnService;
import com.kap.service.dao.COLgnMapper;
import com.kap.service.dao.COUserLgnMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 로그인/로그아웃을 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: COUserLgnServiceImpl.java
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
public class COUserLgnServiceImpl  implements COUserLgnService {

	//DAO
    private final COUserLgnMapper cOUserLgnMapper;

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

		COUserDto rtnCOUserDto = cOUserLgnMapper.getLoginInfo(cOLoginDTO);

		if (rtnCOUserDto != null)
    	{
    		// 차단여부 확인
			if (!"Y".equals(rtnCOUserDto.getWthdrwYn()))
			{
				cOLoginDTO.setRespCd("1090");
    		}
			else
			{
				// 암호화 비밀번호
				log.error(COSeedCipherUtil.encryptPassword(cOLoginDTO.getPassword(), cOLoginDTO.getId()));
				rtnCOUserDto.setPasswordConfirm(COSeedCipherUtil.encryptPassword(cOLoginDTO.getPassword(), cOLoginDTO.getId()));

				//정보 업데이트 TODO 양현우 오픈일 수정 가능
				int dateResult = 0;
				if(!"".equals(rtnCOUserDto.getLastLgnDtm()) && rtnCOUserDto.getLastLgnDtm() != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

					try {
						Date date1 = dateFormat.parse(rtnCOUserDto.getLastLgnDtm());
						Date date2 = dateFormat2.parse("2024-03-08");
						dateResult = date1.compareTo(date2);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				LocalDate currentDate = LocalDate.now();
				LocalDate targetDate = LocalDate.parse("2024-03-08");
				if(currentDate.isBefore(targetDate)) {
					dateResult = 0;
				}
				//로그인 5회 체크
				if (rtnCOUserDto.getLgnFailCnt() < 5)
				{
					// 비밀번호 확인
					if (!rtnCOUserDto.getPwd().equals(rtnCOUserDto.getPasswordConfirm()))
					{
						// 로그인 오류 횟수 증가
						cOUserLgnMapper.updateLgnFailCntIncrs(rtnCOUserDto);
						cOLoginDTO.setRespCd("1190");
					}
					else if(rtnCOUserDto.getTmpPwdYn()!= null && rtnCOUserDto.getTmpPwdYn().equals("Y")) {

						//비밀번호 임시
						cOLoginDTO.setRespCd("1210");
					}
					else if(dateResult < 0) {

						//오픈일 이후 첫 로그인 정보 업데이트
						cOLoginDTO.setRespCd("1310");
					}
					else
					{
						// 비밀번호 변경주기(3개월) 확인
						String today = CODateUtil.getToday();
						String pwdChngDtm = CODateUtil.convertDate(rtnCOUserDto.getPwdChngDtm(), "yyyy-MM-dd HH:mm:ss", "yyyyMMdd", "");
						 if (CODateUtil.getDaysDiff(CODateUtil.addYearMonthDay(pwdChngDtm, 0, 0, 90), today) > 0)
						{
							if(rtnCOUserDto.getChngXtnsnCnt() >=3) {
								//비밀번호 90일 변경 3회 연장 시
								cOLoginDTO.setRespCd("1510");
							} else {
								//비밀번호 90일 변경
								cOLoginDTO.setRespCd("1410");
							}

						}
						// 접근가능 메뉴 확인
						else
						{
							//드라이브 메뉴 목록 조회
							List<COMenuDTO> driveMenuList = cOUserLgnMapper.getDriveMenuList(rtnCOUserDto);
							int driveMenuSeq = driveMenuList.get(0).getMenuSeq();
							RequestContextHolder.getRequestAttributes().setAttribute("driveMenuList", driveMenuList, RequestAttributes.SCOPE_SESSION);
							RequestContextHolder.getRequestAttributes().setAttribute("driveMenuSeq", driveMenuSeq, RequestAttributes.SCOPE_SESSION);

							// 로그인 시간을 업데이트 해준다.
							COUserDetailsDTO tmpCOUserDetailsDTO = COUserDetailsDTO.builder()
																	.seq(rtnCOUserDto.getMemSeq())
																    .driveMenuSeq(driveMenuSeq)
																	.authCd( rtnCOUserDto.getMemCd() )
																	.build();

//							List<COMenuDTO> menuList = cOLgnMapper.getMenuList(tmpCOUserDetailsDTO);
							// 관리자 메인으로 사용할 메뉴 url
//							String admMainUrl = "not";
//							String admUrl = "";
//							// 관리자 메인으로 사용할 메뉴 접근권한
//							boolean admMainAuth = false;
//							for (int i = 0, size = menuList.size(); i < size; i++)
//							{
//								if ("".equals(admUrl) && !"".equals(COStringUtil.nullConvert(menuList.get(i).getAdmUrl())))
//								{
//									admUrl = menuList.get(i).getAdmUrl();
//								}
//								if (admMainUrl.equals(menuList.get(i).getAdmUrl()))
//								{
//									admMainAuth = true;
//									break;
//								}
//							}
//							if ("".equals(admUrl))
//							{
//								cOLoginDTO.setRespCd("2090");
//							}
//							else if (admMainAuth)
//							{
//								cOLoginDTO.setRdctUrl(admMainUrl);
//							}
//							else
//							{
//								cOLoginDTO.setRdctUrl(admUrl);
//							}
							cOLoginDTO.setRdctUrl("/");
							rtnCOUserDto.setDriveMenuSeq( driveMenuSeq );
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
			}
			String rtnCode = cOLoginDTO.getRespCd(), loginIp = CONetworkUtil.getMyIPaddress(request), loginPrcsCd = "";
//			String rtnCode = cOLoginDTO.getRespCd(), loginIp = CONetworkUtil.getMyIPaddress(request), loginPrcsCd = "";
			//로그인 로그 객체
			COSystemLogDTO cOSystemLogDTO = new COSystemLogDTO();
			//로그인 객체
			COUserDetailsDTO cOUserDetailsDTO = null;
			if (rtnCode.endsWith("00") || rtnCode.endsWith("10"))
			{
				cOUserDetailsDTO = cOUserLgnMapper.actionLogin(rtnCOUserDto);
				// 보안 처리 (로그인 세션 변경)
				request.getSession().invalidate();
        		/// 로그인 세션을 생성한다.
        		if (rtnCode.endsWith("00"))
        		{
    				// 로그인 IP set
					cOUserDetailsDTO.setLoginIp(loginIp);
					cOUserDetailsDTO.setConSessionId(RequestContextHolder.getRequestAttributes().getSessionId());
    				// 로그인 처리코드 set
    				loginPrcsCd = "LI";
					//오픈 날짜 전 로그인 인 경우 마지막 로그인 시간을 본인인증 후에 update 시킨다.
					if(!rtnCode.equals("1310")) {
						cOUserLgnMapper.updateLastLgnDtm(cOUserDetailsDTO);
					}
					cOUserDetailsDTO.setRdctUrl( cOLoginDTO.getRdctUrl() );
        			RequestContextHolder.getRequestAttributes().setAttribute("tmpLgnMap", cOUserDetailsDTO, RequestAttributes.SCOPE_SESSION);
        		}
        		// 임시 로그인 세션을 생성한다.
        		else
        		{
					cOUserDetailsDTO = COUserDetailsDTO.builder().build();
					cOUserDetailsDTO.setId(cOLoginDTO.getId());
					cOUserDetailsDTO.setRespCd( cOLoginDTO.getRespCd() );
        			RequestContextHolder.getRequestAttributes().setAttribute("tmpLgnMap", cOUserDetailsDTO, RequestAttributes.SCOPE_SESSION);
        		}

        		// 로그인 오류 횟수를 초기화한다.
				cOUserLgnMapper.updateLgnFailCntInit(cOUserDetailsDTO);
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
				cOSystemLogDTO.setRegId(rtnCOUserDto.getId());
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
    public void actionLogout(COUserDto cOUserDto, HttpServletRequest request) throws Exception
    {
		COUserDetailsDTO lgnCOAAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();
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
    public String setPwdChng(COUserDto cOUserDto) throws Exception
    {
		String rtnCode = "";
		COLoginDTO cOLoginDTO = new COLoginDTO();
		cOLoginDTO.setId( cOUserDto.getId() );
		COUserDto rtnCOUserDTO = cOUserLgnMapper.getLoginInfo(cOLoginDTO);
		if (rtnCOUserDTO != null)
		{
			// 암호화 비밀번호
			System.out.println(COSeedCipherUtil.encryptPassword(cOUserDto.getPassword(), cOUserDto.getId()));
			rtnCOUserDTO.setNowEncPwd(COSeedCipherUtil.encryptPassword(cOUserDto.getPassword(), cOUserDto.getId()));
			rtnCOUserDTO.setNewEncPwd(COSeedCipherUtil.encryptPassword(cOUserDto.getNewPassword(), cOUserDto.getId()));

    		// 현재 비밀번호 확인
    		if (!rtnCOUserDTO.getPwd().equals(rtnCOUserDTO.getNowEncPwd()))
        	{
        		rtnCode = "10";
        	}
			// 신규 비밀번호 확인
	    	else if ("".equals(cOUserDto.getNewPassword()) || ( !cOUserDto.getNewPassword().equals(cOUserDto.getPasswordConfirm())) )
	    	{
	    		rtnCode = "20";
	    	}

			if ("".equals(rtnCode))
	    	{
	    		int actCnt = cOUserLgnMapper.updatePwdChng(rtnCOUserDTO);

	    		if (actCnt > 0)
	    		{
	    			rtnCode = "00";
	    		}
	    	}
		}
    	return rtnCode;
    }


	/**
	 * 다음에 비밀번호를 변경한다.
	 */
	public String setPwdNextChng(COUserDto cOUserDto) throws Exception
	{
		String rtnCode = "";
		COLoginDTO cOLoginDTO = new COLoginDTO();
		cOLoginDTO.setId( cOUserDto.getId() );
		COUserDto rtnCOUserDTO = cOUserLgnMapper.getLoginInfo(cOLoginDTO);
		if (rtnCOUserDTO != null)
		{
			int actCnt = cOUserLgnMapper.updatePwdNextChng(rtnCOUserDTO);

			if (actCnt > 0)
			{
				rtnCode = "00";
			}
		}
		return rtnCode;
	}

	/**
	 * 드라이브 메뉴 목록을 조회한다.
	 */
	public List<COMenuDTO> getDriveMenuList(COUserDto cOUserDto) throws Exception
	{
		return cOUserLgnMapper.getDriveMenuList(cOUserDto);
	}

  	/**
	 * 로그인 처리에 따른 메뉴를 가져온다.
	 */
    public List<COMenuDTO> getMenuList(COUserDetailsDTO cOUserDetailsDTO) throws Exception
    {
		/*cOAAdmDTO.setUserMenuList( cOLgnMapper.getUserMenuList() );*/
    	return cOUserLgnMapper.getMenuList(cOUserDetailsDTO);
    }

	/**
	 * CMS Root 메뉴 정보를 가져온다.
	 */
	public COMenuDTO getCmsRootInf(COUserDto cOUserDto) throws Exception
	{
		return cOUserLgnMapper.getCmsRootInf(cOUserDto);
	}

	@Override
	public COUserDto getIdFind(COIdFindDto coIdFindDto) throws Exception {

		String replaceBirth = coIdFindDto.getBirthdate().replace("-", "");
		coIdFindDto.setBirthdate(replaceBirth.substring(0,4)+"-"+replaceBirth.substring(4,6)+"-"+replaceBirth.substring(6));
		if(coIdFindDto.getTypes().equals("1")) {
			String replaceHp = coIdFindDto.getMobile_no().replace("-", "");
			coIdFindDto.setMobile_no(replaceHp.substring(0, 3) + "-" + replaceHp.substring(3, 7) + "-" + replaceHp.substring(7));
		}

		return cOUserLgnMapper.getIdFind(coIdFindDto);
	}
}