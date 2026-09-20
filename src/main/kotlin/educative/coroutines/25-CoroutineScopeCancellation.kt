package org.sony.educative.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

data class Details(val name: String, val followers: Int)
data class Tweet(val text: String)
class ApiException( val code: Int,  message: String) : Throwable(message)

fun getFollowersNumber(): Int =
    throw ApiException(500, "Service unavailable")

suspend fun getUserName(): String {
    delay(500.milliseconds)
    return "username"
}

fun getTweets(): List<Tweet> {
    return listOf(Tweet("Hello, world"))
}

suspend fun getUserDetails(): Details = coroutineScope { // 2. coroutineScope - executes in the same coroutine; gets cancelled later by child at line 30
    val userName = async { getUserName() } // 3. suspended at line 20; gets cancelled later by sibling in the next line
    val followersNumber = async { getFollowersNumber() } // 4. throws error; cancels parent and its sibling
    Details(userName.await(), followersNumber.await()) // 5. throws exception when await is called
}

fun main() = runBlocking {
    val details = try {
        getUserDetails()  //1.
    } catch (e: ApiException) {
        println("${e.code}: ${e.message}") //6. follows 5' printed.
        null // 7. details is null.
    }
    val tweets = async { getTweets() } // 8. gets tweets successfully
    println("User: $details") // 9. prints User: null
    println("Tweets: ${tweets.await()}") // 10. prints tweets
}