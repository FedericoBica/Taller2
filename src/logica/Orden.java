package logica;

import java.io.Serializable;

public class Orden implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private ItemOrden items[];
    private int tope;

    public Orden(int max) {
    	items = new ItemOrden[max];
        tope = 0;
    }
    
    public int getTope() {
        return tope;
    }
    
    public ItemOrden[] getItems() {
        return items;
    }
    
    public ItemOrden getItem(int i) {
        return items[i];
    }

    public boolean insBack(ItemOrden item) {
        if (tope < items.length) {
            items[tope] = item;
            tope++;
            return true;
        }
        return false;
    }
    
    public ItemOrden find(String codPostre) {
    	for (int i = 0; i < tope; i++) {
    		if (items[i].getPostre().getNombre().equals(codPostre)) {
                return items[i];
            }
        }
        return null;
    }
    
    public boolean deleteOrden(String codPostre) {
        for (int i = 0; i < tope; i++) {
            if (items[i].getPostre().getCodigo().equals(codPostre)) {
                for (int j = i; j < tope - 1; j++) {
                    items[j] = items[j + 1];
                }
                items[tope-1] = null;
                return true;
            }
        }
        return false;
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