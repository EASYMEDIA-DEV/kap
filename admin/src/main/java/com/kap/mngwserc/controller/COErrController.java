package com.kap.mngwserc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 전역 에러 페이지
 * </pre>
 * @ClassName		: COErrController.java
 * @Description		: 전역 에러 페이지
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2022.01.04	  박주석	             최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class COErrController implements ErrorController
{
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request)
    {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null)
        {
            int statusCode = Integer.valueOf(status.toString());
            //코드(404, 500등등)에 따른 화면 처리
            if(statusCode == HttpStatus.NOT_FOUND.value())
            {
                return "mngwserc/COError";
            }
            else if(statusCode == HttpStatus.UNAUTHORIZED.value())
            {
                return "mngwserc/COUnauthorized";
            }
            else
            {
                return "mngwserc/COError";
            }
        }
        return "mngwserc/COError";
    }
}
