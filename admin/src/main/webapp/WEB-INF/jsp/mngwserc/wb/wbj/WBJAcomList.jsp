<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

	<div class="container-fluid">
		<div class="card-body" data-controller="controller/co/COFormCtrl controller/wb/wbj/WBJAcomListCtrl">
			<h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 검색</h6>
			<form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="account">
				<!-- 현재 페이징 번호 -->
				<input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
				<!-- 페이징 버튼 사이즈 -->
				<input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
				<input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
				<!-- CSRF KEY -->
				<input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<!-- 상세로 이동시 시퀀스 -->
				<input type="hidden" id="detailsKey" name="detailsKey" value="" />
				<input type="hidden" id="workBsnmNo" name="workBsnmNo" value="" />
				<input type="hidden" id="year" name="year" value="" />
				<!--기간 검색 시작-->
				<jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
					<jsp:param name="srchText" value="기간검색" />
					<jsp:param name="srchType" value="wbjb" />
					<jsp:param name="srchOption" value="접수기간,최초등록일시,최종수정일시" />
				</jsp:include>
				<!--기간 검색 종료-->
				<fieldset>
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">사업연도</label>
						<div class="col-sm-11"  style="margin-bottom: 15px; width: 150px;">
							<select class="form-control input-sm" id="optYear" name="optYear" data-name="optYear">
								<option value="">연도 전체</option>
								<c:forEach var="optYear" items="${optYearList}" varStatus="status">
									<option value="${optYear}">${optYear}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</fieldset>

				<fieldset>
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">구분</label>
						<div class="col-sm-11"  style="margin-bottom: 15px;">
							<label class="checkbox-inline c-checkbox classType">
								<input type="checkbox" class="checkboxAll" name=""/>
								<span class="ion-checkmark-round"></span> 전체
							</label>
							<c:forEach var="cdList" items="${typeList.COMPANY_TYPE}" varStatus="status">
								<c:if test="${cdList.cd eq 'COMPANY01001' or cdList.cd eq 'COMPANY01002'}">
									<label class="checkbox-inline c-checkbox classType">
										<input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" name="cd" value="${cdList.cd}" <c:if test="${fn:contains(rtnData.ctgryCdList, cdList.cd)}">checked</c:if>/>
										<span class="ion-checkmark-round"></span> ${cdList.cdNm}
									</label>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</fieldset>

				<fieldset>
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">포상부문</label>
						<div class="col-sm-11"  style="margin-bottom: 15px;">
							<label class="checkbox-inline c-checkbox classType">
								<input type="checkbox" class="checkboxAll" />
								<span class="ion-checkmark-round"></span> 전체
							</label>
							<c:forEach var="cdList" items="${classTypeList.MNGCNSLT_DIS}" varStatus="status">
								<label class="checkbox-inline c-checkbox classType">
									<input type="checkbox" class="checkboxSingle" data-name="mrtsCdList" name="cd" value="${cdList.cd}"/>
									<span class="ion-checkmark-round"></span> ${cdList.cdNm}
								</label>
							</c:forEach>
						</div>
					</div>
				</fieldset>

				<fieldset>
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">훈격</label>
						<div class="col-sm-11"  style="margin-bottom: 15px;">
							<label class="checkbox-inline c-checkbox classType">
								<input type="checkbox" class="checkboxAll" />
								<span class="ion-checkmark-round"></span> 전체
							</label>
							<c:forEach var="cdList" items="${classTypeList.MNGCNSLT_REW}" varStatus="status">
								<label class="checkbox-inline c-checkbox classType">
									<input type="checkbox" class="checkboxSingle" data-name="prizeCdList" name="cd" value="${cdList.cd}"/>
									<span class="ion-checkmark-round"></span> ${cdList.cdNm}
								</label>
							</c:forEach>
						</div>
					</div>
				</fieldset>

				<fieldset>
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">1차결과</label>
						<div class="col-sm-11"  style="margin-bottom: 15px;">
							<label class="checkbox-inline c-checkbox classType">
								<input type="checkbox" class="checkboxAll" />
								<span class="ion-checkmark-round"></span> 전체
							</label>
							<c:forEach var="cdList" items="${resultList.PRO_TYPE}" varStatus="status">
								<c:if test="${cdList.cd eq 'PRO_TYPE05001_01_003' or cdList.cd eq 'PRO_TYPE05001_01_002'}">
									<label class="checkbox-inline c-checkbox classType">
										<input type="checkbox" class="checkboxSingle" data-name="appctnSttsCdList" name="cd" value="${cdList.cd}"/>
										<span class="ion-checkmark-round"></span> ${cdList.cdNm}
									</label>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</fieldset>

				<fieldset>
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">최종결과</label>
						<div class="col-sm-11"  style="margin-bottom: 15px;">
							<label class="checkbox-inline c-checkbox classType">
								<input type="checkbox" class="checkboxAll" />
								<span class="ion-checkmark-round"></span> 전체
							</label>
							<c:forEach var="cdList" items="${resultList.PRO_TYPE}" varStatus="status">
								<c:if test="${cdList.cd eq 'PRO_TYPE05002_01_004' or cdList.cd eq 'PRO_TYPE05002_01_003' or cdList.cd eq 'PRO_TYPE05002_01_002'}">
									<label class="checkbox-inline c-checkbox classType">
										<input type="checkbox" class="checkboxSingle" data-name="mngSttsCdList" name="cd" value="${cdList.cd}"/>
										<span class="ion-checkmark-round"></span> ${cdList.cdNm}
									</label>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</fieldset>


				<fieldset class="last-child">
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">검색키워드</label>
						<div class="col-sm-6">
							<div class="row">
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
						<button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnExcelDown">엑셀다운로드</button>
						<button type="button" class="btn btn-danger btn-sm mb-sm" id="btnChooseDelete">선택삭제</button>
						<button type="button" class="btn btn-info btn-sm mb-sm" id="btnWrite">등록</button>
					</div>
				</div>

				<!--VUE 영역 시작 -->
				<div class="table-responsive col-sm-12 p0 m0" id="vueList">
					<table class="table table-hover table-striped" >
						<thead>
							<tr>
								<th class="text-center">
									<label class="checkbox-inline c-checkbox">
										<input type="checkbox" class="checkboxAll notRequired" title="전체선택" />
										<span class="ion-checkmark-round"></span>
									</label>
								</th>
								<th class="text-center">번호</th>
								<th class="text-center">사업연도</th>
								<th class="text-center">부품사명</th>
								<th class="text-center">구분</th>
								<th class="text-center">규모</th>
								<th class="text-center">사업자등록번호</th>
								<th class="text-center">훈격</th>
								<th class="text-center">포상부문</th>
								<th class="text-center">포상대상자</th>
								<th class="text-center">핸드폰번호</th>
								<th class="text-center">1차결과</th>
								<th class="text-center">최종결과</th>
								<th class="text-center">최종 수정자(아이디)</th>
								<th class="text-center">최종 수정일시</th>
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

<div class="modal fade excel-down" tabindex="-1" role="dialog" >
	<div class="modal-dialog modal-lg modal-center" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" >▣ 엑셀 다운로드
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</h5>
			</div>
			<div class="modal-body">
				<div class="form-group ">
					<p><em class="ion-play mr-sm"></em>사유입력</p>
					<div class="col-sm-12">
						<textarea maxlength="30" class="col-sm-12 pv" style="resize: vertical;" rows="10" placeholder="사유를 입력하세요." id="rsn" title="사유" oninput="cmmCtrl.checkMaxlength(this);"></textarea>
					</div>
				</div>
			</div>
			<div class="modal-footer row">
				<div class="text-center">
					<button type="button" class="btn btn-primary down mt">다운로드</button>
				</div>
			</div>
		</div>
	</div>
</div>