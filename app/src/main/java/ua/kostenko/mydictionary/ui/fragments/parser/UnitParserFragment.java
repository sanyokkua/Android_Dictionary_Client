package ua.kostenko.mydictionary.ui.fragments.parser;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.dataaccess.DataAccessUtils;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.core.local.parsing.ParserUnit;
import ua.kostenko.mydictionary.ui.OnClickCustomListener;
import ua.kostenko.mydictionary.ui.dialogs.FileInfoDialog;
import ua.kostenko.mydictionary.ui.dialogs.UnitCreateDialog;
import ua.kostenko.mydictionary.ui.fragments.BaseFragment;
import ua.kostenko.mydictionary.ui.fragments.parser.tasks.AddAllTask;
import ua.kostenko.mydictionary.ui.fragments.parser.tasks.ParseTask;

import static ua.kostenko.mydictionary.core.commonutils.Utils.isNotEmpty;
import static ua.kostenko.mydictionary.core.commonutils.Utils.isNotNull;

public class UnitParserFragment extends BaseFragment {
    private static final String TAG = UnitParserFragment.class.getSimpleName();
    private static final int PICK_FILE_CODE = 1;
    @Bind(R.id.open_file_button) Button openFileButton;
    @Bind(R.id.parser_list) RecyclerView parserListView;
    @Inject DataAccessUtils dataAccessUtils;
    private List<ParserUnit> result;

    public UnitParserFragment() {
    }

    public static UnitParserFragment newInstance() {
        return new UnitParserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parser_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.open_file_button)
    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, PICK_FILE_CODE);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_FILE_CODE) {
            final String filePath = data.getData().getPath();
            openDialog(filePath);
        }
    }

    private void openDialog(@NonNull final String fileLocation) {
        final long fileSizeInBytes = dataAccessUtils.getFileByPath(fileLocation).length();
        final FileInfoDialog fileInfoDialog = new FileInfoDialog(getActivity(), getLayoutInflater(null),
                fileLocation, fileSizeInBytes, new OnClickCustomListener<String>() {
            @Override
            public void onItemClick(String item) {
                parse(fileLocation);
            }
        });
        fileInfoDialog.getDialog().show();
    }

    private void parse(@NonNull final String filePath) {
        ParseTask task = new ParseTask(getContext(), new ParseTask.OnFinish() {
            @Override
            public void onFinish(List<ParserUnit> unitsList) {
                result = unitsList;
                setAdapterWithData();
            }
        });
        task.execute(filePath);
    }

    private void setAdapterWithData() {
        if (isNotNull(parserListView)) {
            parserListView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ParserUnitRecyclerViewAdapter adapter = new ParserUnitRecyclerViewAdapter(result, new OnClickCustomListener<ParserUnit>() {
                @Override
                public void onItemClick(ParserUnit item) {
                    openAddDialog(item);
                }
            });
            parserListView.setAdapter(adapter);
        } else {
            Log.w(TAG, "Parser RecyclerView is null");
        }
    }

    private void openAddDialog(@NonNull final ParserUnit item) {
        final Unit unit = new Unit(item.getSource(), "", item.getCounter());
        final UnitCreateDialog unitCreateDialog = new UnitCreateDialog(getActivity(), getLayoutInflater(null), unit, null);
        unitCreateDialog.show();
    }

    @OnClick(R.id.fab)
    public void fabOnClick(FloatingActionButton floatingActionButton) {
        if (isNotEmpty(result)) {
            addAllUnitsWithDefaultTranslation(result);
        } else {
            Snackbar.make(floatingActionButton.getRootView(), R.string.parser_nothing_to_parse, Snackbar.LENGTH_LONG).show();
        }
    }

    private void addAllUnitsWithDefaultTranslation(@NonNull final List<ParserUnit> result) {
        new MaterialDialog.Builder(getContext())
                .title(R.string.parser_dialog_add_all_warning)
                .content(R.string.parser_dialog_add_all_question)
                .negativeText(R.string.standard_cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText(R.string.standard_ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        addAllUnits(result);
                    }
                })
                .canceledOnTouchOutside(false)
                .show();
    }

    private void addAllUnits(@NonNull List<ParserUnit> result) {
        AddAllTask addAllTask = new AddAllTask(getContext());
        addAllTask.execute(result);
    }
}
