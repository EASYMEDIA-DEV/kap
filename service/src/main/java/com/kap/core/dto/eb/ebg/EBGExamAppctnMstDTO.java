package com.kap.core.dto.eb.ebg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.common.utility.CODateUtil;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.COFileDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *  SQ평가원 신청 마스터 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  김학규         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "SQ평가원 신청 마스터 DTO")
public class EBGExamAppctnMstDTO extends BaseDTO {
    @Schema(title = "SQ평가원 신청순번")
    private Integer examAppctnSeq;
    @Schema(title = "회원순번")
    private Integer memSeq;
    @Schema(title = "SQ 평가원 구분 코드")
    private String examCd;
    @Schema(title = "SQ 평가원 구분 코드명")
    private String examCdNm;
    @Schema(title = "발급상태 코드")
    private String issueCd;
    @Schema(title = "반려 사유")
    private String rtrnRsn;
    @Schema(title = "취득일시")
    private String acqsnDtm;
    @Schema(title = "갱신일시")
    private String rfrshDtm;
    @Schema(title = "유효시작일자")
    private String validStrtDt;
    @Schema(title = "유효종료일자")
    private String validEndDt;
    @Schema(title = "증명사진파일순번")
    private Integer idntfnPhotoFileSeq;
    @Schema(title = "자격증번호")
    private String jdgmtNo;
    @Schema(title = "사업자번호")
    private String bsnmNo;
    @Schema(title = "부품사 명")
    private String cmpnNm;
    @Schema(title = "사용여부")
    private String useYn;
    @Schema(title = "등록ID")
    private String regId;
    @Schema(title = "등록IP")
    private String regIp;
    @Schema(title = "등록일시")
    private String regDtm;
    @Schema(title = "최종수정자ID")
    private String modId;
    @Schema(title = "최종수정자IP")
    private String modIp;
    @Schema(title = "최종수정일시")
    private String modDtm;
    @Schema(title = "전달받을 파일 리스트")
    private List<COFileDTO> fileList;

    @Schema(title = "참여순번")
    private Integer ptcptSeq;

    @Schema(title = "회원 명")
    private String name;
    @Schema(title = "회원 생년월일")
    private String birth;
    @Schema(title = "회원 이미지 web url")
    private String idntfnPhotoFileSeqUrl;

    @Schema(title = "회원 이미지 web url")
    private boolean expiration;

    @Schema(title = "만료 여부")
    public boolean getExpiration(){
        if(validStrtDt != null && validEndDt != null){
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String now = CODateUtil.getCurrentDate("yyyy-MM-dd");
                Date endDt = formatter.parse(validEndDt);
                Date nowDt = formatter.parse(now);
                if(nowDt.after(endDt)){
                    return true;
                }
            }
            catch (Exception e){
                return false;
            }
        }
        return false;
    }
}
