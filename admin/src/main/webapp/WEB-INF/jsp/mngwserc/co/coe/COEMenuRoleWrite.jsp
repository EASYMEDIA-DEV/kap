<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/coe/COEMenuRoleLogCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnData.modSeq}" />
            <input type="hidden" class="notRequired" id="mChecked" name="mChecked" value="" />
            <input type="hidden" class="notRequired" id="mUndetermined" name="mUndetermined" value="" />
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <!-- 현재 페이징 번호 -->
            <input type="hidden" class="notRequired" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" class="notRequired" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
            <input type="hidden" class="notRequired" id="listRowSize" name="listRowSize" value="3" />

            <h6 class="mt-lg"> ◈ 메뉴권한 변경자 정보 </h6>

            <div class="table-responsive col-sm-12 p0 m0">
                <table class="table">
                    <colgroup>
                        <col style="width:20%;">
                        <col style="width:80%;">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">아이디</th>
                            <td>${rtnData.admId}</td>
                        </tr>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">성명</th>
                            <td>${rtnData.admNm}</td>
                        </tr>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">부서</th>
                            <td>${rtnData.admDeptNm}</td>
                        </tr>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">변경일시</th>
                            <td>${kl:convertDate(rtnData.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <h6 class="mt-xl "> ◈ 변경 대상자 정보 </h6>

            <div class="table-responsive col-sm-12 p0 m0">
                <table class="table">
                    <colgroup>
                        <col style="width:20%;">
                        <col style="width:80%;">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">아이디</th>
                            <td>${rtnData.trgtAdmId}</td>
                        </tr>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">성명</th>
                            <td>${rtnData.trgtAdmNm}</td>
                        </tr>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">부서</th>
                            <td>${rtnData.trgtAdmDeptNm}</td>
                        </tr>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">이전 권한</th>
                            <td>
                                <div <c:if test="${fn:length(rtnData.bfreMenuList) > 10}">style="height: 200px; overflow-y: auto"</c:if>>
                                    <c:forEach var="list" items="${rtnData.bfreMenuList}">
                                        ${list} <br/>
                                    </c:forEach>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row" class="bg-gray-lighter" >변경 권한</th>
                            <td>
                                <div <c:if test="${fn:length(rtnData.chngMenuList) > 10}">style="height: 200px; overflow-y: auto"</c:if>>
                                    <c:forEach var="list" items="${rtnData.chngMenuList}">
                                        ${list} <br/>
                                    </c:forEach>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <hr />

            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-param="${strPam}">목록</button>
                </div>
            </div>
        </form>
    </div>
</div>
