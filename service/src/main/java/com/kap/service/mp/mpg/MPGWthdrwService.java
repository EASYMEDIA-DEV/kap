package com.kap.service.mp.mpg;

import com.kap.core.dto.mp.mpg.MPGWthdrwDto;

import javax.servlet.http.HttpServletResponse;

/* <pre>
 * 탈퇴회원 관리를 위한 Service
     * </pre>
    *
    * @ClassName		: MPDCmtService.java
    * @Description		: 탈퇴회원 관리를 위한 Service
    * @author 양현우
    * @since 2023.11.28
    * @version 1.0
    * @see
 * @Modification Information
        * <pre>
 * 		since			author				  description
         *    ==========    ==============    =============================
         *    2023.11.28		양현우				   최초 생성
         * </pre>
        */
public interface MPGWthdrwService {

    MPGWthdrwDto selectWthdrwList(MPGWthdrwDto mpgWthdrwDto);


    /**
     * 엑셀 생성
     * @param mpgWthdrwDto
     * @param response
     * @throws Exception
     */
    void excelDownload(MPGWthdrwDto mpgWthdrwDto, HttpServletResponse response) throws Exception;

}