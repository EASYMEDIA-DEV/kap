package com.kap.core.dto.mp.mpc;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  강사 관리
 *
 * @author 구은희
 * @since 2023.11.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.20  구은희         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class MPCLecturerDTO extends BaseDTO {

    @Schema(title = "강사순번", example = "숫자")
    private Integer isttrSeq;

    @Schema(title = "이름", example = "")
    private String name;

    @Schema(title = "소속명", example = "")
    private String ffltnNm;

    @Schema(title = "부서명", example = "")
    private String deptNm;

    @Schema(title = "직급명", example = "")
    private String pstnNm;

    @Schema(title = "전화번호", example = "")
    private String telNo;

    @Schema(title = "휴대전화번호", example = "")
    private String hpNo;

    @Schema(title = "이메일", example = "")
    private String email;

    @Schema(title = "약력", example = "")
    private String spclCntn;

    @Schema(title = "등록ID", example = "")
    private String regId;

    @Schema(title = "등록IP", example = "127.0.0.1")
    private String regIp;

    @Schema(title = "등록일시", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;

    @Schema(title = "수정ID", example = "")
    private String modId;

    @Schema(title = "수정IP", example = "127.0.0.1")
    private String modIp;

    @Schema(title = "수정일시", example = "yyyy-MM-dd hh:mm:ss")
    private String modDtm;

    @Schema(title = "조회 리스트", example = "")
    private List<MPCLecturerDTO> list;


    // 상생 사업 현황 조회
    @Schema(title = "사업연도", example = "숫자")
    private Integer year;

    @Schema(title = "회차", example = "숫자")
    private Integer episd;

    // 확인 필요!!
    @Schema(title = "사업구분명", example = "")
    private String typeCdNm;

    @Schema(title = "사업명", example = "")
    private String bsnNm;

    @Schema(title = "사업시작일시", example = "yyyy-MM-dd hh:mm:ss")
    private String bsnStrtDtm;

    @Schema(title = "사업종료일시", example = "yyyy-MM-dd hh:mm:ss")
    private String bsnEndDtm;

    @Schema(title = "사업자번호", example = "")
    private String sbrdnBsnmNo;

    @Schema(title = "사업신청일", example = "yyyy-MM-dd hh:mm:ss")
    private String bsnRegDtm;

    @Schema(title = "부품사명", example = "")
    private String cmpnNm;

    @Schema(title = "구분", example = "")
    private String ctgryNm;

    @Schema(title = "규모", example = "")
    private String sizeNm;

    @Schema(title = "진행상태명", example = "")
    private String rsumeSttsNm;


    // 검색조건
    @Schema(title = "검색 등록/수정 기간 시작일자", example = "yyyy-MM-dd")
    private String dStrDt;

    @Schema(title = "검색 등록/수정 기간 종료일자", example = "yyyy-MM-dd")
    private String dEndDt;

    @Schema(title = "검색 레이어에서 호출 여부", example = "Y")
    private String srchLayer;
}
