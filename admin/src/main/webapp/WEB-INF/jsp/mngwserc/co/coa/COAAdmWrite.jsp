<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
	<div class="card-body" data-controller="controller/co/coa/COAAdmWriteCtrl">
		<h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} ${empty rtnData.detailsKey ? "등록" : "상세/수정"}</h6>
		<form class="form-horizontal" id="frmData" name="frmData" method="post" action="${empty rtnData.detailsKey ? 'insert' : 'update'}">
			<input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnData.detailsKey}" />
			<input type="hidden" class="notRequired" id="mChecked" name="mChecked" value="" />
			<input type="hidden" class="notRequired" id="mUndetermined" name="mUndetermined" value="" />
			<input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

			<input type="hidden" class="notRequired" id="typeCd" name="typeCd" value="${rtnDto.typeCd }">
			<input type="hidden" class="notRequired" id="lgnSsnId" value="${rtnData.lgnSsnId}">

			<div class="col-sm-4 p0">
				<div id="divCategoris" style="height:700px; overflow-y:auto;">

				</div>
			</div>
			<div class="col-sm-8 p0">
				<div class="p-lg">
					<fieldset>
						<div class="form-group text-sm">
							<label class="col-sm-2 control-label">권한1<span class="star"> *</span></label>
							<div class="col-sm-10">
								<select class="form-control input-sm wd-sm" id="authCd" name="authCd" title="권한">
									<option value="">선택</option>
									<c:forEach var="cdList" items="${cdDtlList.ADMIN_AUTH_CD}" varStatus="status">
										<option value="${cdList.cd}" <c:if test="${rtnDto.authCd eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
									</c:forEach>
								</select>
								<strong class="help-block mt-sm mb0">※ 최고 관리자는 메뉴 선택에 상관없이 모든 메뉴를 관리할 수 있습니다.</strong>
							</div>
						</div>
					</fieldset>
					<fieldset>
						<div class="form-group text-sm">
							<label class="col-sm-2 control-label">이름<span class="star text-danger"> *</span></label>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${not empty rtnInfo}">
										<input type="hidden" name="name" value="${rtnDto.name}" title="이름"/>
										<p class="form-control-static">${rtnDto.name}</p>
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control input-sm" id="name" name="name"  title="이름" maxlength="10" placeholder="이름을 입력하세요." oninput="this.value=this.value.replace(/[^ㄱ-힣a-zA-Z]/gi,'');"/>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</fieldset>
					<fieldset>
						<div class="form-group text-sm">
							<label class="col-sm-2 control-label">부서명<span class="star text-danger"> *</span></label>
							<div class="col-sm-10">
								<input type="text" class="form-control input-sm" id="deptNm" name="deptNm" value="${rtnDto.deptNm}" title="부서명" maxlength="20" placeholder="부서명을 입력하세요." oninput="this.value=this.value.replace(/[^ㄱ-힣a-zA-Z0-9]/gi,'');"/>
							</div>
						</div>
					</fieldset>
					<fieldset>
						<div class="form-group text-sm">
							<label class="col-sm-2 control-label">아이디<span class="star text-danger"> *</span></label>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${not empty rtnInfo}">
										<input type="hidden" name="id" value="${rtnDto.id}" title="아이디" />
										<p class="form-control-static">${rtnDto.id}</p>
									</c:when>
									<c:otherwise>
										<div class="input-group">
											<input type="text" class="form-control input-sm idChk" id="id" name="id" title="아이디" maxlength="12" autocomplete="off" placeholder="아이디를 입력하세요." oninput="this.value=this.value.replace(/[^a-zA-Z0-9]/gi,'');"/>
											<span class="input-group-btn"><button type="button" class="btn btn-default btn-sm" id="btnIdOvrlChk" title="아이디">중복확인</button></span>
										</div>
										<span class="help-block mt-sm mb0">※ 6~12자 이내 영문, 영문/숫자 조합으로 입력하세요<br/>
										※ adm, admin, manager, master, system, root, administrator 사용 불가</span>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</fieldset>
					<c:choose>
						<c:when test="${not empty rtnInfo}">
							<fieldset>
								<div class="form-group text-sm">
									<label class="col-sm-2 control-label">비밀번호<span class="star text-danger"> *</span></label>
									<div class="col-sm-10">
										<button type="button" class="btn btn-inverse btn-sm" id="btnPwdInit" data-id="${rtnDto.id}" >비밀번호 초기화</button>
									</div>
								</div>
							</fieldset>
						</c:when>
						<c:otherwise>
							<fieldset>
								<div class="form-group text-sm">
									<label class="col-sm-2 control-label">비밀번호<span class="star text-danger"> *</span></label>
									<div class="col-sm-10">
										<input type="password" class="form-control input-sm passChk" id="pwd" name="pwd" title="비밀번호" maxlength="15" autocomplete="off" placeholder="비밀번호를 입력하세요." oninput="this.value=this.value.replace(/[\sㄱ-ㅎ|ㅏ-ㅣ|가-힣<c:out value="&<>:;?\'\""/>]/g,'');"/>
										<span class="help-block mt-sm mb0">※ 특수문자,숫자,영문 3가지 이상 조합하여 8자리 이상 ~ 15자리 이하로 입력이 가능합니다 <br/>
										※ 특수 문자 중 다음 문자는 사용하실 수 없습니다.(& < > : ; ? ' " 공백)</span>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<div class="form-group text-sm">
									<label class="col-sm-2 control-label">비밀번호 확인<span class="star text-danger"> *</span></label>
									<div class="col-sm-10">
										<input type="password" class="form-control input-sm passEqual" id="pwdCnfrm" name="pwdCnfrm" title="비밀번호 확인" maxlength="15" autocomplete="off" placeholder="비밀번호를 다시 한 번 입력하세요." oninput="this.value=this.value.replace(/[\sㄱ-ㅎ|ㅏ-ㅣ|가-힣<c:out value="&<>:;?\'\""/>]/g,'');"/>
									</div>
								</div>
							</fieldset>
						</c:otherwise>
					</c:choose>
					<fieldset>
						<div class="form-group text-sm">
							<label class="col-sm-2 control-label">이메일<span class="star text-danger"> *</span></label>
							<div class="col-sm-10">
								<div class="input-group">
									<input type="text" class="form-control input-sm emailChk" id="email" name="email" value="${rtnDto.email}" title="이메일주소" maxlength="50" placeholder="이메일주소를 입력하세요." oninput="this.value=this.value.replace(/[^a-zA-Z0-9~!@#$%^&*()_+|<>?:{}.]/gi,'');"/>
									<span class="input-group-btn"><button type="button" class="btn btn-default btn-sm" id="btnEmailOvrlChk">중복확인</button></span>
								</div>
							</div>
						</div>
					</fieldset>
					<fieldset>
						<div class="form-group text-sm">
							<label class="col-sm-2 control-label">허용 IP</label>
							<div class="col-sm-10">
								<input type="text" class="form-control input-sm notRequired ipChk" id="allwIp" name="allwIp" value="${rtnDto.allwIp}" title="허용 IP" maxlength="15" placeholder="미입력 시 모든 IP로 접근이 가능합니다." oninput="this.value=this.value.replace(/[^.0-9]/g,'');"/>
							</div>
						</div>
					</fieldset>
					<fieldset class="last-child">
						<div class="form-group text-sm">
							<label class="col-sm-2 control-label">로그인 2차 인증 여부<span class="star text-danger"> *</span></label>
							<div class="col-sm-10">
								<c:set var="lgnCrtfnYn" value="${kl:nvl(rtnDto.lgnCrtfnYn, 'Y')}" />
								<label class="radio-inline c-radio">
									<input type="radio" name="lgnCrtfnYn" value="Y" title="로그인2차인증여부" <c:if test="${lgnCrtfnYn eq 'Y'}">checked</c:if> />
									<span class="ion-record"></span> 인증
								</label>
								<label class="radio-inline c-radio">
									<input type="radio" name="lgnCrtfnYn" value="N" title="로그인2차인증여부" <c:if test="${lgnCrtfnYn eq 'N'}">checked</c:if> />
									<span class="ion-record"></span> 미인증
								</label>
							</div>
						</div>
					</fieldset>
					<fieldset class="last-child">
						<div class="form-group text-sm">
							<label class="col-sm-2 control-label">계정상태<span class="star text-danger"> *</span></label>
							<div class="col-sm-10">
								<c:set var="useYn" value="${kl:nvl(rtnDto.useYn, 'Y')}" />
								<label class="radio-inline c-radio">
									<input type="radio" name="useYn" value="Y" title="활성여부" <c:if test="${useYn eq 'Y'}">checked</c:if> />
									<span class="ion-record"></span> 활성
								</label>
								<label class="radio-inline c-radio">
									<input type="radio" name="useYn" value="N" title="활성여부" <c:if test="${useYn eq 'N'}">checked</c:if> />
									<span class="ion-record"></span> 비활성
								</label>
							</div>
						</div>
					</fieldset>

					<hr />

					<div class="clearfix">
						<div class="pull-left">
							<button type="button" class="btn btn-sm btn-default" id="btnList" data-param="${strPam}">목록</button>
						</div>
						<div class="pull-right">
							<c:choose>
								<c:when test="${empty rtnInfo}">
									<button type="submit" class="btn btn-sm btn-success" id="btnSubmit">등록</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-sm btn-danger" id="btnOneDelete">삭제</button>
									<button type="submit" class="btn btn-sm btn-success" id="btnSubmit">수정</button>
								</c:otherwise>

							</c:choose>

						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript" src="/common/js/lib/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="/common/js/lib/jquery/jquery.hotkeys.js"></script>


