/*******************************************************************************
 *
 * Copyright 2007 Randall
 *
 * This file is part of gomule.
 *
 * gomule is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * gomule is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * gomlue; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 *
 ******************************************************************************/
package randall.util;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco
 * @version 1.0
 */
public class RandallFileFilter extends FileFilter {

    private final List<String> iExtensions;
    private final String iDescription;

    public RandallFileFilter(String pDescription) {
        iExtensions = new ArrayList<>();
        iDescription = pDescription;
    }

    public boolean accept(File pFile) {
        for (String iExtension : iExtensions) {
            if (pFile.isDirectory()) {
                return true;
            }
            if (pFile.getAbsolutePath().toLowerCase().endsWith(iExtension.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void addExtension(String pExtension) {
        iExtensions.add(pExtension);
    }

    public String getDescription() {
        return iDescription;
    }
}