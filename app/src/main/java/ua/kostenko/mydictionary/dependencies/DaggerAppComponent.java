package ua.kostenko.mydictionary.dependencies;

import javax.inject.Singleton;

import dagger.Component;
import ua.kostenko.mydictionary.core.local.dataaccess.DataAccessUtils;
import ua.kostenko.mydictionary.core.local.dataaccess.FileUtils;
import ua.kostenko.mydictionary.core.webpart.services.implementation.TranslateServiceImpl;
import ua.kostenko.mydictionary.ui.activities.DictionaryActivity;
import ua.kostenko.mydictionary.ui.dialogs.UnitCreateDialog;
import ua.kostenko.mydictionary.ui.fragments.dictionary.UnitInfoFragment;
import ua.kostenko.mydictionary.ui.fragments.dictionary.UnitRecyclerViewAdapter;
import ua.kostenko.mydictionary.ui.fragments.dictionary.UnitsFragment;
import ua.kostenko.mydictionary.ui.fragments.parser.UnitParserFragment;
import ua.kostenko.mydictionary.ui.fragments.parser.tasks.AddAllTask;
import ua.kostenko.mydictionary.ui.fragments.parser.tasks.ParseTask;

@Component(modules = {DaggerAppModule.class, DaggerDaoModule.class, DaggerUtilsModule.class, DaggerServicesModule.class})
@Singleton
public interface DaggerAppComponent {
    void inject(DictionaryActivity dictionaryActivity);

    void inject(UnitsFragment unitsFragment);

    void inject(UnitCreateDialog unitCreateDialog);

    void inject(DataAccessUtils dataAccessUtils);

    void inject(UnitParserFragment unitParserFragment);

    void inject(FileUtils fileUtils);

    void inject(ParseTask parseTask);

    void inject(TranslateServiceImpl translateService);

    void inject(UnitRecyclerViewAdapter unitRecyclerViewAdapter);

    void inject(UnitInfoFragment unitInfoFragment);

    void inject(AddAllTask addAllTask);
}
