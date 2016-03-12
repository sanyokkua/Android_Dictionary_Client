package ua.kostenko.mydictionary.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.common.base.Preconditions;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import ua.kostenko.mydictionary.R;


public class FileInfoDialog {
    private MaterialDialog.Builder builder;
    @NonNull
    private MaterialDialog.SingleButtonCallback onNegativeButtonClick;
    @NonNull
    private MaterialDialog.SingleButtonCallback onPositiveButtonClick;
    @NonNull
    private OnClickCustomListener<String> okCallback;
    @Bind(R.id.new_file_size)
    TextView sizeEditText;
    @Bind(R.id.new_file_location)
    TextView locationEditText;
    @BindString(R.string.dictionary_add)
    String positiveText;
    @BindString(R.string.std_cancel)
    String negativeText;

    private FileInfoDialog(final Context context, final LayoutInflater inflater) {
        View dialogView = inflater.inflate(R.layout.new_open_file_dialog, null, false);
        ButterKnife.bind(this, dialogView);
        builder = new MaterialDialog.Builder(context);
        builder.customView(dialogView, false);
        onNegativeButtonClick = new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull final MaterialDialog dialog, @NonNull final DialogAction which) {
                dialog.dismiss();
            }
        };
        onPositiveButtonClick = new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                okCallback.onItemClick("Ok");
            }
        };
    }

    public FileInfoDialog(@NonNull final Context context, @NonNull final LayoutInflater inflater,
                          @NonNull final String fileLocation, final long fileSize,
                          @NonNull final OnClickCustomListener<String> okCallback) {
        this(context, inflater);
        Preconditions.checkNotNull(okCallback);
        sizeEditText.setText(String.valueOf(fileSize));
        locationEditText.setText(fileLocation);
        this.okCallback = okCallback;
    }

    public MaterialDialog getDialog() {
        Preconditions.checkNotNull(onNegativeButtonClick);
        Preconditions.checkNotNull(onPositiveButtonClick);
        builder.positiveText(positiveText);
        builder.negativeText(negativeText);
        builder.onPositive(onPositiveButtonClick);
        builder.onNegative(onNegativeButtonClick);
        return builder.build();
    }
}
