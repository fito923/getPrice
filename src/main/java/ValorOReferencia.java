public class ValorOReferencia {

private String param1 = new String();

/** Creates a new instance of PassValueOrReference */
public ValorOReferencia(String param1) {
this.setParam1(param1);
}

 public static void cambiarObjeto(ValorOReferencia objeto) {
 objeto = new ValorOReferencia("Este es un nuevo objeto.");
 System.out.println("Luego de \"reasignar\" pass: " + objeto);
 }

 public static void cambiarParam1(ValorOReferencia objeto) {
 objeto.setParam1("Este es un nuevo valor para param1.");
 }

 public static void main(String[] args) {
 ValorOReferencia pass =
 new ValorOReferencia("Objeto inicial.");
 System.out.println("Entender que Java pasa parámetros por valor: ");
 System.out.println("Antes de modificar pass es: " + pass);
 ValorOReferencia.cambiarObjeto(pass);
 System.out.println("De vuelta en main pass es: " + pass);
 System.out.println("Ahora vamos a cambiar sólo param1:");
 ValorOReferencia.cambiarParam1(pass);
 System.out.println("De seguro param 1 ha cambiado: " + pass);
 System.out.println("Parece difícil, pero no lo es.");
 }

 public String getParam1() {
 return param1;
 }

 public void setParam1(String param1) {
 this.param1 = param1;
 }

 public String toString() {
 return "[param1 = " + this.getParam1() + "]";
 }

 }