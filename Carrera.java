
import java.util.*;

class Carrera {
    private final String nombre;
    private final int duracionAnios;
    private final Coordinador coordinador;
    private final double precioInscripcion;
    private final double precioCuota;
    private final List<Materia> materias = new ArrayList<>();

    public Carrera(String nombre, int duracionAnios, Coordinador coordinador, double precioInscripcion, double precioCuota) {
        this.nombre = nombre;
        this.duracionAnios = duracionAnios;
        this.coordinador = coordinador;
        this.precioInscripcion = precioInscripcion;
        this.precioCuota = precioCuota;
    }

    public String getNombre() { return nombre; }
    public int getDuracionAnios() { return duracionAnios; }
    public Coordinador getCoordinador() { return coordinador; }
    public double getPrecioInscripcion() { return precioInscripcion; }
    public double getPrecioCuota() { return precioCuota; }
    public List<Materia> getMaterias() { return Collections.unmodifiableList(materias); }

    public void agregarMateria(Materia m) {
        if (!materias.contains(m)) materias.add(m);
    }

    @Override
    public String toString() {
        return nombre + " (" + duracionAnios + " años) - Coord.: " + coordinador.getApellido();
    }
}
