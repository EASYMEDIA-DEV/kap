<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>KAP</title>
	<link rel="shortcut icon" href="/common/images/favicon.ico" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<meta name="keywords" content="KAP"/>
	<meta name="description" content="KAP"/>
	<meta name="X-CSRF-TOKEN" content="${_csrf.token}" />
	<meta property="og:site_name" content="KAP" id="og-sitename-value"/>
	<meta property="og:type" content="website" id="og-type-value"/>
	<meta property="og:url" content="URL" id="og-url-value"/>
	<meta property="og:title" content="NOTICE | KAP" id="og-title-value"/>
	<meta property="og:description" content="KAP 홈페이지에 방문하신 것을 환영합니다." id="og-description-value"/>
	<meta property="og:image" content="img URL" id="og-image-value"/>
	<link rel="stylesheet" href="/common/css/swiper.css" />
	<link rel="stylesheet" href="/common/css/fonts.css" />
	<link rel="stylesheet" href="/common/css/common.css" />
	<link rel="stylesheet" href="/common/css/style.css" />
	<script type="text/javascript" src="/common/js/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="/common/js/gsap.min.js"></script>
	<script type="text/javascript" src="/common/js/ScrollTrigger.min.js"></script>
	<script type="text/javascript" src="/common/js/swiper.min.js"></script>
	<script type="text/javascript" src="/common/js/script.js"></script>
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<c:set var="curtDt"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd" /></c:set>
	<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" /></c:set>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<!--함수 공통-->
	<script type="text/javascript" src="/common/js/controller/co/COCmmCtrl.js"></script>
	<!--메시지 공통-->
	<script type="text/javascript" src="/common/js/controller/co/COMsgCtrl.js"></script>
