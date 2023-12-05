package com.kap.core.dto.mp.mpa;

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
public class MPAAttctnDto extends BaseDTO {

    @Schema(title = "회원순번", example = "1")
    private Integer memSeq; //회원순번

    @Schema(title = "사업년도", example = "2022")
    private Integer  year;

    @Schema(title = "팀장명", example = "홍길동")
    private String name;

    @Schema(title = "참여구분 코드", example = "code")
    private String ptcptType;

    @Schema(title = "참여구분", example = "개인")
    private String ptcptTypeNm;

    @Schema(title = "주제코드", example = "code")
    private String themeCd;

    @Schema(title = "주제", example = "미래")
    private String themeCdNm;

    @Schema(title = "시상식코드", example = "code")
    private String wdcrmCd;

    @Schema(title = "시상식", example = "우수")
    private String wdcrmCdNm;

    @Schema(title = "서류합불코드", example = "code")
    private String seoryuCd;

    @Schema(title = "서류합불", example = "통과")
    private String seoryuCdNm;

    @Schema(title = "1차합불코드", example = "code")
    private String firstCd;

    @Schema(title = "1차합불", example = "통과")
    private String firstCdNm;

    @Schema(title = "최종합불코드", example = "code")
    private String endCd;

    @Schema(title = "최종합불", example = "수상")
    private String endCdNm;


    @Schema(title = "회차", example = "1")
    private String episd;



    private List<MPAAttctnDto> list;

    private String lgnSsnId;

}