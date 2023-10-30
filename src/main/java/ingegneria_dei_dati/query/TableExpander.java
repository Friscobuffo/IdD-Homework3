package ingegneria_dei_dati.query;

import ingegneria_dei_dati.index.IndexHandler;
import ingegneria_dei_dati.index.IndexHandlerInterface;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;

public class TableExpander {
    private final String FIELD = "text";
    private IndexHandlerInterface indexHandler;

    public TableExpander(String indexPath) {
        this.indexHandler = new IndexHandler(indexPath);
    }

    /*
     * columnRepresentation is expected to be a sequence of terms occurring in one column
     * divided by a blank space
     */
    private void searchForColumnExpansion(String columnRepresentation){
        String[] columnTerms = columnRepresentation.split(" ");
        BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();

        for(String term : columnTerms){
            TermQuery termQuery = new TermQuery(new Term(this.FIELD, term));
            booleanQueryBuilder.add(new BooleanClause(termQuery, BooleanClause.Occur.SHOULD));
        }
        BooleanQuery booleanQuery = booleanQueryBuilder.build();

        this.indexHandler.search(booleanQuery);
    }


}
