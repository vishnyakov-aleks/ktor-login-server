package pojo

class User (val id: Int,
            val email: String,
            @Transient val encPassword: String,
            val registerDate: Long)