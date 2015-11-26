package com.silver.seed.query.showcase.web;

import com.silver.seed.query.Condition;
import com.silver.seed.query.JoinColumns;
import com.silver.seed.query.Query;
import com.silver.seed.query.Table;
import com.silver.seed.query.showcase.repository.QueryRepository;
import com.silver.seed.query.showcase.service.QueryService;
import com.silver.seed.query.entity.ColumnVO;
import com.silver.wheel.common.exception.CodedRuntimeException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class TableAndColumnVO {
    private List<Table> tables;

    private List<JoinColumns> columns;

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<JoinColumns> getColumns() {
        return columns;
    }

    public void setColumns(List<JoinColumns> columns) {
        this.columns = columns;
    }
}



/**
 * @author Liaojian
 */
@Controller
@RequestMapping(value = "/setting")
@SessionAttributes(value = {QueryController.QUERY_SESSION_NAME, QueryController.WIZARD_SESSION_NAME},
        types = {Query.class, Wizard.class})
public class QueryController {

    public static final String QUERY_SESSION_NAME = "pendingQuery";

    public static final String WIZARD_SESSION_NAME = "wizard";

    public static final  String BASIC_NODE_NAME = "basic";

    public static final  String TABLE_NODE_NAME = "table";

    public static final  String CONDITION_NODE_NAME = "condition";

    public static final String COLUMN_NODE_NAME = "column";

    public static final String SUMMARY_NODE_NAME = "summary";

    @Resource
    private QueryRepository queryRepository;

    @Resource
    private QueryService queryService;

    /*
     组装model中的Wizard对象和Query对象。注意Wizard对象和Query对象已经在类的@SessionAttributes注解中指定，所以
     要在组装前判断是否存在，避免覆盖掉Session中的对象。
     */
    @ModelAttribute
    public void populateModel(Model model) {

        if(!model.containsAttribute(WIZARD_SESSION_NAME)) {
            model.addAttribute(WIZARD_SESSION_NAME, new Wizard(new String[] {
                    BASIC_NODE_NAME,TABLE_NODE_NAME, CONDITION_NODE_NAME, COLUMN_NODE_NAME,
                    SUMMARY_NODE_NAME}));
        }

        if(!model.containsAttribute(QUERY_SESSION_NAME)) {
            Query query = new Query();
            model.addAttribute(QUERY_SESSION_NAME, query);
        }
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create() {
        return "template";
    }

    @RequestMapping(value = "create/basic", method = RequestMethod.GET)
    public String basic(Model model) {
        return BASIC_NODE_NAME;
    }

    @RequestMapping(value = "create/store-basic-and-forward", method = RequestMethod.POST)
    public String storeBasicAndForward(@RequestBody Query query, @ModelAttribute(value = QUERY_SESSION_NAME) Query pendingQuery,
                                       @ModelAttribute(value = WIZARD_SESSION_NAME) Wizard wizard) {

        try {
            BeanUtils.copyProperties(pendingQuery, query);
        } catch (IllegalAccessException e) {
            throw new CodedRuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new CodedRuntimeException(e);
        }

        queryRepository.saveAndFlush(query);

        wizard.goForward();

        return "table";
    }

    @RequestMapping(value = "create/table", method = RequestMethod.GET)
    public String tableAndView(@ModelAttribute(WIZARD_SESSION_NAME) Wizard wizard) {
        return goForwardCurrentNode(TABLE_NODE_NAME, wizard);
    }

    @RequestMapping(value = "create/store-table-and-forward", method = RequestMethod.POST)
    public String storeTableAndForward(@RequestBody TableAndColumnVO tc, @ModelAttribute(value = WIZARD_SESSION_NAME) Wizard wizard,
                                       @ModelAttribute(value = QUERY_SESSION_NAME) Query query) {
        queryService.saveTableAndJoindedColumns(tc.getTables(), tc.getColumns());

        query.setTables(tc.getTables());
        query.setJoinColumnses(tc.getColumns());

        wizard.goForward();

        return CONDITION_NODE_NAME;
    }

    @RequestMapping(value = "create/condition", method = RequestMethod.GET)
    public String condition(@ModelAttribute(WIZARD_SESSION_NAME) Wizard wizard) {
        return goForwardCurrentNode(CONDITION_NODE_NAME, wizard);
    }

    @RequestMapping(value = "create/store-condition-and-forward", method = RequestMethod.POST)
    public String storeConditionAndForward(@RequestBody List<Condition> conditions, @ModelAttribute(value = WIZARD_SESSION_NAME) Wizard wizard,
                                           @ModelAttribute(value = QUERY_SESSION_NAME) Query query) {
        System.out.println(conditions);
        return COLUMN_NODE_NAME;
    }

    /*
    @RequestMapping(value = "create/schemas", method = RequestMethod.GET)
    public void getSchemas(Model model) {
        List<String> schemas = queryService.getSchemas();
        model.addAttribute("schemas", schemas);
    }
    */

    @RequestMapping(value = "create/tables", method = RequestMethod.GET)
    public void getTables(Model model) {
        List<String> tables = queryService.getTablesNames(null);
        model.addAttribute("tables", tables);
    }


    @RequestMapping(value = "create/columns/{table}", method = RequestMethod.GET)
    public void getColumns(@PathVariable String table, Model model) {
        List<ColumnVO> columns = queryService.getColumns(table);
        model.addAttribute("columns", columns);
    }

    @RequestMapping(value = "create/tablesSelected")
    public void getTablesSelected() {
        return;
    }

    @RequestMapping(value = "tables/exists/{tableName}", method = RequestMethod.GET)
    public void tableExists(@PathVariable String tableName, Model model) {
        model.addAttribute("exists", queryService.tableExists(tableName));
    }


    private String goForwardCurrentNode(String viewName, Wizard wizard) {
        if(wizard.getCurrentWizardNode().getName().equals(viewName)) {
            return viewName;
        }
        return wizard.getBeginNode().getName();
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
