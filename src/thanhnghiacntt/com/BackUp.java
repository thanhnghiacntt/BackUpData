/**
 * 
 */
package thanhnghiacntt.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackUp {

	/**
	 * Expired time to delete
	 */
	private int expiredTime;
	
	/**
	 * Period
	 */
	private int period;
	
	/**
	 * Start date
	 */
	private Date start;
	
	/**
	 * End date
	 */
	private Date end;
	
	/**
	 * Source folder to delete
	 */
	private String src;
	
	/**
	 * Destination folder to delete
	 */
	private String des;
	
	/**
	 * Is helpper
	 */
	private boolean isHelp;

	/**
	 * Copy
	 */
	public void copy() {
		copy(this.src, this.des);
	}

	/**
	 * Copy
	 * @param source folder to delete
	 * @param destination folder to delete
	 */
	public void copy(String source, String destination) {
		File fSrc = new File(source);
		String folderDest = destination + File.separator + getCurrentDate();
		File fDes = new File(folderDest);
		if (fSrc.exists()) {
			recursiveCopy(fSrc, fDes);
		}
	}

	/**
	 * Delete
	 */
	public void delete() {
		long expried = new Date().getTime() - this.expiredTime * 1000;
		delete(this.des, expried);
	}

	/**
	 * Delete
	 * @param src source folder to delete
	 * @param expried time to delete
	 */
	public void delete(String src, long expried) {
		File fSrc = new File(src);
		if (fSrc.exists()) {
			delete(fSrc, expried);
		}
	}

	/**
	 * Delete
	 * @param fSrc file folder
	 * @param expired
	 */
	private void delete(File fSrc, long expired) {
		if (fSrc.isDirectory()) {
			if (fSrc.lastModified() < expired) {
				deleteAll(fSrc);
			} else {
				File[] fList = fSrc.listFiles();
				for (File file : fList) {
					delete(file, expired);
				}
			}
		} else {
			if (fSrc.lastModified() < expired) {
				fSrc.delete();
			}
		}
	}

	/**
	 * Delete all
	 * @param file
	 */
	private void deleteAll(File file) {
		if (file.isDirectory()) {
			File[] f = file.listFiles();
			for (File temp : f) {
				deleteAll(temp);
				temp.delete();
			}
		}
		file.delete();
		System.out.println("Deleted " + file.getAbsolutePath());
	}

	/**
	 * Get current date
	 * @return
	 */
	public String getCurrentDate() {
		Date date = new Date();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		return formatter.format(date);
	}

	/**
	 * Get date
	 * @param dateString
	 * @return
	 */
	public Date getDate(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = null;
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Copy recursive
	 * @param fSource
	 * @param fDest
	 */
	private void recursiveCopy(File fSource, File fDest) {
		try {
			if (fSource.isDirectory()) {
				// A simple validation, if the destination is not exist then
				// create it
				if (!fDest.exists()) {
					fDest.mkdirs();
				}

				// Create list of files and directories on the current source
				// Note: with the recursion 'fSource' changed accordingly
				String[] fList = fSource.list();

				for (int index = 0; index < fList.length; index++) {
					File dest = new File(fDest, fList[index]);
					File source = new File(fSource, fList[index]);

					// Recursion call take place here
					recursiveCopy(source, dest);
				}
			} else {
				// Found a file. Copy it into the destination, which is already
				// created in 'if' condition above

				// Open a file for read and write (copy)
				FileInputStream fInStream = new FileInputStream(fSource);
				FileOutputStream fOutStream = new FileOutputStream(fDest);

				// Read 2K at a time from the file
				byte[] buffer = new byte[2048];
				int iBytesReads;

				// In each successful read, write back to the source
				while ((iBytesReads = fInStream.read(buffer)) >= 0) {
					fOutStream.write(buffer, 0, iBytesReads);
				}

				// Safe exit
				if (fInStream != null) {
					fInStream.close();
				}

				if (fOutStream != null) {
					fOutStream.close();
				}
				
			}
		} catch (Exception ex) {
			// Please handle all the relevant exceptions here
		}
	}

	/**
	 * Get expired time
	 * @return
	 */
	public int getExpiredTime() {
		return expiredTime;
	}

	/**
	 * Set expired time
	 * @param expiredTime
	 */
	public void setExpiredTime(int expiredTime) {
		this.expiredTime = expiredTime;
	}

	/**
	 * Get start date
	 * @return
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * Set start date
	 * @param start
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * Get end date
	 * @return
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * Set end date
	 * @param end
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * Get source folder
	 * @return
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * Set source folder
	 * @param src
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * Get destination folder
	 * @return
	 */
	public String getDes() {
		return des;
	}

	/**
	 * Set destination folder
	 * @param des
	 */
	public void setDes(String des) {
		this.des = des;
	}

	/**
	 * Is helper
	 * @return
	 */
	public boolean isHelp() {
		return isHelp;
	}

	/**
	 * Set helper
	 * @param isHelp
	 */
	public void setHelp(boolean isHelp) {
		this.isHelp = isHelp;
	}

	/**
	 * Get period
	 * @return
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * Set period
	 * @param period
	 */
	public void setPeriod(int period) {
		this.period = period;
	}
}
