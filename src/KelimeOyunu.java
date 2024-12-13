class KelimeOyunu extends Oyun {
    private String gizliKelime;
    private StringBuilder tahminEdilen;

    public KelimeOyunu(String gizliKelime, int maxTahminSayisi) {
        super("Kelime Oyunu", maxTahminSayisi);
        this.gizliKelime = gizliKelime;
        this.tahminEdilen = new StringBuilder("_".repeat(gizliKelime.length()));
    }

    public String getGizliKelime() {
        return gizliKelime;
    }

    public String getTahminEdilen() {
        StringBuilder gorunum = new StringBuilder();
        for (int i = 0; i < gizliKelime.length(); i++) {
            if (tahminEdilen.charAt(i) != '_') {
                gorunum.append(tahminEdilen.charAt(i));
            } else {
                gorunum.append(" _ ");
            }
        }
        return gorunum.toString().trim();
    }

    public boolean tahminYap(char harf) {
        boolean dogruTahmin = false;
        for (int i = 0; i < gizliKelime.length(); i++) {
            if (gizliKelime.charAt(i) == harf) {
                tahminEdilen.setCharAt(i, harf);
                dogruTahmin = true;
            }
        }
        return dogruTahmin;
    }

    @Override
    public void baslat() {
        System.out.println("\"Adam Asmaca\" oyununa hoş geldiniz!");
        System.out.println("Tahmin Edilecek Kelime: " + getTahminEdilen());
    }

    @Override
    public void bitir() {
        System.out.println("Oyun sona erdi. Doğru kelime: " + gizliKelime);
    }
}
