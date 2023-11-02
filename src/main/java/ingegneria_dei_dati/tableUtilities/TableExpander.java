package ingegneria_dei_dati.tableUtilities;

import ingegneria_dei_dati.index.IndexHandler;
import ingegneria_dei_dati.index.IndexHandlerInterface;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class TableExpander {
    private final String FIELD = "text";
    private final IndexHandlerInterface indexHandler;

    public TableExpander(String indexPath) throws IOException {
        this.indexHandler = new IndexHandler(indexPath);
    }
    private void searchForColumnExpansion(String query) throws IOException {
        BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
        List<String> terms = this.tokenizeString(query);

        for(String term : terms){
            TermQuery termQuery = new TermQuery(new Term(this.FIELD, term));
            booleanQueryBuilder.add(new BooleanClause(termQuery, BooleanClause.Occur.SHOULD));
        }
        BooleanQuery booleanQuery = booleanQueryBuilder.build();

        this.indexHandler.search(booleanQuery);

    }

    private List<String> tokenizeString(String query) {
        List<String> terms = new ArrayList<String>();
        try {
            TokenStream stream  = this.indexHandler.getAnalyzer().tokenStream(this.FIELD, new StringReader(query));
            stream.reset();
            while (stream.incrementToken()) {
                terms.add(stream.getAttribute(CharTermAttribute.class).toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return terms;
    }

}