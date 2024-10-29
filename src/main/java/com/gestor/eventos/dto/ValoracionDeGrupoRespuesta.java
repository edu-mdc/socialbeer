package com.gestor.eventos.dto;

import java.util.List;

public class ValoracionDeGrupoRespuesta {
    private List<ValoracionDeGrupoDTO> contenido;
    private int numeroPagina;
    private int medidaPagina;
    private Long totalElementos;
    private int totalPaginas;
    private boolean ultima;

    public ValoracionDeGrupoRespuesta() {
    }

    public List<ValoracionDeGrupoDTO> getContenido() {
        return contenido;
    }

    public void setContenido(List<ValoracionDeGrupoDTO> contenido) {
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

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public Long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(Long totalElementos) {
        this.totalElementos = totalElementos;
    }

    public boolean isUltima() {
        return ultima;
    }

    public void setUltima(boolean ultima) {
        this.ultima = ultima;
    }
}
