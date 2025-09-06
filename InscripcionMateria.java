
class InscripcionMateria {
    private final Alumno alumno;
    private final Materia materia;
    private EstadoFinal estadoFinal = EstadoFinal.PENDIENTE;
    private int inasistencias = 0;

    public InscripcionMateria(Alumno alumno, Materia materia) {
        this.alumno = alumno;
        this.materia = materia;
    }

    public Alumno getAlumno() { return alumno; }
    public Materia getMateria() { return materia; }
    public EstadoFinal getEstadoFinal() { return estadoFinal; }
    public int getInasistencias() { return inasistencias; }

    public void registrarFalta() { inasistencias++; }
    public void cargarCierre(EstadoFinal e, int faltas) {
        this.estadoFinal = e;
        this.inasistencias = faltas;
    }
}
