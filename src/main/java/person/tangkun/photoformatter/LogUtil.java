/*
 * Copyright (c) 2025 by Tang Kun. All rights reserved.
 */

package person.tangkun.photoformatter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtil {

    private static final Logger logger = Logger.getLogger(LogUtil.class.getName());

    public static void e(String TAG, String msg){
        logger.log(Level.SEVERE, TAG + ", " + msg);
    }

    public static void w(String TAG, String msg){
        logger.log(Level.WARNING, TAG + ", " + msg);
    }

    public static void i(String TAG, String msg){
        logger.log(Level.INFO, TAG + ", " + msg);
    }
}
