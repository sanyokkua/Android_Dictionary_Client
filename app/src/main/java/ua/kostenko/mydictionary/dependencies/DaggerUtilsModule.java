package ua.kostenko.mydictionary.dependencies;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.kostenko.mydictionary.core.local.dataaccess.DataAccessUtils;
import ua.kostenko.mydictionary.core.local.dataaccess.FileUtils;
import ua.kostenko.mydictionary.core.local.parsing.Parser;
import ua.kostenko.mydictionary.core.local.parsing.ParserUnit;
import ua.kostenko.mydictionary.core.local.parsing.implementation.TxtParser;

@Module
public class DaggerUtilsModule {

    @Provides
    @Singleton
    public DataAccessUtils provideDataAccessUtils() {
        return new DataAccessUtils();
    }

    @Provides
    @Singleton
    public FileUtils provideFileUtils() {
        return new FileUtils();
    }

    @Provides
    @Singleton
    public Parser<ParserUnit> provideParser() {
        return new TxtParser();
    }
}
