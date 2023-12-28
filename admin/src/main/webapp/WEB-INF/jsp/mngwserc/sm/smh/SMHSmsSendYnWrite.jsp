<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/sm/smh/SMHSmsSendYnWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
        <form class="form-horizontal" id="frmSearch" name="frmSearch" method="post" action="" data-del-type="account">
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <!-- 상세로 이동시 시퀀스 -->
            <input type="hidden" id="cfgSeq" name="cfgSeq" value="${rtnDto.cfgSeq}" />
            <fieldset class="last-child mb0">
                <div class="col-sm-7 card bg-info">
                    <div class="col-sm-12 card-body text-center ">
                        <em class="ion-alert-circled mr-sm"></em>‘OFF’로 설정 시 SMS로 발송되는 모든 내용이 발송되지 않습니다.
                    </div>
                </div>
                <div class="col-sm-4 ml-xl mt-sm">
                    <c:set var="smsSendYn" value="${kl:nvl(rtnDto.smsSendYn, 'Y')}" />
                    <label class="radio-inline c-radio">
                        <input type="radio" name="smsSendYn" value="Y" <c:if test="${smsSendYn eq 'Y'}">checked</c:if> />
                        <span class="ion-record"></span> ON(발송)
                    </label>
                    <label class="radio-inline c-radio">
                        <input type="radio" name="smsSendYn" value="N" <c:if test="${smsSendYn eq 'N'}">checked</c:if> />
                        <span class="ion-record"></span> OFF(미발송)
                    </label>
                    <button type="submit" class="btn btn-sm btn-success ml-xl mt-sm" id="btnSmsYn">저장</button>
                </div>
            </fieldset>
            <div class="content-with-margin" style="margin: 260px;"></div>
            <h6 class="mt"><em class="ion-play mr-sm"></em>등록/수정이력</h6>
            <div class="table-responsive ">
                <table class="table text-sm">
                    <tbody>
                    <tr>
                        <th>최종 수정자</th>
                        <td>
                            <c:choose>
                                <c:when test="${ not empty rtnDto.modName }">
                                    ${ rtnDto.modName }(${ rtnDto.modId })
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <th>최종 수정일시</th>
                        <td>
                            <c:choose>
                                <c:when test="${ not empty rtnDto.modDtm }">
                                    ${ kl:convertDate(rtnDto.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </form>
    </div>
</div>