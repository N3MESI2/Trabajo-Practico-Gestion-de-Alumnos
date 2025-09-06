
import java.util.*;

class Universidad {
    private final String nombre;
    private final Map<Integer, Alumno> alumnosPorLegajo = new HashMap<>();
    private final List<Carrera> carreras = new ArrayList<>();
    private final Map<Alumno, Set<Carrera>> matriculas = new HashMap<>();
    private final Map<Materia, List<InscripcionMateria>> inscripcionesPorMateria = new HashMap<>();

    public Universidad(String nombre) { this.nombre = nombre; }

    public String getNombre() { return nombre; }
    public List<Carrera> getCarreras() { return Collections.unmodifiableList(carreras); }
    public Collection<Alumno> getAlumnos() { return Collections.unmodifiableCollection(alumnosPorLegajo.values()); }

    public void agregarCarrera(Carrera c) { if (!carreras.contains(c)) carreras.add(c); }
    public void agregarAlumno(Alumno a) { alumnosPorLegajo.put(a.getLegajo(), a); }
    public Alumno buscarAlumnoPorLegajo(int legajo) { return alumnosPorLegajo.get(legajo); }

    public void matricularAlumno(Alumno alumno, Carrera carrera) {
        matriculas.putIfAbsent(alumno, new HashSet<>());
        matriculas.get(alumno).add(carrera);
    }

    public boolean estaMatriculado(Alumno alumno, Carrera carrera) {
        return matriculas.getOrDefault(alumno, Collections.emptySet()).contains(carrera);
    }

    public void inscribirAlumnoEnMateria(Alumno alumno, Materia materia) {
        if (!estaMatriculado(alumno, materia.getCarrera())) {
            throw new IllegalStateException("El alumno no está matriculado en la carrera de la materia.");
        }
        inscripcionesPorMateria.putIfAbsent(materia, new ArrayList<>());
        List<InscripcionMateria> lista = inscripcionesPorMateria.get(materia);
        boolean yaInscripto = lista.stream().anyMatch(im -> im.getAlumno().equals(alumno));
        if (yaInscripto) throw new IllegalStateException("El alumno ya está inscripto en esta materia.");
        lista.add(new InscripcionMateria(alumno, materia));
    }

    public List<InscripcionMateria> obtenerInscripciones(Materia materia) {
        return Collections.unmodifiableList(inscripcionesPorMateria.getOrDefault(materia, Collections.emptyList()));
    }

    public List<InscripcionMateria> obtenerInscripcionesPorEstado(Materia materia, boolean soloFinalizados) {
        List<InscripcionMateria> todas = obtenerInscripciones(materia);
        if (!soloFinalizados) return todas;
        List<InscripcionMateria> res = new ArrayList<>();
        for (InscripcionMateria im : todas) if (im.getEstadoFinal() != EstadoFinal.PENDIENTE) res.add(im);
        return res;
    }

    public void registrarFalta(Alumno alumno, Materia materia) {
        List<InscripcionMateria> lista = inscripcionesPorMateria.get(materia);
        if (lista == null) throw new IllegalStateException("No hay inscripciones para la materia.");
        for (InscripcionMateria im : lista) {
            if (im.getAlumno().equals(alumno)) { im.registrarFalta(); return; }
        }
        throw new IllegalStateException("El alumno no está inscripto en la materia.");
    }

    public void cerrarCursado(Alumno alumno, Materia materia, EstadoFinal estado, int inasistenciasFinales) {
        List<InscripcionMateria> lista = inscripcionesPorMateria.get(materia);
        if (lista == null) throw new IllegalStateException("No hay inscripciones para la materia.");
        for (InscripcionMateria im : lista) {
            if (im.getAlumno().equals(alumno)) { im.cargarCierre(estado, inasistenciasFinales); return; }
        }
        throw new IllegalStateException("El alumno no está inscripto en la materia.");
    }

    public java.util.List<InscripcionMateria> todasLasInscripciones() {
        return inscripcionesPorMateria
            .values()
            .stream()
            .flatMap(java.util.Collection::stream)
            .collect(java.util.stream.Collectors.toList());
    }

    public void limpiarTodo() {
        alumnosPorLegajo.clear();
        carreras.clear();
        matriculas.clear();
        inscripcionesPorMateria.clear();
    }
}
