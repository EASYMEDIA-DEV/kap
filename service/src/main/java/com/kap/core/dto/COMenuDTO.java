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
public class COMenuDTO extends BaseDTO {
    //메뉴순번
    private Integer menuSeq;
    //부모순번
    private int parntSeq;
    //정렬
    private int pstn;
    //왼쪽값
    private int lftVal;
    //오른쪽값
    private int rhtVal;
    //뎊스
    private int dpth;
    //메뉴구분
    private String menuType;
    //메뉴명
    private String menuNm;
    //서브메뉴명
    private String subMenuNm;
    //관리자URL
    private String admUrl;
    //사용자URL
    private String userUrl;
    //GNB여부
    private String gnbYn;
    //새창여부
    private String wnppYn;
    //SEO키워드
    private String seoKwrd;
    //SEO내용
    private String seoCntn;
    //사용여부
    private String useYn;
    //자식갯수
    private int childcnt;
    //체크타입
    private String checktype;
    //참조 순번
    private int refSeq;
    //복사 여부
    private int isCopy;
    //체크여부
    private String isChkd;
    //관리자 메뉴
    private Integer admSeq;
    //다국어 체크
    private String lnggCd;
    //사용자 메뉴 조회 여부
    private String isMenu;
    private List<COMenuDTO> userMenuList;
    //관리자 수정 여부 (관리자 등록 및 수정에서 로그관리 메뉴는 숨김처리를 위함)
    private String isAdmUdt;

    //메뉴 이동 참조
    private int nodeParntSeq;
    private int nodePstn;
    private int ndif;
    private int nodeRhtVal;
    private int nodeLftVal;
    private String[] nodeIds;
    private int self;
    private int refInd;
    private int idif;
    private int ldif;
}
