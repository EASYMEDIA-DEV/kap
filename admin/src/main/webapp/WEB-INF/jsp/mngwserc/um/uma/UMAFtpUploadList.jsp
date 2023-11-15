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
							<spring:eval var="imageExtns" expression="@environment.getProperty('app.file.imageExtns')" />
							<div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq" data-file-extn="${imageExtns}" data-max-file-size="5242880" data-max-file-cnt="1" data-title="파일 업로드">
								<div class="dz-default dz-message">
									<span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
								</div>
							</div>
							<p class="text-bold mt">
								※ 파일 확장자(${imageExtns}) / 최대 용량 (5MB) / 최대 개수 (1개)
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

				<hr class="mt0" />
				<div class="clearfix">
					<h6 class="pull-left mt0">
						<em class="ion-play mr-sm"></em>${pageTitle} 이미지 목록
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
								<th>이미지 파일명</th>
								<th>이미지 url</th>
								<th>최초 등록자</th>
								<th>최초 등록일시</th>
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