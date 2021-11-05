package com.example.JavaCrtLogTblWthOracle.WriteSql;

import com.example.JavaCrtLogTblWthOracle.Database.Query;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CreateLog {

    public CreateLog(String url, String owner, String password) throws SQLException, IOException {
        Query query = new Query();
        List<String> create = query.ShowNotLogTables(url, owner, password);


        File file = new File("dosya.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, false);
        BufferedWriter bWriter = new BufferedWriter(fileWriter);
        int i;
        for (i = 0; i < create.size(); i++) {
            bWriter.write("Create table " + create.get(i) + "_LOG As Select * From " + create.get(i) + " Where 1=0;" +
                    "\n/" +
                    "\n" +
                    "Alter Table " + create.get(i) + "_LOG " + "Add (ISLEM_TURU varchar(20), ISLEM_ZAMANI Date Default Sysdate);\n" +
                    "/\n" +
                    "CREATE OR REPLACE TRIGGER " + create.get(i) + "_DELETE_UPDATE_TRG\n AFTER DELETE OR UPDATE OF "
            );
            List<String> column = query.GetColomnName(url, owner, password, create.get(i));
            System.out.println(column);

            for (int z = 0, x = 0; z < column.size(); z++, x++) {
                bWriter.write(column.get(z));
                if (x != column.size() - 1)
                    bWriter.write(", ");
            }

            bWriter.write(" ON " + create.get(i) + "\n REFERENCING OLD AS OLD NEW AS NEW FOR EACH ROW \n BEGIN \n IF DELETING THEN \n INSERT INTO "
                    + create.get(i) + "_LOG\n (");

            for (int z = 0; z < column.size(); z++) {
                bWriter.write(column.get(z));
                bWriter.write(", ");
            }

            bWriter.write("ISLEM_TURU)\n VALUES\n (");

            for  (int z = 0; z < column.size(); z++) {
                bWriter.write(":OLD."+ column.get(z));
                bWriter.write(", ");
            }

            bWriter.write("'Silindi');\n ELSIF UPDATING THEN\n INSERT INTO "+ create.get(i)+"_LOG\n (");

            for (int z = 0; z < column.size(); z++) {
                bWriter.write(column.get(z));
                bWriter.write(", ");
            }

            bWriter.write("ISLEM_TURU)\n VALUES\n (");

            for  (int z = 0; z < column.size(); z++) {
                bWriter.write(":OLD."+ column.get(z));
                bWriter.write(", ");
            }

            bWriter.write("'Güncellendi');\n END IF;\n END;\n/\n\n\n");

        }


        bWriter.close();
    }
}
