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
}
