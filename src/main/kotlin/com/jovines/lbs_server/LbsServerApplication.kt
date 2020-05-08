package com.jovines.lbs_server

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer


@SpringBootApplication
@MapperScan("com.jovines.lbs_server.dao")
class LbsServerApplication: SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder? {
        return application.sources(LbsServerApplication::class.java)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(LbsServerApplication::class.java, *args)
}