package helper;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Id {

	public String TaoId() {
        SimpleDateFormat formatter = new SimpleDateFormat("ddHHmmss");
        return formatter.format(new Date());
    }
}
