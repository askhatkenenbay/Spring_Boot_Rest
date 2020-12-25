package com.sale.task;

import com.sale.task.model.Item;
import com.sale.task.model.ItemType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemRowMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getInt("id"));
        item.setOwnerId(resultSet.getInt("owner_id"));
        item.setParentId(resultSet.getInt("parent_id"));
        item.setChildrenCount(resultSet.getInt("children_count"));
        item.setSerial(resultSet.getString("serial"));
        item.setCreateDate(resultSet.getDate("create_date"));
        int type = resultSet.getInt("type");
        if (type == 1) {
            item.setType(ItemType.ITEM);
        } else {
            item.setType(ItemType.PACK);
        }
        return item;
    }
}
