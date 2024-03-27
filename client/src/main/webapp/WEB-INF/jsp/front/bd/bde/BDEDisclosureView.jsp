<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" data-controller="controller/bd/bde/BDEDisclosureViewCtrl">
    <form class="form-horizontal" id="frmData" name="frmData" method="post" >
        <input type="hidden" class="notRequired" id="dsclsrSeq" name="dsclsrSeq" value="" />
    </form>
    <div class="cont-wrap">
        <div class="sub-top-vis-area">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">경영공시</span></p>
            </div>
        </div>

        <div class="divide-con-area">
            <!--LNB 시작-->
            <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
            <!--LNB 종료-->

            <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec scroll-motion">
                        <div class="for-motion">
                            <div class="sec-con-area">
                                <div class="form-group">
                                    <div class="form-select bdr-select">
                                        <select id="dsclsrSelectList" title="연도 선택">
                                            <c:forEach var="list" items="${rtnData.list}" varStatus="status">
                                                <option value="${list.dsclsrSeq}">${list.titl}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
                                        <input type="hidden" id="dsclsrSeq1" value="${list.dsclsrSeq}" />
                                    </c:forEach>
                                </div>
                                <div class="table-sec">
                                    <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                        <div class="cntnArea"></div>
                                    </div>
                                    <div class="fileArea">

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
