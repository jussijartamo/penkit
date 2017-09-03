package penkit

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import penkit.db.CacheDataAccessLayer
import penkit.db.Entry
import penkit.db.TeamView
import java.math.BigDecimal

class TestCache {

    private val db = PostgreForTesting.dao()
    private val cache = CacheDataAccessLayer(db)

    @Test
    fun testCacheStaysInSyncWithDB() {
        val increment = BigDecimal("10.0")
        val entry = Entry(name="JUNIT",team="CACHE", kg= increment)
        cache.insert(entry)
        val inBeginning = cache.viewCurrentSituation()
        cache.insert(entry)
        val afterInsert = cache.viewCurrentSituation()
        val teamCacheInBeginning = inBeginning.find { it.team == "CACHE" }
        val teamCacheAfterInsert = afterInsert.find { it.team == "CACHE" }

        assertEquals("Beginning state should be ${increment} smaller than ending state!",
                teamCacheInBeginning!!.overallKg.add(increment),
                teamCacheAfterInsert!!.overallKg)
    }
}