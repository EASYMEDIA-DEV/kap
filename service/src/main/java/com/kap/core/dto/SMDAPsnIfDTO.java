package com.kap.core.dto;

import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  개인정보처리방침 관리
 *
 * @author 구은희
 * @since 2023.09.26
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.09.26  구은희         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class SMDAPsnIfDTO extends BaseDTO {

    // 개인정보순번
    private Integer psnifSeq;

    // 제목
    private String titl;

    // 내용
    @SaxFilter
    private String cntn;

    // 노출여부
    private String expsYn;

    // 등록ID
    private String regId;

    // 등록IP
    private String regIP;

    // 등록일시
    private String regDtm;

    // 수정ID
    private String modId;

    // 수정IP
    private String modIP;

    // 수정일시
    private String modDtm;

    // 검색 등록/수정 기간 시작일
    private String dStrDt;

    // 검색 등록/수정 기간 종료일
    private String dEndDt;

    // 조회
    private List<SMDAPsnIfDTO> list;

    // 검색 조건
    // 노출여부 구분(=사용여부 구분)
    private List<String> expsYnList;

    //삭제할 데이터
    private List<String> delValueList;

    //테이블 이름
    private String tableNm;

}
