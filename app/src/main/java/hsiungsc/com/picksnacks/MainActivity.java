package hsiungsc.com.picksnacks;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SnackAdapter snackAdapter;          // The adapter
    private Snacks mSnacks;                     // Snack list

    private RecyclerView mRecyclerView;

    private CheckBox mVegCheckBoxView;
    private CheckBox mNonVegCheckBoxView;

    // Default snack lists
    String vegSnacks[] = {
            "French fries",
            "Veggieburger",
            "Carrots",
            "Apple",
            "Banana",
            "Milkshake"};

    String nonvegSnacks[] = {
            "Cheeseburger",
            "Hamburger",
            "Hot dog"};

    private class CheckBoxChnageClicker implements CheckBox.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if(isChecked) {
                if(buttonView==mVegCheckBoxView) {
                    mSnacks.addSnacks(vegSnacks, true);
                }

                if(buttonView==mNonVegCheckBoxView) {
                    mSnacks.addSnacks(nonvegSnacks, false);
                }
            } else
            {
                if(buttonView==mVegCheckBoxView) {
                    mSnacks.removeSnacks(true);
                }

                if(buttonView==mNonVegCheckBoxView) {
                    mSnacks.removeSnacks(false);
                }
            }

            // Notify the adapter
            snackAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mVegCheckBoxView = findViewById(R.id.checkBoxVeg);
        mVegCheckBoxView.setOnCheckedChangeListener(new CheckBoxChnageClicker());
        mNonVegCheckBoxView = findViewById(R.id.checkBoxNonVeg);
        mNonVegCheckBoxView.setOnCheckedChangeListener(new CheckBoxChnageClicker());
    }

    @Override
    public void onResume()
    {
        super.onResume();

        initial();
    }

    // create an action bar 'add' button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.snackmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle add button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addbutton) {
            // Launch add snack dialog
            addNewSnack();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewSnack()
    {
        // Get dialog layout
        LayoutInflater layoutIn = LayoutInflater.from(this);
        View addDialogView = layoutIn.inflate(R.layout.add_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(addDialogView);

        final EditText newSnackName = (EditText) addDialogView.findViewById(R.id.editTextSnackName);
        final RadioGroup radioGroup = (RadioGroup) addDialogView.findViewById(R.id.radioGroup);
        final RadioButton vegButton = (RadioButton) addDialogView.findViewById(R.id.radioButtonVeg);

        // Set dialog buttons
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(MainActivity.this.getString(R.string.save_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int selectedId = radioGroup.getCheckedRadioButtonId();

                                // Make sure the snack name is not empty
                                String newName = newSnackName.getText().toString();
                                boolean isExists = mSnacks.isExist(newName);
                                if(!newName.isEmpty() && !isExists) {
                                    // find which radioButton is checked by id
                                    boolean veg;
                                    if (selectedId == vegButton.getId()) {
                                        veg = true;
                                    } else {
                                        veg = false;
                                    }

                                    // Get the new snack name
                                    String name = newSnackName.getText().toString();
                                    mSnacks.addSnack(name, veg);
                                } else if(isExists)
                                {
                                    String message = "'" + newName + "' " + MainActivity.this.getString(R.string.duplicated_snak_name);
                                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                .setNegativeButton(MainActivity.this.getString(R.string.cancel_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        // Create Dialog
        AlertDialog addDialog = alertDialogBuilder.create();

        alertDialogBuilder.show();
    }

    private void initial()
    {
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        getDefaultSnacks();

        snackAdapter = new SnackAdapter(mSnacks.getSnacks());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(snackAdapter);
    }

    // Get default snack lists
    private void getDefaultSnacks()
    {
        if(mSnacks == null) {
            mSnacks = new Snacks();
            mSnacks.addSnacks(vegSnacks, true);
            mSnacks.addSnacks(nonvegSnacks, false);
        }
    }

    // Handle submit button click
    public void onClickSubmitBtn(View v)
    {
        // Get list of selected snack
        StringBuilder listOfSnack = new StringBuilder();
        int numSelected = 0;
        for(Snack snack : mSnacks.getSnacks())
        {
            if(snack.getChecked())
            {
                listOfSnack.append(snack.getName());
                listOfSnack.append("\n");
                numSelected++;
            }
        }

        if(numSelected == 0) {
            Toast.makeText(this, R.string.no_selection, Toast.LENGTH_LONG).show();
        }
        else {
            // Show the results in dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(MainActivity.this.getString(R.string.selection_dlg_msg));
            builder.setMessage(listOfSnack);
            builder.setCancelable(true);
            builder.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog selectionDlg = builder.create();
            selectionDlg.show();

            // Clear the selectios
            mSnacks.resetSelection();

            // Notify the adapter
            snackAdapter.notifyDataSetChanged();
        }
    }

    // Snack option button checked
    public void onOptionsCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
