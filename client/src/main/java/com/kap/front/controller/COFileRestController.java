package com.kap.front.controller;

import com.kap.common.utility.COWebUtil;
import com.kap.common.utility.seed.COBrowserUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 파일 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		    : COFileRestController.java
 * @Description		: 파일 관리를 위한 Controller
 * @author 박주석
 * @since 2022.03.24
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2022.03.24	  박주석	             최초 생성
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class COFileRestController {

    //파일 업로드 유틸
    private final COFileUtil cOFileUtil;

    //파일 서비스
    private final COFileService cOFileService;

    @GetMapping("/file/view")
    public void cvplFileDownload(COFileDTO coFileDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (COUserDetailsHelperService.isAuthenticated()) {
            COFileDTO fileDTO = cOFileService.getFileInf(coFileDTO);

            String phyPath = fileDTO.getPhyPath();
            String realFileNm = fileDTO.getOrgnFileNm();
            String saveFileNm = fileDTO.getSaveFileNm();

            if (fileDTO == null || "".equals(phyPath) || "".equals(saveFileNm)) {
                throw new FileNotFoundException("파일이 없습니다.");
            }
            File uFile = new File(phyPath);

            long fSize = uFile.length();

            if (fSize > 0) {
                String userAgent = COWebUtil.removeCRLF(request.getHeader("User-Agent"));

                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", COBrowserUtil.getDisposition(realFileNm, userAgent, "UTF-8"));
                response.setContentLengthLong(fSize);

                BufferedInputStream in = null;
                BufferedOutputStream out = null;

                try {
                    in = new BufferedInputStream(new FileInputStream(uFile));
                    out = new BufferedOutputStream(response.getOutputStream());

                    FileCopyUtils.copy(in, out);

                    out.flush();
                }
                catch (IOException ex) {
                    // 다음 Exception 무시 처리
                    // Connection reset by peer: socket write error
                    // EgovBasiclog.ignore("IO Exception", ex);
                    if (ex == null) {
                        log.error("IO Exception");
                    } else {
                        log.error("IO Exception", ex);
                    }

                }
                finally {
                    in.close();
                    out.close();
                    // ResourceCloseHelper.close(in, out);
                }
            } else {
                throw new FileNotFoundException("파일이 없습니다.");
            }
        }
    }


    /**
     * 파일 임시 업로드 후 정보 조회한다.
     */
    @PostMapping(value="/file/upload")
    public List<COFileDTO> getUpdoadFileInfs(COFileDTO coFileDTO, HttpServletRequest request) throws Exception
    {
        List<COFileDTO> rtnList = null;
        try
        {
            //파일처리
            if(request.getContentType() != null && request.getContentType().indexOf("multipart") > -1){
                Map<String, MultipartFile> files = ((MultipartHttpServletRequest)request).getFileMap();
                Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();
                MultipartFile file;
                int atchFileCnt = 0;
                while (itr.hasNext())
                {
                    Map.Entry<String, MultipartFile> entry = itr.next();
                    file = entry.getValue();
                    if (file.getName().indexOf("Filedata") > -1 && file.getSize() > 0)
                    {
                        atchFileCnt++;
                    }
                }
                if (!files.isEmpty())
                {
                    rtnList =cOFileUtil.parseFileInf(files, "", atchFileCnt, "", "file", 0);
                }
            }
        }
        catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return rtnList;
    }
}
