<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

	<div class="container-fluid">
		<div class="card-body" data-controller="controller/co/COFormCtrl controller/eb/ebg/EBGSqCertiConfrimListCtrl">
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
				<!-- EBDSqCertiReq와 리스트를 같이 사용하려고 한다.(발급건만 조회) -->
				<input type="hidden" name="issueCdList" value="EBD_SQ_I" />
				<!--기간 검색 시작-->
				<jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
					<jsp:param name="srchText" value="기간검색" />
					<jsp:param name="srchOption" value="취득일,만료예정일,수정일" />
				</jsp:include>
				<fieldset>
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">부품사 구분</label>
						<div class="col-sm-5">
							<label class="checkbox-inline c-checkbox">
								<input type="checkbox" class="checkboxAll" />
								<span class="ion-checkmark-round"></span> 전체
							</label>
							<c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
								<c:if test="${ cdList.cd eq 'COMPANY01001' or cdList.cd eq 'COMPANY01002' }">
									<label class="checkbox-inline c-checkbox">
										<input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" value="${cdList.cd}" <c:if test="${fn:contains(rtnData.ctgryCdList, cdList.cd)}">checked</c:if> />
										<span class="ion-checkmark-round"></span> ${cdList.cdNm}
									</label>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</fieldset>
				<fieldset>
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">자격증 상태</label>
						<div class="col-sm-5">
							<label class="checkbox-inline c-checkbox">
								<input type="checkbox" class="checkboxAll" />
								<span class="ion-checkmark-round"></span> 전체
							</label>
							<label class="checkbox-inline c-checkbox">
								<input type="checkbox" class="checkboxSingle" data-name="certiSttsList" value="0" <c:if test="${fn:contains(rtnData.certiSttsList, '0')}">checked</c:if> />
								<span class="ion-checkmark-round"></span> 만료
							</label>
							<label class="checkbox-inline c-checkbox">
								<input type="checkbox" class="checkboxSingle" data-name="certiSttsList" value="1" <c:if test="${fn:contains(rtnData.certiSttsList, '1')}">checked</c:if> />
								<span class="ion-checkmark-round"></span> 정상
							</label>
							<label class="checkbox-inline c-checkbox">
								<input type="checkbox" class="checkboxSingle" data-name="certiSttsList" value="2" <c:if test="${fn:contains(rtnData.certiSttsList, '2')}">checked</c:if> />
								<span class="ion-checkmark-round"></span> 중지
							</label>
						</div>
					</div>
				</fieldset>
				<fieldset>
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">SQ평가원 구분</label>
						<div class="col-sm-5">
							<label class="checkbox-inline c-checkbox">
								<input type="checkbox" class="checkboxAll" />
								<span class="ion-checkmark-round"></span> 전체
							</label>
							<c:forEach var="cdList" items="${cdDtlList.EBD_SQ_TP}" varStatus="status">
								<label class="checkbox-inline c-checkbox">
									<input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" value="${cdList.cd}" <c:if test="${fn:contains(rtnData.examCdList, cdList.cd)}">checked</c:if> />
									<span class="ion-checkmark-round"></span> ${cdList.cdNm}
								</label>
							</c:forEach>
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
							<col width="100px">
							<col width="100px">
							<col width="100px">
							<col width="100px">
							<col width="150px">
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
								<th class="text-center" rowspan="2">번호</th>
								<th class="text-center" rowspan="2">자격증상태</th>
								<th class="text-center" rowspan="2">SQ평가원</br>구분</th>
								<th class="text-center" rowspan="2">부품사명</th>
								<th class="text-center" rowspan="2">구분</th>
								<th class="text-center" rowspan="2">규모</th>
								<th class="text-center" rowspan="2">사업자등록번호</th>
								<th class="text-center" colspan="3">신청자정보</th>
								<th class="text-center" rowspan="2">취득일시</th>
								<th class="text-center" rowspan="2">만료 예정일</th>
								<th class="text-center" rowspan="2">최종 수정자</th>
								<th class="text-center" rowspan="2">최종 수정일시</th>
							</tr>
							<tr>
								<th class="text-center">이름(아이디)</th>
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
