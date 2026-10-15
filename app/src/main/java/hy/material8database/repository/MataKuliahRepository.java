
package hy.material8database.repository;

import hy.material8database.database.Database;
import hy.material8database.models.MataKuliah;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MataKuliahRepository {

    private static final Connection conn = Database.connect();

    public static List<MataKuliah> getAllMataKuliah() {
        List<MataKuliah> result = new ArrayList<>();

        String sql = "SELECT kode_matakuliah, nama, sks, kode_jurusan "
                   + "FROM matakuliah ORDER BY kode_matakuliah";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(new MataKuliah(
                    rs.getString("kode_matakuliah"),
                    rs.getString("nama"),
                    rs.getInt("sks"),
                    rs.getString("kode_jurusan")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void addMataKuliah(MataKuliah matakuliah) {
        String sql = "INSERT INTO matakuliah "
                   + "(kode_matakuliah, nama, sks, kode_jurusan) "
                   + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matakuliah.kodeMatakuliah);
            stmt.setString(2, matakuliah.nama);
            stmt.setInt(3, matakuliah.sks);
            stmt.setString(4, matakuliah.kodeJurusan);
            stmt.executeUpdate();

            System.out.println("Mata kuliah berhasil ditambahkan.");

        } catch (SQLException e) {
            System.out.println("Gagal menambahkan mata kuliah.");
            e.printStackTrace();
        }
    }

    public static void updateMataKuliah(MataKuliah matakuliah) {
        String sql = "UPDATE matakuliah "
                   + "SET nama = ?, sks = ?, kode_jurusan = ? "
                   + "WHERE kode_matakuliah = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matakuliah.nama);
            stmt.setInt(2, matakuliah.sks);
            stmt.setString(3, matakuliah.kodeJurusan);
            stmt.setString(4, matakuliah.kodeMatakuliah);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Mata kuliah berhasil diubah.");
            } else {
                System.out.println("Mata kuliah tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengubah mata kuliah.");
            e.printStackTrace();
        }
    }

    public static void deleteMataKuliah(String kodeMatakuliah) {
        String sql = "DELETE FROM matakuliah WHERE kode_matakuliah = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kodeMatakuliah);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Mata kuliah berhasil dihapus.");
            } else {
                System.out.println("Mata kuliah tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println(
                "Gagal menghapus mata kuliah. Periksa apakah mata kuliah masih diambil mahasiswa."
            );
            e.printStackTrace();
        }
    }
}