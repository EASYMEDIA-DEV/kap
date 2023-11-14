package com.kap.core.dto;

/**
 *  미래차공모전 신청내역
 *
 * @author 양현우
 * @since 2023.11.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  일반사용자         최초 생성
 * </pre>
 */

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
public class MPAAttctnDto extends BaseDTO {

    private Integer memSeq; //회원순번

    private Integer  year; //사업년도

    private String name; //팀장명

    private String participationCategory; //참여구분

    private String ctgryCd; //카테고리

    private String wdcrmCd; //시상부문

    private String episd;   //회차
    private String appctnSttsCd; //1차결과
    private String mngSttsCd; //최종결과

    private List<MPAAttctnDto> list;

    private String lgnSsnId;

}