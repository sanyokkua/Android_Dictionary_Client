package ua.kostenko.mydictionary.ui.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.core.webpart.enums.Languages;
import ua.kostenko.mydictionary.core.webpart.services.TranslateService;

import static ua.kostenko.mydictionary.core.commonutils.Utils.checkNotNull;

public class UnitCreateDialog {
    private final MaterialDialog.Builder builder;
    @NonNull private final View.OnClickListener onTranslateButtonClick;
    @NonNull private final MaterialDialog.SingleButtonCallback onNegativeButtonClick;
    @NonNull private final MaterialDialog.SingleButtonCallback onPositiveButtonClick;
    @Bind(R.id.source_text) EditText sourceEditText;
    @Bind(R.id.translation_text) EditText translationEditText;
    @Bind(R.id.user_translation_text) EditText userTranslationEditText;
    @Bind(R.id.translate_button) Button translateButton;
    @BindString(R.string.dictionary_add_unit) String positiveText;
    @BindString(R.string.standard_cancel) String negativeText;
    @Inject UnitDao unitDao;
    @Inject TranslateService translateService;

    public UnitCreateDialog(@NonNull final Context context, @NonNull final LayoutInflater inflater) {
        final View dialogView = inflater.inflate(R.layout.dialog_create_unit, null, false);
        ButterKnife.bind(this, dialogView);
        App.getAppComponent().inject(this);
        builder = new MaterialDialog.Builder(context);
        builder.customView(dialogView, false);
        onNegativeButtonClick = new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        };
        onPositiveButtonClick = new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                save(dialog);
            }
        };
        onTranslateButtonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translate(v);
            }
        };
    }

    public UnitCreateDialog(@NonNull final Context context, LayoutInflater inflater, Unit unit) {
        this(context, inflater);
        sourceEditText.setText(unit.getSource());
        translationEditText.setText(unit.getTranslations());
        userTranslationEditText.setText(unit.getUserTranslation());
    }

    private void save(MaterialDialog dialog) {
        Toast.makeText(dialog.getView().getContext(), "Ok", Toast.LENGTH_LONG).show(); //TODO: Add realization
        unitDao.saveUnit(new Unit(getSourceText(), getTranslationText(), getUserTranslationText(), 0));
    }

    private void translate(View v) {
        Toast.makeText(v.getContext(), "Translate2", Toast.LENGTH_LONG).show(); //TODO: Add realization
        translationEditText.setText(translateService.translate(Languages.ENGLISH, Languages.RUSSIAN, getSourceText()));
    }

    public void show() {
        getDialog().show();
    }

    private MaterialDialog getDialog() {
        checkNotNull(onNegativeButtonClick);
        checkNotNull(onPositiveButtonClick);
        checkNotNull(onTranslateButtonClick);
        builder.positiveText(positiveText);
        builder.negativeText(negativeText);
        builder.onPositive(onPositiveButtonClick);
        builder.onNegative(onNegativeButtonClick);
        translateButton.setOnClickListener(onTranslateButtonClick);
        return builder.build();
    }

    public String getSourceText() {
        return sourceEditText.getText().toString();
    }

    public String getTranslationText() {
        return translationEditText.getText().toString();
    }

    public String getUserTranslationText() {
        return userTranslationEditText.getText().toString();
    }
}
