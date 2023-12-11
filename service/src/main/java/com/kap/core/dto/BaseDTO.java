package com.kap.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.utility.PropertiesSiteProfileType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import java.util.List;

@MappedSuperclass
@Getter @Setter
@ToString(exclude = "delValueList")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(title = "최상위 부모 DTO")
public class BaseDTO {
    //순번
    @Schema(title = "공통 순번", description = "", example = "1")
    private String detailsKey;
    //검색어
    private String q;
    //검색필드
    private String f;
    //에러 반환 리스트
    private List<COErrResponseDTO> errors;
    //반환 코드
    private String respCd;
    //반환 메시지
    private String respMsg;
    //등록 id
    private String regId;
    //등록 이름
    private String regName;
    //등록 부서명
    private String regDeptCd;
    //등록 부서명
    private String regDeptNm;
    //등록 ip
    private String regIp;
    //등록 일시
    private String regDtm;
    //수정 id
    private String modId;
    //수정 이름
    private String modName;
    //수정 id
    private String modIp;
    //수정 id
    private String modDtm;
    // 기간 검색 select값
    private String srchDate;
    //검색 시작 일자
    private String strtDt;
    //검색 종료 일자
    private String endDt;
    //삭제할 데이터
    private List<String> delValueList;
    //테이블에서 번호
    private String no;

    //반환 개수
    private int respCnt = 0;

    //페이징 조회
    private int totalCount = 0;
    private int pageIndex = 1;
    private Integer listRowSize = 10;
    private Integer pageRowSize = 10;
    private Integer firstIndex;
    private Integer recordCountPerPage;
    //전달받을 파일 리스트
    private List<COFileDTO> fileList;

    private String siteGubun = PropertiesSiteProfileType.getSiteProfile(); //admin , front
}
