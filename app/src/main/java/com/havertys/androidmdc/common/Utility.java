package com.havertys.androidmdc.common;

import java.io.InputStream;
import java.util.Scanner;

public class Utility {
	/**
	 * Validate a scanned wms location or a wms location enetered on a screen.  
	 * @param wmsLocationInput wms location input to be validated from a screen or scanner
	 * @return wms location without identifiers and check digits if it is valid, otherwise null 
	 */
	public static String wmsLocationInputValidate(String wmsLocationInput) {
		String wmsLocationResult = null;

		if (wmsLocationInput.substring(0,1).equals("L")) {
			wmsLocationResult = wmsLocationInput.substring(1).trim();
		}
		else if ((wmsLocationInput.substring(0,1).equals("R")) && (wmsLocationInput.substring(1,2).equals(".")) && (wmsLocationInput.substring(5,6).equals(".")) && (wmsLocationInput.substring(7,8).equals("."))) {
			wmsLocationResult = wmsLocationInput.substring(0,1) +  wmsLocationInput.substring(2,5) +  wmsLocationInput.substring(6,7) +  wmsLocationInput.substring(8);  
		}
		else {
			int scannedLength = wmsLocationInput.length();
			if ((scannedLength == 0) || (scannedLength < 9)) {
				wmsLocationResult = wmsLocationInput;
			}
		}

		return wmsLocationResult;
	}

	/**
	 * Validate a scanned lp or an lp enetered on a screen.  
	 * @param lpInput lp input to be validated from a screen or scanner
	 * @return lp number without identifiers and check digits if it is valid, otherwise null 
	 */
	public static Long lpInputValidate(String lpInput) {
		Long lpResult = null;
		final int maximumLengthLPKeyed = 12;
		final int lengthSmallLabel = 13;
		final int lengthRegularLabel = 22;

		try {
			if (lpInput.length() <= maximumLengthLPKeyed) {
				if (lpValidateSiteId(lpInput)) {
					lpResult = new Long(lpInput);
				}
			}
			if (lpInput.length() == lengthSmallLabel){
				if (lpInput.substring(0,1).equals("7")) {
					if (lpValidateSiteId(lpInput.substring(1,13))) {
						lpResult = new Long(lpInput.substring(1,13));
					}
				}
			}
			if (lpInput.length()==lengthRegularLabel){
				if (lpInput.substring(0,1).equals("7")) {
					if (lpValidateSiteId(lpInput.substring(10,22))) {
						lpResult = new Long(lpInput.substring(10,22));
					}
				}
			}			
		}
		catch (NumberFormatException ne) {
			lpResult = null;
		}

		return lpResult;
	}

	/**
	 * Validate that an LP number contains a valid site ID.
	 * @param lpInput lp input to be validated for a valid site ID
	 * @return true if lp has a valid site ID, otherwise false
	 */
	public static boolean lpValidateSiteId (String lpInput) {
		boolean validSiteId = false;
		if (lpInput.substring(lpInput.length() - 3, lpInput.length() - 1) .equals("40") ||
				lpInput.substring(lpInput.length() - 3, lpInput.length() - 1) .equals("02")||
				lpInput.substring(lpInput.length() - 3, lpInput.length() - 1) .equals("30")||
				lpInput.substring(lpInput.length() - 3, lpInput.length() - 1) .equals("33")||
				lpInput.substring(lpInput.length() - 3, lpInput.length() - 1) .equals("34")||
				lpInput.substring(lpInput.length() - 3, lpInput.length() - 1) .equals("35")) {
			validSiteId = true;
		}
		return validSiteId;
	}

	public static String fixedLengthString(String s, int requestedLength) {
		return fixedLengthString(s, requestedLength, " ");
	}

	public static String fixedLengthString(String s, int requestedLength, String str) {
		if (s == null || requestedLength < 0) {
			s = "";
		}
		StringBuffer sb = new StringBuffer(s);
		if (sb.length() > requestedLength) {
			sb.setLength(requestedLength);
		}
		while (sb.length() < requestedLength) {
			sb.append(str);
		}
		return sb.toString();
	}

    public static String getResponseText(InputStream in) {

        Scanner scanner = null;
        try {
            scanner = new Scanner(in);
            return scanner.useDelimiter("\\A").next();
        } finally {
            if(scanner != null)
                scanner.close();
        }
    }
}