package com.silver.seed.query.showcase.web;

import com.silver.seed.query.Query;
import com.silver.seed.query.Table;
import com.silver.seed.query.showcase.repository.QueryRepository;
import com.silver.seed.query.showcase.service.QueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import java.util.List;
import java.util.concurrent.locks.Condition;

/**
 * @author Liaojian
 */
@Controller
@RequestMapping(value = "/setting")
public class QueryController {

    @Resource
    private QueryRepository queryRepository;

    @Resource
    private QueryService queryService;

    /*
    @RequestMapping(value = "{name}", method = RequestMethod.GET)
    public String template(@PathVariable String name) {
        Query query = queryRepository.findByName(name);
        System.out.println("query:" + query);
        return "query";
    }
    */

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create() {
        return "template";
    }

    @RequestMapping(value = "create/basic", method = RequestMethod.GET)
    public String basic() {
        return "basic";
    }
    /*
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String doCreate(@ModelAttribute Query query) {
        Query used = queryRepository.findByName(query.getName());
        if (used != null) {
            return "query-setting";
        } else {
            queryRepository.saveAndFlush(query);
            return "query-setting";
        }
    }
    */

    @RequestMapping(value = "create/store-basic-and-forward", method = RequestMethod.POST)
    public String storeBasicAndForward(@RequestBody Query query) {
        queryRepository.saveAndFlush(query);
        return "table";
    }

    @RequestMapping(value = "create/table", method = RequestMethod.GET)
    public String tableAndView() {
        return "table";
    }

    @RequestMapping(value = "create/store-table-and-forward", method = RequestMethod.POST)
    public String storeTableAndForward(@RequestBody Table[] tables) {
        return "condition";
    }

    @RequestMapping(value = "create/schemas", method = RequestMethod.GET)
    public void getSchemas(Model model) {
        List<String> schemas = queryService.getSchemas();
        model.addAttribute("schemas", schemas);
    }

    @RequestMapping(value = "create/tables", method = RequestMethod.GET)
    public void getTables(Model model) {
        List<String> tables = queryService.getTablesNames(null);
        model.addAttribute("tables", tables);
    }

    @RequestMapping(value = "tables/exists/{tableName}", method = RequestMethod.GET)
    public void tableExists(@PathVariable String tableName, Model model) {
        model.addAttribute("exists", queryService.tableExists(tableName));
    }

    public QueryService getQueryService() {
        return queryService;
    }

    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    public QueryRepository getQueryRepository() {
        return queryRepository;
    }

    public void setQueryRepository(QueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

}
