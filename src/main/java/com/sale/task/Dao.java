package com.sale.task;

import com.sale.task.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("myDao")
public class Dao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Party> getAllParty() {
        String sql = "SELECT * FROM party;";
        return jdbcTemplate.query(sql, new PartyRowMapper());
    }

    public Party getPartyById(int id) {
        String sql = "SELECT * FROM party WHERE id=" + id;
        try {
            return jdbcTemplate.queryForObject(sql, new PartyRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public void saveParty(Party party) {
        String sql = "INSERT INTO `mydb`.`party`\n" +
                "(`name`,`create_date`,`version`)" +
                "VALUES (?,?,?);";
        jdbcTemplate.update(sql, party.getName(), party.getDate(), party.getVersion());
    }

    public List<Item> getAllItem(Integer serialNumber, Integer limit, Integer offset) {
        String sql = "SELECT @a:=@a+1 serial_number, id, parent_id, owner_id,serial,type,children_count,create_date FROM item , (SELECT @a:=0) AS a \n" +
                "WHERE parent_id IS NULL AND owner_id IS NOT NULL AND TYPE=2 ";
        if (serialNumber != null) {
            sql += "HAVING serial_number = " + serialNumber;
        }
        if (limit != null) {
            sql += " LIMIT " + limit;
        }
        if (offset != null) {
            sql += " OFFSET " + offset;
        }
        try {
            return jdbcTemplate.query(sql, new ItemRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Item getItemById(int id) {
        String sql = "SELECT * FROM item WHERE ID=" + id;
        try {
            return jdbcTemplate.queryForObject(sql, new ItemRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void saveItem(Item item) {
        String sql = "INSERT INTO `mydb`.`item`\n" +
                "(`owner_id`,`parent_id`,`serial`,`type`,`children_count`,`create_date`)" +
                "VALUES (?,?,?,?,?,?);";
        jdbcTemplate.update(sql, item.getOwnerId(), item.getParentId(), item.getSerial(), item.getType().getNum(), item.getChildrenCount(), item.getCreateDate());
    }

    public List<PageOpen> getAllPageOpen() {
        String sql = "SELECT * FROM pageopen;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new PageOpen(rs.getInt("id"), rs.getDate("date")));
    }

    public void saveNewPageOpen() {
        String sql = "INSERT INTO `pageopen` (`date`) VALUES (now())";
        jdbcTemplate.execute(sql);
    }

    public void saleItem(int id) {
        updateSoldItem(id);
        updateSoldItemDown(id);
        updateSoldItemUp(id);
    }

    private void updateSoldItem(int id) {
        String sql = "UPDATE item SET owner_id = NULL, children_count=0 WHERE id = ? AND `type` = 1";
        jdbcTemplate.update(sql, id);
    }

    private void updateSoldItemDown(int id) {
        String sql = "UPDATE item SET children_count = children_count - 1 WHERE type=1 AND id IN (select temp.id from (with recursive cte(id,parent_id,children_count) as \n" +
                "(select id, parent_id,children_count from item where id = ?\n" +
                "union all\n" +
                "select p.id, p.parent_id,p.children_count\n" +
                "from item p\n" +
                "inner join cte on p.id = cte.parent_id)\n" +
                "select * from cte where id != ?) as temp);";
        jdbcTemplate.update(sql, id, id);
    }

    private void updateSoldItemUp(int id) {
        String sql = "UPDATE item SET owner_id = NULL, parent_id = null, children_count = 0 WHERE `type`=1 AND id IN (select temp.id \n" +
                "from (with recursive cte(id,owner_id,parent_id,children_count) as \n" +
                "(select t.id,t.owner_id,t.parent_id,t.children_count from item o left join item t on o.id=t.parent_id where o.id=?\n" +
                "union all\n" +
                "select p.id,p.owner_id, p.parent_id,p.children_count\n" +
                "from item p\n" +
                "inner join cte on p.parent_id = cte.id)\n" +
                "select * from cte) as temp);";
        jdbcTemplate.update(sql, id);
    }
}
