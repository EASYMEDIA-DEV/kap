package com.kap.core.dto.im.ima;

import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.COFileDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  1:1 문의 DTO
 *
 * @author 장두석
 * @since 2023.11.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.01    장두석              최초 생성
 * </pre>
 */
@Getter @Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class IMAQaDTO extends BaseDTO {

    @Schema(title = "QA순번", example = "")
    private Integer qaSeq;

    @Schema(title = "회원(작성자) 순번", example = "")
    private Integer memSeq;

    @Schema(title = "QA답변 순번", example = "")
    private int qaRplySeq;

    @Schema(title = "진행코드", example = "")
    private String rsumeCd;

    @Schema(title = "이메일", example = "")
    private String email;

    @Schema(title = "휴대전화 번호", example = "")
    private String hpNo;

    @Schema(title = "제목", example = "")
    private String titl;

    @Schema(title = "내용", example = "")
    private String cntn;

    @Schema(title = "QA 파일 순번", example = "")
    private Integer fileSeq;

    @Schema(title = "부모 카테고리 코드", example = "")
    private String parntCtgryCd;

    @Schema(title = "부모 카테고리명", example = "")
    private String parntCtgryNm;

    @Schema(title = "카테고리 코드", example = "")
    private String ctgryCd;

    @Schema(title = "카테고리명", example = "")
    private String ctgryNm;

    @Schema(title = "깊이", example = "")
    private int dpth;

    @Schema(title = "QA답변 내용", example = "")
    @NotNull
    private String rplyCntn;

    @Schema(title = "QA답변 파일 순번", example = "")
    private Integer rplyFileSeq;

    @Schema(title = "QA답변 파일 리스트", example = "")
    private List<COFileDTO> rplyFileList;

    //검색 리스트
    private String inqFir;
    private String inqSec;
    private List<String> rsumeCdList;
    private String srchDate;

    @Schema(title = "테이블 이름", example = "")
    private String tableNm;

    @Schema(title = "조회 리스트", example = "")
    private List<IMAQaDTO> list;

    @Schema(title = "마이페이지 여부", example = "")
    private String mypageYn;

    @Schema(title = "마이페이지 메인 여부", example = "")
    private String mypageMainYn;

    @Schema(title = "나의 1:1 문의 검색어", example = "")
    private String searchText;

    @Schema(title = "대시보드 검색타입", example = "A/B")
    private String dashBoardType;
}
