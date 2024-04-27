public class DetailPengembalian {
    private Pengembalian noPengembalian;
    private int jumlah;
    private Buku IDBuku;
    private CD IDCD;
    private StatusKembali statusrusak;

    public DetailPengembalian() {

    }

    public DetailPengembalian(Pengembalian noPengembalian, int jumlah, Buku IDBuku, CD IDCD, StatusKembali statuskmbl) {
        this.noPengembalian = noPengembalian;
        this.jumlah = jumlah;
        this.IDBuku = IDBuku;
        this.IDCD = IDCD;
        this.statusrusak = statuskmbl;
    }

    public void inputData(Pengembalian noKembali, Buku IDBuku) {

        this.noPengembalian = noKembali;
        this.IDBuku = IDBuku;
        System.out.println("Buku: " + IDBuku.getIDBuku() + " - " + IDBuku.getJudul());
        jumlah = 1;
        System.out.println("Jumlah pengembalian: 1");
        statusrusak = getStKembali1();
    }

    public void inputData(Pengembalian noKembali, CD IDCD) {

        this.noPengembalian = noKembali;
        this.IDCD = IDCD;
        System.out.println("CD: " + IDCD.getIDCD() + " - " + IDCD.getJudul());
        jumlah = 1;
        System.out.println("Jumlah pengembalian: 1");
        statusrusak = getStKembali1();
    }

    StatusKembali getStKembali1() {
        System.out.println("Pilih Status Kembali: ");
        for (StatusKembali statusKembali : StatusKembali.values()) {
            System.out.println((statusKembali.ordinal() + 1) + ". " + statusKembali);
        }
        int choice = Main.getChoice(StatusKembali.values().length);
        return StatusKembali.values()[choice - 1];
    }

    Pilihan getPilihan() {
        System.out.println("Pilih: ");
        for (Pilihan pilihan : Pilihan.values()) {
            System.out.println((pilihan.ordinal() + 1) + ". " + pilihan);
        }
        int choice = Main.getChoice(Pilihan.values().length);
        return Pilihan.values()[choice - 1];
    }

    public Pengembalian getNoPengembalian() {
        return this.noPengembalian;
    }

    public void setNoPengembalian(Pengembalian noPengembalian) {
        this.noPengembalian = noPengembalian;
    }

    public StatusKembali getStatusKembali() {
        return this.statusrusak;
    }

    public void setJumlah(StatusKembali statusKembali) {
        this.statusrusak = statusKembali;
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

enum StatusKembali {
    Rusak,
    Hilang,
    Tidak
}
