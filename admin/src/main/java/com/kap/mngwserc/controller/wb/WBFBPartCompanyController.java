package com.kap.mngwserc.controller.wb;

import com.kap.core.dto.wb.wbf.WBFASmartRoundDTO;
import com.kap.service.COCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * <pre>
 * 스마트공장구축 회차 관리 Controller
 * </pre>
 *
 * @ClassName		: WBFASmartRoundController.java
 * @Description		: 스마트공장구축 업체 관리 Controller
 * @author 김동현
 * @since 2023.11.02
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김동현				   최초 생성
 * </pre>
 */
@Tag(name = "스마트공장구축-신청업체관리페이지", description = "신청업체관리 생성,수정,삭제")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbfb")
public class WBFBPartCompanyController {

}
