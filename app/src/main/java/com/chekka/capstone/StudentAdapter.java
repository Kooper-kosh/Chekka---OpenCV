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
public class StudentAdapter extends ArrayAdapter<Student> {

    private static final String LOG_TAG = com.chekka.capstone.StudentAdapter.class.getSimpleName();

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param student A List of Dessert objects to display in a list
     */
    public StudentAdapter(Activity context, ArrayList<Student> student) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, student);
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
                    R.layout.list_item_for_student, parent, false);
        }

        // Get the {@link Dessert} object located at this position in the list
        Student currentClasses = getItem(position);


        TextView nameTextView = (TextView) listItemView.findViewById(R.id.item_student_name_ST);
        nameTextView.setText(currentClasses.getS_StudentName());

       // ImageView numberTextView = (ImageView) listItemView.findViewById(R.id.item_student_icon_ST);
       // numberTextView.setImageResource(currentClasses.getS_StudentIcon());

      //  ImageView quesTextView = (ImageView) listItemView.findViewById(R.id.item_student_number_icon_ST);
      //  quesTextView.setImageResource(currentClasses.getS_StudentNumberIcon());

        TextView scannedTextView = (TextView) listItemView.findViewById(R.id.item_student_number_ST);
        scannedTextView.setText(String.valueOf(currentClasses.getS_StudentNumber()));

        ImageView iconView = (ImageView) listItemView.findViewById(R.id.item_student_edit_icon_ST);
        iconView.setImageResource(currentClasses.getS_StudentEditIcon());


        return listItemView;
    }
}