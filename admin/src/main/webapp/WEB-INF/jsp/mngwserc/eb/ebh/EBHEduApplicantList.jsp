<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="container-fluid">
	<div class="card-body" data-controller="controller/co/COFormCtrl controller/eb/ebh/EBHEduApplicantListCtrl">
		<h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 검색</h6>
		<form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="none">
			<!-- 현재 페이징 번호 -->
			<input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
			<!-- 페이징 버튼 사이즈 -->
			<input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
			<input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
			<!-- CSRF KEY -->
			<input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

			<!--복사유무-->
			<input type="hidden" id="copyYn" name="copyYn" value="N" />

			<!-- 상세로 이동시 시퀀스 -->
			<input type="hidden" id="detailsKey" name="detailsKey" value="" />
			<!--기간 검색 시작-->
			<jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
				<jsp:param name="srchText" value="기간검색" />
				<jsp:param name="srchOption" value="교육기간,접수기간,등록일,수정일" />
				<jsp:param name="srchType" value="edctn" />
			</jsp:include>
			<!--기간 검색 종료-->

			<fieldset>
				<div class="form-group text-sm">
					<label class="col-sm-1 control-label">과정분류</label>
					<div class="col-sm-11"  style="margin-bottom: 15px;">
						<label class="checkbox-inline c-checkbox classType">
							<input type="checkbox" class="checkboxAll" />
							<span class="ion-checkmark-round"></span> 전체
						</label>
						<c:forEach var="cdList" items="${classTypeList.CLASS_TYPE}" varStatus="status">
							<label class="checkbox-inline c-checkbox classType">
								<input type="checkbox" class="checkboxSingle" data-name="prntCdList" name="prntCd" value="${cdList.cd}" data-cdnm="${cdList.cdNm}"/>
								<span class="ion-checkmark-round"></span> ${cdList.cdNm}
							</label>
						</c:forEach>
					</div>
					<!--과정분류 2뎁스 생성구간 시작-->
					<label class="col-sm-1 control-label"></label>
					<div class="col-sm-11 detailCdList">
						<!-- 시작 -->
						<div class="cdListContainer CLASS01" style="display:none;margin-bottom: 15px;">
							<div class="row">
								<div class="col-sm-1 pr0 cdnm">
								</div>
							</div>
							<c:forEach  var="cdList" items="${cdList1}" varStatus="status">
								<c:if test="${status.count % 11 eq 0}">
									<br />
								</c:if>
								<label class="checkbox-inline c-checkbox">
									<input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" name="ctgryCd" value="${cdList.cd}"disabled="true"/>
									<span class="ion-checkmark-round"></span> ${cdList.cdNm}
								</label>
							</c:forEach>
						</div>
						<div  class="cdListContainer CLASS02" style="display:none;margin-bottom: 15px;">
							<div class="row">
								<div class="col-sm-1 pr0 cdnm">
								</div>
							</div>
							<c:forEach  var="cdList" items="${cdList2}" varStatus="status">
								<c:if test="${status.count % 11 eq 0}">
									<br />
								</c:if>
								<label class="checkbox-inline c-checkbox">
									<input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" name="ctgryCd" value="${cdList.cd}" disabled="true"/>
									<span class="ion-checkmark-round"></span> ${cdList.cdNm}
								</label>
							</c:forEach>
						</div>
						<div class="cdListContainer CLASS03" style="display:none;">
							<div class="row">
								<div class="col-sm-1 pr0 cdnm">
								</div>
							</div>
							<c:forEach  var="cdList" items="${cdList3}" varStatus="status">
								<c:if test="${status.count % 11 eq 0}">
									<br />
								</c:if>
								<label class="checkbox-inline c-checkbox">
									<input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" name="ctgryCd" value="${cdList.cd}" disabled="true"/>
									<span class="ion-checkmark-round"></span> ${cdList.cdNm}
								</label>
							</c:forEach>
						</div>
						<!-- 끝 -->
					</div>
					<!--과정분류 2뎁스 생성구간 끝-->
				</div>
			</fieldset>

			<fieldset>
				<div class="form-group text-sm">
					<label class="col-sm-1 control-label">학습방식</label>
					<div class="col-sm-5">
						<label class="checkbox-inline c-checkbox">
							<input type="checkbox" class="checkboxAll" />
							<span class="ion-checkmark-round"></span> 전체
						</label>
						<c:forEach var="cdList" items="${classTypeList.STDUY_MTHD}" varStatus="status">
							<c:if test="${status.count % 11 eq 0}">
								<br />
							</c:if>
							<label class="checkbox-inline c-checkbox">
								<input type="checkbox" class="checkboxSingle" data-name="stduyMthdCdList" value="${cdList.cd}" name="stduyMthdCd" <c:if test="${fn:contains(rtnData.stduyMthdCd, cdList.cd)}">checked</c:if> />
								<span class="ion-checkmark-round"></span> ${cdList.cdNm}
							</label>
						</c:forEach>
					</div>
				</div>
			</fieldset>

			<fieldset>
				<div class="form-group text-sm">
					<label class="col-sm-1 control-label">학습시간</label>
					<div class="col-sm-6">
						<div class="row">
							<div class="col-sm-1 pr0" style="padding-top: 7px">
								학습일
							</div>
							<div class="col-sm-3 pl0">
								<select class="form-control input-sm" name="stduyDdCd">
									<option value="">전체</option>
									<c:forEach var="cdList" items="${classTypeList.STDUY_DD}" varStatus="status">
										<option value="${cdList.cd}" <c:if test="${rtnData.stduyDdCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-1 pr0" style="padding-top: 7px">
								학습시간
							</div>
							<div class="col-sm-3 pl0">
								<select class="form-control input-sm" name="stduyTimeCd">
									<option value="">전체</option>
									<c:forEach var="cdList" items="${classTypeList.STDUY_TIME}" varStatus="status">
										<option value="${cdList.cd}" <c:if test="${rtnData.stduyTimeCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
				</div>
			</fieldset>

			<fieldset>
				<div class="form-group text-sm">
					<label class="col-sm-1 control-label">부품사 구분</label>
					<div class="col-sm-5">
						<label class="checkbox-inline c-checkbox">
							<input type="checkbox" class="checkboxAll" />
							<span class="ion-checkmark-round"></span> 전체
						</label>
						<c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
							<c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY01')}">
								<c:if test="${status.count % 11 eq 0}">
									<br />
								</c:if>
								<label class="checkbox-inline c-checkbox">
									<input type="checkbox" class="checkboxSingle" name="partsCtgryCd" data-name="partsCtgryCdList" value="${cdList.cd}" />
									<span class="ion-checkmark-round"></span> ${cdList.cdNm}
								</label>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</fieldset>

			<fieldset>
				<div class="form-group text-sm">
					<label class="col-sm-1 control-label">선발 구분</label>
					<div class="col-sm-5">
						<label class="checkbox-inline c-checkbox">
							<input type="checkbox" class="checkboxAll" />
							<span class="ion-checkmark-round"></span> 전체
						</label>
						<c:forEach var="cdList" items="${cdDtlList.EDU_STTS_CD}" varStatus="status">
							<c:if test="${status.count % 11 eq 0}">
								<br />
							</c:if>
							<c:choose>
								<c:when test="${fn:contains(cdList.cd, '01')}">
									<c:set var="cdName" value="선발"/>
								</c:when>
								<c:when test="${fn:contains(cdList.cd, '02')}">
									<c:set var="cdName" value="신청취소"/>
								</c:when>
								<c:when test="${fn:contains(cdList.cd, '03')}">
									<c:set var="cdName" value="신청양도"/>
								</c:when>
								<c:when test="${fn:contains(cdList.cd, '04')}">
									<c:set var="cdName" value="선발대기"/>
								</c:when>
								<c:when test="${fn:contains(cdList.cd, '05')}">
									<c:set var="cdName" value="미선발"/>
								</c:when>
								<c:when test="${fn:contains(cdList.cd, '06')}">
									<c:set var="cdName" value="교육취소"/>
								</c:when>
								<c:otherwise>
									<c:set var="cdName" value="알 수 없음"/>
								</c:otherwise>
							</c:choose>
							<label class="checkbox-inline c-checkbox">
								<input type="checkbox" class="checkboxSingle" name="choiceCtgryCd" data-name="choiceCtgryCdList" value="${cdList.cd}" />
								<span class="ion-checkmark-round"></span> ${cdName}
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
									<option value="1" <c:if test="${rtnData.f eq '1'}">selected</c:if>>과정명</option>
									<option value="2" <c:if test="${rtnData.f eq '2'}">selected</c:if>>회원 이름</option>
									<option value="3" <c:if test="${rtnData.f eq '3'}">selected</c:if>>회원 연락처</option>
									<option value="4" <c:if test="${rtnData.f eq '4'}">selected</c:if>>회원 이메일</option>
									<option value="5" <c:if test="${rtnData.f eq '5'}">selected</c:if>>부품사명</option>
									<option value="6" <c:if test="${rtnData.f eq '6'}">selected</c:if>>사업자 등록번호</option>
									<option value="7" <c:if test="${rtnData.f eq '7'}">selected</c:if>>최종 수정자</option>
									<option value="8" <c:if test="${rtnData.f eq '8'}">selected</c:if>>최종 수정일시</option>
								</select>
							</div>
							<div class="col-sm-9 pr0">
								<input type="text" class="form-control input-sm" data-name="q" value="${rtnData.q}" maxlength="50" />
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
				<div class="pull-right">
					<button type="button" class="btn btn-success btn-sm mb-sm" id="btnChoice" style="width: 60px">선발</button>
					<button type="button" class="btn btn-danger btn-sm mb-sm" id="btnNotChoice" style="width: 60px">미선발</button>
				</div>
			</div>
			<!--VUE 영역 시작 -->
			<div class="table-responsive col-sm-12 p0 m0" id="vueList">
				<table class="table table-hover table-striped">
					<thead>
						<tr>
							<th class="text-center" rowspan="2">
								<label class="checkbox-inline c-checkbox">
									<input type="checkbox" class="checkboxAll notRequired" title="전체선택" />
									<span class="ion-checkmark-round"></span>
								</label>
							</th>
							<th class="text-center" rowspan="2">번호</th>
							<th class="text-center" rowspan="2">선발상태</th>
							<th class="text-center" rowspan="2">과정분류</th>
							<th class="text-center" rowspan="2">과정명</th>
							<th class="text-center" rowspan="2">학습방식</th>
							<th class="text-center" rowspan="2">학습시간</th>
							<th class="text-center" rowspan="2">년도</th>
							<th class="text-center" rowspan="2">회차</th>
							<th class="text-center" rowspan="2">업종</th>
							<th class="text-center" rowspan="2">부품사명</th>
							<th class="text-center" rowspan="2">구분</th>
							<th class="text-center" rowspan="2">규모</th>
							<th class="text-center" rowspan="2">사업자등록번호</th>
							<th class="text-center" rowspan="2">지역</th>
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