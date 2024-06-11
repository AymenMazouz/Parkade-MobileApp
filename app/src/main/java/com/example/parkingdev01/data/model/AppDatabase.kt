package com.example.parkingdev01.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.parkingdev01.data.dao.ParkingDao
import com.example.parkingdev01.data.dao.ReservationDao
import com.example.parkingdev01.data.model.Parking
import com.example.parkingdev01.data.model.Reservation

@Database(entities = [Reservation::class, Parking::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reservationDao(): ReservationDao

    abstract fun parkingDao(): ParkingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
