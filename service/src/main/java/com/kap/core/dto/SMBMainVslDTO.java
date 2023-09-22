package com.kap.core.dto;

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
    // 언어 코드
    private String langCd;
    // 디바이스 코드
    private String dvcCd;
    // 제목
    private String titl;
    // 게시 시작 일시
    private String postStrtDtm;
    // 게시 종료 일시
    private String postEndDtm;
    // 상시 여부
    private String odtmYn;
    // 메인 카피
    private String mnCopy;
    // 메인 컬러 코드
    private String mnHexCd;
    // 서브 카피
    private String subCopy;
    // 서브 컬러 코드
    private String subHexCd;
    // PC 이미지/영상 파일 순번
    private int pcAtchFileSeq;
    // 모바일 이미지/영상 파일 순번
    private int mblAtchFileSeq;
    // 링크 URL
    private String url;
    // 새창 여부
    private String newWndwYn;
    // 정렬
    private Integer ord;
    // 노출 여부
    private String useYn;
    // 배너 배치 위치 (상:10, 중:20, 하:30)
    private String positionOrd;

    // 이미지 파일 확장자
    private String imageExtns;
    // 영상 파일 확장자
    private String videoExtns;

    // pc 업로드 이미지/영상 파일
//    private List<String> pcAtchAddFile;
//    private List<String> pcAtchAddFileAlt;

    // 모바일 업로드 이미지/영상 파일
//    private List<String> mblAtchAddFile;
//    private List<String> mblAtchAddFileAlt;

    // 현재 업로드 파일 확장자
    private String nowGb;
    // 기존 비주얼 파일 확장자
    private String beforeGb;

    // 조회
    private List<SMBMainVslDTO> list;

    // 검색 조건
    private List<String> expsYnList;

    // 선택 항목
    private List<String> seqList;
}
