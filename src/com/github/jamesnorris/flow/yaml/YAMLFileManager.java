package com.github.jamesnorris.flow.yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.io.Files;

public class YAMLFileManager {
    private File file;
    private YAMLValue[] values;
    private String header, footer;
    private boolean created;
    private Charset charset = Charset.forName("UTF-8");

    public YAMLFileManager(File file, YAMLValue... values) {
        try {
            this.file = file;
            if (!file.exists()) {
                file.createNewFile();
            }
            this.values = values;
            /* TODO writes to the file without use of Bukkit YAML parsing. Simply adds '#' to every new line of the comments,
             * and places the value and the setting as so: 'value: setting' */
        } catch (Exception ex) {
            System.err.println("Flow could not create the file: " + file.getPath() + ".\n" + ex.getMessage());
        }
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }

    public boolean hasBeenCreated() {
        return created;
    }

    public void writeValuesToFile(boolean writeComments) {
        writeValuesToFile(true, true, writeComments);
    }

    public void writeValuesToFile(boolean writeHeader, boolean writeFooter, boolean writeComments) {
        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            if (writeHeader && header != null) {
                String[] headerLines = header.split("\\r?\\n");
                for (String headerLine : headerLines) {
                    writer.println("#" + headerLine);
                }
                writer.print("\n\n");
            }
            for (YAMLValue value : values) {
                writer.println(value.getValue() + ": " + value.get());
                if (writeComments && value.getComment() != null) {
                    String[] commentLines = value.getComment().split("\\r?\\n");
                    for (String commentLine : commentLines) {
                        writer.println("#" + commentLine);
                    }
                }
                writer.print("\n");
            }
            if (writeFooter && footer != null) {
                writer.print("\n");
                String[] footerLines = footer.split("\\r?\\n");
                for (String footerLine : footerLines) {
                    writer.println("#" + footerLine);
                }
            }
            writer.flush();
            writer.close();
            created = true;
        } catch (Exception ex) {
            System.err.println("Could not create the file " + file.getPath() + ".\n" + ex.getMessage());
        }
    }

    public List<String> getLines() {
        try {
            return Files.readLines(file, charset);
        } catch (IOException ex) {
            System.err.println("Could not get the lines from file " + file.getPath() + ".\n" + ex.getMessage());
        }
        return null;
    }

    public int find(int startingIndex, String text) {
        return find(startingIndex, text, false, false, true);
    }

    public int find(int startingIndex, String text, boolean wholeLine, boolean caseSensitive, boolean commented) {
        List<String> fileLines = getLines();
        for (int lineNumber = startingIndex; lineNumber < fileLines.size(); lineNumber++) {
            String line = fileLines.get(lineNumber);
            if (!commented && line.startsWith("#")) {
                continue;
            }
            boolean contained = caseSensitive && line.contains(text);
            boolean containedIgnoreCase = !caseSensitive && line.toLowerCase().contains(text.toLowerCase());
            boolean equalsWholeLine = wholeLine && (caseSensitive ? line : line.toLowerCase()).equals(caseSensitive ? text : text.toLowerCase());
            if (contained || containedIgnoreCase || equalsWholeLine) {
                return lineNumber;
            }
        }
        return -1;
    }

    public boolean replace(String oldKey, YAMLValue updated) {
        for (YAMLValue value : values) {
            if (value.getValue().equals(oldKey)) {
                return replace(value, updated);
            }
        }
        return false;
    }

    public boolean replace(YAMLValue old, YAMLValue updated) {
        return replace(0, old.getValue() + ": " + old.get(), updated.getValue() + ": " + updated.get(), true, true, false);
        // TODO replace the comments too
    }

    public boolean replace(String old, String updated) {
        return replace(0, old, updated);
    }

    public boolean replace(int startingIndex, String old, String updated) {
        return replace(startingIndex, old, updated, false, false, true);
    }

    public boolean replace(int startingIndex, String old, String updated, boolean wholeLine, boolean caseSensitive, boolean commented) {
        try {
            List<String> lines = getLines();
            int lineIndex = find(startingIndex, old, wholeLine, caseSensitive, commented);
            if (lineIndex == -1) {
                return false;
            }
            lines.set(lineIndex, lines.get(lineIndex).replace(old, updated));
            FileWriter writer = new FileWriter(file);
            writer.write(compact(lines));
            writer.close();
        } catch (Exception ex) {
            System.err.println("Could not replace \"" + old + "\" with \"" + updated + "\" in file" + file.getPath() + ".\n" + ex.getMessage());
        }
        return true;
    }

    public void replaceAll(String old, String updated) {
        replaceAll(0, getLines().size(), old, updated);
    }

    public void replaceAll(int startingIndex, int endingIndex, String old, String updated) {
        // TODO same thing as replace, just with the whole file
    }

    protected String compact(List<String> lines) {
        String compacted = "";
        for (String line : lines) {
            compacted += line;
        }
        return compacted;
    }

    public void createComments() {
        // TODO search for all values, and add comments after each, according to the YAMLValue assigned
    }

    public void removeComments() {
        // TODO read the file and remove all lines beginning with '#'
    }
    // TODO getters and setters
}
