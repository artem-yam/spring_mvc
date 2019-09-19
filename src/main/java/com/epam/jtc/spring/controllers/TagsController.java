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
@RequestMapping("/tags")
public class TagsController {
    
    /**
     * logger for class
     */
    private static final Logger logger = LogManager
                                             .getLogger(new Object() {
                                             }.getClass().getEnclosingClass());
    private TagDAO dao;
    
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
        return dao.getAllTags();
    }
    
    /**
     * Add some tag to the book
     *
     * @param bookId book id
     * @param tag    tag to add
     * @return book id
     */
    //@PostMapping("/books/{bookId}/tags")
    @PostMapping("/{tag}")
    public int addTagToBook(@RequestBody int bookId,
                            @PathVariable String tag) {
        
        dao.addTagToBook(bookId, tag);
        
        return bookId;
    }
}
