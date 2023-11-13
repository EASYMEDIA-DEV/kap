package com.kap.core.dto;

import com.kap.core.dto.BaseDTO;
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

    private String titl; //제목

    private String startInqrDtm; //문의등록일

    private String endInqrDtm;  //답변등록일

    private String inqrStts; //진행상태

    private List<MPAInqrDto> list;

    private String lgnSsnId;

}