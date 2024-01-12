<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap">
    <!-- 지급정보관리 팝업 -->
    <div class="layer-popup paymentInfoManagPopup"><!-- 팝업 디자인 확인을 위해 first-show 클래스 추가한 상태 -->
        <div class="for-center">
            <div class="pop-wrap">
                <div class="pop-con-area">
                    <div class="tit-area">
                        <p class="f-title1">지급정보관리</p>
                    </div>
                    <div class="tab-btn-area">
                        <div class="txt-tab-swiper func-tab">
                            <div class="swiper-container">
                                <div class="swiper-wrapper">
                                    <a class="swiper-slide txt-tab-btn active" href="javascript:">
                                        <p class="txt"><span class="menu-name">선급금</span></p>
                                    </a>
                                    <a class="swiper-slide txt-tab-btn" href="javascript:">
                                        <p class="txt"><span class="menu-name">지원금</span></p>
                                    </a>
                                    <a class="swiper-slide txt-tab-btn" href="javascript:">
                                        <p class="txt"><span class="menu-name">기술임치</span></p>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="con-area">
                        <div class="scroll-area">
                            <div class="tab-con-w">
                                <div class="tab-con-box">
                                    <!-- 선급금 탭 -->
                                    <div class="tab-con">
                                        <div class="tab-con-area">
                                            <div class="p-cont-sec">
                                                <div class="sec-tit-area">
                                                    <p class="f-head">지급정보를 입력해주세요</p>
                                                    <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                                </div>
                                                <div class="sec-con-area">
                                                    <div class="data-enter-form">
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">접수일<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input calendar">
                                                                                <input type="text" placeholder="날짜 선택">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                                <p class="title f-body1">
                                                                    <span>정부지원금<i class="circle-num"><img src="/common/images/icon-circle-num-1.svg" alt="1"></i></span>
                                                                    <span class="essential-mark color-sky">*</span>
                                                                </p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="정부지원금 입력" value="15,000,000">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                                <p class="title f-body1">
                                                                    <span>대기업출연금<i class="circle-num"><img src="/common/images/icon-circle-num-2.svg" alt="2"></i></span>
                                                                    <span class="essential-mark color-sky">*</span>
                                                                </p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="대기업출연금 입력" value="35,000,000">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                                <p class="title f-body1">
                                                                    <span>총금액<i class="circle-num"><img src="/common/images/icon-circle-num-1.svg" alt="1"></i>+<i class="circle-num"><img src="/common/images/icon-circle-num-2.svg" alt="2"></i></span>
                                                                    <span class="essential-mark color-sky">*</span>
                                                                </p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="총금액 입력" value="50,000,000" readonly>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">은행명<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="은행 입력" value="신한은행">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">계좌번호<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="계좌번호 입력" value="1001235678910">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">예금주<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="예금주 입력" value="에이테크">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="p-cont-sec">
                                                <div class="sec-tit-area">
                                                    <p class="f-head">파일을 첨부해주세요</p>
                                                </div>
                                                <div class="sec-con-area">
                                                    <div class="data-enter-form">
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">지원금신청서<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                                <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                <div class="file-list">
                                                                                    <p class="file-name"><span class="name">지원금신청서(에이테크)</span><span class="unit">pdf</span></p>
                                                                                    <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                                                </div>
                                                                            </div>
                                                                            <div class="file-btn-area">
                                                                                <input type="file" id="searchFile">
                                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                                            </div>
                                                                            <!-- 2023-12-20 추가 -->
                                                                            <div class="file-prev-area">
                                                                                <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a><!-- 204-01-03 속성 변경 -->
                                                                            </div>
                                                                            <!-- // 2023-12-20 추가 -->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">계좌이체약정서<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                                <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                <div class="file-list">
                                                                                    <p class="file-name"><span class="name">계좌이체약정서(에이테크)</span><span class="unit">pdf</span></p>
                                                                                    <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                                                </div>
                                                                            </div>
                                                                            <div class="file-btn-area">
                                                                                <input type="file" id="searchFile">
                                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                                            </div>
                                                                            <!-- 2023-12-20 추가 -->
                                                                            <div class="file-prev-area">
                                                                                <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a><!-- 204-01-03 속성 변경 -->
                                                                            </div>
                                                                            <!-- // 2023-12-20 추가 -->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">통장사본<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                            </div>
                                                                            <div class="file-btn-area">
                                                                                <input type="file" id="searchFile">
                                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                                            </div>
                                                                            <!-- 2023-12-20 추가 -->
                                                                            <div class="file-prev-area">
                                                                                <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a><!-- 204-01-03 속성 변경 -->
                                                                            </div>
                                                                            <!-- // 2023-12-20 추가 -->
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
                                    <!-- 지원금 탭 -->
                                    <div class="tab-con">
                                        <div class="tab-con-area">
                                            <div class="p-cont-sec">
                                                <div class="sec-tit-area">
                                                    <p class="f-head">지급정보를 입력해주세요</p>
                                                    <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                                </div>
                                                <div class="sec-con-area">
                                                    <div class="data-enter-form">
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">접수일<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input calendar">
                                                                                <input type="text" placeholder="날짜 선택">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                                <p class="title f-body1">
                                                                    <span>정부지원금<i class="circle-num"><img src="/common/images/icon-circle-num-1.svg" alt="1"></i></span>
                                                                    <span class="essential-mark color-sky">*</span>
                                                                </p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="정부지원금 입력" value="15,000,000">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                                <p class="title f-body1">
                                                                    <span>대기업출연금<i class="circle-num"><img src="/common/images/icon-circle-num-2.svg" alt="2"></i></span>
                                                                    <span class="essential-mark color-sky">*</span>
                                                                </p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="대기업출연금 입력" value="35,000,000">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                                <p class="title f-body1">
                                                                    <span>총금액<i class="circle-num"><img src="/common/images/icon-circle-num-1.svg" alt="1"></i>+<i class="circle-num"><img src="/common/images/icon-circle-num-2.svg" alt="2"></i></span>
                                                                    <span class="essential-mark color-sky">*</span>
                                                                </p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="총금액 입력" value="50,000,000" readonly>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">은행명<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="은행 입력" value="신한은행">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">계좌번호<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="계좌번호 입력" value="1001235678910">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">예금주<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="예금주 입력" value="에이테크">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="p-cont-sec">
                                                <div class="sec-tit-area">
                                                    <p class="f-head">파일을 첨부해주세요</p>
                                                </div>
                                                <div class="sec-con-area">
                                                    <div class="data-enter-form">
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">지원금신청서<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                                <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                <div class="file-list">
                                                                                    <p class="file-name"><span class="name">지원금신청서(에이테크)</span><span class="unit">pdf</span></p>
                                                                                    <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                                                </div>
                                                                            </div>
                                                                            <div class="file-btn-area">
                                                                                <input type="file" id="searchFile">
                                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                                            </div>
                                                                            <!-- 2023-12-20 추가 -->
                                                                            <div class="file-prev-area">
                                                                                <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a><!-- 204-01-03 속성 변경 -->
                                                                            </div>
                                                                            <!-- // 2023-12-20 추가 -->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">계좌이체약정서<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                                <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                <div class="file-list">
                                                                                    <p class="file-name"><span class="name">계좌이체약정서(에이테크)</span><span class="unit">pdf</span></p>
                                                                                    <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                                                </div>
                                                                            </div>
                                                                            <div class="file-btn-area">
                                                                                <input type="file" id="searchFile">
                                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                                            </div>
                                                                            <!-- 2023-12-20 추가 -->
                                                                            <div class="file-prev-area">
                                                                                <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a><!-- 204-01-03 속성 변경 -->
                                                                            </div>
                                                                            <!-- // 2023-12-20 추가 -->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">통장사본<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                            </div>
                                                                            <div class="file-btn-area">
                                                                                <input type="file" id="searchFile">
                                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                                            </div>
                                                                            <!-- 2023-12-20 추가 -->
                                                                            <div class="file-prev-area">
                                                                                <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a><!-- 204-01-03 속성 변경 -->
                                                                            </div>
                                                                            <!-- // 2023-12-20 추가 -->
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
                                    <!-- 기술임치 탭 -->
                                    <div class="tab-con">
                                        <div class="tab-con-area">
                                            <div class="p-cont-sec">
                                                <div class="sec-tit-area">
                                                    <p class="f-head">지급정보를 입력해주세요</p>
                                                    <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                                </div>
                                                <div class="sec-con-area">
                                                    <div class="data-enter-form">
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">접수일<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input calendar">
                                                                                <input type="text" placeholder="날짜 선택">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">수수료<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="수수료 입력" value="15,000,000">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">은행명<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="은행 입력" value="신한은행">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">계좌번호<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="계좌번호 입력" value="1001235678910">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">예금주<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-input">
                                                                                <input type="text" placeholder="예금주 입력" value="에이테크">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="p-cont-sec">
                                                <div class="sec-tit-area">
                                                    <p class="f-head">파일을 첨부해주세요</p>
                                                </div>
                                                <div class="sec-con-area">
                                                    <div class="data-enter-form">
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">지원금신청서<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                                <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                <div class="file-list">
                                                                                    <p class="file-name"><span class="name">지원금신청서(에이테크)</span><span class="unit">pdf</span></p>
                                                                                    <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                                                </div>
                                                                            </div>
                                                                            <div class="file-btn-area">
                                                                                <input type="file" id="searchFile">
                                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                                            </div>
                                                                            <!-- 2023-12-20 추가 -->
                                                                            <div class="file-prev-area">
                                                                                <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a><!-- 204-01-03 속성 변경 -->
                                                                            </div>
                                                                            <!-- // 2023-12-20 추가 -->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">기술임치증<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                                <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                                                <div class="file-list">
                                                                                    <p class="file-name"><span class="name">계좌이체약정서(에이테크)</span><span class="unit">pdf</span></p>
                                                                                    <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                                                </div>
                                                                            </div>
                                                                            <div class="file-btn-area">
                                                                                <input type="file" id="searchFile">
                                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                                            </div>
                                                                            <!-- 2023-12-20 추가 -->
                                                                            <div class="file-prev-area">
                                                                                <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a><!-- 204-01-03 속성 변경 -->
                                                                            </div>
                                                                            <!-- // 2023-12-20 추가 -->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-body1">임치료 납입 영수증<span class="essential-mark color-sky">*</span></p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                                <p class="empty-txt">선택된 파일 없음</p>
                                                                            </div>
                                                                            <div class="file-btn-area">
                                                                                <input type="file" id="searchFile">
                                                                                <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                                            </div>
                                                                            <!-- 2023-12-20 추가 -->
                                                                            <div class="file-prev-area">
                                                                                <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a><!-- 204-01-03 속성 변경 -->
                                                                            </div>
                                                                            <!-- // 2023-12-20 추가 -->
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
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="bot-fix-btn-area">
                        <div class="btn-wrap align-right">
                            <button class="btn-solid small black-bg btn-role-close btn-agree" type="button"><span>저장</span></button>
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
</div>