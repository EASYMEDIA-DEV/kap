package com.kap.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

/**
 *  메일 객체
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
//GETTER, SETTER
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@MappedSuperclass
@Schema(title="메시지 대상자 DTO")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Hidden
public class COMessageDTO {
    @Schema(title="발송자 이메일, 발신자 전화번호")
    private String sender;
    @Schema(title="발신자 명")
    private String sender_name;
    @Schema(title="directsend id")
    private String username;
    @Schema(title="directsend 발급 api key")
    private String key;
    @Schema(title="메일 제목")
    private String subject;
    @Schema(title="메일 대상자들")
    private List<COMessageReceiverDTO> receiver = new ArrayList<COMessageReceiverDTO>();
    @Schema(title="첨부 파일 URL", description = "첨부파일의 URL을 보내면 DirectSend에서 파일을 download 받아 발송처리를 진행합니다. 첨부파일은 전체 10MB 이하로 발송을 해야 하며, 파일의 구분자는 '|(shift+\\)'로 사용하며 5개까지만 첨부가 가능합니다.")
    private String file_url;
    @Schema(title="첨부 파일 명", description = "첨부파일의 이름은 순차적(https://directsend.co.kr/test.png - image.png, https://directsend.co.kr/test1.png - image2.png) 와 같이 적용이 되며, file_name을 지정하지 않은 경우 마지막의 파일의 이름으로 메일에 보여집니다.")
    private String file_name;
}
