public class Mahasiswa {
    private String NIM;
    private String nama;
    private String alamat;
    private String noHP;
    private Prodi prodi;
    private Fakultas fakultas;
    private String password;
    private int pDenda;
    private int totalDenda;

    public Mahasiswa() {
    }

    public Mahasiswa(String NIM, String nama, String alamat, String noHP, Prodi prodi, Fakultas fakultas, int pDenda,
            int totalDenda, String password) {
        this.NIM = NIM;
        this.nama = nama;
        this.password = password;
        this.alamat = alamat;
        this.noHP = noHP;
        this.prodi = prodi;
        this.fakultas = fakultas;
        this.pDenda = pDenda;
        this.totalDenda = totalDenda;
    }

    public void displayDetails() {
        System.out.println("===== Detail Mahasiswa =====");
        System.out.println("NIM: " + NIM);
        System.out.println("Nama: " + nama);
        System.out.println("Alamat: " + alamat);
        System.out.println("No HP: " + noHP);
        System.out.println("Prodi: " + prodi);
        System.out.println("Fakultas: " + fakultas);
    }

    public void inputData() {
        NIM = Main.getInput("Masukkan NIM: ", false, false, true);
        nama = Main.getInput("Masukkan Nama: ", false, false, false);
        password = Main.getInput("Masukkan Password: ", false, false, false);
        alamat = Main.getInput("Masukkan Alamat: ", false, false, false);
        noHP = Main.getInput("Masukkan No HP: ", true, false, false);
        prodi = getProdi1();
        fakultas = getFakultas1();
    }

    Prodi getProdi1() {
        System.out.println("Pilih Prodi:");
        for (Prodi prodi : Prodi.values()) {
            if (prodi != Prodi.STAFF)
                System.out.println((prodi.ordinal() + 1) + ". " + prodi);
        }
        int choice = Main.getChoice(Prodi.values().length);
        return Prodi.values()[choice - 1];
    }

    Fakultas getFakultas1() {
        System.out.println("Pilih Fakultas:");
        for (Fakultas fakultas : Fakultas.values()) {
            if (fakultas != Fakultas.STAFF)
                System.out.println((fakultas.ordinal() + 1) + ". " + fakultas);
        }
        int choice = Main.getChoice(Fakultas.values().length);
        return Fakultas.values()[choice - 1];
    }

    public Prodi getProdi() {
        return this.prodi;
    }

    public Fakultas getFakultas() {
        return this.fakultas;
    }

    public String getNIM() {
        return this.NIM;
    }

    public void setNIM(String NIK) {
        this.NIM = NIK;
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

    public int getPDenda() {
        return this.pDenda;
    }

    public void setPDenda(int pDenda) {
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