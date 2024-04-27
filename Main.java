import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static ArrayList<Mahasiswa> usermhs = new ArrayList<>();
    private static ArrayList<Dosen_Staff> userdosen = new ArrayList<>();
    private static ArrayList<Pustakawan> listpustakawan = new ArrayList<>();
    private static ArrayList<CD> listcd = new ArrayList<>();
    private static ArrayList<Buku> daftarBuku = new ArrayList<>();
    private static ArrayList<Peminjaman> dafPeminjaman = new ArrayList<>();
    private static ArrayList<DetailPeminjaman> dafDetPeminjaman = new ArrayList<>();
    private static ArrayList<Pengembalian> dafPengembalian = new ArrayList<>();
    private static ArrayList<DetailPengembalian> dafDetPengembalian = new ArrayList<>();
    private static ArrayList<DetailPembayaran> dafDetPembayaran = new ArrayList<>();
    public static Pustakawan pustaktif;
    public static Object useraktif;
    // private static ArrayList<DetailPeminjaman> dafDetPeminjaman = new
    // ArrayList<>();
    private static Scanner myScanner = new Scanner(System.in);

    public static String TimeNow() {
        LocalDate tanggalPinjam = LocalDate.now();
        return String.valueOf(tanggalPinjam);
    }

    public static String autokodePust(ArrayList<Pustakawan> pustakawan) {
        if (pustakawan.isEmpty()) {
            return "PUST" + String.format("%03d", 1);
        } else {
            String lastID = pustakawan.get(pustakawan.size() - 1).getIDPustakawan();
            int lastNumber = Integer.parseInt(lastID.substring(4));
            lastNumber++;
            String formattedNumber = String.format("%03d", lastNumber);
            return "PUST" + formattedNumber;
        }
    }

    public static String autokodeMhs(ArrayList<Mahasiswa> mhs) {
        if (mhs.isEmpty()) {
            return "MHS" + String.format("%03d", 1);
        } else {
            String lastID = mhs.get(mhs.size() - 1).getNIM();
            int lastNumber = Integer.parseInt(lastID.substring(4));
            lastNumber++;
            String formattedNumber = String.format("%03d", lastNumber);
            return "MHS" + formattedNumber;
        }
    }

    public static String autokodeDosen(ArrayList<Dosen_Staff> dosen) {
        if (dosen.isEmpty()) {
            return "DST" + String.format("%03d", 1);
        } else {
            String lastID = dosen.get(dosen.size() - 1).getNIK();
            int lastNumber = Integer.parseInt(lastID.substring(4));
            lastNumber++;
            String formattedNumber = String.format("%03d", lastNumber);
            return "DST" + formattedNumber;
        }
    }

    public static String autokodeCD(ArrayList<CD> listcd) {
        if (listcd.isEmpty()) {
            return "CD" + String.format("%03d", 1);
        } else {
            String lastID = listcd.get(listcd.size() - 1).getIDCD();
            int lastNumber = Integer.parseInt(lastID.substring(2));
            lastNumber++;
            String formattedNumber = String.format("%03d", lastNumber);
            return "CD" + formattedNumber;
        }
    }

    public static String autokodeBuku(ArrayList<Buku> listbuku) {
        if (listbuku.isEmpty()) {
            return "BUKU" + String.format("%03d", 1);
        } else {
            String lastID = listbuku.get(listbuku.size() - 1).getIDBuku();
            int lastNumber = Integer.parseInt(lastID.substring(4));
            lastNumber++;
            String formattedNumber = String.format("%03d", lastNumber);
            return "BUKU" + formattedNumber;
        }
    }

    public static String autoNoTransaksi(String prefix) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String tanggal = dateFormat.format(new Date());
        int maxNum = 0;
        if (prefix == "PNJM") {
            for (Peminjaman kode : dafPeminjaman) {
                if (kode.getNoPinjam().startsWith(prefix + tanggal)) {
                    int num = Integer.parseInt(kode.getNoPinjam().substring(prefix.length() + 6));
                    if (num > maxNum) {
                        maxNum = num;
                    }
                }
            }
        } else {
            for (Pengembalian kode : dafPengembalian) {
                if (kode.getNoKembali().startsWith(prefix + tanggal)) {
                    int num = Integer.parseInt(kode.getNoKembali().substring(prefix.length() + 6));
                    if (num > maxNum) {
                        maxNum = num;
                    }
                }
            }
        }
        return String.format("%s%s%03d", prefix, tanggal, maxNum + 1);

    }

    public static String getInputDate(String message) {
        try {
            System.out.print(message + "(yyyy-MM-dd): ");
            String input = myScanner.nextLine().trim();

            LocalDate.parse(input);

            return String.valueOf(input);
        } catch (DateTimeParseException e) {
            return getInputDate(message);
        }
    }

    public static String getInput(String message, boolean notelp, boolean yesno, boolean NIM, boolean keluar) {
        try {
            System.out.print(message);
            String input = myScanner.nextLine().trim();

            if (notelp != true && NIM != true && keluar != true) {
                if (input.matches("\\d+")) {
                    throw new IllegalArgumentException("Input tidak boleh hanya berisi angka.");
                }
            } else if (notelp == true || NIM == true) {
                if (!Pattern.matches("[\\p{Alnum}]+", input)) {
                    System.out.println("Input mengandung simbol. Harap masukkan bilangan bulat.");
                    return getInput(message, notelp, yesno, NIM, keluar);
                }

                if (Pattern.matches(".*[a-zA-Z].*", input)) {
                    System.out.println("Input mengandung huruf alfabet. Harap masukkan bilangan bulat.");
                    return getInput(message, notelp, yesno, NIM, keluar);
                }

                if (input.length() > 13) {
                    System.out.println("No. Telp maksimal hanya 13 angka.");
                    return getInput(message, notelp, yesno, NIM, keluar);
                }
            } else if (keluar == true) {
                if (input.matches("\\d+") && input != "0") {
                    throw new IllegalArgumentException("Input tidak valid.");
                }
            }

            if (input.isEmpty()) {
                System.out.println("Input tidak boleh kosong. Silakan coba lagi.");
                return getInput(message, notelp, yesno, NIM, keluar);
            }

            if (yesno == true) {
                if (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    System.out.println("Tolong isi hanya Y / N.");
                    return getInput(message, notelp, yesno, NIM, keluar);
                }
            }

            return input;
        } catch (IllegalArgumentException ex) {
            System.out.println("Terjadi kesalahan: " + ex.getMessage());
            return getInput(message, notelp, yesno, NIM, keluar);
        } catch (Exception ex) {
            System.out.println("Terjadi kesalahan: " + ex.getMessage());
            return getInput(message, notelp, yesno, NIM, keluar);
        }
    }

    public static String getInputJK(String message) {
        try {
            System.out.print(message);
            String input = myScanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input tidak boleh kosong. Silakan coba lagi.");
                return getInputJK(message);
            }

            if (!input.equalsIgnoreCase("m") && !input.equalsIgnoreCase("f")) {
                System.out.println("Tolong isi hanya M / F.");
                return getInputJK(message);
            }

            return input.toUpperCase();
        } catch (Exception ex) {
            System.out.println("Terjadi kesalahan: " + ex.getMessage());
            return getInputJK(message);
        }
    }

    public static int getInputInt(String message) {
        try {
            System.out.print(message);
            String inputStr = myScanner.nextLine().trim();
            if (inputStr.isEmpty()) {
                System.out.println("Input tidak boleh kosong. Silakan coba lagi.");
                return getInputInt(message);
            }

            if (inputStr.contains(".") || inputStr.contains(",")) {
                System.out.println("Input tidak valid. Harap masukkan bilangan bulat.");
                return getInputInt(message);
            }

            Integer input = Integer.parseInt(inputStr);

            if (input < 0) {
                System.out.println("Input tidak boleh kurang dari atau sama dengan 0. Silakan coba lagi.");
                return getInputInt(message);
            }

            return input;
        } catch (NumberFormatException ex) {
            System.out.println("Input tidak valid. Harap masukkan bilangan bulat.");
            return getInputInt(message);
        } catch (Exception ex) {
            System.out.println("Terjadi kesalahan: " + ex.getMessage());
            return getInputInt(message);
        }
    }

    public static float getInputFloat(String message) {
        try {
            System.out.print(message);
            String inputStr = myScanner.nextLine().trim();
            if (inputStr.isEmpty()) {
                System.out.println("Input tidak boleh kosong. Silakan coba lagi.");
                return getInputFloat(message);
            }

            if (inputStr.contains(",")) {
                System.out.println("Simbol untuk bilangan desimal adalah '.', bukan ','");
                return getInputFloat(message);
            }

            Float input = Float.parseFloat(inputStr);

            if (input <= 0) {
                System.out.println("Input tidak boleh kurang dari atau sama dengan 0. Silakan coba lagi.");
                return getInputFloat(message);
            }

            return input;
        } catch (Exception ex) {
            System.out.println("Terjadi kesalahan: " + ex.getMessage());
            return getInputFloat(message);
        }
    }

    public static int getChoice(int max) {
        while (true) {
            try {
                int choice = Main.getInputInt("Pilihan: ");
                if (choice < 1 || choice > max) {
                    if (choice == 0)
                        return choice;
                    throw new NumberFormatException();
                }
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // public static void displayBuku() {
    // System.out.println(
    // "==================================================================================================================================================================================================================================================");
    // System.out.println(
    // "| Daftar Data Buku |");
    // System.out.println(
    // "==================================================================================================================================================================================================================================================");
    // System.out.printf(
    // String.format(
    // "| %-7s | %-25s | %-15s | %-17s | %-13s | %-13s | %-15s | %-17s| %-12s |
    // %-12s | %-12s | %-12s | %-11s |%n",
    // "ID", "Judul Buku", "Pengarang", "Penerbit", "Tahun Terbit", "Edisi Terbit",
    // "Jenis Sampul",
    // "Jumlah Tersedia", "Jumlah Terpinjam", "Jumlah Rusak", "Jumlah Total",
    // "Genre", "Biaya Denda"));
    // System.out.println(
    // "==================================================================================================================================================================================================================================================");
    // for (Buku buku : daftarBuku) {
    // System.out.printf(
    // String.format(
    // "| %-7s | %-25s | %-15s | %-17s | %-13s | %-13s | %-15s | %-17s| %-12s |
    // %-12s | %-12s | %-12s |%n",
    // buku.getIDBuku(), buku.getJudul(), buku.getPengarang(), buku.getPenerbit(),
    // buku.getTahunTerbit(), buku.getEdisiTerbit(), buku.getJenisSampul(),
    // buku.getJumlahTersedia(), buku.getJumlahTerpinjam(), buku.getJumlahRusak(),
    // buku.getJumlahTotal(),
    // buku.getGenre(), buku.getHargaDenda()));
    // System.out.println(
    // "==================================================================================================================================================================================================================================================");
    // }
    // }

    // ==========================

    // public static void displayBuku() {
    // // Menginisialisasi array untuk menyimpan panjang maksimum untuk setiap kolom
    // int[] columnLengths = new int[13]; // Anda memiliki 13 kolom
    // String[] header = { "ID", "Judul Buku", "Pengarang", "Penerbit", "Tahun
    // Terbit", "Edisi Terbit", "Jenis Sampul",
    // "Jumlah Tersedia", "Jumlah Terpinjam", "Jumlah Rusak", "Jumlah Total",
    // "Genre", "Biaya Denda" };
    // // Iterasi melalui daftar buku untuk menentukan panjang maksimum untuk setiap
    // // kolom
    // for (Buku buku : daftarBuku) {
    // // Mengambil data dari setiap buku
    // String[] data = { buku.getIDBuku(), buku.getJudul(), buku.getPengarang(),
    // buku.getPenerbit(),
    // buku.getTahunTerbit(), buku.getEdisiTerbit(),
    // String.valueOf(buku.getJenisSampul()),
    // String.valueOf(buku.getJumlahTersedia()),
    // String.valueOf(buku.getJumlahTerpinjam()),
    // String.valueOf(buku.getJumlahRusak()),
    // String.valueOf(buku.getJumlahTotal()), String.valueOf(buku.getGenre()),
    // String.valueOf(buku.getHargaDenda()) };

    // // Memperbarui panjang maksimum untuk setiap kolom
    // for (int i = 0; i < data.length; i++) {
    // columnLengths[i] = Math.max(columnLengths[i], data[i].length());
    // if (columnLengths[i] < header[i].length()) {
    // columnLengths[i] = header[i].length();
    // }
    // }
    // }

    // // Menampilkan tabel dengan lebar kolom yang disesuaikan
    // System.out.println(
    // "==================================================================================================================================================================================================================================================");
    // System.out.println(
    // "| Daftar Data Buku |");
    // System.out.println(
    // "==================================================================================================================================================================================================================================================");
    // System.out.printf(
    // String.format(
    // "| %-7s | %-" + columnLengths[1] + "s | %-" + columnLengths[2] + "s | %-" +
    // columnLengths[3]
    // + "s | %-" + columnLengths[4] + "s | %-" + columnLengths[5] + "s | %-"
    // + columnLengths[6] + "s | %-" + columnLengths[7] + "s | %-" +
    // columnLengths[8]
    // + "s | %-" + columnLengths[9] + "s | %-" + columnLengths[10] + "s | %-"
    // + columnLengths[11] + "s | %-" + columnLengths[12] + "s |%n",
    // "ID", "Judul Buku", "Pengarang", "Penerbit", "Tahun Terbit", "Edisi Terbit",
    // "Jenis Sampul",
    // "Jumlah Tersedia", "Jumlah Terpinjam", "Jumlah Rusak", "Jumlah Total",
    // "Genre", "Biaya Denda"));
    // System.out.println(
    // "==================================================================================================================================================================================================================================================");
    // for (Buku buku : daftarBuku) {
    // System.out.printf(
    // String.format(
    // "| %-7s | %-" + columnLengths[1] + "s | %-" + columnLengths[2] + "s | %-"
    // + columnLengths[3] + "s | %-" + columnLengths[4] + "s | %-" +
    // columnLengths[5]
    // + "s | %-" + columnLengths[6] + "s | %-" + columnLengths[7] + "s | %-"
    // + columnLengths[8] + "s | %-" + columnLengths[9] + "s | %-" +
    // columnLengths[10]
    // + "s | %-" + columnLengths[11] + "s | %-" + columnLengths[12] + "s |%n",
    // buku.getIDBuku(), buku.getJudul(), buku.getPengarang(), buku.getPenerbit(),
    // buku.getTahunTerbit(), buku.getEdisiTerbit(), buku.getJenisSampul(),
    // buku.getJumlahTersedia(), buku.getJumlahTerpinjam(), buku.getJumlahRusak(),
    // buku.getJumlahTotal(),
    // buku.getGenre(), buku.getHargaDenda()));
    // System.out.println(
    // "==================================================================================================================================================================================================================================================");
    // }
    // }

    public static void displayBuku() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("OutputBuku.txt");
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            StringBuilder separatorTop = new StringBuilder();
            StringBuilder teksJudul = new StringBuilder();

            String[] header = { "ID", "Judul Buku", "Pengarang", "Penerbit", "Tahun Terbit", "Edisi Terbit",
                    "Jenis Sampul",
                    "Jumlah Tersedia", "Jumlah Terpinjam", "Jumlah Rusak", "Jumlah Total", "Genre", "Biaya Denda" };
            int[] columnLengths = new int[header.length]; // Anda memiliki 13 kolom

            for (Buku buku : daftarBuku) {
                String[] data = { buku.getIDBuku(), buku.getJudul(), buku.getPengarang(), buku.getPenerbit(),
                        buku.getTahunTerbit(), buku.getEdisiTerbit(), String.valueOf(buku.getJenisSampul()),
                        String.valueOf(buku.getJumlahTersedia()), String.valueOf(buku.getJumlahTerpinjam()),
                        String.valueOf(buku.getJumlahRusak()),
                        String.valueOf(buku.getJumlahTotal()), String.valueOf(buku.getGenre()),
                        String.valueOf(buku.getHargaDenda()) };

                for (int i = 0; i < data.length; i++) {
                    columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                    if (columnLengths[i] < header[i].length()) {
                        columnLengths[i] = header[i].length();
                    }
                }
            }

            int totalLength = 0;
            for (int length : columnLengths) {
                totalLength += length;
            }
            totalLength += 3 * columnLengths.length + 1;

            String judul = "Daftar Data Buku";
            int judulLength = totalLength - judul.length() - 2;

            for (int i = 0; i < (judulLength / 2); i++) {
                teksJudul.append(" ");
            }

            for (int i = 0; i < totalLength; i++) {
                separatorTop.append("=");
            }

            printWriter.println(separatorTop.toString());

            printWriter.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

            printWriter.println(separatorTop.toString());

            for (int i = 0; i < header.length; i++) {
                printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
            }
            printWriter.println("|");

            printWriter.println(separatorTop.toString());

            for (Buku buku : daftarBuku) {
                String[] data = { buku.getIDBuku(), buku.getJudul(), buku.getPengarang(), buku.getPenerbit(),
                        buku.getTahunTerbit(), buku.getEdisiTerbit(), String.valueOf(buku.getJenisSampul()),
                        String.valueOf(buku.getJumlahTersedia()), String.valueOf(buku.getJumlahTerpinjam()),
                        String.valueOf(buku.getJumlahRusak()),
                        String.valueOf(buku.getJumlahTotal()), String.valueOf(buku.getGenre()),
                        String.valueOf(buku.getHargaDenda()) };

                for (int i = 0; i < data.length; i++) {
                    printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
                }
                printWriter.println("|");
            }

            printWriter.println(separatorTop.toString());
            printWriter.close();
            System.out.println("File berhasil di export ke file OutputBuku.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Kesalahan: File tidak ditemukan");
        }
    }

    public static void displayCD() {
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.println(
        // "| Daftar Data CD |");
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.printf(
        // String.format(
        // "| %-7s | %-16s | %-15s | %-11s | %-12s | %-12s | %-11s | %-14s | %-12s |%n",
        // "ID", "Judul CD", "Genre", "Tahun Publikasi", "Rak",
        // "Jumlah Tersedia", "Jumlah Terpinjam", "Jumlah Rusak", "Jumlah Total"));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // for (CD cd : listcd) {
        // System.out.printf(
        // String.format(
        // "| %-7s | %-16s | %-15s | %-11s | %-12s | %-12s | %-11s | %-14s | %-12s |%n",
        // cd.getIDCD(), cd.getJudul(), cd.getGenre(), cd.getTahun_publikasi(),
        // cd.getRak(), cd.getJumlahTersedia(),
        // cd.getJumlahTersedia(), cd.getJumlahTerpinjam(), cd.getJumlahRusak(),
        // cd.getJumlahTotal()));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("OutputCD.txt");
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            StringBuilder separatorTop = new StringBuilder();
            StringBuilder teksJudul = new StringBuilder();

            String[] header = { "ID", "Judul CD", "Genre", "Tahun Publikasi", "Rak", "Jumlah Tersedia",
                    "Jumlah Terpinjam",
                    "Jumlah Rusak", "Jumlah Total" };
            int[] columnLengths = new int[header.length];

            for (CD cd : listcd) {
                // Mengambil data dari setiap buku
                String[] data = { cd.getIDCD(), cd.getJudul(),
                        String.valueOf(cd.getGenre()), String.valueOf(cd.getTahun_publikasi()),
                        String.valueOf(cd.getRak()),
                        String.valueOf(cd.getJumlahTersedia()), String.valueOf(cd.getJumlahTerpinjam()),
                        String.valueOf(cd.getJumlahRusak()), String.valueOf(cd.getJumlahTotal()) };

                // Memperbarui panjang maksimum untuk setiap kolom
                for (int i = 0; i < data.length; i++) {
                    columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                    if (columnLengths[i] < header[i].length()) {
                        columnLengths[i] = header[i].length();
                    }
                }
            }

            int totalLength = 0;
            for (int length : columnLengths) {
                totalLength += length;
            }
            totalLength += 3 * columnLengths.length + 1;

            String judul = "Daftar Data CD";
            int judulLength = totalLength - judul.length() - 2;

            for (int i = 0; i < (judulLength / 2); i++) {
                teksJudul.append(" ");
            }

            for (int i = 0; i < totalLength; i++) {
                separatorTop.append("=");
            }

            printWriter.println(separatorTop.toString());

            printWriter.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

            printWriter.println(separatorTop.toString());

            for (int i = 0; i < header.length; i++) {
                printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
            }
            printWriter.println("|");

            printWriter.println(separatorTop.toString());

            for (CD cd : listcd) {
                String[] data = { cd.getIDCD(), cd.getJudul(),
                        String.valueOf(cd.getGenre()), String.valueOf(cd.getTahun_publikasi()),
                        String.valueOf(cd.getRak()),
                        String.valueOf(cd.getJumlahTersedia()), String.valueOf(cd.getJumlahTerpinjam()),
                        String.valueOf(cd.getJumlahRusak()), String.valueOf(cd.getJumlahTotal()) };

                for (int i = 0; i < data.length; i++) {
                    printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
                }
                printWriter.println("|");
            }

            printWriter.println(separatorTop.toString());
            printWriter.close();
            System.out.println("File berhasil di export ke file OutputCD.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Kesalahan: File tidak ditemukan");
        }
    }

    public static void displayDosen_Staff() {
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.println(
        // "| Daftar Data Dosen/Staff |");
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.printf(
        // String.format(
        // "| %-12s | %-16s | %-15s | %-11s | %-12s | %-12s | %-11s | %-14s | %-12s |
        // %-12s | %-12s | %-12s |%n",
        // "NIK", "Nama", "Password", "Alamat", "No. Hp", "Prodi", "Fakultas",
        // "Punya Denda?", "Total Denda"));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // for (Dosen_Staff DS : userdosen) {
        // System.out.printf(
        // String.format(
        // "| %-12s | %-16s | %-15s | %-11s | %-12s | %-12s | %-11s | %-14s | %-12s |
        // %-12s | %-12s | %-12s |%n",
        // DS.getNIK(), DS.getNama(), DS.getPassword(), DS.getAlamat(),
        // DS.getNoHP(), DS.getProdi(),
        // DS.getFakultas(), DS.getPDenda(), DS.getTotalDenda()));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("OutputDosenStaff.txt");
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            StringBuilder separatorTop = new StringBuilder();
            StringBuilder teksJudul = new StringBuilder();

            String[] header = { "NIK", "Nama", "Password", "Alamat", "No. Hp", "Prodi", "Fakultas",
                    "Punya Denda?", "Total Denda" };
            int[] columnLengths = new int[header.length];

            for (Dosen_Staff DS : userdosen) {
                // Mengambil data dari setiap buku
                String[] data = { DS.getNIK(), DS.getNama(), DS.getPassword(), DS.getAlamat(),
                        DS.getNoHP(), String.valueOf(DS.getProdi()),
                        String.valueOf(DS.getFakultas()), String.valueOf(DS.getPDenda()),
                        String.valueOf(DS.getTotalDenda()) };

                // Memperbarui panjang maksimum untuk setiap kolom
                for (int i = 0; i < data.length; i++) {
                    columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                    if (columnLengths[i] < header[i].length()) {
                        columnLengths[i] = header[i].length();
                    }
                }
            }

            int totalLength = 0;
            for (int length : columnLengths) {
                totalLength += length;
            }
            totalLength += 3 * columnLengths.length + 1;

            String judul = "Daftar Data Dosen/Staff";
            int judulLength = totalLength - judul.length() - 2;

            for (int i = 0; i < (judulLength / 2); i++) {
                teksJudul.append(" ");
            }

            for (int i = 0; i < totalLength; i++) {
                separatorTop.append("=");
            }

            printWriter.println(separatorTop.toString());

            printWriter.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

            printWriter.println(separatorTop.toString());

            for (int i = 0; i < header.length; i++) {
                printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
            }
            printWriter.println("|");

            printWriter.println(separatorTop.toString());

            for (Dosen_Staff DS : userdosen) {
                // Mengambil data dari setiap buku
                String[] data = { DS.getNIK(), DS.getNama(), DS.getPassword(), DS.getAlamat(),
                        DS.getNoHP(), String.valueOf(DS.getProdi()),
                        String.valueOf(DS.getFakultas()), String.valueOf(DS.getPDenda()),
                        String.valueOf(DS.getTotalDenda()) };

                for (int i = 0; i < data.length; i++) {
                    printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
                }
                printWriter.println("|");
            }

            printWriter.println(separatorTop.toString());
            printWriter.close();
            System.out.println("File berhasil di export ke file OutputDosenStaff.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Kesalahan: File tidak ditemukan");
        }
    }

    public static void displayMahasiswa() {
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.println(
        // "| Daftar Data Mahasiswa |");
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.printf(
        // String.format(
        // "| %-12s | %-16s | %-15s | %-11s | %-12s | %-12s | %-11s | %-14s | %-12s
        // |%n",
        // "NIM", "Nama", "Password", "Alamat", "No. Hp", "Prodi", "Fakultas",
        // "Punya Denda?", "Total Denda"));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // for (Mahasiswa mhs : usermhs) {
        // System.out.printf(
        // String.format(
        // "| %-12s | %-16s | %-15s | %-11s | %-12s | %-12s | %-11s | %-14s | %-12s
        // |%n",
        // mhs.getNIM(), mhs.getNama(), mhs.getPassword(), mhs.getAlamat(),
        // mhs.getNoHP(), mhs.getProdi(),
        // mhs.getFakultas(), mhs.getPDenda(), mhs.getTotalDenda()));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("OutputMahasiswa.txt");
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            StringBuilder separatorTop = new StringBuilder();
            StringBuilder teksJudul = new StringBuilder();

            String[] header = { "NIM", "Nama", "Password", "Alamat", "No. Hp", "Prodi", "Fakultas",
                    "Punya Denda?", "Total Denda" };
            int[] columnLengths = new int[header.length];

            for (Mahasiswa mhs : usermhs) {
                // Mengambil data dari setiap buku
                String[] data = { String.valueOf(mhs.getNIM()), String.valueOf(mhs.getNama()),
                        String.valueOf(mhs.getPassword()), String.valueOf(mhs.getAlamat()),
                        mhs.getNoHP(), String.valueOf(mhs.getProdi()),
                        String.valueOf(mhs.getFakultas()), String.valueOf(mhs.getPDenda()),
                        String.valueOf(mhs.getTotalDenda()) };

                // Memperbarui panjang maksimum untuk setiap kolom
                for (int i = 0; i < data.length; i++) {
                    columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                    if (columnLengths[i] < header[i].length()) {
                        columnLengths[i] = header[i].length();
                    }
                }
            }

            int totalLength = 0;
            for (int length : columnLengths) {
                totalLength += length;
            }
            totalLength += 3 * columnLengths.length + 1;

            String judul = "Daftar Data Mahasiswa";
            int judulLength = totalLength - judul.length() - 2;

            for (int i = 0; i < (judulLength / 2); i++) {
                teksJudul.append(" ");
            }

            for (int i = 0; i < totalLength; i++) {
                separatorTop.append("=");
            }

            printWriter.println(separatorTop.toString());

            printWriter.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

            printWriter.println(separatorTop.toString());

            for (int i = 0; i < header.length; i++) {
                printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
            }
            printWriter.println("|");

            printWriter.println(separatorTop.toString());

            for (Mahasiswa mhs : usermhs) {
                // Mengambil data dari setiap buku
                String[] data = { String.valueOf(mhs.getNIM()), String.valueOf(mhs.getNama()),
                        String.valueOf(mhs.getPassword()), String.valueOf(mhs.getAlamat()),
                        mhs.getNoHP(), String.valueOf(mhs.getProdi()),
                        String.valueOf(mhs.getFakultas()), String.valueOf(mhs.getPDenda()),
                        String.valueOf(mhs.getTotalDenda()) };

                for (int i = 0; i < data.length; i++) {
                    printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
                }
                printWriter.println("|");
            }

            printWriter.println(separatorTop.toString());
            printWriter.close();
            System.out.println("File berhasil di export ke file OutputMahasiswa.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Kesalahan: File tidak ditemukan");
        }
    }

    public static void displayPustakawan() {
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.println(
        // "| Daftar Data Pustakawan |");
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.printf(
        // String.format(
        // "| %-7s | %-16s | %-15s | %-11s| %-12s| %-12s| %-11s |%n",
        // "ID", "Nama", "Email", "Password", "Alamat", "No. Hp", "Jenis Kelamin"));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // for (Pustakawan pust : listpustakawan) {
        // System.out.printf(
        // String.format(
        // "| %-7s | %-16s | %-15s | %-11s| %-12s| %-12s| %-11s |%n",
        // pust.getIDPustakawan(), pust.getNama(), pust.getEmail(), pust.getPassword(),
        // pust.getAlamat(), pust.getNoHP(), pust.getJenisKelamin()));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("OutputPust.txt");
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            StringBuilder separatorTop = new StringBuilder();
            StringBuilder teksJudul = new StringBuilder();

            String[] header = { "ID", "Nama", "Email", "Password", "Alamat", "No. Hp", "Jenis Kelamin" };
            int[] columnLengths = new int[header.length];

            for (Pustakawan pust : listpustakawan) {
                // Mengambil data dari setiap buku
                String[] data = { pust.getIDPustakawan(), pust.getNama(), pust.getEmail(), pust.getPassword(),
                        pust.getAlamat(), pust.getNoHP(), String.valueOf(pust.getJenisKelamin()) };

                // Memperbarui panjang maksimum untuk setiap kolom
                for (int i = 0; i < data.length; i++) {
                    columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                    if (columnLengths[i] < header[i].length()) {
                        columnLengths[i] = header[i].length();
                    }
                }
            }

            int totalLength = 0;
            for (int length : columnLengths) {
                totalLength += length;
            }
            totalLength += 3 * columnLengths.length + 1;

            String judul = "Daftar Data Pustakawan";
            int judulLength = totalLength - judul.length() - 2;

            for (int i = 0; i < (judulLength / 2); i++) {
                teksJudul.append(" ");
            }

            for (int i = 0; i < totalLength; i++) {
                separatorTop.append("=");
            }

            printWriter.println(separatorTop.toString());

            printWriter.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

            printWriter.println(separatorTop.toString());

            for (int i = 0; i < header.length; i++) {
                printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
            }
            printWriter.println("|");

            printWriter.println(separatorTop.toString());

            for (Pustakawan pust : listpustakawan) {
                String[] data = { pust.getIDPustakawan(), pust.getNama(), pust.getEmail(), pust.getPassword(),
                        pust.getAlamat(), pust.getNoHP(), String.valueOf(pust.getJenisKelamin()) };

                for (int i = 0; i < data.length; i++) {
                    printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
                }
                printWriter.println("|");
            }

            printWriter.println(separatorTop.toString());
            printWriter.close();
            System.out.println("File berhasil di export ke file OutputPust.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Kesalahan: File tidak ditemukan");
        }
    }

    public static void displayPeminjaman(boolean view) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("OutputPeminjaman.txt");
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            StringBuilder separatorTop = new StringBuilder();
            StringBuilder teksJudul = new StringBuilder();
            String mahasiswa = "", dosen = "";
            String[] header = { "No. Peminjaman", "Nama Pustakawan", "Nama Mahasiswa", "Nama Dosen/Staff",
                    "Tanggal Peminjaman", "Tenggat Kembali",
                    "Status" };
            int[] columnLengths = new int[header.length];
            for (Peminjaman peminjaman : dafPeminjaman) {
                if (peminjaman.getNIK() != null)
                    dosen = peminjaman.getNIK().getNama();
                else if (peminjaman.getNIM() != null)
                    mahasiswa = peminjaman.getNIM().getNama();
                String[] data = { peminjaman.getNoPinjam(), peminjaman.getIDPustakawan().getNama(),
                        mahasiswa, dosen,
                        peminjaman.getTglPinjam(), String.valueOf(peminjaman.getDueDate()),
                        String.valueOf(peminjaman.getStatus()) };

                for (int i = 0; i < data.length; i++) {
                    columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                    if (columnLengths[i] < header[i].length()) {
                        columnLengths[i] = header[i].length();
                    }
                }
            }

            int totalLength = 0;
            for (int length : columnLengths) {
                totalLength += length;
            }
            totalLength += 3 * columnLengths.length + 1;

            String judul = "Peminjaman";
            int judulLength = totalLength - judul.length() - 2;

            for (int i = 0; i < (judulLength / 2); i++) {
                teksJudul.append(" ");
            }

            for (int i = 0; i < totalLength; i++) {
                separatorTop.append("=");
            }

            printWriter.println(separatorTop.toString());

            printWriter.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

            printWriter.println(separatorTop.toString());

            for (int i = 0; i < header.length; i++) {
                printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
            }
            printWriter.println("|");

            printWriter.println(separatorTop.toString());

            for (Peminjaman peminjaman : dafPeminjaman) {
                if (peminjaman.getNIK() != null)
                    dosen = peminjaman.getNIK().getNama();
                else if (peminjaman.getNIM() != null)
                    mahasiswa = peminjaman.getNIM().getNama();
                String[] data = { peminjaman.getNoPinjam(), peminjaman.getIDPustakawan().getNama(),
                        mahasiswa, dosen,
                        peminjaman.getTglPinjam(), String.valueOf(peminjaman.getDueDate()),
                        String.valueOf(peminjaman.getStatus()) };

                for (int i = 0; i < data.length; i++) {
                    printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
                }
                printWriter.println("|");
            }

            printWriter.println(separatorTop.toString());
            System.out.println("File berhasil di export ke file OutputPeminjaman.txt");
            printWriter.close();
            if (view == true) {
                boolean isRunning = true;
                while (isRunning) {
                    String input = getInput("Apakah ingin melihat data detail peminjaman (Y/N) ?", false, true, false,
                            false);

                    if (input.equalsIgnoreCase("Y")) {
                        System.out.println("");
                        String noPinjam = getInput(
                                "Masukkan nomor peminjaman yang ingin dilihat detailnya (0 untuk keluar):",
                                false, false, false, true);

                        if (noPinjam.equals("0")) {
                            break;
                        }

                        Peminjaman noPinjamPilih = null;
                        for (Peminjaman peminjaman : dafPeminjaman) {
                            if (peminjaman.getNoPinjam().equals(noPinjam)) {
                                noPinjamPilih = peminjaman;
                                break;
                            }
                        }

                        if (noPinjamPilih != null) {
                            displayDetailPeminjaman(noPinjamPilih);
                        } else {
                            System.out.println("Nomor peminjaman tidak ditemukan.");
                        }
                    } else if (input.equalsIgnoreCase("N")) {
                        isRunning = false;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Kesalahan: File tidak ditemukan");
        }
    }

    public static void displayDetailPeminjaman(Peminjaman noPinjam) {
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.println(
        // "| Detail Peminjaman |");
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.printf(
        // String.format(
        // "| %-14s | %-16s | %-16s | %-16s | %-18s |%n",
        // "No. Peminjaman", "ID Buku", "ID CD", "Jumlah", "Tenggat Kembali"));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // for (DetailPeminjaman detpeminjaman : dafDetPeminjaman) {
        // System.out.printf(
        // String.format(
        // "| %-14s | %-16s | %-16s | %-16s | %-18s |%n",
        // detpeminjaman.getNoPinjam(), detpeminjaman.getIDBuku(),
        // detpeminjaman.getIDCD(),
        // detpeminjaman.getJumlah(), detpeminjaman.getDueDate()));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("OutputDetPinjam.txt");
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            StringBuilder separatorTop = new StringBuilder();
            StringBuilder teksJudul = new StringBuilder();
            String idbuku = "", idCD = "";

            String[] header = { "No. Peminjaman", "Judul Buku", "Judul CD", "Jumlah" };
            int[] columnLengths = new int[header.length];

            for (DetailPeminjaman detpeminjaman : dafDetPeminjaman) {
                if (detpeminjaman.getNoPinjam().equals(noPinjam)) {
                    if (detpeminjaman.getIDBuku() != null)
                        idbuku = detpeminjaman.getIDBuku().getJudul();
                    else if (detpeminjaman.getIDCD() != null)
                        idCD = detpeminjaman.getIDCD().getJudul();
                    String[] data = { detpeminjaman.getNoPinjam().getNoPinjam(),
                            idbuku, idCD, String.valueOf(detpeminjaman.getJumlah()) };

                    for (int i = 0; i < data.length; i++) {
                        columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                        if (columnLengths[i] < header[i].length()) {
                            columnLengths[i] = header[i].length();
                        }
                    }
                }
            }

            int totalLength = 0;
            for (int length : columnLengths) {
                totalLength += length;
            }
            totalLength += 3 * columnLengths.length + 1;

            String judul = "Detail Peminjaman";
            int judulLength = totalLength - judul.length() - 2;

            for (int i = 0; i < (judulLength / 2); i++) {
                teksJudul.append(" ");
            }

            for (int i = 0; i < totalLength; i++) {
                separatorTop.append("=");
            }

            printWriter.println(separatorTop.toString());

            printWriter.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

            printWriter.println(separatorTop.toString());

            for (int i = 0; i < header.length; i++) {
                printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
            }
            printWriter.println("|");

            printWriter.println(separatorTop.toString());

            for (DetailPeminjaman detpeminjaman : dafDetPeminjaman) {
                if (detpeminjaman.getNoPinjam().equals(noPinjam)) {
                    if (detpeminjaman.getIDBuku() != null)
                        idbuku = detpeminjaman.getIDBuku().getJudul();
                    else if (detpeminjaman.getIDCD() != null)
                        idCD = detpeminjaman.getIDCD().getJudul();
                    String[] data = { detpeminjaman.getNoPinjam().getNoPinjam(),
                            idbuku, idCD, String.valueOf(detpeminjaman.getJumlah()) };

                    for (int i = 0; i < data.length; i++) {
                        printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
                    }
                    printWriter.println("|");
                }
            }

            printWriter.println(separatorTop.toString());
            printWriter.close();
            System.out.println("File berhasil di export ke file OutputDetPinjam.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Kesalahan: File tidak ditemukan");
        }
    }

    public static void displayPengembalian() {
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.println(
        // "| Pengembalian |");
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.printf(
        // String.format(
        // "| %-16s | %-14s | %-14s | %-20s | %-12s | %-12s | %-12s | %-12s | %-12s
        // |%n",
        // "No. Pengembalian", "No. Peminjaman", "ID Pustakawan", "Tanggal
        // Pengembalian", "NIM", "NIK",
        // "Biaya Denda", "Biaya Telat", "Total Bayar"));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // for (Pengembalian pengembalian : dafPengembalian) {
        // System.out.printf(
        // String.format(
        // "| %-16s | %-14s | %-14s | %-20s | %-12s | %-12s | %-12s | %-12s | %-12s
        // |%n",
        // pengembalian.getNoKembali(), pengembalian.getNoPinjam(),
        // pengembalian.getIDPustakawan(),
        // pengembalian.getTglKembali(), pengembalian.getNIM(), pengembalian.getNIK(),
        // pengembalian.getTotalDendaRusak(), pengembalian.getTotalTelat(),
        // pengembalian.getTotalBayar()));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("OutputPengembalian.txt");
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            StringBuilder separatorTop = new StringBuilder();
            StringBuilder teksJudul = new StringBuilder();
            String mahasiswa = "", dosen = "";

            String[] header = { "No. Pengembalian", "No. Peminjaman", "Nama Pustakawan", "Tanggal Pengembalian",
                    "Nama Mahasiswa", "Nama Dosen/Staff",
                    "Biaya Denda", "Biaya Telat", "Total Bayar" };
            int[] columnLengths = new int[header.length];

            for (Pengembalian pengembalian : dafPengembalian) {
                if (pengembalian.getNIK() != null) {
                    dosen = pengembalian.getNIK().getNama();
                } else if (pengembalian.getNIM() != null) {
                    mahasiswa = pengembalian.getNIM().getNama();
                }
                String[] data = { String.valueOf(pengembalian.getNoKembali()),
                        pengembalian.getNoPinjam().getNoPinjam(),
                        String.valueOf(pengembalian.getIDPustakawan().getNama()),
                        String.valueOf(pengembalian.getTglKembali()), mahasiswa,
                        dosen,
                        String.valueOf(pengembalian.getTotalDendaRusak()), String.valueOf(pengembalian.getTotalTelat()),
                        String.valueOf(pengembalian.getTotalBayar()) };

                for (int i = 0; i < data.length; i++) {
                    columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                    if (columnLengths[i] < header[i].length()) {
                        columnLengths[i] = header[i].length();
                    }
                }
            }

            int totalLength = 0;
            for (int length : columnLengths) {
                totalLength += length;
            }
            totalLength += 3 * columnLengths.length + 1;

            String judul = "Pengembalian";
            int judulLength = totalLength - judul.length() - 2;

            for (int i = 0; i < (judulLength / 2); i++) {
                teksJudul.append(" ");
            }

            for (int i = 0; i < totalLength; i++) {
                separatorTop.append("=");
            }

            printWriter.println(separatorTop.toString());

            printWriter.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

            printWriter.println(separatorTop.toString());

            for (int i = 0; i < header.length; i++) {
                printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
            }
            printWriter.println("|");

            printWriter.println(separatorTop.toString());

            for (Pengembalian pengembalian : dafPengembalian) {
                if (pengembalian.getNIK() != null) {
                    dosen = pengembalian.getNIK().getNama();
                } else if (pengembalian.getNIM() != null) {
                    mahasiswa = pengembalian.getNIM().getNama();
                }
                String[] data = { String.valueOf(pengembalian.getNoKembali()),
                        pengembalian.getNoPinjam().getNoPinjam(),
                        String.valueOf(pengembalian.getIDPustakawan().getNama()),
                        String.valueOf(pengembalian.getTglKembali()), mahasiswa,
                        dosen,
                        String.valueOf(pengembalian.getTotalDendaRusak()), String.valueOf(pengembalian.getTotalTelat()),
                        String.valueOf(pengembalian.getTotalBayar()) };

                for (int i = 0; i < data.length; i++) {
                    printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
                }
                printWriter.println("|");
            }

            printWriter.println(separatorTop.toString());
            System.out.println("File berhasil di export ke file OutputPengembalian.txt");
            printWriter.close();
            boolean isRunning = true;
            while (isRunning) {
                String input = getInput("Apakah ingin melihat data detail pengembalian (Y/N) ?", false, true, false,
                        false);

                if (input.equalsIgnoreCase("Y")) {
                    System.out.println("");
                    String noKembali = getInput(
                            "Masukkan nomor pengembalian yang ingin dilihat detailnya (0 untuk keluar):",
                            false, false, false, false);

                    if (noKembali.equals("0")) {
                        break;
                    }

                    Pengembalian noKembaliPilih = null;
                    for (Pengembalian pengembalian : dafPengembalian) {
                        if (pengembalian.getNoKembali().equals(noKembali)) {
                            noKembaliPilih = pengembalian;
                            break;
                        }
                    }

                    if (noKembaliPilih != null) {
                        displayDetailPembayaran(noKembaliPilih);
                        displayDetailPengembalian(noKembaliPilih);

                    } else {
                        System.out.println("Nomor pengembalian tidak ditemukan.");
                    }
                } else if (input.equalsIgnoreCase("N")) {
                    isRunning = false;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Kesalahan: File tidak ditemukan");
        }
    }

    public static void displayDetailPengembalian(Pengembalian noPengembalian) {
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.println(
        // "| Detail Pengembalian |");
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.printf(
        // String.format(
        // "| %-16s | %-7s | %-7s | %-8s |%n",
        // "No. Pengembalian", "ID Buku", "ID CD", "Jumlah"));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // for (DetailPengembalian detpengembalian : dafDetPengembalian) {
        // System.out.printf(
        // String.format(
        // "| %-16s | %-7s | %-7s | %-8s |%n",
        // detpengembalian.getNoPengembalian(), detpengembalian.getIDBuku(),
        // detpengembalian.getIDCD(),
        // detpengembalian.getJumlah()));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("OutputDetKembali.txt");
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            StringBuilder separatorTop = new StringBuilder();
            StringBuilder teksJudul = new StringBuilder();
            String idbuku = "", idCD = "";

            String[] header = { "No. Pengembalian", "Judul Buku", "Judul CD", "Jumlah" };
            int[] columnLengths = new int[header.length];

            for (DetailPengembalian detpengembalian : dafDetPengembalian) {
                if (detpengembalian.getNoPengembalian().equals(noPengembalian)) {
                    if (detpengembalian.getIDBuku() != null) {
                        idbuku = detpengembalian.getIDBuku().getJudul();
                    } else if (detpengembalian.getIDCD() != null) {
                        idCD = detpengembalian.getIDCD().getJudul();
                    }
                }
                String[] data = { detpengembalian.getNoPengembalian().getNoKembali(),
                        idbuku, idCD, String.valueOf(detpengembalian.getJumlah()) };

                // Memperbarui panjang maksimum untuk setiap kolom
                for (int i = 0; i < data.length; i++) {
                    columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                    if (columnLengths[i] < header[i].length()) {
                        columnLengths[i] = header[i].length();
                    }
                }
            }

            int totalLength = 0;
            for (int length : columnLengths) {
                totalLength += length;
            }
            totalLength += 3 * columnLengths.length + 1;

            String judul = "Detail Pengembalian";
            int judulLength = totalLength - judul.length() - 2;

            for (int i = 0; i < (judulLength / 2); i++) {
                teksJudul.append(" ");
            }

            for (int i = 0; i < totalLength; i++) {
                separatorTop.append("=");
            }

            printWriter.println(separatorTop.toString());

            printWriter.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

            printWriter.println(separatorTop.toString());

            for (int i = 0; i < header.length; i++) {
                printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
            }
            printWriter.println("|");

            printWriter.println(separatorTop.toString());

            for (DetailPengembalian detpengembalian : dafDetPengembalian) {
                if (detpengembalian.getNoPengembalian().equals(noPengembalian)) {
                    if (detpengembalian.getIDBuku() != null) {
                        idbuku = detpengembalian.getIDBuku().getJudul();
                    } else if (detpengembalian.getIDCD() != null) {
                        idCD = detpengembalian.getIDCD().getJudul();
                    }
                }
                String[] data = { detpengembalian.getNoPengembalian().getNoKembali(),
                        idbuku, idCD, String.valueOf(detpengembalian.getJumlah()) };

                for (int i = 0; i < data.length; i++) {
                    printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
                }
                printWriter.println("|");
            }
            printWriter.println(separatorTop.toString());
            printWriter.close();
            System.out.println("File berhasil di export ke file OutputDetKembali.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Kesalahan: File tidak ditemukan");
        }
    }

    public static void displayDetailPembayaran(Pengembalian noKembali) {
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.println(
        // "| Detail Pembayaran |");
        // System.out.println(
        // "====================================================================================================================================================================================");
        // System.out.printf(
        // String.format(
        // "| %-16s | %-11s | %-5s | %-12s |%n",
        // "No. Pengembalian", "Jenis Denda", "Biaya Denda", "Jumlah Bayar"));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // for (DetailPembayaran detpembayaran : dafDetPembayaran) {
        // System.out.printf(
        // String.format(
        // "| %-16s | %-12s | %-5s | %-12s |%n",
        // detpembayaran.getNoPengembalian(), detpembayaran.getJenisDenda(),
        // detpembayaran.getJumlah_denda(),
        // detpembayaran.getJumlah()));
        // System.out.println(
        // "====================================================================================================================================================================================");
        // }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("OutputDetBayar.txt");
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            StringBuilder separatorTop = new StringBuilder();
            StringBuilder teksJudul = new StringBuilder();
            String[] header = { "No. Pengembalian", "Judul Buku", "Judul CD", "Jenis Denda", "Biaya Denda",
                    "Jumlah Bayar" };
            int[] columnLengths = new int[header.length];
            String idbuku = "", idcd = "";

            for (DetailPembayaran detpembayaran : dafDetPembayaran) {
                if (detpembayaran.getNoPengembalian().equals(noKembali)) {
                    if (detpembayaran.getIDBuku() != null) {
                        idbuku = detpembayaran.getIDBuku().getJudul();
                    } else if (detpembayaran.getIDCD() != null) {
                        idcd = detpembayaran.getIDCD().getJudul();
                    }
                }

                String[] data = { detpembayaran.getNoPengembalian().getNoKembali(), idbuku, idcd,
                        String.valueOf(detpembayaran.getJenisDenda()),
                        String.valueOf(detpembayaran.getJumlah_denda()),
                        String.valueOf(detpembayaran.getJumlah()) };

                // Memperbarui panjang maksimum untuk setiap kolom
                for (int i = 0; i < data.length; i++) {
                    columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                    if (columnLengths[i] < header[i].length()) {
                        columnLengths[i] = header[i].length();
                    }
                }
            }

            int totalLength = 0;
            for (int length : columnLengths) {
                totalLength += length;
            }
            totalLength += 3 * columnLengths.length + 1;

            String judul = "Detail Pembayaran";
            int judulLength = totalLength - judul.length() - 2;

            for (int i = 0; i < (judulLength / 2); i++) {
                teksJudul.append(" ");
            }

            for (int i = 0; i < totalLength; i++) {
                separatorTop.append("=");
            }

            printWriter.println(separatorTop.toString());

            printWriter.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

            printWriter.println(separatorTop.toString());

            for (int i = 0; i < header.length; i++) {
                printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
            }
            printWriter.println("|");

            printWriter.println(separatorTop.toString());

            for (DetailPembayaran detpembayaran : dafDetPembayaran) {
                if (detpembayaran.getNoPengembalian().equals(noKembali)) {
                    if (detpembayaran.getIDBuku() != null) {
                        idbuku = detpembayaran.getIDBuku().getJudul();
                    } else if (detpembayaran.getIDCD() != null) {
                        idcd = detpembayaran.getIDCD().getJudul();
                    }
                }

                String[] data = { detpembayaran.getNoPengembalian().getNoKembali(), idbuku, idcd,
                        String.valueOf(detpembayaran.getJenisDenda()),
                        String.valueOf(detpembayaran.getJumlah_denda()),
                        String.valueOf(detpembayaran.getJumlah()) };

                for (int i = 0; i < data.length; i++) {
                    printWriter.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
                }
                printWriter.println("|");
            }

            printWriter.println(separatorTop.toString());
            printWriter.close();
            System.out.println("File berhasil di export ke file OutputDetBayar.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Kesalahan: File tidak ditemukan");
        }
    }

    public static void menuutamapustakawan() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Menu:");
            System.out.println("1. Tambah Data Buku");
            System.out.println("2. Tambah Data CD");
            System.out.println("3. Daftarkan Peminjam");
            System.out.println("4. Tambah Peminjaman");
            System.out.println("5. Lihat List Peminjaman");
            System.out.println("6. Tambah Pengembalian");
            System.out.println("7. Lihat List Pengembalian");
            System.out.println("0. Keluar");
            int menu = getInputInt("Pilih menu: ");

            switch (menu) {
                case 1:
                    menubukupustakawan();
                    break;
                case 2:
                    menucdpustakawan();
                    break;
                case 3: {
                    boolean isRunning1 = true;
                    while (isRunning1) {
                        System.out.println("Anda ingin membuat peminjam: ");
                        System.out.println("1. Mahasiswa");
                        System.out.println("2. Dosen/Staff");
                        System.out.println("Anda ingin melihat daftar peminjam: ");
                        System.out.println("3. Daftar Mahasiswa");
                        System.out.println("4. Daftar Dosen/Staff");
                        System.out.println("0. Keluar");
                        int pilihan = getInputInt("Pilihan anda: ");
                        switch (pilihan) {
                            case 1:
                                createUser(UserType.MAHASISWA);
                                break;
                            case 2:
                                createUser(UserType.DOSEN);
                                break;
                            case 3:
                                displayMahasiswa();
                                break;
                            case 4:
                                displayDosen_Staff();
                                break;
                            case 0:
                                isRunning1 = false;
                                break;
                            default:
                                System.out.println("Menu tidak valid.");
                        }
                        // break;
                    }
                    break;
                }
                case 4:
                    menutambahpeminjaman(false);
                    break;
                case 5:
                    displayPeminjaman(true);
                    break;
                case 6:
                    menutambahpengembalian();
                    break;
                case 7:
                    displayPengembalian();
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Menu tidak valid.");
            }
        }
        menulogin();
    }

    public static void menuapprovepinjaman() {
        boolean isRunning = true;
        while (isRunning) {
            displayPeminjaman(false);
            String input = getInput("Masukkan nomor peminjaman yang ingin diapprove ATAU Masukkan 0 untuk kembali: ",
                    false, false, false, true);
            if (input.equals("0")) {
                isRunning = false;
            } else {
                try {
                    int choice = Integer.parseInt(input);
                    if (choice > 0 && choice <= dafPeminjaman.size()) {
                        Peminjaman peminjaman = dafPeminjaman.get(choice - 1);
                        peminjaman.setStatus(Status.APPROVED);
                        System.out.println("Peminjaman dengan nomor " + peminjaman.getNoPinjam() + " telah diapprove.");
                    } else {
                        System.out.println("Nomor peminjaman tidak valid.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid.");
                }
            }
        }
    }

    public static void menuutamauser() {
        boolean isRunning = true;
        String mau;
        while (isRunning) {
            System.out.println("Menu:");
            System.out.println("1. Lihat Daftar Buku");
            System.out.println("2. Lihat Daftar CD");
            // System.out.println("3. Buat Peminjaman");
            System.out.println("0. Keluar");
            int menu = getInputInt("Pilih menu: ");

            switch (menu) {
                case 1:
                    displayBuku();
                    mau = getInput("Apakah anda ingin meminjam buku? (Y/N) ", false, true, false, false);
                    if (mau.equalsIgnoreCase("y"))
                        menutambahpeminjaman(true);
                    else
                        break;
                case 2:
                    displayCD();
                    mau = getInput("Apakah anda ingin meminjam CD? (Y/N) ", false, true, false, false);
                    if (mau.equalsIgnoreCase("y"))
                        menutambahpeminjaman(true);
                    else
                        break;
                    // case 3:
                    // System.out.println("Anda ingin membuat peminjaman: ");
                    // menutambahpeminjaman(true);
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Menu tidak valid.");
            }
        }
        menulogin();
    }

    public static void menubukupustakawan() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Menu:");
            System.out.println("1. Tambah Buku Baru");
            // System.out.println("2. Update Data Buku");
            // System.out.println("3. Hapus Data Buku");
            System.out.println("2. Lihat List Buku");
            System.out.println("0. Keluar");
            int menu = getInputInt("Pilih menu: ");

            switch (menu) {
                case 1:
                    Buku buku = new Buku();
                    buku.inputData(autokodeBuku(daftarBuku));
                    daftarBuku.add(buku);
                    System.out.println("Buku berhasil ditambahkan!");
                    break;
                case 2:
                    displayBuku();
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Menu tidak valid.");
            }
        }
    }

    static PilihanBukuCD getPilihanBukuCD() {
        System.out.println("Pilih: ");
        for (PilihanBukuCD pilihan : PilihanBukuCD.values()) {
            System.out.println((pilihan.ordinal() + 1) + ". " + pilihan);
        }
        int choice = Main.getChoice(PilihanBukuCD.values().length);
        return PilihanBukuCD.values()[choice - 1];
    }

    public static void menutambahpeminjaman(boolean user) {
        String noTrans = autoNoTransaksi("PNJM");
        Peminjaman newPeminjaman = new Peminjaman();
        if (user == true) {
            if (useraktif instanceof Mahasiswa)
                newPeminjaman.inputDataUser(noTrans, (Mahasiswa) useraktif, Status.WAITING);
            else
                newPeminjaman.inputDataUser(noTrans, (Dosen_Staff) useraktif, Status.WAITING);
        } else {
            newPeminjaman.inputData(noTrans, pustaktif, usermhs, userdosen, Status.APPROVED);
        }
        String ulang;
        System.out.println("Input detail peminjaman: ");
        for (int i = 0; i < 2; i++) {
            int choice;
            System.out.println("Masukkan jenis koleksi yang ingin dipinjam: ");
            PilihanBukuCD pilihan = getPilihanBukuCD();
            if (pilihan == PilihanBukuCD.Buku) {
                System.out.println("Daftar Buku:");
                for (int j = 0; j < daftarBuku.size(); j++) {
                    System.out.println((j + 1) + ". " + daftarBuku.get(j).getIDBuku() + " - "
                            + daftarBuku.get(j).getJudul());
                }

                choice = Main.getChoice(daftarBuku.size());
                Buku IDBuku = daftarBuku.get(choice - 1);

                DetailPeminjaman newDetailPeminjaman = new DetailPeminjaman();
                newDetailPeminjaman.inputData(newPeminjaman, IDBuku);
                dafDetPeminjaman.add(newDetailPeminjaman);

            } else if (pilihan == PilihanBukuCD.CD) {
                System.out.println("Daftar CD:");
                for (int j = 0; j < listcd.size(); j++) {
                    System.out.println((j + 1) + ". " + listcd.get(j).getIDCD() + " - "
                            + listcd.get(j).getJudul());
                }

                choice = Main.getChoice(listcd.size());
                CD IDCD = listcd.get(choice - 1);

                DetailPeminjaman newDetailPeminjaman = new DetailPeminjaman();
                newDetailPeminjaman.inputData(newPeminjaman, IDCD);
                dafDetPeminjaman.add(newDetailPeminjaman);
            } else {
                System.out.println("Pilihan tidak valid.");
            }

            ulang = getInput("Tambah Buku / CD lagi? (Y/N)", false, true, false, false);
            if (ulang.equalsIgnoreCase("n"))
                break;
        }
        dafPeminjaman.add(newPeminjaman);
    }

    public static void menutambahpengembalian() {
        try {
            String noTrans = autoNoTransaksi("KMBL");

            displayPeminjaman(false);
            String nomorPeminjaman = getInput("Masukkan nomor peminjaman: ", false, false, false, false);
            Pengembalian pengembalian = new Pengembalian();
            Peminjaman peminjaman = null;
            for (Peminjaman nopeminjaman : dafPeminjaman) {
                if (nopeminjaman.getNoPinjam().equals(nomorPeminjaman)) {
                    peminjaman = nopeminjaman;
                    break;
                }
            }

            if (peminjaman != null) {
                Mahasiswa mahasiswa = (Mahasiswa) peminjaman.getNIM();
                Dosen_Staff dosen = (Dosen_Staff) peminjaman.getNIK();
                System.out.println("Tanggal kembali: " + LocalDate.now());
                int biayarusak = 0;
                int selisihHari = hitungSelisihHari(LocalDate.parse(peminjaman.getDueDate()), LocalDate.now());
                if (mahasiswa != null) {
                    pengembalian.inputData(noTrans, peminjaman, mahasiswa, pustaktif, selisihHari, dafDetPembayaran,
                            null);
                } else {
                    pengembalian.inputData(noTrans, peminjaman, dosen, pustaktif, selisihHari, dafDetPembayaran, null);
                }
                System.out.println("Input detail pengembalian: ");
                for (DetailPeminjaman detpeminjaman : dafDetPeminjaman) {
                    if (detpeminjaman.getNoPinjam().equals(peminjaman)) {
                        if (detpeminjaman.getIDBuku() != null) {
                            DetailPengembalian detkembali = new DetailPengembalian();
                            detkembali.inputData(pengembalian, detpeminjaman.getIDBuku());
                            biayarusak = detpeminjaman.getIDBuku().getHargaDenda();
                            dafDetPengembalian.add(detkembali);
                            // displayDetailPengembalian(pengembalian);
                        } else if (detpeminjaman.getIDCD() != null) {
                            DetailPengembalian detkembali = new DetailPengembalian();
                            detkembali.inputData(pengembalian, detpeminjaman.getIDCD());
                            biayarusak = detpeminjaman.getIDCD().getHargaDenda();
                            dafDetPengembalian.add(detkembali);
                            // displayDetailPengembalian(pengembalian);
                        }
                    }
                }
                Object bukucd = null;
                for (DetailPengembalian detkembali : dafDetPengembalian) {
                    if (detkembali.getNoPengembalian().equals(pengembalian)) {
                        // DetailPembayaran detailPembayaran = new DetailPembayaran();
                        if (detkembali.getIDBuku() != null)
                            bukucd = detkembali.getIDBuku();
                        else if (detkembali.getIDCD() != null)
                            bukucd = detkembali.getIDCD();

                        if (detkembali.getStatusKembali().equals(StatusKembali.Rusak)) {
                            DetailPembayaran detailPembayaran = new DetailPembayaran();
                            detailPembayaran.inputData(pengembalian, biayarusak, JenisDenda.Rusak, bukucd);
                            dafDetPembayaran.add(detailPembayaran);
                        } else if (detkembali.getStatusKembali().equals(StatusKembali.Hilang)) {
                            DetailPembayaran detailPembayaran = new DetailPembayaran();
                            detailPembayaran.inputData(pengembalian, biayarusak, JenisDenda.Hilang, bukucd);
                            dafDetPembayaran.add(detailPembayaran);
                        } else if (selisihHari > 14) {
                            DetailPembayaran detailPembayaran = new DetailPembayaran();
                            int denda = selisihHari * 1000;
                            detailPembayaran.inputData(pengembalian, denda, JenisDenda.Telat, bukucd);
                            dafDetPembayaran.add(detailPembayaran);
                        }
                    }
                }

                dafPengembalian.add(pengembalian);

                if (mahasiswa != null) {
                    pengembalian.inputData(noTrans, peminjaman, mahasiswa, pustaktif, selisihHari, dafDetPembayaran,
                            pengembalian);
                } else {
                    pengembalian.inputData(noTrans, peminjaman, dosen, pustaktif, selisihHari, dafDetPembayaran,
                            pengembalian);
                }

            } else {
                System.out.println("Nomor peminjaman tidak ditemukan.");
            }
        } catch (NullPointerException e) {
            System.out.println("Terjadi kesalahan: Data tidak ditemukan. Silahkan input ulang");
            menutambahpengembalian();
        }

    }

    public static Object login() {
        System.out.println("Selamat datang di Perpus Cinema!");
        System.out.println("Silakan pilih jenis user anda untuk masuk:");
        System.out.println("1. Pustakawan");
        System.out.println("2. Mahasiswa");
        System.out.println("3. Dosen/Staff");
        System.out.println("9. Signup");
        System.out.println("0. Keluar");

        int choice = getInputInt("Masukkan pilihan anda: ");

        switch (choice) {
            case 1:
                System.out.println("=== Login sebagai Pustakawan ===");
                break;
            case 2:
                System.out.println("=== Login sebagai Mahasiswa ===");
                break;
            case 3:
                System.out.println("=== Login sebagai Dosen/Staff ===");
                break;
            case 9:
                signup();
                break;
            case 0:
                break;
            // System.exit(0);
        }

        System.out.print("Kode: ");
        String kode = myScanner.nextLine();
        System.out.print("Password: ");
        String password = myScanner.nextLine();

        switch (choice) {
            case 1:
                for (Pustakawan pustakawan : listpustakawan) {
                    if (pustakawan.getIDPustakawan().equals(kode) && pustakawan.getPassword().equals(password)) {
                        System.out.println("Login berhasil!");
                        return pustakawan;
                    }
                }
                break;
            case 2:
                for (Mahasiswa mahasiswa : usermhs) {
                    if (mahasiswa.getNIM().equals(kode) && mahasiswa.getPassword().equals(password)) {
                        System.out.println("Login berhasil!");
                        return mahasiswa;
                    }
                }
                break;
            case 3:
                for (Dosen_Staff dosen : userdosen) {
                    if (dosen.getNIK().equals(kode) && dosen.getPassword().equals(password)) {
                        System.out.println("Login berhasil!");
                        return dosen;
                    }
                }
                break;
            default:
                System.out.println("Jenis user tidak valid.");
                return login();
        }

        System.out.println("Kode atau password salah.");
        return login();
    }

    public static void menucdpustakawan() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Menu:");
            System.out.println("1. Tambah CD Baru");
            System.out.println("2. Lihat Daftar CD");
            System.out.println("0. Keluar");
            int menu = getInputInt("Pilih menu: ");

            switch (menu) {
                case 1:
                    CD cd = new CD();
                    cd.inputData(autokodeCD(listcd));
                    listcd.add(cd);
                    System.out.println("CD berhasil ditambahkan!");
                    break;
                case 2:
                    displayCD();
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Menu tidak valid.");
            }
        }
    }

    public static void main(String[] args) {
        if (signup() == true) {
            menulogin();
        }
    }

    public static void menulogin() {
        Object pengguna = login();
        if (pengguna instanceof Pustakawan) {
            pustaktif = (Pustakawan) pengguna;
            menuutamapustakawan();
        } else if (pengguna instanceof Mahasiswa) {
            useraktif = (Mahasiswa) pengguna;
            menuutamauser();
        } else if (pengguna instanceof Dosen_Staff) {
            useraktif = (Dosen_Staff) pengguna;
            menuutamauser();
        }
    }

    public static Peminjaman cariPeminjaman(String nomorPeminjaman) {
        for (Peminjaman peminjaman : dafPeminjaman) {
            if (peminjaman.getNoPinjam() == nomorPeminjaman) {
                return peminjaman;
            }
        }
        return null;
    }

    public static int hitungSelisihHari(LocalDate dueDate, LocalDate currentDate) {
        return currentDate.until(dueDate).getDays();
    }

    public static int hitungTotalDenda(long selisihHari) {
        // Denda per hari: Rp 5000
        return (int) (selisihHari * 5000);
    }

    private static boolean signup() {
        while (true) {
            System.out.println("Selamat datang di Perpus Cinema!");
            System.out.println("Silakan pilih jenis user yang ingin Anda buat:");
            System.out.println("1. Pustakawan");
            System.out.println("2. Login");
            System.out.println("0. Keluar");

            int choice = getInputInt("Masukkan pilihan anda: ");

            switch (choice) {
                case 1:
                    createUser(UserType.PUSTAKAWAN);
                    return true;
                case 2:
                    menulogin();
                    return true;
                case 0:
                    System.out.println("Terima kasih, sampai jumpa lagi!");
                    return false;
                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
                    return signup();
            }
        }
    }

    private static void createUser(UserType userType) {
        switch (userType) {
            case PUSTAKAWAN:
                Pustakawan pustakawan = new Pustakawan();
                pustakawan.inputData(autokodePust(listpustakawan));
                listpustakawan.add(pustakawan);
                System.out.println("Pustakawan berhasil dibuat!");
                break;
            case MAHASISWA:
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.inputData();
                usermhs.add(mahasiswa);
                System.out.println("Mahasiswa berhasil dibuat!");
                break;
            case DOSEN:
                Dosen_Staff dosen = new Dosen_Staff();
                dosen.inputData();
                userdosen.add(dosen);
                System.out.println("Dosen berhasil dibuat!");
                break;
            default:
                System.out.println("Jenis user tidak valid.");
        }
    }
}

enum UserType {
    MAHASISWA,
    DOSEN,
    PUSTAKAWAN
}

enum PilihanBukuCD {
    Buku,
    CD
}

enum Status {
    WAITING,
    APPROVED
}