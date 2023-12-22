<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="modal-dialog modal-lg modal-center" role="document" style="width:1500px;" data-controller="controller/ex/exg/EXGExamUserDtlCtrl">
    <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" >▣ 평가 상세
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </h5>
        </div>
        <form class="form-horizontal">
            <div class="modal-body">
                <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em>회원정보</h7>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">아이디</label>
                        <div class="col-sm-11">
                            ${ rtnData.id }
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <div class="form-group text-sm">
                        <label class="col-sm-1 control-label">이름</label>
                        <div class="col-sm-11">
                            ${ rtnData.name }
                        </div>
                    </div>
                </fieldset>
                <h7 class="mt0"><em class="ion-android-arrow-dropright mr-sm"></em>시험정보</h7>
                <input type="hidden" class="notRequired" name="ptcptSeq" value="${rtnData.ptcptSeq}" />
                <input type="hidden" class="notRequired" name="examSeq" value="${rtnData.examSeq}" />
                <input type="hidden" class="notRequired" name="memSeq" value="${rtnData.memSeq}" />
                <c:choose>
                    <c:when test="${ rtnExamData.examSeq != null }">
                        <c:forEach var="qstnList" items="${rtnExamData.exExamQstnDtlList}" varStatus="qstnStatus">
                            <fieldset class="survey-list" data-srv-type-cd="${qstnList.srvTypeCd}">
                                <input type="hidden" class="notRequired" name="qstnSeq" value="${qstnList.qstnSeq}" />
                                <input type="hidden" class="notRequired" name="scord" value="${qstnList.scord}" />
                                <div class="form-group text-sm">
                                    <label class="col-sm-1 control-label">
                                        <div class="form-group mb">질문${qstnList.qstnOrd}</div>
                                        <div class="form-group mb">
                                            <c:choose>
                                                <c:when test="${ qstnList.srvTypeCd eq 'EXG_C' or qstnList.srvTypeCd eq 'EXG_D'}">
                                                    <select name="qstnCanswYn" class="form-control input-sm notRequired">
                                                        <option value="X">선택</option>
                                                        <option value="Y" ${ kl:decode(qstnList.canswYn, 'Y', 'selected', '') }>정답(O)</option>
                                                        <option value="N" ${ kl:decode(qstnList.canswYn, 'N', 'selected', '') }>오답(X)</option>
                                                    </select>
                                                </c:when>
                                                <c:when test="${ qstnList.canswYn eq 'Y' }">(O)</c:when>
                                                <c:otherwise>(X)</c:otherwise>
                                            </c:choose></div>
                                    </label>
                                    <div class="col-sm-11 control-label">
                                        <div class="form-group mb">
                                            <div class="col-sm-2 text-left">ㆍ문항유형<span class="star"> *</span></div>
                                            <div class="col-sm-9 text-left">${qstnList.srvTypeCdNm}</div>
                                        </div>
                                        <div class="form-group mb">
                                            <div class="col-sm-2 text-left">ㆍ질문<span class="star"> *</span></div>
                                            <div class="col-sm-8 text-left">${ qstnList.qstnNm }</div>
                                        </div>
                                        <div class="form-group mb-sm">
                                            <div class="col-sm-2 text-left">ㆍ응답<span class="star"> *</span></div>
                                            <div class="col-sm-9 text-left">
                                                <c:choose>
                                                    <c:when test="${ qstnList.srvTypeCd eq 'EXG_B' or qstnList.srvTypeCd eq 'EXG_A'}">
                                                        <c:forEach var="exmplList" items="${qstnList.exExamExmplDtlList}" varStatus="exmplStatus">
                                                            <div class="form-group mb-sm">
                                                                <c:set var="isCheck" value="N" />
                                                                <c:forEach var="rspnList" items="${qstnList.exExamExmplRspnDtlList}" varStatus="rspnStatus">
                                                                    <c:if test="${ exmplList.exmplSeq eq rspnList.exmplSeq }">
                                                                        <c:set var="isCheck" value="Y" />
                                                                    </c:if>
                                                                </c:forEach>
                                                                - ${ exmplList.exmplNm }${kl:decode(isCheck, 'Y', '<em class="ml-sm star ion-android-done"></em>', '')}
                                                                <c:if test="${ exmplList.canswYn eq 'Y' }">
                                                                    <span class="k-font-weight-bold color-blue-a100">(정답)</span>
                                                                </c:if>
                                                            </div>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:forEach var="rspnList" items="${qstnList.exExamExmplRspnDtlList}" varStatus="rspnStatus">
                                                            <div class="form-group mb-sm">
                                                                - ${ rspnList.sbjctRply }
                                                            </div>
                                                        </c:forEach>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-2 text-left">ㆍ배점<span class="star"> *</span></div>
                                            <div class="col-sm-9 text-left">${ qstnList.scord }점</div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </div>
            <!--리스트 종료 -->
        </form>
        <div class="modal-footer row">
            <div class="text-center">
                <button type="submit" class="btn btn-success down mt btnEduPlaceLayerChoice">선택</button>
            </div>
        </div>
    </div>
</div>