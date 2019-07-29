package com.example.demo

import com.example.demo.mapper.Usermapper
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.example.demo.mapper")//对包进行扫描
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
    @Bean
    fun  restTemplate():RestTemplate{
        return  RestTemplate()
    }
}

@Bean
fun api(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
            .apiInfo( ApiInfoBuilder()
            .title("xx公司_xx项目_接口文档")
            .description("描述内容")
//                        .contact("xxxx")
            .version("1.0.0")
            .build())
            .select()
            .apis(RequestHandlerSelectors.any())//路径扫描
            .paths(PathSelectors.any()).build()
}
