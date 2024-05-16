package com.invernadero.fachadadatosexternos;

import DominioDatos.Datos;
import com.mycompany.dao_sensores.DatosDAO;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Jorge
 */
public class FachadaDatosExternos implements IFachada{

    DatosDAO datosServicio = null;
    
    public FachadaDatosExternos(){
        if(datosServicio == null){
            datosServicio = new DatosDAO("localhost", "3306", "datossensores", "root", "1234");
        }
    }
    
    @Override
    public Datos agregarDatos(Datos datos) {
        return datosServicio.agregarDatos(datos);
    }

    @Override
    public List<Datos> obtenerDatos() {
        return datosServicio.obtenerDatos();
    }

    @Override
    public List<Datos> obtenerDatosPorIdSensor(String idSensor) {
        return datosServicio.obtenerDatosPorIdSensor(idSensor);
    }

    @Override
    public List<Datos> obtenerDatosPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return datosServicio.obtenerDatosPorPeriodo(fechaInicio, fechaFin);
    }
    
}
