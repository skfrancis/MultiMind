package subversion;

import java.io.File;
import java.util.Collection;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;




public class SVNConnector {
	
	private static SVNConnection svnConnection;
	private static SVNClientManager svnManager;
	private static DefaultSVNOptions options;
	private static SVNRepository repository;
	
	private SVNConnector() {
		setUpLibrary();
		options = SVNWCUtil.createDefaultOptions(true);
		svnConnection = null;
	}
		
	public static void setConnection(SVNConnection connection) throws SVNException {
		svnConnection = connection;
		if (svnConnection != null) {
			svnManager = SVNClientManager.newInstance(options, connection.getUserName(), connection.getPassword());
			repository = svnManager.createRepository(SVNURL.parseURIEncoded(connection.getHostURL()), true);
		}
	}
	
	public static subversion.SVNConnection getConnection() {
		return svnConnection;
	}
	
	public static SVNRepository getRepository() {
		return repository;
	}
	
		
	public static Collection<?> getRepositoryEntries (String path) throws SVNException {
		Collection<?> entries = repository.getDir(path, -1, null, (Collection<?>) null);
		return entries;
	}
	
	public static void checkOutRepository(File directory) throws SVNException {
		SVNUpdateClient updateClient = svnManager.getUpdateClient();
		SVNURL url = SVNURL.parseURIEncoded(svnConnection.getHostURL());
		// doCheckout(SVNURL url, File dstPath, SVNRevision pegRevision, SVNRevision revision, SVNDepth depth, boolean allowUnversionedObstructions) 
		updateClient.doCheckout(url, directory , SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, true);
	}
	
	/**
     * Initializes the library to work with a repository via 
     * different protocols.
     */
	private static void setUpLibrary() {
        DAVRepositoryFactory.setup();     // For using over http:// and https://
        SVNRepositoryFactoryImpl.setup(); // For using over svn:// and svn+xxx://
        FSRepositoryFactory.setup();      // For using over file:///
    }
}
