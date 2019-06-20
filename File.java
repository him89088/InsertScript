package test;

import org.apache.commons.io.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class Files
{
    public static void main(final String[] args) throws IOException {
        final String file_txt = args[1];
        final List<String> lines = (List<String>)FileUtils.readLines(new File(file_txt), "utf-8");
        final List<String> data = new ArrayList<String>();
        final String[] PO_Num = new String[2000];
        final String[] Serial_Num = new String[2000];
        try {
            for (final String str : lines) {
                String[] split;
                for (int length = (split = str.replace("\"", "").replace(".pdf", "").split("_")).length, k = 0; k < length; ++k) {
                    final String s = split[k];
                    data.add(s);
                }
            }
            int odd_place = 0;
            int even_place = 0;
            for (int i = 0; i < data.size(); ++i) {
                if (i % 2 == 0) {
                    Serial_Num[even_place] = data.get(i);
                    ++even_place;
                }
                else {
                    PO_Num[odd_place] = data.get(i);
                    ++odd_place;
                }
            }
            final String date = args[0];
            final String path = String.valueOf(args[2]) + date.replace("/", "") + ".txt";
            final PrintWriter writer = new PrintWriter(path, "UTF-8");
            final String command = "INSERT INTO switchlookup.dbo.ProductTestReport (PO_Number,Serial_Number,Date_Added,File_Location)";
            for (int j = 0; j < lines.size(); ++j) {
                writer.println(command);
                writer.println("VALUES ('" + PO_Num[j] + "', '" + Serial_Num[j] + "', '" + date + "', '" + lines.get(j).toString().replace("\"", "") + "')");
                writer.println();
            }
            writer.close();
            System.out.println("The file has been generated.");
            Desktop.getDesktop().open(new File(path));
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
