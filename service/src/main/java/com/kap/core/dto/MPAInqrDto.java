package com.kap.core.dto;

import com.kap.core.dto.BaseDTO;
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
public class MPAInqrDto extends BaseDTO {

    @Schema(title = "제목", example = "제목")
    private String titl;

    @Schema(title = "문의등록일", example = "date")
    private String startInqrDtm;

    @Schema(title = "답변등록일", example = "date")
    private String endInqrDtm;

    @Schema(title = "진행상태", example = "등록")
    private String inqrStts;

    @Schema(title = "부모 카테고리", example = "일반")
    private String parntCtgryCdNm ;

    @Schema(title = "카테고리", example = "아이디찾기")
    private String ctgryCdNm;

    private List<MPAInqrDto> list;

    private String lgnSsnId;



}