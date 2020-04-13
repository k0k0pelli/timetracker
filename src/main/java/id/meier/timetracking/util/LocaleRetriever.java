package id.meier.timetracking.util;

import com.vaadin.flow.component.UI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Component
@Order(0)
public class LocaleRetriever {
    private Locale locale;

    @Value("${id.meier.time.tracker.locale.lang}")
    private String localeLang;

    @Value("${id.meier.time.tracker.locale.country}")
    private String localeCountry;

    private boolean initialized = false;

    public Locale getLocale() {
        ensureInitialized();
        return locale;
    }

    private void ensureInitialized() {
        if (!initialized) {
            locale = null;
            if (!StringUtils.isEmpty(localeLang) && !StringUtils.isEmpty(localeCountry)) {
                locale = new Locale(localeLang, localeCountry);

            } else if (!StringUtils.isEmpty(localeLang)) {
                locale = new Locale(localeLang);
            } else {
                locale = Locale.getDefault();
            }

            Locale.setDefault(locale);
            UI currentUi = UI.getCurrent();
            if (currentUi != null) {
                currentUi.setLocale(locale);
            }

            initialized = true;
        }
    }
}
