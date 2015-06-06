package ua.nure.mydictionary.UI.Fragments.Parser;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;

import ua.nure.mydictionary.R;
import ua.nure.mydictionary.UI.SecondaryClasses.OpenFileDialog;
import ua.nure.mydictionary.UI.SecondaryClasses.ToolbarHandler;
import ua.nure.mydictionary.UI.SecondaryInterfaces.Identifier;

public class ParserFragment extends Fragment implements Identifier, View.OnClickListener {
    public static final String NAME = "ParserFragment";

    private Toolbar mToolbar;
    private Button mButtonOpenFile;
    private Button mButtonParse;
    private String mFilePath;

    public ParserFragment() {
        // Required empty public constructor
    }

    public static ParserFragment newInstance() {
        ParserFragment fragment = new ParserFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parser, container, false);
        mToolbar = ToolbarHandler.getToolbar(getActivity());
        ToolbarHandler.setOnlyTitleMode(mToolbar);
        ToolbarHandler.getTitleTextView(mToolbar).setText(getString(R.string.title_parser_fragment));
        mButtonOpenFile = (Button) rootView.findViewById(R.id.parser_open_file_button);
        mButtonParse = (Button) rootView.findViewById(R.id.parser_parse_button);
        mButtonOpenFile.setOnClickListener(this);
        mButtonParse.setOnClickListener(this);
        return rootView;
    }


    @Override
    public String getIdentifier() {
        return NAME;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.parser_open_file_button:
                OpenFileDialog dialog = new OpenFileDialog(getActivity());
                dialog.setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    public void OnSelectedFile(String fileName) {
                        Log.d("File name:", fileName);
                        mFilePath = fileName;
                    }
                });
                dialog.show();
                break;
            case R.id.parser_parse_button:
                startParsing();
                break;
        }
    }

    private void startParsing() {
        if (mFilePath != null) {
            Snackbar.make(getView(), "parsing:" + mFilePath, Snackbar.LENGTH_LONG).show();
        } else new MaterialDialog.Builder(getActivity()).title("Error")
                .content("File wasn't chosen").cancelable(true)
                .negativeText(getActivity().getResources()
                        .getString(R.string.std_cancel)).callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.cancel();
                    }
                }).show();
    }
}
