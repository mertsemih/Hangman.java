class SkorTahtasi {
    private int skor;
    private static int toplamSkor;

    public SkorTahtasi() {
        this.skor = 0;
    }

    public int getSkor() {
        return skor;
    }

    public void skorArtir(int puan) {
        this.skor += puan;
        toplamSkor += puan;
    }

    public static int getToplamSkor() {
        return toplamSkor;
    }
}