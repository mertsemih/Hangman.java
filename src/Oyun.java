abstract class Oyun implements OyunArayuzu {
    private String oyunAdi;
    private int maxTahminSayisi;

    public Oyun(String oyunAdi, int maxTahminSayisi) {
        this.oyunAdi = oyunAdi;
        this.maxTahminSayisi = maxTahminSayisi;
    }

    public String getOyunAdi() {
        return oyunAdi;
    }

    public int getMaxTahminSayisi() {
        return maxTahminSayisi;
    }
}