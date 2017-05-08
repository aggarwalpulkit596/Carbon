package tk.zielony.carbonsamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import carbon.component.DataBindingComponent;
import carbon.component.PaddingItem;
import carbon.component.PaddingRow;
import carbon.recycler.RowArrayAdapter;
import carbon.widget.RecyclerView;

public class SampleListActivity extends SamplesActivity {

    RowArrayAdapter<Object> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samplelist);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(getResources().getBoolean(R.bool.tablet) ?
                new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false) :
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new RowArrayAdapter<>(SampleActivityItem.class, parent -> new DataBindingComponent<>(recyclerView, R.layout.row_main));
        adapter.addFactory(PaddingItem.class, PaddingRow.FACTORY);
        adapter.addFactory(String.class, parent -> new DataBindingComponent<String>(parent, R.layout.row_description));
        adapter.setOnItemClickedListener((view, item, position) -> {
            if (item instanceof SampleActivityItem) {
                Class activityClass = ((SampleActivityItem) item).getActivityClass();
                Intent intent = new Intent(view.getContext(), activityClass);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    protected void setItems(Object[] items) {
        adapter.setItems(items);
    }

}
