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
 * @author 임서화
 * @since 2023.09.18
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.09.18    임서화              최초 생성
 * </pre>
 */
@Getter @Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class COGBoardDTO extends BaseDTO {

    //샘플 순번
    private Integer seq;
    private String langCd;
    private Integer typeCd;

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
    private String cnts;

    private String jsCnts;

    private String f;
    private String q;
    //제목
    @NotNull
    private String titl;

    //게시 시작 일시
    private String postStrtDtm;
    //게시 종료 일시
    private String postEndDtm;

    //파일 순번
    private Integer fileSeq;
    //파일 순번 - pc썸네일
    private Integer pcThumbFileSeq;
    //파일 순번 - mobile썸네일
    private Integer moThumbFileSeq;

    //상시여부
    private String odtmYn;

    //년도?
    private String year;

    //조회수
    private String readCnt;

    //중요공지 여부
    private String topYn;

    // FAQ 타입
    private String faqType;

    // FAQ 타입 이름
    private String faqTypeNm;

    //private String atchFileId;
    //private String thumbFileId;

    //사용여부
    private String mainYn;

    //검색 리스트
    private List<String> authCdList;
    private List<String> mainYnList;
    private List<String> faqTypeList;

    //테이블 이름
    private String tableNm;
    private String srchDate;

    //조회 리스트
    private List<COGBoardDTO> list;
    //전달받을 PC 썸네일 파일 리스트
    private List<COFileDTO> pcThumbList;
    //전달받을 MO 썸네일 파일 리스트
    private List<COFileDTO> moThumbList;

}
