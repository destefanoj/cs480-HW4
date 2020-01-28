import java.util.Scanner;

class FileScanner {

    static String getLine(Scanner s) {
        String line;
        while (true) {
            if (!s.hasNextLine()) { break; }
            line = s.nextLine();
            line = line.trim();
            if (line.length() > 0){ return line; }
        }
        return null;
    }
}

