package ua.kostenko.mydictionary.ui.fragments.dictionary.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.commonutils.Utils;
import ua.kostenko.mydictionary.core.local.database.DatabaseHelper;
import ua.kostenko.mydictionary.core.local.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.ui.fragments.dictionary.UnitInfoFragment;
import ua.kostenko.mydictionary.ui.iterfaces.OnClickCustomListener;
import ua.kostenko.mydictionary.ui.iterfaces.OnLongClickCustomListener;
import ua.kostenko.mydictionary.ui.iterfaces.OnUpdate;

import static ua.kostenko.mydictionary.core.commonutils.Utils.checkNotNull;
import static ua.kostenko.mydictionary.core.commonutils.Utils.isNotNull;

public class UnitRecyclerViewAdapter extends RecyclerView.Adapter<UnitRecyclerViewAdapter.ViewHolder> implements OnUpdate {
    private static final String TAG = UnitRecyclerViewAdapter.class.getSimpleName();
    @NonNull private final OnClickCustomListener<Unit> onClickCustomListener;
    @NonNull private final OnLongClickCustomListener<Unit> onLongClickCustomListener;
    @Inject UnitDao unitDao;
    @Inject DatabaseHelper databaseHelper;
    @NonNull private List<Unit> unitList;

    public UnitRecyclerViewAdapter() {
        App.getAppComponent().inject(this);
        onClickCustomListener = new OnClickCustomListener<Unit>() {
            @Override
            public void onItemClick(Unit item) {
                show(item);
            }
        };
        onLongClickCustomListener = new OnLongClickCustomListener<Unit>() {
            @Override
            public void onItemLongClick(Unit item, View view) {
                onLongClick(item, view);
            }
        };
        unitList = unitDao.findAll();
    }

    private void onLongClick(final Unit item, final View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.CENTER);
        popupMenu.inflate(R.menu.unit_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_show:
                        show(item);
                        break;
                    case R.id.action_remove:
                        remove(item, view);
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void show(Unit item) {
        Fragment fragment = UnitInfoFragment.newInstance(item.getSource());
        Utils.checkNotNull(fragment, "Fragment can't be null!");
        EventBus.getDefault().post(fragment);
        Log.d(TAG, String.format("Fragment was replaced to : %s", fragment.getClass().getSimpleName()));
    }

    private void remove(final Unit item, View view) {
        MaterialDialog dialog = new MaterialDialog.Builder(view.getContext())
                .title("Remove unit?")
                .positiveText("Remove")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        unitDao.removeUnit(item);
                        update();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        dialog.show();
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dictionary_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setFields(unitList.get(position));
    }

    @Override
    public int getItemCount() {
        return unitList.size();
    }

    @Override
    public void update() {
        List<Unit> unitsUpdated = databaseHelper.getUnits();
        if (isNotNull(unitsUpdated) && unitsUpdated.size() != unitList.size()) {
            unitList = unitsUpdated;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final View view;
        @Bind(R.id.unit_source_text)
        public TextView source;
        @Bind(R.id.unit_translation_text)
        public TextView translation;
        @Bind(R.id.unit_counter_text)
        public TextView counter;
        public Unit unit;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void setFields(Unit unit) {
            this.unit = unit;
            source.setText(unit.getSource());
            translation.setText(unit.getTranslations());
            counter.setText(String.valueOf(unit.getCounter()));
        }

        @Override
        public void onClick(View v) {
            checkNotNull(onClickCustomListener, "OnClickCustomListener is not set");
            onClickCustomListener.onItemClick(unit);
        }

        @Override
        public boolean onLongClick(View v) {
            checkNotNull(onLongClickCustomListener, "OnLongClickCustomListener is not set");
            onLongClickCustomListener.onItemLongClick(unit, v);
            return true;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + translation.getText() + "'";
        }
    }
}
