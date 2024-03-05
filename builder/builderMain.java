import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class builderMain {

	public static void main(String[] args) throws Exception{

		// 실행할 Git 명령어
		String gitCommand = "git diff 51ff5bc3 da08e872 --name-only";

		// 외부 프로세스 실행
		Process process = Runtime.getRuntime().exec(gitCommand);

		// 프로세스의 출력을 읽어오기 위한 BufferedReader
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;

		// 출력 내용 읽기
		while ((line = reader.readLine()) != null) {
			System.out.println("@@  = " + line);
		}

		// 프로세스 종료 대기
		process.waitFor();



		
		DeployBuilder db = new DeployBuilder();
		
		//여기부터 유저기입란 시작
		String absolutePath = "D:\\EGOVFRAME\\project";
		String workspaceNm = "KAP"; // 예시 : absolutePath와 workspaceNm가 합쳐져서 D:\\EGOVFRAME\\project\\LOTTEDFS\\까지의 경로가 만들어져야함(하위에 .metadata 폴더가 존재해야함)
		
		String buildPath = "D:\\문서들\\작업내역\\2024-02-26\\KAP\\builderTest";
		
		//본점
		//String[] langStr = {"client", "admin", "USERS_CT", "USERS_EN", "USERS_JP", "USERS_VN"};

		//String[] langUrl = {"https://www.kapkorea.org", "https://mng.kapkorea.org"};

		//배포할 풀 경로를 기입한다 prev와 next 사이에는 프로젝트의 폴더명이 들어가므로 반영할 개발서버와 운영서버의 풀경로를 알아낸 뒤에  알맞게 수정
		//buildFolder\\real(혹은dev)까지는 이 프로그램에서 사용하는 폴더구조고 그 하위부터 운영에 배포될 실제 경로
		String realPjNmBackPrev = "\\buildFolder\\real\\back\\"; // 예시 \\buildFolder\\real\\back\\users\\webapps\\WEB-INF
		String realPjNmPrev = "\\buildFolder\\real\\webapp\\"; // 예시 \\buildFolder\\real\\ldfs_web\\users\\webapps\\WEB-INF
		String realPjNmNext = "\\webapps\\";
		
		String devPjNmPrev = "\\buildFolder\\dev\\webapp\\";
		String devPjNmNext = "\\webapps\\ROOT\\";

		
		db.setValues(workspaceNm, buildPath, absolutePath);//폴더 세팅에 필요한 기본값 입력
		db.setArrayValues(langStr, langUrl);//폴더 세팅에 필요한 기본값(배열구조) 입력
		db.setPjFolder(devPjNmPrev, devPjNmNext, realPjNmBackPrev, realPjNmPrev, realPjNmNext);//폴더 세팅에 필요한 경로값 입력
		
		//trunk.patch의 정보를 분석해서 리스트를 반환한다
		try {
			String searchList = db.patchAnalyze();
			
			db.folderBuild(searchList);
			
			
            // 리포지토리 URL
            String repositoryUrl = "https://github.com/EASYMEDIA-DEV/kap.git";
            
            // 실행할 Git 명령어
            String gitCommand = "git status";

            // 외부 프로세스 실행
            Process process = Runtime.getRuntime().exec(gitCommand, null, new File("/"));

            // 프로세스의 출력을 읽어오기 위한 BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            // 출력 내용 읽기
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 프로세스 종료 대기
            process.waitFor();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
