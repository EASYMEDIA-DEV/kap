<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<spring:eval var="siteName" expression="@environment.getProperty('app.site.name')" />
	<c:set var="pageTitle" value="${siteName}"/>
	<c:if test="${ not empty pageMenuDto }">
		<c:set var="pageTitle" value="${pageMenuDto.menuNm} | ${siteName}"/>
	</c:if>
	<title>${ pageTitle }</title>
	<link rel="shortcut icon" href="/common/images/favicon.ico" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<meta name="keywords" content="${ pageMenuDto.seoKwrd }"/>
	<meta name="description" content="${ pageMenuDto.seoCntn }"/>
	<meta name="X-CSRF-TOKEN" content="${_csrf.token}" />
	<meta property="og:site_name" content="${siteName}" id="og-sitename-value"/>
	<meta property="og:type" content="website" id="og-type-value"/>
	<c:set var="scheme" value="${pageContext.request.scheme}" scope="request"/>
	<c:set var="serverName" value="${pageContext.request.serverName}" scope="request"/>
	<c:set var="servletPath" value="${requestScope['javax.servlet.forward.servlet_path']}" scope="request"/>
	<c:set var="requestUrlOrgn" value="${scheme}://${serverName}${serverPort}${servletPath}" scope="request" />
	<c:set var="siteUrl">${requestUrlOrgn}<c:if test="${not empty strPam}">?${strPam}</c:if></c:set>
	<meta property="og:url" content="${siteUrl}" id="og-url-value"/>
	<meta property="og:title" content="${ pageTitle }" id="og-title-value"/>
	<meta property="og:description" content="${ pageMenuDto.seoCntn }" id="og-description-value"/>
	<c:set var="ogImage">
		<c:choose>
			<c:when test="${fn:contains(siteUrl,'/main')}">${scheme}://${serverName}${serverPort}/common/images/img-main-kv-01.jpg</c:when>
			<c:otherwise>${scheme}://${serverName}${serverPort}/common/images/img-main-kv-03.jpg</c:otherwise>
		</c:choose>
	</c:set>
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<c:set var="curtDt"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd" /></c:set>
	<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" /></c:set>
	<meta property="og:image" content="${ogImage}" id="og-image-value"/>
	<link rel="stylesheet" href="/common/css/swiper.css" />
	<link rel="stylesheet" href="/common/css/fonts.css" />
	<link rel="stylesheet" href="/common/css/common.css?bust=${sysDate}" />
	<link rel="stylesheet" href="/common/css/main.css?bust=${sysDate}" />
	<link rel="stylesheet" href="/common/css/style.css" />
	<link rel="stylesheet" href="/common/js/lib/datetimepicker/jquery.datetimepicker.css" />
	<script type="text/javascript" src="/common/js/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="/common/js/gsap.min.js"></script>
	<script type="text/javascript" src="/common/js/ScrollTrigger.min.js"></script>
	<script type="text/javascript" src="/common/js/Draggable.min.js"></script>
	<script type="text/javascript" src="/common/js/swiper.min.js"></script>
	<script type="text/javascript" src="/common/js/lottie-player.js"></script>
	<script type="text/javascript" src="/common/js/jquery.cookies.js"></script>
	<script type="text/javascript" src="/common/js/script.js"></script>
	<script type="text/javascript" src="/common/js/lib/datetimepicker/jquery.datetimepicker.js"></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript" src="/common/js/kakao.js"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f90d438f5adb45f1893d7ca237eaac27&libraries=services,clusterer,drawing"></script>
	<script type="text/javascript" src="/common/js/kakaomap.js"></script>

	<!--함수 공통-->
	<script type="text/javascript" src="/common/js/controller/co/COCmmCtrl.js"></script>
	<!--메시지 공통-->
	<script type="text/javascript" src="/common/js/controller/co/COMsgCtrl.js"></script>
	<c:set var="servletPath" value="${requestScope['javax.servlet.forward.servlet_path']}" scope="request" />
	<c:set var="mainYn" value="${ not empty mainYn ? mainYn : 'N'}" />
	<c:choose>
		<c:when test="${ mainYn eq 'Y' }">
			<script type="text/javascript" src="/common/js/hammer.min.js"></script>
			<script type="text/javascript" src="/common/js/main.js?bust=${sysDate}"></script>
			<script type="text/javascript" src="/common/js/bezier-easing.min.js"></script>
			<c:set var="wrapClass" value="main" />
		</c:when>
		<c:when test="${ fn:startsWith(servletPath, '/my-page/') }">
			<c:set var="wrapClass" value="mypage" />
		</c:when>
		<c:when test="${ fn:startsWith(servletPath, '/pwd-find') }">
			<c:set var="wrapClass" value="member" />
		</c:when>
		<c:otherwise>

		</c:otherwise>
	</c:choose>
