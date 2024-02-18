package com.example.hospital_navigation_system;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HospitalDataDao {

    @Insert
    public void insertPatient(Patient ps);

    @Delete
    public void deletePatient(Patient ps);

    @Update
    public void updatePatient(Patient ps);

    @Query("SELECT * FROM Patient WHERE patientName= :pname ")
    Patient fetchPatientByName(String pname);
    @Query("SELECT pId FROM Patient WHERE patientName = :name")
    int fetchPatientID(String name);

    @Query("SELECT * FROM Patient")
    List<Patient> fetchPatients();

    @Query ("SELECT CASE WHEN nausea > 0 THEN 'Nausea, ' ELSE '' END || CASE WHEN headache > 0 THEN 'Headache, ' ELSE '' END  || CASE WHEN diarrhea > 0 THEN 'Diarrhea, ' ELSE '' END || CASE WHEN soreThroat > 0 THEN 'Sore Throat' ELSE '' END || CASE WHEN cnc > 0 THEN 'Cold and Cough, ' ELSE '' END AS Symptoms FROM Patient WHERE patientName = :name")
    String fetchPatientSymptoms(String name);

    // Doctor methods
    @Insert
    void insertDoctor(Doctor doc);

    @Delete
    void deleteDoctor(Doctor doc);

    @Query("DELETE FROM Doctor WHERE docId NOT IN (SELECT MIN(docId) FROM Doctor GROUP BY doctorName HAVING COUNT(*) > 1)")
    void deleteDupDocs();

    @Update
    void updateDoctor(Doctor doc);

    @Query("SELECT * FROM Doctor WHERE specialization=:spec AND isOnCall=1 LIMIT 1")
    Doctor fetchOnCallDoctor(String spec);

    @Query("SELECT * FROM Doctor WHERE doctorName= :name LIMIT 1")
    Doctor fetchDoctor(String name);

    // Room Allocation
    @Insert
    void insertRoom(RoomAllocation room);

    @Delete
    void deleteRoom(RoomAllocation room);

    @Update
    void updateRoom(RoomAllocation room);

    @Query("SELECT * FROM RoomAllocation WHERE roomCode= :roomCode AND isUse=0")
    RoomAllocation fetchAvlRoom(String roomCode);


    // DPLink
    @Query("INSERT INTO DPLink (pId, docId) VALUES (:pID, :docId)")
    void insertDPAlloc(int pID, int docId);

    // Master
    @Query("INSERT INTO DPLink (pId, docId) VALUES (:pID, :docId)")
    void insertDPLink(int pID, int docId);

    @Query("SELECT patientName, age, height, weight, CASE WHEN accident > 0 THEN 'Accident, ' ELSE '' END || CASE WHEN stroke > 0 THEN 'Stroke, ' ELSE ''  END || CASE WHEN allergies > 0 THEN 'Allergies, ' ELSE '' END || CASE WHEN covid > 0 THEN 'Covid ' ELSE '' END AS symptoms FROM Patient WHERE pId IN (SELECT pId FROM DPLink WHERE docId IN (SELECT docId FROM DOCTOR WHERE doctorName=:name))")
    List<PatientDetails> fetchPatientDetails(String name);

}
