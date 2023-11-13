<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!DOCTYPE html>
<html lang="en">
  	<head>
		<spring:eval var="siteName" expression="@environment.getProperty('app.site.name')" />
		<spring:eval var="httpWebSourceUrl" expression="@environment.getProperty('app.site.web-source-url')" />
		<spring:eval var="httpUserDomain" expression="@environment.getProperty('app.user-domain')" />
		<spring:eval var="siteProfile" expression="@environment.getProperty('spring.config.activate.on-profile')" />
		<c:set var="now" value="<%=new java.util.Date()%>" />
		<c:set var="curtDt"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd" /></c:set>
		<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" /></c:set>
    	<meta charset="utf-8" />
    	<meta http-equiv="x-ua-compatible" content="IE=edge" >
    	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    	<meta name="X-CSRF-TOKEN" content="${_csrf.token}" />
    	<title>${siteName}</title>
    	<link rel="shortcut icon" href="/common/images/bookmark_logo.ico" />
    	<link rel="stylesheet" href="/bootstrap/vendor/animate.css/animate.css" />
    	<link rel="stylesheet" href="/bootstrap/vendor/bootstrap/dist/css/bootstrap.min.css" />
    	<link rel="stylesheet" href="/bootstrap/vendor/bootstrap/dist/css/bootstrap-submenu.css" />
    	<link rel="stylesheet" href="/bootstrap/vendor/ionicons/css/ionicons.css" />
		<link rel="stylesheet" href="/bootstrap/vendor/select2/dist/css/select2.css" />
	 	<link rel="stylesheet" href="/bootstrap/vendor/dropzone/dist/basic.css">
	 	<link rel="stylesheet" href="/bootstrap/vendor/mjolnic-bootstrap-colorpicker/dist/css/bootstrap-colorpicker.css">
		<link rel="stylesheet" href="/bootstrap/vendor/dropzone/dist/dropzone.css" />
    	<link rel="stylesheet" href="/bootstrap/vendor/jquery.bootgrid/dist/jquery.bootgrid.css" />
		<link rel="stylesheet" href="/bootstrap/vendor/loaders.css/loaders.css" />
    	<link rel="stylesheet" href="/bootstrap/css/app.css" />
		<link rel="stylesheet" href="/common/kendo/css/kendo.common.min.css" />
		<link rel="stylesheet" href="/common/kendo/css/kendo.bootstrap.min.css" />
    	<link rel="stylesheet" href="/common/js/lib/datetimepicker/jquery.datetimepicker.css" />
		<link rel="stylesheet" href="/common/ckeditor/plugins/codemirror/css/codemirror.min.css" />
    	<script type="text/javascript" src="/common/js/lib/jquery/jquery.1.12.4.js"></script>
    	<script type="text/javascript" src="/bootstrap/vendor/bootstrap/dist/js/bootstrap.js"></script>
		<script type="text/javascript" src="/bootstrap/vendor/bootstrap/dist/js/bootstrap-submenu.js"></script>
    	<script type="text/javascript" src="/bootstrap/vendor/select2/dist/js/select2.js"></script>
    	<script type="text/javascript" src="/bootstrap/vendor/mjolnic-bootstrap-colorpicker/dist/js/bootstrap-colorpicker.js"></script>
    	<script type="text/javascript" src="/bootstrap/vendor/dropzone/dist/dropzone.js"></script>
		<script type="text/javascript" src="/common/js/lib/jquery/jquery-ui.js"></script>
    	<script type="text/javascript" src="/common/js/lib/jquery/jquery.cookies.js"></script>
    	<script type="text/javascript" src="/common/js/lib/jquery/jquery.highlight.js"></script>
    	<script type="text/javascript" src="/common/js/lib/datetimepicker/jquery.datetimepicker.js"></script>
    	<script type="text/javascript" src="/common/js/lib/xlsx.full.min.js"></script>
		<script type="text/javascript" src="/common/js/app.js"></script>
		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		<!--함수 공통-->
		<script type="text/javascript" src="/common/js/controller/co/COCmmCtrl.js?${sysDate}"></script>
		<!--메시지 공통-->
		<script type="text/javascript" src="/common/js/controller/co/COMsgCtrl.js?${sysDate}"></script>
		<%--메뉴 분류--%>
		<script type="text/javascript">
			//<![CDATA[
			jQuery(document).ready(function(){
				var trgtPstn = jQuery("#leftMenu${pageNo}").offset();

				if (trgtPstn)
				{
					jQuery(".sidebar-content").animate({ scrollTop : trgtPstn.top - 70 }, 0);
				}

				jQuery("#topMenuArea .dropdown").each(function(q){
					var trgtObj = jQuery(this);
					if (trgtObj.data("menuType") == "menu")
					{
						setRemoveFolder(trgtObj);

						trgtObj.find("ul:first").children("li").each(function(q){
							var menuType = jQuery(this).data("menuType");

							if (menuType != "cms" && jQuery(this).find("li[data-menu-type='cms']").length == 0)
							{
								jQuery(this).remove();
							}
						});
					}
				});
			});

			function setRemoveFolder(trgtObj)
			{
				if (trgtObj.find("li").length > 0)
				{
					trgtObj.find("li").each(function(q){
						setRemoveFolder(jQuery(this));

						if (jQuery(this).find("li").length == 0)
						{
							jQuery(this).find("ul").remove();
							jQuery(this).removeClass("dropdown-submenu");
						}
					});
				}
				else
				{
					var menuType = trgtObj.data("menuType");

					if (menuType != "cms")
					{
						trgtObj.remove();
					}
				}
			}
			//]]>
		</script>
	</head>
	<c:set var="sidebarShowheader"  value="${kl:decode(cookie.sidebarShowheader.value,  '', 'checked', cookie.sidebarShowheader.value)}" />
	<c:set var="sidebarShowtoolbar" value="${kl:decode(cookie.sidebarShowtoolbar.value, '', 'checked', cookie.sidebarShowtoolbar.value)}" />
	<c:set var="sidebarOffcanvas"   value="${kl:decode(cookie.sidebarOffcanvas.value,   '', '',        cookie.sidebarOffcanvas.value)}" />
  	<body class="theme-${kl:nvl(cookie.theme.value, '1')} ${kl:decode(sidebarOffcanvas, 'checked', ' sidebar-offcanvas', '')}"
  		data-curt-dt="${curtDt}"
  	    data-web-source-url="${httpWebSourceUrl}"
		data-user-domain="${httpUserDomain}">
    	<div class="layout-container">
			<!-- top navbar-->
      		<header class="header-container">
        		<nav>
        			<ul class="visible-xs visible-sm">
			            <li><a href="javascript:" class="menu-link menu-link-slide" id="sidebar-toggler"><span><em></em></span></a></li>
			        </ul>
	        		<ul class="hidden-xs">
	            		<li><a href="javascript:" class="menu-link menu-link-slide" id="offcanvas-toggler"><span><em></em></span></a></li>
	          		</ul>
					<c:set var="topSize" value="${fn:length(menuList)}" />
					<c:forEach var="top" items="${menuList}" varStatus="status">
						<c:choose>
							<c:when test="${status.count lt topSize}">
								<fmt:formatNumber var="nextlevel" value="${menuList[status.count].dpth}" type="number" />
							</c:when>
							<c:otherwise>
								<fmt:formatNumber var="nextlevel" value="${menuList[status.count-1].dpth}" type="number" />
							</c:otherwise>
						</c:choose>

						<fmt:formatNumber var="currentlevel" value="${top.dpth}" type="number" />

						<c:if test="${empty nextlevel}">
							<fmt:formatNumber var="nextlevel" value="${currentlevel}" type="number" />
						</c:if>

						<c:if test="${status.count eq 1}">
							<ul class="nav navbar-nav" id="topMenuArea" style="z-index:1000">
						</c:if>

						<c:if test="${menuList[status.index-1].dpth lt currentlevel and currentlevel gt 2}">
							<ul class="dropdown-menu" role="menu">
						</c:if>
						<c:choose>
					       	<c:when test="${currentlevel eq 2}">
					       		<li class="dropdown" data-menu-type="${top.menuType}">
					       	</c:when>
					       	<c:otherwise>
					       		<c:choose>
					       			<c:when test="${currentlevel lt nextlevel}">
					       				<li class="dropdown-submenu" data-menu-type="${top.menuType}">
					       			</c:when>
					       			<c:otherwise>
					       				<li data-menu-type="${top.menuType}">
					       			</c:otherwise>
					       		</c:choose>
					       	</c:otherwise>
					    </c:choose>

					   	<c:choose>
					   		<c:when test="${currentlevel eq 2 and currentlevel le nextlevel}">
								<c:choose>
									<c:when test="${cmsCount eq 0 && top.menuNm eq 'CMS 관리'}">
									</c:when>
									<c:when test="${top.menuNm eq '1:1 문의' && top.admUrl eq '/mngwserc/im/ima/list'}">
										<a href="${kl:nvl(top.admUrl, 'javascript:')}" target="${top.wnppYn eq 'Y' ? '_blank' : '_self'}" role="button" aria-expanded="false" data-submenu="" <c:if test="${top.wnppYn eq 'Y'}"> rel="noopener noreferrer" </c:if>>
											<h6>${top.menuNm}<c:if test="${currentlevel lt nextlevel}"><span class="caret"></span></c:if></h6>
										</a>
									</c:when>
									<c:otherwise>
										<a href="${kl:nvl(top.admUrl, 'javascript:')}" target="${top.wnppYn eq 'Y' ? '_blank' : '_self'}" class="dropdown-toggle"
										   data-toggle="dropdown" role="button" aria-expanded="false" data-submenu="" <c:if test="${top.wnppYn eq 'Y'}"> rel="noopener noreferrer" </c:if>>
											<h6>${top.menuNm}<c:if test="${currentlevel lt nextlevel}"><span class="caret"></span></c:if></h6>
										</a>
									</c:otherwise>
								</c:choose>
					    	</c:when>
					    	<c:otherwise>
					    		<a href="${kl:nvl(top.admUrl, 'javascript:')}" target="${top.wnppYn eq 'Y' ? '_blank' : '_self'}" <c:if test="${top.wnppYn eq 'Y'}"> rel="noopener noreferrer" </c:if>><h7>${top.menuNm}</h7></a>
					    	</c:otherwise>
					    </c:choose>
						<c:choose>
							<c:when test="${status.last}">
								<c:forEach var="close" begin="2" end="${currentlevel}" step="1">
										</li>
									</ul>
								</c:forEach>
							</c:when>
							<c:when test="${currentlevel gt nextlevel}">
								<c:forEach var="close" begin="1" end="${currentlevel - nextlevel}" step="1">
										</li>
									</ul>
								</c:forEach>
							</c:when>
							<c:when test="${currentlevel eq nextlevel}">
								</li>
							</c:when>
						</c:choose>
					</c:forEach>

	         	 	<ul class="pull-right">
			            <li class="dropdown">
			            	<a href="javascript:" class="dropdown-toggle ripple" data-toggle="dropdown" aria-expanded="false">
            					<em class="ion-person"></em>
			            	</a>
			            	<ul class="dropdown-menu dropdown-menu-right">
				                <li>
				                	<a href="./profile"><em class="ion-home icon-fw"></em>Profile (${sessionScope.loginMap.name}님)</a>
				                </li>
				                <li>
				                	<a href="./logout"><em class="ion-log-out icon-fw"></em>Logout</a>
				                </li>
				                <li class="divider" role="presentation"></li>
				                <li>
				                	<a style="pointer-events: none"><em class="ion-location icon-fw"></em>${sessionScope.loginMap.loginIp}</a>
				                </li>
							</ul>
			            </li>
			            <li>
            				<a href="javscript:" class="ripple" id="header-settings"><em class="ion-gear-b"></em></a>
            			</li>
						<li>
							<div class="actions btn-group mr ml-sm">
								<select class="form-control input-sm" onchange="location.href='/mngwserc/drive/'+$(this).val()">
									<c:forEach var="driveList" items="${driveMenuList}" varStatus="status">
										<option value="${ driveList.menuSeq }" ${ driveList.menuSeq eq driveMenuSeq ? 'selected' : '' } >${ driveList.menuNm }</option>
									</c:forEach>
								</select>
							</div>
						</li>
		          	</ul>
	        	</nav>
      		</header>
	      	<aside class="sidebar-container">
	        	<div class="sidebar-header" style="display:${kl:decode(sidebarShowheader, 'checked', 'block', 'none')};">
	          		<div class="pull-right pt-lg text-muted hidden">
	          			<em class="ion-close-round"></em>
	          		</div>
	          		<a href="${firstUrl}" class="sidebar-header-logo">
					<%--<a href="/mngwserc/dashboard" class="sidebar-header-logo">--%>
	          			<span class="sidebar-header-logo-text">${siteName}</span>
	          		</a>
	        	</div>
	        	<div class="sidebar-content">
					<div class="text-center" id="sidebar-toolbar" style="display:${kl:decode(sidebarShowtoolbar, 'checked', 'block', 'none')};">
						<c:if test="${fn:length(lnbMenuList) gt 0}">
							<h5 class="header-title text-center"><b>${lnbMenuList[0].menuNm}</b></h5>
						</c:if>
					</div>
	          		<nav class="sidebar-nav">
	            		<ul>
							<c:set var="leftSize" value="${fn:length(lnbMenuList)}" />
							<c:forEach var="left" items="${lnbMenuList}" varStatus="status">
								<c:choose>
									<c:when test="${status.count lt leftSize}">
										<fmt:formatNumber var="nextlevel" value="${lnbMenuList[status.count].dpth}" type="number" />
									</c:when>
									<c:otherwise>
										<fmt:formatNumber var="nextlevel" value="${lnbMenuList[status.count-1].dpth}" type="number" />
									</c:otherwise>
								</c:choose>

								<fmt:formatNumber var="currentlevel" value="${left.dpth}" type="number" />

								<c:if test="${empty nextlevel}">
									<fmt:formatNumber var="nextlevel" value="${currentlevel}" type="number" />
								</c:if>

								<c:if test="${status.index gt 0}">
									<c:choose>
										<c:when test="${not empty cmsRoot and left.menuType ne 'cms'}">
											<c:set var="admUrl" value="javascript:" />
											<c:set var="menuActiveYn" value="N" />
										</c:when>
										<c:otherwise>
											<c:set var="admUrl" value="${kl:nvl(left.admUrl, 'javascript:')}" />
											<c:set var="menuActiveYn" value="Y" />
										</c:otherwise>
									</c:choose>
									<c:if test="${lnbMenuList[status.index-1].dpth lt currentlevel and currentlevel gt 2}">
										<ul class="sidebar-subnav">
									</c:if>
									<li id="leftMenu${left.menuSeq}">
									    <a href="${kl:nvl(admUrl, 'javascript:')}" target="${left.wnppYn eq 'Y' ? '_blank' : '_self'}" <c:if test="${left.wnppYn eq 'Y'}"> rel="noopener noreferrer" </c:if>>
									    	<c:if test="${currentlevel lt nextlevel}">
												<span class="pull-right nav-caret"><em class="ion-ios-arrow-right"></em></span>
											</c:if>
											<span class="pull-right nav-label"></span>
											<span class="highlight ${menuActiveYn eq 'Y' ? 'text-bold ' : 'text-muted '} <c:if test="${pageNo eq left.menuSeq}">text-info</c:if>">
												<c:if test="${currentlevel gt 2}">
													<strong style="font-size:large;">&middot;</strong>&nbsp;
												</c:if>
												${left.menuNm}
											</span>
										</a>
									<c:choose>
										<c:when test="${status.last}">
											<c:forEach var="close" begin="2" end="${currentlevel}" step="1">
													</li>
												</ul>
											</c:forEach>
										</c:when>
										<c:when test="${currentlevel gt nextlevel}">
											<c:forEach var="close" begin="1" end="${currentlevel - nextlevel}" step="1">
													</li>
												</ul>
											</c:forEach>
										</c:when>
										<c:when test="${currentlevel eq nextlevel}">
											</li>
										</c:when>
									</c:choose>
								</c:if>
							</c:forEach>
	            		</ul>
	          		</nav>
	        	</div>
	      	</aside>
	      	<div class="sidebar-layout-obfuscator"></div>

      		<!-- Main section-->
      		<main class="main-container">
      			<!-- Page content-->
        		<section class="form-validate">
