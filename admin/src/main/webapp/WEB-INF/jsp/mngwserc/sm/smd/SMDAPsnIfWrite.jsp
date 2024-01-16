<%--
  Created by IntelliJ IDEA.
  User: EM_NB126
  Date: 2022-02-24
  Time: 오후 1:47
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/sm/smd/SMDAPsnIfWriteCtrl">
        <c:choose>
            <c:when test="${not empty rtnInfo}">
                <h6 class="mt0"><em class="ion-play mr-sm"></em>개인정보처리방침 상세/수정</h6>
            </c:when>
            <c:when test="${empty rtnInfo}">
                <h6 class="mt0"><em class="ion-play mr-sm"></em>개인정보처리방침 등록</h6>
            </c:when>
        </c:choose>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnInfo.psnifSeq}" />

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" title="제목" maxlength="200" placeholder="제목을 입력해주세요" />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control notRequired ckeditorRequired" id="cntn" name="cntn" title="내용" data-type="${pageGb}">${rtnDto.cntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출 여부<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="Y" title="노출 여부" <c:if test="${rtnDto.expsYn eq 'Y' or rtnDto.expsYn eq null}">checked</c:if>/>
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="N" title="노출 여부" <c:if test="${rtnDto.expsYn eq 'N'}">checked</c:if>/>
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                    <c:if test="${ not empty rtnInfo }">
                        <button type="button" class="btn btn-sm btn-danger" id="btn_delete">삭제</button>
                    </c:if>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${ not empty rtnInfo}">
                            <button type="submit" class="btn btn-sm btn-success" >저장</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:if test="${ not empty rtnDto }">
                <h6 class="mt"><em class="ion-play mr-sm"></em>등록/수정이력</h6>
                <div class="table-responsive ">
                    <table class="table text-sm">
                        <tbody>
                        <tr>
                            <th>최초 등록자</th>
                            <td>${ rtnDto.regName }(${ rtnDto.regId })</td>
                            <th>최초 등록일시</th>
                            <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                        </tr>
                        <tr>
                            <th>최종 수정자</th>
                            <td>${ empty rtnDto.modName ? '-' : rtnDto.modName += '(' += rtnDto.modId += ')' }</td>
                            <th>최종 수정일시</th>
                            <td>${ kl:decode(rtnDto.modDtm, "", "-", kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')) }</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </form>
    </div>
</div>