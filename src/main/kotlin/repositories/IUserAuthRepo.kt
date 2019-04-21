package repositories

import pojo.PasswordRestoreCode
import pojo.User

interface IUserAuthRepo {
    fun getUserByEmail(email: String): User?
    fun getUserById(userId: Int): User?
    fun addUser(email: String, password: String, createTime: Long): Int
    fun isEmailFree(email: String): Boolean
    fun addRestorePasswordCode(expireTime: Long, code: String, userId: Int): Int
    fun getPasswordRestoreCode(code: String): PasswordRestoreCode?
    fun expirePasswordResetCode(code: String): Int
    fun setPassword(userId: Int, encryptedPassword: String): Int
}