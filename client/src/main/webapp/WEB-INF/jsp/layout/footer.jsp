<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
			<footer>
				<!-- footer - START -->
				<div class="inner">
					<figure class="footer-logo">
						<img src="/common/images/icon-logo-foot.svg" alt="KAP 자동차부품산업진흥재단">
					</figure>
					<div class="links-box f-body2">
						<a class="personal bold" class="active" href="javascript:">개인정보 처리방침</a>
						<a class="terms" href="javascript:">이용약관</a>
						<a class="email" href="javascript:">이메일무단수집거부</a>
						<a class="way" href="javascript:">오시는길</a>
					</div>
					<div class="copy-area f-caption2">
						<span>(04365)서울시 용산구 원효로 74</span>
						<span>TEL : 02-3271-2965</span>
						<span>FAX : 02-3271-2969</span>
						<span>Copyright 2002 KAP All rights reserved.</span>
					</div>
					<div class="family-site">
						<div class="form-group">
							<div class="form-select">
								<select title="관련사이트 이동" id="footerFamilySelect" onchange="setFooterFamilySelect()">
									<option value="">관련사이트</option>
									<option value="http://www.motie.go.kr">산업통상자원부</option>
									<option value="http://www.mosf.go.kr">기획재정부</option>
									<option value="http://www.kaica.or.kr">한국자동차산업협동조합</option>
									<option value="http://www.kase.org">한국자동차공학회</option>
									<option value="http://www.molit.or.kr">국토교통부</option>
									<option value="http://www.smba.go.kr">중소기업청</option>
									<option value="http://www.katech.re.kr">자동차부품연구원</option>
									<option value="http://www.autopart.or.kr">부산자동차부품공업협동조합</option>
									<option value="http://www.kama.or.kr">한국자동차공업협회</option>
									<option value="http://www.hkasa.co.kr">현대기아협력회</option>
									<option value="http://www.sbc.or.kr">중소기업진흥공단</option>
									<option value="http://kari.hmc.co.kr">한국자동차산업엽구소</option>
									<option value="http://www.hyundai-motor.co.kr">현대자동차</option>
									<option value="http://www.kia.co.kr">기아자동차</option>
									<option value="http://www.renaultsamsungm.com">르노삼성자동차</option>
									<option value="http://www.mobis.co.kr">현대모비스</option>
									<option value="http://www.gm-korea.co.kr">한국지엠</option>
									<option value="http://www.smotor.com">쌍용자동차</option>
								</select>
								<script type="text/javascript">
									function setFooterFamilySelect(){
										if($.trim($("#footerFamilySelect").val()) != ""){
											window.open($("#footerFamilySelect").val(), "_blank");
										}
									}
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
						<a class="quick-btn edu-schedule" href="javascript:"><span class="txt">교육일정</span></a>
						<a class="quick-btn certificate" href="javascript:"><span class="txt">증명서</span></a>
						<a class="quick-btn faq" href="javascript:"><span class="txt">FAQ</span></a>
						<a class="quick-btn qna" href="javascript:"><span class="txt">1:1 문의</span></a>
						<a class="quick-btn manager" href="javascript:"><span class="txt">담당자</span></a>
					</div>
				</div>
				<a class="btn-top" href="javascript:" title="페이지 상단으로 이동"><span class="txt">TOP</span></a>
			</div>
			<div class="scroll-gauge">
				<div class="bar"></div>
			</div>
			<div class="dimd"></div>
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
		</div>
		<!-- require -->
		<script type="text/javascript" src="/common/js/lib/require.js" data-main="/common/js/site"></script>
	</body>
</html>