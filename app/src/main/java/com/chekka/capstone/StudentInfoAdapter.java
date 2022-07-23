package com.chekka.capstone;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * {@link DessertAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link Dessert} objects.
 * */
public class StudentInfoAdapter extends ArrayAdapter<com.chekka.capstone.StudentInfo> {

    private static final String LOG_TAG = com.chekka.capstone.StudentInfo.class.getSimpleName();

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param student_info A List of Dessert objects to display in a list
     */
    public StudentInfoAdapter(Activity context, ArrayList<com.chekka.capstone.StudentInfo> student_info) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, student_info);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_for_student_info, parent, false);
        }

        // Get the {@link Dessert} object located at this position in the list
        com.chekka.capstone.StudentInfo currentClasses = getItem(position);


        TextView examnameTextView = (TextView) listItemView.findViewById(R.id.examname_IF);
        examnameTextView.setText(currentClasses.getS_examname());

        ImageView iconTextView = (ImageView) listItemView.findViewById(R.id.examname_icon_IF);
        iconTextView.setImageResource(currentClasses.getS_examname_icon());

        TextView scorenameTextView = (TextView) listItemView.findViewById(R.id.scorename_IF);
        scorenameTextView.setText(currentClasses.getS_examscore_name());


        TextView scorenumberTextView = (TextView) listItemView.findViewById(R.id.score_IF);
        scorenumberTextView.setText(currentClasses.getS_examscore());

        TextView itemnumberTextView = (TextView) listItemView.findViewById(R.id.itemname_IF);
        itemnumberTextView.setText(currentClasses.getS_itemscore());

        TextView averagenumberTextView = (TextView) listItemView.findViewById(R.id.itemaverage_IF);
        averagenumberTextView.setText(currentClasses.getS_itemscore());
        return listItemView;
    }
}