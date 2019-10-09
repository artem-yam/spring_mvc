package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.TagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for tags
 */
@RestController
//@RequestMapping
@RequestMapping("/tags")
public class TagsController {
    
    /**
     * logger for class
     */
    private static final Logger logger =
        LogManager.getLogger(TagsController.class);
    
    /**
     * DAO for operations with tags
     */
    private TagDAO dao;
    
    /**
     * Constructor
     *
     * @param dataSourceType type of data source
     */
    @Autowired
    public TagsController(DataSourceType dataSourceType) {
        dao = DAOFactory.getInstance(dataSourceType)
                  .getTagDAO();
    }
    
    /**
     * Gets all tags from dao
     *
     * @return list of tags
     */
    //@GetMapping("/tags")
    @GetMapping
    public List<String> getAllTags() {
        logger.debug("getAllTags method triggered");
        return dao.getAllTags();
    }
    
    /**
     * Add some tag to the book
     *
     * @param bookId book id
     * @param tag    tag to add
     * @return list of the book tags
     */
    //@PostMapping("/books/{bookId}/tags")
    @PostMapping("/{tag}")
    public List<String> addTagToBook(@RequestBody int bookId,
                                     @PathVariable String tag) {
        logger.info("Adding tag \'{}\' to book {}", tag, bookId);
        return dao.addTagToBook(bookId, tag);
    }
}
