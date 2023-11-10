package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  코드 객체
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
public class COCodeDTO extends BaseDTO {
    //코드순번
    private Integer cdSeq;
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
    //코드ID
    private String cdId;
    //코드
    private String cd;
    //코드명
    private String cdNm;
    //사용여부
    private String useYn;
    //자식여부
    private int childcnt;
    //참조 순번
    private int refSeq;
    //복사 여부
    private int isCopy;
    //상태
    private String type;

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


    private List<COCodeDTO> list;
}
