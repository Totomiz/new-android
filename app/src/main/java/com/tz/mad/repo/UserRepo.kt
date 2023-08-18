package com.tz.mad.repo

import com.tz.mad.model.User

interface UserRepo {
    fun findUser(name:String):User?

    fun addUsers(users : List<User>)
}

class UserRepoImpl:UserRepo{

    private val _users = arrayListOf<User>()

    override fun findUser(name: String): User? {
        return _users.firstOrNull { it.name == name }
    }

    override fun addUsers(users : List<User>) {
        _users.addAll(users)
    }
}