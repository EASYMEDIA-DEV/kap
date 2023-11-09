<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnQaInfo" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/im/ima/IMAQaWriteCtrl">
        <h5 class="mt0">${pageTitle} 상세/수정</h5>
        <hr/>

        <h6 class="mt-lg"><em class="ion-play mr-sm"></em>문의내용</h6>
        <form class="form-horizontal">
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">1차 문의유형</label>
                    <div class="col-sm-4">
                        <c:forEach var="cdList" items="${cdDtlList.INQUIRY_TYPE}" varStatus="status">
                            <c:if test="${cdList.cd eq rtnQaInfo.parntCtgryCd}">
                                <input type="text" class="form-control input-sm" value="${cdList.cdNm}" readonly />
                            </c:if>
                        </c:forEach>
                    </div>
                    <label class="col-sm-1 control-label">2차 문의유형</label>
                    <div class="col-sm-4">
                        <c:forEach var="cdList" items="${cdDtlList.INQUIRY_TYPE}" varStatus="status">
                            <c:if test="${cdList.cd eq rtnQaInfo.ctgryCd}">
                                <input type="text" class="form-control input-sm" value="${cdList.cdNm}" readonly />
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">작성자</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm" value="${rtnQaInfo.regName}" readonly />
                    </div>
                    <label class="col-sm-1 control-label">이메일</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm" value="${rtnQaInfo.email}" readonly />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연락처</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm" value="${rtnQaInfo.hpNo}" readonly />
                    </div>
                    <label class="col-sm-1 control-label">문의등록일</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control input-sm" value="${kl:convertDate(rtnQaInfo.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}" readonly />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">제목</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control input-sm" value="${rtnQaInfo.titl}" readonly />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">내용</label>
                    <div class="col-sm-11">
                        <textarea class="form-control notRequired ckeditorRequired" readonly>${rtnQaInfo.cntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">첨부파일</label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="fileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="5" data-title="QA 첨부파일">

                        </div>
                    </div>
                </div>
            </fieldset>
        </form>

        <h6 class="mt-lg"><em class="ion-play mr-sm"></em>문의답변</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" <c:if test="${rtnQaInfo.rsumeCd eq 'ACK'}">data-prcs-cd="20"</c:if>>
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnQaInfo.qaSeq}" />
            <input type="hidden" class="notRequired" id="regName" name="regName" value="${rtnQaInfo.regName}" />
            <input type="hidden" class="notRequired" id="titl" name="titl" value="${rtnQaInfo.titl}" />
            <input type="hidden" class="notRequired" id="cntn" name="cntn" value="${rtnQaInfo.cntn}" />
            <input type="hidden" class="notRequired" id="inqFir" name="inqFir" value="${rtnQaInfo.inqFir}" />
            <input type="hidden" class="notRequired" id="inqSec" name="inqSec" value="${rtnQaInfo.inqSec}" />
            <!-- 첨부파일 순번 -->
            <input type="hidden" class="notRequired" id="rplyFileSeq" name="rplyFileSeq" value="${rtnQaInfo.rplyFileSeq}" />
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">진행 상태<span class="star"> *</span></label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control input-sm" value="${kl:decode(rtnQaInfo.rsumeCd, 'ACK', '답변완료', '접수완료')}" title="진행상태" readonly />
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">답변내용<span class="star"> *</span></label>
                    <div class="col-sm-11">
                        <textarea class="form-control notRequired ckeditorRequired" id="rplyCntn" name="rplyCntn" title="답변내용" data-type="${pageGb}">${rtnQaInfo.rplyCntn}</textarea>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">첨부파일</label>
                    <div class="col-sm-10 col-md-11">
                        <spring:eval var="fileExtns" expression="@environment.getProperty('app.file.fileExtns')" />
                        <spring:eval var="atchUploadMaxSize" expression="@environment.getProperty('app.file.max-size')" />
                        <div class="dropzone attachFile notRequired" data-file-field-nm="rplyFileSeq" data-file-extn="${fileExtns}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="5" data-title="답변 첨부파일">
                            <div class="dz-default dz-message">
                                <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                            </div>
                        </div>
                        <p class="text-bold mt">
                            ※ ${fileExtns} 파일만 등록 가능합니다. (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하, 최대 5개 파일 등록 가능)
                        </p>
                    </div>
                </div>
            </fieldset>
            <hr />
            <div style="border:solid black 1px;">
                <b>답변 등록 시 회원에게 답변 알림 메일이 발송됩니다. 알림 발송 후에는 내용을 수정 할 수 없으므로 주의 부탁드립니다.</b>
            </div>
            <hr />
            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                </div>
                <c:if test="${rtnQaInfo.rsumeCd ne 'ACK'}">
                <div class="pull-right">
                    <button type="submit" class="btn btn-sm btn-success">저장</button>
                </div>
                </c:if>
            </div>
        </form>
        <c:if test="${ rtnQaInfo.rsumeCd eq 'ACK' }">
            <h6 class="mt"><em class="ion-play mr-sm"></em>수정이력</h6>
            <div class="table-responsive ">
                <table class="table text-sm">
                    <tbody>
                    <tr>
                        <th>최종 수정자</th>
                        <td>
                            ${rtnQaInfo.modName}
                        </td>
                        <th>최종 수정일시</th>
                        <td>
                            <%--${ kl:convertDate(rtnQaInfo.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }--%>
                            ${rtnQaInfo.modDtm}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>