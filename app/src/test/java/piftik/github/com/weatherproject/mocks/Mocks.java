package piftik.github.com.weatherproject.mocks;

import junit.framework.Assert;

import java.io.InputStream;

public final class Mocks {
    public static InputStream stream() {
        final InputStream resourceAsStream = Mocks.class.getClassLoader().getResourceAsStream("Json.json");
        Assert.assertNotNull("resource not found, maybe you forget add .json?", resourceAsStream);
        return resourceAsStream;
    }
}
