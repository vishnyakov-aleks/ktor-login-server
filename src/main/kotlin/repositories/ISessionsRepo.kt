package repositories

interface ISessionsRepo {
    fun getExpireReasonCode(session: String): Int?
    fun addSession(userAgent: String, userId: Int, session: String, createTime: Long): Int
    fun replaceSession(session: String, newSession: String): Int
    fun expireSession(session: String, code: Int): Int
    fun expireAllSessions(userId: Int, code: Int = 100): Int
}