package com.shallop.bpc.collection.persistent;

import com.couchbase.lite.Context;
import com.couchbase.lite.NetworkReachabilityManager;
import com.couchbase.lite.storage.JavaSQLiteStorageEngineFactory;
import com.couchbase.lite.storage.SQLiteStorageEngineFactory;
import com.couchbase.lite.support.Version;

import java.io.File;

/**
 * @author chenxuanlong
 * @date 2017/9/30
 */
public class JavaContext2 implements Context {
	private String subdir;

	public JavaContext2(String subdir) {
		this.subdir = subdir;
	}

	public JavaContext2() {
		this.subdir = "cblite2";
	}

	public File getFilesDir() {
		return new File(this.getRootDirectory(), this.subdir);
	}

	public File getTempDir() {
		return new File(System.getProperty("java.io.tmpdir"));
	}

	public void setNetworkReachabilityManager(NetworkReachabilityManager networkReachabilityManager) {
	}

	public NetworkReachabilityManager getNetworkReachabilityManager() {
		return new JavaContext2.FakeNetworkReachabilityManager();
	}

	public SQLiteStorageEngineFactory getSQLiteStorageEngineFactory() {
		return new JavaSQLiteStorageEngineFactory();
	}

	public File getRootDirectory() {
		String rootDirectoryPath = System.getProperty("user.dir");
		return new File(rootDirectoryPath, "data/data/com.couchbase.lite.test/files");
	}

	public String getUserAgent() {
		return String.format("CouchbaseLite/%s (Java %s/%s %s/%s)", "1.3", System.getProperty("os.name"), System.getProperty("os.arch"), Version
				.getVersionName(), Version.getCommitHash());
	}

	class FakeNetworkReachabilityManager extends NetworkReachabilityManager {
		FakeNetworkReachabilityManager() {
		}

		public void startListening() {
		}

		public void stopListening() {
		}

		public boolean isOnline() {
			return true;
		}
	}
}
