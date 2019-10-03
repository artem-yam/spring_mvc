package com.epam.jtc.spring.datalayer.oracleDB.rowMappers;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class BookImageRowMapper extends BeanPropertyRowMapper<byte[]> {
    
    @Override
    public byte[] mapRow(ResultSet rs, int rowNum)
        throws SQLException {
        
        byte[] image = null;
        
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

