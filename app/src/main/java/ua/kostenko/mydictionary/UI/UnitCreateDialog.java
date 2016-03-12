package ua.kostenko.mydictionary.UI;

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
    private MaterialDialog.Builder builder;
    @NonNull
    private View.OnClickListener onTranslateButtonClick;
    @NonNull
    private MaterialDialog.SingleButtonCallback onNegativeButtonClick;
    @NonNull
    private MaterialDialog.SingleButtonCallback onPositiveButtonClick;
    @Bind(R.id.source_text)
    EditText sourceEditText;
    @Bind(R.id.translation_text)
    EditText translationEditText;
    @Bind(R.id.user_translation_text)
    EditText userTranslationEditText;
    @Bind(R.id.translate_button)
    Button translateButton;
    @BindString(R.string.dictionary_add)
    String positiveText;
    @BindString(R.string.std_cancel)
    String negativeText;

    public UnitCreateDialog(Context context, LayoutInflater inflater) {
        View dialogView = inflater.inflate(R.layout.new_dialog_create, null, false);
        ButterKnife.bind(this, dialogView);
        builder = new MaterialDialog.Builder(context);
        builder.customView(dialogView, false);
        onNegativeButtonClick = new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
                dialog.dismiss();
            }
        };
        onPositiveButtonClick = new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog dialog, DialogAction which) {
                Toast.makeText(dialog.getView().getContext(), "Ok", Toast.LENGTH_LONG).show();
            }
        };
        onTranslateButtonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Translate", Toast.LENGTH_LONG).show();
            }
        };
    }

    public UnitCreateDialog(Context context, LayoutInflater inflater, Unit unit) {
        this(context, inflater);
        sourceEditText.setText(unit.getSource());
        translationEditText.setText(unit.getTranslations());
        userTranslationEditText.setText(unit.getUserTranslation());
    }

    public MaterialDialog getDialog() {
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
