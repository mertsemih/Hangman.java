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
        return tahminEdilen.toString();
    }

    public boolean tahminYap(char harf) {
        boolean dogruTahmin = false;
        for (int i = 0; i < gizliKelime.length(); i++) {
            if (Character.toLowerCase(gizliKelime.charAt(i)) == Character.toLowerCase(harf)) {
                tahminEdilen.setCharAt(i, gizliKelime.charAt(i));
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
