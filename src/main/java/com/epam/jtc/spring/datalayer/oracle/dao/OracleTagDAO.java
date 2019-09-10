package com.epam.jtc.spring.datalayer.oracle.dao;

import com.epam.jtc.spring.datalayer.dao.TagDAO;

import java.util.List;

public class OracleTagDAO implements TagDAO {


    @Override
    public List<String> getAllTags() {
        return null;
    }

    @Override
    public void addTag(String text) {

    }

    @Override
    public void addTagToBook(int bookId, String tag) {

    }

    @Override
    public List<String> getBookTags(int bookId) {
        return null;
    }
}
