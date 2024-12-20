package com.gestor.eventos.dto;

import java.util.List;

public class EntradaRespuesta {
    private List<EntradaDTO> contenido;
    private int numeroPagina;
    private int medidaPagina;
    private Long totalElementos;
    private int totalPaginas;
    private boolean ultima;

    public EntradaRespuesta() {
    }

    public List<EntradaDTO> getContenido() {
        return contenido;
    }

    public void setContenido(List<EntradaDTO> contenido) {
        this.contenido = contenido;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public int getMedidaPagina() {
        return medidaPagina;
    }

    public void setMedidaPagina(int medidaPagina) {
        this.medidaPagina = medidaPagina;
    }

    public Long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(Long totalElementos) {
        this.totalElementos = totalElementos;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public boolean isUltima() {
        return ultima;
    }

    public void setUltima(boolean ultima) {
        this.ultima = ultima;
    }
}
