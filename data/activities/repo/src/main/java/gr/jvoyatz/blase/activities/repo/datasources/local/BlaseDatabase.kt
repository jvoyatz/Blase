package gr.jvoyatz.blase.activities.repo.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gr.jvoyatz.blase.activities.repo.datasources.local.entities.BoredActivityEntity


private const val DB_VERSION = 1
@Database(
    entities = arrayOf(BoredActivityEntity::class),
    version = DB_VERSION
)
abstract class BlaseDatabase: RoomDatabase() {
    abstract fun boredActivityDao(): BoredActivityDao

    companion object{
        private var instance: BlaseDatabase? = null

        fun getDatabase(context: Context):BlaseDatabase {
            return instance ?: lazy(this) {
                Room.databaseBuilder(context, BlaseDatabase::class.java, "blase_database").build()
            }.let {
                instance = it.value
                instance!!
            }
        }
    }
}