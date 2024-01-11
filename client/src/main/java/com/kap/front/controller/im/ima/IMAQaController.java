package com.kap.front.controller.im.ima;

import com.kap.core.dto.im.ima.IMAQaDTO;
import com.kap.service.COCodeService;
import com.kap.service.IMAQaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * <pre>
 * 재단정보 > 고객센터 > 1:1문의 컨트롤러
 * </pre>
 *
 * @ClassName		: IMAQaController.java
 * @Description		: 1:1문의를 위한 Controller
 * @author 장두석
 * @since 2024.01.11
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.11		장두석				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/foundation/cs/qa")
public class IMAQaController {

    /** Service **/
    private final IMAQaService iMAQaService;
    private final COCodeService cOCodeService;

    /**
     * 1:1문의 페이지
     */
    @GetMapping(value = "/index")
    public String getPartUserListPage(IMAQaDTO pIMAQaDTO, ModelMap modelMap) throws Exception {
//        COUserDetailsDTO lgnData = COUserDetailsHelperService.getAuthenticatedUser();

//        if(lgnData.getId() != null && !lgnData.getId().isEmpty()) {
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("INQUIRY_TYPE");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            return "/front/im/ima/IMAQaWrite.front";
//        }
//        else {
//            return "redirect:/";
//        }


        /*MPAUserDto rtnProDto = new MPAUserDto();
        MPAUserDto rtnAdvDto = new MPAUserDto();

        rtnProDto.setMemCd("CS");
        rtnProDto.setWthdrwYn("Y");
        rtnProDto.setListRowSize(9);
        rtnAdvDto.setMemCd("CS");
        rtnAdvDto.setWthdrwYn("Y");
        rtnAdvDto.setListRowSize(9);

        List<String> cmssrWorkList = new ArrayList<>();
        cmssrWorkList.add("MEM_CD04001");
        rtnProDto.setCmssrWorkList(cmssrWorkList);
        rtnAdvDto.setCmssrWorkList(cmssrWorkList);

        List<String> cmssrTypeList = new ArrayList<>();
        cmssrTypeList.add("MEM_CD03001");
        cmssrTypeList.add("MEM_CD03002");
        cmssrTypeList.add("MEM_CD03003");

        List<String> cmssrTypeList1 = new ArrayList<>();
        cmssrTypeList1.add("MEM_CD03001");
        cmssrTypeList1.add("MEM_CD03002");
        rtnProDto.setCmssrTypeList(cmssrTypeList1);
        MPAUserDto rtnProData = mpaUserService.selectUserList(rtnProDto);


        List<String> cmssrTypeList2 = new ArrayList<>();
        cmssrTypeList2.add("MEM_CD03003");
        rtnAdvDto.setCmssrTypeList(cmssrTypeList2);
        MPAUserDto rtnAdvData = mpaUserService.selectUserList(rtnAdvDto);

        modelMap.addAttribute("rtnProData", rtnProData);
        modelMap.addAttribute("rtnAdvData", rtnAdvData);*/

    }

}
