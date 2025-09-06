
class Materia {
    private final String nombre;
    private final int curso;
    private final int cuatrimestre;
    private final Profesor profesor;
    private final Carrera carrera;

    public Materia(String nombre, int curso, int cuatrimestre, Profesor profesor, Carrera carrera) {
        this.nombre = nombre;
        this.curso = curso;
        this.cuatrimestre = cuatrimestre;
        this.profesor = profesor;
        this.carrera = carrera;
    }

    public String getNombre() { return nombre; }
    public int getCurso() { return curso; }
    public int getCuatrimestre() { return cuatrimestre; }
    public Profesor getProfesor() { return profesor; }
    public Carrera getCarrera() { return carrera; }

    @Override
    public String toString() {
        return nombre + " - " + curso + "º año - " + cuatrimestre + "º cuat.";
    }
}
