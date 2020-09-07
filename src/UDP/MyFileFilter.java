package UDP;

import java.io.File;
import java.io.FilenameFilter;

// This filter checks for specified filetype and filesize
// In our case we want only .jpg files that are less then or equals to 65507 bytes
public class MyFileFilter implements FilenameFilter {
    private String filetype;
    private int size;

    public MyFileFilter(String filetype, int size){
        this.filetype = filetype;
        this.size = size;
    }

    @Override
    public boolean accept(File dir, String name) {
        File file = new File(dir, name);
        return name.endsWith(filetype) && file.length() <= size;
    }
}
