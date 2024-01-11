<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy.MM.dd" /></c:set>
<div class="container-fluid">
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
            <h6 class="mt0"><em class="ion-play mr-sm"></em>교육사업 <span style="float:right;"><small class="text-muted" >더보기</small></span></h6>
            <div class="card-deck">
                <div class="card" style="height:300px;">
                    <div class="card-body">
                        <p class="card-text"><small class="text-muted" style="float:right;">기준일 : ${sysDate}</small></p>
                        <p class="card-text">&nbsp;</p>
                        <p class="card-text"><strong>접수중</strong><span style="float:right;"><strong>5</strong>건</span></p>
                        <p class="card-text"><strong>접수대기</strong><span style="float:right;"><strong>5</strong>건</span></p>
                        <p class="card-text"><strong>교육중</strong><span style="float:right;"><strong>5</strong>건</span></p>
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
                        <p class="card-text"><strong>신청</strong><span style="float:right;"><strong>5</strong>건</span></p>
                        <p class="card-text"><strong>지도중</strong><span style="float:right;"><strong>5</strong>건</span></p>
                        <h6 class="card-title"><strong>경영컨설팅</strong></h6>
                        <p class="card-text"><strong>신청</strong><span style="float:right;"><strong>5</strong>건</span></p>
                        <p class="card-text"><strong>지도중</strong><span style="float:right;"><strong>5</strong>건</span></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="card" style="border:none;">
        <div class="card-body" style="float:left;width:33%;min-width: 20rem;" >
            <h6 class="mt0"><em class="ion-play mr-sm"></em>공지사항 <span style="float:right;"><small class="text-muted">더보기</small></span></h6>
            <div class="card-deck">
                <div class="card" style="height:335px;">
                    <div class="card-body">
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">공지사항 제목이 표시됩니다..<span style="float:right;">2023.01.01</span></li>
                        <li class="list-group-item">공지사항 제목이 표시됩니다..<span style="float:right;">2023.01.01</span></li>
                        <li class="list-group-item">공지사항 제목이 표시됩니다..<span style="float:right;">2023.01.01</span></li>
                        <li class="list-group-item">공지사항 제목이 표시됩니다..<span style="float:right;">2023.01.01</span></li>
                        <li class="list-group-item">공지사항 제목이 표시됩니다..<span style="float:right;">2023.01.01</span></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="card-body" style="float: left;width:33%;min-width: 20rem;">
            <h6 class="mt0"><em class="ion-play mr-sm"></em>1:1문의 <span style="float:right;"><small class="text-muted">더보기</small></span></h6>
            <div class="card-deck">
                <div class="card" style="height:335px;">
                    <div class="card-body">
                        <p class="card-text"><small class="text-muted" style="float:right;">기준일 : ${sysDate}</small></p>
                        <p class="card-text">&nbsp;</p>
                        <p class="card-text"><strong>접수대기</strong><span style="float:right;"><strong>5</strong>건</span></p>
                        <p class="card-text"><strong>접수완료</strong><span style="float:right;"><strong>5</strong>건</span></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-body" style="float: left;width:33%;min-width: 20rem;">
            <h6 class="mt0"><em class="ion-play mr-sm"></em>일반회원 <span style="float:right;"><small class="text-muted">더보기</small></span></h6>
            <div class="card-deck">
                <div class="card" >
                    <div class="card-body">
                        <p class="card-text"><small class="text-muted" style="float:right;">기준일 : ${sysDate}</small></p>
                        <p class="card-text">&nbsp;</p>
                        <p class="card-text"><strong>신규가입</strong><span style="float:right;"><strong>15</strong></span></p>
                        <p class="card-text"><strong>탈퇴</strong><span style="float:right;"><strong>5</strong></span></p>
                    </div>
                </div>
            </div>
            <h6 class="mt0"><em class="ion-play mr-sm"></em>부품사회원 <span style="float:right;"><small class="text-muted">더보기</small></span></h6>
            <div class="card-deck">
                <div class="card">
                    <div class="card-body">
                        <p class="card-text"><small class="text-muted" style="float:right;">기준일 : ${sysDate}</small></p>
                        <p class="card-text">&nbsp;</p>
                        <p class="card-text"><strong>신규가입</strong><span style="float:right;"><strong>15</strong></span></p>
                        <p class="card-text"><strong>탈퇴</strong><span style="float:right;"><strong>5</strong></span></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
