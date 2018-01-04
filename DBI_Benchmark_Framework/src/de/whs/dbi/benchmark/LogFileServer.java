package de.whs.dbi.benchmark;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogFileServer extends Thread
{
    protected String dir;
    protected ServerSocket serverSocket;
    protected Integer transferCount;
    protected int loadDriverCount;
    protected int connectedCount;

    public LogFileServer(int loadDriverCount, int port, String dir) throws IOException
    {
        this.loadDriverCount = loadDriverCount;
        this.dir = dir;
        Path path = Paths.get(this.dir);
        if (!Files.exists(path)) Files.createDirectories(path);
        transferCount = 0;
        connectedCount = 0;
        serverSocket = new ServerSocket(port);
    }
    
    public void run()
    {
        collectFiles();
    }

    public void collectFiles()
    {
        while (connectedCount < loadDriverCount)
        {
            try
            {
                Socket clientSocket = serverSocket.accept();
                connectedCount++;
                Receiver receiver = new Receiver(clientSocket);
                receiver.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        // warten bis alle LoadDriver ihre Dateien uebertragen haben.
        while (getTransferCount() < loadDriverCount)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    private class Receiver extends Thread
    {
        private Socket clientSocket;

        public Receiver(Socket clientSocket)
        {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run()
        {
            try
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String fileName = br.readLine();
                // Path path = Paths.get(dir + "/" + fileName);
                // BufferedWriter writer = Files.newBufferedWriter(path); //
                // TODO nicht Java 7 kompatibel!

                File file = new File(dir + "/" + fileName);
                if (!file.exists())
                {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);

                String str;
                while ((str = br.readLine()) != null)
                {
                    bw.write(str);
                    bw.newLine();
                }
                bw.flush(); // schreibt den kompletten Buffer ins File
                bw.close();
                br.close();
                clientSocket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                increaseTransferCount();
            }
        }
    }

    public int getTransferCount()
    {
        synchronized (transferCount)
        {
            return transferCount; // Wird im Hintergrund auf int gecasted,
                                  // da keine Referenz auf das
                                  // transferCount-Objekt (Integer)
                                  // nach aussen zurueckgegeben werden darf.
                                  // (Concurrency)
        }
    }

    protected void increaseTransferCount()
    {
        synchronized (transferCount)
        {
            transferCount++;
        }
    }
}
