package dbutil

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.util.logging.Logger


/**
 * Created by vishnyakov on 26.06.17.
 */

class DBManager private constructor() {
    init {
        Class.forName("org.postgresql.Driver")
    }

    enum class InitType { read, write, priority }

    fun con(type: InitType): DSLContext {
        var con = when(type) {
            InitType.read -> DBQueuePool.nextReadableConnection
            InitType.write -> DBQueuePool.nextWritableConnection
            InitType.priority -> DBQueuePool.priorityConnection
        }

        try {
            con.schema
        } catch (e: Exception) {
            Logger.getAnonymousLogger().warning("SQL connection error " + e.message)
            con = DBQueuePool.init()
        }

        return DSL.using(con, SQLDialect.POSTGRES_10)
    }

    companion object {
        val instance = DBManager()
    }
}
