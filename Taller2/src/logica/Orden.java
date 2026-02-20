package logica;

public class Orden {

    private ItemOrden items[];
    private int tope;

    public Orden() {}

    public boolean InsBack(ItemOrden item) {
        if (tope < items.length) {
            items[tope] = item;
            tope++;
            return true;
        }
        return false;
    }
    
    public ItemOrden find(String nombrePostre) {
    	for (int i = 0; i < tope; i++) {
            if (items[i].getPostre().getNombre() == nombrePostre) {
                return items[i];
            }
        }
        return null;
    }
   
    
    public int calcularCantTotal() {
    	 int total = 0;
         for (int i = 0; i < tope; i++) {
             total += items[i].getCantidad();
         }
         return total;
    }
    
    public float calcularMontoTotal() {
        float total = 0;
        for (int i = 0; i < tope; i++) {
            total += items[i].calcularSubtotal();
        }
        return total;
    }
}