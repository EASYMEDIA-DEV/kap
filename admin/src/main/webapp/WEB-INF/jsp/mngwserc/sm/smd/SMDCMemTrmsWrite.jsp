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
    <div class="card-body" data-controller="controller/sm/smd/SMDCMemTrmsWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnInfo.memTrmsSeq}" />

            <fieldset>
                <h6 style="margin-left: 88px; font-size: 13px;"><em class="ion-play mr-sm"></em> 이용약관</h6>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control notRequired ckeditorRequired" id="tmncsCntn" name="tmncsCntn" title="내용" data-type="${pageGb}">${rtnDto.tmncsCntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <h6 style="margin-left: 88px; font-size: 13px;"><em class="ion-play mr-sm"></em> 개인정보 수집 및 이용동의</h6>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control notRequired ckeditorRequired" id="psnifCntn" name="psnifCntn" title="내용" data-type="${pageGb}">${rtnDto.psnifCntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <h6 style="margin-left: 88px; font-size: 13px;"><em class="ion-play mr-sm"></em> 제3자 정보 제공 동의</h6>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control notRequired ckeditorRequired" id="offerCntn" name="offerCntn" title="내용" data-type="${pageGb}">${rtnDto.offerCntn}</textarea>
                    </div>
                </div>
            </fieldset>
            CFG_SEQ
            SMS_SEND_YN
            REG_ID
            REG_IP
            REG_DTM
            MOD_ID
            MOD_IP
            MOD_DTM
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" onclick="location.href='./list?${strPam}'">목록</button>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">저장</button>
                </div>
            </div>
            <c:if test="${ not empty rtnInfo }">
                <h6 class="mt"><em class="ion-play mr-sm"></em>등록/수정이력</h6>
                <div class="table-responsive ">
                    <table class="table text-sm">
                        <tbody>
                        <tr>
                            <th>최초 등록자</th>
                            <td>${ rtnDto.regName }</td>
                            <th>최초 등록일시</th>
                            <td>${ kl:convertDate(rtnDto.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                        </tr>
                        <tr>
                            <th>최종 수정자</th>
                            <td>
                                <c:choose>
                                    <c:when test="${ not empty rtnDto.modName }">
                                        ${ rtnDto.modName }
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
            </c:if>
        </form>
    </div>
</div>