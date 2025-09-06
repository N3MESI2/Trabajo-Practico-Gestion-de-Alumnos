
public class Main {
    public static void main(String[] args) {
        Universidad uni;
        if (args.length > 0 && "--demo".equalsIgnoreCase(args[0])) {
            uni = DatosDemo.fabricarUniversidadDemo();   // modo demo
        } else {
            uni = new Universidad("Universidad Nacional"); // modo vacío
        }
        new MenuConsola(uni).iniciar();
    }
}
