package com.sale.task;

import com.sale.task.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class MyController {

    private final MyService myService;

    @Autowired
    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/party")
    List<Party> getAllParty() {
        partyPageOpen();
        return myService.getAllParty();
    }

    @PostMapping("/party")
    @ResponseBody
    Party createParty(@RequestBody Party party) {
        myService.saveParty(party);
        return party;
    }

    @GetMapping("/party/{id}")
    Party getPartyById(@PathVariable int id) {
        partyPageOpen();
        return myService.getPartyById(id);
    }


    @GetMapping("/item")
    @ResponseBody
    List<Item> getAllItemWithParameters(@RequestParam(required = false) Integer serialNumber,
                                        @RequestParam(required = false) Integer limit,
                                        @RequestParam(required = false) Integer offset) {
        return myService.getAllItem(serialNumber, limit, offset);
    }

    @PostMapping("/item")
    Item createItem(@RequestBody Item item) {
        myService.saveItem(item);
        return item;
    }

    @GetMapping("/item/{id}")
    Item getItemById(@PathVariable int id) {
        return myService.getItemById(id);
    }

    @PostMapping("/item/{id}/sale")
        //Продажа item. Означает, что необходимо в поле ownerid поставить значение null и item исключается из всех вышестояших
        // item - parent_id. Продавать можно только item.type=item.
        // После продажи необходимо проверить если этот Item был последний в агрегации вышестоящего уровня (parent, parentId).
        // Если был последним, то необходимо для вышестоящего item проставть owner=null, parentId=null и так до самого верха иерархии.
        // + необходимо скорректировать все childrenCount по иерархии.
    void saleItem(@PathVariable int id) {
        myService.saleItem(id);
    }

    @GetMapping("/party/open")
    void partyPageOpen() {
        myService.saveNewPageOpen();
    }

    @GetMapping("/party/open/stat")
    List<PageOpen> getAllPageOpen() {
        return myService.getAllPageOpen();
    }
}
