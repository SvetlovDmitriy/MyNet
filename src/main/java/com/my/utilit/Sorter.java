package com.my.utilit;

import com.my.db.entity.Product;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sorter {

    private static final Comparator<Product> compareByPrice = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            return Double.compare(p1.getPrice(), p2.getPrice());
        }
    };

    private static final Comparator<Product> compareByName = new Comparator<Product>() {
        public int compare(Product p1, Product p2){
            return p1.getName().compareTo(p2.getName());
        }
    };

    private static final Comparator<Product> compareByPriceRevers = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            return - Double.compare(p1.getPrice(), p2.getPrice());
        }
    };

    private static final Comparator<Product> compareByNameRevers = new Comparator<Product>() {
        public int compare(Product p1, Product p2){
            return - p1.getName().compareTo(p2.getName());
        }
    };

    public static void sortByPrice(List<Product> list){
        Collections.sort(list, compareByPrice);
    }

    public static void sortByName(List<Product> list){
        Collections.sort(list, compareByName);
    }

    public static void sortByPriceRevers(List<Product> list){
        Collections.sort(list, compareByPriceRevers);
    }

    public static void sortByNameRevers(List<Product> list){
        Collections.sort(list, compareByNameRevers);
    }
}
