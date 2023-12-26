package com.kap.core.dto.mp.mpg;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  탈퇴
 *
 * @author 양현우
 * @since 2023.11.28
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.28  탈퇴사용자         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPGWthdrwDto extends BaseDTO {

    @Schema(title = "회원순번", example = "1")
    private Integer memSeq;

    @Schema(title = "회원코드", example = "1")
    private String memCd;

    @Schema(title = "회원코드명", example = "1")
    private String memCdNm;

    @Schema(title = "아이디", example = "1")
    private String id;

    @Schema(title = "탈퇴사유", example = "탈퇴")
    private String wthdrwRsnNm;

    @Schema(title = "탈퇴사유기타", example = "탈퇴")
    private String wthdrwRsnEtcNm;

    @Schema(title = "탈퇴일", example = "2023-10-11")
    private String wthdrwRegDtm;

    private String lgnSsnId;

    private List<MPGWthdrwDto> list;

    @Schema(title = "회원분류", example = "LIST")
    private List<String> memCdList;

    @Schema(title = "검색날짜param", example = "1")
    private String date;

    @Schema(title = "엑셀사유", example = "asd")
    private String rsn;

}
