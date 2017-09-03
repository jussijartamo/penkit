package penkit.db

import org.dalesbred.Database
import org.dalesbred.query.SqlQuery.*

class PostgreDataAccessLayer(config: PostgresConfig) : DataAccessLayer {

    private val db = Database.forUrlAndCredentials(config.url, config.username, config.password)

    override fun viewCurrentSituation(): List<TeamView> {
        return db.findAll(TeamView::class.java, "SELECT * FROM teamview")
    }

    override fun insert(entry: Entry) {
        db.withTransaction({
            db.update(query("INSERT INTO team (\"name\", \"kg\") VALUES(?,?) ON CONFLICT (\"name\") DO UPDATE SET \"kg\" = team.\"kg\" + EXCLUDED.\"kg\" WHERE team.\"name\" = EXCLUDED.\"name\"", entry.team, entry.kg))
            db.update(query("INSERT INTO entry (\"team\", \"name\", kg) VALUES(?,?,?)", entry.team, entry.name, entry.kg))
        })
    }

}