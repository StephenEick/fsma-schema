package com.example.fsma.config

import com.example.fsma.model.Article
import com.example.fsma.model.User
import com.example.fsma.repository.ArticleRepository
import com.example.fsma.repository.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FsmaConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            articleRepository: ArticleRepository
    ) = ApplicationRunner {

        val johnDoe = userRepository.save(User("johnDoe", "John", "Doe"))
        articleRepository.save(
            Article(
            title = "Lorem",
            headline = "Lorem",
            content = "dolor sit amet",
            author = johnDoe
        )
        )
        articleRepository.save(
            Article(
            title = "Ipsum",
            headline = "Ipsum",
            content = "dolor sit amet",
            author = johnDoe
        )
        )
    }
}
