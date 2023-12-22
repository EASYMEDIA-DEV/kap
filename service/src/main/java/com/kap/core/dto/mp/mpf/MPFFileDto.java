package com.kap.core.dto.mp.mpf;

import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.COFileDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  이미지 조회
 *
 * @author 양현우
 * @since 2023.12.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.19  탈퇴         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPFFileDto extends BaseDTO {

    @Schema(title = "파일순번", example = "1")
    private String fileSeq;

    @Schema(title = "파일정렬", example = "1")
    private String fileOrd;

    @Schema(title = "실제이름", example = "1")
    private String orgnFileNm;

    @Schema(title="컨설팅 순번")
    private String cnstgSeq;

    @Schema(title="파일")
    private List<COFileDTO> kickFile;

    @Schema(title="파일")
    private List<COFileDTO> lvlFile;

    @Schema(title="킥오프파일순번")
    private Integer kickfFileSeq;

    @Schema(title="킥오프파일순번")
    private Integer lvlupFileSeq;

    private List<MPFFileDto> list;
}