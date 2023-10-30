<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

	<div class="container-fluid">
		<div class="card-body" data-controller="controller/co/COFormCtrl controller/um/uma/UMAFtpUploadListCtrl">
			<h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
			<fieldset class="last-child">
				<div class="form-group text-sm">
					<label class="col-sm-1 control-label">파일 업로드</label>
				</div>
			</fieldset>
			<form class="form-horizontal" id="frmData" name="frmData" method="post">
				<input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

				<fieldset>
					<div class="form-group text-sm">
						<div class="col-sm-10 col-md-11">
							<spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
							<spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
							<div class="dropzone" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-title="PC 첨부파일">
								<div class="dz-default dz-message">
									<span><em class="ion-upload text-info icon-2x"></em><br />Drop files here to upload</span>
								</div>
							</div>
							<p class="text-bold mt">
								※ 1920 X 1080, ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 1개 파일 등록 가능)
							</p>
						</div>
					</div>
					<div class="pull-right">
						<button type="submit" class="btn btn-info btn-sm mb-sm" id="btnInsert">등록</button>
					</div>
				</fieldset>
			</form>
			<form class="form-horizontal" name="frmSearch" id="frmSearch" method="post" action="">
				<!-- 현재 페이징 번호 -->
				<input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
				<!-- 페이징 버튼 사이즈 -->
				<input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
				<input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
				<!-- CSRF KEY -->
				<input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<!-- 상세로 이동시 시퀀스 -->
				<input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.seq}" />
				<input type="hidden" id="langCd" name="langCd" value="${langCd}" />
				<jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
					<jsp:param name="srchText" value="기간" />
					<jsp:param name="periodType" value="csa" />
				</jsp:include>
				<fieldset class="last-child">
					<div class="form-group text-sm">
						<label class="col-sm-1 control-label">검색키워드</label>
						<div class="col-sm-4">
							<div class="row">
								<div class="col-sm-3 pr0">
									<select class="form-control input-sm" data-name="f">
										<option value="">전체</option>
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
					<div class="pull-right">
						<button type="button" class="btn btn-danger btn-sm mb-sm" id="btnDelete">선택삭제</button>
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
								<th>번호</th>
								<th>이미지</th>
								<th>url</th>
								<th>파일 이름</th>
								<th>날짜</th>
								<th>이름</th>
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