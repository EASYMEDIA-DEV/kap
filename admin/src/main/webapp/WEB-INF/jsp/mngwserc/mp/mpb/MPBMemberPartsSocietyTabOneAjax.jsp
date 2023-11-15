<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<form class="form-horizontal" id="frmData" name="frmData" method="post" >
    <input type="hidden" id="oldEmail" name="oldEmail" value=${rtnDtl.email} />
    <input type="hidden" id="id" name="id" value=${rtnDtl.id} />
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
                <th scope="row" class="bg-gray-lighter">이름 <span style="color: red">*</span></th>
                <td>${rtnDtl.name}</td>
                <th scope="row" class="bg-gray-lighter">부품사명 <span style="color: red">*</span></th>
                <td>   <input type="text" class="form-control input-sm" id="ctgry_cd" title="부품사명" name="ctgry_cd" readonly  value="${rtnDtl.cmpnNm}"/>
                </td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">사업자등록번호 <span style="color: red">*</span></th>
                <td> <input type="text" class="form-control input-sm" id="workBsnmNo" title="부품사명" name="workBsnmNo"   value="${rtnDtl.workBsnmNo}"/>
                    <button type="button" class="btn btn-secondary" id="btnBsnmNo" >인증[TODO]</button> </td>
                <th scope="row" class="bg-gray-lighter">구분 <span style="color: red">*</span></th>
                <td>
                    <select class="form-control input-sm" name="ctgryCd" disabled>
                    <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                        <c:if test="${fn:contains(cdList, 'COMPANY010')}">
                            <option value="${cdList.cd}"  ${rtnDtl.ctgryCd == cdList.cd ? 'selected' : ''} >${cdList.cdNm}</option>
                        </c:if>
                    </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">부서 <span style="color: red">*</span></th>
                <td>
                    <select class="form-control input-sm" name="deptCd">
                        <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                            <c:if test="${fn:contains(cdList, 'MEM_CD010')}">
                                <option value="${cdList.cd}"  ${rtnDtl.deptCd == cdList.cd ? 'selected' : ''}>${cdList.cdNm}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <input type="text" class="form-control input-sm" id="deptDtlNm" title="부서상세" name="deptDtlNm"  value="${rtnDtl.deptDtlNm}"/>
                </td>
                <th scope="row" class="bg-gray-lighter">직급 <span style="color: red">*</span></th>
                <td>
                    <select class="form-control input-sm" name="pstnCd">
                        <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                            <c:if test="${fn:contains(cdList, 'MEM_CD020')}">
                                <option value="${cdList.cd}"  ${rtnDtl.pstnCd == cdList.cd ? 'selected' : ''}>${cdList.cdNm}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">아이디 <span style="color: red">*</span></th>
                <td>${rtnDtl.id}</td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">비밀번호</th>
                <td><button type="button" class="btn btn-secondary" id="btnPwdInit" data-id="${rtnDtl.id}" >비밀번호 초기화</button></td>
            </tr>
            <tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">주소 <span style="color: red">*</span></th>
                <td>
                    <div class="col-sm-10">
                    <div class="form-group input-group">
                        <input type="text" class="form-control input-sm" id="zipcode" title="우편번호" name="zipcode" readonly  value="${rtnDtl.zipcode}"/>
                        <button type="button" class="btn btn-secondary" id="searchPostCode" >우편번호 검색</button>
                    </div>
                    </div>
                    <div class="form-group col-sm-10">
                        <input type="text" class="form-control input-sm" title="주소" id="bscAddr" name="bscAddr"  readonly  value="${rtnDtl.bscAddr}"/>
                    </div>
                    <div class="form-group col-sm-10">
                        <input type="text" class="form-control input-sm" title="주소" id="dtlAddr" name="dtlAddr" value="${rtnDtl.dtlAddr}"/>
                    </div>

                </td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">전화번호 </th>
                <td> <div class="form-group col-sm-10">
                        <input type="text" class="form-control input-sm notRequired" id="telNo" name="telNo" value="${rtnDtl.telNo}" oninput="this.value=this.value.replace(/[^0-9]/g, '')"/>
                    </div>
                </td>
                <th scope="row" class="bg-gray-lighter">휴대번호 <span style="color: red">*</span></th>
                <td> <div  class="form-group col-sm-10">
                        <input type="text" class="form-control input-sm" title="휴대번호" id="hpNo" name="hpNo" value="${rtnDtl.hpNo}" oninput="this.value=this.value.replace(/[^0-9]/g, '')"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">이메일  <span style="color: red">*</span></th>
                <td> <div class="form-group col-sm-10">
                    <input type="text" class="form-control input-sm"  title="이메일" id="email" value="${rtnDtl.email}" name="email" maxlength="50" oninput="this.value=this.value.replace(/[^\x00-\x7F]/g, '')"/>
                    <button type="button" class="btn btn-secondary" id="dupEmail" >중복확인</button>
                </div>
                </td>
            </tr>
            <tr>
                <th scope="row" class="bg-gray-lighter">생년월일 </th>
                <td> <div class="col-sm-10">
                    ${ kl:birthConvert(rtnDtl.birth)}
                </div>
                </td>
                <th scope="row" class="bg-gray-lighter">성별 <span style="color: red">*</span></th>
                <td> <div class="col-sm-10">
                    ${rtnDtl.gndr}
                </div>
                </td>
            </tr>

            <tr>
                <th scope="row" class="bg-gray-lighter">SMS 수신여부 <span style="color: red">*</span> </th>
                <td>
                    <div class="form-group text-sm">
                        <div class="col-sm-10">
                            <label class="radio-inline c-radio">
                                <input type="radio" name="ntfySmsRcvYn" class="notRequired"  value="Y" checked ${rtnDtl.ntfySmsRcvYn.toString() == 'Y' ? 'checked' : ''} />
                                <span class="ion-record"></span> 수신
                            </label>
                            <label class="radio-inline c-radio">
                                <input type="radio" name="ntfySmsRcvYn" class="notRequired" value="N"  ${rtnDtl.ntfySmsRcvYn.toString() == 'N' ? 'checked' : ''}/>
                                <span class="ion-record"></span> 수신 안함
                            </label>
                            ${ kl:convertDate(rtnDtl.ntfySmsRcvChngDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')} 변경
                        </div>
                    </div>
                </td>
                <th scope="row" class="bg-gray-lighter">이메일 수신여부 <span style="color: red">*</span> </th>
                <td>
                    <div class="form-group text-sm">
                        <div class="col-sm-10">
                            <label class="radio-inline c-radio">
                                <input type="radio" name="ntfyEmailRcvYn" class="notRequired" value="Y"  ${rtnDtl.ntfyEmailRcvYn.toString() == 'Y' ? 'checked' : ''} />
                                <span class="ion-record"></span> 수신
                            </label>
                            <label class="radio-inline c-radio">
                                <input type="radio" name="ntfyEmailRcvYn" class="notRequired" value="N"  ${rtnDtl.ntfyEmailRcvYn.toString() == 'N' ? 'checked' : ''}/>
                                <span class="ion-record"></span> 수신 안함
                            </label>
                            ${ kl:convertDate(rtnDtl.ntfyEmailRcvChngDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')} 변경
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
                <th scope="row" class="bg-gray-lighter">최종 수정자 <span style="color: red">*</span></th>
                <td>${rtnDtl.modName}</td>
                <th scope="row" class="bg-gray-lighter">최종 수정일시 <span style="color: red">*</span></th>
                <td>${ kl:convertDate(rtnDtl.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</td>
            </tr>
            </tbody>
        </table>

