package piftik.github.com.weatherproject.models;

import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Locale;

import piftik.github.com.weatherproject.models.annotations.Table;
import piftik.github.com.weatherproject.models.annotations.types.DBDouble;
import piftik.github.com.weatherproject.models.annotations.types.DBInteger;
import piftik.github.com.weatherproject.models.annotations.types.DBIntegerAutoIncrement;
import piftik.github.com.weatherproject.models.annotations.types.DBIntegerPrimaryKey;
import piftik.github.com.weatherproject.models.annotations.types.DBString;

public final class TableQueryGenerator {
    private static final String SQL_TABLE_CREATE_TEMPLATE = "CREATE TABLE IF NOT EXISTS %s (%s)";
    private static final String SQL_TABLE_DELETE_TEMPLATE = "DROP TABLE IF EXISTS %s";
    private static final String SQL_TABLE_CREATE_FIELD_TEMPLATE = "%s %s";

    //    Package local
    @Nullable
    public static String getTableCreateQuery(final Class<? extends TableClass> clazz) {

        final Table table = clazz.getAnnotation(Table.class);

        if (table != null) {
            try {
                final String name = table.name();

                final StringBuilder builder = new StringBuilder();
                final Field[] fields = clazz.getFields();
                boolean firstField = true;
                for (final Field field : fields) {
                    final Annotation[] annotations = field.getAnnotations();

                    if (annotations == null) {
                        continue;
                    }

                    String type = null;

                    for (final Annotation annotation : annotations) {
                        if (annotation instanceof DBInteger) {
                            type = ((DBInteger) annotation).value();
                        } else if (annotation instanceof DBDouble) {
                            type = ((DBDouble) annotation).value();
                        } else if (annotation instanceof DBString) {
                            type = ((DBString) annotation).value();
                        } else if (annotation instanceof DBIntegerPrimaryKey) {
                            type = ((DBIntegerPrimaryKey) annotation).value();
                        } else if (annotation instanceof DBIntegerAutoIncrement) {
                            type = ((DBIntegerAutoIncrement) annotation).value();
                        }
                    }

                    if (type == null) {
                        continue;
                    }

                    final String value = (String) field.get(null);

                    if (firstField) {
                        firstField = false;
                    } else {
                        builder.append(", ");
                    }

                    builder.append(String.format(Locale.getDefault(), SQL_TABLE_CREATE_FIELD_TEMPLATE, value, type));

                }

                return String.format(Locale.getDefault(), SQL_TABLE_CREATE_TEMPLATE, name, builder);
            } catch (final Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Nullable
    static String getTableDeleteQuery(final Class<? extends TableClass> clazz) {

        final Table table = clazz.getAnnotation(Table.class);

        if (table != null) {
            return String.format(Locale.getDefault(), SQL_TABLE_DELETE_TEMPLATE, table.name());
        } else {
            return null;
        }
    }

    @Nullable
    public static String getTableName(final Class<? extends TableClass> clazz) {
        final Table table = clazz.getAnnotation(Table.class);
        if (table != null) {
            return table.name();
        } else {
            return null;
        }
    }
}
