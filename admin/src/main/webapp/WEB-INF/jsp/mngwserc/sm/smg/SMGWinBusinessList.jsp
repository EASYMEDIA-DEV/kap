<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

	<div class="container-fluid">
		<div class="card-body" data-controller="controller/co/COFormCtrl controller/sm/smg/SMGWinBusinessListCtrl">
			<form class="form-horizontal" id="frmData" name="frmData" method="post" actio="insert">
				<input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<!-- 현재 페이징 번호 -->
				<input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
				<!-- 페이징 버튼 사이즈 -->
				<input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
				<input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
				<!-- CSRF KEY -->
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
				<div class="table-responsive col-sm-12 p0 m0" id="vueList">
					<table class="table table-hover table-striped" >
						<tbody>
							<tr>
								<th>사업명</th>
								<th>안내메시지</th>
								<th>링크 url</th>
								<th>노출여부</th>
								<th>노출순서</th>
								<th>삭제</th>
							</tr>
						</tbody>
						<!-- 리스트 목록 결과 -->
						<c:choose>
							<c:when test="${ not empty rtnData.list}">
								<c:forEach var="list" items="${rtnData.list}" varStatus="status">
									<tr class="bus${list.seq}" data-seq="${list.seq}">
										<td style="display: none">
											<label class="checkbox-inline c-checkbox">
												<input type="checkbox" value="${list.seq}" name="delValueList" class="checkboxSingle checkbox${list.seq} notRequired " />
												<span class="ion-checkmark-round"></span>
											</label>
										</td>
										<td><input type="text" class="form-control input-sm title" title="사업명" name="titl" maxlength="10" placeholder="사업명" value="${list.titl}"/></td>
										<td><input type="text" class="form-control input-sm cntn" title="안내 메시지" name="cntn" maxlength="50" placeholder="안내 메시지" value="${list.cntn}"/></td>
										<td><input type="text" class="form-control input-sm urlChk" title="링크" name="linkUrl" placeholder="링크 URL" value ="${list.linkUrl}"/></td>
										<td>
											<c:set var="mainYn" value="${kl:nvl(list.mainYn, 'Y')}" />
											<select class="form-control input-sm" name="mainYn">
												<option value="Y" <c:if test="${mainYn eq 'Y'}">checked</c:if>>노출</option>
												<option value="N" <c:if test="${mainYn eq 'N'}">checked</c:if>>미노출</option>
											</select>
											</select>
										</td>
										<td class="text-center" data-key="${list.seq}" data-value="${list.ord}">
											<button type="button" class="btn btn-default btn-xs sortUp" name="sortUp" id="btnSort"><i class="ion-arrow-up-b"></i></button>
											<button type="button" class="btn btn-default btn-xs ml-sm sortDown" name="sortDown" id="btnSort"><i class="ion-arrow-down-b"></i></button>
										</td>
										<td><button type="button" class="btn btn-danger btn-sm mb-sm btnDeleteCheck" data-seq="${list.seq}">삭제</button></td>
									</tr>
									</form><form class="form-horizontal" id="${list.seq}" name="${list.seq}" method="post">
								</c:forEach>
							</c:when>
						</c:choose>
						<tbody id="temp">
						<tr id="copyArea" class="dataArea">
							<td><input type="text" class="form-control input-sm title" title="사업명" name="titl" maxlength="10" placeholder="사업명" value=""/></td>
							<td><input type="text" class="form-control input-sm cntn" title="안내 메시지" name="cntn" maxlength="50" placeholder="안내 메시지" value=""/></td>
							<td><input type="text" class="form-control input-sm urlChk" title="링크" name="linkUrl" placeholder="링크 URL" value =""/></td>
							<td style="display: none"><input type="hidden" class="mainYnVal" name="mainYn" value ="Y"/></td>
							<td>
								<select class="form-control input-sm mainYn" name="mainYn">
									<option value="Y">노출</option>
									<option value="N">미노출</option>
								</select>
							</td>
							<td class="text-center" data-key="${list.seq}" data-value="${list.ord}">
								<button type="button" class="btn btn-default btn-xs sortUp" name="sortUp" id="btnSort"><i class="ion-arrow-up-b"></i></button>
								<button type="button" class="btn btn-default btn-xs ml-sm sortDown" name="sortDown" id="btnSort"><i class="ion-arrow-down-b"></i></button>
							</td>
							<td><button type="button" class="btn btn-danger btn-sm mb-sm btnDeleteCheck" id="">삭제</button></td>
						</tr>
						</tbody>
					</table>
					<!-- 페이징 버튼 -->
					<div id="pagingContainer"/>
				</div>
				<!--리스트 종료 -->
				<div class="pull-left">
					<button type="button" class="btn btn-info btn-sm mb-sm busPlus">사업추가</button>
				</div>
				<div class="pull-right">
					<button type="button" class="btn btn-warning btn-sm mb-sm saveBtn">저장</button>
				</div>
			</form>
		</div>
	</div>