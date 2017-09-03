package penkit

import org.dalesbred.Database
import org.dalesbred.integration.kotlin.executeQuery
import org.dalesbred.query.SqlQuery
import org.dalesbred.result.ResultTable
import org.junit.Test
import penkit.db.Entry
import penkit.db.PostgreDataAccessLayer
import penkit.db.Team
import penkit.db.TeamView
import java.math.BigDecimal
import java.util.stream.IntStream

class TestQuery {

    val db = PostgreForTesting.dao()

    @Test
    fun testMultipleInsertsForSameTeam() {
        IntStream.range(0, 10).parallel().forEach({
            db.insert(Entry(name="JUNIT", team="MULTI_INSERT", kg = BigDecimal("10.5")))
        })
    }

    @Test
    fun testMultipleInsertsForDifferentTeams() {
        IntStream.range(0, 10).parallel().forEach({
            db.insert(Entry(name="JUNIT", team="MULTI_INSERT_${it}", kg = BigDecimal("10.5")))
        })
    }
}
