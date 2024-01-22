package com.kap.core.dto.sm.smc;

import com.kap.core.annotation.SaxFilter;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  메인 팝업 관리
 *
 * @author 구은희
 * @since 2023.09.21
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.09.21  구은희         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(title = "팝업 관리")
public class SMCMnPopDTO extends BaseDTO {

    @Schema(title = "팝업순번", example = "숫자")
    private Integer popupSeq;

    @Schema(title = "매체 코드 구분", example = "PC, MBL")
    private String mdCd;

    @Schema(title = "노출순서", example = "")
    private Integer expsOrd;

    @Schema(title = "노출 시작 일시", example = "yyyy-MM-dd hh:mm:ss")
    private String expsStrtDtm;

    @Schema(title = "노출 종료 일시", example = "yyyy-MM-dd hh:mm:ss")
    private String expsEndDtm;

    @Schema(title = "상시여부", example = "Y, N")
    private String odtmYn;

    @Schema(title = "제목", example = "")
    private String titl;

    @Schema(title = "태그구분", example = "PC, MBL")
    private String tagCd;

    @Schema(title = "파일순번", example = "숫자")
    private Integer fileSeq;

    @Schema(title = "HTML", example = "")
    @SaxFilter
    private String cntn;

    @Schema(title = "링크URL", example = "")
    private String urlUrl;

    @Schema(title = "검색 내용", example = "")
    private String srchCntn;

    @Schema(title = "새창여부", example = "Y, N")
    private String wnppYn;

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
    private List<SMCMnPopDTO> list;

    @Schema(title = "검색 태그 구분 목록")
    private List<String> tagCdList;

    @Schema(title = "검색 노출여부 구분 목록")
    private List<String> expsYnList;

    @Schema(title = "검색 등록/수정 기간 시작일자", example = "yyyy-MM-dd")
    private String ptupSrchStrDt;

    @Schema(title = "검색 등록/수정 기간 종료일자", example = "yyyy-MM-dd")
    private String ptupSrchEndDt;

    // 게시 시작 날짜
    private String dStrDt;
    // 게시 종료 날짜
    private String dEndDt;

    // 게시 기간 등록
    @Schema(title = "게시 시작 일자", example = "yyyy-MM-dd")
    private String ptupStrtDt;

    @Schema(title = "게시 종료 일자", example = "yyyy-MM-dd")
    private String ptupEndDt;

    @Schema(title = "게시 시작 시간", example = "hh")
    private String ptupStrtHh;

    @Schema(title = "게시 시작 분", example = "mm")
    private String ptupStrtMi;

    @Schema(title = "게시 종료 시간", example = "hh")
    private String ptupEndHh;

    @Schema(title = "게시 종료 분", example = "mm")
    private String ptupEndMi;

    @Schema(title = "삭제 리스트")
    private List<String> delValueList;

    @Schema(title = "노출 정렬 타입", example = "UP, DOWN")
    private String sortType;

    @Schema(title = "파일 경로")
    private String webPath;
    @Schema(title = "파일설명")
    private String fileDsc;

}