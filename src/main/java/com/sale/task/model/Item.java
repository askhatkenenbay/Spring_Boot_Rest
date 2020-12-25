package com.sale.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private int id;
    private Integer parentId;
    private Integer ownerId;
    private String serial;
    private ItemType type;
    private Integer childrenCount;
    private Date createDate;
}
