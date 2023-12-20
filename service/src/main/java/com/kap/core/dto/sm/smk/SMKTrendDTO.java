package com.kap.core.dto.sm.smk;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  TREND 관리
 *
 * @author 구은희
 * @since 2023.12.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.20  구은희         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(title = "TREND 관리")
public class SMKTrendDTO extends BaseDTO {

    @Schema(title = "트렌드순번", example = "숫자")
    private Integer trndSeq;

    @Schema(title = "타입코드", example = "")
    private String typeCd;

    @Schema(title = "타입코드명", example = "")
    private String typeCdNm;

    @Schema(title = "노출순서", example = "")
    private Integer expsOrd;

    @Schema(title = "제목", example = "")
    private String titl;

    @Schema(title = "썸네일파일순번", example = "숫자")
    private Integer thnlFileSeq;

    @Schema(title = "링크URL", example = "")
    private String urlUrl;

    @Schema(title = "노출여부", example = "Y, N")
    private String expsYn;

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

    // 검색
    @Schema(title = "조회 리스트", example = "")
    private List<SMKTrendDTO> list;

    @Schema(title = "검색 타입코드 목록")
    private List<String> typeCdList;

    @Schema(title = "검색 노출여부 구분 목록")
    private List<String> expsYnList;

    @Schema(title = "검색 등록/수정 기간 시작일자", example = "yyyy-MM-dd")
    private String dStrDt;

    @Schema(title = "검색 등록/수정 기간 종료일자", example = "yyyy-MM-dd")
    private String dEndDt;

    @Schema(title = "삭제 리스트")
    private List<String> delValueList;

    @Schema(title = "노출 정렬 타입", example = "UP, DOWN")
    private String sortType;

}