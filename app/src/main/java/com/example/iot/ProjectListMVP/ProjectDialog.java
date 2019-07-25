package com.example.iot.ProjectListMVP;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iot.R;
import com.example.iot.model.Project;

public class ProjectDialog extends Dialog {
    public EditText project_name_field,token_field;
    Button saveBtn;
    ProjectListContract.Presenter presenter;
    Context context;
    Project project;

    public ProjectDialog( Context context,ProjectListContract.Presenter presenter) {
        super(context);
        this.context=context;
        this.presenter=presenter;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.project_add_dialog);

        saveBtn=findViewById(R.id.save_project_btn);
        project_name_field=findViewById(R.id.project_name_input);
        token_field=findViewById(R.id.token_input);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String projectName=project_name_field.getText().toString();
                String token=token_field.getText().toString();

                if (TextUtils.isEmpty(projectName) || TextUtils.isEmpty(token)){
                    Toast.makeText(context,"please input both data",Toast.LENGTH_SHORT).show();
                }else {
                    presenter.addproject(projectName,token);
                    dismiss();
                }
            }
        });

        findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteProject(project);
                dismiss();
            }
        });

        findViewById(R.id.updateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token=token_field.getText().toString();
                String projectName=project_name_field.getText().toString();
                if (!TextUtils.isEmpty(projectName) && !TextUtils.isEmpty(token)){
                    if(project.getToken().equals(token)){
                        project.setProjectName(projectName);
                        presenter.updateProject(project);
                        dismiss();
                    }else {
                        if (presenter.projectAlreadyExists(token)){

                        }else {
                            presenter.deleteProject(project);
                            presenter.addproject(projectName,token);
                        }

                        dismiss();
                    }
                }
            }
        });

    }

    public void loadProject(Project project){
        this.project=project;
        saveBtn.setVisibility(View.GONE);
        findViewById(R.id.linearlayout).setVisibility(View.VISIBLE);
        project_name_field.setText(project.getProjectName());
        token_field.setText(project.getToken());

    }
}
