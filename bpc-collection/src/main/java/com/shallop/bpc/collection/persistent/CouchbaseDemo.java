package com.shallop.bpc.collection.persistent;

import com.alibaba.fastjson.JSON;
import com.couchbase.lite.*;
import com.couchbase.lite.auth.Authenticator;
import com.couchbase.lite.auth.AuthenticatorFactory;
import com.couchbase.lite.replicator.Replication;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author chenxuanlong
 * @date 2017/9/30
 */
public class CouchbaseDemo {

	@Test
	public void testConflict() throws IOException, CouchbaseLiteException {
		Manager manager = new Manager(new JavaContext(), Manager.DEFAULT_OPTIONS);
		Database database = manager.getDatabase("lifego");
		// This code can be found in Application.java
		// in the startConflictLiveQuery() method
		LiveQuery conflictsLiveQuery = database.createAllDocumentsQuery().toLiveQuery();
		conflictsLiveQuery.setAllDocsMode(Query.AllDocsMode.ONLY_CONFLICTS);
		conflictsLiveQuery.addChangeListener(new LiveQuery.ChangeListener() {
			@Override
			public void changed(LiveQuery.ChangeEvent event) {
				System.out.println("conflicts rows:" + JSON.toJSON(event.getRows()));
			}
		});
		conflictsLiveQuery.start();
	}

	@Test
	public void test(){
		(new ObjectMapper()).disable(new JsonParser.Feature[]{ JsonParser.Feature.AUTO_CLOSE_SOURCE});
	}

	@Test
	public void testDB2() throws Exception {
		testCouchbaseDemo(new JavaContext2());
	}

	@Test
	public void testDB1() throws Exception {
		testCouchbaseDemo(new JavaContext());
	}

	public void testCouchbaseDemo(Context context) throws Exception {
		// Get the database (and create it if it doesn’t exist).
		Manager manager = new Manager(context, Manager.DEFAULT_OPTIONS);
		Database database = manager.getDatabase("lifego");
		// Create a new document (i.e. a record) in the database.
//		Document document = database.createDocument();
		newDocAndUpdate(database);

		// Delete a document.
//		document.delete();

		// Create replicators to push & pull changes to & from the cloud.
		URL url = new URL("http://n.longxuan.tk:4984/lifego");
		Replication push = database.createPushReplication(url);
		Replication pull = database.createPullReplication(url);
		push.setContinuous(true);
		pull.setContinuous(true);
		pull.addChangeListener(changeEvent -> {
			System.out.println("pull Change:" + JSON.toJSONString(changeEvent));
		});

		// Add authentication.
		String name = "test";
		String password = "123456";
		Authenticator authenticator = AuthenticatorFactory.createBasicAuthenticator(name, password);
		push.setAuthenticator(authenticator);
		pull.setAuthenticator(authenticator);

		// Listen to database change events (there are also change
		// events for documents, replications, and queries).
		database.addChangeListener(changeEvent -> {
			System.out.println("database change" + JSON.toJSONString(changeEvent.getChanges()));
		});
		push.addChangeListener(changeEvent -> {
			System.out.println("push Change:" + JSON.toJSONString(changeEvent));
		});
		// Start replicators
		push.start();
		pull.start();
		System.in.read();
	}

	private void newDocAndUpdate(Database database) throws CouchbaseLiteException {
		new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String docId = "test" + "." + UUID.randomUUID().toString().replace("-", "");
		System.out.println(docId);
		Document document = database.getDocument(docId);
		Map properties = new HashMap();
		properties.put("firstName", "John");
		document.putProperties(properties);

		// Update a document.
		document.update(new Document.DocumentUpdater() {
			@Override
			public boolean update(UnsavedRevision newRevision) {
				Map properties = newRevision.getUserProperties();
				properties.put("firstName", "Johnny");
				newRevision.setUserProperties(properties);
				return true;
			}
		});
	}

}
