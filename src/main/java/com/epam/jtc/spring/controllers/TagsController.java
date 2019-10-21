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
     * Add new tag to the book
     *
     * @param bookId book id
     * @param tag    tag to add
     * @return list of the book tags
     */
    @PostMapping
    public List<String> addNewTag(@RequestParam int bookId,
                                  @RequestParam String tag) {
        logger.info("Adding new tag \'{}\' and binding it to book {}",
                tag, bookId);

        dao.addTag(tag);

        return dao.bindTagToBook(bookId, tag);
    }


    /**
     * Binds some tag to the book
     *
     * @param bookId book id
     * @param tag    tag to bind
     * @return list of the book tags
     */
    @PostMapping("/{tag}/books")
    public List<String> addTagToBook(@RequestBody int bookId,
                                     @PathVariable String tag) {
        logger.info("Binding tag \'{}\' to book {}", tag, bookId);
        return dao.bindTagToBook(bookId, tag);
    }

    /**
     * Unbinds some tag from the book
     *
     * @param bookId book id
     * @param tag    tag to add
     * @return list of the book tags
     */
    @DeleteMapping("/{tag}/books")
    public List<String> unbindTag(@RequestBody int bookId,
                                  @PathVariable String tag) {
        logger.info("Unbinding tag \'{}\' from book {}", tag, bookId);
        return dao.unbindTag(bookId, tag);
    }
}
