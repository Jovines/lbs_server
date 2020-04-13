package com.jovines.lbs_server

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@MapperScan("com.jovines.lbs_server.dao")
class LbsServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(LbsServerApplication::class.java, *args)
}