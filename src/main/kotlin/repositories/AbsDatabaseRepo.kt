package repositories

import dbutil.DBManager

abstract class AbsDatabaseRepo {
    val dbReader = DBManager.instance.con(DBManager.InitType.read)
    val dbWriter = DBManager.instance.con(DBManager.InitType.write)
}