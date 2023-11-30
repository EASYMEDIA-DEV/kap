package com.kap.core.dto.sm.smi;

import com.kap.core.annotation.SaxFilter;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  SMS 발송 관리
 *
 * @author 구은희
 * @since 2023.11.16
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.16  구은희         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Schema(title = "SMS 내용 관리")
public class SMISmsCntnDTO extends BaseDTO {

    @Schema(title = "SMS내용순번", example = "숫자")
    private Integer smsCntnSeq;

    @Schema(title = "SMS내용코드", example = "SMS01, SMS02, SMS03, SMS04, SMS05, SMS06")
    private String smsCntnCd;

    @Schema(title = "SMS내용코드명", example = "")
    private String smsCntnName;

    @Schema(title = "내용", example = "")
    @SaxFilter
    private String cntn;

    @Schema(title = "등록ID", example = "")
    private String regId;

    @Schema(title = "등록IP", example = "127.0.0.1")
    private String regIp;

    @Schema(title = "등록일시", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;

    @Schema(title = "수정ID", example = "")
    private String modId;

    @Schema(title = "수정IP", example = "127.0.0.1")
    private String modIp;

    @Schema(title = "수정일시", example = "yyyy-MM-dd hh:mm:ss")
    private String modDtm;

    @Schema(title = "조회 리스트", example = "")
    private List<SMISmsCntnDTO> list;

    @Schema(title = "검색 등록/수정 기간 시작일자", example = "yyyy-MM-dd")
    private String dStrDt;

    @Schema(title = "검색 등록/수정 기간 종료일자", example = "yyyy-MM-dd")
    private String dEndDt;
}
