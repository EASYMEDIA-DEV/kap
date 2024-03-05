import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vo.FileVo;


public class DeployBuilder {
	
	private static String absolutePath = "";
	
	private static String workspaceNm = "";
	
	private static String buildPath = "";
	
	private static String[] langStr = null;
	private static String[] langUrl = null;
	
	static String str ="";
	static FileVo vo = new FileVo();
	static boolean passStr = true;
	
	//프로젝트명과 경로 입력
	protected void setValues(String workspaceNm, String buildPath, String absolutePath){
		this.absolutePath = absolutePath;
		this.workspaceNm = workspaceNm;
		this.buildPath = buildPath;
	}
	
	//프로젝트의 언어별 프로젝트명, 도메인명을 입력
	protected void setArrayValues(String[] langStr, String[] langUrl){
		this.langStr = langStr;
		this.langUrl = langUrl;
	}
	
	//프로젝트의 개발,운영서버 및 백업폴더 구조 경로 세팅
	protected void setPjFolder(String devPjNmPrev, String devPjNmNext, String realPjNmBackPrev, String realPjNmPrev, String realPjNmNext){
		vo.setDevPjNmPrev(devPjNmPrev);
		vo.setDevPjNmNext(devPjNmNext);
		
		vo.setRealPjNmBackPrev(realPjNmBackPrev);
		vo.setRealPjNmPrev(realPjNmPrev);
		vo.setRealPjNmNext(realPjNmNext);
	}
	
	
	
