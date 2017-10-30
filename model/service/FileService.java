package model.service;

public interface FileService {
	boolean transFile(String gisu, String id, String abFile) throws Exception;
	boolean saveFile(String gisu, String id, String imgLoc) throws Exception;
	void makeFolder(String gisu) throws Exception;
}
