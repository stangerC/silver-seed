package com.silver.seed.admin.query.web.controller;

import com.silver.seed.admin.query.web.WizardNode;
import com.silver.seed.query.entity.*;
import com.silver.seed.admin.query.service.QueryService;
import com.silver.seed.admin.query.web.Wizard;
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
@RequestMapping(value = "/query/setting")
@SessionAttributes(value = {QueryController.QUERY_SESSION_NAME, QueryController.WIZARD_SESSION_NAME},
        types = {Query.class, Wizard.class})
public class QueryController {

    public static final String QUERY_SESSION_NAME = "pendingQuery";

    public static final String WIZARD_SESSION_NAME = "wizard";

    public static final String BASIC_NODE_NAME = "basic";

    public static final String BASIC_VIEW_NAME = "/query/basic";

    public static final String TABLE_NODE_NAME = "table";

    public static final String TABLE_VIEW_NAME = "/query/table";

    public static final String CONDITION_NODE_NAME = "condition";

    public static final String CONDITION_VIEW_NAME = "/query/condition";

    public static final String COLUMN_NODE_NAME = "column";

    public static final String COLUMN_VIEW_NAME = "/query/column";

    public static final String PREVIEW_NODE_NAME = "preview";

    public static final String PREVIEW_VIEW_NAME = "/query/preview";

    @Resource
    private QueryService queryService;

    /**
     * 组装model中的Wizard对象和Query对象。注意Wizard对象和Query对象已经在类的@SessionAttributes注解中指定，所以
     * 要在组装前判断是否存在，避免覆盖掉Session中的对象。
     */
    @ModelAttribute
    public void populateModel(Model model) {

        if (!model.containsAttribute(WIZARD_SESSION_NAME)) {
            model.addAttribute(WIZARD_SESSION_NAME, new Wizard(new WizardNode[]{
                    new WizardNode(BASIC_NODE_NAME, BASIC_VIEW_NAME), new WizardNode(TABLE_NODE_NAME, TABLE_VIEW_NAME),
                    new WizardNode(CONDITION_NODE_NAME, CONDITION_VIEW_NAME), new WizardNode(COLUMN_NODE_NAME,COLUMN_VIEW_NAME),
                    new WizardNode(PREVIEW_NODE_NAME, PREVIEW_VIEW_NAME)}));
        }

        if (!model.containsAttribute(QUERY_SESSION_NAME)) {
            Query query = new Query();
            model.addAttribute(QUERY_SESSION_NAME, query);
        }
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create() {
        return "query/template";
    }

    @RequestMapping(value = "create/basic", method = RequestMethod.GET)
    public String basic( @ModelAttribute(value = WIZARD_SESSION_NAME) Wizard wizard) {
        return wizard.getCurrentWizardNode().getPath();
    }

    @RequestMapping(value = "create/store-basic-then-forward", method = RequestMethod.POST)
    public String storeBasicAndForward(@RequestBody Query query, @ModelAttribute(value = QUERY_SESSION_NAME) Query pendingQuery,
                                       @ModelAttribute(value = WIZARD_SESSION_NAME) Wizard wizard) {

        queryService.saveQuery(query);

        try {
            BeanUtils.copyProperties(pendingQuery, query);
        } catch (IllegalAccessException e) {
            throw new CodedRuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new CodedRuntimeException(e);
        }

        return  wizard.goForward().getPath();
    }

    @RequestMapping(value = "create/table", method = RequestMethod.GET)
    public String tableAndView(@ModelAttribute(WIZARD_SESSION_NAME) Wizard wizard) {
        return wizard.getCurrentWizardNode().getPath();
    }

    @RequestMapping(value = "create/store-table-then-forward", method = RequestMethod.POST)
    public String storeTableAndForward(@RequestBody TableAndColumnVO tc,
                                       @ModelAttribute(value = WIZARD_SESSION_NAME) Wizard wizard,
                                       @ModelAttribute(value = QUERY_SESSION_NAME) Query query) {
        queryService.saveTableAndJoindedColumns(query.getId(), tc.getTables(), tc.getColumns());

        query.setTables(tc.getTables());
        query.setJoinColumnses(tc.getColumns());

        return wizard.goForward().getPath();
    }

    @RequestMapping(value = "create/condition", method = RequestMethod.GET)
    public String condition(@ModelAttribute(WIZARD_SESSION_NAME) Wizard wizard) {
        return wizard.getCurrentWizardNode().getPath();
    }

    @RequestMapping(value = "create/store-condition-then-forward", method = RequestMethod.POST)
    public String storeConditionAndForward(@RequestBody List<Condition> conditions,
                                           @ModelAttribute(value = WIZARD_SESSION_NAME) Wizard wizard,
                                           @ModelAttribute(value = QUERY_SESSION_NAME) Query query) {
        for(Condition condition : conditions) {
            condition.setQuery(query);
            for(Table table : query.getTables()) {
                if (table.getId() != null && table.getId().equals(condition.getTable().getId())) {
                    condition.setTable(table);
                }
            }
        }
        getQueryService().saveConditions(conditions);
        query.setConditions(conditions);
        return wizard.goForward().getPath();
    }

    @RequestMapping(value = "create/column", method = RequestMethod.GET)
    public String column(@ModelAttribute(WIZARD_SESSION_NAME) Wizard wizard) {
        return wizard.getCurrentWizardNode().getPath();
    }

    @RequestMapping(value = "create/store-column-then-forward", method = RequestMethod.POST)
    public String storeColumnThenForward(@RequestBody List<Column> columns,
                                         @ModelAttribute(value = WIZARD_SESSION_NAME) Wizard wizard,
                                         @ModelAttribute(value = QUERY_SESSION_NAME) Query query) {

        for(Column column : columns) {
            column.setQuery(query);
            for(Table table : query.getTables()) {
                if (table.getId() != null && table.getId().equals(column.getTable().getId())) {
                    column.setTable(table);
                }
            }
        }

        getQueryService().saveColumns(columns);
        return wizard.goForward().getPath();
    }

    @RequestMapping(value = "create/preview", method = RequestMethod.GET)
    public String preview(@ModelAttribute(WIZARD_SESSION_NAME) Wizard wizard,
                          @ModelAttribute(value = QUERY_SESSION_NAME) Query query,Model model) {
        model.addAttribute("previewSQL", getQueryService().generateSQL(query.getId()));
        return wizard.getCurrentWizardNode().getPath();
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
        if (wizard.getCurrentWizardNode().getName().equals(viewName)) {
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
}
