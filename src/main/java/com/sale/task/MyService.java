package com.sale.task;

import com.sale.task.model.Item;
import com.sale.task.model.PageOpen;
import com.sale.task.model.Party;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyService {

    private final Dao dao;

    @Autowired
    public MyService(Dao dao) {
        this.dao = dao;
    }

    public List<Party> getAllParty() {
        return dao.getAllParty();
    }

    public Party getPartyById(int id) {
        return dao.getPartyById(id);
    }

    public void saveParty(Party party) {
        dao.saveParty(party);
    }

    public List<Item> getAllItem(Integer serialNumber, Integer limit, Integer offset) {
        return dao.getAllItem(serialNumber, limit, offset);
    }

    public Item getItemById(int id) {
        return dao.getItemById(id);
    }

    public void saveItem(Item item) {
        dao.saveItem(item);
    }

    public List<PageOpen> getAllPageOpen() {
        return dao.getAllPageOpen();
    }

    public void saveNewPageOpen() {
        dao.saveNewPageOpen();
    }

    public void saleItem(int id) {
        dao.saleItem(id);
    }
}
