package com.antique.login.data.mapper

import com.antique.common.data.User
import com.antique.common.data.UserDto

fun mapperToDomain(user: UserDto): User {
    return User(
        user.uid,
        user.nickName,
        user.profileImageUrl,
        user.date
    )
}