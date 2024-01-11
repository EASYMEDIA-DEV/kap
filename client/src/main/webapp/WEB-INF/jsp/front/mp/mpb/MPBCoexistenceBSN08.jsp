<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-sec no-border scroll-motion">
    <div class="for-motion">
        <div class="sec-tit-area">
            <p class="f-title3">사업진행상황</p>
        </div>
        <div class="sec-con-area">
            <div class="article-sec">
                <div class="article-list-w accordion-st"><!-- accordion-st : 아코디언 스타일 -->
                    <div class="list-item active"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">신청</p>
                            </div>
                            <p class="box-label bigger arr"><span>보완요청</span></p>
                        </a>
                        <div class="acco-hide-area">
                            <p class="exclamation-txt f-body1">사업신청서에 전년도 매출액 누락되어 보완 바랍니다.</p>
                            <div class="data-enter-form">
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">신청내용</p>
                                        <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                <p class="data-title f-body1"><span>지원금<i class="circle-num"><img src="/common/images/icon-circle-num-1.svg" alt="1"></i></span><span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" placeholder="지원금 입력" value="18,321,190">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                <p class="data-title f-body1"><span>자부담<i class="circle-num"><img src="/common/images/icon-circle-num-2.svg" alt="2"></i></span><span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" placeholder="자부담 입력" value="0">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <!-- @ essential-mark 사용을 위해 title에 flex가 걸려있기 때문에, circle-num이 사용될 경우, span 태그로 한번 감싸야함. -->
                                                <p class="data-title f-body1"><span>총금액<i class="circle-num"><img src="/common/images/icon-circle-num-1.svg" alt="1"></i>+<i class="circle-num"><img src="/common/images/icon-circle-num-2.svg" alt="2"></i></span><span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" placeholder="총금액 입력" value="18,321,190" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">첨부파일</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="noti-txt-w">
                                                    <P class="bullet-noti-txt f-caption2">* 첨부 가능 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개)</P>
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <p class="data-title f-body1">사업신청서<span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                    </div>
                                                    <div class="file-btn-area">
                                                        <input type="file" id="searchFile">
                                                        <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                    </div>
                                                    <!-- 2024-01-03 추가 -->
                                                    <div class="file-prev-area">
                                                        <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a>
                                                    </div>
                                                    <!-- 2024-01-03 추가 -->
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <div class="inner-line">
                                                    <p class="data-title f-body1">계좌이체약정서<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                            <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                            <div class="file-list">
                                                                <p class="file-name"><span class="name">(주)이지미디어개선활동개선활동개선활동개선활동개선활동개선활동활동개선활동개선활동활동개선활동개선활동</span><span class="unit">pdf</span></p>
                                                                <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                            </div>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input type="file" id="searchFile">
                                                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                        <div class="file-prev-area">
                                                            <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <div class="inner-line">
                                                    <p class="data-title f-body1">통장사본<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                            <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                            <div class="file-list">
                                                                <p class="file-name"><span class="name">(주)이지미디어개선활동개선활동개선활동개선활동개선활동개선활동활동개선활동개선활동활동개선활동개선활동</span><span class="unit">pdf</span></p>
                                                                <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                            </div>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input type="file" id="searchFile">
                                                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                        <div class="file-prev-area">
                                                            <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="btn-wrap align-right">
                                <a class="btn-solid small black-bg" href="javascript:"><span>저장</span></a>
                            </div>
                        </div>
                    </div>
                    <div class="list-item"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">증빙</p>
                            </div>
                            <p class="box-label bigger"><span>접수전</span></p>
                        </a>
                        <div class="acco-hide-area">
                            <p class="exclamation-txt f-body1">사업신청서에 전년도 매출액 누락되어 보완 바랍니다.</p>
                            <div class="data-enter-form">
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">신청내용</p>
                                        <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <p class="data-title f-body1">지원금 ①<span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" placeholder="지원금 입력" value="18,321,190">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <p class="data-title f-body1">자부담 ②<span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" placeholder="자부담 입력" value="0">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <p class="data-title f-body1">총금액 ① + ②<span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" placeholder="총금액 입력" value="18,321,190" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">첨부파일</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="noti-txt-w">
                                                    <P class="bullet-noti-txt f-caption2">* 첨부 가능 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개)</P>
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <p class="data-title f-body1">사업신청서<span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                    </div>
                                                    <div class="file-btn-area">
                                                        <input type="file" id="searchFile">
                                                        <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                    </div>
                                                    <!-- 2024-01-03 추가 -->
                                                    <div class="file-prev-area">
                                                        <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a>
                                                    </div>
                                                    <!-- 2024-01-03 추가 -->
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <div class="inner-line">
                                                    <p class="data-title f-body1">계좌이체약정서<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                            <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                            <div class="file-list">
                                                                <p class="file-name"><span class="name">(주)이지미디어개선활동개선활동개선활동개선활동개선활동개선활동활동개선활동개선활동활동개선활동개선활동</span><span class="unit">pdf</span></p>
                                                                <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                            </div>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input type="file" id="searchFile">
                                                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                        <div class="file-prev-area">
                                                            <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <div class="inner-line">
                                                    <p class="data-title f-body1">통장사본<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                            <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                            <div class="file-list">
                                                                <p class="file-name"><span class="name">(주)이지미디어개선활동개선활동개선활동개선활동개선활동개선활동활동개선활동개선활동활동개선활동개선활동</span><span class="unit">pdf</span></p>
                                                                <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                            </div>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input type="file" id="searchFile">
                                                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                        <div class="file-prev-area">
                                                            <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="btn-wrap align-right">
                                <a class="btn-solid small black-bg" href="javascript:"><span>저장</span></a>
                            </div>
                        </div>
                    </div>
                    <div class="list-item"><!-- 활성화된 단계 active 클래스 추가 (아코디언 열림) -->
                        <a class="acco-click-area" href="javascript:">
                            <div class="txt-box">
                                <p class="tit f-head">심사</p>
                            </div>
                            <p class="box-label bigger"><span>접수전</span></p>
                        </a>
                        <div class="acco-hide-area">
                            <p class="exclamation-txt f-body1">사업신청서에 전년도 매출액 누락되어 보완 바랍니다.</p>
                            <div class="data-enter-form">
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">신청내용</p>
                                        <p class="f-caption1"><span class="essential-mark color-sky">*</span> 표시는 필수 기재 항목입니다.</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <p class="data-title f-body1">지원금 ①<span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" placeholder="지원금 입력" value="18,321,190">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <p class="data-title f-body1">자부담 ②<span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" placeholder="자부담 입력" value="0">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <p class="data-title f-body1">총금액 ① + ②<span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="form-input w-longer">
                                                        <input type="text" placeholder="총금액 입력" value="18,321,190" readonly>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">첨부파일</p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="noti-txt-w">
                                                    <P class="bullet-noti-txt f-caption2">* 첨부 가능 확장자(jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip) / 용량(최대50MB) / 최대개수(1개)</P>
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <p class="data-title f-body1">사업신청서<span class="essential-mark color-sky">*</span></p>
                                                <div class="form-group">
                                                    <div class="file-list-area"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                        <p class="empty-txt">선택된 파일 없음</p>
                                                    </div>
                                                    <div class="file-btn-area">
                                                        <input type="file" id="searchFile">
                                                        <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                    </div>
                                                    <!-- 2024-01-03 추가 -->
                                                    <div class="file-prev-area">
                                                        <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a>
                                                    </div>
                                                    <!-- 2024-01-03 추가 -->
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <div class="inner-line">
                                                    <p class="data-title f-body1">계좌이체약정서<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                            <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                            <div class="file-list">
                                                                <p class="file-name"><span class="name">(주)이지미디어개선활동개선활동개선활동개선활동개선활동개선활동활동개선활동개선활동활동개선활동개선활동</span><span class="unit">pdf</span></p>
                                                                <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                            </div>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input type="file" id="searchFile">
                                                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                        <div class="file-prev-area">
                                                            <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="data-line">
                                                <div class="inner-line">
                                                    <p class="data-title f-body1">통장사본<span class="essential-mark color-sky">*</span></p>
                                                    <div class="form-group">
                                                        <div class="file-list-area attached"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                            <p class="empty-txt">선택된 파일 없음</p>
                                                            <!-- 파일 첨부되면 file-list 영역 생성 -->
                                                            <div class="file-list">
                                                                <p class="file-name"><span class="name">(주)이지미디어개선활동개선활동개선활동개선활동개선활동개선활동활동개선활동개선활동활동개선활동개선활동</span><span class="unit">pdf</span></p>
                                                                <button class="btn-delete" title="파일 삭제하기" type="button"></button>
                                                            </div>
                                                        </div>
                                                        <div class="file-btn-area">
                                                            <input type="file" id="searchFile">
                                                            <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                        <div class="file-prev-area">
                                                            <a href="javascript:" download="" title="파일 다운로드">이전에 등록된 파일.pdf</a>
                                                        </div>
                                                        <!-- 2024-01-03 추가 -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="btn-wrap align-right">
                                <a class="btn-solid small black-bg" href="javascript:"><span>저장</span></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
