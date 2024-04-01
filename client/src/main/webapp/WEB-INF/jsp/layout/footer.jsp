<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
			<footer>

				<jsp:include page="/WEB-INF/jsp/front/eb/eba/EBAEduTotCalLayer2.jsp"></jsp:include><!--전체 교육일정-->

				<!-- footer - START -->
				<div class="inner">
					<figure class="footer-logo">
						<img src="/common/images/icon-logo-foot.svg" alt="KAP 자동차부품산업진흥재단">
					</figure>
					<div class="links-box f-body2">
						<a class="personal bold" class="active" href="/utility/privacy-policy/index">개인정보 처리방침</a>
						<a class="terms" href="/utility/terms-of-use/index">이용약관</a>
						<a class="email" id="showEmailCollectPopup" href="javascript:">이메일무단수집거부</a>
						<a class="way" href="/foundation/intro/location/content">오시는길</a>
					</div>
					<div class="copy-area f-caption2">
						<span>(04365)서울시 용산구 원효로 74&nbsp;</span>
						<span>FAX : 02-3271-2969</span>
						<span>Copyright 2002 KAP All rights reserved.</span>
					</div>
					<div class="family-site">
						<div class="form-group">
							<div class="form-select">
								<select title="관련사이트 이동" id="footerFamilySelect" onchange="setFooterFamilySelect()">
									<option value="">관련사이트</option>
									<option value="https://www.motie.go.kr">산업통상자원부</option>
									<option value="https://www.moef.go.kr">기획재정부</option>
									<option value="https://www.kaica.or.kr">한국자동차산업협동조합</option>
									<option value="https://www.ksae.org">한국자동차공학회</option>
									<option value="http://www.molit.go.kr">국토교통부</option>
									<option value="https://www.mss.go.kr">중소벤처기업부</option>
									<option value="https://www.katech.re.kr">한국자동차연구원</option>
									<option value="http://www.baica.or.kr">부산자동차부품공업협동조합</option>
									<option value="https://www.kama.or.kr">한국자동차모빌리티산업협회</option>
									<option value="https://www.hkasa.com">현대·기아 협력회</option>
									<option value="https://www.kosmes.or.kr">중소벤처기업진흥공단</option>
									<option value="http://www.kmir.org">한국자동차산업시험연구소</option>
									<option value="https://www.hyundai.com">현대자동차</option>
									<option value="https://www.kia.com">기아</option>
									<option value="https://www.renaultkoream.com">르노코리아자동차</option>
									<option value="https://www.mobis.co.kr">현대모비스</option>
									<option value="https://www.gm-korea.co.kr">한국지엠</option>
									<option value="http://www.kg-mobility.com">KG 모빌리티</option>
								</select>
								<script type="text/javascript">
									function setFooterFamilySelect(){
										if($.trim($("#footerFamilySelect").val()) != ""){
											window.open($("#footerFamilySelect").val(), "_blank");
										}
									}

									$(document).ready(function() {
										// 이메일무단수집거부 팝업 열기
										$("#showEmailCollectPopup").click(function() {
											openPopup('emailCollectPopup', this);
										});
									});
								</script>
							</div>
						</div>
					</div>
				</div>
				<!-- footer - END -->
			</footer>



			<div class="quick-menu">
				<div class="menu-area">
					<button class="btn-open-menu" title="퀵 메뉴 열기" type="button"></button>
					<div class="btn-w">
						<a class="quick-btn trend" href="javascript:"  onclick="openPopup('trendPopup', this)"><span class="txt">TREND</span></a>
						<a class="quick-btn edu-schedule" href="javascript:eduSchedule(this);" ><span class="txt">교육일정</span></a>

						<c:choose>
							<c:when test="${not empty loginMap.authCd}">

								<c:choose>
									<c:when test="${loginMap.authCd eq 'CS'}">
										<a class="quick-btn certificate" href="#" onclick="alert('위원 계정은 해당 서비스를 이용할 수 없습니다.');"><span class="txt">증명서</span></a>
									</c:when>
									<c:when test="${loginMap.authCd ne 'CS'}">
										<a class="quick-btn certificate" href="/my-page/edu-apply/list?crtfctYn=Y" ><span class="txt">증명서</span></a>
									</c:when>
								</c:choose>
							</c:when>
							<c:otherwise>
								<a class="quick-btn certificate" href="#" onclick="alert('로그인후 이용해주세요');"><span class="txt">증명서</span></a>
							</c:otherwise>

						</c:choose>

						<a class="quick-btn faq" href="/foundation/board/faq/list"><span class="txt">FAQ</span></a>
						<a class="quick-btn manager" href="/foundation/intro/organization/content"><span class="txt">담당자</span></a>
					</div>
				</div>
				<a class="btn-top" href="javascript:" title="페이지 상단으로 이동"><span class="txt">TOP</span></a>
			</div>
			<div class="scroll-gauge">
				<div class="bar"></div>
			</div>
			<div class="dimd"></div>

			<div class="only-vertical-view"></div>


			<div class="loading-area" style="display: none;">
				<div class="for-center">
					<div class="lottie-area">
						<lottie-player src="/common/images/v2.json" background="transparent" style="width: 48rem; height: 48rem;" speed="1" loop="" autoplay=""></lottie-player>
					</div>
					<div class="txt-area">
						<p class="tit">로딩 중 입니다.</p>
						<p class="txt">잠시만 기다려주세요.</p>
					</div>
				</div>
			</div>
			<!-- 이메일무단수집거부 팝업 -->
			<div class="layer-popup emailCollectPopup" style="display: none;">
				<div class="for-center">
					<div class="pop-wrap">
						<div class="pop-con-area">
							<div class="tit-area">
								<p class="f-large-title">이메일무단수집거부</p>
							</div>
							<div class="con-area">
								<div class="scroll-area">
									<div class="p-info-sec">
										<div class="sec-con-area">
											<p class="f-sub-head">본 사이트에 게시된 이메일 주소가 전자우편 수집 프로그램이나, 그 밖의 기술적 장치를
												이용하여 무단으로 수집되는 것을 거부하며, 이를 위반 시 <span class="f-head">정보통신망법에 의해 형사처벌 됨을 유의</span>하시기 바랍니다.</p>
										</div>
									</div>
								</div>
							</div>
							<div class="user-opt-area">
								<button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 퀵메뉴 > TREND -->
		<div class="layer-popup full-popup trendPopup">
			<div class="for-center">
				<div class="pop-wrap">
					<div class="pop-con-area">
						<div class="tit-area">
							<p class="f-head">TREND</p>
						</div>
						<div class="con-area">
							<div class="scroll-area">
								<div class="p-cont-sec">
									<p class="trend-tit f-title1">기대 그 이상</p>
									<div class="trend-swiper-area">
										<div class="swiper-container trend-swiper">
											<div class="swiper-wrapper">
												<!--
													각 사업 종류 별로, swiper-slide에 클래스 추가
													교육사업 : education
													상생사업 : winwin
													컨설팅사업 : consulting
												-->
												<c:forEach var="list" items="${quickTrendList.list}" varStatus="status">
													<c:set var="typeClass" value=""/>
													<c:choose>
														<c:when test="${list.typeCd eq 'TYPE01'}">
															<c:set var="typeClass" value="education"/>
														</c:when>
														<c:when test="${list.typeCd eq 'TYPE02'}">
															<c:set var="typeClass" value="consulting"/>
														</c:when>
														<c:otherwise>
															<c:set var="typeClass" value="winwin"/>
														</c:otherwise>
													</c:choose>
													<c:choose>
														<c:when test="${not empty list.urlUrl}">
															<a class="swiper-slide ${typeClass}" href="${list.urlUrl}">
														</c:when>
														<c:otherwise>
															<a class="swiper-slide ${typeClass}" href="javascript:">
														</c:otherwise>
													</c:choose>
														<div class="for-motion">
															<div class="img-area">
																<img src="${list.webPath}" alt="${list.fileDsc}">
															</div>
															<div class="txt-area">
																<p class="label f-body2">${list.typeCdNm}</p>
																<p class="tit f-title3">${list.titl}</p>
																<div class="btn-wrap">
																	<c:choose>
																		<c:when test="${typeClass eq 'education'}">
																			<div class="btn-solid small gray-bg"><span>더 알아보기</span></div>
																		</c:when>
																		<c:otherwise>
																			<div class="btn-text-icon black-circle"><span>자세히 보기</span></div>
																		</c:otherwise>
																	</c:choose>
																</div>
															</div>
														</div>
													</a>
												</c:forEach>
											</div>
										</div>
										<div class="swiper-button-next circle-arr btn_next"></div>
										<div class="swiper-button-prev circle-arr btn_prev"></div>
									</div>
								</div>
							</div>
						</div>
						<div class="user-opt-area">
							<button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- require -->
		<script type="text/javascript" src="/common/js/lib/require.js" data-main="/common/js/site"></script>

	</body>
</html>