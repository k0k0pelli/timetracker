package id.meier.timetracking.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vaadin.flow.i18n.I18NProvider;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Component
@Order(1)
public class TranslationProvider implements I18NProvider {

    private static final String BUNDLE_PREFIX = "translate";

    private final Locale LOCALE_DE = new Locale("de", "CH");
    private final Locale LOCALE_IT = new Locale("it", "CH");
    private final Locale LOCALE_FR = new Locale("fr", "CH");
    private final Locale LOCALE_EN = new Locale("en", "GB");

    private final List<Locale> locales = Collections
            .unmodifiableList(Arrays.asList(LOCALE_DE, LOCALE_IT, LOCALE_FR, LOCALE_EN));

    private static final LoadingCache<Locale, ResourceBundle> bundleCache = CacheBuilder
            .newBuilder()
            .build(new CacheLoader<Locale, ResourceBundle>() {
                @Override
                public ResourceBundle load(final Locale key) {
                    return initializeBundle(key);
                }
            });

    @Override
    public List<Locale> getProvidedLocales() {
        return locales;
    }

    public String getTranslation(String key, Object... params) {
    	return getTranslation(key, Locale.getDefault(), params);
    }
    
    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        if (key == null) {
            LoggerFactory.getLogger(TranslationProvider.class.getName())
                    .warn("Got lang request for key with null value!");
            return "";
        }



        String value;
        try {
            final ResourceBundle bundle = bundleCache.get(locale);
            value = bundle.getString(key);
        } catch (final MissingResourceException | ExecutionException e) {
            LoggerFactory.getLogger(TranslationProvider.class.getName())
                    .warn("Missing resource", e);
            return "!" + locale.getLanguage() + ": " + key;
        }
        if (params.length > 0) {
            value = String.format(value, params);
        }
        return value;
    }

    private static ResourceBundle initializeBundle(final Locale locale) {
        return readProperties(locale);
    }

    private static ResourceBundle readProperties(final Locale locale) {
        final ClassLoader cl = TranslationProvider.class.getClassLoader();

        ResourceBundle propertiesBundle = null;
        try {
            propertiesBundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale,
                    cl);
        } catch (final MissingResourceException e) {
            LoggerFactory.getLogger(TranslationProvider.class.getName())
                    .warn("Missing resource", e);
        }
        return propertiesBundle;
    }
}