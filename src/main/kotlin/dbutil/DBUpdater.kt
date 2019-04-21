package dbutil

import authserver.database.generated.Tables.UPDATEDB
import java.io.File
import java.time.LocalDateTime
import java.util.*

object DBUpdater {
    private val DB_UPDATE_FOLDER = File("updatedb")
    private val DB_INIT_SQL = File(DB_UPDATE_FOLDER.absolutePath + File.separator + "init.sql")
    private var currentDBVersion: Int = -1

    fun createDatabase() {
        val create = DBManager.instance.con(DBManager.InitType.write)
        currentDBVersion = try {
            create.select(UPDATEDB.VERSION)
                    .from(UPDATEDB)
                    .fetchOne().get(UPDATEDB.VERSION)!!
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }

        if (currentDBVersion == -1) {
            DB_INIT_SQL.readText().trim().split(";")
                    .filterNot { query -> query.trim().isEmpty() }
                    .forEach { query -> println(query); create.execute(query) }
        }
    }

    fun needUpdate(): Int {
        if (!DB_UPDATE_FOLDER.exists()) {
            DB_UPDATE_FOLDER.mkdirs()
        }

        val create = DBManager.instance.con(DBManager.InitType.read)
        currentDBVersion = create.select(UPDATEDB.VERSION)
                .from(UPDATEDB)
                .fetchOne().get(UPDATEDB.VERSION)

        return DB_UPDATE_FOLDER.listFiles()
                .asSequence()
                .filterNot { it.name.split(".")[0].contains("\\D".toRegex()) }
                .map { Integer.parseInt(it.name.split(".")[0]) }
                .count { it > currentDBVersion }
    }

    fun updateDB() {
        val create = DBManager.instance.con(DBManager.InitType.write)
        val updateFiles = TreeMap<Int, String>()
        var updateVersion = 0
        for (file in DB_UPDATE_FOLDER.listFiles()) {
            if (file.name == DB_INIT_SQL.name) {
                continue
            }

            val fileVersion = Integer.parseInt(file.name.split(".")[0])
            if (fileVersion > currentDBVersion) {
                updateFiles[fileVersion] = file.readText().trim()
            }
        }

        for (entry in updateFiles) {
            entry.value.split(";")
                    .filterNot { query -> query.trim().isEmpty() }
                    .forEach { query -> create.execute(query) }
            if (updateVersion < entry.key) {
                updateVersion = entry.key

                create.update(UPDATEDB)
                        .set(UPDATEDB.VERSION, updateVersion)
                        .set(UPDATEDB.UPDATE_TIME, System.currentTimeMillis())
                        .execute()
            }
        }

    }
}