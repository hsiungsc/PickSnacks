package hsiungsc.com.picksnacks;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

public class SnackAdapter extends RecyclerView.Adapter<SnackAdapter.ViewHolder> {
    private ArrayList<Snack> snacks;

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public CheckBox snackItem;
        public View layout;

        public ViewHolder(View view)
        {
            super(view);
            layout = view;
            snackItem = (CheckBox) view.findViewById(R.id.checkBox);
        }
    }

    // Constructor to set the data set
    public SnackAdapter(ArrayList<Snack> snacks)
    {
        this.snacks = snacks;
    }

    // Create new views for snacks
    @Override
    public SnackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_snack, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    // Replace the contents of view
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position)
    {
        // Get the snack from the snack list based on the position, then update the view content
        final Snack snack = snacks.get(position);

        // Populate the snack item name
        viewHolder.snackItem.setText(snack.getName());

        // Set the snack font color
        if(snack.isVeg())
            viewHolder.snackItem.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorGreen));
        else
            viewHolder.snackItem.setTextColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.colorRed));

        // Set the check status
        if(snack.getChecked())
        {
            viewHolder.snackItem.setChecked(true);
        }
        else
        {
            viewHolder.snackItem.setChecked(false);
        }

        viewHolder.snackItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getText().equals(snack.getName())) {
                    if (isChecked) {
                        snack.setChecked(true);
                    } else {
                        snack.setChecked(false);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return snacks.size();
    }
}
