package jankins;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Watch {
    
    public static void main(String[] args) {
        validateInput(args);
        watch(new File(args[0]));
    }

    public static void validateInput(String [] args){
        String ussage = "Ussage:\n\t mvn exec:java -Dexec.args=\"<PathToLocalGitRepo>\"";
        if(args.length != 1){
            System.err.println("Accepts only 1 argument \n" + ussage);
            System.exit(1);
        }
        String RepoPath = args[0];
        File repo = new File(RepoPath);
        if (!repo.exists() || !repo.isDirectory()) {
            System.err.println("The path you provided is not a Directory\n" + ussage);
            System.exit(1);
        }
        if(!CheckForGit(repo)){
            System.err.println("No git repository found at path.\n\tTo make a git repository use command git init <Directory> \n" + ussage);
            System.exit(1);
        }
        
    }
    public static boolean CheckForGit(File repo) {
        File git = new File(repo.getAbsolutePath() + "/.git");
        return git.exists();
    }

    public static void watch(File repo){
        File headFile = new File(repo.getAbsolutePath() + "/.git/logs/HEAD");
                long lastModified = headFile.lastModified();
                System.out.println("Watching for commits: last head modification at-" + lastModified);
                while (true) {
                    if (headFile.lastModified() != lastModified) {
                        lastModified = headFile.lastModified();
                        System.out.println("Commit Detected! at " + lastModified);
                        makePost(repo.getAbsolutePath());
                    }
                }
    }
    public static void makePost(String RepoPath){
        try{
            URL url = new URL("http://localhost:8080/jankins/Repo");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            String jsonInputString = "{RepoPath: \""+RepoPath+"\"}";
            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);			
            }
            StringBuilder responseBody = new StringBuilder();
            BufferedReader reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            while(reader.ready()){
                responseBody.append(reader.readLine() + "\n");
            }
            System.out.println("Response:" + connection.getResponseCode() + "\n" + responseBody);
            reader.close();
        }catch( MalformedURLException e){
            e.printStackTrace();
        }catch( IOException e){
            e.printStackTrace();
        }
    }
}