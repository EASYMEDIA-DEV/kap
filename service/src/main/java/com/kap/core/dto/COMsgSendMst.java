package com.kap.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Hidden
@Builder
@Schema(title="메시지 대상자 마스터 수집 DTO")
public class COMsgSendMst {
    @Schema(title="메시지 발송 순번")
    private Integer msgSendSeq;
    @Schema(title="상태코드")
    private String sttsCd;
    @Schema(title="상태메시지")
    private String sttsMsg;
    @Schema(title="제목")
    private String titl;
    @Schema(title="내용")
    private String cntn;
    @Schema(title="등록일시 ")
    private String regDtm;
}
