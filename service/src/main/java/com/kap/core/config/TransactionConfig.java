package com.kap.core.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;

/**
 * <pre>
 * 트랜젝션 config 설정
 * </pre>
 *
 * @ClassName		    : TransactionConfig.java
 * @Description		: 트랜젝션 Config 설정
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
@Configuration
public class TransactionConfig {
    //참고할 패키지명
    @Value("${mybatis-config.transaction-package-nm}")
    private String AOP_TRANSCTION_EXPRESSION;

    @Autowired
    private TransactionManager transactionManager;

    @Bean
    public TransactionInterceptor transactionAdvice() {
        TransactionInterceptor txAdvice = new TransactionInterceptor();
        NameMatchTransactionAttributeSource txAttributeSource = new NameMatchTransactionAttributeSource();
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        transactionAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        HashMap<String, TransactionAttribute> txmethods = new HashMap<String, TransactionAttribute>();
        //insert로 시작하는 함수
        txmethods.put("insert*", transactionAttribute);
        //update로 시작하는 함수
        txmethods.put("update*", transactionAttribute);
        //delete로 시작하는 함수
        txmethods.put("delete*", transactionAttribute);
        txAttributeSource.setNameMap(txmethods);
        txAdvice.setTransactionAttributeSource(txAttributeSource);
        txAdvice.setTransactionManager(transactionManager);
        return txAdvice;
    }

    @Bean
    public Advisor transactionAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_TRANSCTION_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
    }
}