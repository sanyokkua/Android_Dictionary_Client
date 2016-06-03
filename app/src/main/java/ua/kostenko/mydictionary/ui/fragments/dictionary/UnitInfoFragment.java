package ua.kostenko.mydictionary.ui.fragments.dictionary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.commonutils.Utils;
import ua.kostenko.mydictionary.core.local.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.local.database.domain.MyMap;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.ui.fragments.BaseFragment;

public class UnitInfoFragment extends BaseFragment {
    private static final String TAG = UnitsFragment.class.getSimpleName();
    private static final String UNIT_SOURCE = "UNIT_SOURCE";
    @Bind(R.id.source_text) TextView sourceTextView;
    @Bind(R.id.translation_text) TextView translationTextView;
    @Bind(R.id.additional_translations) TextView additionalTranslationTextView;
    @Bind(R.id.user_translation) TextView userTranslationTextView;
    @Bind(R.id.technologies) TextView technologiesTextView;
    @Inject UnitDao unitDao;
    private Unit unit;

    public UnitInfoFragment() {
    }

    public static UnitInfoFragment newInstance(@NonNull final String source) {
        UnitInfoFragment fragment = new UnitInfoFragment();
        Bundle args = new Bundle();
        args.putString(UNIT_SOURCE, source);
        Log.d(TAG, String.format("Bundled source is: %s", source));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        Bundle arguments = getArguments();
        String source = arguments.getString(UNIT_SOURCE);
        Log.d(TAG, String.format("Unbundled source is: %s", source));
        Utils.checkNotNull(source);
        unit = unitDao.findBySource(source);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unit_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onResume() {
        String valueAsString = getPrettyJsonFromMap(unit.getTranslationsAdditional());
        sourceTextView.setText(unit.getSource());
        translationTextView.setText(unit.getTranslations());
        additionalTranslationTextView.setText(valueAsString);
        userTranslationTextView.setText(unit.getUserTranslation());
        technologiesTextView.setText(unit.getTechnologies());
        super.onResume();
    }

    private String getPrettyJsonFromMap(MyMap translationsAdditional) {
        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = "";
        try {
            valueAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(translationsAdditional);
        } catch (JsonProcessingException e) {
            Log.w(TAG, "Error with prettyfying json", e);
        }
        return valueAsString;
    }
}
