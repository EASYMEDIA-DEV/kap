package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  파일 객체
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
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class COFileDTO extends BaseDTO {

    // 파일 순번
    private Integer fileSeq;
    // 파일 정렬
    private Integer fileOrd;
    // 물리 경로
    private String phyPath;
    // 원본 파일명
    private String orgnFileNm;
    // 저장 파일명
    private String saveFileNm;
    // 파일 확장자
    private String fileExtn;
    // 파일 설명
    private String fileDsc;
    // 파일 크기
    private long fileSize;
    // 웹 경로
    private String webPath;
    // 사용여부
    private String useYn;
    // 파일 상태값
    private String status;
    // 파일 필드명
    private String fieldNm;
    // width
    private int width;
    // height
    private int height;

    // 파일 ID에 해당하는 fileSeq
    private Integer fileTypeIdSeq;
    // 파일 순번 배열
    private int[] fileOrdArr;
    // 파일 수정 시 fileSeq, fileDec 넣는 List
    private List<COFileDTO> fileInfList;
}
