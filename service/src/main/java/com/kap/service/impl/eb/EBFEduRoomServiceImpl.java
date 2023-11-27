package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomSearchDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomWriteDTO;
import com.kap.service.COFileService;
import com.kap.service.COMailService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBFEduRoomService;
import com.kap.service.dao.eb.EBFEduRoomMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

/**
 * 교육장 serviceImpl
 *
 * @author 장두석
 * @since 2023.11.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.15  장두석         최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EBFEduRoomServiceImpl implements EBFEduRoomService {

    /** Mapper **/
    private final EBFEduRoomMapper eBFEduRoomMapper;

    /** Service **/
    private final COFileService cOFileService;
    private final COMailService cOMailService;

    /** Sequence **/
    /* 관리자 시퀀스 */
    private final EgovIdGnrService edctnPlaceSeqIdgen;


    /**
     * 교육장 목록 조회
     */
    public EBFEduRoomSearchDTO selectEduRoomList(EBFEduRoomSearchDTO pEBFEduRoomSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pEBFEduRoomSearchDTO.getPageIndex());
        page.setRecordCountPerPage(pEBFEduRoomSearchDTO.getListRowSize());

        page.setPageSize(pEBFEduRoomSearchDTO.getPageRowSize());

        pEBFEduRoomSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        pEBFEduRoomSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pEBFEduRoomSearchDTO.setTotalCount(eBFEduRoomMapper.getEduRoomListTotCnt(pEBFEduRoomSearchDTO));
        pEBFEduRoomSearchDTO.setList(eBFEduRoomMapper.selectEduRoomList(pEBFEduRoomSearchDTO));
        return pEBFEduRoomSearchDTO;
    }

    /**
     * 교육장 상세
     */
    public EBFEduRoomDetailDTO selectEduRoomDtl(EBFEduRoomDetailDTO pEBFEduRoomDetailDTO) throws Exception{
        return eBFEduRoomMapper.selectEduRoomDtl(pEBFEduRoomDetailDTO);
    }

    /**
     * 교육장 등록
     */
    public int insertEduRoom(EBFEduRoomWriteDTO pEBFEduRoomWriteDTO) throws Exception {
        //등록자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pEBFEduRoomWriteDTO.setRegId(cOUserDetailsDTO.getId());
        pEBFEduRoomWriteDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        pEBFEduRoomWriteDTO.setPlaceSeq(edctnPlaceSeqIdgen.getNextIntegerId());

        return eBFEduRoomMapper.insertEduRoom(pEBFEduRoomWriteDTO);
    }

    /**
     * 교육장 수정
     */
    public int updateEduRoom(EBFEduRoomWriteDTO pEBFEduRoomWriteDTO) throws Exception {
        if(!pEBFEduRoomWriteDTO.getDetailsKey().isEmpty()) {
            //수정자
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            pEBFEduRoomWriteDTO.setModId(cOUserDetailsDTO.getId());
            pEBFEduRoomWriteDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            return eBFEduRoomMapper.updateEduRoom(pEBFEduRoomWriteDTO);
        }
        else {
            return 0;
        }
    }

    /**
     * 교육장 삭제
     */
    public int deleteEduRoom(EBFEduRoomSearchDTO pEBFEduRoomSearchDTO) throws Exception {
        if(!pEBFEduRoomSearchDTO.getDelValueList().isEmpty()) {
            return eBFEduRoomMapper.deleteEduRoom(pEBFEduRoomSearchDTO);
        }
        else {
            return 0;
        }
    }

}
