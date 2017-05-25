package sigosoft.android.jackandtrades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    ListView listView;
   // List<ProductModel> items;
   ArrayList<ProductModel> dataArray;
//Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        listView = (ListView) findViewById(R.id.listId);
      //  add=findViewById(R.id.)
        dataArray = new ArrayList<>();

        DBHelper bn = new DBHelper(this);


        ProductAdapter adapter = new ProductAdapter(ProductActivity.this, dataArray);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

/*
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/
    }

}
