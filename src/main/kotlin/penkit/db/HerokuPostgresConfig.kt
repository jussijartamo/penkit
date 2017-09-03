package penkit.db

import java.net.URI

object HerokuPostgresConfig {
    private val HEROKU_DATABASE_URL = "DATABASE_URL"

    fun postgresConfigFromEnv(): PostgresConfig {
        val dbUri = URI(System.getenv(HEROKU_DATABASE_URL));
        val username = dbUri.userInfo.split(":")[0]
        val password = dbUri.userInfo.split(":")[1]
        val dbUrl = "jdbc:postgresql://" + dbUri.host + ':' + dbUri.port + dbUri.path
        return PostgresConfig(
            url = dbUrl,
                username = username,
                password = password
        )
    }

}

