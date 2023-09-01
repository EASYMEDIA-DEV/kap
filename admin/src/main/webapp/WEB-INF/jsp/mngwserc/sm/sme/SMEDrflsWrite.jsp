<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/sm/sme/SMEDrflsWriteCtrl">
        <c:choose>
            <c:when test="${empty rtnData}">
                <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
            </c:when>
            <c:otherwise>
                <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세/수정</h6>
            </c:otherwise>
        </c:choose>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" action="${empty rtnData.drflsSeq ? 'insert' : 'update'}">
            <input type="hidden" class="notRequired" id="popSeq" name="dataPrcsSeq" value="${rtnData.drflsSeq}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnData.drflsSeq}" />
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <fieldset class="last-child mb0">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">상세내용<span class="star text-danger"> *</span></label>
                    <div class="col-sm-11">
                        <div class="dextEditor" id="cntn">${rtnData.cntn}</div>
                        <textarea name="cntn" title="상세내용" style="display:none;"></textarea>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div class="clearfix">
                <div class="pull-right">
                    <c:choose>
                        <c:when test="${empty rtnData.drflsSeq}">
                            <button type="submit" class="btn btn-sm btn-success">등록</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-sm btn-success">수정</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:if test="${not empty rtnData}">
                <h6 class="mt"><em class="ion-play mr-sm"></em>수정이력</h6>
                <div class="table-responsive ">
                    <table class="table text-sm">
                        <tbody>
                        <tr>
                            <th>최초 작성자</th>
                            <td>${rtnData.regName} (${rtnData.regId})</td>
                            <th>최초 작성일</th>
                            <td>${ kl:convertDate(rtnData.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                        </tr>
                        <tr>
                            <th>최종 수정자</th>
                            <td>
                                <c:choose>
                                    <c:when test="${rtnData.regDtm ne rtnData.modDtm}">
                                        ${rtnData.modName} (${rtnData.modId})
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <th>최종 수정일</th>
                            <td>
                                <c:choose>
                                    <c:when test="${rtnData.regDtm ne rtnData.modDtm}">
                                        ${kl:convertDate(rtnData.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
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
