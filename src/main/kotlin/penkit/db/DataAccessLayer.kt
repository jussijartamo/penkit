package penkit.db

interface DataAccessLayer {

    fun insert(entry: Entry)

    fun viewCurrentSituation(): List<TeamView>

}