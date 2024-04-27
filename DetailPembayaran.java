public class DetailPembayaran {
    private Pengembalian noPengembalian;
    private int jumlah;
    private int jumlah_denda;
    private JenisDenda jenisDenda;
    private Buku IDBuku;
    private CD IDCD;

    public DetailPembayaran() {
    }

    public DetailPembayaran(Pengembalian noPengembalian, int jumlah, int jumlah_denda, JenisDenda jenisDenda,
            Buku IDBuku, CD IDCD) {
        this.noPengembalian = noPengembalian;
        this.jumlah = jumlah;
        this.jumlah_denda = jumlah_denda;
        this.jenisDenda = jenisDenda;
        this.IDBuku = IDBuku;
        this.IDCD = IDCD;
    }

    public void inputData(Pengembalian noKembali, int biayadenda, JenisDenda jenisDenda, Object IDBukuCD) {
        this.noPengembalian = noKembali;
        jumlah = 1;
        this.jenisDenda = jenisDenda;
        if (IDBukuCD instanceof Buku)
            this.IDBuku = (Buku) IDBukuCD;
        else if (IDBukuCD instanceof CD)
            this.IDCD = (CD) IDBukuCD;
        jumlah_denda = biayadenda;
    }

    JenisDenda getJenisDenda1() {
        System.out.println("Rusak/Tidak ? : ");
        for (JenisDenda jenisDenda : JenisDenda.values()) {
            System.out.println((jenisDenda.ordinal() + 1) + ". " + jenisDenda);
        }
        int choice = Main.getChoice(JenisDenda.values().length);
        return JenisDenda.values()[choice - 1];
    }

    public Pengembalian getNoPengembalian() {
        return this.noPengembalian;
    }

    public void setNoPengembalian(Pengembalian noPengembalian) {
        this.noPengembalian = noPengembalian;
    }

    public int getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getJumlah_denda() {
        return this.jumlah_denda;
    }

    public void setJumlah_denda(int jumlah_denda) {
        this.jumlah_denda = jumlah_denda;
    }

    public JenisDenda getJenisDenda() {
        return this.jenisDenda;
    }

    public void setJenisDenda(JenisDenda jenisDenda) {
        this.jenisDenda = jenisDenda;
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

enum JenisDenda {
    Rusak,
    Hilang,
    Telat
}