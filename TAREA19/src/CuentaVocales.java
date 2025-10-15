/**
 * ESTA CLASE EXTIENDE THREAD PARA PERMITIR LA EJECUCIÓN CONCURRENTE, REPRESENTA ADEMÁS EL HILO QUE SE ENCARGA DE CONTAR LA VOCAL ESPECÍFICA EN UN TEXTO.
 * IMPLEMENTA SYNCHRONIZED PARA GARANTIZAR QUE SOLO UN HILO A LA VEZ PUEDA EJECUTAR UNA PARTE CRÍTICA DEL CÓDIGO.
 */
public class CuentaVocales extends Thread {

    /**
     * DECLARAMOS VARIABLES Y EL OBJETO "lock".
     * muchoTexto -> TEXTO QUE TODOS LOS HILOS DEBEN ANALIZAR.
     * totalVocales -> VARIABLE QUE ACUMULA EL TOTAL DE VOCALES ENCONTRADAS POR LOS HILOS.
     * lock -> OBJETO PRIVADO, ESTÁTICO Y FINAL USADO COMO "CANDADO" PARA LA SINCRONIZACIÓN Y EL ACCESO A ESA PARTE CRÍTICA DEL CÓDIGO.
     * vocal -> VOCAL ESPECÍFICA QUE CADA HILO DEBE DE CONTAR (a, e, i, o, u).
     */
    private static String muchoTexto;
    private static int totalVocales = 0;
    private static final Object lock = new Object();
    private final char vocal;

    /**
     * CONSTRUCTOR DE LA CLASE "CuentaVocales"
     * @param vocal -> VOCAL QUE EL HILO CONTARÁ, CONVERSIÓN A MINÚSCULA PARA SIMPLIFICAR LA LÓGICA Y MEJORAR LA EFICIENCIA.
     */
    public CuentaVocales(char vocal) {
        this.vocal = Character.toLowerCase(vocal);
    }

    /**
     * ESTABLECE EL TEXTO INTRODUCIDO PARA ASÍ SER ANALIZADO POR LOS HILOS, CONVERSIÓN A MINÚSCULAS DE NUEVO PARA SIMPLIFICAR LA LÓGICA DE COMPARACIÓN.
     *     @param muchoTexto -> TEXTO DE ENTRADA QUE INTRODUCE EL USUARIO.
     */
    public static void setTexto(String muchoTexto) {
        CuentaVocales.muchoTexto = muchoTexto.toLowerCase();
    }

    /**
     * DEVUELVE EL VALOR FINAL DEL CONTADOR DE VOCALES, ESTE MÉTODO DEBE LLAMARSE UNA VEZ LOS HILOS HAYAN FINALIZADO.
     *     @return -> QUE RETORNE EL NÚMERO TOTAL DE VOCALES EN EL TEXTO INTRODUCIDO.
     */
    public static int getTotalVocales() {
        return totalVocales;
    }

    /**
     * SOBRESCRIBIMOS EL MÉTODO RUN DE LA PROPIA CLASE THREAD CONTANDO CUÁNTAS VECES APARECE SU VOCAL ASIGNADA EN EL TEXTO ACTUALIZANDO EL TOTAL COMPARTIDO.
     * ESTO LO HACE MEDIANTE EL BLOQUE SYNCHRONIZED.
     */
    @Override
    public void run() {

        // DECLARAMOS UN CONTADOR PARA LLEVAR EL NÚMERO DE LAS OCURRENCIAS DE LA VOCAL ASIGNADA.
        int contador = 0;

        // RECORRE CADA CARÁCTER DEL TEXTO CONVIRTIÉNDOLO EN UN ARRAY DE CARACTERES.
        for (char c : muchoTexto.toCharArray()) {
            // COMPARA EL CARÁCTER DE CADA ITERACIÓN PARA VALIDAR SI SE TRATA DE LA VOCAL ASIGNADA PARA ESE HILO.
            if (c == vocal) {
                // DE CUMPLIRSE ESTA CONDICIÓN, AUMENTAMOS EL CONTADOR.
                contador++;
            }
        }

        // PARTE CRÍTICA DEL CÓDIGO, LA ACTUALIZACIÓN DE LA VARIABLE COMPARTIDA "totalVocales".
        synchronized (lock) {

            // LA OPERACIÓN DE SUMAR EL RESULTADO LOCAL DE CADA CUENTA POR PARTE DE CADA HILO SE IDENTIFICA COMO UNA OPERACIÓN NO ATÓMICA ->
            // POR LO TANTO... REQUIERE SINCRONIZACIÓN!!
            totalVocales += contador;

            // IMPRIMIMOS CUÁNTAS OCURRENCIAS ENCONTRÓ CADA HILO.
            System.out.println("EL HILO PARA LA VOCAL '" + vocal + "' CONTÓ " + contador + " OCURRENCIAS.");
        }
    }
}