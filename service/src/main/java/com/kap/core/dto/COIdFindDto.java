package com.kap.core.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  사용자
 *
 * @author 양현우
 * @since 2023.12.05
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.05  일반사용자         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class COIdFindDto extends BaseDTO  {

    @Schema(title = "회원순번", example = "1")
    private Integer memSeq;

    @Schema(title = "이름", example = "ㅇㅇ")
    private String name;

    @Schema(title = "생년", example = "19999999")
    private String birthdate;

    @Schema(title="성별")
    private String gndr;


    @Schema(title = "휴대폰번호", example = "010-1234-4567")
    private String mobileno;

    @Schema(title = "타입", example = "1")
    private String types;

    @Schema(title = "이메일", example = "1")
    private String email;

    @Schema(title = "아이디", example = "1")
    private String id;

    @Schema(title = "ci")
    private String ci;

    private String param1;


}
