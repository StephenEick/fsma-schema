package com.foodtraceai

import com.foodtraceai.config.FsmaProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(FsmaProperties::class)
class FsmaApplication

fun main(args: Array<String>) {
    runApplication<FsmaApplication>(*args)
}
