package com.silver.seed.query.showcase.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Liaojian
 */
@Controller
@RequestMapping(value = "/query")
public class QueryController {
    @RequestMapping(value = "{id}")
    public String template(@PathVariable String id) {
        System.out.println(id);
        return "table-template";
    }
}
