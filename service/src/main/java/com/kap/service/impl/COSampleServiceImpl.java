package com.kap.service.impl;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COSampleDTO;
import com.kap.core.dto.EmfMap;
import com.kap.service.COFileService;
import com.kap.service.COSampleService;
import com.kap.service.dao.COFileMapper;
import com.kap.service.dao.COSampleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * COUserDetailsService Helper 클래스
 *
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.01.10  박주석         최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COSampleServiceImpl implements COSampleService {

    //Mapper
    private final COSampleMapper sampleMapper;
    //파일 서비스
    private final COFileService cOFileService;
    // DAO
    private final COFileMapper cOFileMapper;
    //파일 업로드 위치
    @Value("${app.file.upload-path}")
    private String fileUploadPath;


    /**
     * 샘플 조회
     */
    public COSampleDTO selectSmplList(COSampleDTO cOSampleDTO) throws Exception{
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(cOSampleDTO.getPageIndex());
        page.setRecordCountPerPage(cOSampleDTO.getListRowSize());

        page.setPageSize(cOSampleDTO.getPageRowSize());

        cOSampleDTO.setFirstIndex( page.getFirstRecordIndex() );
        cOSampleDTO.setRecordCountPerPage( page.getRecordCountPerPage() );
        cOSampleDTO.setTotalCount( sampleMapper.getSmplListTotCnt( cOSampleDTO ));
        cOSampleDTO.setList( sampleMapper.selectSmplList( cOSampleDTO ) );
        return cOSampleDTO;
    }

    /**
     * 샘플 등록
     */
    public COSampleDTO insertSmpl(COSampleDTO cOSampleDTO) throws Exception{
        //파일 처리
        int actCnt = 0;
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(cOSampleDTO.getFileList());
        //파일 처리
        cOSampleDTO.setFileSeq( fileSeqMap.get("fileSeq") );
        //성공 갯수
        actCnt = sampleMapper.insertSmpl( cOSampleDTO );
        cOSampleDTO.setRespCnt( actCnt );
        return cOSampleDTO;
    }

    /**
     * 샘플 상세
     */
    public COSampleDTO selectSmplDtl(COSampleDTO cOSampleDTO) throws Exception{
        return sampleMapper.selectSmplDtl( cOSampleDTO );
    }

    /**
     * 샘플 수정
     */
    public COSampleDTO updateSmpl(COSampleDTO cOSampleDTO) throws Exception{
        //파일 처리
        int actCnt = 0;
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(cOSampleDTO.getFileList());
        //파일 처리
        cOSampleDTO.setFileSeq( fileSeqMap.get("fileSeq") );
        //성공 갯수
        actCnt = sampleMapper.updateSmpl( cOSampleDTO );
        cOSampleDTO.setRespCnt( actCnt );
        return cOSampleDTO;
    }
    /**
     * 샘플 삭제
     */
    public COSampleDTO deleteSmpl(COSampleDTO cOSampleDTO) throws Exception{
        int actCnt = 0;
        //성공 갯수
        actCnt = sampleMapper.deleteSmpl( cOSampleDTO );
        cOSampleDTO.setRespCnt( actCnt );
        return cOSampleDTO;
    }

    /**
     * 데이터 삭제
     */
    public COSampleDTO setRemoveTbNm(COSampleDTO cOSampleDTO) throws Exception{
        if("ir".equals(cOSampleDTO.getTbNm()) || "news".equals(cOSampleDTO.getTbNm()) || "engNews".equals(cOSampleDTO.getTbNm()) || "people".equals(cOSampleDTO.getTbNm())){
            sampleMapper.deleteIr(cOSampleDTO);
        }
        return cOSampleDTO;
    }

    /**
     * 데이터 이관
     */
    public COSampleDTO setTbNm(COSampleDTO cOSampleDTO) throws Exception{
        List<EmfMap> rtnList = null;
        List<EmfMap> rtnFileList = null;
        if("ir".equals(cOSampleDTO.getTbNm()) || "news".equals(cOSampleDTO.getTbNm()) || "engNews".equals(cOSampleDTO.getTbNm()) || "people".equals(cOSampleDTO.getTbNm())){
            sampleMapper.deleteIr(cOSampleDTO);
            rtnList = sampleMapper.getIrList(cOSampleDTO);
        }
        if(rtnList != null && rtnList.size() > 0){
            for(int q = 0 ; q < rtnList.size() ; q++){
                EmfMap rtnMap = rtnList.get(q);
                if(!"".equals(rtnMap.getString("fileName")) && "ir".equals(cOSampleDTO.getTbNm()))
                {
                    COFileDTO mstFileDto = new COFileDTO();
                    mstFileDto.setRegId("SYSTEM");
                    mstFileDto.setModId("SYSTEM");
                    cOFileMapper.insertTmpFileMaster(mstFileDto);
                    mstFileDto.setFileOrd(0);
                    mstFileDto.setPhyPath(rtnMap.getString("filePath").replace("/data/file/kolon_com/user_kor/uploadfiles/", fileUploadPath  + "/upload/"  ) );
                    mstFileDto.setOrgnFileNm(rtnMap.getString("fileName"));
                    mstFileDto.setSaveFileNm(rtnMap.getString("fileName"));
                    mstFileDto.setFileExtn(rtnMap.getString("fileName").substring(rtnMap.getString("fileName").lastIndexOf(".") + 1));
                    mstFileDto.setFileDsc(rtnMap.getString("fileName"));
                    mstFileDto.setFileSize(1000);
                    mstFileDto.setWebPath(mstFileDto.getPhyPath().replace(fileUploadPath, ""));
                    mstFileDto.setUseYn("Y");
                    cOFileMapper.insertTmpFileDetail(mstFileDto);
                    rtnMap.put("fileSeq", mstFileDto.getFileSeq());
                }
                else if(!"".equals(rtnMap.getString("imgName")) && "news".equals(cOSampleDTO.getTbNm()))
                {
                    COFileDTO mstFileDto = new COFileDTO();
                    mstFileDto.setRegId("SYSTEM");
                    mstFileDto.setModId("SYSTEM");
                    cOFileMapper.insertTmpFileMaster(mstFileDto);
                    mstFileDto.setFileOrd(0);
                    mstFileDto.setPhyPath(rtnMap.getString("imgPath").replace("/data/file/kolon_com/user_kor/uploadfiles/", fileUploadPath  + "/upload/"  )  );
                    mstFileDto.setOrgnFileNm(rtnMap.getString("imgName"));
                    mstFileDto.setSaveFileNm(rtnMap.getString("imgName"));
                    mstFileDto.setFileExtn(rtnMap.getString("imgName").substring(rtnMap.getString("imgName").lastIndexOf(".") + 1));
                    mstFileDto.setFileDsc(rtnMap.getString("imgAlter"));
                    mstFileDto.setFileSize(1000);
                    mstFileDto.setWebPath(mstFileDto.getPhyPath().replace(fileUploadPath, ""));
                    mstFileDto.setUseYn("Y");
                    cOFileMapper.insertTmpFileDetail(mstFileDto);
                    rtnMap.put("fileSeq", mstFileDto.getFileSeq());
                }
                else if(!"".equals(rtnMap.getString("imgName")) && "people".equals(cOSampleDTO.getTbNm()))
                {
                    COFileDTO mstFileDto = new COFileDTO();
                    mstFileDto.setRegId("SYSTEM");
                    mstFileDto.setModId("SYSTEM");
                    cOFileMapper.insertTmpFileMaster(mstFileDto);
                    mstFileDto.setFileOrd(0);
                    mstFileDto.setPhyPath(rtnMap.getString("imgPath").replace("/data/file/kolon_com/user_kor/uploadfiles/", fileUploadPath  + "/upload/"  ) );
                    mstFileDto.setOrgnFileNm(rtnMap.getString("imgName"));
                    mstFileDto.setSaveFileNm(rtnMap.getString("imgName"));
                    mstFileDto.setFileExtn(rtnMap.getString("imgName").substring(rtnMap.getString("imgName").lastIndexOf(".") + 1));
                    mstFileDto.setFileDsc(rtnMap.getString("imgAlter"));
                    mstFileDto.setFileSize(1000);
                    mstFileDto.setWebPath(mstFileDto.getPhyPath().replace(fileUploadPath, ""));
                    mstFileDto.setUseYn("Y");
                    cOFileMapper.insertTmpFileDetail(mstFileDto);
                    rtnMap.put("fileSeq", mstFileDto.getFileSeq());
                    rtnMap.put("movUrl", rtnMap.getString("movUrl").replace("https://www.youtube.com/embed/", ""));
                }
                else if(!"".equals(rtnMap.getString("imgName")) && "engNews".equals(cOSampleDTO.getTbNm()))
                {
                    COFileDTO mstFileDto = new COFileDTO();
                    mstFileDto.setRegId("SYSTEM");
                    mstFileDto.setModId("SYSTEM");
                    cOFileMapper.insertTmpFileMaster(mstFileDto);
                    mstFileDto.setFileOrd(0);
                    if("grade_en".equals(rtnMap.getString("grade")))
                    {
                        mstFileDto.setPhyPath(rtnMap.getString("imgPath").replace("/data/file/kolon_com/user_kor/en/uploadfiles/", fileUploadPath  + "/upload/en/"  ) );
                        mstFileDto.setPhyPath(rtnMap.getString("imgPath").replace("/data/file/kolon_com/user_other/en/uploadfiles/", fileUploadPath  + "/upload/en/"  ) );
                    }
                    else if("grade_ch".equals(rtnMap.getString("grade")))
                    {
                        mstFileDto.setPhyPath(rtnMap.getString("imgPath").replace("/data/file/kolon_com/user_kor/ch/uploadfiles/", fileUploadPath  + "/upload/ch/"  ) );
                        mstFileDto.setPhyPath(rtnMap.getString("imgPath").replace("/data/file/kolon_com/user_other/ch/uploadfiles/", fileUploadPath  + "/upload/ch/"  ) );
                    }
                    else
                    {

                    }
                    mstFileDto.setOrgnFileNm(rtnMap.getString("imgName"));
                    mstFileDto.setSaveFileNm(rtnMap.getString("imgName"));
                    mstFileDto.setFileExtn(rtnMap.getString("imgName").substring(rtnMap.getString("imgName").lastIndexOf(".") + 1));
                    mstFileDto.setFileDsc(rtnMap.getString("imgAlter"));
                    mstFileDto.setFileSize(1000);
                    mstFileDto.setWebPath(mstFileDto.getPhyPath().replace(fileUploadPath, ""));
                    mstFileDto.setUseYn("Y");
                    cOFileMapper.insertTmpFileDetail(mstFileDto);
                    rtnMap.put("fileSeq", mstFileDto.getFileSeq());
                }
                rtnMap.put("regId", "SYSTEM");
                rtnMap.put("modId", "SYSTEM");
                rtnMap.put("regDtm", rtnMap.getString("regDate"));
                if("people".equals(cOSampleDTO.getTbNm())) {
                    rtnMap.put("inUse", rtnMap.getString("type"));
                }
                if(!"".equals(rtnMap.getString("contents"))){
                    rtnMap.put("srchCntn", COStringUtil.getHtmlStrCnvr(COStringUtil.removeHtmlTag(rtnMap.getString("contents"))));
                }
                rtnMap.put("tbNm", cOSampleDTO.getTbNm());
                sampleMapper.insertIr(rtnMap);
                if("news".equals(cOSampleDTO.getTbNm())){
                    if("NEWS_CD_01".equals(rtnMap.getString("cateCd"))){
                        rtnMap.put("tagCd", "A");
                    }
                    else if("NEWS_CD_02".equals(rtnMap.getString("cateCd"))){
                        rtnMap.put("tagCd", "C");
                    }
                    else if("NEWS_CD_03".equals(rtnMap.getString("cateCd"))){
                        rtnMap.put("tagCd", "D");
                    }
                    else if("NEWS_CD_04".equals(rtnMap.getString("cateCd"))){
                        rtnMap.put("tagCd", "B");
                    }
                    else if("NEWS_CD_05".equals(rtnMap.getString("cateCd"))){
                        rtnMap.put("tagCd", "ETC");
                    }
                    sampleMapper.insertTag(rtnMap);
                }
                else if("engNews".equals(cOSampleDTO.getTbNm())){
                    rtnMap.put("tagCd", "A");
                    sampleMapper.insertTag(rtnMap);
                }
            }
        }
        return cOSampleDTO;
    }
}
