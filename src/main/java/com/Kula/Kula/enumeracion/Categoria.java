package com.Kula.Kula.enumeracion;



    
public enum Categoria{
    Violencia_de_genero("Violencia de Género"),
    Maltrato_infantil("Maltrato Infantil"),
    Educacion("Educación"),
    Maltrato_animal("Maltrato Animal"),
    Otros("Otros");
    private String nombre;

    private Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
