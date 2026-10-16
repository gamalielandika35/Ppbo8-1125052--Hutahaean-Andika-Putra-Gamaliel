package hy.material8database;

import hy.material8database.models.Mahasiswa;
import hy.material8database.repository.MahasiswaRepository;
import java.util.List;
import java.util.Scanner;
import hy.material8database.models.jurusan;
import hy.material8database.models.MataKuliah;
import hy.material8database.repository.JurusanRepository;
import hy.material8database.repository.MataKuliahRepository;
import hy.material8database.repository.MahasiswaMatakuliahRepository;

public class App {

    private Scanner scanner = new Scanner(System.in);

   public App() {
    menuUtama();
}

    private void addNewMahasiswa() {
        System.out.print("Masukkan NIM: ");
        String nim = scanner.nextLine();

        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();

        Mahasiswa mhs = new Mahasiswa(nim, nama);
        MahasiswaRepository.addMahasiswa(mhs);
    }

    private void printAllMahasiswa() {
        List<Mahasiswa> allMahasiswa = MahasiswaRepository.getAllMahasiswa();
        System.out.println("\n📋 Daftar Mahasiswa:");
        for (Mahasiswa m : allMahasiswa) {
            System.out.println(m.nim + " - " + m.nama);
        }
    }

    private void menuUtama() {
    boolean jalan = true;

    while (jalan) {
        System.out.println("\nMenu:");
        System.out.println("1. mahasiswa");
        System.out.println("2. jurusan");
        System.out.println("3. mata kuliah");
        System.out.println("0. keluar");

        int pilihan = bacaAngka("Pilih menu: ");

        switch (pilihan) {
            case 1:
                menuMahasiswa();
                break;
            case 2:
                menuJurusan();
                break;
            case 3:
                menuMataKuliah();
                break;
            case 0:
                jalan = false;
                System.out.println("Program selesai.");
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }
}

private int bacaAngka(String pesan) {
    while (true) {
        System.out.print(pesan);
        String input = scanner.nextLine();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Masukkan angka yang valid.");
        }
    }
}
private void deleteMahasiswa() {
    System.out.print("Masukkan NIM mahasiswa: ");
    String nim = scanner.nextLine().trim();

    Mahasiswa mhs = MahasiswaRepository.getMahasiswaByNim(nim);

    if (mhs == null) {
        System.out.println("Mahasiswa tidak ditemukan.");
        return;
    }

    System.out.print("Yakin ingin menghapus " + mhs.nama + "? (y/n): ");
    String jawaban = scanner.nextLine().trim();

    if (jawaban.equalsIgnoreCase("y")) {
        MahasiswaRepository.deleteMahasiswa(nim);
        printAllMahasiswa();
    } else {
        System.out.println("Penghapusan dibatalkan.");
    }
}

private void menuMahasiswa() {
    boolean balek = false;
    
    while (!balek) {
        System.out.println("\nMENU MAHASISWA =====");
        System.out.println("1. View all");
        System.out.println("2. Create");
        System.out.println("3. Edit");
        System.out.println("4. Delete");
        System.out.println("5. Ambil matkul");
        System.out.println("0. Kembali");

        int pilihan = bacaAngka("Pilih menu: ");

        switch (pilihan) {
            case 1:
                printAllMahasiswa();
                break;
            case 2:
                addNewMahasiswa();
                printAllMahasiswa();
                break;
            case 3:
                editMahasiswa();
                break;
            case 4:
                deleteMahasiswa();
                break;
            case 5:
                ambilMatkul();
                break;
            case 0:
                balek = true;
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }
}

private void editMahasiswa() {
    System.out.println("\n===== EDIT MAHASISWA =====");

    System.out.print("Masukkan NIM: ");
    String nim = scanner.nextLine().trim();

    Mahasiswa mhs = MahasiswaRepository.getMahasiswaByNim(nim);

    if (mhs == null) {
        System.out.println("Mahasiswa tidak ditemukan.");
        return;
    }

    System.out.println("Nama sekarang: " + mhs.nama);
    System.out.print("Masukkan nama baru: ");
    String nama = scanner.nextLine().trim();

    if (nama.isBlank()) {
        System.out.println("Nama tidak boleh kosong.");
        return;
    }

    mhs.nama = nama;
    MahasiswaRepository.updateMahasiswa(mhs);
    printAllMahasiswa();
}

private void ambilMatkul() {
    System.out.println("\n===== AMBIL MATA KULIAH =====");

    printAllMahasiswa();

    System.out.println("\nDaftar Mata Kuliah:");

    List<MataKuliah> daftar =
        MataKuliahRepository.getAllMataKuliah();

    if (daftar.isEmpty()) {
        System.out.println("Belum ada mata kuliah.");
        return;
    }

    for (MataKuliah mk : daftar) {
        System.out.println(
            mk.kodeMatakuliah + " - " + mk.nama
            + " (" + mk.sks + " SKS)"
        );
    }

    System.out.print("\nMasukkan NIM mahasiswa: ");
    String nim = scanner.nextLine().trim();

    Mahasiswa mhs = MahasiswaRepository.getMahasiswaByNim(nim);

    if (mhs == null) {
        System.out.println("Mahasiswa tidak ditemukan.");
        return;
    }

    System.out.print("Masukkan kode mata kuliah: ");
    String kode = scanner.nextLine().trim().toUpperCase();

    boolean ditemukan = false;

    for (MataKuliah mk : daftar) {
        if (mk.kodeMatakuliah.equalsIgnoreCase(kode)) {
            ditemukan = true;
            break;
        }
    }

    if (!ditemukan) {
        System.out.println("Mata kuliah tidak ditemukan.");
        return;
    }

    MahasiswaMatakuliahRepository.addMatkul(nim, kode);
}

private void menuJurusan() {
    boolean kembali = false;

    while (!kembali) {
        System.out.println("\n===== MENU JURUSAN =====");
        System.out.println("1. View all");
        System.out.println("2. Create");
        System.out.println("3. Edit");
        System.out.println("4. Delete");
        System.out.println("0. Kembali");

        int pilihan = bacaAngka("Pilih menu: ");

        switch (pilihan) {
            case 1:
                viewJurusan();
                break;
            case 2:
                createJurusan();
                break;
            case 3:
                editJurusan();
                break;
            case 4:
                deleteJurusan();
                break;
            case 0:
                kembali = true;
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }
}

private void viewJurusan() {
    List<jurusan> data = JurusanRepository.getAllJurusan();

    System.out.println("\n===== DAFTAR JURUSAN =====");

    if (data.isEmpty()) {
        System.out.println("Belum ada data jurusan.");
        return;
    }

    for (jurusan j : data) {
        System.out.println(j.kodeJurusan + " - " + j.nama);
    }
}

private void createJurusan() {
    System.out.println("\n===== TAMBAH JURUSAN =====");

    System.out.print("Kode jurusan: ");
    String kode = scanner.nextLine().trim().toUpperCase();

    System.out.print("Nama jurusan: ");
    String nama = scanner.nextLine().trim();

    if (kode.isBlank() || nama.isBlank()) {
        System.out.println("Kode dan nama jurusan wajib diisi.");
        return;
    }

    JurusanRepository.addJurusan(new jurusan(kode, nama));
}

private void editJurusan() {
    System.out.println("\n===== EDIT JURUSAN =====");

    System.out.print("Kode jurusan yang ingin diedit: ");
    String kode = scanner.nextLine().trim().toUpperCase();

    jurusan ditemukan = null;

    for (jurusan j : JurusanRepository.getAllJurusan()) {
        if (j.kodeJurusan.equalsIgnoreCase(kode)) {
            ditemukan = j;
            break;
        }
    }

    if (ditemukan == null) {
        System.out.println("Jurusan tidak ditemukan.");
        return;
    }

    System.out.println("Nama sekarang: " + ditemukan.nama);
    System.out.print("Nama baru: ");
    String nama = scanner.nextLine().trim();

    if (nama.isBlank()) {
        System.out.println("Nama baru tidak boleh kosong.");
        return;
    }

    ditemukan.nama = nama;
    JurusanRepository.updateJurusan(ditemukan);
}

private void deleteJurusan() {
    System.out.println("\n===== HAPUS JURUSAN =====");

    System.out.print("Kode jurusan yang ingin dihapus: ");
    String kode = scanner.nextLine().trim().toUpperCase();

    boolean ditemukan = false;

    for (jurusan j : JurusanRepository.getAllJurusan()) {
        if (j.kodeJurusan.equalsIgnoreCase(kode)) {
            ditemukan = true;
            break;
        }
    }

    if (!ditemukan) {
        System.out.println("Jurusan tidak ditemukan.");
        return;
    }

    System.out.print("Yakin ingin menghapus? (y/n): ");
    String jawaban = scanner.nextLine().trim();

    if (jawaban.equalsIgnoreCase("y")) {
        JurusanRepository.deleteJurusan(kode);
    } else {
        System.out.println("Penghapusan dibatalkan.");
    }
}

private void menuMataKuliah() {
    boolean kembali = false;

    while (!kembali) {
        System.out.println("\n===== MENU MATA KULIAH =====");
        System.out.println("1. View all");
        System.out.println("2. Create");
        System.out.println("3. Edit");
        System.out.println("4. Delete");
        System.out.println("0. Kembali");

        int pilihan = bacaAngka("Pilih menu: ");

        switch (pilihan) {
            case 1:
                viewMataKuliah();
                break;
            case 2:
                createMataKuliah();
                break;
            case 3:
                editMataKuliah();
                break;
            case 4:
                deleteMataKuliah();
                break;
            case 0:
                kembali = true;
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }
}

private void viewMataKuliah() {
    List<MataKuliah> data =
        MataKuliahRepository.getAllMataKuliah();

    System.out.println("\n===== DAFTAR MATA KULIAH =====");

    if (data.isEmpty()) {
        System.out.println("Belum ada data mata kuliah.");
        return;
    }

    for (MataKuliah mk : data) {
        System.out.println(
            mk.kodeMatakuliah + " - " + mk.nama
            + " | " + mk.sks + " SKS"
            + " | Jurusan: "
            + (mk.kodeJurusan == null ? "-" : mk.kodeJurusan)
        );
    }
}

private void createMataKuliah() {
    System.out.println("\n===== TAMBAH MATA KULIAH =====");

    System.out.print("Kode mata kuliah: ");
    String kode = scanner.nextLine().trim().toUpperCase();

    System.out.print("Nama mata kuliah: ");
    String nama = scanner.nextLine().trim();

    if (kode.isBlank() || nama.isBlank()) {
        System.out.println("Kode dan nama mata kuliah wajib diisi.");
        return;
    }

    int sks = bacaAngka("Jumlah SKS: ");

    if (sks <= 0) {
        System.out.println("SKS harus lebih dari 0.");
        return;
    }

    System.out.print("Kode jurusan (IF/SI, kosong jika tidak ada): ");
    String kodeJurusan = scanner.nextLine().trim().toUpperCase();

    if (kodeJurusan.isBlank()) {
        kodeJurusan = null;
    }

    MataKuliah mk = new MataKuliah(
        kode, nama, sks, kodeJurusan
    );

    MataKuliahRepository.addMataKuliah(mk);
}

private void editMataKuliah() {
    System.out.println("\n===== EDIT MATA KULIAH =====");

    System.out.print("Kode mata kuliah: ");
    String kode = scanner.nextLine().trim().toUpperCase();

    MataKuliah ditemukan = null;

    for (MataKuliah mk : MataKuliahRepository.getAllMataKuliah()) {
        if (mk.kodeMatakuliah.equalsIgnoreCase(kode)) {
            ditemukan = mk;
            break;
        }
    }

    if (ditemukan == null) {
        System.out.println("Mata kuliah tidak ditemukan.");
        return;
    }

    System.out.println("Nama sekarang: " + ditemukan.nama);
    System.out.print("Nama baru (Enter untuk tetap): ");
    String nama = scanner.nextLine().trim();

    if (!nama.isBlank()) {
        ditemukan.nama = nama;
    }

    System.out.println("SKS sekarang: " + ditemukan.sks);
    System.out.print("SKS baru (Enter untuk tetap): ");
    String inputSks = scanner.nextLine().trim();

    if (!inputSks.isBlank()) {
        try {
            int sks = Integer.parseInt(inputSks);

            if (sks <= 0) {
                System.out.println("SKS harus lebih dari 0.");
                return;
            }

            ditemukan.sks = sks;
        } catch (NumberFormatException e) {
            System.out.println("SKS harus berupa angka.");
            return;
        }
    }

    System.out.println("Jurusan sekarang: " + ditemukan.kodeJurusan);
    System.out.print("Jurusan baru (Enter untuk tetap): ");
    String jurusan = scanner.nextLine().trim();

    if (!jurusan.isBlank()) {
        ditemukan.kodeJurusan = jurusan.toUpperCase();
    }

    MataKuliahRepository.updateMataKuliah(ditemukan);
}

private void deleteMataKuliah() {
    System.out.println("\n===== HAPUS MATA KULIAH =====");

    System.out.print("Kode mata kuliah: ");
    String kode = scanner.nextLine().trim().toUpperCase();

    boolean ditemukan = false;

    for (MataKuliah mk : MataKuliahRepository.getAllMataKuliah()) {
        if (mk.kodeMatakuliah.equalsIgnoreCase(kode)) {
            ditemukan = true;
            break;
        }
    }

    if (!ditemukan) {
        System.out.println("Mata kuliah tidak ditemukan.");
        return;
    }

    System.out.print("Yakin ingin menghapus? (y/n): ");
    String jawaban = scanner.nextLine().trim();

    if (jawaban.equalsIgnoreCase("y")) {
        MataKuliahRepository.deleteMataKuliah(kode);
    } else {
        System.out.println("Penghapusan dibatalkan.");
    }
}

    public static void main(String[] args) {
        new App();
    }
}

