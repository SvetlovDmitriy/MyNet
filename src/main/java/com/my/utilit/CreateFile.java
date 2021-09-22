package com.my.utilit;

import com.my.db.entity.Category;
import com.my.db.entity.Product;
import com.my.db.sqlworker.MySqlWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import static com.my.constant.AppConstant.EXCEPTION;

public class CreateFile {
    public static final String ENCODING = "UTF-8";
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    public static void createFile(Map<Category, List<Product>> priceList, String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (OutputStreamWriter wr = new OutputStreamWriter(new FileOutputStream(path), ENCODING);
             BufferedWriter bf = new BufferedWriter(wr)) {
            sb.append("Price list").append(System.lineSeparator());
            priceList.forEach((c, p) -> {
                sb.append(System.lineSeparator()).append(c.getName()).append(System.lineSeparator());
                for (Product product : p){
                    sb.append("  *  ").append(product.getName()).append("  ").append(product.getDescription()).
                            append("  ").append(product.getPrice()).append(System.lineSeparator());
                }
            });
            sb.deleteCharAt(sb.length() - 1);
            bf.write(sb.toString());
        } catch (IOException ex){
           log.error(EXCEPTION, "can't create file in method CreateFile", ex);
           throw new IOException(ex);
        }
    }
}
