package com.kap.core.dto.eb.ebb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  교육차수 교육참여자 상세 객체
 *
 * @author 김학규
 * @since 2023.11.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.30  교육참여자 상세 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBPtcptDTO extends BaseDTO {

    @Schema(title = "교육순번", example = "숫자")
    @NotNull
    private Integer edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    @NotNull
    private Integer episdOrd;

    @Schema(title = "회차년도", example = "yyyy")
    @NotNull
    private Integer episdYear;

    @Schema(title = "참여순번", example = "숫자")
    @NotNull
    private Integer ptcptSeq;

    @Schema(title = "아이디", example = "텍스트")
    private String id;

    @Schema(title = "이름", example = "텍스트")
    private String name;

    @Schema(title = "회사 회차", example = "텍스트")
    private String ctgryNm;

    @Schema(title = "부품사명", example = "텍스트")
    private String cmpnNm;

    @Schema(title = "사업자등록번호", example = "텍스트")
    private String ptcptBsnmNo;

    @Schema(title = "휴대폰번호", example = "텍스트")
    private String hpNo;

    @Schema(title = "이메일", example = "텍스트")
    private String email;

    @Schema(title = "교육상태코드", example = "텍스트")
    private String sttsCd;

    @Schema(title = "교육상태코드명", example = "텍스트")
    private String sttsCdNm;

    @Schema(title = "교육신청일시", example = "날짜 yyyy-mm-dd HH:mm:ss")
    private String eduDtm;

    @Schema(title = "교육 출석률", example = "xx")
    private String eduAtndc;

    @Schema(title = "시험점수", example = "")
    private Integer examScore;

    @Schema(title = "수료여부", example = "Y/N")
    private String cmptnYn;

    @Schema(title = "오프라인 평가 여부", example = "Y/N")
    private String otsdExamPtcptYn;

    @Schema(title = "수료여부 변경여부", example = "Y/N")
    private String cmptnChangeYn;

    @Schema(title = "교육상태", example = "텍스트")
    private String eduStat;


    private List<EBBPtcptDTO> ptcptList;//교육 참여자 목록


}
