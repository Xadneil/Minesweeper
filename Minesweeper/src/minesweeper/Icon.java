package minesweeper;

/**
 * An enumeration of the icons used. Contains the corresponding ImageIcon.
 *
 * @author Daniel
 */
public enum Icon {

    I0("0"),
    I1("1"),
    I2("2"),
    I3("3"),
    I4("4"),
    I5("5"),
    I6("6"),
    I7("7"),
    I8("8"),
    COVERED("plain"),
    FLAG("flag"),
    QUESTION("question"),
    MINE("mine"),
    LOST("minered"),
    BADMINE("badflag"),
    SMILE("smile"),
    PRESSED("pressed"),
    DEAD("dead"),
    SUN("sun"),
    TEASE("tease");
    public final javax.swing.ImageIcon image;
    private final String path = "/images/", ext = ".gif";

    Icon(String image) {
        java.net.URL resource = getClass().getResource(path + image + ext);
        this.image = new javax.swing.ImageIcon(resource);
    }
}