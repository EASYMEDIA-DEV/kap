package com.kap.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  메일 객체
 *
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.01.10  박주석         최초 생성
 * </pre>
 */
//GETTER, SETTER
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Schema(title="이메일 발송 DTO")
@Hidden
public class COMailDTO extends COMessageDTO {
    //@Schema(title="발송 성공 실패 여부")
    //private int return_url = 0;
    //@Schema(title="메일 열람 확인")
    //private int open = 0;
    //@Schema(title="메일 클릭 확인")
    //private int click = 0;
    @Schema(title="예약 발송")
    private String mail_type = "NORMAL";
    @Schema(title="메일 내용(치환전 템플릿)")
    private String body;

    @Schema(title="첨부 파일")
    @JsonIgnore
    private List<COFileDTO> fileList;
}
