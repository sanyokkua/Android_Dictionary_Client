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
import com.google.common.base.Preconditions;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;

public class UnitCreateDialog {
    private final MaterialDialog.Builder builder;
    @NonNull
    private final View.OnClickListener onTranslateButtonClick;
    @NonNull
    private final MaterialDialog.SingleButtonCallback onNegativeButtonClick;
    @NonNull
    private final MaterialDialog.SingleButtonCallback onPositiveButtonClick;
    @Bind(R.id.source_text)
    protected EditText sourceEditText;
    @Bind(R.id.translation_text)
    protected EditText translationEditText;
    @Bind(R.id.user_translation_text)
    protected EditText userTranslationEditText;
    @Bind(R.id.translate_button)
    protected Button translateButton;
    @BindString(R.string.dictionary_add_unit)
    protected String positiveText;
    @BindString(R.string.standard_cancel)
    protected String negativeText;

    public UnitCreateDialog(@NonNull final Context context, LayoutInflater inflater, Unit unit) {
        this(context, inflater);
        sourceEditText.setText(unit.getSource());
        translationEditText.setText(unit.getTranslations());
        userTranslationEditText.setText(unit.getUserTranslation());
    }

    public UnitCreateDialog(@NonNull final Context context, @NonNull final LayoutInflater inflater) {
        final View dialogView = inflater.inflate(R.layout.dialog_create_unit, null, false);
        ButterKnife.bind(this, dialogView);
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

    private void save(MaterialDialog dialog) {
        Toast.makeText(dialog.getView().getContext(), "Ok", Toast.LENGTH_LONG).show(); //TODO: Add realization
    }

    private void translate(View v) {
        Toast.makeText(v.getContext(), "Translate", Toast.LENGTH_LONG).show(); //TODO: Add realization
    }

    public void show() {
        getDialog().show();
    }

    private MaterialDialog getDialog() {
        Preconditions.checkNotNull(onNegativeButtonClick);
        Preconditions.checkNotNull(onPositiveButtonClick);
        Preconditions.checkNotNull(onTranslateButtonClick);
        builder.positiveText(positiveText);
        builder.negativeText(negativeText);
        builder.onPositive(onPositiveButtonClick);
        builder.onNegative(onNegativeButtonClick);
        translateButton.setOnClickListener(onTranslateButtonClick);
        return builder.build();
    }
}
