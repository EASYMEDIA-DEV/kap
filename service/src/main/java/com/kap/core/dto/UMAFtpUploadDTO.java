package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    // 이미지 순번
    private Integer pubcSeq;
    private Integer seq;
    // 이미지 다운 url
    private String url;
    // 이미지 원본 이름
    private String orgnFileNm;
    // Web Path
    private String webPath;
    // Phy Path
    private String phyPath;

    // 파일 순번
    private Integer fileSeq;
    // 파일 정렬
    private Integer fileOrd;

    //조회 리스트
    private List<UMAFtpUploadDTO> list;
}
