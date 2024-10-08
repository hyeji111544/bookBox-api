package green.mtcoding.bookbox.core.util;


public class Msg {

    public static String fail(String msg) {
        return "{\"reason\": " + msg + "}";
    }
}
