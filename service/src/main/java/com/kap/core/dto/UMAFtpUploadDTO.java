package com.kap.core.dto;

import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  UMAFtpUploadDTO 객체
 *
 * @author 임서화
 * @since 2023.10.17
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.10.17    임서화              최초 생성
 * </pre>
 */
@Getter @Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class UMAFtpUploadDTO extends BaseDTO {

    //샘플 순번
    private Integer seq;
    private String url;
    private String langCd;
    private String orgnFileNm;
    private String webPath;
    private String phyPath;

    //내용
    @NotNull
    @SaxFilter
    private String cnts;

    private String f;
    private String q;


    //파일 순번
    private Integer fileSeq;
    private Integer fileOrd;
    private String atchFileId;

    //검색 리스트

    //테이블 이름
    private String tableNm;
    private String srchDate;

    //조회 리스트
    private List<UMAFtpUploadDTO> list;
}
