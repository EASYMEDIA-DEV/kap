package com.kap.core.dto.mp.mpd;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  부품사회원 - 컨설팅 사업
 *
 * @author 양현우
 * @since 2023.11.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.20  부품사회원 컨설팅 사업 현황         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPDKenDto extends BaseDTO {

    @Schema(title = "출석순번", example = "1")
    private int atndcSeq;

    @Schema(title = "출석일", example = "2023-01-01")
    private String atndcDt;

    @Schema(title = "근태옵션명", example = "지도")
    private String atndcCdNm;

    @Schema(title = "근태옵션", example = "CODE")
    private String atndcCd;

    @Schema(title = "회사명", example = "CODE")
    private String cmpnNm;

    @Schema(title = "지도부품사1", example = "부품사1")
    private String guidePartCmpn1;

    @Schema(title = "회사사업자번호", example = "1234")
    private String appctnBsnmNo;

    @Schema(title = "소재지1", example = "1234")
    private String firstRgnsCd;

    @Schema(title = "소재지1명", example = "1234")
    private String firstRgnsCdNm;

    @Schema(title = "소재지2", example = "1234")
    private String scndRgnsCd;

    @Schema(title = "소재지2명", example = "1234")
    private String scndRgnsCdNm;

    @Schema(title = "컨설팅순번", example = "1234")
    private String cnstgSeq;

    @Schema(title = "컨설팅순번1", example = "1234")
    private String cnstgSeq1;

    @Schema(title = "컨설팅순번2", example = "1234")
    private String cnstgSeq2;

    @Schema(title = "소재지역1", example = "경북")
    private String rgns1;

    @Schema(title = "지도부품사2", example = "부품사2")
    private String guidePartCmpn2;

    @Schema(title = "탈퇴여부", example = "Y")
    @Builder.Default
    private String wthdrwYn = "Y";

    @Schema(title = "total", example = "0")
    private int total;


    @Schema(title = "소재지역2", example = "경북2")
    private String rgns2;

    @Schema(title = "기타출장", example = "기타")
    private String etcBsntrp;

    @Schema(title = "기타", example = "기타")
    private String etc;

    @Schema(title = "근태체크일", example = "기타")
    private String monthpicker;

    @Schema(title = "년도", example = "2022")
    private String year;

    @Schema(title = "월", example = "01")
    private String mnth;

    @Schema(title = "일", example = "01")
    private String dd;

    @Schema(title = "이름", example = "홍길동")
    private String name;

    @Schema(title="일일 / 월 근태 엑셀 타입" , example = "D")
    private String excelType;

    @Schema(title="엑셀 여부" , example = "Y")
    private String excelYn;

    @Schema(title="사유" , example = "D")
    private String rsn;

    //로그인세션ID
    private String lgnSsnId;

    @Schema(title="일" , example = "1")
    private List<String> days ;

    @Schema(title="월" , example = "1")
    private List<String> months ;

    @Schema(title="월" , example = "2020-11-11,2020-11-11")
    private String monthDays;

    @Schema(title="근태타입" , example = "cmssr")
    private String monthTypes;

    @Schema(title="근태타입이름" , example = "지도,지도")
    private String monthTypesNm;


    private List<String> kenDays;


    @Schema(title = "근태 목록", example = "DTO의 리스트")
    private List<MPDKenDto> list;

    @Schema(title="컨설팅 갯수" , example = "0")
    private Integer cnstgCnt;

    @Schema(title="출근 갯수" , example = "0")
    private int atndnCnt;

    @Schema(title="강의 갯수" , example = "0")
    private int lectureCnt;

    @Schema(title="역량 개발 갯수" , example = "0")
    private int cpcptyDvlpmCnt;

    @Schema(title="연차 갯수" , example = "0")
    private int annualCnt;

    @Schema(title="재택 갯수" , example = "0")
    private int homeCnt;

    @Schema(title="기타갯수" , example = "0")
    private int etcCnt;



}