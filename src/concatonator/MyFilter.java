package concatonator;

import java.io.File;
import java.io.FileFilter;

public class MyFilter implements FileFilter
{

	@Override
	public boolean accept(File pathname) {		
		String pathName = pathname.toString();
		if (pathName.endsWith(".csv") || pathName.endsWith(".csv"))
        {		
			return true;
        }
        return false;
	}
}