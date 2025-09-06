
class DatosDemo {
    public static Universidad fabricarUniversidadDemo() {
        Universidad uni = new Universidad("Universidad Nacional");
        Coordinador coord1 = new Coordinador("Laura", "Benítez", "31.123.456");
        Coordinador coord2 = new Coordinador("Sergio", "Gómez", "28.987.321");
        Carrera ing = new Carrera("Ingeniería en Sistemas", 5, coord1, 50000, 25000);
        Carrera lic = new Carrera("Licenciatura en Administración", 4, coord2, 45000, 22000);
        uni.agregarCarrera(ing); uni.agregarCarrera(lic);

        Profesor p1 = new Profesor("Ana", "Martínez", "24.111.222");
        Profesor p2 = new Profesor("Carlos", "Ríos", "26.222.333");
        Profesor p3 = new Profesor("Luis", "Pérez", "30.333.444");

        Materia prog1 = new Materia("Programación I", 1, 1, p1, ing);
        Materia anal1 = new Materia("Análisis Matemático I", 1, 2, p2, ing);
        Materia econ1 = new Materia("Economía I", 1, 1, p3, lic);
        ing.agregarMateria(prog1); ing.agregarMateria(anal1);
        lic.agregarMateria(econ1);

        Alumno a1 = new Alumno("Sofía", "Pérez", "40.111.222", 1001);
        Alumno a2 = new Alumno("Juan", "Ramírez", "39.333.444", 1002);
        uni.agregarAlumno(a1); uni.agregarAlumno(a2);

        uni.matricularAlumno(a1, ing);
        uni.matricularAlumno(a2, lic);
        uni.inscribirAlumnoEnMateria(a1, prog1);
        uni.inscribirAlumnoEnMateria(a2, econ1);

        return uni;
    }
}
