package dbutil


object DBCleaner {
    fun cleanAll() {
        try {
            println("start clean")
            cleanOldUsers()
            println("cleanOldUsers finished")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun cleanOldUsers(): Int {
        val create = DBManager.instance.con(DBManager.InitType.write)
        return 0 //TODO
    }
}