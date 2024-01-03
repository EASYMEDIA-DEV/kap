package com.kap.core.dto.sm.smb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  메인 비주얼 DTO
 *
 * @author 장두석
 * @since 2023.09.21
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.09.21  장두석         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class SMBMainVslDTO extends BaseDTO {
    // 순번 (primary key)
    private Integer vslSeq;
    // 디바이스 코드
    private String mdCd;
    // 노출 순서
    private Integer expsOrd;
    // 게시 시작 일
    private String expsStrtDtm;
    // 게시 시작 시
    private String ptupStrtHh;
    // 게시 시작 분
    private String ptupStrtMi;
    // 게시 종료 일
    private String expsEndDtm;
    // 게시 종료 시
    private String ptupEndHh;
    // 게시 종료 분
    private String ptupEndMi;
    // 상시 여부
    private String odtmYn;
    // 제목
    private String titl;
    // 메인 카피
    private String mnCopy;
    // 메인 컬러 코드
    private String mnHexCd;
    // 서브 카피
    private String subCopy;
    // 서브 컬러 코드
    private String subHexCd;
    // 태그 코드
    private String tagCd;
    // 파일 순번
    private Integer fileSeq;
    private Integer imgFileSeq;
    private Integer videoFileSeq;
    // 파일 확장자
    private String fileExtn;
    // 파일 순번
    private Integer mnVslfileSeq;
    // 링크 URL
    private String urlUrl;
    // 새창 여부
    private String wnppYn;
    // 노출 여부
    private String expsYn;
    // 정렬
    private String sortType;
    // 업데이트 유무
    private String updateYn;
    // 조회
    private List<SMBMainVslDTO> list;

    // 검색 조건
    private List<String> mainYnList;
    private List<String> tagCdList;

    // 선택 항목
    private List<String> seqList;

    // 게시 시작 날짜 (검색)
    private String dStrDt;
    // 게시 종료 날짜 (검색)
    private String dEndDt;

    @Schema(title = "파일 경로")
    private String webPath;

    @Schema(title = "파일설명")
    private String fileDsc;


}