</head>
<body>
<div id="wrap" class="${ wrapClass }">
	<header id="header">

		<!-- header - START -->
		<h1><a class="logo" href="/"></a></h1>
		<a href="javascript:" class="prev-btn icon-btn"></a>
		<h2 class="nav-tit">${ not empty pageMenuDto ? pageMenuDto.menuNm : ''}</h2><!-- @ 해당 서브페이지의 이름 -->
		<nav>
			<c:if test="${ not empty gnbMenuList}">
				<ul class="gnb">
					<c:forEach var="menu" items="${gnbMenuList}" varStatus="status">
						<c:if test="${ menu.attr.gnbYn eq 'Y'}">
							<li>
								<a class="one-depth" href="${ empty menu.attr.link ? 'javascript:' : menu.attr.link }">${ menu.data}</a>
								<c:if test="${ menu.children != null && fn:length(menu.children) > 0 }">
									<div class="two-pack-wrap">
										<ul class="two-pack">
											<c:forEach var="menu2" items="${menu.children}" varStatus="status2">
												<c:if test="${ menu2.attr.gnbYn eq 'Y'}">
													<li>
														<a class="two-depth" href="${ empty menu2.attr.link ? 'javascript:' : menu2.attr.link }">${ menu2.data}</a>
														<c:if test="${ menu2.children != null && fn:length(menu2.children) > 0 }">
															<ul class="three-pack">
																<c:forEach var="menu3" items="${menu2.children}" varStatus="status3">
																	<c:if test="${ menu3.attr.gnbYn eq 'Y'}">
																		<li><a class="three-depth" href="${ empty menu3.attr.link ? 'javascript:' : menu3.attr.link }">${ menu3.data}</a></li>
																	</c:if>
																</c:forEach>
															</ul>
														</c:if>
													</li>
												</c:if>
											</c:forEach>
										</ul>
									</div>
								</c:if>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</c:if>
			<div class="line"></div>
		</nav>

		<div class="util-area">
			<div class="pop-wrap">
				<button class="noti-btn icon-btn headerNtfyButton" type="button" title="팝업"><span>공지</span></button>
				<div class="pop-box">
					<p class="f-head">공지</p>
					<div class="list headerNtfyButtonContainer">
						<c:set var="curDate"><fmt:formatDate value="${now}" pattern="yyyyMMdd" /></c:set>
						<c:forEach var="ntfyList" items="${headerNtfyList}" varStatus="status">
							<a href="/board/notice?detailsKey=${ntfyList.ntfySeq}" title="링크 이동">
								<c:set var="daysDiff" value="${kl:getDaysDiff(curDate, kl:convertDate(ntfyList.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyyMMdd', ''))}" />
								<p class="tit f-body2">
									<c:if test="${ daysDiff >= -3 }">
										<span class="new-icon small" aria-label="새로운 정보"></span>
									</c:if>
										${ ntfyList.titl }
								</p>
								<span class="date f-caption2">${kl:convertDate(ntfyList.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '')}</span>
							</a>
						</c:forEach>
					</div>
				</div>
				<script type="text/javascript">
					//상단 공지 NEW 붙이기
					$(document).ready(function(){
						if($(".headerNtfyButtonContainer").find(".new-icon").size() > 0){
							$(".headerNtfyButton").addClass("on");
						}
					})
				</script>
			</div>
			<button class="search-btn icon-btn" type="button" title=""><span>검색</span></button>
			<div class="pop-wrap">
				<c:choose>
					<c:when test="${ not empty loginMap}">
						<button class="log-btn icon-btn" type="button" title="팝업"><span>마이페이지</span></button>
					</c:when>
					<c:otherwise>
						<button class="log-btn icon-btn" type="button" title="팝업"><span>로그인</span></button>
					</c:otherwise>
				</c:choose>

				<div class="pop-box">
					<c:choose>
						<c:when test="${ not empty loginMap}">
							<div class="btn-wrap">
								<c:if test="${ loginMap.authCd ne 'CS'}">
									<a class="btn-text-icon black-arrow" href="/my-page/main" title="링크 이동"><span>마이페이지</span></a>
								</c:if>
								<c:if test="${ loginMap.authCd eq 'CS'}">
									<a class="btn-text-icon black-arrow" href="/my-page/member/attend/attend-page" title="링크 이동"><span>마이페이지</span></a>
								</c:if>
							</div>
							<div class="btn-wrap">
								<a class="btn-text-icon black-arrow" href="/my-page/logout" title="링크 이동"><span>로그아웃</span></a>
							</div>
						</c:when>
						<c:otherwise>
							<div class="btn-wrap">
								<a class="btn-text-icon black-arrow" href="/login" title="링크 이동"><span>로그인</span></a>
							</div>
							<div class="btn-wrap">
								<a class="btn-text-icon black-arrow" href="/member/join" title="링크 이동"><span>회원가입</span></a>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<button class="init menu-btn icon-btn" type="button" title="">
				<span>메뉴</span>
				<i>
					<span class="top line"></span>
					<span class="mid line"></span>
					<span class="bot line"></span>
				</i>
			</button>
		</div>

		<section class="all-menu">
			<div class="scroll-area">
				<div class="menu-bg"></div>
				<div class="menu-wrap">
					<div class="menu-head">
						<p class="menu-tit f-xlarge-title">전체 서비스</p>
						<div class="log-menu">
                            <c:choose>
                                <c:when test="${ not empty loginMap}">
                                    <p class="user-tit f-title1"><span>${loginMap.name}</span>님<br class="only-mobile"> 안녕하세요.</p><!-- @ 로그인 후에 보이는 요소 -->
                                    <div class="pc btn-wrap">
										<c:if test="${loginMap.authCd ne 'CS'}">
                                        <a class="btn-text-icon black-arrow" href="/my-page/member/intrduction/certification" title="링크 이동"><span>정보수정</span></a>
										</c:if>
                                        <a class="btn-text-icon black-arrow" href="/my-page/logout" title="링크 이동"><span>로그아웃</span></a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <p class="log-tit f-title3">MY</p>
									<div class="pc btn-wrap">
										<a class="btn-text-icon black-arrow" href="/login" title="링크 이동"><span>로그인</span></a>
										<a class="btn-text-icon black-arrow" href="/member/join" title="링크 이동"><span>회원가입</span></a>
									</div>
                                </c:otherwise>
                            </c:choose>
						</div>
                        <c:if test="${ not empty loginMap}">
                            <div class="loginfo-wrap">
                                <div class="loginfo-box">
                                    <p class="info-tit f-title3">신청내역</p>
									<span class="f-caption2">최근 1년 기준</span>
                                    <div class="info-cont">
                                        <ul class="counts">
                                            <li class="count">
                                                <span class="f-sub-head">교육사업</span>
                                                <a class="f-title1" href="javascript:">목록갯수</a>
                                            </li>
                                            <li class="count">
                                                <span class="f-sub-head">컨설팅사업</span>
                                                <a class="f-title1" href="javascript:">목록갯수</a>
                                            </li>
                                            <li class="count">
                                                <span class="f-sub-head">상생사업</span>
                                                <a class="f-title1" href="javascript:">목록갯수</a>
                                            </li>
                                        </ul>
                                        <div class="pc btn-wrap">
                                            <div class="btn-set">
                                                <a class="btn-solid small white-bg" href="javascript:"><span>증명서 발급</span></a>
                                                <a class="btn-solid small white-bg" href="javascript:"><span>1:1 문의</span></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="mob btn-wrap">
                                    <div class="btn-set">
                                        <a class="btn-solid small gray-bg" href="javascript:"><span>증명서 발급</span></a>
                                        <a class="btn-solid small gray-bg" href="javascript:"><span>1:1 문의</span></a>
                                    </div>
                                </div>
                                <p class="last-date f-caption2"><span>최근접속일</span><span class="date">${ kl:convertDate(loginMap.lastLgnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</span></p>
                            </div>
                        </c:if>
					</div>
                    <c:if test="${ not empty gnbMenuList}">
                        <ul class="gnb">
                            <c:forEach var="menu" items="${gnbMenuList}" varStatus="status">
                                <c:if test="${ menu.attr.gnbYn eq 'Y'}">
                                    <li>
                                        <div class="one-pack">
                                            <a class="one-depth for-move" href="${ empty menu.attr.link ? 'javascript:' : menu.attr.link }">${ menu.data}</a>
                                        </div>
                                    <c:if test="${ menu.children != null && fn:length(menu.children) > 0 }">
                                        <ul class="two-pack">
                                            <c:forEach var="menu2" items="${menu.children}" varStatus="status2">
                                                <c:if test="${ menu2.attr.gnbYn eq 'Y'}">
                                                    <li>
                                                        <div class="for-move">
                                                            <a class="two-depth" href="${ empty menu2.attr.link ? 'javascript:' : menu2.attr.link }">${ menu2.data}</a>
                                                            <c:if test="${ menu2.children != null && fn:length(menu2.children) > 0 }">
                                                                <ul class="three-pack">
                                                                    <c:forEach var="menu3" items="${menu2.children}" varStatus="status3">
                                                                        <c:if test="${ menu2.attr.gnbYn eq 'Y'}">
                                                                            <li><a class="three-depth" href="${ empty menu3.attr.link ? 'javascript:' : menu3.attr.link }">${ menu3.data}</a></li>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </ul>
                                                            </c:if>
                                                        </div>
                                                    </li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </c:if>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </c:if>
					<div class="notice-wrap">
						<div class="notice-rolling">
							<ul>
                                <c:forEach var="ntfyList" items="${headerNtfyList}" varStatus="status">
                                    ${ status.index}
                                    <c:choose>
                                        <c:when test="${ status.index eq 0 }">
                                            <c:set var="cls" value="current" />
                                        </c:when>
                                        <c:when test="${ status.index eq 1 }">
                                            <c:set var="cls" value="next" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="cls" value="prev" />
                                        </c:otherwise>
                                    </c:choose>
                                    <li class="${ cls }">
                                        <c:set var="daysDiff" value="${kl:getDaysDiff(curDate, kl:convertDate(ntfyList.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyyMMdd', ''))}" />
                                        <a class="f-body2">
                                            <c:if test="${ daysDiff >= -3 }">
                                                <span class="new-icon small" aria-label="새로운 정보"></span>
                                            </c:if>
                                                ${ ntfyList.titl }
                                        </a>
                                    </li>
                                </c:forEach>
							</ul>
						</div>
					</div>

				</div>
			</div>
		</section>

		<section class="all-srch">
			<div class="srch-wrap" data-controller="controller/co/COHeaderTotalSearch">
				<form method="get" id="headerSrchFrm">
					<div class="form-input srch-input">
						<input type="text" placeholder="검색어를 입력해 주세요." title="검색어" name="q" maxlength="50" />
						<div class="input-btn-wrap">
							<button class="delete-btn" title="지우기" type="button"></button>
							<button class="srch-btn" title="검색" type="submit"></button>
						</div>
					</div>
					<c:if test="${ not empty cdDtlList }">
						<ul class="top-srchs">
							<c:forEach var="cdList" items="${cdDtlList.FRONT_TOTAL_KEYWORD}" varStatus="status">
								<c:if test="${ status.index <= 4}">
									<li class="srch-item">
										<a href="javascript:" class="frontTotalKeyword">${cdList.cdNm}</a>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</c:if>
				</form>
			</div>
		</section>
		<!-- header - END -->
	</header>