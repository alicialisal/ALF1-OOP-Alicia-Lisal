public class Pustakawan {
    private String IDPustakawan;
    private String Nama;
    private String Alamat;
    private String NoHP;
    private char jenisKelamin;
    private String Email;
    private String Password;

    public Pustakawan() {
    }

    public Pustakawan(String IDPustakawan, String Nama, String Alamat, String NoHP, char jenisKelamin, String Email,
            String Password) {
        this.IDPustakawan = IDPustakawan;
        this.Nama = Nama;
        this.Email = Email;
        this.Password = Password;
        this.Alamat = Alamat;
        this.NoHP = NoHP;
        this.jenisKelamin = jenisKelamin;
    }

    public void displayDetails() {
        System.out.println("===== Detail Pustakawan =====");
        System.out.println("NIM: " + IDPustakawan);
        System.out.println("Nama: " + Nama);
        System.out.println("Alamat: " + Alamat);
        System.out.println("No HP: " + NoHP);
    }

    public void inputData(String kodepust) {
        IDPustakawan = kodepust;
        System.out.println("===== Buat Akun Pustakawan =====");
        Nama = Main.getInput("Masukkan Nama: ", false, false, false);
        Alamat = Main.getInput("Masukkan Alamat: ", false, false, false);
        NoHP = Main.getInput("Masukkan No HP: ", true, false, false);
        jenisKelamin = Main.getInputJK("Masukkan Jenis Kelamin: ").charAt(0);
        Email = Main.getInput("Masukkan Email: ", false, false, false);
        Password = Main.getInput("Masukkan Password: ", false, false, false);
    }

    public String getIDPustakawan() {
        return this.IDPustakawan;
    }

    public void setIDPustakawan(String IDPustakawan) {
        this.IDPustakawan = IDPustakawan;
    }

    public String getNama() {
        return this.Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }

    public String getAlamat() {
        return this.Alamat;
    }

    public void setAlamat(String Alamat) {
        this.Alamat = Alamat;
    }

    public String getNoHP() {
        return this.NoHP;
    }

    public void setNoHP(String NoHP) {
        this.NoHP = NoHP;
    }

    public char getJenisKelamin() {
        return this.jenisKelamin;
    }

    public void setJenisKelamin(char jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

}