</head>
<body>
<div id="wrap">
	<header id="header">
		<!-- header - START -->
		<h1><a class="logo" href="#"></a></h1>
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
																	<c:if test="${ menu2.attr.gnbYn eq 'Y'}">
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
							<a href="javascript:" title="링크 이동">
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
					$(document).ready(function(){
						if($(".headerNtfyButtonContainer").find(".new-icon").size() > 0){
							$(".headerNtfyButton").addClass("on");
						}
					})
				</script>
			</div>
			<button class="search-btn icon-btn" type="button" title=""><span>검색</span></button>
			<div class="pop-wrap">
				<button class="log-btn icon-btn" type="button" title="팝업"><span>로그인</span></button>
				<div class="pop-box">
					<div class="btn-wrap">
						<a class="btn-text-icon black-arrow" href="/login" title="링크 이동"><span>로그인</span></a>
					</div>
					<div class="btn-wrap">
						<a class="btn-text-icon black-arrow" href="/member/join" title="링크 이동"><span>회원가입</span></a>
					</div>
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
							<!-- <p class="log-tit f-title3">MY</p> --><!-- @ 로그인 전에 보이는 요소 -->
							<p class="user-tit f-title1"><span>강문주</span>님 <br/>안녕하세요.</p><!-- @ 로그인 후에 보이는 요소 -->
							<div class="pc btn-wrap">
								<a class="btn-text-icon black-arrow" href="javascript:" title="링크 이동"><span>정보수정</span></a>
								<a class="btn-text-icon black-arrow" href="javascript:" title="링크 이동"><span>로그아웃</span></a>
							</div>
						</div>
						<!-- @ 로그인 후에 보이는 요소 -->
						<div class="loginfo-wrap">
							<div class="loginfo-box">
								<p class="info-tit f-title3">신청내역</p>
								<div class="info-cont">
									<ul class="counts">
										<li class="count">
											<span class="f-sub-head">교육사업</span>
											<a class="f-title1" href="javascript:">5</a>
										</li>
										<li class="count">
											<span class="f-sub-head">컨설팅사업</span>
											<a class="f-title1" href="javascript:">21</a>
										</li>
										<li class="count">
											<span class="f-sub-head">상생사업</span>
											<a class="f-title1" href="javascript:">0</a>
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
							<p class="last-date f-caption2"><span>최근접속일</span><span class="date">2023.01.01 10:00</span></p>
						</div>
						<!-- // @ 로그인 후에 보이는 요소 -->
					</div>
					<ul class="gnb">
						<li>
							<div class="one-pack">
								<a class="one-depth for-move" href="javascript:">교육사업</a>
							</div>
							<ul class="two-pack">
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">교육사업</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">품질아카데미</a></li>
											<li><a class="three-depth" href="javascript:">제조/경영혁신</a></li>
											<li><a class="three-depth" href="javascript:">세미나</a></li>
										</ul>
									</div>
								</li>
							</ul>
						</li>
						<li>
							<div class="one-pack">
								<a class="one-depth for-move" href="javascript:">컨설팅사업</a>
							</div>
							<ul class="two-pack">
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">기술지도</a>
									</div>
								</li>
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">경영컨설팅</a>
									</div>
								</li>
							</ul>
						</li>
						<li>
							<div class="one-pack">
								<a class="one-depth for-move" href="javascript:">상생사업</a>
							</div>
							<ul class="two-pack">
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">경쟁력향상지원</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">미래차다각화</a></li>
											<li><a class="three-depth" href="javascript:">사업재편지원</a></li>
											<li><a class="three-depth" href="javascript:">보안솔루션구축</a></li>
											<li><a class="three-depth" href="javascript:">안전설비구축</a></li>
											<li><a class="three-depth" href="javascript:">탄소배출저감</a></li>
											<li><a class="three-depth" href="javascript:">스마트공장</a></li>
										</ul>
									</div>
								</li>
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">자금지원</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">시험계측장비</a></li>
											<li><a class="three-depth" href="javascript:">검교정</a></li>
											<li><a class="three-depth" href="javascript:">공급망안정화기금</a></li>
										</ul>
									</div>
								</li>
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">포상지원</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">자동차부품산업대상</a></li>
											<li><a class="three-depth" href="javascript:">미래차공모전</a></li>
										</ul>
									</div>
								</li>
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">설문조사</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">상생협력체감도조사</a></li>
										</ul>
									</div>
								</li>
							</ul>
						</li>
						<li>
							<div class="one-pack">
								<a class="one-depth for-move" href="javascript:">재단정보</a>
							</div>
							<ul class="two-pack">
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">재단소개</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">인사말</a></li>
											<li><a class="three-depth" href="javascript:">재단개요</a></li>
											<li><a class="three-depth" href="javascript:">재단연혁</a></li>
											<li><a class="three-depth" href="javascript:">재단조직</a></li>
											<li><a class="three-depth" href="javascript:">윤리경영</a></li>
											<li><a class="three-depth" href="javascript:">경영공시</a></li>
											<li><a class="three-depth" href="javascript:">홍보자료</a></li>
											<li><a class="three-depth" href="javascript:">오시는길</a></li>
										</ul>
									</div>
								</li>
								<li>
									<div class="for-move">
										<a class="two-depth" href="javascript:">고객센터</a>
										<ul class="three-pack">
											<li><a class="three-depth" href="javascript:">공지사항</a></li>
											<li><a class="three-depth" href="javascript:">재단소식</a></li>
											<li><a class="three-depth" href="javascript:">뉴스레터</a></li>
											<li><a class="three-depth" href="javascript:">FAQ</a></li>
											<li><a class="three-depth" href="javascript:">1:1문의</a></li>
										</ul>
									</div>
								</li>
							</ul>
						</li>
					</ul>
					<div class="notice-wrap">
						<div class="notice-rolling">
							<ul>
								<li class="current"><a class="f-body2" href="javascript:"><span class="new-icon small" aria-label="새로운 정보"></span>협력사 온라인 정보공유 소통 플랫폼 오픈 공지 협력사 온라인 정보공유 소통 플랫폼 오픈 공지</a></li>
								<li class="next"><a class="f-body2" href="javascript:"><span class="new-icon small" aria-label="새로운 정보"></span>대구시, 자동차부품산업진흥재단 등과 미래자동차 산업 육성 협력 대구시, 자동차부품산업진흥재단 등과 미래자동차 산업 육성 협력</a></li>
								<li class="prev"><a class="f-body2" href="javascript:">2023년 하반기 자문위원 채용 공고 2023년 하반기 자문위원 채용 공고</a></li>
							</ul>
						</div>
					</div>

				</div>
			</div>
		</section>

		<section class="all-srch">
			<div class="srch-wrap" data-controller="controller/co/COHeaderTotalSearch">
				<form method="get">
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