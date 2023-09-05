package com.kap.core.utility;

import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.EmfMap;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * <pre>
 * 파일 처리 함수
 * </pre>
 *
 * @ClassName		: COFileUtil.java
 * @Description		: 파일 처리 함수
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2022.01.10	  박주석	             최초 생성
 * </pre>
 */
@Component("cOFileUtil")
@Log4j2
public class COFileUtil {


	//파일 업로드 PATH
	@Value("${app.file.upload-path}")
	private String uploadFilePath;

	// 파일 확장자
	@Value("${app.file.imageExtns}")
	private String imageExtns;
	@Value("${app.file.videoExtns}")
	private String videoExtns;
	@Value("${app.file.fileExtns}")
	private String fileExtns;

	//파일 업로드 사이즈
	@Value("${app.file.max-size}")
	private int atchUploadMaxSize;

	/**
     * 첨부파일에 대한 목록 정보를 취득한다.
     *
     * @param files
     * @return
     * @throws Exception
     */
    public List<COFileDTO> parseFileInf(Map<String, MultipartFile> files, String KeyStr, int fileSeq, String atchFileId, String formName, long maxSize) throws Exception {
		int fileKey = fileSeq;
		String atchFileIdString = "";
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String[] checkFileExtn;

		List<COFileDTO> result = new ArrayList<COFileDTO>();
		Tika tika = new Tika();
		Calendar cal = Calendar.getInstance();
		String folderType = "";

		if(maxSize == 0){
			maxSize = atchUploadMaxSize;
		}
		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();
			file = entry.getValue();

			if (file.getName().indexOf(formName) > -1) {
				String mimeType = tika.detect(file.getInputStream());

				if (mimeType.indexOf("image") > -1) {
					folderType = "/upload/image";
				} else if (mimeType.indexOf("video") > -1) {
					folderType = "/upload/video";
				} else {
					folderType = "/upload/document";
				}

				String filePyhPath = uploadFilePath + folderType;
				filePyhPath += File.separator + String.format("%02d", cal.get(Calendar.YEAR)) + File.separator + String.format("%02d", cal.get(Calendar.MONTH) + 1);

				File saveFolder = new File(COWebUtil.filePathBlackList(filePyhPath));

				if (!saveFolder.exists() || saveFolder.isFile()) {
					if (saveFolder.mkdirs()) {
						log.debug("[file.mkdirs] saveFolder : Creation Success ");
					} else {
						log.error("[file.mkdirs] saveFolder : Creation Fail ");
					}
				}

				//--------------------------------------
				// 원 파일명이 없는 경우 처리
				// (첨부가 되지 않은 input file type)
				//--------------------------------------
				String orginFileName = file.getOriginalFilename();

				long fileSize = file.getSize();

				String status = "00";
				String message = "";

				System.out.println("file.getName()3 : " + file.getName());
				System.out.println("orginFileName4 : " + orginFileName);
				System.out.println("maxSize : " + maxSize);
				System.out.println("fileSize : " + fileSize);

				if ("".equals(orginFileName)) {
					status = "99";
					message = "첨부된 파일이 없습니다.";
				} else if(fileSize > maxSize){
					status = "99";
					message = "첨부된 파일의 크기가 큽니다.";
				} else if(fileSize == 0){
					status = "99";
					message = "비어있는 파일은 등록할 수 없습니다.";
				}
				////------------------------------------

				if("00".equals(status)){
					String fileExtn = orginFileName.substring(orginFileName.lastIndexOf(".") + 1);

					boolean isFileExtn = true;

					if (mimeType.indexOf("image") > -1) {
						checkFileExtn = imageExtns.split(",");
					} else if (mimeType.indexOf("video") > -1) {
						checkFileExtn = videoExtns.split(",");
					} else {
						checkFileExtn = fileExtns.split(",");
					}

					System.out.println("checkFileExtn : " + checkFileExtn);
					System.out.println("fileExtn : " + fileExtn);

					if (checkFileExtn != null) {
						isFileExtn = false;

						for (int q = 0, len = checkFileExtn.length; q < len; q++) {
							if (checkFileExtn[q].trim().toLowerCase().equals(fileExtn.toLowerCase())) {
								isFileExtn = true;
								break;
							}
						}
					}
					System.out.println("isFileExtn : " + isFileExtn);
					if (!isFileExtn) {
						COFileDTO fileDto = new COFileDTO();
						fileDto.setRespCd("99");
						fileDto.setRespMsg("지원하지 않는 파일 확장자입니다.");
						result.add(fileDto);
						continue;
					}else{
						String newFileName = UUID.randomUUID().toString() + "." + fileExtn; //getTimeStamp() + String.format("%02d", fileKey) + RandomStringUtils.randomNumeric(4) + "." + fileExtn;
						String filePath = filePyhPath + File.separator + newFileName;
						String webPath = filePath.replace(uploadFilePath, "");
						webPath = webPath.replace(File.separator, "/");
						file.transferTo(new File(COWebUtil.filePathBlackList(filePath)));
						COFileDTO fileDto = new COFileDTO();
						fileDto.setPhyPath( filePyhPath );
						fileDto.setOrgnFileNm(orginFileName);
						fileDto.setSaveFileNm( newFileName );
						fileDto.setFileExtn( fileExtn );
						fileDto.setFileSize( fileSize );
						fileDto.setWebPath(webPath);
						fileDto.setRespCd(status);
						if (mimeType.indexOf("image") > -1)
						{
							BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
							int width = bufferedImage.getWidth();
							int height = bufferedImage.getHeight();
							fileDto.setWidth(width);
							fileDto.setHeight(height);
						}
						result.add(fileDto);
						fileKey++;
					}
				}else{
					COFileDTO fileDto = new COFileDTO();
					fileDto.setRespCd(status);
					fileDto.setRespMsg(message);
					result.add(fileDto);
					continue;
				}
			}
		}
		return result;
    }

	/**
	 * 임시 첨부파일에 대한 목록 정보를 취득한다.
	 *
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public List<EmfMap> tempFileInf(Map<String, MultipartFile> files, String formName) throws Exception {
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;

		List<EmfMap> result = new ArrayList<EmfMap>();

		Tika tika = new Tika();

		Calendar cal = Calendar.getInstance();

		String filePyhPath = uploadFilePath + "temp" + File.separator + String.valueOf(cal.get(Calendar.YEAR)) + File.separator + String.valueOf(cal.get(Calendar.MONTH) + 1);

		File saveFolder = new File(COWebUtil.filePathBlackList(filePyhPath));

		if (!saveFolder.exists() || saveFolder.isFile()) {
			// 2017.03.03 조성원 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
			if (saveFolder.mkdirs()) {
				log.debug("[file.mkdirs] saveFolder : Creation Success ");
			} else {
				log.error("[file.mkdirs] saveFolder : Creation Fail ");
			}
		}

		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();

			file = entry.getValue();

			if (file.getName().indexOf(formName) > -1) {
				String mimeType = tika.detect(file.getInputStream());

				String[] checkFileExtn = null;

				if (mimeType.indexOf("image") > -1) {
					checkFileExtn = imageExtns.split(",");
				} else if (mimeType.indexOf("video") > -1) {
					checkFileExtn = videoExtns.split(",");
				} else {
					checkFileExtn = fileExtns.split(",");
				}

				String status = "00";
				String message = "";

				// --------------------------------------
				// 원 파일명이 없는 경우 처리
				// (첨부가 되지 않은 input file type)
				// --------------------------------------
				String orginFileName = file.getOriginalFilename(), fileExtn = orginFileName.substring(orginFileName.lastIndexOf(".") + 1);

				if ("".equals(orginFileName)) {
					status = "99";
					message = "첨부된 파일이 없습니다.";
				} else {
					boolean isFileExtn = true;

					if (checkFileExtn != null) {
						isFileExtn = false;

						for (int q = 0, len = checkFileExtn.length; q < len; q++) {
							if (checkFileExtn[q].trim().toLowerCase().equals(fileExtn.toLowerCase())) {
								isFileExtn = true;
								break;
							}
						}
					}

					if (!isFileExtn) {
						status = "99";
						message = "지원하지 않는 파일 확장자입니다.";
					}
				}

				String newFileName = "", webPath = "";

				if ("00".equals(status)) {
					newFileName = orginFileName;

					String tempfilePath = filePyhPath + File.separator + orginFileName;

					File tempFile = new File(COWebUtil.filePathBlackList(tempfilePath));

					if (tempFile.exists()) {
						String duplName = orginFileName.substring(0, orginFileName.lastIndexOf("."));
						String checkName = null;

						boolean _exist = true;

						int i = 0;

						while (_exist) {
							i++;

							checkName = duplName + " (" + i + ")." + fileExtn;
							tempfilePath = filePyhPath + File.separator + checkName;
							_exist = new File(tempfilePath).isFile();

							if (!_exist) {
								newFileName = checkName;
							}
						}
					}

					String filePath = tempfilePath;

					webPath = File.separator + filePath.replace(uploadFilePath, "");
					webPath = webPath.replace(File.separator, "/");

					file.transferTo(new File(COWebUtil.filePathBlackList(filePath)));
				}

				EmfMap fileMap = new EmfMap();

				fileMap.put("orgnFileNm", orginFileName);
				fileMap.put("saveFileNm", newFileName);
				fileMap.put("fileExtn", fileExtn);
				fileMap.put("fileSize", file.getSize());
				fileMap.put("webPath", webPath);
				fileMap.put("state", status);
				fileMap.put("message", message);

				result.add(fileMap);
			}
		}
		return result;
	}

	public List<COFileDTO> copyFileInfs(COFileDTO cOFileDTO, Integer fileSeq, int fileKeyParam, List<String> filePaths, List<String> fileAlts) throws Exception {
		int fileKey = fileKeyParam;

		int atchFileSeq = 0;

		if ("".equals(fileSeq) || fileSeq == null) {
			// atchFileSeq = idgenService.getFileNextSeq();
		} else {
			atchFileSeq = fileSeq;
		}

		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel fcin = null;
		FileChannel fcout = null;

		List<COFileDTO> result = new ArrayList<COFileDTO>();

		Tika tika = new Tika();

		Calendar cal = Calendar.getInstance();

		try {
			for (int i = 0, size = filePaths.size(); i < size; i++) {
				String tempWebPath = COStringUtil.getHtmlStrCnvr(filePaths.get(i));

				if (!"".equals(tempWebPath)) {
					File tempFile = new File(uploadFilePath + tempWebPath);

					int fSize = (int) tempFile.length();

					if (fSize > 0) {
						String mimeType = tika.detect(new FileInputStream(tempFile));

						String imageFileAlt = "";
						String filePyhPath = uploadFilePath + "/upload";

						if (mimeType.indexOf("image") > -1) {
							filePyhPath += "/image";
							if(fileAlts.size() > 0) {
								imageFileAlt = fileAlts.get(i);
							}
						} else if (mimeType.indexOf("video") > -1) {
							filePyhPath += "/video";
						} else {
							filePyhPath += "/upload";
						}

						filePyhPath += File.separator + String.valueOf(cal.get(Calendar.YEAR)) + File.separator + String.valueOf(cal.get(Calendar.MONTH) + 1);

						File saveFolder = new File(COWebUtil.filePathBlackList(filePyhPath));

						if (!saveFolder.exists() || saveFolder.isFile()) {
							// 2017.03.03 조성원 시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
							if (saveFolder.mkdirs()) {
								log.debug("[file.mkdirs] saveFolder : Creation Success ");
							} else {
								log.debug("[file.mkdirs] saveFolder : Creation Fail ");
							}
						}

						String orginFileName = tempFile.getName().replaceAll("\\([\\d]+\\)", "");;
						String fileExtn = orginFileName.substring(orginFileName.lastIndexOf(".") + 1);
						String newFileName = getTimeStamp() + String.format("%02d", fileKey) + RandomStringUtils.randomNumeric(4) + "." + fileExtn;
						String filePath = filePyhPath + File.separator + newFileName;
						String webPath = filePath.replace(uploadFilePath, "");
						webPath = webPath.replace(File.separator, "/");

						fis = new FileInputStream(tempFile);
						fos = new FileOutputStream(filePath);

						fcin =  fis.getChannel();
						fcout = fos.getChannel();

						long fileSize = fcin.size();

						fcin.transferTo(0, fileSize, fcout);

						COFileDTO fileDTO = new COFileDTO();

						fileDTO.setFileSeq(atchFileSeq);
						fileDTO.setFileOrd(fileKey);
						fileDTO.setPhyPath(filePyhPath);
						fileDTO.setOrgnFileNm(orginFileName);
						fileDTO.setSaveFileNm(newFileName);
						fileDTO.setFileExtn(fileExtn);
						fileDTO.setFileSize(fileSize);
						fileDTO.setFileDsc(imageFileAlt);
						fileDTO.setWebPath(webPath);
						fileDTO.setRegId(cOFileDTO.getRegId());
						fileDTO.setRegIp(cOFileDTO.getRegIp());

						result.add(fileDTO);

						fileKey++;
					}
				}
			}
		}
		finally {
			fis.close();
			fos.close();
			fcin.close();
			fcout.close();
		}
		return result;
	}

	/**
	 * 임시 폴더에 첨부파일을 UDID기준으로 작한다. 동일한 파일명을 업로드 할 수 있다.
	 *
	 * @return
	 * @throws Exception
	 */
	/*public List<COFileDTO> tempCosFileInf(COFileDTO cFileDTO, HttpServletRequest request) throws Exception
	{
		List<COFileDTO> result = new ArrayList<COFileDTO>();
		if (request.getContentType() != null && request.getContentType().indexOf("multipart/form-data") > -1)
		{
			Tika tika = new Tika();
			Calendar cal = Calendar.getInstance();
			String fileTempPyhPath = uploadFilePath + File.separator  + "tempdir";
			String fileRealPyhPath = uploadFilePath + File.separator + "tempdir" + File.separator + "files" + File.separator + String.format("%02d", cal.get(Calendar.YEAR)) + File.separator + String.format("%02d", cal.get(Calendar.MONTH) + 1);
			File saveFolder = new File(COWebUtil.filePathBlackList(fileRealPyhPath));
			if (!saveFolder.exists() || saveFolder.isFile())
			{
				if (saveFolder.mkdirs())
				{
					log.debug("[file.mkdirs] saveFolder : Creation Success ");
				}
				else
				{
					log.error("[file.mkdirs] saveFolder : Creation Fail ");
				}
			}
			File saveTempFolder = new File(COWebUtil.filePathBlackList(fileTempPyhPath));
			if (!saveTempFolder.exists() || saveTempFolder.isFile())
			{
				if (saveFolder.mkdirs())
				{
					log.debug("[file.mkdirs] saveFolder : Creation Success ");
				}
				else
				{
					log.error("[file.mkdirs] saveFolder : Creation Fail ");
				}
			}

			MultipartRequest multi = new MultipartRequest(request, fileTempPyhPath, atchUploadMaxSize, "UTF-8");
			Enumeration files = multi.getFileNames();
			int read = 0;
			byte[] buf = null;
			FileInputStream fin = null;
			FileOutputStream fout = null;
			while (files.hasMoreElements())
			{
				String fileName = (String)files.nextElement();
				File fileObj = multi.getFile(fileName);
				if(fileObj.isFile())
				{
					// 파일명 변경 (아파치에서 한글파일명 깨지는 경우 방지)
					String orgFileNm = fileObj.getName();
					String reFileNm = UUID.randomUUID().toString() + orgFileNm.substring(orgFileNm.lastIndexOf("."));

					File reFile = new File(fileTempPyhPath + File.separator + reFileNm);

					if(!fileObj.renameTo(reFile))
					{
						//rename이 되지 않을경우 강제로 파일을 복사하고 기존파일은 삭제
						buf = new byte[1024];
						fin = new FileInputStream(fileObj);
						fout = new FileOutputStream(reFile);
						read = 0;
						while((read=fin.read(buf,0,buf.length))!=-1){
							fout.write(buf, 0, read);
						}
						fin.close();
						fout.close();
						reFile.delete();
					}

					String mimeType = tika.detect(reFile);
					String[] checkFileExtn = null;
					if (mimeType.indexOf("image") > -1)
					{
						checkFileExtn = imageExtns.split(",");
					}
					else if (mimeType.indexOf("video") > -1)
					{
						checkFileExtn = videoExtns.split(",");
					}
					else
					{
						checkFileExtn = fileExtns.split(",");
					}
					String status = "00";
					String message = "";

					// 파일 사이즈가 0byte일 경우 처리
					if (reFile.length() == 0 ) {
						status = "99";
						message = "비어있는 파일은 등록할 수 없습니다.";
					}

					// 원 파일명이 없는 경우 처리
					// (첨부가 되지 않은 input file type)
					String orginFileName = multi.getOriginalFileName(fileName), fileExtn = orginFileName.substring(orginFileName.lastIndexOf(".") + 1);
					if ("".equals(orginFileName))
					{
						status = "99";
						message = "첨부된 파일이 없습니다.";
					}
					else
					{
						boolean isFileExtn = true;
						if (checkFileExtn != null)
						{
							isFileExtn = false;

							for (int q = 0, len = checkFileExtn.length; q < len; q++) {
								if (checkFileExtn[q].trim().toLowerCase().equals(fileExtn.toLowerCase())) {
									isFileExtn = true;
									break;
								}
							}
						}
						if (!isFileExtn) {
							status = "99";
							message = "지원하지 않는 파일 확장자입니다.";
						}
					}

					try
					{
						String webPath = "", fileNameRealPyhPath;

						COFileDTO fileDto = new COFileDTO();
						fileDto.setRespCd( String.valueOf(status) );
						fileDto.setRespMsg( message );

						if ("00".equals(status))
						{
							String filePath = fileRealPyhPath + File.separator + reFileNm;
							webPath = filePath.replace(uploadFilePath, "");
							webPath = webPath.replace(File.separator, "/");

							// 파일명 rename 및  이동
							File newFile = new File(filePath);
							FileUtils.moveFile(reFile, newFile);

							// 실제 저장될 파일 객체 생성
							fileDto.setOrgnFileNm( orginFileName );
							fileDto.setSaveFileNm( reFileNm );
							fileDto.setPhyPath( newFile.getAbsolutePath()  );
							fileDto.setFileExtn( fileExtn );
							fileDto.setFileSize( newFile.length() );
							fileDto.setWebPath(  webPath );
						}
						else{
							// 에러 메시지 생성
							fileDto.setRespCd( String.valueOf(status) );
							fileDto.setRespMsg( message );

							// 잘못된 파일이기때문에 임시폴더에서 삭제
							reFile.delete();
						}

						result.add(fileDto);
					}
					catch(IOException ie)
					{
						throw ie;
					}
				}
			}
		}
		return result;
	}*/

	/**
	 * 임시 폴더 파일을 실제 폴더로 복사하고 임시 폴더 파일 삭제
	 *
	 * @return
	 * @throws Exception
	 */
	public COFileDTO cosFileCopy(COFileDTO cFileDTO) throws Exception
	{
		Tika tika = new Tika();
		Calendar cal = Calendar.getInstance();
		String fileTempPyhPath = uploadFilePath + File.separator  + cFileDTO.getWebPath().replace("/", File.separator);
		String fileRealPyhPath = uploadFilePath + File.separator + "upload" + File.separator + "files" + File.separator + String.format("%02d", cal.get(Calendar.YEAR)) + File.separator + String.format("%02d", cal.get(Calendar.MONTH) + 1);
		File saveFolder = new File(COWebUtil.filePathBlackList(fileRealPyhPath));
		if (!saveFolder.exists() || saveFolder.isFile())
		{
			if (saveFolder.mkdirs())
			{
				log.debug("[file.mkdirs] saveFolder : Creation Success ");
			}
			else
			{
				log.error("[file.mkdirs] saveFolder : Creation Fail ");
			}
		}
		File fileObj = new File(fileTempPyhPath);
		if(fileObj.isFile())
		{
			int read = 0;
			byte[] buf = null;
			FileInputStream fin = null;
			FileOutputStream fout = null;

			String mimeType = tika.detect(fileObj);
			String[] checkFileExtn = null;
			if (mimeType.indexOf("image") > -1)
			{
				checkFileExtn = imageExtns.split(",");
			}
			else if (mimeType.indexOf("video") > -1)
			{
				checkFileExtn = videoExtns.split(",");
			}
			else
			{
				checkFileExtn = fileExtns.split(",");
			}
			String status = "00";
			String message = "";
			// --------------------------------------
			// 원 파일명이 없는 경우 처리
			// (첨부가 되지 않은 input file type)
			// --------------------------------------
			String orginFileName = cFileDTO.getOrgnFileNm(), fileExtn = orginFileName.substring(orginFileName.lastIndexOf(".") + 1);
			if ("".equals(orginFileName))
			{
				status = "99";
				message = "첨부된 파일이 없습니다.";
			}
			else
			{
				boolean isFileExtn = true;
				if (checkFileExtn != null)
				{
					isFileExtn = false;

					for (int q = 0, len = checkFileExtn.length; q < len; q++) {
						if (checkFileExtn[q].trim().toLowerCase().equals(fileExtn.toLowerCase())) {
							isFileExtn = true;
							break;
						}
					}
				}
				if (!isFileExtn) {
					status = "99";
					message = "지원하지 않는 파일 확장자입니다.";
				}
			}

			try
			{
				String newFileName = "", webPath = "", fileNameRealPyhPath;
				if ("00".equals(status))
				{
					newFileName = UUID.randomUUID().toString() + "." + fileExtn;;
					String filePath = fileRealPyhPath + File.separator + newFileName;
					webPath = filePath.replace(uploadFilePath, "");
					webPath = webPath.replace(File.separator, "/");
					// 파일명 rename
					// 실제 저장될 파일 객체 생성
					File newFile = new File(filePath);
					if(!fileObj.renameTo(newFile))
					{
						//rename이 되지 않을경우 강제로 파일을 복사하고 기존파일은 삭제
						buf = new byte[1024];
						fin = new FileInputStream(fileObj);
						fout = new FileOutputStream(newFile);
						read = 0;
						while((read=fin.read(buf,0,buf.length))!=-1){
							fout.write(buf, 0, read);
						}
						fin.close();
						fout.close();
						fileObj.delete();
					}
					cFileDTO.setOrgnFileNm( orginFileName );
					cFileDTO.setSaveFileNm( newFileName );
					cFileDTO.setPhyPath( newFile.getAbsolutePath()  );
					cFileDTO.setFileExtn( fileExtn );
					cFileDTO.setFileSize( newFile.length() );
					cFileDTO.setWebPath(  webPath );
					cFileDTO.setRespCd( status);
					cFileDTO.setRespMsg( message );
				}
			}
			catch(IOException ie)
			{
				throw ie;
			}
		}
		return cFileDTO;
	}

	public COFileDTO moveFileInfs(COFileDTO cOFileDTO) throws Exception {
		Tika tika = new Tika();
		Calendar cal = Calendar.getInstance();

		try {
			String tempWebPath = COStringUtil.getHtmlStrCnvr(cOFileDTO.getWebPath());

			if (!"".equals(tempWebPath)) {
				String fileTempPath = uploadFilePath + tempWebPath;
				File tempFile = new File(fileTempPath);

				String imageFileAlt = "";

				String mimeType = tika.detect(tempFile);
				String filePyhPath = uploadFilePath + File.separator + "upload";

				if (mimeType.indexOf("image") > -1) {
					filePyhPath += "/image";
					imageFileAlt = cOFileDTO.getFileDsc();
				} else if (mimeType.indexOf("video") > -1) {
					filePyhPath += "/video";
				} else {
					filePyhPath += "/upload";
				}
				filePyhPath += File.separator + String.valueOf(cal.get(Calendar.YEAR)) + File.separator + String.valueOf(cal.get(Calendar.MONTH) + 1);
				File saveFolder = new File(COWebUtil.filePathBlackList(filePyhPath));

				if (!saveFolder.exists() || saveFolder.isFile()) {
					if (saveFolder.mkdirs()) {
						log.debug("[file.mkdirs] saveFolder : Creation Success ");
					} else {
						log.debug("[file.mkdirs] saveFolder : Creation Fail ");
					}
				}

				String orginFileName = tempFile.getName().replaceAll("\\([\\d]+\\)", "");;
				String fileExtn = orginFileName.substring(orginFileName.lastIndexOf(".") + 1);
				String newFileName = getTimeStamp() + String.format("%02d", cOFileDTO.getFileOrd()) + RandomStringUtils.randomNumeric(4) + "." + fileExtn;
				String filePath = filePyhPath + File.separator + newFileName;
				String webPath = filePath.replace(uploadFilePath, "");
				webPath = webPath.replace(File.separator, "/");

				// temp폴더의 파일 업로드 폴더로 이동
				File uploadFile = new File(COWebUtil.filePathBlackList(filePath));
				boolean fileMoveResult = tempFile.renameTo(uploadFile);

				if(!fileMoveResult) {
					this.copy(tempFile, uploadFile);
				}

				cOFileDTO.setPhyPath(filePyhPath);
				cOFileDTO.setOrgnFileNm(orginFileName);
				cOFileDTO.setSaveFileNm(newFileName);
				cOFileDTO.setFileExtn(fileExtn);
				cOFileDTO.setFileSize(uploadFile.length());
				cOFileDTO.setFileDsc(imageFileAlt);
				cOFileDTO.setWebPath(webPath);
				cOFileDTO.setRegId(cOFileDTO.getRegId());
				cOFileDTO.setRegIp(cOFileDTO.getRegIp());
			}
		}
		catch(IOException ie) {
			throw ie;
		}
		return cOFileDTO;
	}

	/**
	 * 파일 복사
	 *
	 * @return
	 * @throws Exception
	 */
	public void copy(File sourceF, File targetF){
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			int cnt = 0;
			byte[] b = new byte[4096];
			fis = new FileInputStream(sourceF);
			fos = new FileOutputStream(targetF) ;

			while((cnt = fis.read(b)) != -1){
				fos.write(b, 0, cnt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 공통 컴포넌트 utl.fcc 패키지와 Dependency제거를 위해 내부 메서드로 추가 정의함
	 * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
	 *
	 * @param
	 * @return Timestamp 값
	 * @see
	 */
    public static String getTimeStamp() {
    	// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
		String pattern = "yyMMddhhmmss";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);

		Timestamp ts = new Timestamp(System.currentTimeMillis());

		return sdfCurrent.format(ts.getTime());
    }

	/**
	 * 파일명 보안 적용
	 * @return
	 * @throws Exception
	 */
	public static String getXssRemoveFileName(String str) throws Exception
	{
		String match = "[^\\-^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, "");
		str = COWebUtil.filePathBlackList(str);
		str = COWebUtil.removeCRLF(str);
		str = COWebUtil.setFileHipen(str);
		return str;
	}

	/**
	 * 동일한 파일명의 파일이 존재하는지 확인하여 존재한다면 파일명 뒤에 "-숫자" 를
	 * 붙이고 "-숫자"가 존재한다면 "-숫자" +1 을 더한값을 재귀적으로 카운트
	 * */
	public static String appendSuffixName(String orgFileName, int seq) {
		String retFileName = "";
		// 파일이 존재하는지 확인한다.
		if (new File(orgFileName).exists())
		{
			int plusSeq = 1;
			String seqStr = "-" + seq;
			String firstFileName = orgFileName.substring(0,orgFileName.lastIndexOf("."));
			String extName = orgFileName.substring(orgFileName.lastIndexOf("."));

			// 만약 파일명에 _숫자가 들어간경우라면..
			if (orgFileName.lastIndexOf("-") != -1 && !firstFileName.endsWith("-"))
			{
				String numStr = orgFileName.substring(orgFileName.lastIndexOf("-") + 1,orgFileName.lastIndexOf(extName));
				try
				{
					plusSeq = Integer.parseInt(numStr);
					plusSeq = plusSeq + 1;
					retFileName = firstFileName.substring(0,firstFileName.lastIndexOf("-"))+ "-" + plusSeq  + extName;
				}
				catch (NumberFormatException e)
				{
					retFileName = firstFileName + seqStr + extName;
					return appendSuffixName(retFileName, ++plusSeq);
				}
			}
			else
			{
				retFileName = firstFileName + seqStr + extName;
			}
			return appendSuffixName(retFileName, ++plusSeq);
		}
		else
		{
			return orgFileName;
		}
	}
}