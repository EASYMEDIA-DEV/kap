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
    private Integer seq;
    // 제목
    private String titl;
    private String cntn;
    // url
    private String linkUrl;
    // 링크 URL
    private String urlLink;
    // 정렬
    private Integer ord;
    // 노출 여부
    private String mainYn;

    private String admId;
    private String admIp;
    private String regNm;
    private String regId;
    private String regIp;
    private String modNm;
    private String modId;


    // 노출정렬 UP/DOWN
    private String sortType;

    // 조회
    private List<SMGWinBusinessDTO> list;

    // 선택 항목
    private List<String> seqList;
}
