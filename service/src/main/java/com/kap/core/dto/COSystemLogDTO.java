package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  시스템 로그 객체
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
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class COSystemLogDTO extends BaseDTO {

    //대상 메뉴명
    private String trgtMenuNm;
    //서비스명
    private String srvcNm;
    //함수명
    private String fncNm;
    //처리코드
    private String prcsCd;
    //처리코드명
    private String prcsCdNm;

    //부서코드
    private String deptCd;
    //부서코드명
    private String deptCdNm;
    //부서코드명
    private String deptNm;
    //에러코드
    private String errCd;
    //에러내용
    private String errCntn;
    //로그 타입 (acc/sys)
    private String logType;
    //엑셀다운여부
    private String excelYn;
    //사유
    private String rsn;
    //쿼리 내용
    private String quryCntn;

    //조회
    private List<COSystemLogDTO> list;

    //검색영역
    // 검색 (처리구분)
    private List<String> prcsCdList;
    //관리자 구분
    private List<String> typeCdList;
    //부서
    private List<String> deptCdList;
}
