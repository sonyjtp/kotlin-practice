package org.sony.educative.coroutines.testing

import org.sony.User


interface UserDatabase {
    suspend fun getUserData(): User
    suspend fun sendUserData(user: User)
}

class UserViewModel(
    val userDatabase: UserDatabase,

) {
    var progressBar: Boolean = false

    suspend fun sendUserData() {
        val user = userDatabase.getUserData()
        progressBar = true
        userDatabase.sendUserData(user.copy(age = user.age + 1))
        progressBar = false
    }
}