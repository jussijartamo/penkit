package penkit.db

import java.math.BigDecimal

data class TeamView(val team: String, val overallKg: BigDecimal, val recentlyAddedKg: BigDecimal)