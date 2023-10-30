package ingegneria_dei_dati.table;

import ingegneria_dei_dati.utils.Triple;

import java.util.List;

public class Table {
    public String className;
    public String id;
    public Cell[] cells;
    public String referenceContext;
    public Coordinates maxDimensions;
    public String[] headersCleaned;
    public int keyColumn;

    public List<Triple<String, String, List<String>>> tableDocumentsRepresentation() {
        return null;
    }
}