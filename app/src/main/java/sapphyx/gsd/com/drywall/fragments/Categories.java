package sapphyx.gsd.com.drywall.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import fr.ganfra.materialspinner.MaterialSpinner;
import sapphyx.gsd.com.drywall.R;
import sapphyx.gsd.com.drywall.fragments.collections.CollectionsFive;
import sapphyx.gsd.com.drywall.fragments.collections.CollectionsFour;
import sapphyx.gsd.com.drywall.fragments.collections.CollectionsOne;
import sapphyx.gsd.com.drywall.fragments.collections.CollectionsSix;
import sapphyx.gsd.com.drywall.fragments.collections.CollectionsThree;
import sapphyx.gsd.com.drywall.fragments.collections.CollectionsTwo;
import sapphyx.gsd.com.drywall.fragments.collections.LandingPage;
import sapphyx.gsd.com.drywall.views.RevealLayout;

import static sapphyx.gsd.com.drywall.activity.MainActivityBase.ARGS_INSTANCE;

/**
 * Created by ry on 2/27/18.
 */

public class Categories extends Fragment {

    private MaterialSpinner spinner;
    private RevealLayout mRevealLayout;


    public Categories() {
    }

    public static Categories newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        Categories fragment = new Categories();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.categories_layout, container, false);
        setHasOptionsMenu(true);

        mRevealLayout = v.findViewById(R.id.container);

        spinner = v.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> mArrayAdapter =  ArrayAdapter.createFromResource(getActivity(), R.array.items, R.layout.layout_drop_title);
        mArrayAdapter.setDropDownViewResource(R.layout.layout_drop_list);
        spinner.setAdapter(mArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, CollectionsOne.newInstance(1)).commit();
                        break;
                    case 1:
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, CollectionsTwo.newInstance(2)).commit();
                        break;
                    case 2:
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, CollectionsThree.newInstance(3)).commit();
                        break;
                    case 3:
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, CollectionsFour.newInstance(4)).commit();
                        break;
                    case 4:
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, CollectionsFive.newInstance(5)).commit();
                        break;
                    case 5:
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, CollectionsSix.newInstance(6)).commit();
                        break;

                    default:
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, LandingPage.newInstance(0)).commit();
                        mRevealLayout.show(400);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        return  v;
    }
}
