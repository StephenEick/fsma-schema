package com.example.fsma.util

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("blog")
data class FsmaProperties(var title: String, val banner: Banner) {
    data class Banner(val title: String? = null, val content: String)
}
