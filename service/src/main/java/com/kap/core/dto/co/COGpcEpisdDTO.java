package com.kap.core.dto.co;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "KAP 등록 된 교육 과정의 상세 차수 정보 GPCKAP002")
public class COGpcEpisdDTO {

    @Schema(title = "KAP순번", example = "숫자")
    private Integer kapSeq;

    @Schema(title = "KAP일정순번", example = "숫자")
    private Integer kapSchdSeq;

    @Schema(title = "교육유형", example = "텍스트")
    private String eduType;

    @Schema(title = "카테고리", example = "텍스트")
    private String ctgry;

    @Schema(title = "트랙", example = "텍스트")
    private String trck;

    @Schema(title = "과정명", example = "텍스트")
    private String prcsNm;

    @Schema(title = "레벨", example = "텍스트")
    private String lvl;

    @Schema(title = "교육년도", example = "텍스트")
    private String eduYear;

    @Schema(title = "차수", example = "텍스트")
    private String ord;

    @Schema(title = "교육시작", example = "YYYY-MM-DD")
    private String eduStrt;

    @Schema(title = "교육종료", example = "YYYY-MM-DD")
    private String eduEnd;

    @Schema(title = "신청시작", example = "YYYY-MM-DD")
    private String rqstStrt;

    @Schema(title = "신청종료", example = "YYYY-MM-DD")
    private String rqstEnd;

    @Schema(title = "교육시간", example = "숫자")
    private Integer eduTime;

    @Schema(title = "정원수", example = "숫자")
    private Integer maxCnt;

    @Schema(title = "신청수", example = "숫자")
    private Integer rqstCnt;

    @Schema(title = "환급과정", example = "숫자")
    private String rfndPrcs;

    @Schema(title = "강사 ID", example = "숫자")
    private String admSeq;

    @Schema(title = "평가완료", example = "숫자")
    private String examFlag;


    @Schema(title = "전송일", example = "텍스트")
    private String kapSendDd;

    private List<COGpcEpisdDTO> list;



}