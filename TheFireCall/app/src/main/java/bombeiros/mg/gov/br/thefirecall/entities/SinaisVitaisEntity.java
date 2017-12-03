package bombeiros.mg.gov.br.thefirecall.entities;

import java.math.BigDecimal;

import bombeiros.mg.gov.br.thefirecall.database.SinaisVitais;

/**
 * Created by Hiago Paraguaçú on 02/12/2017.
 *
 */

public class SinaisVitaisEntity {
    private int id;
    private int id_ocorrencia;
    private String pressao;
    private String frequencia_cardiaca;
    private String saturacao_oxigenio;
    private String temperatura;

    public SinaisVitaisEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_ocorrencia() {
        return id_ocorrencia;
    }

    public void setId_ocorrencia(int id_ocorrencia) {
        this.id_ocorrencia = id_ocorrencia;
    }

    public String getPressao() {
        return pressao;
    }

    public void setPressao(String pressao) {
        this.pressao = pressao;
    }

    public String getFrequenciaCardiaca() {
        return frequencia_cardiaca;
    }

    public void setFrequenciaCardiaca(String frequenciaCardiaca) {
        this.frequencia_cardiaca = frequenciaCardiaca;
    }

    public String getSaturacaoOxigenio() {
        return saturacao_oxigenio;
    }

    public void setSaturacaoOxigenio(String saturacaoOxigenio) {
        this.saturacao_oxigenio = saturacaoOxigenio;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }
}
