package ru.skillbranch.kotlinexample

import java.lang.IllegalArgumentException

object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(fullName: String, email: String, password: String): User {
        val user = User.makeUser(fullName, email = email, password = password)
        if (map.containsKey(user.login)) {
            throw IllegalArgumentException("A user with this email already exists")
        } else {
            map[user.login] = user
        }
        return user
    }

    fun loginUser(login: String, password: String): String? {
        var user = map[login.trim()]
        if (user == null) {
            user = map[login.replace("[^+\\d]".toRegex(), "")]
        }
        return user?.run {
            if (checkPassword(password)) {
                this.userInfo
            } else {
                null
            }
        }
    }

    fun registerUserByPhone(fullName: String, phone: String): User {
        if (!phone.matches(Regex("[0-9+() -]+"))) {
            throw IllegalArgumentException()
        }
        val user = User.makeUser(fullName, phone = phone)
        if (map.containsKey(user.login)) {
            throw IllegalArgumentException("A user with this phone already exists")
        } else {
            map[user.login] = user
            user.accessCode
        }
        return user
    }

    fun requestAccessCode(phone: String) {
        val user = map[phone.replace("[^+\\d]".toRegex(), "")]
        user?.requestAccessCode()
    }

    fun clearHolder() {
        map.clear()
    }
}