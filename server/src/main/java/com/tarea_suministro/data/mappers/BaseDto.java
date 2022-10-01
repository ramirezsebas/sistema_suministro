package com.tarea_suministro.data.mappers;

import com.tarea_suministro.utils.JsonUtil;

public class BaseDto {
    int tipoOperacion;
    String nis;
    double consumo;

    public BaseDto() {
    }

    public int getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(int tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    @Override
    public String toString() {
        return "BaseDto [tipoOperacion=" + tipoOperacion + ", nis=" + nis + ", consumo=" + consumo + "]";
    }

    public static BaseDto fromJson(String json) {
        return JsonUtil.fromJson(json, BaseDto.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

}
