package postpc.project.erez0_000.weddingapp.searches;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import postpc.project.erez0_000.weddingapp.R;

public class FabFilterFragment extends DialogFragment
        implements View.OnClickListener {
    private FilterListener listener;
    private String type, region, kosher;
    private int typeInt, regionInt, kosherInt;
    Spinner typeSpinner, regionSpinner, kosherSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(postpc.project.erez0_000.weddingapp.R.layout.fab_filter_fragment, container, false);
        view.findViewById(postpc.project.erez0_000.weddingapp.R.id.accept).setOnClickListener(this);
        view.findViewById(postpc.project.erez0_000.weddingapp.R.id.cancel).setOnClickListener(this);

        typeSpinner = view.findViewById(postpc.project.erez0_000.weddingapp.R.id.type);
        regionSpinner = view.findViewById(postpc.project.erez0_000.weddingapp.R.id.region);
        kosherSpinner = view.findViewById(postpc.project.erez0_000.weddingapp.R.id.kosher);
        typeSpinner.setSelection(typeInt);
        regionSpinner.setSelection(regionInt);
        kosherSpinner.setSelection(kosherInt);


        return view;
    }

    public static FabFilterFragment newInstance() {

        Bundle args = new Bundle();

        FabFilterFragment fragment = new FabFilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(FilterListener listener) {
        this.listener = listener;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case postpc.project.erez0_000.weddingapp.R.id.accept:
                Spinner type = getView().findViewById(postpc.project.erez0_000.weddingapp.R.id.type);
                String typeString = type.getSelectedItem().toString();

                Spinner kosher = getView().findViewById(postpc.project.erez0_000.weddingapp.R.id.kosher);
                String kosher_string = kosher.getSelectedItem().toString();

                Spinner region = getView().findViewById(postpc.project.erez0_000.weddingapp.R.id.region);
                String regionString = region.getSelectedItem().toString();
                listener.addFilters(typeString,typeSpinner.getSelectedItemPosition(),
                                    regionString,regionSpinner.getSelectedItemPosition(),
                                    kosher_string, kosherSpinner.getSelectedItemPosition());
                dismiss();
                break;
            case postpc.project.erez0_000.weddingapp.R.id.cancel:
                dismiss();
                break;
        }
    }

    public void setSpinnerVals(int typeStr, int regionStr, int kosherStr) {
        typeInt = typeStr;
        regionInt =regionStr;
        kosherInt = kosherStr;
    }


    /**
     * listener to catch changes of the filters, and make a new call to the DB
     */
    public interface FilterListener {
        void addFilters(String type, int typeIndex, String region, int regionIndex,
                        String Kosher, int kosherIndex);
    }

}
