package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  메뉴변경로그 객체
 *
 * @author 신혜정
 * @since 2022.04.14
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.04.14  메뉴변경로그 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class COEMenuRoleDTO extends BaseDTO {
    //수정순번
    private Integer modSeq;
    // 이전 변경 순번
    private Integer bfreModSeq;
    //변경자 아이디
    private String admId;
    //변경자 이름
    private String admNm;
    //변경자 부서
    private String admDetp;
    //변경자 부서명
    private String admDeptNm;
    //대상자 아이디
    private String trgtAdmId;
    //대상자 이름
    private String trgtAdmNm;
    //변경자 부서
    private String trgtAdmDetp;
    //대상자 부서명
    private String trgtAdmDeptNm;
    //엑셀다운로드 여부
    private String excelYn;
    //사유
    private String rsn;

    //메뉴명
    private String menuNm;

    //삭제여부
    private String delYn;

    //조회
    private List<COEMenuRoleDTO> list;
    private String type;

    // 상세
    //이전 메뉴 목록
    private List<COEMenuRoleDTO> bfreMenuList;
    //변경 메뉴 목록
    private List<COEMenuRoleDTO> chngMenuList;
}
