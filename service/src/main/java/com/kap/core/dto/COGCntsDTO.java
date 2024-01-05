package com.kap.core.dto;

import lombok.*;

import java.util.List;

/**
 *  관리자 객체
 *
 * @author 임서화
 * @since 2023.09.07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.09.07  관리자 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class COGCntsDTO extends BaseDTO  {
    //글 시퀀스
    private Integer seq;
    //메뉴 시퀀스
    private Integer menuSeq;
    //글 버전
    private Integer ver;
    //콘텐츠
    private String cnts;
    //자바스크립트
    private String jsCnts;
    //상태 코드
    private String prcsCd;

    //조회
    private List<COGCntsDTO> list;
    private List<COMenuDTO> userMenuList;
    private List<COAAdmAuthLogDTO> chngAuthList;

    //드라이브 순번
    private int driveMenuSeq;

    //사용자 페이지 여부
    private String userYn;
}
