package com.cqp.sm9utilsstarter.config;

import com.cqp.sm9utilsstarter.SM2.SM2Service.SM2ServiceImpl;
import com.cqp.sm9utilsstarter.SM3.SM3Service.SM3ServiceImpl;
import com.cqp.sm9utilsstarter.SM4.SM4Service.SM4ServiceImpl;
import com.cqp.sm9utilsstarter.SM9.service.SM9ServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName SM9Config.java
 * @Description TODO
 * @createTime 2021年04月07日 14:53:00
 */
@Configuration
public class SMConfig {

    @Bean(name = "SM2Service")
    public SM2ServiceImpl SM2ServiceImpl(){
        return new SM2ServiceImpl();
    }

    @Bean(name = "SM3Service")
    public SM3ServiceImpl SM3ServiceImpl(){
        return new SM3ServiceImpl();
    }

    @Bean(name = "SM4Service")
    public SM4ServiceImpl SM4ServiceImpl(){
        return new SM4ServiceImpl();
    }

    @Bean(name = "SM9Service")
    public SM9ServiceImpl SM9ServiceImpl(){
        return new SM9ServiceImpl();
    }

}
