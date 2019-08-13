package com.example

import com.example.filter.JwtAuthenticationFilter
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

//@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@EnableSwagger2
@MapperScan(basePackages = ["com.example.demo.dao", "com.example.demo.mapper"
])
@SpringBootApplication
//对包进行扫描
open class DemoApplication {


    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun jwtFilter(): FilterRegistrationBean<JwtAuthenticationFilter> {
        val filter = JwtAuthenticationFilter(
                "/user/**")
        val registrationBean = FilterRegistrationBean(filter)
        registrationBean.filter = filter
        return registrationBean
    }

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(ApiInfoBuilder()
                        .title("swagger")
                        .description("描述内容")
//                        .contact("xxxx")
                        .version("1.0.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.any())//路径扫描
                .paths(PathSelectors.any()).build()
    }

}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)

}


