public class DetailPeminjaman {
    private Peminjaman noPinjam;
    private int jumlah;
    private Buku IDBuku;
    private CD IDCD;

    public DetailPeminjaman() {
    }

    public DetailPeminjaman(Peminjaman noPinjam, int jumlah, Buku IDBuku, CD IDCD) {
        this.noPinjam = noPinjam;
        this.jumlah = jumlah;
        this.IDBuku = IDBuku;
        this.IDCD = IDCD;
    }

    public void inputData(Peminjaman noPinjam, Buku IDBuku) {

        this.noPinjam = noPinjam;
        this.IDBuku = IDBuku;
        jumlah = 1;
    }

    public void inputData(Peminjaman noPinjam, CD IDCD) {

        this.noPinjam = noPinjam;
        this.IDCD = IDCD;
        jumlah = 1;
    }

    Pilihan getPilihan() {
        System.out.println("Pilih: ");
        for (Pilihan pilihan : Pilihan.values()) {
            System.out.println((pilihan.ordinal() + 1) + ". " + pilihan);
        }
        int choice = Main.getChoice(Pilihan.values().length);
        return Pilihan.values()[choice - 1];
    }

    public Peminjaman getNoPinjam() {
        return this.noPinjam;
    }

    public void setNoPinjam(Peminjaman noPinjam) {
        this.noPinjam = noPinjam;
    }

    public int getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public Buku getIDBuku() {
        return this.IDBuku;
    }

    public void setIDBuku(Buku IDBuku) {
        this.IDBuku = IDBuku;
    }

    public CD getIDCD() {
        return this.IDCD;
    }

    public void setIDCD(CD IDCD) {
        this.IDCD = IDCD;
    }

}

enum Pilihan {
    Buku,
    CD
}
