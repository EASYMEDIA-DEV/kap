package com.kap.front.controller.im.ima;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.im.ima.IMAQaDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.IMAQaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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
        COUserDetailsDTO lgnData = COUserDetailsHelperService.getAuthenticatedUser();

        if(lgnData != null) {
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("INQUIRY_TYPE");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnData", pIMAQaDTO);

            return "/front/im/ima/IMAQaWrite.front";
        }
        else {
            return "redirect:/login";
        }
    }



    /**
     * @ClassName		: IMAQaRestController.java
     * @Description		: 1:1 문의를 위한 REST Controller
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
    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/foundation/cs/qa")
    public class IMAQaRestController {

        /** 1:1 문의 서비스 **/
        private final IMAQaService iMAQaService;

        /**
         * 1:1 문의 등록
         */
        @PostMapping(value="/insert")
        public IMAQaDTO qaInsertPage(IMAQaDTO pIMAQaDTO, MultipartHttpServletRequest multiRequest) throws Exception
        {
            try
            {
                COUserDetailsDTO lgnData = COUserDetailsHelperService.getAuthenticatedUser();

                if(lgnData != null) {
                    pIMAQaDTO.setRegId(lgnData.getId());
                    pIMAQaDTO.setRegIp(lgnData.getLoginIp());
                    System.out.println("인서트 파라미터 ==================================================================== ");
                    System.out.println(pIMAQaDTO.toString());

                    Map<String, MultipartFile> files = multiRequest.getFileMap();
                    Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();
                    while(itr.hasNext()) {
//                        System.out.println("파일 데이터 =========================== " + itr.next());
                        Map.Entry<String, MultipartFile> entry = itr.next();
                        String fileName = entry.getKey();
                        MultipartFile file = entry.getValue();

                        System.out.println("파일 이름: " + fileName);
                        System.out.println("파일 데이터: " + file);
                        System.out.println("파일 데이터 toString: " + file.toString());

                        // 파일 데이터를 읽을 수도 있습니다.
                        if (file != null && !file.isEmpty()) {
                            try {
                                byte[] fileData = file.getBytes();
                                System.out.println("파일 데이터: " + fileData.toString());
                                // 여기에서 파일 데이터를 사용하거나 저장할 수 있습니다.
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

//                    pIMAQaDTO.setRespCnt(iMAQaService.insertQa(pIMAQaDTO, multiRequest));
                }
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pIMAQaDTO;
        }

    }



}
