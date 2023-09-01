<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${not empty rtnInfo ? rtnInfo : rtnData}"/>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/sm/smd/SMDPsnIfWriteCtrl">
        <c:choose>
            <c:when test="${empty rtnInfo}">
                <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
            </c:when>
            <c:otherwise>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세/수정</h6>
            </c:otherwise>
        </c:choose>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" action="${empty rtnData.detailsKey ? './insert' : './update'}">
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnData.detailsKey}" />

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <input type="text" class="form-control input-sm" id="titl" name="titl" value="${rtnDto.titl}" maxlength="30" title="제목" placeholder="제목을 입력하세요." />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">상세내용<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <div class="dextEditor" id="cntn">${rtnDto.cntn}</div>
                        <textarea name="cntn" title="상세내용" style="display:none;"></textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child mb0">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <c:set var="expsYn" value="${kl:nvl(rtnDto.expsYn, 'Y')}" />
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="Y" <c:if test="${expsYn eq 'Y'}">checked</c:if> />
                            <span class="ion-record"></span> 노출
                        </label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="expsYn" value="N" <c:if test="${expsYn eq 'N'}">checked</c:if> />
                            <span class="ion-record"></span> 미노출
                        </label>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <input type="hidden" class="notRequired" value="${strPam}" name="strPam"/>
                    <button type="button" class="btn btn-sm btn-default" id="btnList">목록</button>
                </div>
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${not empty rtnInfo}">
                            <button type="button" class="btn btn-sm btn-danger" id="btnOneDelete">삭제</button>
                            <button type="submit" class="btn btn-sm btn-success">수정</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">등록</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:if test="${not empty rtnInfo}">
                <h6 class="mt"><em class="ion-play mr-sm"></em>수정이력</h6>
                <div class="table-responsive ">
                    <table class="table text-sm">
                        <tbody>
                        <tr>
                            <th>최초 작성자</th>
                            <td>${rtnDto.regName} (${rtnDto.regId})</td>
                            <th>최초 작성일</th>
                            <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                        </tr>
                        <tr>
                            <th>최종 수정자</th>
                            <td>
                                <c:choose>
                                    <c:when test="${rtnDto.regDtm ne rtnDto.modDtm}">
                                        ${rtnDto.modName} (${rtnDto.modId})
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <th class="text-sm">최종 수정일</th>
                            <td>
                                <c:choose>
                                    <c:when test="${rtnDto.regDtm ne rtnDto.modDtm}">
                                        ${kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </form>
    </div>
</div>
