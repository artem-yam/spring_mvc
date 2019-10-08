package com.epam.jtc.spring.datalayer.oracleDB.rowMappers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.apache.commons.io.IOUtils.toByteArray;

/**
 * Converts DB blob image value to byte representation
 */
@Component
public class BookImageRowMapper implements RowMapper<byte[]> {
    
    private static final Logger logger =
        LogManager.getLogger(BookImageRowMapper.class);
    
    @Override
    @Nullable
    public byte[] mapRow(ResultSet rs, int rowNum)
        throws SQLException {
        
        byte[] image = new byte[0];
        
        if (rs.getBlob(1) != null) {
            InputStream imageBlobStream =
                rs.getBlob(1).getBinaryStream();
            try {
                image = toByteArray(imageBlobStream);
            } catch (IOException bookImageIOException) {
                logger.warn("Can't get image",
                    bookImageIOException);
            }
        }
        
        return image;
    }
}

