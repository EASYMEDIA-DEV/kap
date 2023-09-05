package com.kap.mngwserc.controller;

import com.kap.core.dto.COFileDTO;
import com.kap.core.utility.COFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 에디터의 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: COEditorController.java
 * @Description		: 에디터의 관리를 위한 Controller
 * @author 허진영
 * @since 2019.07.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2019.07.08	  허진영	             최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class COEditorController {

	//파일 업로드 유틸
    private final COFileUtil cOFileUtil;
	//파일 업로드 확장자
	@Value("${app.file.fileExtns}")
	private String imgUploadFileExtns;

	//파일 업로드 경로
	@Value("${app.file.upload-path}")
	private String imgUploadFilePath;
	@Value("${app.file.imageExtns}")
	private String imgType;
	@Value("${app.file.videoExtns}")
	private String videoType;

	/**
	 * 에디터 이미지를 업로드 한다.
	 *
	 */
	@RequestMapping(value="/mngwserc/editor-image/upload")
	@ResponseBody
	public String setEditorImageUpload(final MultipartHttpServletRequest multiRequest, ModelMap modelMap) throws Exception
	{
		try
		{
			int actCnt = 0;

			List<COFileDTO> result = null;

			Map<String, MultipartFile> files = multiRequest.getFileMap();

			if (!files.isEmpty())
			{
				result = cOFileUtil.parseFileInf(files, "EDITOR_", 0, "EDITOR", "upload", 10485760);

				if (result.size() > 0)
				{
					actCnt = result.size();

					modelMap.addAttribute("url", result.get(0).getWebPath());
					modelMap.addAttribute("fileName", result.get(0).getOrgnFileNm());
				}
			}

			if (actCnt == 0)
			{
				JSONObject errObj = new JSONObject();

				errObj.put("message", "파일 업로드에 실패하였습니다. \n관리자에게 문의해주세요.");

				modelMap.addAttribute("error", errObj);
			}

			modelMap.addAttribute("uploaded", actCnt);
		}
		catch(Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}

		return "jsonView";
	}

}