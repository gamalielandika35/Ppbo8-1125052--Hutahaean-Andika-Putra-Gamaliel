
package hy.material8database.repository;

import hy.material8database.database.Database;
import hy.material8database.models.jurusan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JurusanRepository {

    private static final Connection conn = Database.connect();

    public static List<jurusan> getAllJurusan() {
        List<jurusan> result = new ArrayList<>();

        String sql = "SELECT kode_jurusan, nama FROM jurusan ORDER BY kode_jurusan";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(new jurusan(
                    rs.getString("kode_jurusan"),
                    rs.getString("nama")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void addJurusan(jurusan jurusan) {
        String sql = "INSERT INTO jurusan (kode_jurusan, nama) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jurusan.kodeJurusan);
            stmt.setString(2, jurusan.nama);
            stmt.executeUpdate();

            System.out.println("Jurusan berhasil ditambahkan.");

        } catch (SQLException e) {
            System.out.println("Gagal menambahkan jurusan.");
            e.printStackTrace();
        }
    }

    public static void updateJurusan(jurusan jurusan) {
        String sql = "UPDATE jurusan SET nama = ? WHERE kode_jurusan = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jurusan.nama);
            stmt.setString(2, jurusan.kodeJurusan);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Jurusan berhasil diubah.");
            } else {
                System.out.println("Jurusan tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengubah jurusan.");
            e.printStackTrace();
        }
    }

    public static void deleteJurusan(String kodeJurusan) {
        String sql = "DELETE FROM jurusan WHERE kode_jurusan = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kodeJurusan);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Jurusan berhasil dihapus.");
            } else {
                System.out.println("Jurusan tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println(
                "Gagal menghapus jurusan. Periksa apakah jurusan masih digunakan."
            );
            e.printStackTrace();
        }
    }
}