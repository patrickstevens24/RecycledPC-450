package pws24.uw.tacoma.edu.recycledpc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pws24.uw.tacoma.edu.recycledpc.item.ItemContent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class ItemDetailFragment extends Fragment {



    public static final String DETAIL_PARAM = "detail_param";
    private ItemContent.DummyItem mDummyItem;



    public ItemDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ItemDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemDetailFragment getItemDetailFragment(ItemContent.DummyItem dummyItem) {
        ItemDetailFragment fragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DETAIL_PARAM, dummyItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDummyItem = (ItemContent.DummyItem)
                    getArguments().getSerializable(DETAIL_PARAM);
        }

    }

    public void updateItemView(ItemContent.DummyItem item) {
        TextView courseIdTextView = (TextView) getActivity().findViewById(R.id.course_item_id);
        courseIdTextView.setText(item.id);

        TextView courseTitleTextView = (TextView) getActivity().findViewById(R.id.course_item_title);
        courseTitleTextView.setText(item.title);

        TextView courseShortDescTextView = (TextView) getActivity().findViewById(R.id.course_item_desc);
        courseShortDescTextView.setText(item.shortDesc);

        ImageView courseImageView = (ImageView) getActivity().findViewById(R.id.image1);
        courseImageView.setImageResource(item.imageTest);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_item_detail, container, false);
        if (mDummyItem == null) {
            mDummyItem = ItemContent.ITEMS.get(0);
        }
        TextView courseIdTextView = (TextView) v.findViewById(R.id.course_item_id);
        courseIdTextView.setText(mDummyItem.id);

        TextView courseTitleTextView = (TextView) v.findViewById(R.id.course_item_title);
        courseTitleTextView.setText(mDummyItem.title);

        TextView courseShortDescTextView = (TextView) v.findViewById(R.id.course_item_desc);
        courseShortDescTextView.setText(mDummyItem.shortDesc);

        ImageView courseImageView = (ImageView) v.findViewById(R.id.image1);
        courseImageView.setImageResource(mDummyItem.imageTest);

        return v;

    }




}