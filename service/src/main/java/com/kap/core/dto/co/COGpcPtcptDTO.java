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
@Schema(title = "KAP 교육 신청자 정보 GPCKAP003")
public class COGpcPtcptDTO {

    @Schema(title = "KAP순번", example = "숫자")
    private Integer kapSeq;

    @Schema(title = "KAP일정순번", example = "숫자")
    private Integer kapSchdSeq;

    @Schema(title = "KAP Id", example = "텍스트")
    private String kapId;

    @Schema(title = "성명", example = "텍스트")
    private String name;

    @Schema(title = "성별", example = "텍스트")
    private String gndrCd;

    @Schema(title = "회사명", example = "텍스트")
    private String cmpnNm;

    @Schema(title = "카테고리", example = "텍스트")
    private String ctgry;

    @Schema(title = "트랙", example = "텍스트")
    private String trck;

    @Schema(title = "과정명", example = "텍스트")
    private String prcsNm;

    @Schema(title = "레벨", example = "텍스트")
    private String lvl;

    @Schema(title = "교육차수", example = "텍스트")
    private String ord;

    @Schema(title = "교육일수", example = "숫자")
    private Integer eduDdCnt;

    @Schema(title = "교육시작", example = "YYYY-MM-DD")
    private String eduStrt;

    @Schema(title = "교육종료", example = "YYYY-MM-DD")
    private String eduEnd;

    @Schema(title = "환급과정", example = "숫자")
    private String rfndPrcs;

    @Schema(title = "총점", example = "숫자")
    private Integer totScore;

    @Schema(title = "이수여부", example = "Y/N")
    private String cmpltYn;

    @Schema(title = "전송일", example = "텍스트")
    private String kapSendDd;



    private List<COGpcPtcptDTO> list;



}