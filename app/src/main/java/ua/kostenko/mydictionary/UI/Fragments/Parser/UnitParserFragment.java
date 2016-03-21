package ua.kostenko.mydictionary.ui.fragments.parser;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.dataaccess.DataAccessUtils;
import ua.kostenko.mydictionary.core.local.dataaccess.FileUtils;
import ua.kostenko.mydictionary.core.local.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.core.local.parsing.Parser;
import ua.kostenko.mydictionary.core.local.parsing.ParserUnit;
import ua.kostenko.mydictionary.core.local.parsing.implementation.TxtParser;
import ua.kostenko.mydictionary.core.webpart.enums.Languages;
import ua.kostenko.mydictionary.core.webpart.services.TranslateService;
import ua.kostenko.mydictionary.ui.OnClickCustomListener;
import ua.kostenko.mydictionary.ui.dialogs.FileInfoDialog;
import ua.kostenko.mydictionary.ui.dialogs.UnitCreateDialog;

public class UnitParserFragment extends Fragment {
    private static final String TAG = UnitParserFragment.class.getSimpleName();
    private static final int PICK_FILE_CODE = 1;

    @Bind(R.id.open_file_button)
    protected Button openFileButton;
    @Bind(R.id.new_parser_list)
    protected RecyclerView parserListView;
    private UnitDao unitDao; //TODO: inject dao
    private List<ParserUnit> result;
    private TranslateService translateService;

    public UnitParserFragment() {
        // Required empty public constructor
    }

    public static UnitParserFragment newInstance() {
        return new UnitParserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        final DataAccessUtils dataAccessUtils = new DataAccessUtils(getActivity().getApplicationContext());
        final long fileSizeInBytes = dataAccessUtils.getFileByPath(fileLocation).length();
        final FileInfoDialog fileInfoDialog = new FileInfoDialog(getActivity(), getLayoutInflater(null),
                fileLocation, fileSizeInBytes, new OnClickCustomListener<String>() {
            @Override
            public void onItemClick(String item) {
                onFilePickedAndParsingStart(fileLocation);
            }
        });
        fileInfoDialog.getDialog().show();
    }

    private void onFilePickedAndParsingStart(@NonNull final String filePath) {
        final FileUtils fileUtils = new FileUtils(getActivity().getApplicationContext());
        final Parser<ParserUnit> parser = new TxtParser();
        result = parser.parse(fileUtils.readFile(filePath));
        if (parserListView != null) {
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
        final UnitCreateDialog unitCreateDialog = new UnitCreateDialog(getActivity(), getLayoutInflater(null), unit);
        unitCreateDialog.show();
    }

    @OnClick(R.id.new_fab)
    public void fabOnClick(FloatingActionButton floatingActionButton) { //TODO: uncomment after
        if (result != null && !result.isEmpty()) {
            Snackbar.make(floatingActionButton, "Stub for add all", Snackbar.LENGTH_LONG).show();
            //addAllUnitsWithDefaultTranslation(result);
        } else {
            Snackbar.make(floatingActionButton.getRootView(), R.string.parser_nothing_to_parse, Snackbar.LENGTH_LONG).show();
        }
    }

    private void addAllUnitsWithDefaultTranslation(@NonNull final List<ParserUnit> result) {
        for (ParserUnit current : result) {
            Unit unit = unitDao.findBySource(current.getSource());
            if (unit != null) {
                update(current, unit);
            } else {
                create(current);
            }
        }
    }

    private void update(@NonNull final ParserUnit current, Unit unit) {
        long counter = unit.getCounter() + current.getCounter();
        unit.setCounter(counter);
        unitDao.saveUnit(unit);
    }

    private void create(@NonNull final ParserUnit current) {
        String translation = translateService.translate(Languages.ENGLISH, Languages.RUSSIAN, current.getSource());
        Unit newUnit = new Unit(current.getSource(), translation, current.getCounter());
        unitDao.saveUnit(newUnit);
    }
}
