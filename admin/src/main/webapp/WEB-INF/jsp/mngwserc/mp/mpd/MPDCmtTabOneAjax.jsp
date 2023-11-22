
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- Datepicker-->
<form class="form-horizontal" id="frmData" name="frmData" method="post" >
    <input type="hidden" id="oldEmail" name="oldEmail" value=${rtnDtl.email} />
    <input type="hidden" id="id" name="id" value=${rtnDtl.id} />
    <input type="hidden" id="cmssrCbsnCdSe" name="cmssrCbsnCdSe" value=${rtnDtl.cmssrCbsnCd} />
    <input type="hidden" class="notRequired" id="fileSeq" name="fileSeq" value="${rtnDtl.cmssrPhotoFileSeq}" />
</form>
        <table class="table">
            <colgroup>
                <col style="width:10%;">
                <col style="width:40%;">
                <col style="width:10%;">
                <col style="width:40%;">
            </colgroup>
            <tbody>

            <tr>
                <th scope="row" class="bg-gray-lighter">아이디 <span style="color: red">*</span></th>
                <td>${rtnDtl.id}</td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">비밀번호</th>
                <td><button type="button" class="btn btn-secondary" id="btnPwdInit" data-id="${rtnDtl.id}" >비밀번호 초기화</button></td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">이름 <span style="color: red">*</span></th>
                <td>${rtnDtl.name}</td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">위원구분<span style="color:red">*</span></th>
                <td>
                    <select class="form-control input-sm" id="cmssrTypeCd" name="cmssrTypeCd" title="위원구분" >
                        <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                            <c:if test="${fn:contains(cdList, 'MEM_CD030')}">
                                <c:if test="${fn:contains(cdList.dpth, '3')}">
                                    <option value="${cdList.cd}" ${ rtnDtl.cmssrTypeCd eq cdList.cd ? 'selected' : '' }>
                                            ${cdList.cdNm}
                                    </option>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
                <th scope="row" class="cmssrCdDiv bg-gray-lighter">업종/분야<span style="color:red">*</span></th>
                <td class="cmssrCdDiv">
                    <select class="form-control input-sm notRequired" id="cmssrCbsnCd" name="cmssrCbsnCd" title="업종분야" >
                        <option value="">선택</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">생년월일<span style="color:red">*</span></th>
                <td>
                    <div class="input-group">
                        <input type="text" class="form-control input-sm datetimepicker_strtDt" name="birth" value="${rtnDtl.birth}" title="생년월일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                        <span class="input-group-btn" style="z-index:0;">
                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this);jQuery(this).parent().prev().focus();">
                                            <em class="ion-calendar"></em>
                                        </button>
                                    </span>
                    </div>
                </td>
                <th scope="row" class="bg-gray-lighter">재직여부<span style="color:red">*</span></th>
                <td>
                    <select class="form-control input-sm" id="cmssrWorkCd" name="cmssrWorkCd" title="재직여부" >
                        <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                            <c:if test="${fn:contains(cdList, 'MEM_CD040')}">
                                <option value="${cdList.cd}" ${ rtnDtl.cmssrWorkCd eq cdList.cd ? 'selected' : '' }>
                                        ${cdList.cdNm}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>


                <th scope="row" class="bg-gray-lighter">입사일<span style="color:red">*</span></th>
                <td>
                    <div class="input-group">
                        <input type="text" class="form-control input-sm datetimepicker_strtDt" name="cmssrMplmnDt" value="${rtnDtl.cmssrMplmnDt}" title="입사일"  readonly onclick="cmmCtrl.initCalendar(this);"/>
                        <span class="input-group-btn" style="z-index:0;">
                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus(); ">
                                            <em class="ion-calendar"></em>
                                        </button>
                                    </span>
                    </div>
                </td>
                <th scope="row" class="bg-gray-lighter">퇴사일</th>
                <td>
                    <div class="input-group">
                        <input type="text" class="notRequired form-control input-sm datetimepicker_strtDt " name="cmssrRsgntDt" value="${rtnDtl.cmssrRsgntDt}" title="퇴사일" readonly onclick="cmmCtrl.initCalendar(this);"/>
                        <span class="input-group-btn" style="z-index:0;">
                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                            <em class="ion-calendar"></em>
                                        </button>
                                    </span>
                    </div>
                </td>

            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">휴대폰번호<span style="color:red">*</span></th>
                <td>
                    <input type="text" class="form-control input-sm" id="hpNo" title="휴대폰번호" name="hpNo" value="${rtnDtl.hpNo}" oninput="this.value=this.value.replace(/[^0-9]/g, '')" maxlength="13"/>
                </td>
                <th scope="row" class="bg-gray-lighter">이메일  <span style="color: red">*</span></th>
                <td> <div class="form-group col-sm-10">
                    <input type="text" class="form-control input-sm"  title="이메일" id="email" value="${rtnDtl.email}" name="email" maxlength="50" oninput="this.value=this.value.replace(/[^\x00-\x7F]/g, '')"/>
                    <button type="button" class="btn btn-secondary" id="dupEmail" >중복확인</button>
                </div>
                </td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">사진<span style="color:red">*</span></th>
                <td colspan="3">
                    <div class="form-group text-sm">
                        <div class="col-sm-10 col-md-11">
                            <spring:eval var="imgExtns" expression="@environment.getProperty('app.file.imgExtns')" />
                            <spring:eval var="atchUploadMaxSize" expression="5242880" />
                            <div class="dropzone attachFile" data-file-field-nm="fileSeq" data-file-extn="${imgType}" data-max-file-size="${atchUploadMaxSize}" data-max-file-cnt="1" data-titl="이미지">
                                <div class="dz-default dz-message">
                                    <span><em class="ion-upload text-info icon-2x"></em><br />파일을 드래그&드랍 또는 선택해주세요</span>
                                </div>
                            </div>
                            <p class="text-bold mt">
                                ※ 1920 X 1080 / 파일 확장자(${imgType}) / 최대용량 (<fmt:formatNumber value="${atchUploadMaxSize / 1024 / 1024}" maxFractionDigits="1" />MB 이하) / 최대 개수 (1개)
                            </p>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">주요경력<span style="color:red">*</span></th>
                <td colspan="3">
                    <textarea style="width: -webkit-fill-available" type="text" id="cmssrMjrCarerCntn" title="주요경력" name="cmssrMjrCarerCntn">${rtnDtl.cmssrMjrCarerCntn}</textarea>
                </td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">주요경력 화면노출여부<span style="color:red">*</span></th>
                <td>
                    <div class="form-group text-sm">
                        <div class="col-sm-10">
                            <label class="radio-inline c-radio">
                                <input type="radio" name="cmssrMjrCarerExpsYn"  value="Y" ${rtnDtl.cmssrMjrCarerExpsYn.toString() == 'Y' ? 'checked' : ''}  />
                                <span class="ion-record"></span> 노출
                            </label>
                            <label class="radio-inline c-radio">
                                <input type="radio" name="cmssrMjrCarerExpsYn" class="notRequired" value="N" ${rtnDtl.cmssrMjrCarerExpsYn.toString() == 'N' ? 'checked' : ''} />
                                <span class="ion-record"></span> 미노출
                            </label>
                        </div>
                    </div>
                </td>

            </tr>
            </tbody>
        </table>
    <h5>등록/수정이력</h5>
        <table class="table">
            <colgroup>
                <col style="width:10%;">
                <col style="width:40%;">
                <col style="width:10%;">
                <col style="width:40%;">
            </colgroup>
            <tbody>
            <tr>
                <th scope="row" class="bg-gray-lighter">최초 등록자 </th>
                <td>${rtnDtl.regName}(${rtnDtl.regId})</td>
                <th scope="row" class="bg-gray-lighter">최초 등록일시</th>
                <td>${ kl:convertDate(rtnDtl.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">최종 수정자</th>
                <td>${rtnDtl.modName}(${rtnDtl.modId})</td>
                <th scope="row" class="bg-gray-lighter">최종 수정일시 </th>
                <td>${ kl:convertDate(rtnDtl.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</td>
            </tr>
            </tbody>
        </table>

