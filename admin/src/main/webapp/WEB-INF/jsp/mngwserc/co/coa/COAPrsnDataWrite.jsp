<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

			<c:set var="info" value="${rtnData}" />
			
			<div class="container-fluid">
				<div class="card-body" data-controller="controller/co/coa/COAPrsnDataWriteCtrl">
					<h6 class="mt0"><em class="ion-play mr-sm"></em>관리자정보 수정</h6>
					<form class="form-horizontal" id="frmData" name="frmData" method="post" action="update" data-login-id="${ info.id }">
						<input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${info.admSeq}" />
						<fieldset>
							<div class="form-group text-sm">
				      			<label class="col-sm-1 control-label">이름</label>
				      			<div class="col-sm-11">
				      				<p class="form-control-static">${info.name}</p>
				      			</div>
				      		</div>
						</fieldset>
						<fieldset>
							<div class="form-group text-sm">
				      			<label class="col-sm-1 control-label">아이디</label>
				      			<div class="col-sm-11">
				      				<p class="form-control-static" id="id">${info.id}</p>
				      			</div>
				      		</div>
						</fieldset>
						<fieldset>
							<div class="form-group text-sm">
								<label class="col-sm-1 control-label">비밀번호</label>
								<div class="col-sm-11">
									<input type="password" class="form-control input-sm passChk notRequired" id="pwd" name="pwd" title="비밀번호" placeholder="비밀번호를 입력하세요." maxlength="15" autocomplete="off" oninput="this.value=this.value.replace(/[\sㄱ-ㅎ|ㅏ-ㅣ|가-힣<c:out value="&<>:;?\'\""/>]/g,'');"/>
									<span class="help-block mt-sm mb0">※ 특수문자,숫자,영문 3가지 이상 조합하여 8자리 이상 ~ 15자리 이하로 입력이 가능합니다.<br/>
									※ 특수 문자 중 다음 문자는 사용하실 수 없습니다.(& < > : ; ? ' " 공백)</span>
								</div>
							</div>
						</fieldset>
						<fieldset>
							<div class="form-group text-sm">
								<label class="col-sm-1 control-label">비밀번호 확인</label>
								<div class="col-sm-11">
									<input type="password" class="form-control input-sm passEqual notRequired" id="pwdCnfrm" name="pwdCnfrm" title="비밀번호 확인" placeholder="비밀번호를 다시 한 번 입력하세요." maxlength="15" autocomplete="off" oninput="this.value=this.value.replace(/[\sㄱ-ㅎ|ㅏ-ㅣ|가-힣<c:out value="&<>:;?\'\""/>]/g,'');"/>
								</div>
							</div>
						</fieldset>
						<fieldset>
							<div class="form-group text-sm">
								<label class="col-sm-1 control-label">부서명<span class="star text-danger"> *</span></label>
								<div class="col-sm-11">
									<input type="text" class="form-control input-sm " id="deptNm" name="deptNm" value="${info.deptNm}" title="부서명" placeholder="부서명을 입력하세요." maxlength="20" />
								</div>
							</div>
						</fieldset>
						<fieldset class="last-child">
	             			<div class="form-group text-sm">
	             				<label class="col-sm-1 control-label">이메일<span class="star text-danger"> *</span></label>
		               			<div class="col-sm-11">
		               				<div class="input-group">
										<input type="text" class="form-control input-sm emailChk" id="email" name="email" value="${info.email}" title="이메일 주소" maxlength="50" placeholder="이메일주소를 입력하세요."/>
										<span class="input-group-btn"><button type="button" class="btn btn-default btn-sm" id="btnEmailOvrlChk">중복확인</button></span>
									</div>
		               			</div>
	             			</div>
	             		</fieldset>
						<hr />
						<div class="clearfix">
							<div class="pull-left">

							</div>
							<div class="pull-right">
								<button type="submit" class="btn btn-sm btn-success">수정</button>
							</div>
						</div>
					</form>
				</div>
			</div>