	//전달받은 정보를 토대로 patch파일을 분석후 파일 리스트를 생성한다.
	protected String patchAnalyze() throws IOException{
		
		ArrayList<String> projectList = new ArrayList<String>();
		
		vo.setOrignPath(buildPath+"\\원본");
		
		String metaPath = vo.getMetapath().replace("^name^", workspaceNm).replace("^ABSOLUTE_PATH^", absolutePath);
		
		File file = new File(buildPath+"\\trunk.patch");
		
		ArrayList<String> list = new ArrayList<String>();
		
		if(file.exists()) {
		    BufferedReader inFile = new BufferedReader(new FileReader(file));
		    String sLine = null;
		    while( (sLine = inFile.readLine()) != null )
		    	//if문에 포함되는 확장자만 체크한다
		    	if((sLine.indexOf("Index:") > -1 || sLine.indexOf("Modified : ") > -1 || sLine.indexOf("Added : ") > -1)
		    			&& ( sLine.indexOf(".class") > -1
		    			|| sLine.indexOf(".java") > -1
		    			|| sLine.indexOf(".xml") > -1
		    			|| sLine.indexOf(".jsp") > -1
		    			|| sLine.indexOf(".html") > -1
		    			|| sLine.indexOf(".js") > -1
		    			|| sLine.indexOf(".pdf") > -1
		    			|| sLine.indexOf(".css") > -1
		    			|| sLine.indexOf(".jpg") > -1
		    			|| sLine.indexOf(".gif") > -1
		    			|| sLine.indexOf(".png") > -1
		    			|| sLine.indexOf(".ico") > -1
		    			|| sLine.indexOf(".svg") > -1
		    			|| sLine.indexOf(".mp4") > -1
		    			|| sLine.indexOf(".docx") > -1)
		    			){
		    		sLine = sLine.replace("Index: ", "").replace("Modified : ", "").replace("Added : ", "").replace("/trunk/", "").replace("src/main/resources", "/WEB-INF/classes").replace("src/main/java", "/WEB-INF/classes").replace("src/main/webapp", "").replace(".java", ".class").replace("/", "\\").replace("\\\\", "\\");
		    		
		    		if(!projectList.contains(sLine.substring(0, sLine.indexOf("\\")))) projectList.add(sLine.substring(0, sLine.indexOf("\\")));	
		    		list.add(sLine);
		    	}
		}
		
		// List를 Set으로 변경 (중복제거작업)
        Set<String> set = new HashSet<String>(list);
        // Set을 List로 변경
        List<String> newList =new ArrayList<String>(set);
        Collections.sort(newList);
		
		//프로젝트의 주소
		ArrayList<String> sourceList = new ArrayList<String>();
		
		for(String arg:new File(metaPath).list()){
			if(arg.indexOf("tmp") > -1 && dircChk(metaPath+"\\"+arg)){//tmp0~들의 정보를 가져와서 폴더인지 체크한다 폴더인것만 선별
				for(String arg2:new File(metaPath+"\\"+arg + "\\wtpwebapps").list()){//폴더일경우 반복문을 돌려서 하위의 정보를 리스트화 한다
					for(String a : projectList){//상단에서 뽑아낸 프로젝트명 리스트와 비교한다
						if(arg2.indexOf(a) > -1 && arg2.replace(a, "").length()<1   ){
							sourceList.add(metaPath+"\\"+arg + "\\wtpwebapps\\"+a+"\\");
						}
					}
				}
			}
		}
		
		String fileName = "";
		String fullPath = "";//파일까지의 풀 경로
		
		/*정렬순서상 MNGR이 USERS계열보다 먼저 위치하는 이슈때문에 임의로 sort를 다시 해줌*/
		ArrayList<String> newList2 = new ArrayList<String>();
		for(String arg:newList){
			arg = arg.replace("MNGWSERC", "USERS_MNGWSERC");
			newList2.add(arg);
		}
		
		ArrayList<String> projectList2 = new ArrayList<String>();
		for(String arg:projectList){
			arg = arg.replace("MNGWSERC", "USERS_MNGWSERC");
			projectList2.add(arg);
		}
		
		Collections.sort(sourceList);
		Collections.sort(projectList2);
		Collections.sort(newList2);
		
		newList = new ArrayList<String>();
		for(String arg:newList2){
			arg = arg.replace("USERS_MNGWSERC", "MNGWSERC");
			newList.add(arg);
		}
		
		projectList = new ArrayList<String>();
		for(String arg:projectList2){
			arg = arg.replace("USERS_MNGWSERC", "MNGWSERC");
			projectList.add(arg);
		}
		/*정렬순서상 MNGR이 USERS계열보다 먼저 위치하는 이슈때문에 임의로 sort를 다시 해줌*/
		
		String searchFile1 = "NOT (";
		String searchFileMain = "";
		String searchFile2 = ")";
		String searchList = "";
		//프로젝트별로 빌드용 폴더의 원본이 되는 구조를 생성한다
		for(String arg:newList){
			int i=0;
			for(String a : projectList){
				if(arg.replace(a, "").indexOf("\\")==0){
					fullPath = sourceList.get(i).substring(0,sourceList.get(i).length()-1) +arg.replace(a, "");
					fileName = fullPath.substring(fullPath.lastIndexOf("\\")+1,fullPath.length());
					
					searchFileMain = searchFileMain + fileName +" OR ";
					
					buildFolder("", "", fullPath.substring(0, fullPath.lastIndexOf("\\")), buildPath+"\\원본\\"+arg.substring(0, arg.length()).replace("\\"+fileName, ""), fileName);		
				}
				i++;
			}
		}
		searchList = searchFile1 + searchFileMain + searchFile2;

		
		return searchList;
		
	}
	
	
	//파일리스트를 토대로 빌드용 폴더구조를 생성한다
	public void folderBuild(String searchList){
	
		passStr = arrayListAdd();
		
		//언어별 프로젝트명(langStr), url명(langUrl)을 기입 한 경우에만 2차 폴더 나누기, url파일 추출을 진행한다
		if(passStr)	{
			isDirectoryChk();	
			
			//생성한 url경로를 기록한다
			if(!str.equals("")){
				try {
					FileWriter fw = new FileWriter(vo.getOrignpath()+"\\buildFolder\\url.txt");
					BufferedWriter writer = new BufferedWriter(fw);

					writer.write(str);
				  	writer.close();
				  	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//생성한 파일리스트로 백업용 리스트를 생성한다
			if(!searchList.equals("")){
				try {
					FileWriter fw = new FileWriter(vo.getOrignpath()+"\\buildFolder\\backUpList.txt");
					BufferedWriter writer = new BufferedWriter(fw);

					writer.write(searchList);
				  	writer.close();
				  	
				  	
				  	removeEmptyFolder();//백업후 빈폴더를 삭제해주는 배치프로그램
				  	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	private void removeEmptyFolder(){
		try{
			FileWriter rmoveBat = new FileWriter(vo.getOrignpath()+"\\buildFolder\\real\\back\\removeEmptyFolder.bat");
			
			BufferedWriter writer = new BufferedWriter(rmoveBat);
			
			String temp = "";
			/*
			@echo off
			for /f "delims=" %%i in ('dir /ad /s /b /o-n ^| sort /r') do (
			    dir /a /b "%%i" | findstr /r /c:".*" >nul || (
			        echo Deleting empty directory: "%%i"
			        rd "%%i"
			    )
			)
			echo Empty directories deleted.
			pause

			*/
			
			
			temp += "@echo off";
			temp +="\r\n";
			temp += "for /f \"delims=\" %%i in ('dir /ad /s /b /o-n ^| sort /r') do (";
			temp +="\r\n";
			temp += "dir /a /b \"%%i\" | findstr /r /c:\".*\" >nul || (";
			temp +="\r\n";
			temp += "echo Deleting empty directory: \"%%i\"";
			temp +="\r\n";
			temp += "rd \"%%i\"";
			temp +="\r\n";
			temp += ")";
			temp +="\r\n";
			temp += ")";
			temp +="\r\n";
			temp += "echo Empty directories deleted.";
			temp +="\r\n";
			temp += "pause";
			
			
			writer.write(temp);
		  	writer.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	} 
	
	
	//전달받은 프로젝트명과 도메인명의 싱크체크후 리스트화
	private static boolean arrayListAdd(){
		
		boolean passStr = true;
		
		ArrayList<String> tempList1 = new ArrayList<String>();
		ArrayList<String> tempList2 = new ArrayList<String>();
		
		if(langStr !=null && langUrl !=null){
			if(langStr.length == langUrl.length){
				for(int i=0; i<langStr.length; i++){
					tempList1.add(langStr[i]);
					tempList2.add(langUrl[i]);
				}
				vo.setLangStr2(tempList1);
				vo.setLangUrl2(tempList2);
			}
		}else{
			passStr = false;
		}
		
		return passStr;
	}
	
	//전달받은 경로가 폴더인지 체크하고 폴더가 아니면 현재경로 +파일명 추출, 폴더면 하위로 이동하여 같은작업 반복
	private static boolean isDirectoryChk(){
		
		if("".equals(vo.getDefaultPath())) vo.setDefaultPath(vo.getOrignpath());
		
		String path= vo.getDefaultPath();
		
		File f = new File(path);
		
		for(String arg:f.list()){
			if("buildFolder".equals(arg))continue;
			vo.setDefaultPath(path+"\\"+arg);
			boolean a=dircChk(path+"\\"+arg) ? isDirectoryChk() : pathText(f, arg);//폴더유무 체크해서 폴더면 하위폴더 탐색, 파일이면 경로까지 해서 출력
		}
		
		return true;
	}
	
	//현재 입력받은 주소가 폴더인지 체크하여 반환한다
	private static boolean dircChk(String path){
		return new File(path).isDirectory();
	}
			
	//현재까지의 url로 어느 언어 사이트인지 판독하여 url 반환
	private static String urlReturn(String getPath){
		
		String textUrl = "";
		
		if(dircChk(vo.getOrignpath()+"\\"+getPath.substring(getPath.length(), getPath.length()))){
			int i=0;
			//전달받은 경로에서 해당 문자열이 있는지 판단후 알맞은 url을 반환한다.
			for(String arg : vo.getLangStr2()){
				if(getPath.indexOf(arg)>0){
					textUrl = vo.getLangUrl2().get(i);
				}
				i++;
			}
		}
		return textUrl;
	}
	
	//전달받은 파일정보에 대한 파일 경로와 url를 출력한다.
	private static boolean pathText(File f, String fileName){
		
		//getPath:현재 탐지한 경로, subPath:출발지부터 지금까지의 경로, textUrl:탐지한 파일의 url값
		String getPath = f.getPath(), subPath = "", textUrl = "";
		
		textUrl = urlReturn(getPath);
		
		subPath = getPath.substring(vo.getOrignpath().length(), getPath.length());
		
		for(String arg:vo.getLangStr2()){
			
			if(subPath.indexOf("\\"+arg+"\\")>-1){
				subPath = subPath.replace("\\"+arg+"\\", "");
			}
		}
		
		//허용된 확장자를 제외하고 출력 방지
		int i=0;
		for(String arg:vo.getExtensionList()){
			//확장자가 jsp인경우는 넘긴다.		
			
			//if(fileName.indexOf(".jsp") > 0) continue; //확장자가 jsp는 js랑 indexOf가 겹쳐서 예외처리 해줌
			
			if(fileName.indexOf("."+arg) > 0){
				//url 경로 기록
				if(textUrl.equals("")){
					str = "";
				}else if(fileName.indexOf(".jsp") < 0){
					if(str.equals("")){
						str = textUrl+"/" +subPath.replace("\\", "/")+"/"+ fileName;	
					}else{
						str = str + "\r\n" + textUrl+"/" +subPath.replace("\\", "/")+"/"+ fileName;
					}
				}
				
				//배포용 폴더구조로 재구성
				copy(textUrl, subPath, fileName);
			}
			i++;
		}
		
		return true;
	}
		
	//해당 url(경로) 에 해당하는 폴더를 배포용으로 재구성한다
	//textUrl : 도메인명, subPath : webapp 이후의 경로, fileName : 파일명
	private static void copy(String textUrl, String subPath, String fileName){
		
		int i=0;
		for(String arg2 : vo.getLangUrl2()){
			
			String orgPath = vo.getOrignpath()+"\\"+vo.getLangStr2().get(i).toLowerCase()+"\\"+subPath;
			String langStr = vo.getLangStr2().get(i).toLowerCase();
			
			String copyBackPath = vo.getOrignpath()+vo.getRealPjNmBackPrev()+langStr+vo.getRealPjNmNext()+subPath;//운영서버 백업경로
			String copyPath = vo.getOrignpath()+vo.getDevPjNmPrev()+langStr+vo.getDevPjNmNext()+subPath;//운영서버 경로
			String copyPathDev = vo.getOrignpath()+vo.getRealPjNmPrev()+langStr+vo.getRealPjNmNext()+subPath;//개발서버 경로
			
			String copyPathList[] = {copyPath, copyPathDev, copyBackPath};//개발 운영 두번 작업
			for(String path:copyPathList){
				buildFolder(arg2, textUrl, orgPath, path, fileName);
			}
			i++;
		}
	}
	
	//전달받은 경로를 바탕으로 폴더구조를 생성한다
	//arg2:언어, textUrl : url명 orgPath : 원본경로, copyPath : 복사할 위치경로, fileName : 파일명
	public static void buildFolder(String arg2, String textUrl, String orgPath, String copyPath, String fileName){
		
		if(arg2.equals(textUrl)){
			
			//폴더 생성
			new File(copyPath).mkdirs();
			
			//복사장소에 동일한 파일이 있다면 삭제후 진행한다.
			if(new File(copyPath+"\\"+fileName).isFile()){
				new File(copyPath+"\\"+fileName).delete();
			}

			FileInputStream input = null;
	        FileOutputStream output = null;
	        try{
	            // 복사할 대상 파일을 지정해준다.
	            File file = new File(orgPath+"\\"+fileName);
	             
	            // FileInputStream 는 File object를 생성자 인수로 받을 수 있다.          
	            input = new FileInputStream(file);
	            // 복사된 파일의 위치를 지정해준다. 
	            output = new FileOutputStream(new File(copyPath+"\\"+fileName));
	            
	            int readBuffer = 0;
	            byte [] buffer = new byte[2048];
	            
	            while((readBuffer = input.read(buffer)) != -1) {
	                output.write(buffer, 0, readBuffer);
	            }
	            //System.out.println("파일이 복사되었습니다.");
	        } catch (IOException e) {
	            System.out.println(e);
	        } finally {
	            try{
	                // 생성된 InputStream Object를 닫아준다.
	                input.close();
	                // 생성된 OutputStream Object를 닫아준다.
	                output.close();
	            } catch(IOException io) {}
	        }
	
		}
	}

}
