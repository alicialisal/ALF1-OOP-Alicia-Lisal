public class CD {
    private String IDCD;
    private String judul;
    private GenreChoice Genre;
    private String tahun_publikasi;
    private String rak;
    private int jumlahTersedia;
    private int jumlahTerpinjam;
    private int jumlahRusak;
    private int jumlahTotal;
    private int hargaDenda;

    public CD() {
    }

    public CD(String IDCD, String judul, GenreChoice kategori, String tahun_publikasi, String rak, int jumlahTersedia,
            int jumlahTerpinjam, int jumlahRusak, int jumlahTotal) {
        this.IDCD = IDCD;
        this.judul = judul;
        this.Genre = kategori;
        this.tahun_publikasi = tahun_publikasi;
        this.rak = rak;
        this.jumlahTersedia = jumlahTersedia;
        this.jumlahTerpinjam = jumlahTerpinjam;
        this.jumlahRusak = jumlahRusak;
        this.jumlahTotal = jumlahTotal;
    }

    public void inputData(String kodecd) {
        IDCD = kodecd;
        judul = Main.getInput("Masukkan Judul CD: ", false, false, false);
        Genre = getGenre1();
        tahun_publikasi = Main.getInputDate("Masukkan Tahun Publikasi ");
        rak = Main.getInput("Masukkan Rak: ", false, false, false);
        jumlahTersedia = Main.getInputInt("Masukkan Jumlah Tersedia: ");
        jumlahTerpinjam = Main.getInputInt("Masukkan Jumlah Terpinjam: ");
        jumlahRusak = Main.getInputInt("Masukkan Jumlah Rusak: ");
        jumlahTotal = jumlahTerpinjam + jumlahTersedia;
        System.out.println("Jumlah total: " + jumlahTotal);
    }

    GenreChoice getGenre1() {
        System.out.println("Pilih Genre:");
        for (GenreChoice genre : GenreChoice.values()) {
            System.out.println((genre.ordinal() + 1) + ". " + genre);
        }
        int choice = Main.getChoice(GenreChoice.values().length);
        return GenreChoice.values()[choice - 1];
    }

    public int getHargaDenda() {
        return this.hargaDenda;
    }

    public void setHargaDenda(int HargaBeli) {
        this.hargaDenda = HargaBeli;
    }

    public GenreChoice getGenre() {
        return this.Genre;
    }

    public String getIDCD() {
        return this.IDCD;
    }

    public void setIDCD(String IDCD) {
        this.IDCD = IDCD;
    }

    public String getJudul() {
        return this.judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public GenreChoice getKategori() {
        return this.Genre;
    }

    public void setGenre(GenreChoice Genre) {
        this.Genre = Genre;
    }

    public String getTahun_publikasi() {
        return this.tahun_publikasi;
    }

    public void setTahun_publikasi(String tahun_publikasi) {
        this.tahun_publikasi = tahun_publikasi;
    }

    public String getRak() {
        return this.rak;
    }

    public void setRak(String rak) {
        this.rak = rak;
    }

    public int getJumlahTersedia() {
        return this.jumlahTersedia;
    }

    public void setJumlahTersedia(int jumlahTersedia) {
        this.jumlahTersedia = jumlahTersedia;
    }

    public int getJumlahTerpinjam() {
        return this.jumlahTerpinjam;
    }

    public void setJumlahTerpinjam(int jumlahTerpinjam) {
        this.jumlahTerpinjam = jumlahTerpinjam;
    }

    public int getJumlahRusak() {
        return this.jumlahRusak;
    }

    public void setJumlahRusak(int jumlahRusak) {
        this.jumlahRusak = jumlahRusak;
    }

    public int getJumlahTotal() {
        return this.jumlahTotal;
    }

    public void setJumlahTotal(int jumlahTotal) {
        this.jumlahTotal = jumlahTotal;
    }

}

enum GenreChoice {
    Horor,
    Komedi,
    Romansa,
    Aksi,
    SciFi,
    Edukasi
}
