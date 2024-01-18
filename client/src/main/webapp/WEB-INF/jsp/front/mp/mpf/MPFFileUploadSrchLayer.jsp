<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap">
    <spring:eval var="imgExtns" expression="@environment.getProperty('app.file.fileExtns')" />
    <spring:eval var="atchUploadMaxSize" expression="104857600" />
    <input type="hidden" id="imgExtns" value="${imgExtns}"/>
    <input type="hidden" id="atchUploadMaxSize" value="${atchUploadMaxSize}"/>
    <!-- 자료 업로드 팝업 -->
    <div class="layer-popup paymentInfoManagPopup" ><!-- 팝업 디자인 확인을 위해 first-show 클래스 추가한 상태 -->
        <div class="for-center">
            <div class="pop-wrap">
                <div class="pop-con-area">
                    <div class="tit-area">
                        <p class="f-title1">자료 업로드   </p>
                    </div>
                    <div class="con-area">
                        <div class="scroll-area">
                            <div class="p-cont-sec">
                                <div class="sec-tit-area">
                                    <p class="f-head">파일을 첨부해주세요</p>
                                </div>
                                <div class="sec-con-area">
                                    <div class="data-enter-form">
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">지도부품사</p>
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="form-group">
                                                            <div class="form-select">
                                                                <select id="cmpnListSel" title="선택">
                                                                    <option value="" selected>선택</option>
                                                                    <c:forEach var="list" items="${cmpnData.list}" varStatus="status">
                                                                        <option value="${list.cnstgSeq}">${list.cmpnNm}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">킥오프자료</p><!-- 2023-12-06 필수 표시 마크 삭제 -->
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="inner-line">
                                                            <div class="form-group">
                                                                <div class="file-list-area" id="kickOffFile"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                    <p class="empty-txt" id="emptykickFile">선택된 파일 없음</p>
                                                                    <div class="file-list" id="showKickFile" style="display:none">
                                                                        <p class="file-name">
                                                                            <span class="name" id="fileKickNm"></span>
                                                                        </p>
                                                                        <a class="btn-delete" title="파일삭제" href="javascript:" id="delKickFile"></a>
                                                                    </div>
                                                                </div>
                                                                <div class="file-btn-area">
                                                                    <input type="file" id="searchFile">
                                                                    <label class="btn-solid gray-bg" for="searchFile">파일 찾기</label>
                                                                </div>
                                                                <!-- 2023-12-20 추가 -->
                                                                <div class="file-prev-area" id="prevKickFile">
                                                                </div>
                                                                <!-- // 2023-12-20 추가 -->
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="th">
                                                <p class="title f-head">랩업자료</p><!-- 2023-12-06 필수 표시 마크 삭제 -->
                                            </div>
                                            <div class="td">
                                                <div class="data-line-w">
                                                    <div class="data-line">
                                                        <div class="inner-line">
                                                            <div class="form-group">
                                                                <div class="file-list-area" id="LvlFile"><!-- 파일 첨부되면 attached 클래스 추가 -->
                                                                    <p class="empty-txt" id="emptyLvlFile">선택된 파일 없음</p>
                                                                    <div class="file-list" id="showLvlFile" style="display:none">
                                                                        <p class="file-name">
                                                                            <span class="name" id="fileLvlNm"></span>
                                                                        </p>
                                                                        <a class="btn-delete" title="파일삭제" href="javascript:" id="delLvlFile"></a>
                                                                    </div>
                                                                </div>
                                                                <div class="file-btn-area">
                                                                    <input type="file" id="searchLvlFile">
                                                                    <label class="btn-solid gray-bg" for="searchLvlFile">파일 찾기</label>
                                                                </div>
                                                                <!-- 2023-12-20 추가 -->
                                                                <div class="file-prev-area" id="prevLvlFile">
                                                                </div>
                                                                <!-- // 2023-12-20 추가 -->
                                                            </div>
                                                        </div>
                                                        <div class="inner-line">
                                                            <div class="noti-txt-w">
                                                                <p class="bullet-noti-txt f-caption2">* jpg,jpeg,gif,png,bmp,mp4,webm,wmv,avi,mkv,pdf,ppt,pptx,xls,xlsx,doc,docx,hwp,hwpx,txt,zip 파일만 등록 가능합니다. (100MB 이하, 최대 1개 파일 등록 가능)</p><!-- 2023-12-06 텍스트 수정 -->
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
                            <a href="javascript:" class="btn-solid small black-bg " id="fileSave"><span>저장</span></a>
                        </div>
                    </div>
                    <div class="user-opt-area">
                        <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>