<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" data-controller="controller/sm/SMDAPsnIfCtrl">
    <form class="form-horizontal" id="frmData" name="frmData" method="post" >
         <input type="hidden" class="notRequired" id="psnifSeq" name="psnifSeq" value="" />
    </form>
    <div class="cont-wrap">
        <div class="inner">
            <!-- @ 개인정보처리방침, 이용약관: policy-page 클래스 사용 -->
            <div class="sub-top-vis-area">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title">
                        <span class="for-move">개인정보 처리방침</span>
                    </p>
                </div>
            </div>
            <div class="policy-page">
                <div class="form-group align-right">
                    <div class="form-select w332">
                        <select id="psnIfSelectList" title="개인정보처리방침 이동">
                            <c:forEach var="list" items="${rtnData.list}" varStatus="status">
                                <option value="${list.psnifSeq}">${list.titl}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
                        <input type="hidden" id="prsnSeq1" value="${list.psnifSeq}" />
                    </c:forEach>
                </div>
                <div class="cntnArea"></div>
            </div>
        </div>
    </div>
</div>
