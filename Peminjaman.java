import java.time.LocalDate;
import java.util.ArrayList;

class Peminjaman {
    private String noPinjam;
    private Mahasiswa NIM;
    private Dosen_Staff NIK;
    private String tglPinjam;
    private String dueDate;
    private Pustakawan IDPustakawan;
    private Status status;

    public Peminjaman() {
    }

    public Peminjaman(String noPinjam, Mahasiswa NIM, Dosen_Staff NIK, String tglPinjam, String dueDate,
            Pustakawan IDPustakawan, Status status) {
        this.noPinjam = noPinjam;
        this.NIM = NIM;
        this.NIK = NIK;
        this.tglPinjam = tglPinjam;
        this.dueDate = dueDate;
        this.IDPustakawan = IDPustakawan;
        this.status = status;
    }

    public void inputDataUser(String noPinjam, Mahasiswa mhs, Status status) {
        this.noPinjam = noPinjam;
        tglPinjam = Main.TimeNow();
        dueDate = String.valueOf(LocalDate.parse(tglPinjam).plusWeeks(2));
        System.out.println("Tanggal Harus Kembali: " + dueDate);
        NIM = mhs;
        this.status = status;
    }

    public void inputDataUser(String noPinjam, Dosen_Staff dosen, Status status) {
        this.noPinjam = noPinjam;
        tglPinjam = Main.TimeNow();
        dueDate = String.valueOf(LocalDate.parse(tglPinjam).plusWeeks(2));
        System.out.println("Tanggal Harus Kembali: " + dueDate);
        NIK = dosen;
        this.status = status;
    }

    public void inputData(String noPinjam, Pustakawan pustakawan, ArrayList<Mahasiswa> mhsList,
            ArrayList<Dosen_Staff> dosenList, Status status) {
        int choice;
        this.noPinjam = noPinjam;
        tglPinjam = Main.TimeNow();
        System.out.println("Masukkan Tanggal Pinjam: " + tglPinjam);

        IDPustakawan = pustakawan;

        UserType pilihan = getPilihan();
        if (pilihan == UserType.MAHASISWA) {
            System.out.println("Daftar Mahasiswa:");
            for (int i = 0; i < mhsList.size(); i++) {
                System.out.println((i + 1) + ". " + mhsList.get(i).getNIM() + " - "
                        + mhsList.get(i).getNama());
            }

            choice = Main.getChoice(mhsList.size());
            NIM = mhsList.get(choice - 1);
        } else if (pilihan == UserType.DOSEN) {
            System.out.println("Daftar Dosen:");
            for (int i = 0; i < dosenList.size(); i++) {
                System.out.println((i + 1) + ". " + dosenList.get(i).getNIK() + " - "
                        + dosenList.get(i).getNama());
            }

            choice = Main.getChoice(dosenList.size());
            NIK = dosenList.get(choice - 1);
        }

        dueDate = String.valueOf(LocalDate.parse(tglPinjam).plusWeeks(2));
        System.out.println("Tanggal Harus Kembali: " + dueDate);

        this.status = status;
    }

    UserType getPilihan() {
        System.out.println("Pilih: ");
        for (UserType pilihan : UserType.values()) {
            if (pilihan != UserType.PUSTAKAWAN)
                System.out.println((pilihan.ordinal() + 1) + ". " + pilihan);
        }
        int choice = Main.getChoice(UserType.values().length);
        return UserType.values()[choice - 1];
    }

    public String getNoPinjam() {
        return this.noPinjam;
    }

    public void setNoPinjam(String noPinjam) {
        this.noPinjam = noPinjam;
    }

    public Mahasiswa getNIM() {
        return this.NIM;
    }

    public void setNIM(Mahasiswa NIM) {
        this.NIM = NIM;
    }

    public Dosen_Staff getNIK() {
        return this.NIK;
    }

    public void setNIK(Dosen_Staff NIK) {
        this.NIK = NIK;
    }

    public String getTglPinjam() {
        return this.tglPinjam;
    }

    public void setTglPinjam(String tglPinjam) {
        this.tglPinjam = tglPinjam;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Pustakawan getIDPustakawan() {
        return this.IDPustakawan;
    }

    public void setIDPustakawan(Pustakawan IDPustakawan) {
        this.IDPustakawan = IDPustakawan;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}

enum Status {
    WAITING,
    APPROVED
}
