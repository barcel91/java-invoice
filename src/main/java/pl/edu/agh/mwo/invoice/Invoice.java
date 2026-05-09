package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

//    public void addProduct(Product product, Integer quantity) {
//        if (product == null || quantity <= 0) {
//            throw new IllegalArgumentException();
//        }
//        products.put(product, quantity);
//    }

    public void addProduct(Product product, Integer quantity) {

        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }

        if (products.containsKey(product)) {

            int currentQuantity = products.get(product);

            products.put(product, currentQuantity + quantity);

        } else {

            products.put(product, quantity);
        }
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    static int NUMBER = 0;

    private int number;

    public Invoice() {
        this.number = NUMBER += 1;
    }

    public int getNumber() {
        return number;
    }

    public String printInvoice() {

        StringBuilder text = new StringBuilder();

        text.append("Numer faktury: ")
                .append(getNumber())
                .append("\n");

        for (Product p : products.keySet()) {

            int quantity = products.get(p);

            text.append(p.getName())
                    .append(" | ")
                    .append(quantity)
                    .append(" szt. | ")
                    .append(p.getPrice())
                    .append(" pln\n");
        }

        text.append("Ilość pozycji na fakturze: ")
                .append(getPositionsCount())
                .append("\n");

        text.append("Suma brutto wszystkich produktów: ")
                .append(getGrossTotal())
                .append(" pln\n")
                .append("-----------------------------------------------------\n");

        return text.toString();
    }

    public int getPositionsCount() {

        int sum = 0;

        for (int qty : products.values()) {
            sum += qty;
        }

        return sum;
    }

}
