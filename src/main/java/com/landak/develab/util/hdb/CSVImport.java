package com.landak.develab.util.hdb;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.landak.develab.http.HttpServiceImpl;
import com.landak.develab.util.MYSQL;
import com.landak.develab.util.geo.LatLonCoordinate;
import com.landak.develab.util.geo.SVY21;
import com.landak.develab.util.hdb.obj.HDBRecord;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DO NOT run this individually! This script is used by maven build!
 */
public class CSVImport {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVImport.class);

    public static void main(String[] args) {

        final Properties properties = new Properties();
        try {
            // Load the properties from the file
            final FileInputStream input = new FileInputStream("src/main/resources/application.properties");
            properties.load(input);
            input.close();
        } catch (IOException e) {
            LOGGER.error("Error occurred. Msg {}", e.getMessage(), e);
            throw new RuntimeException("Unable to get resources file. Abort build....", e);
        }

        MYSQL.setupConnection(properties.getProperty("app.db_name"), properties.getProperty("app.db_server"), 
                properties.getProperty("app.db_name"), properties.getProperty("spring.datasource.username"), 
                properties.getProperty("spring.datasource.password"));

        final List<HDBRecord> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/HDBCarparkInformation.csv"))) {

            final List<String[]> csvData = reader.readAll();
            for (final String[] row : csvData) {
                final String[] data = new String[12];
                int pointer = 0;
                for (final String cell : row) {
                    data[pointer] = cell;
                    pointer++;
                }
                records.add(new HDBRecord(data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]),
                        data[4], data[5], data[6], data[7],
                        data[8], Integer.parseInt(data[9]), Double.parseDouble(data[10]), Boolean.parseBoolean(data[11])));
            }
        } catch (IOException | CsvException e) {
            LOGGER.error("Error occurred. Msg {}", e.getMessage(), e);
            throw new RuntimeException("Unable to detect file. Abort build....", e);
        }

        try (final MYSQL mysql = new MYSQL("develab")) {
            LOGGER.info("Importing to db....");
            for (final HDBRecord row : records) {
                final LatLonCoordinate conversionResult = SVY21.computeLatLon(row.coorY(), row.coorX());
                final BigDecimal latitude = BigDecimal
                        .valueOf(conversionResult.latitude())
                        .setScale(5, RoundingMode.HALF_UP);
                final BigDecimal longitude = BigDecimal
                        .valueOf(conversionResult.longitude())
                        .setScale(5, RoundingMode.HALF_UP);
                final String sql = "insert into parking_info (car_park_no, address, latitude, longitude) values (?, ?, ?, ?)";
                final PreparedStatement ps = mysql.getPreparedStatement(sql);
                ps.setString(1, row.carParkNo());
                ps.setString(2, row.address());
                ps.setDouble(3, latitude.doubleValue());
                ps.setDouble(4, longitude.doubleValue());
                mysql.execute(ps);
            }
            LOGGER.info("Finish importing to db....");
        } catch (SQLException e) {
            LOGGER.error("Error occurred. Msg {}", e.getMessage(), e);
            throw new RuntimeException("Unable to save into db. Abort build....", e);
        }

        MYSQL.globalClose();
        // fixme : [WARNING] thread Thread[mysql-cj-abandoned-connection-cleanup,5,com.landak.develab.util.hdb.CSVImport] 
        //  will linger despite being asked to die via interruption 
    }

}

    


