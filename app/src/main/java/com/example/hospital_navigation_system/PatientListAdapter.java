package com.example.hospital_navigation_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.hospital_navigation_system.PatientDetails;
import java.util.List;

public class PatientListAdapter extends ArrayAdapter<PatientDetails> {

    private final Context context;
    private final int layoutResourceId;
    private final List<PatientDetails> data;

    public PatientListAdapter(PatientDashboard context, int layoutResourceId, List<PatientDetails> data) {

        super((Context) context, layoutResourceId, data);
        System.out.println("Printing data" + data);

        System.out.println("Patientlist adapter");
        this.context = (Context) context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PatientHolder holder;
        System.out.println("Patientlist"+ position);
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PatientHolder();
            holder.txtName = row.findViewById(R.id.patientName);
            holder.txtAge = row.findViewById(R.id.patientAge);
            holder.txtHeight = row.findViewById(R.id.patientHeight);
            holder.txtWeight = row.findViewById(R.id.patientWeight);
            holder.txtSymptoms = row.findViewById(R.id.patientSymptoms);

            row.setTag(holder);
        } else {
            holder = (PatientHolder) row.getTag();
        }

        PatientDetails patient = data.get(position);
        holder.txtName.setText(patient.patientName);
        holder.txtAge.setText(String.valueOf(patient.age));
        holder.txtHeight.setText(String.valueOf(patient.height));
        holder.txtWeight.setText(String.valueOf(patient.weight));
        holder.txtSymptoms.setText(patient.symptoms);

        return row;
    }

    static class PatientHolder {
        TextView txtName;
        TextView txtAge;
        TextView txtHeight;
        TextView txtWeight;
        TextView txtSymptoms;
    }
}

