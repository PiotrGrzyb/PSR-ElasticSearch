import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ElasticSearch {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));

        int choice = 0;
        Scanner myInput = new Scanner( System.in );
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do{
            System.out.println("[1] Add new fireman");
            System.out.println("[2] Get fireman by ID");
            System.out.println("[3] Update fireman rank");
            System.out.println("[4] Delete fireman by ID");
            System.out.println("[5] Get max age fireman");
            System.out.println("[6] Quit");
            choice = myInput.nextInt();
            if (choice == 1){
                System.out.println("Fireman ID :");
                String id =  reader.readLine();
                System.out.println("Fireman name:");
                String name =  reader.readLine();
                System.out.println("Fireman surname:");
                String surname =  reader.readLine();
                System.out.println("Fireman rank:");
                String rank =  reader.readLine();
                System.out.println("Fireman age :");
                int age = myInput.nextInt();
                Map<String, Object> fireman = new HashMap<>();
                fireman.put("name", name);
                fireman.put("surname", surname);
                fireman.put("rank", rank);
                fireman.put("age", age);
                IndexRequest firemanAddRequest = new IndexRequest("fireman")
                        .id(id).source(fireman);
                IndexResponse firemanAddResponse = client.index(firemanAddRequest, RequestOptions.DEFAULT);
            }
            if (choice == 2){
                System.out.println("Fireman ID :");
                String id =  reader.readLine();
                GetRequest getFiremanRequest = new GetRequest(
                        "fireman",
                        id);
                GetResponse getFiremanResponse = client.get(getFiremanRequest, RequestOptions.DEFAULT);
                Map<String, Object> firemanMap = getFiremanResponse.getSourceAsMap();
                System.out.println(firemanMap);
            }
            if (choice == 3){
                System.out.println("Fireman ID to update :");
                String id =  reader.readLine();
                GetRequest checkIfFiremanExists = new GetRequest(
                        "fireman",
                        id);
                checkIfFiremanExists.fetchSourceContext(new FetchSourceContext(false));
                checkIfFiremanExists.storedFields("_none_");
                boolean firemanExists = client.exists(checkIfFiremanExists, RequestOptions.DEFAULT);
                if(firemanExists){
                    System.out.println("Fireman new rank:");
                    String rank =  reader.readLine();
                    Map<String, Object> updateRank = new HashMap<>();
                    updateRank.put("rank", rank);

                    UpdateRequest requestFiremanUpdate = new UpdateRequest("fireman", id)
                            .doc(updateRank);
                    UpdateResponse updateFiremanResponse = client.update(
                            requestFiremanUpdate, RequestOptions.DEFAULT);
                }
                else System.out.println("Fireman with ID: " + id + " doesnt exist");

            }
            if (choice == 4){
                System.out.println("Fireman ID to delete :");
                String id =  reader.readLine();
                GetRequest checkIfFiremanExists = new GetRequest(
                        "fireman",
                        id);
                checkIfFiremanExists.fetchSourceContext(new FetchSourceContext(false));
                checkIfFiremanExists.storedFields("_none_");
                boolean firemanExists = client.exists(checkIfFiremanExists, RequestOptions.DEFAULT);
                if(firemanExists){
                    DeleteRequest deleteFireman = new DeleteRequest(
                            "fireman",
                            id);

                    DeleteResponse deleteFiremanResponse = client.delete(
                            deleteFireman, RequestOptions.DEFAULT);
                }
                else System.out.println("Fireman with ID: " + id + " doesnt exist");
            }
            if (choice == 5){
                final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                searchSourceBuilder.aggregation(AggregationBuilders.max("age_aggr").field("age"));
                searchSourceBuilder.query(QueryBuilders.matchAllQuery());

                final SearchRequest searchRequest = new SearchRequest();
                searchRequest.source(searchSourceBuilder);
                final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
                final double result = ((Max) searchResponse.getAggregations().get("age_aggr")).getValue();
                System.out.println("Max age of fireman: " + result);
            }

        }while(choice != 6);


    }
}
