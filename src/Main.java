import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress[] games = new GameProgress[]{
                new GameProgress(100, 50, 1, 30.0),
                new GameProgress(85, 30, 2, 63.3),
                new GameProgress(70, 15, 5, 96.5)
        };

        String gamePathSave = "C:/Users/shayl/Games/savegames/save";
        String gamePathSaveZIP = "C:/Users/shayl/Games/savegames/save.zip";

        saveGame(gamePathSave, games);
        File dir = new File("C:/Users/shayl/Games/savegames");

        File[] fileList = null;

        if (dir.isDirectory()) {
            fileList = dir.listFiles();
        }

        zipFiles(gamePathSaveZIP, fileList);
    }

    static void saveGame(String path, GameProgress[] games) {
        for (int i = 0; i < games.length; i++ ) {
            try (FileOutputStream fileSave = new FileOutputStream(path + i + ".dat");
                 ObjectOutputStream obj = new ObjectOutputStream(fileSave);) {

                obj.writeObject(games[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static void zipFiles(String gamePathSaveZIP, File[] fileList) {

        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(gamePathSaveZIP))) {
            for (File file : fileList) {
                FileInputStream fis = new FileInputStream(file.getPath());
                ZipEntry entry = new ZipEntry(file.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (File path : fileList) {
            path.delete();
        }
    }
}