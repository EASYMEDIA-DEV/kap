package com.kap.core.config;

import com.kap.service.IdGnrBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.impl.EgovSequenceIdGnrServiceImpl;
import org.egovframe.rte.fdl.idgnr.impl.EgovTableIdGnrServiceImpl;
import org.egovframe.rte.fdl.idgnr.impl.strategy.EgovIdGnrStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class IdgenConfig {

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    /**
     * 첨부파일 ID Generation  Strategy Config
     * @return
     */
    private EgovIdGnrStrategyImpl fileStrategy() {
        EgovIdGnrStrategyImpl egovIdGnrStrategyImpl = new EgovIdGnrStrategyImpl();
        egovIdGnrStrategyImpl.setPrefix("FILE_");
        egovIdGnrStrategyImpl.setCipers(15);
        egovIdGnrStrategyImpl.setFillChar('0');
        return egovIdGnrStrategyImpl;
    }

    /**
     * 첨부파일 ID Generation  Config
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl egovFileIdGnrService() {
        EgovTableIdGnrServiceImpl egovTableIdGnrServiceImpl = new EgovTableIdGnrServiceImpl();
        egovTableIdGnrServiceImpl.setDataSource(dataSource);
        egovTableIdGnrServiceImpl.setStrategy(fileStrategy());
        egovTableIdGnrServiceImpl.setBlockSize(1);
        egovTableIdGnrServiceImpl.setTable("co_seq_mst");
        egovTableIdGnrServiceImpl.setTableName("FILE_ID");
        return egovTableIdGnrServiceImpl;
    }

    /** 샘플 테이블 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl sampleTableIdGnrService() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("SAMPLE_ID")
                .setCipers(13)
                .build();
    }

    /** 메뉴 테이블 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl menuTableIdGnrService() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("MENU_TEST_SEQ")
                .setCipers(13)
                .build();
    }

    /** 샘플 쿼리 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovSequenceIdGnrServiceImpl sampleQueryIdGnrService() {
        EgovSequenceIdGnrServiceImpl egovIdGnrStrategyImpl = new EgovSequenceIdGnrServiceImpl();
        egovIdGnrStrategyImpl.setDataSource(dataSource);
        egovIdGnrStrategyImpl.setQuery("SELECT SEQ_SAMPLE.NEXTVAL FROM DUAL");
        return egovIdGnrStrategyImpl;
    }


    /** 시스템 로그 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl serviceLogIdGnrService() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setPreFix("SYSLOG_")
                .setTableName("SYSLOG_ID")
                .setCipers(13)
                .build();
    }

    /** 관리자 계정 테이블 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl admIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("ADM_SEQ")
                .setCipers(13)
                .build();
    }

    /** 관리자 알림 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl admNtcIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("ADM_NTC_SEQ")
                .setCipers(13)
                .build();
    }

    /** 관리자 알림 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl egovSysLogIdGnrService() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setPreFix("SYSLOG_")
                .setTable("co_seq_mst")
                .setTableName("SYSLOG_ID")
                .setCipers(13)
                .build();
    }

    /** 메뉴
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl menuIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("MENU_SEQ")
                .setCipers(13)
                .build();
    }

    /** 1:1 문의
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl qstnIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("QSTN_SEQ")
                .setCipers(13)
                .build();
    }

    /** 1:1 문의 답변
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl rplyIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("RPLY_SEQ")
                .setCipers(13)
                .build();
    }

    /** 1:1 문의 담당자
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl qaPicIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("QA_PIC_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육 시험 마스터
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl examMstIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EXAM_MST_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육 시험 질문 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl examQstnDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EXAM_QSTN_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육 시험 질문 보기 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl examQstnExmplDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EXAM_EXMPL_SEQ")
                .setCipers(13)
                .build();
    }
    /** 설문 마스터
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl svMstIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("SV_MST_SEQ")
                .setCipers(13)
                .build();
    }
    /** 설문 질문 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl svQstnDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("SV_QSTN_DTL_SEQ")
                .setCipers(13)
                .build();
    }
    /** 설문 질문 보기 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl svQstnExmplDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("SV_EXMPL_DTL_SEQ")
                .setCipers(13)
                .build();
    }
    /** 메인 비주얼
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl mainVslIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("MAIN_VSL_SEQ")
                .setCipers(13)
                .build();
    }

    /** 상생 사업
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl winBusIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("WIN_BUS_SEQ")
                .setCipers(13)
                .build();
    }

    /** 이미지 업로드
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl ftpIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("FTP_SEQ")
                .setCipers(13)
                .build();
    }

    /** 팝업 관리 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl smcMnPopDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("MN_POP_SEQ")
                .setCipers(13)
                .build();
    }

    /** SMS 내용 관리 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl smiSmsCntnDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("SMS_CNTN_SEQ")
                .setCipers(13)
                .build();
    }

    /** 강사 관리 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl mpcLecturerDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("LEC_SEQ")
                .setCipers(13)
                .build();
    }

    /** 부품사 관리 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl mpePartsCompanyDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("PARTS_CP_SEQ")
                .setCipers(13)
                .build();
    }

    /** 회원약관 관리 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl memTrmsDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("MEM_TRMS_SEQ")
                .setCipers(13)
                .build();
    }

    /** 이용약관 관리 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl trmsDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("TMNCS_SEQ")
                .setCipers(13)
                .build();
    }

    /** 개인정보처리방침 관리 상세
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl prsnDtlIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("PRSN_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육과정관리 마스터
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl edctnMstIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EDCTN_MST_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육차수관리 마스터
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl edctnEpisdIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EDCTN_EPISD_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육차수관리 마스터
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl edctnLctrIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EDCTN_LCTR_SEQ")
                .setCipers(13)
                .build();
    }



    /** 관리자 인증로그 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl admCrtfnNodgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("ADM_CRTFN_SEQ")
                .setCipers(13)
                .build();
    }


    /** 회원 변경 로그 시퀏스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl memModSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("MEM_MOD_SEQ")
                .setCipers(13)
                .build();
    }

    /** 회원 마스터  시퀏스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl memSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("MEM_SEQ")
                .setCipers(13)
                .build();
    }

    /** 회차관리 마스터 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl cxEpisdSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("CX_EPISD_SEQ")
                .setCipers(13)
                .build();
    }

    /** 지급차수 마스터 시퀏스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl cxEpisdGiveSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("CX_EPISD_GIVE_SEQ")
                .setCipers(13)
                .build();
    }

    /** 지급차수 마스터 시퀏스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl cxEpisdBsnOptnSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("CX_EPISD_BSN_OPTN_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육장 관리
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl edctnPlaceSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EDCTN_PLACE_SEQ")
                .setCipers(13)
                .build();
    }

    /** 콘텐츠 관리
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl cmsSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("CMS_SEQ")
                .setCipers(13)
                .build();
    }

    /** 상생신청 마스터 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl cxAppctnMstSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("CX_APPCTN_MST_SEQ")
                .setCipers(13)
                .build();
    }

    /** 상생신청 상세 시퀀스
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl cxAppctnRsumeDtlSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("CX_APPCTN_RSUME_DTL_SEQ")
                .setCipers(13)
                .build();
    }

    /** 공지사항 관리
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl ntfySeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("NTFY_SEQ")
                .setCipers(13)
                .build();
    }

    /** 재단소식 관리
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl fndnNewsSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("FNDN_NEWS_SEQ")
                .setCipers(13)
                .build();
    }

    /** 상생협력체감도조사 관리
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl cxAppctnRsumeSrvIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("CX_APPCTN_RSUME_SRV_SEQ")
                .setCipers(13)
                .build();
    }

    /** FAQ 관리
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl faqSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("FAQ_SEQ")
                .setCipers(13)
                .build();
    }

    /** 뉴스레터 관리
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl newsletterSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("NEWSLETTER_SEQ")
                .setCipers(13)
                .build();
    }

    /** 경영공시 관리
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl disclosureSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("DISCLOSURE_SEQ")
                .setCipers(13)
                .build();
    }

    /** 상생사업 관리 (사이트관리)
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl winMngSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("WIN_MNG_SEQ")
                .setCipers(13)
                .build();
    }


    /** 메시지 발송 로그
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl msgSendMstSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("MSG_SEND_SEQ")
                .setCipers(13)
                .build();
    }

    /** 상생협력체감도조사 회차관리
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl cxCmpnEpisdMstIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("CX_CMPN_EPISD_MST_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육 시험 참여 순번
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl examPtcptSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EXAM_PTCPT_SEQ")
                .setCipers(13)
                .build();
    }

    /** 교육 신청자 관리 순번
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public EgovTableIdGnrServiceImpl eduApplicantSeqIdgen() {
        return new IdGnrBuilder().setDataSource(dataSource).setEgovIdGnrStrategyImpl(new EgovIdGnrStrategyImpl())
                .setBlockSize(1)
                .setTable("co_seq_mst")
                .setTableName("EDU_APPLICANT_SEQ")
                .setCipers(13)
                .build();
    }

}
