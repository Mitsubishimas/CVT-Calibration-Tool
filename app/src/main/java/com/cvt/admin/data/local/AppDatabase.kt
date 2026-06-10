package com.cvt.admin.data.local

import androidx.room.*
import android.content.Context

@Database(
    entities = [
        CachedOrder::class,
        CachedUser::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun orderDao(): OrderDao
    abstract fun userDao(): UserDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cvt_admin_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Entity(tableName = "cached_orders")
data class CachedOrder(
    @PrimaryKey
    val id: Int,
    val orderNumber: String,
    val email: String,
    val phone: String,
    val productName: String,
    val amount: Double,
    val status: String,
    val type: String,
    val createdAt: String,
    val cachedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "cached_users")
data class CachedUser(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val isBlocked: Boolean,
    val subscriptionUntil: String?,
    val cachedAt: Long = System.currentTimeMillis()
)

@Dao
interface OrderDao {
    @Query("SELECT * FROM cached_orders WHERE type = :type ORDER BY id DESC")
    suspend fun getOrdersByType(type: String): List<CachedOrder>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<CachedOrder>)
    
    @Query("DELETE FROM cached_orders WHERE cachedAt < :timestamp")
    suspend fun deleteOldOrders(timestamp: Long)
}

@Dao
interface UserDao {
    @Query("SELECT * FROM cached_users")
    suspend fun getAllUsers(): List<CachedUser>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<CachedUser>)
    
    @Query("DELETE FROM cached_users")
    suspend fun clearAll()
}
