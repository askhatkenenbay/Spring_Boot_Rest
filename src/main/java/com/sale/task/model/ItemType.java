package com.sale.task.model;

public enum ItemType {
    PACK(2), ITEM(1);
    int num;

    ItemType(int i) {
        this.num = i;
    }

    public int getNum() {
        return num;
    }
}
