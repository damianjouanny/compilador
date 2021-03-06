/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import filereader.SourceCode;
import herramientaerror.EventoError;
import java.io.Console;
import lexico.AnalizadorLexico;
import sintactico.Parser;

/**
 *
 * @author Gabriel
 */
public class CompiladorManager {
    private EventoError eventoError;
    private Parser parser;
    private AnalizadorLexico analizadorLexico;
        
    CompiladorManager() {
        this.eventoError = new EventoError();
        this.parser = new Parser(false);
        this.parser.addEventoError(this.eventoError);
        this.analizadorLexico = new AnalizadorLexico(this.eventoError);
        this.parser.addAnalizadorLexico(this.analizadorLexico);
    }
    
    public void start() {
        Console console = System.console();
        execLoop(console);
    }
    
    public  void execLoop(Console console) {
        boolean cont = true;
        while (cont) {
            String filePath = console.readLine("Ingrese el Path del archivo a leer: ");
            SourceCode s = new SourceCode(filePath);
            String strCont;
            if (s.generateSource() != 0) {
                System.out.println("El archivo no puede ser alcanzado");
                strCont = console.readLine("Desea ingresar otro Path? (Y/N): ");
            } else {
                analizar(s, console);
                 strCont = console.readLine("Realizar otro analisis? (Y/N): ");
            }
            
            if (!strCont.equals("Y") && !strCont.equals("y")) {
                cont = false;
            }
        }
    }
    
    private void prepareParser(String buffer) {
        this.analizadorLexico.setBuffer(buffer);
        this.parser = new Parser(false);
        this.parser.addEventoError(this.eventoError);
        this.parser.addAnalizadorLexico(this.analizadorLexico);
    }
    
    public  void analizar(SourceCode s, Console console) {
        this.prepareParser(s.getAsString());
        this.parser.parse();
        System.out.println("*************************");
        System.out.println("Resultado del análisis: ");
        if (this.eventoError.hayErrores()) {
            System.out.println("Fallido - Errores");
        } else {
            System.out.println("Exitoso - Sin errores");
        }
        System.out.println("*************************");
        this.eventoError.visualizar();
        System.out.println("*************************");
        this.analizadorLexico.visualizarTablaSimbolos();
    }
}
