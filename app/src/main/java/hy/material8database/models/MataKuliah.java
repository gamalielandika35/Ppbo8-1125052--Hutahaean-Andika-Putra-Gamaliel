
package hy.material8database.models;

public class MataKuliah {

    public String kodeMatakuliah;
    public String nama;
    public int sks;
    public String kodeJurusan;

    public MataKuliah(
        String kodeMatakuliah,
        String nama,
        int sks,
        String kodeJurusan
    ) {
        this.kodeMatakuliah = kodeMatakuliah;
        this.nama = nama;
        this.sks = sks;
        this.kodeJurusan = kodeJurusan;
    }
}