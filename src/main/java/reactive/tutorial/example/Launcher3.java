package reactive.tutorial.example;

import io.reactivex.Observable;

import java.util.List;

public class Launcher3 {
    public static void main(String[] args) {
        List<Warehouse> warehouses = null; //get warehouses

        Observable.fromIterable(warehouses)
                .flatMap(Warehouse::getProducts)
                .map(ProductStock::getQuantity)
                .reduce(0, (total, next) -> total + next)
                .subscribe(i -> System.out.println("There are " + i + " units in inventory"));
    }

    interface Warehouse {
        Observable<ProductStock> getProducts();
    }

    interface ProductStock {
        int getId();
        String getDescription();
        int getQuantity();
    }
}