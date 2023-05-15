package com.mio.typeSecure.models.helpers;

import com.mio.typeSecure.models.TSError;
import com.mio.typeSecure.models.instructions.SymbolTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportHelper {

    public static final String TABLE_REPORT_NAME = "table.html";
    public static final String ERROR_REPORT_NAME = "error.html";
    public static final String HTML_O = "<html>\n";
    public static final String HTML_C = "</html>\n";
    public static final String BODY_O = "\t<body>\n";
    public static final String BODY_C = "\t</body>\n";
    public static final String TABLE_O = "\t\t<table>\n";
    public static final String TABLE_C = "\t\t</table>\n";
    public static final String THEAD_O = "\t\t\t<thead>\n";
    public static final String THEAD_C = "\t\t\t</thead>\n";
    public static final String TBODY_O = "\t\t\t<tbody>\n";
    public static final String TBODY_C = "\t\t\t</tbody>\n";
    public static final String TR_O = "\t\t\t\t<tr>\n";
    public static final String TR_C = "\t\t\t\t</tr>\n";
    public static final String TH_O = "\t\t\t\t\t<th>";
    public static final String TH_C = "</th>\n";
    public static final String TD_O = "\t\t\t\t\t<td>";
    public static final String TD_C = "</td>\n";

    public static void createErrorReport(List<TSError> errors) {
        File file = createFile(ReportType.ERROR_REPORT);
        StringBuilder content = new StringBuilder();
        try(FileWriter writer = new FileWriter(file)){
            content.append(HTML_O);
            content.append(BODY_O);

            content.append(TABLE_O);

            content.append(THEAD_O);
            content.append(TR_O);

            content.append(TH_O).append("Linea").append(TH_C);
            content.append(TH_O).append("Columna").append(TH_C);
            content.append(TH_O).append("Mensaje").append(TH_C);

            content.append(TR_C);
            content.append(THEAD_C);

            content.append(TBODY_O);
            errors.forEach(tsError -> {
                content.append(TR_O);
                content.append(TD_O).append(tsError.line()).append(TD_C);
                content.append(TD_O).append(tsError.column()).append(TD_C);
                content.append(TD_O).append(tsError.message()).append(TD_C);
                content.append(TR_C);
            });

            content.append(TBODY_C);

            content.append(TABLE_C);

            content.append(BODY_C);
            content.append(HTML_C);

            writer.write(content.toString());
        } catch (IOException e){
            System.err.println("Error al crear archivo error.html");
        }
    }

    public static void createTableReport(SymbolTable table){
        File file = createFile(ReportType.TABLE_REPORT);
        StringBuilder content = new StringBuilder();
        try(FileWriter writer = new FileWriter(file)){

            content.append(HTML_O);
            content.append(BODY_O);

            content.append(TABLE_O);

            content.append(THEAD_O);

            content.append(TH_O).append("Id").append(TH_C);
            content.append(TH_O).append("Tipo de Variable").append(TH_C);
            content.append(TH_O).append("Valor").append(TH_C);
            content.append(TH_O).append("Tipo de declaración").append(TH_C);

            content.append(THEAD_C);

            content.append(TBODY_O);

            table.variableList.forEach(variable -> {
                content.append(TR_O);
                content.append(TD_O).append(variable.id).append(TD_C);
                content.append(TD_O).append(variable.variableType).append(TD_C);
                content.append(TD_O).append(variable.value).append(TD_C);
                content.append(TD_O).append(variable.declarationType).append(TD_C);
                content.append(TR_C);
            });
            content.append(TBODY_C);

            content.append(TABLE_C);

            content.append(BODY_C);
            content.append(HTML_C);

            writer.write(content.toString());
        } catch (IOException e){
            System.err.println("Error al crear archivo table.html");
        }
    }

    public static File createFile(ReportType reportType){
        return switch (reportType){
            case TABLE_REPORT -> new File(TABLE_REPORT_NAME);
            case ERROR_REPORT -> new File(ERROR_REPORT_NAME);
        };
    }

    public enum ReportType{
        ERROR_REPORT, TABLE_REPORT
    }
}
