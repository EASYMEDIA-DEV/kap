package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  컨설팅 기술 지도 DTO
 *
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.14  임서화         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class CBATechGuidanceDTO extends BaseDTO {
    // 순번 (primary key)
    private Integer vslSeq;
    // 디바이스 코드
    private String mdCd;
    // 노출 순서
    private Integer expsOrd;
    //노출 시작 일시
    private String expsStrtDtm;
    // 노출 종료 일시
    private String expsEndDtm;
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
    //검색 시작 날짜
    private String dStrDt;
    //검색 종료 날짜
    private String dEndDt;

    // 이미지 파일 확장자
    private String imageExtns;
    // 영상 파일 확장자
    private String videoExtns;

    // 조회
    private List<CBATechGuidanceDTO> list;

    // 검색 조건
    private List<String> mainYnList;
    private List<String> tagCdList;

    // 선택 항목
    private List<String> seqList;




}
