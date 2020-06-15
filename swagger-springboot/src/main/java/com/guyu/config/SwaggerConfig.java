package com.guyu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2   //开启swagger2
public class SwaggerConfig {


    //配置swagger的docket的bean实例
    @Bean
    public Docket docket(Environment environment){
        //设置要显示的swagger环境
        Profiles profiles=Profiles.of("dev","test");
        //environment.acceptsProfiles判断自己是否处在自己设定 的环境中
        boolean flag = environment.acceptsProfiles(profiles);
        System.out.println(flag);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("谷雨的api组")
                //.enable(flag)
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.guyu.controller"))
                .build();
    }
    //配置swagger信息=apiinfo
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact=new Contact("谷雨","http://www.wxgy.site/guyu","guyu_818@163.com");

        return new ApiInfo("谷雨的swagger api文档",
                "这里是注释",
                "1.0",
                "http://www.wxgy.site/guyu",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

    //设置多个分组
    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("第一组");
    }
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("第二组");
    }
    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("第三组");
    }

}
