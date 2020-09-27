package com.example.dax30;

import java.util.Objects;

public class Share {

    private String name;
    private String url;
    private String isin;
    private String urlDetail;
    private String valor;
    private int nummer;

    public Share(String name, String url, String isin, String urlDetail, String valor, int nummer) {
        this.name = name;
        this.url = url;
        this.isin = isin;
        this.urlDetail = urlDetail;
        this.valor = valor;
        this.nummer = nummer;
    }


    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getIsin() {
        return isin;
    }

    public String getUrlDetail() {
        return urlDetail;
    }

    public String getValor() {
        return valor;
    }

    public int getNummer() {
        return nummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Share share = (Share) o;
        return Objects.equals(name, share.name) &&
                Objects.equals(url, share.url) && Objects.equals(isin, share.isin) && Objects.equals(urlDetail, share.urlDetail) && Objects.equals(valor, share.valor) && Objects.equals(nummer, share.nummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, isin, urlDetail, valor, nummer);
    }
}
