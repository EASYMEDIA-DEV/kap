<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

	<div class="container-fluid">
		<div class="card-body" data-controller="controller/eb/eba/EBACheckBoxCtrl controller/eb/ebi/EBINonMemberListCtrl">
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
					<jsp:param name="srchOption" value="등록일,수정일" />
					<jsp:param name="srchType" value="edctn" />
					<jsp:param name="endDtVal" value="${rtnData.endDt}" />
					<jsp:param name="strtDtVal" value="${rtnData.strtDt}" />
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
									<input type="checkbox" class="checkboxSingle ${cdList.cd}" data-name="prntCdList" name="prntCd" value="${cdList.cd}" data-cdnm="${cdList.cdNm}" <c:if test="${fn:contains(rtnData.prntCd, cdList.cd)}">checked</c:if>/>
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
								<c:forEach var="cdList" items="${cdList1}" varStatus="status">
									<label class="checkbox-inline c-checkbox">
										<input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" name="ctgryCd" value="${cdList.cd}"data-cdnm="${cdList.cdNm}" <c:if test="${fn:contains(rtnData.ctgryCd, cdList.cd)}">checked</c:if>/>
										<span class="ion-checkmark-round"></span> ${cdList.cdNm}
									</label>
								</c:forEach>
							</div>
							<div class="cdListContainer CLASS02" style="display:none;margin-bottom: 15px;">
								<div class="row">
									<div class="col-sm-1 pr0 cdnm">

									</div>
								</div>
								<c:forEach var="cdList" items="${cdList2}" varStatus="status">
									<label class="checkbox-inline c-checkbox">
										<input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" name="ctgryCd" value="${cdList.cd}" data-cdnm="${cdList.cdNm}" <c:if test="${fn:contains(rtnData.ctgryCd, cdList.cd)}">checked</c:if>/>
										<span class="ion-checkmark-round"></span> ${cdList.cdNm}
									</label>
								</c:forEach>
							</div>
							<div class="cdListContainer CLASS03" style="display:none;">
								<div class="row">
									<div class="col-sm-1 pr0 cdnm">

									</div>
								</div>
								<c:forEach var="cdList" items="${cdList3}" varStatus="status">
									<label class="checkbox-inline c-checkbox">
										<input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" name="ctgryCd" value="${cdList.cd}" data-cdnm="${cdList.cdNm}" <c:if test="${fn:contains(rtnData.ctgryCd, cdList.cd)}">checked</c:if>/>
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
						<label class="col-sm-1 control-label">학습시간</label>
						<div class="col-sm-6">
							<div class="row">
								<div class="col-sm-1 pr0">
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

								<div class="col-sm-1 pr0">
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
						<label class="col-sm-1 control-label">노출여부</label>
						<div class="col-sm-5">
							<label class="checkbox-inline c-checkbox">
								<input type="checkbox" class="checkboxAll" />
								<span class="ion-checkmark-round"></span> 전체
							</label>
							<label class="checkbox-inline c-checkbox">
								<input type="checkbox" class="checkboxSingle" data-name="expsYnList" value="Y" name="expsYn" <c:if test="${fn:contains(rtnData.expsYnList, 'Y')}">checked</c:if> />
								<span class="ion-checkmark-round"></span> 노출
							</label>
							<label class="checkbox-inline c-checkbox">
								<input type="checkbox" class="checkboxSingle" data-name="expsYnList" value="N" name="expsYn" <c:if test="${fn:contains(rtnData.expsYnList, 'N')}">checked</c:if> />
								<span class="ion-checkmark-round"></span> 미노출
							</label>
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
										<option value="2" <c:if test="${rtnData.f eq '2'}">selected</c:if>>최초 등록자</option>
										<option value="3" <c:if test="${rtnData.f eq '3'}">selected</c:if>>최종 수정자</option>
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
						<button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnExcel">엑셀다운로드</button>
						<button type="button" class="btn btn-danger btn-sm mb-sm" id="btnEdDelete">선택삭제</button>
						<button type="button" class="btn btn-default btn-sm mb-sm" id="btnCopy">복사</button>
						<button type="button" class="btn btn-info btn-sm mb-sm" id="btnWrite">등록</button>
					</div>
				</div>
				<!--VUE 영역 시작 -->
				<div class="table-responsive col-sm-12 p0 m0" id="vueList">
					<table class="table table-hover table-striped" >
						<thead>
							<tr>
								<th class="text-center" rowspan="2">
									<label class="checkbox-inline c-checkbox">
										<input type="checkbox" class="checkboxAll notRequired" title="전체선택" />
										<span class="ion-checkmark-round"></span>
									</label>
								</th>
								<th class="text-center">번호</th>
								<th class="text-center">과정분류</th>
								<th class="text-center">과정명</th>
								<th class="text-center">접수기간</th>
								<th class="text-center">교육기간</th>
								<th class="text-center">학습시간</th>
								<th class="text-center">신청인원</th>
								<th class="text-center">정원</th>
								<th class="text-center">최초 등록자</th>
								<th class="text-center">최초 등록일시</th>
								<th class="text-center">최종 수정자</th>
								<th class="text-center">최종 수정일시</th>
								<th class="text-center">노출여부</th>
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


<!-- 사유 레이어 팝업(Modal) -->
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
					<button type="button" class="btn btn-primary down mt">저장</button>
				</div>
			</div>
		</div>
	</div>
</div>