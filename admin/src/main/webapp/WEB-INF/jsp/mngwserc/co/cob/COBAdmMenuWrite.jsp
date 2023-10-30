<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

			<div class="container-fluid">
				<div class="card-body" data-controller="controller/co/COMenuCtrl controller/co/cob/COBAdmMenuWriteCtrl">
					<h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
					<form class="form-horizontal" id="frmData" name="frmData" method="post" action="" data-alert-type="pass">
						<input type="hidden" class="notRequired" id="menuSeq" name="menuSeq" value="" />
						<input type="hidden" class="notRequired" id="admUrl" name="admUrl" value="" />
						<input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="col-sm-6 p0">
							<div class="mb">
								<button type="button" class="btn btn-sm btn-inverse" id="btnDrive">최상위 생성</button>
								<button type="button" class="btn btn-sm btn-success" id="btnChild">하위 생성</button>
								<button type="button" class="btn btn-sm btn-primary" id="btnRename">메뉴명 변경</button>
								<button type="button" class="btn btn-sm btn-danger"  id="btnRemove">삭제</button>
							</div>
							<div id="divCategoris" style="height:800px; overflow-y:auto;">
						
							</div>
						</div>
						<div class="col-sm-6 p0">
							<div class="p-lg mt-lg">
								<fieldset>
			             			<div class="form-group text-sm">
				               			<label class="col-sm-2 control-label">메뉴 순번</label>
				               			<div class="col-sm-10">
				               				<p class="form-control-static" id="pMenuSeq"></p>
				               			</div>
			             			</div>
				           		</fieldset>
				           		<fieldset>
									<div class="form-group text-sm">
										<label class="col-sm-2 control-label">메뉴명</label>
										<div class="col-sm-10">
											<p class="form-control-static" id="pMenuNm"></p>
										</div>
									</div>
								</fieldset>
				           		<fieldset class="hidden" id="menuTypeArea">
									<div class="form-group text-sm">
										<label class="col-sm-2 control-label">메뉴 구분<span class="star text-danger"> *</span></label>
										<div class="col-sm-10">
											<label class="radio-inline c-radio ">
												<input type="radio" class="rboxmenuType" name="menuType" value="drive" title="메뉴 구분" />
												<span class="ion-checkmark-round"></span> 최상위메뉴
											</label>
											<label class="radio-inline c-radio">
												<input type="radio" class="rboxmenuType" name="menuType" value="folder" title="메뉴 구분" />
												<span class="ion-record"></span> 개발
											</label>
				                      		<label class="radio-inline c-radio">
				                        		<input type="radio" class="rboxmenuType" name="menuType" value="menu" title="메뉴 구분" />
				                        		<span class="ion-record"></span> 사용자 메뉴 (드라이브당 한 개만 지정)
				                      		</label>
											<label class="radio-inline c-radio">
												<input type="radio" class="rboxmenuType" name="menuType" value="cms" title="메뉴 구분" />
												<span class="ion-record"></span> CMS
											</label>
										</div>
									</div>
								</fieldset>
								<fieldset>
			              			<div class="form-group text-sm">
			                			<label class="col-sm-2 control-label">사이트 URL<span class="star text-danger"> *</span></label>
			                			<div class="col-sm-10">
			                				<div class="input-group">
			                					<input type="text" class="form-control input-sm urlChk" id="userUrl" name="userUrl" title="사이트URL" maxlength="200" oninput="this.value = this.value.replace(/[ㄱ-ㅎㅏ-ㅣ가-힣]/,'');"/>
			                					<span class="input-group-addon" id="spanSuffix"></span>
			                				</div>
			                			</div>
			              			</div>
			            		</fieldset>
			            		<fieldset>
									<div class="form-group text-sm">
										<label class="col-sm-2 control-label">새 창 여부<span class="star text-danger"> *</span></label>
										<div class="col-sm-10">
											<label class="radio-inline c-radio">
				                        		<input type="radio" name="wnppYn" value="N" title="새 창 여부" />
				                        		<span class="ion-record"></span> 본창
				                      		</label>		
				                      		<label class="radio-inline c-radio">
				                        		<input type="radio" name="wnppYn" value="Y" title="새 창 여부" />
				                        		<span class="ion-record"></span> 새창
				                      		</label>
										</div>
									</div>
								</fieldset>
								<fieldset class="last-child">
			              			<div class="form-group text-sm">
			                			<label class="col-sm-2 control-label">노출 여부</label>
			                			<div class="col-sm-10">
			                				<label class="radio-inline c-radio">
												<input type="radio" name="useYn" value="Y" title="노출 여부" />
												<span class="ion-record"></span> 노출
											</label>
											<label class="radio-inline c-radio">
												<input type="radio" name="useYn" value="N" title="노출 여부" />
												<span class="ion-record"></span> 미노출
											</label>
				                      	</div>
			              			</div>
			            		</fieldset>
			            		<hr />
								<div class="clearfix">
									<div class="pull-right">
										<button type="submit" class="btn btn-sm btn-success">적용</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
			<script type="text/javascript" src="/common/js/lib/jstree/jquery.jstree.js"></script>
			<script type="text/javascript" src="/common/js/lib/jquery/jquery.hotkeys.js"></script>