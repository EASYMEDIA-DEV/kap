<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy.MM.dd" /></c:set>
<div class="container-fluid" data-controller="controller/co/CODashBoardCtrl">

    <form id="frmData" name="frmData" method="post">
        <input type="hidden" name="link">
        <input type="hidden" name="driveMenuSeq">
    </form>
    <div class="card-body ">
        <div class="card-deck">
            <div class="card" style="background-color:#e9e9e9;">
                <div class="card-body">
                    <h5 class="card-title"><strong>${sessionScope.loginMap.name}</strong> 님[${sessionScope.loginMap.authNm}] 환영합니다. </h5>
                    <p class="card-text">최근 로그인 일시 : ${kl:convertDate(sessionScope.loginMap.lastLgnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '')} </p>
                    <p class="card-text">&nbsp;</p>
                    <p class="card-text"><button type="button" class="btn btn-sm btn-inverse" onclick="location.href='/mngwserc/co/coz/profile'">정보수정</button></p>
                </div>
            </div>
        </div>
    </div>
    <div class="card" style="border:none;">
        <div class="card-body" style="float:left;width:50%;min-width: 20rem;" >
            <h6 class="mt0"><em class="ion-play mr-sm"></em>교육사업 <span style="float:right;"><small class="text-muted"><a href="javascript:;" data-link="/eb/ebb/list" class="moreLink" >더보기</a></small></span></h6>
            <div class="card-deck">
                <div class="card" style="height:300px;">
                    <div class="card-body">
                        <p class="card-text"><small class="text-muted" style="float:right;">기준일 : ${sysDate}</small></p>
                        <p class="card-text">&nbsp;</p>
                        <p class="card-text"><strong>접수중</strong><span style="float:right;"><a href="javascript:;" data-link="/eb/ebb/list?dashBoardType=A" class="moreLink"><strong>${rtnData.eduAccepting}</strong></a>건</span></p>
                        <p class="card-text"><strong>교육대기</strong><span style="float:right;"><a href="javascript:;" data-link="/eb/ebb/list?dashBoardType=B" class="moreLink"><strong>${rtnData.eduAcceptWaiting}</strong></a>건</span></p>
                        <p class="card-text"><strong>교육중</strong><span style="float:right;"><a href="javascript:;" data-link="/eb/ebb/list?dashBoardType=C" class="moreLink"><strong>${rtnData.eduAcceptTraining}</strong></a>건</span></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-body" style="float: left;width:50%;min-width: 20rem;">
            <h6 class="mt0"><em class="ion-play mr-sm"></em>컨설팅사업 </h6>
            <div class="card-deck">
                <div class="card" style="height:300px;">
                    <div class="card-body">
                        <p class="card-text"><small class="text-muted" style="float:right;">기준일 : ${sysDate}</small></p>
                        <h6 class="card-title"><strong>기술지도</strong></h6>
                        <p class="card-text"><strong>신청</strong><span style="float:right;"><a href="javascript:;" data-link="/cb/cba/list?dashBoardType=A" class="moreLink"><strong>${rtnData.conTechApplication}</strong></a>건</span></p>
                        <p class="card-text"><strong>지도중</strong><span style="float:right;"><a href="javascript:;" data-link="/cb/cba/list?dashBoardType=B" class="moreLink"><strong>${rtnData.conTechTraining}</strong></a>건</span></p>
                        <h6 class="card-title"><strong>경영컨설팅</strong></h6>
                        <p class="card-text"><strong>신청</strong><span style="float:right;"><a href="javascript:;" data-link="/cb/cbb/list?dashBoardType=A" class="moreLink"><strong>${rtnData.conMngApplication}</strong></a>건</span></p>
                        <p class="card-text"><strong>지도중</strong><span style="float:right;"><a href="javascript:;" data-link="/cb/cbb/list?dashBoardType=B" class="moreLink"><strong>${rtnData.conMngTraining}</strong></a>건</span></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="card" style="border:none;">
        <div class="card-body" style="float:left;width:33%;min-width: 20rem;" >
            <h6 class="mt0"><em class="ion-play mr-sm"></em>공지사항 <span style="float:right;"><small class="text-muted"><a href="javascript:;" data-link="/bd/bda/list" class="moreLink" >더보기</a></small></span></h6>
            <div class="card-deck">
                <div class="card" style="height:335px;">
                    <div class="card-body">
                    </div>
                    <ul class="list-group list-group-flush" >
                    <c:choose>
                        <c:when test="${ not empty rtnData.noticeList}">
                            <c:forEach var="list" items="${rtnData.noticeList}" varStatus="status">
                                <li class="list-group-item" style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
                                    <a href="javascript:;" data-link="/bd/bda/write?detailsKey=${list.ntfySeq}" class="moreLink" ><c:out value="${list.titl}"  /></a>
                                    <span style="float:right;">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</span>
                                </li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <li class="list-group-item">등록된 게시물이 없습니다.</li>
                        </c:otherwise>
                    </c:choose>
                    </ul>
                </div>
            </div>
        </div>
        <div class="card-body" style="float: left;width:33%;min-width: 20rem;">
            <h6 class="mt0"><em class="ion-play mr-sm"></em>1:1문의 <span style="float:right;"><small class="text-muted"><a href="javascript:;" data-link="/im/ima/list" class="moreLink" >더보기</a></small></span></h6>
            <div class="card-deck">
                <div class="card" style="height:335px;">
                    <div class="card-body">
                        <p class="card-text"><small class="text-muted" style="float:right;">기준일 : ${sysDate}</small></p>
                        <p class="card-text">&nbsp;</p>
                        <p class="card-text"><strong>접수대기</strong><span style="float:right;"><a href="javascript:;" data-link="/im/ima/list?dashBoardType=A" class="moreLink"><strong>${rtnData.inquiryApplicationWaiting}</strong></a>건</span></p>
                        <p class="card-text"><strong>접수완료</strong><span style="float:right;"><a href="javascript:;" data-link="/im/ima/list?dashBoardType=B" class="moreLink"><strong>${rtnData.inquiryApplicationCompleted}</strong></a>건</span></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-body" style="float: left;width:33%;min-width: 20rem;">
            <h6 class="mt0"><em class="ion-play mr-sm"></em>일반회원 <span style="float:right;"><small class="text-muted"><a href="javascript:;" data-link="/mp/mpa/list" class="moreLink" >더보기</a></small></span></h6>
            <div class="card-deck">
                <div class="card" >
                    <div class="card-body">
                        <p class="card-text"><small class="text-muted" style="float:right;">기준일 : ${sysDate}</small></p>
                        <p class="card-text">&nbsp;</p>
                        <p class="card-text"><strong>신규가입</strong><span style="float:right;"><strong>${rtnData.generalMemJoin}</strong></span></p>
                        <p class="card-text"><strong>탈퇴</strong><span style="float:right;"><strong>${rtnData.generalMemSecession}</strong></span></p>
                    </div>
                </div>
            </div>
            <h6 class="mt0"><em class="ion-play mr-sm"></em>부품사회원 <span style="float:right;"><small class="text-muted"><a href="javascript:;" data-link="/mp/mpb/list" class="moreLink" >더보기</a></small></span></h6>
            <div class="card-deck">
                <div class="card">
                    <div class="card-body">
                        <p class="card-text"><small class="text-muted" style="float:right;">기준일 : ${sysDate}</small></p>
                        <p class="card-text">&nbsp;</p>
                        <p class="card-text"><strong>신규가입</strong><span style="float:right;"><strong>${rtnData.companyMemJoin}</strong></span></p>
                        <p class="card-text"><strong>탈퇴</strong><span style="float:right;"><strong>${rtnData.companyMemSecession}</strong></span></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
