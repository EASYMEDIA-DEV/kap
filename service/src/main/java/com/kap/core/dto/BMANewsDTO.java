package com.kap.core.dto;

import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class BMANewsDTO extends BaseDTO {
    // 팝업 순번
    private Integer newsSeq;

    // 등록일(사용자노출일시)
    private String expsDtm;

    // 계열사 코드
    private String affltCd;

    // 계열사 이름
    private String affltNm;

    // 제목
    private String titl;

    // 내용
    @SaxFilter
    private String cntn;

    // 조회용 내용
    private String srchCntn;

    // 썸네일이미지 파일 순번
    private Integer thnlFileSeq;

    // 노출여부
    private String mainExpsYn;

    // 노출여부
    private String expsYn;

    // 계열사 목록
    private List<COCodeDTO> codeList;

    // 태그 목록
    private List<COCodeDTO> tagList;
    // 연결된 태그
    private List<COCodeDTO> useTagList;

    // 조회
    private List<BMANewsDTO> list;

    // 검색 조건
    private List<String> expsYnList;
    private List<String> mainExpsYnList;
    private List<String> affltCdList;

    // 다국어
    private String lnggCd;

}
