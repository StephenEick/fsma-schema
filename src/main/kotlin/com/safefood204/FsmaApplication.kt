package com.safefood204

import com.safefood204.config.FsmaProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(FsmaProperties::class)
class FsmaApplication

fun main(args: Array<String>) {
    runApplication<FsmaApplication>(*args)
}
