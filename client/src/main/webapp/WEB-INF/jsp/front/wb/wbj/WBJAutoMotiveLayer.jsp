<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

    <!-- 부품사 검색 팝업 -->
    <div class="layer-popup partCompanySrchPop"><!-- 팝업 디자인 확인을 위해 first-show 클래스 추가한 상태 -->
        <div class="for-center">
            <div class="pop-wrap">
                <div class="pop-con-area">
                    <div class="tit-area">
                        <p class="f-title1">부품사 검색</p>
                    </div>
                    <div class="con-area">
                        <div class="scroll-area">
                            <div class="p-cont-sec">
                                <div class="form-group">
                                    <div class="form-select">
                                        <select id="" name="f" title="">
                                            <option value="" selected="">전체</option>
                                            <option value="1">부품사명</option>
                                            <option value="2">사업자등록번호</option>
                                        </select>
                                    </div>
                                    <div class="form-input srch-input w-longer">
                                        <input type="text" id="q" name="q" placeholder="검색어를 입력해 주세요.">
                                        <div class="input-btn-wrap">
                                            <button class="delete-btn" title="지우기" type="button"></button>
                                            <span class="srch-btn" id="btnSearch" title="검색"></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="p-cont-sec">
                                <div class="sec-con-area">
                                    <ul class="article-list-w index-list divide">
                                        <c:forEach var="item" items="${rtnData.list}" varStatus="status">
                                            <li class="list-item">
                                                <input type="hidden" class="notRequired ctgryNm" value="${item.ctgryNm}"/>
                                                <div class="txt-area">
                                                    <div class="form-checkbox no-txt num">
                                                        <input type="checkbox" id="Chk${item.bsnmNo}" name="chk" value="${item.bsnmNo}">
                                                        <label for="Chk${item.bsnmNo}"></label>
                                                    </div>
                                                    <p class="num f-sub-head">${status.count}</p>
                                                    <div class="training">
                                                        <div class="dl">
                                                            <div class="dt f-body2 w-longest">부품사명</div>
                                                            <div class="dd f-body2 srchListView">${item.cmpnNm}</div>
                                                        </div>
                                                        <div class="dl">
                                                            <div class="dt f-body2 w-longest">사업자등록번호</div>
                                                            <div class="dd f-body2">${item.bsnmNo}</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <div class="btn-wrap add-load align-center">
                                        <a class="btn-solid small black-line addMore" href="javascript:"><span>더보기</span><span class="item-count">(10/${rtnData.totalCount})</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="bot-fix-btn-area">
                        <div class="btn-wrap align-right">
                            <div class="btn-set">
                                <a class="btn-solid small black-bg" href="javascript:" id="selectChoose"><span>선택</span></a>
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

    <div class="dimd"></div>

    <%--</div>--%>



