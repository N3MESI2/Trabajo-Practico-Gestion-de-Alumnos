
class Alumno extends Persona {
    private final int legajo;

    public Alumno(String nombre, String apellido, String dni, int legajo) {
        super(nombre, apellido, dni);
        this.legajo = legajo;
    }

    public int getLegajo() { return legajo; }

    @Override
    public String toString() {
        return super.toString() + " - Legajo " + legajo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alumno)) return false;
        return this.legajo == ((Alumno) o).legajo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(legajo);
    }
}
