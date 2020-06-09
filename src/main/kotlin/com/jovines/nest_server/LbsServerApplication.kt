package com.jovines.nest_server

import com.jovines.nest_server.config.IMG_PATH
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import java.io.File


@SpringBootApplication
@MapperScan("com.jovines.nest_server.dao")
class LbsServerApplication : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder? {
        return application.sources(LbsServerApplication::class.java)
    }
}

fun main(args: Array<String>) {
    //初始化图片地址
    val imageFile = File(IMG_PATH)
    if (!imageFile.exists()) {
        imageFile.mkdirs()
    }
    SpringApplication.run(LbsServerApplication::class.java, *args)
}