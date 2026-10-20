package dao;

import model.ScenarioEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    /**
     * Inserts an appointment utilizing parameterized SQL queries to safeguard runtime transfers.
     */
    public boolean addRecord(ScenarioEntity record) {
        String sql = "INSERT INTO appointments (patient_name, doctor, consult_fee, status) VALUES (?, ?, ?, ?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, record.patientName());
            pstmt.setString(2, record.doctor());
            pstmt.setDouble(3, record.consultFee());
            pstmt.setString(4, record.status());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ DAO Layer Error inserting data record: " + e.getMessage());
            return false;
        }
    }

    /**
     * Queries and maps structural database contents back into runtime list data entities.
     */
    public List<ScenarioEntity> getAllRecords() {
        List<ScenarioEntity> dataList = new ArrayList<>();
        String sql = "SELECT id, patient_name, doctor, consult_fee, status FROM appointments;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ScenarioEntity entity = new ScenarioEntity(
                        rs.getInt("id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor"),
                        rs.getDouble("consult_fee"),
                        rs.getString("status")
                );
                dataList.add(entity);
            }

        } catch (SQLException e) {
            System.err.println("❌ DAO Layer Error querying data list records: " + e.getMessage());
        }
        return dataList;
    }
}
