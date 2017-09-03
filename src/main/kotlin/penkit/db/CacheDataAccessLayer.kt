package penkit.db

import java.math.BigDecimal
import java.util.concurrent.ConcurrentHashMap

class CacheDataAccessLayer(private val db: DataAccessLayer): DataAccessLayer {

    private val cache: ConcurrentHashMap<String, TeamView> by lazy {
        val snapshot = db.viewCurrentSituation().map { it.team to it }.toMap()
        ConcurrentHashMap<String, TeamView>(snapshot)
    }

    override fun viewCurrentSituation(): List<TeamView> {
        return cache.values.toList()
    }

    override fun insert(entry: Entry) {
        db.insert(entry)
        cache.compute(entry.team, { s: String, teamView: TeamView? ->
            if(teamView != null) {
                TeamView(team = teamView.team, overallKg = teamView.overallKg.add(entry.kg), recentlyAddedKg = teamView.recentlyAddedKg.add(entry.kg))
            } else {
                TeamView(team = s, overallKg = entry.kg, recentlyAddedKg = entry.kg)
            }

        })
    }


}