package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  상생 사업 관리 DTO
 *
 * @author 임서화
 * @since 2023.10.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *  2023.10.25    임서화             최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class SMGWinBusinessDTO extends BaseDTO {

    // 순번 (primary key)
    private Integer cxstn_seq;
    // 노출 순서
    private Integer exps_ord;
    // 사업명
    private String bsn_nm;
    // 설명
    private String dsc;
    // 링크 url
    private String urlUrl;
    // 파일 순번
    private Integer fileSeq;
    // 정렬
    private Integer ord;
    // 노출 여부
    private String expsYn;
    // 노출 순서
    private Integer expsOrd;
    // 노출정렬 UP/DOWN
    private String sortType;

    // 조회
    private List<SMGWinBusinessDTO> list;

    // 선택 항목
    private List<String> seqList;
}
