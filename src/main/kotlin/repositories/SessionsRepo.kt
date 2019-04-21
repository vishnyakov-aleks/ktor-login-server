package repositories

import authserver.database.generated.Tables.SESSIONS
import java.util.concurrent.ConcurrentHashMap

class SessionsRepo : AbsDatabaseRepo(), ISessionsRepo {

    private val sessionCodes = ConcurrentHashMap<String, Int>()

    init {
        dbReader.select(SESSIONS.SESSION, SESSIONS.EXPIRED_REASON_CODE)
            .from(SESSIONS)
            .fetch { sessionCodes[it[SESSIONS.SESSION]] = it[SESSIONS.EXPIRED_REASON_CODE] }
    }

    override fun getExpireReasonCode(session: String): Int? {
        return sessionCodes[session]
    }

    override fun addSession(userAgent: String, userId: Int, session: String, createTime: Long): Int {
        sessionCodes[session] = 0
        return dbWriter.insertInto(SESSIONS)
            .columns(
                SESSIONS.SESSION,
                SESSIONS.USER_ID,
                SESSIONS.USER_AGENT,
                SESSIONS.CREATE_TIME
            ).values(
                session,
                userId,
                userAgent,
                createTime
            ).execute()
    }

    override fun replaceSession(session: String, newSession: String): Int {
        return dbWriter.update(SESSIONS)
            .set(SESSIONS.SESSION, newSession)
            .where(SESSIONS.SESSION.eq(session))
            .execute()
            .apply {
                sessionCodes.remove(session)
                sessionCodes[newSession] = 0
            }
    }

    override fun expireSession(session: String, code: Int): Int {
        sessionCodes[session] = code
        return dbWriter.update(SESSIONS)
            .set(SESSIONS.EXPIRED_REASON_CODE, code)
            .where(SESSIONS.SESSION.eq(session))
            .execute()
    }

    override fun expireAllSessions(userId: Int, code: Int): Int {
        return dbReader.select(SESSIONS.SESSION)
            .from(SESSIONS)
            .where(SESSIONS.USER_ID.eq(userId))
            .fetch { expireSession(it[SESSIONS.SESSION], code) }
            .size
    }
}