package com.namvn.shopping.persistence.model;

public class RevenueInfo {
    float count;
    float revenue;

    public RevenueInfo(float count, float revenue) {
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
}
