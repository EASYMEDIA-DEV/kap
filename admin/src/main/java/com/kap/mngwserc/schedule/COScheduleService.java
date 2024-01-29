package com.kap.mngwserc.schedule;

import com.kap.common.utility.CODateUtil;
import com.kap.core.dto.COMailDTO;
import com.kap.core.dto.COMessageReceiverDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiListDTO;
import com.kap.service.COMessageService;
import com.kap.service.EBDSqCertiReqService;
import com.kap.service.dao.COScheduleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * 스케쥴 관리를 위한 서비스
 * </pre>
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2023.11.30	  이옥정	             최초 생성
 * </pre>
 */
@Slf4j
@Service
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
@RequiredArgsConstructor
public class COScheduleService {
    /** 1분 동안 LOCK **/
    private static final String ONE_MIN = "PT1M";
    /** SQ 평가원 서비스 **/
    private final EBDSqCertiReqService eBDSqCertiReqService;
    /** 메일 서비스 **/
    private final COMessageService cOMessageService;

    /** 스케줄러 Mapper **/
    private final COScheduleMapper cOScheduleMapper;

    /**
     * SQ평가원 자격증 취득 후 2년 6개월 경과 시 이메일 발송(만료 일시 6개월 전)
     */
    @Scheduled(cron = "0 0 10 * * ?") // 초(0~59) 분(0~59) 시(0~23) 일(1~31) 월(1~12) 요일(1~7, 일요일 : 1) 연도(생략가능)
    @SchedulerLock(name = "SQ_EXAM_CRTFN", lockAtLeastFor = ONE_MIN, lockAtMostFor = ONE_MIN)
    public void setSqExamCrtfn() throws Exception {
        log.info("SQ_EXAM_CRTFN Schedule Start");
        List<EBDSqCertiListDTO> eBDSqCertiListDTOList = eBDSqCertiReqService.getSqValidEndEmailList(-6);
        if(eBDSqCertiListDTOList != null && eBDSqCertiListDTOList.size() > 0){
            //메일 발송
            COMailDTO cOMailDTO = new COMailDTO();
            cOMailDTO.setSubject("SQ평가원 자격증 갱신 안내");
            for(EBDSqCertiListDTO eBDSqCertiListDTO : eBDSqCertiListDTOList){
                COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
                receiverDto.setEmail(eBDSqCertiListDTO.getEmail());
                receiverDto.setName(eBDSqCertiListDTO.getName());
                receiverDto.setNote1(eBDSqCertiListDTO.getJdgmtNo());
                receiverDto.setNote2(CODateUtil.convertDate(eBDSqCertiListDTO.getAcqsnDtm(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", ""));
                receiverDto.setNote3(eBDSqCertiListDTO.getValidEndDt());
                cOMailDTO.getReceiver().add(receiverDto);
            }
            cOMessageService.sendMail(cOMailDTO, "EBDSqCertiValidEndCrtfnMail.html");
        }
    }

    /**
     * 교육 시작 후 해당 교육의 선발대기 상태인 신청자 상태 미선발로 변경
     */
    @Scheduled(cron = "0 0 2 * * ?") // 초(0~59) 분(0~59) 시(0~23) 일(1~31) 월(1~12) 요일(1~7, 일요일 : 1) 연도(생략가능)
    @SchedulerLock(name = "EP_EDCTN_PTCPT_STTS_CRTFN", lockAtLeastFor = ONE_MIN, lockAtMostFor = ONE_MIN)
    public void updateStrtEduPtcptSttsCrtfn() throws Exception {
        log.info("EP_EDCTN_PTCPT_STTS_CRTFN Schedule Start");

        cOScheduleMapper.updateStrtEduPtcptStts();

        log.info("EP_EDCTN_PTCPT_STTS_CRTFN Schedule End");
    }

    /**
     *  퇴실 안한사람 자동 퇴실처리
     */
    @Scheduled(cron = "0 30 2 * * ?") // 초(0~59) 분(0~59) 시(0~23) 일(1~31) 월(1~12) 요일(1~7, 일요일 : 1) 연도(생략가능)
    @SchedulerLock(name = "EP_EDCTN_PTCPT_STTS_ATNDC", lockAtLeastFor = ONE_MIN, lockAtMostFor = ONE_MIN)
    public void updatAtndcInfo() throws Exception {
        log.info("uEP_EDCTN_PTCPT_STTS_ATNDC Schedule Start");

        cOScheduleMapper.updatAtndcInfo();

        log.info("EP_EDCTN_PTCPT_STTS_ATNDC Schedule End");
    }


}
