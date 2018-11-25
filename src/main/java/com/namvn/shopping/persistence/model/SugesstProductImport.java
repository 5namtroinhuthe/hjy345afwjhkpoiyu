package com.namvn.shopping.persistence.model;



public class SugesstProductImport extends ProductManager implements Comparable {
    private float count;
    private float revenue;

    public SugesstProductImport(String productId, String name, String detail, int quantity, float count, float revenue) {
        super(productId, name, detail, quantity);
        this.count = count;
        this.revenue = revenue;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }




    @Override
    public int compareTo(Object o) {
        float compareage=((SugesstProductImport)o).getRevenue();
        return (int) (compareage-this.getRevenue());
    }
}
