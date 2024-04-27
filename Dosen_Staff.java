public class Dosen_Staff {
    private String nama;
    private String alamat;
    private String NIK;
    private String noHP;
    private Prodi prodi;
    private Fakultas fakultas;
    private boolean pDenda;
    private int totalDenda;
    private String password;

    public Dosen_Staff() {
    }

    public Dosen_Staff(String NIK, String nama, String alamat, String noHP, Prodi prodi, Fakultas fakultas,
            boolean pDenda, int totalDenda, String password) {
        this.NIK = NIK;
        this.nama = nama;
        this.alamat = alamat;
        this.noHP = noHP;
        this.prodi = prodi;
        this.fakultas = fakultas;
        this.pDenda = pDenda;
        this.totalDenda = totalDenda;
        this.password = password;
    }

    public void inputData() {
        NIK = Main.getInput("Masukkan NIK: ", false, false, true);
        nama = Main.getInput("Masukkan Nama: ", false, false, false);
        password = Main.getInput("Masukkan Password: ", false, false, false);
        alamat = Main.getInput("Masukkan Alamat: ", false, false, false);
        noHP = Main.getInput("Masukkan No HP: ", true, false, false);
        prodi = getProdi1();
        fakultas = getFakultas1();
    }

    Prodi getProdi1() {
        System.out.println("Pilih Prodi: ");
        for (Prodi prodi : Prodi.values()) {
            System.out.println((prodi.ordinal() + 1) + ". " + prodi);
        }
        int choice = Main.getChoice(Prodi.values().length);
        return Prodi.values()[choice - 1];
    }

    public Prodi getProdi() {
        return this.prodi;
    }

    public Fakultas getFakultas() {
        return this.fakultas;
    }

    Fakultas getFakultas1() {
        System.out.println("Pilih Fakultas: ");
        for (Fakultas fakultas : Fakultas.values()) {
            System.out.println((fakultas.ordinal() + 1) + ". " + fakultas);
        }
        int choice = Main.getChoice(Fakultas.values().length);
        return Fakultas.values()[choice - 1];
    }

    public String getNIK() {
        return this.NIK;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return this.alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHP() {
        return this.noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public void setProdi(Prodi prodi) {
        this.prodi = prodi;
    }

    public void setFakultas(Fakultas fakultas) {
        this.fakultas = fakultas;
    }

    public boolean getPDenda() {
        return this.pDenda;
    }

    public void setPDenda(boolean pDenda) {
        this.pDenda = pDenda;
    }

    public int getTotalDenda() {
        return this.totalDenda;
    }

    public void setTotalDenda(int totalDenda) {
        this.totalDenda = totalDenda;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

enum Prodi {
    AD,
    AI,
    IBM,
    ILX,
    STAFF
}

enum Fakultas {
    IMT,
    VCD,
    MAN,
    STAFF
}