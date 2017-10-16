package be.industria.industria24u.industria24u;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private List<Object> parents;
    private List<String> tempChildren;
    private HashMap<Object, List<String>> relations;
    private LayoutInflater mInflater;
    private Activity activity;

    public ExpandableAdapter(HashMap<Object, List<String>> children, List<Object> parents) {
        this.parents = parents;
        this.relations = children;
    }

    public void setInflater(LayoutInflater mInflater, Activity act) {
        this.mInflater = mInflater;
        this.activity = act;
    }

    @Override
    public Object getGroup(int parentPosition) {
        return parents.get(parentPosition);
    }

    @Override
    public long getGroupId(int parentPosition) {
        return parentPosition; // NOOP
    }

    @Override
    public int getGroupCount() {
        return parents.size();
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int parentPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) { convertView = mInflater.inflate(R.layout.list_group_row, null); }
        CheckedTextView checked = (CheckedTextView) convertView;
        checked.setText(getGroup(parentPosition).toString()); // Make sure the entity always has a toString() method.
        if (isExpanded) {
            checked.setCheckMarkDrawable(R.drawable.ic_drop_up);
        } else {
            checked.setCheckMarkDrawable(R.drawable.ic_drop_down);
        }
        return convertView;
    }

    @Override
    public List<String> getChild(int parentPosition, int childPosition) {
        return relations.get(getGroup(parentPosition));
    }

    @Override
    public long getChildId(int parentPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int parentPosition) {
        return (relations.get(getGroup(parentPosition))).size();
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int parentPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        tempChildren = relations.get(getGroup(parentPosition));
        TextView text;
        if (convertView == null) { convertView = mInflater.inflate(R.layout.list_child_row, null); }
        text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(tempChildren.get(childPosition));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, tempChildren.get(childPosition),
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int parentPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
