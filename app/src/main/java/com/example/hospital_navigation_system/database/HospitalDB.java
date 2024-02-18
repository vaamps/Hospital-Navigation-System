package com.example.hospital_navigation_system;

// importing all the classes to create a room database

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.Executor;


@Database(entities = {Patient.class, Doctor.class, RoomAllocation.class, DPLink.class}, version = 2, exportSchema = false)
public abstract class HospitalDB extends RoomDatabase {
    private static final String DATABASE_NAME = "HospDB";
    private static HospitalDB dbInstance;
    public abstract HospitalDataDao hDao2();

    // New Migration Code to prefill the DB with Doctor Data
//    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            // Insert initial data here
//            database.execSQL("INSERT INTO DOCTOR (doctorName, isOnCall, specialization) VALUES ('Robert Parkins', 1, 'Accidents'), ('Josh Anderson', 0, 'Accidents'), ('John Smith', 0, 'Allergies'), ('Jane Doe', 1, 'Allergies'), ('Ron Swanson', 1, 'Stroke'), ('Ann Perkins', 0, 'Stroke'), ('Leslie Knope', 1, 'Covid'), ('Donna Dunphy', 0, 'Covid'), ('Amy Santiago', 0, 'ENT'), ('Jake Peralta', 1, 'ENT'), ('Ray Holt', 1, 'General'), ('Terry Crews', 0, 'General')");
//        }
//    };

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Run your data initialization logic here
            docToDB(db);
        }
    };

    private static void docToDB(SupportSQLiteDatabase database) {
        // Insert initial data using raw SQL
        database.execSQL("INSERT INTO DOCTOR (doctorName, isOnCall, specialization) VALUES ('Robert Parkins', 1, 'Accidents'), ('Josh Anderson', 0, 'Accidents'), ('John Smith', 0, 'Allergies'), ('Jane Doe', 1, 'Allergies'), ('Ron Swanson', 1, 'Stroke'), ('Ann Perkins', 0, 'Stroke'), ('Leslie Knope', 1, 'Covid'), ('Donna Dunphy', 0, 'Covid'), ('Amy Santiago', 0, 'ENT'), ('Jake Peralta', 1, 'ENT'), ('Ray Holt', 1, 'General'), ('Terry Crews', 0, 'General')");
    }
    public static synchronized HospitalDB getInstance(Context context){
        //Create new database with last name for a name if none exist
        if(dbInstance == null){
            dbInstance = Room
                    .databaseBuilder(context.getApplicationContext(), HospitalDB.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
//                    .addMigrations(HospitalDB.MIGRATION_1_2)
//                    .addCallback(roomCallback)
                    .build();

            Executor sql = dbInstance.getQueryExecutor();
            sql.execute(() -> {
                    dbInstance.hDao2().insertDoctor(new Doctor("Robert Parkins", 1, "Accidents"));
            dbInstance.hDao2().insertDoctor(new Doctor("Josh Anderson", 0, "Accidents"));
            dbInstance.hDao2().insertDoctor(new Doctor("John Smith", 0, "Allergies"));
            dbInstance.hDao2().insertDoctor(new Doctor("Jane Doe", 1, "Allergies"));
            dbInstance.hDao2().insertDoctor(new Doctor("Ron Swanson", 1, "Stroke"));
            dbInstance.hDao2().insertDoctor(new Doctor("Ann Perkins", 0, "Stroke"));
            dbInstance.hDao2().insertDoctor(new Doctor("Leslie Knope", 1, "Covid"));
            dbInstance.hDao2().insertDoctor(new Doctor("Donna Dunphy", 0, "Covid"));
            dbInstance.hDao2().insertDoctor(new Doctor("Amy Santiago", 0, "ENT"));
            dbInstance.hDao2().insertDoctor(new Doctor("Jake Peralta", 1, "ENT"));
            dbInstance.hDao2().insertDoctor(new Doctor("Ray Holt", 1, "General"));
            dbInstance.hDao2().insertDoctor(new Doctor("Terry Crews", 0, "General"));
            });
        }
        return dbInstance;
    }
}
