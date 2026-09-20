package org.sony.educative.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class CoroutineScopeExample {
    fun main(): Unit = runBlocking {
        getArticleFromUser()
    }

    suspend fun getArticleFromUser() = coroutineScope {
        val articleRepository = ArticleRepositoryImpl()
        val articles = async {
            articleRepository.getArticles()
        }
        articles.await().filter {
            it.title == "abc"
        }
    }
}

@FunctionalInterface
interface ArticleRepository {
    suspend fun getArticles(): List<Article>
}

class ArticleRepositoryImpl() : ArticleRepository {
    override suspend fun getArticles() = listOf(
        Article(
            title = "abc",
            author = "abc",
            description = "abc",
        )
    )
}

data class Article(
    val title: String,
    val author: String,
    val description: String,
)