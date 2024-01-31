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
@Schema(title = "GPC API 등록된 과정정보 호출 GPCKAP001")
public class COGpcEdctnDTO {

    @Schema(title = "KAP순번", example = "숫자")
    private Integer kapSeq;

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

    @Schema(title = "KAP링크", example = "텍스트")
    private String kapUrl;

    @Schema(title = "교육일수", example = "숫자")
    private Integer eduDdCnt;

    @Schema(title = "교육시간", example = "숫자")
    private Integer eduTime;

    @Schema(title = "정원수", example = "숫자")
    private Integer gpcFxnumCnt;

    @Schema(title = "환급과정", example = "텍스트")
    private String rfndPrcs;

    @Schema(title = "강사 ID", example = "텍스트")
    private Integer admSeq;

    @Schema(title = "교육내용", example = "텍스트")
    private String eduCntn;

    @Schema(title = "교육상세", example = "텍스트")
    private String eduDtl;

    @Schema(title = "전송일", example = "텍스트")
    private String kapSendDd;

    @Schema(title = "교육차수순번", example = "날짜 YYYY-MM-DD")
    private String date;


    private List<COGpcEdctnDTO> list;



}