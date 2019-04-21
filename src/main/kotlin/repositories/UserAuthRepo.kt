package repositories

import authserver.database.generated.Tables.RESTORE_PASSWORD_CODES
import authserver.database.generated.Tables.USERS
import pojo.PasswordRestoreCode
import pojo.User

class UserAuthRepo : AbsDatabaseRepo(), IUserAuthRepo {
    override fun getUserByEmail(email: String): User? {
        return dbReader.selectFrom(USERS)
            .where(USERS.EMAIL.eq(email))
            .fetchAny { User(it.id, it.email, it.passwordHash, it.createTime) }
    }

    override fun getUserById(userId: Int): User? {
        return dbReader.selectFrom(USERS)
            .where(USERS.ID.eq(userId))
            .fetchAny { User(it.id, it.email, it.passwordHash, it.createTime) }
    }

    override fun addUser(email: String, password: String, createTime: Long): Int {
        return dbWriter.insertInto(USERS)
            .columns(
                USERS.EMAIL,
                USERS.PASSWORD_HASH,
                USERS.CREATE_TIME
            ).values(
                email,
                password,
                createTime
            ).execute()
    }

    override fun isEmailFree(email: String): Boolean {
        return dbReader.select(USERS.EMAIL)
            .from(USERS)
            .where(USERS.EMAIL.eq(email))
            .count() == 0
    }

    override fun addRestorePasswordCode(expireTime: Long, code: String, userId: Int): Int {
        return dbWriter.insertInto(RESTORE_PASSWORD_CODES)
            .columns(
                RESTORE_PASSWORD_CODES.EXPIRE_TIME,
                RESTORE_PASSWORD_CODES.CODE,
                RESTORE_PASSWORD_CODES.USER_ID
            ).values(
                expireTime,
                code,
                userId
            ).execute()
    }

    override fun getPasswordRestoreCode(code: String): PasswordRestoreCode? {
        return dbReader.selectFrom(RESTORE_PASSWORD_CODES)
            .where(RESTORE_PASSWORD_CODES.CODE.eq(code))
            .fetchAny { PasswordRestoreCode(it.userId, it.expireTime, it.code) }
    }

    override fun expirePasswordResetCode(code: String): Int {
        return dbWriter.update(RESTORE_PASSWORD_CODES)
            .set(RESTORE_PASSWORD_CODES.EXPIRE_TIME, 0)
            .where(RESTORE_PASSWORD_CODES.CODE.eq(code))
            .execute()
    }

    override fun setPassword(userId: Int, encryptedPassword: String): Int {
        return dbWriter.update(USERS)
            .set(USERS.PASSWORD_HASH, encryptedPassword)
            .where(USERS.ID.eq(userId))
            .execute()
    }
}