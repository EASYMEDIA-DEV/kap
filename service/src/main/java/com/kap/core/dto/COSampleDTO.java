package com.kap.core.dto;

import com.kap.core.annotation.CMEmail;
import com.kap.core.annotation.CMId;
import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  샘플 객체
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
@Getter @Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class COSampleDTO extends BaseDTO {

    //샘플 순번
    private Integer smplSeq;

    //아이디
    @CMId
    private String id;

    //이름
    private String name;

    //이메일
    @CMEmail
    private String email;

    //휴대전화번호
    private String telNo;

    //내용
    @NotNull
    @SaxFilter
    private String cntn;

    //제목
    @NotNull
    private String titl;

    //게시 시작 일시
    private String postStrtDtm;
    //게시 종료 일시
    private String postEndDtm;
    //시작 일자
    private String strtDt;
    //종료 일자
    private String endDt;

    //파일 순번
    private Integer fileSeq;

    //상시여부
    private String odtmYn;

    //사용여부
    private String useYn;

    //검색 리스트
    private List<String> authCdList;
    private List<String> useYnList;

    //이관
    private String tbNm;

    //조회 리스트
    private List<COSampleDTO> list;
}
