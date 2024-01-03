<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="servletPath" value="${requestScope['javax.servlet.forward.servlet_path']}" scope="request" />
<c:if test="${ not empty gnbMenuList}">


	<c:if test="${ not empty parntMenuList and (fn:length(parntMenuList) >= 2 or fn:indexOf(servletPath, '/my-page/') > -1)}">


		<c:set var="parntMenu" value="${ parntMenuList[1] }" />

		<c:if test="${ parntMenu.gnbYn eq 'Y' or fn:indexOf(servletPath, '/my-page/') > -1 }">
			<div class="lnb-area">
				<div class="for-motion">
					<c:forEach var="menu" items="${gnbMenuList}" varStatus="lnbStatus">

						<c:if test="${ parntMenuList[0].menuSeq eq menu.attr.treeid and (menu.attr.gnbYn eq 'Y' or fn:indexOf(servletPath, '/my-page/') > -1)}">
							<c:if test="${ menu.children != null && fn:length(menu.children) > 0 }">
								<c:forEach var="menu2" items="${menu.children}" varStatus="status2">
									<div class="lnb-list">
										<c:if test="${ menu2.attr.gnbYn eq 'Y'}">
											<c:set var="childrenGnbYn" value="N"/>
											<c:forEach var="childrenChkList" items="${menu2.children}">
												<c:if test="${childrenChkList.attr.gnbYn eq 'Y' && childrenGnbYn ne 'Y'}">
													<c:set var="childrenGnbYn" value="Y"/>
												</c:if>
											</c:forEach>

											<a class="btn-two-depth ${fn:length(menu2.children) eq 0 || childrenGnbYn eq 'N' ? ' single-menu ' : ''} ${ parntMenuList[1].menuSeq eq menu2.attr.treeid ? 'active' : ''}" href="${ empty menu2.attr.link or fn:length(menu2.children) > 0 ? 'javascript:' : menu2.attr.link }"><span>${ menu2.data}</span></a>
											<c:if test="${ menu2.children != null && fn:length(menu2.children) > 0 }">
												<div class="three-depth-wrap">
													<c:forEach var="menu3" items="${menu2.children}" varStatus="status3">
														<c:if test="${ menu3.attr.gnbYn eq 'Y'}">
															<a class="btn-three-depth ${ parntMenuList[2].menuSeq eq menu3.attr.treeid ? 'active' : ''}" href="${ empty menu3.attr.link ? 'javascript:' : menu3.attr.link }"><span>${ menu3.data}</span></a>
														</c:if>
													</c:forEach>
												</div>
											</c:if>
										</c:if>
									</div>
								</c:forEach>
							</c:if>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</c:if>
	</c:if>
</c:if>