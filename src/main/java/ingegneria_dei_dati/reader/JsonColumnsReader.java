package ingegneria_dei_dati.reader;

import com.google.gson.Gson;
import ingegneria_dei_dati.statistics.IndexCreationStatistics;
import ingegneria_dei_dati.table.Column;
import ingegneria_dei_dati.table.Table;

import java.io.*;
import java.util.List;

public class JsonColumnsReader implements ColumnsReader {
    private List<Column> columns;
    private final BufferedReader reader; // il file json contiene un elemento (ovvero una tabella)
    // per ogni riga, quindi possiamo scorrere il file con il BufferedReader e parsare una riga alla volta
    private final Gson gson; // serve a convertire l'elemento del file json (passandolo in input
    // come stringa) in un oggetto java

    public JsonColumnsReader(String path) throws IOException {
        this.reader = new BufferedReader(new FileReader(path));
        this.gson = new Gson();
    }
    public boolean hasNextColumn() {
        if (this.columns != null)
            if (!this.columns.isEmpty())
                return true;
        try {
            String nextLine = reader.readLine();
            if (nextLine != null) {
                Table table = gson.fromJson(nextLine, Table.class);
                table.makeColumns();
                IndexCreationStatistics.processTableStats(table);
                this.columns = table.columns;
                return true;
            }
            this.reader.close();
            return false;
        }
        catch (IOException e) {
            System.out.println("Catturata eccezione IO");
            return false;
        }
    }
    @Override
    public Column readNextColumn() {
        return this.columns.removeFirst();
    }
}
