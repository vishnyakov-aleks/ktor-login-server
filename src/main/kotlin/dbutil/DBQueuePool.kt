package dbutil

import config.Config
import java.sql.Connection
import java.sql.DriverManager

internal object DBQueuePool {
    private var writeConList: Array<Connection> = arrayOf(
            init(),
            init()
    )
    private var readConList: Array<Connection> = arrayOf(
            init(),
            init()
    )

    val priorityConnection = init()

    fun init(): Connection {
        val userName = Config.PGSQL_USER
        val password = Config.PGSQL_PASSWORD
        val url = Config.PGSQL_URL
        return DriverManager.getConnection(url, userName, password)
    }

    private var lastReadIndex = 0
    private var lastWriteIndex = 0
    val nextWritableConnection: Connection
        get() {
            var i = lastWriteIndex
            if (i >= writeConList.size-1) {
                lastWriteIndex = 0
                i = 0
            } else lastWriteIndex++


            return writeConList[i]
        }

    val nextReadableConnection: Connection
        get() {
            if (lastReadIndex >= readConList.size-1) {
                lastReadIndex = 0
            } else lastReadIndex++

            return readConList[lastReadIndex]
        }
}