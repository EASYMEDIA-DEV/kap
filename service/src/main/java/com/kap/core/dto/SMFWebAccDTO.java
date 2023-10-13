package com.kap.core.dto;

import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  웹접근성 관리
 *
 * @author 구은희
 * @since 2023.10.05
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.10.05  구은희         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class SMFWebAccDTO extends BaseDTO {
    // 순번
    private Integer seq;
    // 다국어
    private String langCd;
    // 제목
    private String titl;
    // 내용
    @SaxFilter
    private String cnts;
    // 노출여부
    private String useYn;
    // 등록자
    private String regId;
    // 등록ip
    private String regIP;

    // 등록일자
    private String regDtm;
    // 수정자Id
    private String modId;
    // 수정ip
    private String modIP;
    // 수정일자
    private String modDtm;

    // 조회
    private List<SMFWebAccDTO> list;
    // 검색 조건
    private List<String> expsYnList;
    // 노출여부 구분(=사용여부 구분)
    private List<String> useYnList;
    // 검색 등록 시작일
    private String strtDtm;
    // 검색 등록 종료일
    private String endDtm;

    //삭제할 데이터
    private List<String> delValueList;

    //테이블 이름
    private String tableNm;
    // 엑셀 여부
    private String excelYn;
}
