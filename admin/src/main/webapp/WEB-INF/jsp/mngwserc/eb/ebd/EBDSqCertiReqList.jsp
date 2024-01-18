<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

	<div class="container-fluid">
		<div class="card-body" data-controller="controller/co/COFormCtrl controller/eb/ebd/EBDSqCertiReqListCtrl">
			<h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 검색</h6>
			<form class="form-horizontal" name="frmSearch" method="post" action="" >
				<!-- 현재 페이징 번호 -->
				<input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
				<!-- 페이징 버튼 사이즈 -->
				<input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
				<input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
				<!-- CSRF KEY -->
				<input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<!-- 상세로 이동시 시퀀스 -->
				<input type="hidden" id="detailsKey" name="detailsKey" value="" />
				<!--기간 검색 시작-->
				<jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
					<jsp:param name="srchText" value="신청/수정기간" />
					<jsp:param name="srchOption" value="신청일,수정일" />
				</jsp:include>
				<fieldset>
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">발급상태</label>
						<div class="col-sm-5">
							<label class="checkbox-inline c-checkbox">
								<input type="checkbox" class="checkboxAll" />
								<span class="ion-checkmark-round"></span> 전체
							</label>
							<c:forEach var="cdList" items="${cdDtlList.EBD_SQ}" varStatus="status">
								<label class="checkbox-inline c-checkbox">
									<input type="checkbox" class="checkboxSingle" data-name="issueCdList" value="${cdList.cd}" <c:if test="${fn:contains(rtnData.issueCdList, cdList.cd)}">checked</c:if> />
									<span class="ion-checkmark-round"></span> ${cdList.cdNm}
								</label>
							</c:forEach>
						</div>
					</div>
				</fieldset>
				<fieldset>
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">양성과정 참여</label>
						<div class="col-sm-11 form-inline">
							<div class="form-group mr-sm">
								<select class="form-control input-sm wd-sm notRequired" data-name="episdYear" id="episdYear" title="년도" style="min-width: 100px;">
									<option value="">선택</option>
									<c:forEach var="cdList" items="${cdDtlList.CO_YEAR_CD}" varStatus="status">
										<option value="${cdList.cd}" <c:if test="${rtnData.episdYear eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
									</c:forEach>
								</select>
								<select class="form-control input-sm wd-sm notRequired" data-name="episdOrd" id="episdOrd" title="회차" style="min-width: 100px;">
									<option value="">선택</option>
									<c:forEach var="cdList" items="${cdDtlList.ROUND_CD}" varStatus="status">
										<option value="${cdList.cd}" <c:if test="${rtnData.episdOrd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group mr-sm">

							</div>
						</div>
					</div>
				</fieldset>
				<fieldset class="last-child">
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">검색키워드</label>
						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-3 pr0">
									<select class="form-control input-sm" data-name="f">
										<option value="">전체</option>
										<!--<option value="1" <c:if test="${rtnData.f eq '1'}">selected</c:if>>과정명</option> 이가은 사원 요청(2023.11.16)-->
										<option value="2" <c:if test="${rtnData.f eq '2'}">selected</c:if>>회원 이름</option>
										<option value="3" <c:if test="${rtnData.f eq '3'}">selected</c:if>>회원 연락처</option>
										<option value="4" <c:if test="${rtnData.f eq '4'}">selected</c:if>>회원 이메일</option>
										<option value="5" <c:if test="${rtnData.f eq '5'}">selected</c:if>>부품사명</option>
										<option value="6" <c:if test="${rtnData.f eq '6'}">selected</c:if>>사업자등록번호</option>
										<option value="7" <c:if test="${rtnData.f eq '7'}">selected</c:if>>최종 수정자</option>
									</select>
								</div>
								<div class="col-sm-9 pr0">
									<input type="text" class="form-control input-sm" id="q" data-name="q" value="${rtnData.q}" maxlength="50" />
								</div>
							</div>
						</div>
						<div class="pull-left ml-sm">
							<button type="button" class="btn btn-inverse btn-sm" id="btnSearch">검색</button>
							<button type="button" class="btn btn-default btn-sm" id="btnRefresh">초기화</button>
						</div>
					</div>
				</fieldset>

				<hr class="mt0" />

				<div class="clearfix">
					<h6 class="pull-left mt0">
						<em class="ion-play mr-sm"></em>${pageTitle} 목록 (총 <span id="listContainerTotCnt">0</span> 건)
					</h6>
					<div class="pull-right ml-sm">
						<select class="form-control input-sm listRowSizeContainer" >
							<jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
								<jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
							</jsp:include>
						</select>
					</div>
				</div>
				<!--VUE 영역 시작 -->
				<div class="col-sm-12 p0 m0 table-responsive-scroll" >
					<table class="table table-hover table-striped text-sm ">
						<colgroup>
							<col width="50px">
							<col width="100px">
							<col width="100px">
							<col width="100px">
							<col width="100px">
							<col width="200px">
							<col width="100px">
							<col width="100px">
							<col width="100px">
							<col width="100px">
							<col width="100px">
							<col width="200px">
							<col width="200px">
						</colgroup>
						<thead>
							<tr>
								<th class="text-center " rowspan="2">
									<label class="checkbox-inline c-checkbox">
										<input type="checkbox" class="checkboxAll notRequired" title="전체선택" />
										<span class="ion-checkmark-round"></span>
									</label>
								</th>
								<th class="text-center" rowspan="2">번호</th>
								<th class="text-center" rowspan="2">발급상태</th>
								<th class="text-center" rowspan="2">년도</th>
								<th class="text-center" rowspan="2">회차</th>
								<th class="text-center" rowspan="2">부품사명</th>
								<th class="text-center" rowspan="2">사업자등록번호</th>
								<th class="text-center" rowspan="2">구분</th>
								<th class="text-center" rowspan="2">규모</th>
								<th class="text-center" colspan="4">신청자정보</th>
								<th class="text-center" rowspan="2">신청일시</th>
								<th class="text-center" rowspan="2">최종 수정자</th>
								<th class="text-center" rowspan="2">최종 수정일시</th>
							</tr>
							<tr>
								<th class="text-center">이름(아이디)</th>
								<th class="text-center">GPC 아이디</th>
								<th class="text-center">휴대폰번호</th>
								<th class="text-center">이메일</th>
							</tr>
						</thead>
						<!-- 리스트 목록 결과 -->
						<tbody id="listContainer"/>
					</table>
					<!-- 페이징 버튼 -->
					<div id="pagingContainer"/>
				</div>
				<!--리스트 종료 -->
			</form>
		</div>
	</div>
