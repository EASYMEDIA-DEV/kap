<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<form class="form-horizontal" name="frmLctrSearch" method="post" action="" data-del-type="none">
    <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
    <!-- 페이징 버튼 사이즈 -->
    <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
    <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
    <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="hidden" id="episdSeq" name="episdSeq" value="" />

    <input type="hidden" id="lctrEpisdYear" name="episdYear" value="" />
    <input type="hidden" id="lctrEpisdOrd" name="episdOrd" value="" />
    <input type="hidden" id="edctnSeq" name="edctnSeq" value="${rtnData.edctnSeq}" />

    <!-- 온라인 교육 목차 팝업 -->
    <div class="layer-popup onlineEduContPopup"><!-- 팝업 디자인 확인을 위해 first-show 클래스 추가한 상태 -->
        <div class="for-center">
            <div class="pop-wrap">
                <div class="pop-con-area">
                    <div class="tit-area">
                        <p class="f-title1">온라인 교육 목차 안내</p>
                    </div>
                    <div class="con-area">
                        <div class="scroll-area">
                            <div class="p-cont-sec">
                                <div class="sec-con-area">
                                    <ul class="article-list-w index-list" id="listLctrContainer">
                                        <!--반복 시작-->

                                        <!--반복 종료-->
                                    </ul>
                                    <div class="btn-wrap align-center" style="display:none;">
                                        <a class="btn-solid small black-line lctrPageSet" href="javascript:"><span>더보기</span><span class="item-count">(1/50)</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="user-opt-area">
                        <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--<div class="dimd"></div>--%>

</form>





<%--</div>--%>




