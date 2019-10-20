package com.epam.jtc.spring.controllers;

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

    private static final Logger logger =
            LogManager.getLogger(TagsController.class);

    private TagDAO dao;

    /**
     * Constructor
     *
     * @param dao tags dao
     */
    @Autowired
    public TagsController(TagDAO dao) {
        this.dao = dao;
    }

    /**
     * Gets all tags from dao
     *
     * @return list of tags
     */
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
    @PostMapping("/{tag}/books")
    public List<String> addTagToBook(@RequestBody int bookId,
                                     @PathVariable String tag) {
        logger.info("Adding tag \'{}\' to book {}", tag, bookId);
        return dao.addTagToBook(bookId, tag);
    }

    /**
     * Unbinds some tag from the book
     *
     * @param bookId book id
     * @param tag    tag to add
     * @return list of the book tags
     */
    @DeleteMapping("/{tag}/books/{bookId}")
    public List<String> unbindTag(@PathVariable int bookId,
                                  @PathVariable String tag) {
        logger.info("Unbinding tag \'{}\' from book {}", tag, bookId);
        return dao.unbindTag(bookId, tag);
    }
}
