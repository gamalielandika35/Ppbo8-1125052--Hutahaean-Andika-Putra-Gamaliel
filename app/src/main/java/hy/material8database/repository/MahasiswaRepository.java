
package hy.material8database.repository;

import hy.material8database.database.Database;
import hy.material8database.models.Mahasiswa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaRepository {

    private static final Connection conn;

    static {
        conn = Database.connect();
    }

    // CREATE
    public static void addMahasiswa(Mahasiswa mhs) {
        if (conn == null) {
            System.out.println("Koneksi database tidak tersedia.");
            return;
        }

        String sql = "INSERT INTO mahasiswa "
                   + "(nim, nama, kode_jurusan) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mhs.nim);
            stmt.setString(2, mhs.nama);
            stmt.setString(3, mhs.kodeJurusan);

            stmt.executeUpdate();
            System.out.println("Mahasiswa berhasil ditambahkan.");

        } catch (SQLException e) {
            System.out.println("Gagal menambahkan mahasiswa: " + e.getMessage());
        }
    }

    // READ ALL
    public static List<Mahasiswa> getAllMahasiswa() {
        List<Mahasiswa> result = new ArrayList<>();

        if (conn == null) {
            System.out.println("Koneksi database tidak tersedia.");
            return result;
        }

        String sql = "SELECT nim, nama, kode_jurusan "
                   + "FROM mahasiswa ORDER BY nim";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(new Mahasiswa(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("kode_jurusan")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil mahasiswa: " + e.getMessage());
        }

        return result;
    }

    // READ BY NIM
    public static Mahasiswa getMahasiswaByNim(String nim) {
        if (conn == null) {
            System.out.println("Koneksi database tidak tersedia.");
            return null;
        }

        String sql = "SELECT nim, nama, kode_jurusan "
                   + "FROM mahasiswa WHERE nim = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nim);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Mahasiswa(
                        rs.getString("nim"),
                        rs.getString("nama"),
                        rs.getString("kode_jurusan")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal mencari mahasiswa: " + e.getMessage());
        }

        return null;
    }

    // UPDATE
    public static void updateMahasiswa(Mahasiswa mhs) {
        if (conn == null) {
            System.out.println("Koneksi database tidak tersedia.");
            return;
        }

        String sql = "UPDATE mahasiswa "
                   + "SET nama = ?, kode_jurusan = ? WHERE nim = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mhs.nama);
            stmt.setString(2, mhs.kodeJurusan);
            stmt.setString(3, mhs.nim);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Mahasiswa berhasil diubah.");
            } else {
                System.out.println("Mahasiswa tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengubah mahasiswa: " + e.getMessage());
        }
    }

    // DELETE
    public static void deleteMahasiswa(String nim) {
        if (conn == null) {
            System.out.println("Koneksi database tidak tersedia.");
            return;
        }

        String sql = "DELETE FROM mahasiswa WHERE nim = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nim);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Mahasiswa berhasil dihapus.");
            } else {
                System.out.println("Mahasiswa tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal menghapus mahasiswa: " + e.getMessage());
        }
    }

    // AMBIL MATA KULIAH
    public static void addMatkul(String nim, String kodeMatakuliah) {
        if (conn == null) {
            System.out.println("Koneksi database tidak tersedia.");
            return;
        }

        String sql = "INSERT INTO mahasiswa_matakuliah "
                   + "(nim, kode_matakuliah, indeks_nilai) "
                   + "VALUES (?, ?, NULL)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nim);
            stmt.setString(2, kodeMatakuliah);

            stmt.executeUpdate();
            System.out.println("Mata kuliah berhasil diambil.");

        } catch (SQLException e) {
            System.out.println("Gagal mengambil mata kuliah: " + e.getMessage());
        }
    }
}