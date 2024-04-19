package com.example.fsma

import com.example.fsma.util.FsmaProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(FsmaProperties::class)
class FsmaApplication

fun main(args: Array<String>) {
    runApplication<FsmaApplication>(*args)
}
