package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tags")
public class TagsController {

    private static final Logger logger = LogManager
            .getLogger(new Object() {
            }.getClass().getEnclosingClass());

    @RequestMapping(value = "/getAll")
    public List<String> getAllTags() {
        return DAOFactory.getInstance(DataSourceType.ORACLE)
                .getTagDAO().getAllTags();
    }

    @RequestMapping(value = "/addToBook")
    public int addTagToBook(@RequestBody Map<String, String> bookIdAndTag) {
        logger.info("bookIdAndTag: {}", bookIdAndTag);

        return DAOFactory.getInstance(DataSourceType.ORACLE).getTagDAO()
                .addTagToBook(Integer.parseInt(bookIdAndTag.get("bookId")),
                        bookIdAndTag.get("tag"));
    }
}
